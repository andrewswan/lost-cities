/**
 * Copyright (c) Andrew Swan 2008
 */
package com.andrewswan.lostcities.domain;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

import com.andrewswan.lostcities.domain.move.CardDraw;
import com.andrewswan.lostcities.domain.move.CardPlay;
import com.andrewswan.lostcities.domain.move.Move;
import com.andrewswan.lostcities.domain.player.ComputerPlayer;
import com.andrewswan.lostcities.domain.player.HumanPlayer;
import com.andrewswan.lostcities.domain.player.Player;

/**
 * The root domain entity in a game of Lost Cities. Responsible for maintaining
 * and correctly transitioning the state of all domain objects. Should not be
 * aware of the controller or view other than by being {@link Observable}.
 *
 * @author Andrew
 */
public class Game extends Observable {

	// -------------------------------- Constants --------------------------------

	public static final String VERSION = "0.1";

	protected static final Log LOGGER = LogFactory.getLog(Game.class);

	// -------------------------------- Properties -------------------------------

	// Final
	private final Deck deck;
	private final Map<Suit, DiscardPile> discardPiles;
	private final Player playerOne;
	private final Player playerTwo;

	// Non-final
	private int round;
	private Player activePlayer;
	private Phase phase;

	/**
	 * Constructor for a game between the two given players, which can be any
	 * combination of {@link ComputerPlayer}s and {@link HumanPlayer}s.
	 *
	 * @param playerOne the player going first; can't be <code>null</code>
	 * @param playerTwo the player going second; can't be <code>null</code>
	 */
	public Game(final Player playerOne, final Player playerTwo) {
		Assert.notNull(playerOne, "Player one can't be null");
		Assert.notNull(playerTwo, "Player two can't be null");
		LOGGER.debug("Starting a new game of Lost Cities (V" + VERSION + ")");
		this.deck = new Deck();
		this.discardPiles = new HashMap<Suit, DiscardPile>();
		this.playerOne = playerOne;
		this.playerTwo = playerTwo;
	}

	/**
	 * Resets this game to its initial state before the first turn. Does not
	 * carry out any per-turn operations such as setting the active player.
	 */
	public void reset() {
		// Reset the round counter (one-indexed, pre-incremented)
		this.round = 0;

		// Initialise the deck
		deck.reset();

		// Initialise the discard piles for each suit
		for (final Suit suit : Suit.values()) {
			discardPiles.put(suit, new DiscardPile(suit));
		}

		// Reset each player and deal their starting hands
		playerOne.reset(deck.dealStartingHand());
		playerTwo.reset(deck.dealStartingHand());

		// Set the active player and phase
		this.activePlayer = this.playerOne;
		this.phase = Phase.PLAY;
		
		notifyObservers();
	}
	
	public void start() {
		// Have as many AI moves as possible (if any)
		executeComputerPlayerMoves();
		if (!isOver()) {
			// Must be a human player's move; let the observers know and wait
			// for the move to come in via executeHumanMove().
			notifyObservers();
		}
	}

	/**
	 * Keeps having the computer player(s) move(s) while it's still any computer
	 * player's move. Any {@link Observer}s will be notified after each AI play
	 * and draw (unless a human's turn is next, in which case the caller needs
	 * to notify them anyway).
	 */
	private void executeComputerPlayerMoves() {
		while (!isOver() && !activePlayer.isHuman()) {
			// Computer player; get and execute their move
			final ComputerPlayer computerPlayer = (ComputerPlayer) activePlayer;

			// Play
			this.phase = Phase.PLAY;
			final CardPlay play = computerPlayer.getPlayCardMove(this);
			play.execute(this, activePlayer);
			this.phase = Phase.DRAW;
			notifyObservers();

			// Draw
			final CardDraw draw = computerPlayer.getDrawCardMove(this);
			draw.execute(this, activePlayer);
			// TODO switchPlayers();
			if (!activePlayer.isHuman()) {
				notifyObservers();	// before the next AI move 
			}
		}
	}

	@Override
	public void notifyObservers() {
		setChanged();
		super.notifyObservers();
	}

	/**
	 * Performs the housekeeping at the end of the active player's turn.
	 *
	 * @return <code>true</code> if there's any more turns left in the game
	 */
	private boolean nextTurn() {
		if (isOver()) {
			this.activePlayer = null;
			this.phase = null;
			return false;
		}
		// The game is not over
		if (this.playerTwo.equals(this.activePlayer)) {
			// Player two just had their turn; it's the end of the round
			this.round++;
		}
		// Toggle the active player
		this.activePlayer = getOpponent(this.activePlayer);
		// TODO nextPhase();
		return true;
	}

	/**
	 * Gives the given player the top card from the deck
	 *
	 * @param player the player drawing the card; can't be <code>null</code>
	 * @throws IllegalStateException if there are no cards left to draw
	 */
	public void drawFromDeck(final Player player) {
		player.putInHand(deck.draw());
	}

	/**
	 * Executes the given move for the active (human) player
	 *
	 * @param move the move to execute; can't be <code>null</code>
	 * @throws IllegalStateException if the active player is not a human being
	 */
	public void executeHumanMove(final Move move) {
		Assert.state(this.activePlayer.isHuman(),
			"Active player is not a human being");
		move.execute(this, activePlayer);
		notifyObservers();
		executeComputerPlayerMoves();
		nextTurn();
	}
	
	/**
	 * Returns the number of cards left in the deck; this is public knowledge in a
	 * game of Lost Cities.
	 *
	 * @return zero or more
	 */
	public int getDeckSize() {
		return this.deck.size();
	}

	/**
	 * Returns the card on top of the given suit's discard pile
	 *
	 * @param suit the suit whose discard pile is being checked; can't be
	 *   <code>null</code>
	 * @return <code>null</code> if that discard pile is empty
	 */
	public Card getTopDiscard(final Suit suit) {
		return discardPiles.get(suit).inspectTopDiscard();
	}

	/**
	 * Gives the given player the top discard from the given suit's discard pile
	 *
	 * @param player the player drawing the card; can't be <code>null</code>
	 * @throws IllegalStateException if that discard pile is empty
	 */
	public void drawFromDiscardPile(final Player player, final Suit suit) {
		final Card topDiscard = discardPiles.get(suit).drawTopDiscard();
		Assert.state(topDiscard != null, "No " + suit.getName() + " discards");
		player.putInHand(topDiscard);
	}

	/**
	 * Discards the card at the given index of the given player's hand
	 *
	 * @param player the player discarding the card; can't be <code>null</code>
	 * @param card the zero-based index of the card being discarded
	 * @throws IllegalArgumentException if the player didn't have that card
	 */
	public void discard(final Player player, final int handIndex) {
		final Card card = player.getHand().get(handIndex);
		discard(player, card);
	}

	/**
	 * Discards the given card from the player's hand into the discard pile for
	 * that suit
	 *
	 * @param player the player discarding the card; can't be <code>null</code>
	 * @param card the card being discarded; can't be <code>null</code>
	 * @throws IllegalArgumentException if the player didn't have that card
	 */
	public void discard(final Player player, final Card card) {
		player.discard(card);
		discardPiles.get(card.getSuit()).add(card);

		// Notify the observer(s) the game state has changed
		notifyObservers();
	}

	/**
	 * Plays the given card from the player's hand into their expedition for that
	 * suit
	 *
	 * @param player the player playing the card; can't be <code>null</code>
	 * @param card the card to play; can't be <code>null</code>
	 * @throws IllegalArgumentException if the player didn't have that card or
	 *   it can't be played into that expedition
	 */
	public void addToExpedition(final Player player, final Card card) {
		player.putInExpedition(card);
	}

	/**
	 * Returns the minimum number of turns left in this game, in other words the
	 * number of turns left if neither player discard any more cards
	 *
	 * @return see above
	 */
	public int getMinimumNumberOfTurnsLeft() {
		//
		int totalDiscards = 0;

		for (final Suit suit : Suit.values()) {
			totalDiscards += discardPiles.get(suit).size();
		}
		if (deck.size() - 1 <= totalDiscards) {
			return deck.size();
		}
		final int totalCards = deck.size() + totalDiscards;
		if (totalCards / 2 * 2 == totalCards) {
			// even number
			return totalCards / 2;
		}
		// odd number
		return (totalCards / 2) + 1;
	}

	/**
	 * Returns the discard pile for the given suit
	 *
	 * @param suit the suit for which to return the discard pile; can't be
	 *   <code>null</code>
	 * @return a non-<code>null</code> copy
	 */
	public DiscardPile getDiscardPile(final Suit suit) {
		return new DiscardPile(discardPiles.get(suit));	// defensive copy
	}

	/**
	 * Returns the suit whose topmost discard gives the most points to the given
	 * player.
	 *
	 * Won't return a suit whose discard pile has an Investment card on top,
	 * because Investment cards always lower the current value of an expedition.
	 *
	 * Might return a suit that the player hasn't started yet (?), which would
	 * be bad (for the AI?).
	 *
	 * @return <code>null</code> if no such suit exists
	 */
	public Suit getMostValuableDiscard(final Player player) {
		int highestCardValue = 0;
		Suit bestSuit = null;
		for (final Suit suit : Suit.values()) {
			final DiscardPile discardPile = discardPiles.get(suit);
			final Card topDiscard = discardPile.inspectTopDiscard();
			final int thisCardValue = player.getWorth(topDiscard);
			if (thisCardValue > highestCardValue) {
				// Remember this card's expedition value and the pile it's on
				highestCardValue = thisCardValue;
				bestSuit = suit;
			}
		}
		if (bestSuit == null) {
			LOGGER.debug("No discard piles have attractive cards.");
		}
		else {
			LOGGER.debug("Best discard pile to pick up from is " + bestSuit);
		}
		return bestSuit;
	}

	/**
	 * Returns the expeditions for the given player's opponent
	 *
	 * @param player the player whose opponent's expeditions will be returned;
	 *   can't be <code>null</code>
	 * @return a non-<code>null</code> map containing copies of those expeditions
	 */
	public Map<Suit, Expedition> getOtherPlayersExpeditions(final Player player) {
		return getOpponent(player).getExpeditions();
	}

	/**
	 * Returns the given player's opponent.
	 *
	 * Not public because not all the details of a player are public, and
	 * because players are mutable and have no copy constructor.
	 *
	 * @param player the player whose opponent is to be returned; can't be
	 *   <code>null</code>
	 */
	private Player getOpponent(final Player player) {
		if (player.equals(this.playerOne)) {
			return this.playerTwo;
		}
		return this.playerOne;
	}

	/**
	 * Indicates the phase of the current turn
	 *
	 * @return <code>null</code> if no game is in progress
	 */
	public Phase getPhase() {
		return this.phase;
	}

	/**
	 * Returns the first player in this game
	 *
	 * @return a non-<code>null</code> player
	 */
	public Player getPlayerOne() {
		return this.playerOne;
	}

	/**
	 * Returns the player who goes second in this game.
	 *
	 * Ideally this method wouldn't exist, as it allows the AI to look at the
	 * human player's hand.
	 *
	 * @return a non-<code>null</code> player
	 */
	public Player getPlayerTwo() {
		return playerTwo;
	}

	/**
	 * Returns what round the game is in
	 *
	 * @return zero for a game that hasn't started yet
	 */
	public int getRound() {
		return round;
	}

	/**
	 * Indicates whether this game is over. The answer is only valid between each
	 * player's turn.
	 *
	 * @return see above
	 */
	public boolean isOver() {
		return this.deck.isEmpty();
	}

	/**
	 * Returns the card play for the active computer player
	 *
	 * @return a non-<code>null</code> card play
	 * @throws IllegalStateException if the active player is a human
	 */
	public CardPlay getComputerPlayerCardPlay() {
		if (this.activePlayer.isHuman()) {
			throw new IllegalStateException("Active player is a human");
		}
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	/**
	 * Returns the card draw for the active computer player
	 *
	 * @return a non-<code>null</code> card draw
	 * @throws IllegalStateException if the active player is a human
	 */
	public CardDraw getComputerPlayerCardDraw() {
		Assert.state(!this.activePlayer.isHuman(), "Active player is a human");
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	/**
	 * Indicates whether the active player is a human being
	 *
	 * @return <code>false</code> if the active player is a computer player (AI)
	 */
	public boolean isActivePlayerHuman() {
		return activePlayer.isHuman();
	}

	/**
	 * Handles the housekeeping at the beginning of a player's turn, namely:
	 * <ul>
	 *   <li>makes player 1 the active player if they're not already, otherwise
	 *     makes player 2 the active player</li>
	 *   <li>sets the <code>phase</code> to {@link Phase#PLAY}</li>
	 *   <li>bumps the <code>round</code> number if it's now player one's turn
	 *
	 * @throws IllegalStateException if the game is over
	 */
	private void newPlayerTurn() {
		Assert.state(!isOver(), "Game is already over");
		if (this.playerOne.equals(activePlayer)) {
			// It's now player two's turn in the current round
			LOGGER.debug(">>> Round " + this.round + ", player 2");
			this.activePlayer = this.playerTwo;
		}
		else {
			// Player one is not the active player; must be a new round
			this.round++;
			LOGGER.debug(">>> Round " + this.round + ", player 1");
			this.activePlayer = this.playerOne;
		}
		this.phase = Phase.PLAY;
	}

	/**
	 * Indicates whether it's player one's turn.
	 *
	 * @return <code>false</code> if it's player two's turn or no game is in
	 *   progress
	 */
	public boolean isPlayerOneActive() {
		return this.playerOne.equals(activePlayer);
	}

	/**
	 * Indicates whether it's player two's turn.
	 *
	 * @return <code>false</code> if it's player one's turn or no game is in
	 *   progress
	 */
	public boolean isPlayerTwoActive() {
		return this.playerTwo.equals(activePlayer);
	}

	/**
	 * Returns the deck (draw pile)
	 *
	 * @return a non-<code>null</code> copy
	 */
	public Deck getDeck() {
		return new Deck(this.deck);	// defensive copy
	}

	/**
	 * Indicates whether the given player can add the card at the given index of
	 * their hand to the relevant expedition
	 *
	 * @param player the player being checked
	 * @param handIndex the zero-based index of the card being checked
	 * @return see above
	 */
	public boolean canAddToExpedition(final Player player, final int handIndex) {
		return player.canAddToExpedition(handIndex);
	}

	/**
	 * Returns the suits for which the active player can pick up the top discard
	 *
	 * @param discardedSuit the suit just discarded by that player;
	 *   <code>null</code> if they didn't just discard a card
	 * @return a non-<code>null</code> set
	 */
	public Set<Suit> getDrawableDiscards(final Suit discardedSuit) {
		final Set<Suit> drawableDiscards = new HashSet<Suit>();
		for (final Suit suit : discardPiles.keySet()) {
			final DiscardPile discardPile = discardPiles.get(suit);
			if (!discardPile.isEmpty()
					&& !discardPile.getSuit().equals(discardedSuit))
			{
				drawableDiscards.add(suit);
			}
		}
		return drawableDiscards;
	}
}
