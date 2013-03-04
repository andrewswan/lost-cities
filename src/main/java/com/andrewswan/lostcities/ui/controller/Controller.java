/**
 * Copyright (c) Andrew Swan 2008
 */
package com.andrewswan.lostcities.ui.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

import com.andrewswan.lostcities.domain.Card;
import com.andrewswan.lostcities.domain.Game;
import com.andrewswan.lostcities.domain.Suit;
import com.andrewswan.lostcities.domain.move.CardPlay;
import com.andrewswan.lostcities.domain.move.Discard;
import com.andrewswan.lostcities.domain.move.DrawFromDeck;
import com.andrewswan.lostcities.domain.move.DrawFromDiscardPile;
import com.andrewswan.lostcities.domain.player.Player;
import com.andrewswan.lostcities.ui.view.MusicPlayer;
import com.andrewswan.lostcities.ui.view.View;

/**
 * The "C" in MVC for this program. Should be view-neutral, i.e. not coupled to
 * any specific presentation technology.
 *
 * Implements {@link Observer} so that it can be notified of changes to the
 * {@link Game} despite the latter not having a typed reference to it.
 *
 * @author Andrew
 */
public class Controller implements Observer {

	// Constants
	protected static final Log LOGGER = LogFactory.getLog(Controller.class);

	// Properties
	final Game game;
	private final MusicPlayer musicPlayer;
	final View view;

	/**
	 * Constructor; wires up this Controller with the given model and view (MVC
	 * pattern). The caller can then display the view to start receiving user
	 * input.
	 *
	 * @param game the domain model being controlled; can't be <code>null</code>
	 * @param view the view from which this controller receives user events and
	 *   which it updates in response to changes in the model; can't be
	 *   <code>null</code>
	 * @param musicPlayer can't be <code>null</code>
	 */
	public Controller(
			final Game game, final View view, final MusicPlayer musicPlayer)
	{
		Assert.notNull(game, "Game can't be null");
		Assert.notNull(musicPlayer, "Music player can't be null");
		Assert.notNull(view, "View can't be null");

		// Wire this controller to the game and vice-versa
		this.game = game;
		game.addObserver(this);

		// Wire this controller to the music player
		this.musicPlayer = musicPlayer;
		this.musicPlayer.play();

		// Wire this controller to the view and vice-versa
		this.view = view;
		registerViewListeners();
	}

	/**
	 * Registers all necessary event listeners with the view
	 */
	private void registerViewListeners() {
		this.view.addHandListener(new HandCardListener());
		this.view.addNewGameListener(new NewGameListener());
		this.view.addHumanCardPlayListener(new HumanCardPlayListener());
		this.view.addDeckListener(new DeckListener());
		this.view.addDiscardPilesListener(new DiscardPilesListener());
	}

	public void update(final Observable observable, final Object payload) {
		// Only expect updates from the game to which we already have a reference
		Assert.state(observable == this.game,
		    "Unexpected observable: " + observable);
		LOGGER.debug("Received notification of a game update");
		// Update the view
		this.view.setGameState(this.game);
	}

	// -------------------------------- Listeners --------------------------------

	/**
	 * Handles events coming from a card in the human player's hand
	 *
	 * @author Andrew
	 */
	class HandCardListener implements ActionListener {

		public void actionPerformed(final ActionEvent event) {
			LOGGER.debug("Received event " + event);
			// Player two is playing a card from their hand
			final Player player = Controller.this.game.getPlayerTwo();
			// Find out which card (the "action command" of the event is its index)
			final int cardIndex = Integer.parseInt(event.getActionCommand());
			final Card playedCard = player.getHand().get(cardIndex);
			// Can the player add this card to their expedition in that suit?
			final boolean canAddToExpedition =
					Controller.this.game.canAddToExpedition(player, cardIndex);
			if (canAddToExpedition) {
				// Ask the player what they want to do with the card
				Controller.this.view.promptForHumanPlayerCardPlay(playedCard);
			}
			else {
				handleForcedDiscard(playedCard);
			}
		}

		/**
		 * Handles the situation where the human player is forced to discard the
		 * card they played
		 * 
		 * @param discardedCard
		 */
		private void handleForcedDiscard(final Card discardedCard) {
			// Can't add to expedition => automatically discard the card
			final CardPlay cardPlay = new Discard(discardedCard);
			Controller.this.game.executeHumanMove(cardPlay);
			// Now player two needs to draw a card; can they pick up a discard?
			final Set<Suit> drawableDiscards =
					Controller.this.game.getDrawableDiscards(discardedCard.getSuit());
			if (drawableDiscards.isEmpty()) {
				// Can't pick up a discard; automatically draw from deck
				Controller.this.game.executeHumanMove(new DrawFromDeck());
			}
			else {
				// Player has the option of drawing from the deck or a discard pile =>
				// prompt the user.
				Controller.this.view.promptForHumanPlayerCardDraw(drawableDiscards);
			}
		}
	}

	/**
	 * Handles the user choosing to start a new game
	 *
	 * @author Andrew
	 */
	class NewGameListener implements ActionListener {

		public void actionPerformed(final ActionEvent e) {
			LOGGER.debug("Detected a New Game event");
			// Reset the model; this will cause the game to notify us of changes
			Controller.this.game.reset();
		}
	}
	
	/**
	 * Handles the human user deciding how to play a card that can be played in
	 * more than one way
	 * 
	 * @author Andrew
	 */
	class HumanCardPlayListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			LOGGER.debug("Detected a Human Card Play event");
			final CardPlay cardPlay = (CardPlay) e.getSource();
			LOGGER.debug("Human card play = " + cardPlay);
			Controller.this.game.executeHumanMove(cardPlay);
		}
	}

	/**
	 * Handles the human user choosing to draw from the deck
	 * 
	 * @author Andrew
	 */
	class DeckListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			LOGGER.debug("Detected a Deck event");
			Controller.this.game.executeHumanMove(new DrawFromDeck());
		}
	}
	
	/**
	 * Handles the human user choosing to draw from a discard pile
	 * 
	 * @author Andrew
	 */
	class DiscardPilesListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			LOGGER.debug("Detected a Discard Piles event");
			// The action command should be the internal name of the drawn suit
			final Suit drawnSuit = Suit.valueOf(e.getActionCommand());
			Controller.this.game.executeHumanMove(new DrawFromDiscardPile(drawnSuit));
		}
	}
}
