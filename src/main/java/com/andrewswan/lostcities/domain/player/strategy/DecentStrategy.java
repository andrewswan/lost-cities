/**
 * Copyright (c) Andrew Swan 2008
 */
package com.andrewswan.lostcities.domain.player.strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.andrewswan.lostcities.domain.Card;
import com.andrewswan.lostcities.domain.DiscardPile;
import com.andrewswan.lostcities.domain.Expedition;
import com.andrewswan.lostcities.domain.Game;
import com.andrewswan.lostcities.domain.Suit;
import com.andrewswan.lostcities.domain.move.AddToExpedition;
import com.andrewswan.lostcities.domain.move.CardDraw;
import com.andrewswan.lostcities.domain.move.CardPlay;
import com.andrewswan.lostcities.domain.move.Discard;
import com.andrewswan.lostcities.domain.move.DrawFromDeck;
import com.andrewswan.lostcities.domain.move.DrawFromDiscardPile;
import com.andrewswan.lostcities.domain.player.Player;

/**
 * Tries to play a decent game
 *
 * @author Andrew
 */
public class DecentStrategy implements Strategy {

	// Constants
	protected static final Log LOGGER = LogFactory.getLog(DecentStrategy.class);

	// Properties
	// -- whether the computer has more number cards to play into started
	//    expeditions than the minimum number of goes it has left
	private boolean endGamePressure;
	// -- the suit discard this turn, if any
	private Suit discardedSuit;

	/**
	 * Constructor
	 */
	public DecentStrategy() {
		// Empty
	}

	public String getName() {
		return "Mr Decent";
	}

	public CardPlay getPlayCardMove(final Game game, final Player player) {
		// Get some useful information
		final int minGoesLeft = game.getMinimumNumberOfTurnsLeft();
		final List<Card> playableCards = player.getMustPlayCards();

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Computer has at least " + minGoesLeft + " goes left.");
			LOGGER.debug("Computer has " + playableCards.size() +
					" playable number cards in started expeds.");
		}

		this.discardedSuit = null;
		if (playableCards.size() >= minGoesLeft) {
			// The AI is under end-game pressure
			this.endGamePressure = true;
			// Play the least valuable card of "minGoesLeft" best cards
			LOGGER.debug("Need to get down some/all cards in hand.");
			player.sortByExpeditionValue(playableCards);
			// Play the card at the (minGoesLeft-1) position of the sorted list
			return new AddToExpedition(playableCards.get(minGoesLeft - 1));
		}
		LOGGER.debug("Not under end-game pressure");
		this.endGamePressure = false;

		LOGGER.debug("Checking for no-brainers...");
		final Set<Card> noBrainers = player.getNoBrainerExpeditionPlays(
				game.getOtherPlayersExpeditions(player));
		if (!noBrainers.isEmpty()) {
			// There's a no-brainer; play the one worth the most points
			final List<Card> noBrainersByDecreasingPoints =
					new ArrayList<Card>(noBrainers);
			player.sortByExpeditionValue(noBrainersByDecreasingPoints);
			final Card highestPointsNoBrainer = noBrainersByDecreasingPoints.get(0);
			LOGGER.debug("Highest value no-brainer is " + highestPointsNoBrainer);
			return new AddToExpedition(highestPointsNoBrainer);
		}
		LOGGER.debug("There weren't any no-brainers.");

		// Find the lowest-worth card in the AI's hand (playable or not)
		Card cardToPlay = null;
		Integer lowestValue = null;
		for (final Card card : player.getHand()) {
			final Expedition expedition = player.getExpedition(card.getSuit());
			final int thisValue = expedition.getWorth(card);
			if (lowestValue == null || thisValue < lowestValue) {
				lowestValue = thisValue;
				cardToPlay = card;
			}
		}
		// We should now have the card to play; see if it can go in its expedition
		if (cardToPlay == null) {
			throw new IllegalStateException(
					"No card found to play, hand = " + player.getHand());
		}
		LOGGER.debug("Lowest value card to play or discard = " + cardToPlay);
		final Expedition expedition = player.getExpedition(cardToPlay.getSuit());
		if (expedition.canAdd(cardToPlay)) {
			return new AddToExpedition(cardToPlay);
		}
		// Must be discarded
		this.discardedSuit = cardToPlay.getSuit();
		return new Discard(cardToPlay);
	}

	public CardDraw getDrawCardMove(final Game game, final Player player) {
		/*
		 * This AI will pick up a discard if any of the following are true:
		 * 1. Under end-game pressure
		 * 2. A discarded card is playable into a started expedition
		 * 3. A discarded card would help to start an unstarted exped that is likely
		 * to succeed (not implemented yet?)
		 */
		if (this.endGamePressure) {
			LOGGER.debug("End-game pressure: computer wants to draw a discard.");
			// Try to draw from a discard pile (not the one just discarded onto, if
			// any)
			final Suit suitToPickUp = getRandomDiscardToPickUp(game);
			if (suitToPickUp == null) {
				// Must have been no legal discards
				LOGGER.debug(
						"Wanted to pick up a discard, but had to draw from the deck.");
				return new DrawFromDeck();
			}
			// There was a discard to draw; pick it up
			return new DrawFromDiscardPile(suitToPickUp);
		}
		// No end-game pressure
		final Suit suit = game.getMostValuableDiscard(player);
		if (suit == null) {
			// No useful discards; draw from the deck
			LOGGER.debug(
					"No end-game pressure, no attractive discards; draw from the deck.");
			return new DrawFromDeck();
		}
		LOGGER.debug("No end-game pressure, drawing an attractive discard from the "
				+ suit + " discard pile.");
		return new DrawFromDiscardPile(suit);
	}

	/**
	 * Returns the suit of a random discard pile from which the AI can legally
	 * pick up
	 *
	 * @param suitDiscarded can be <code>null</code> if the AI didn't just discard
	 * @return <code>null</code> if there are no such discard piles
	 */
	private Suit getRandomDiscardToPickUp(final Game game) {
		for (Suit suit : Suit.values()) {
			final DiscardPile discardPile = game.getDiscardPile(suit);
			if (!discardPile.isEmpty() && !suit.equals(discardedSuit)) {
				// There are one or more legal discards to pick up
				do {
					// Pick a random discard pile with cards in it
					suit = Suit.getRandom();
				} while (suit.equals(discardedSuit) || discardPile.isEmpty());
				LOGGER.debug("Computer draws from the " + suit	+ " discard pile.");
				return suit;
			}
		}
		return null;
	}
}
