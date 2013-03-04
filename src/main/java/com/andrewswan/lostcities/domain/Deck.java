/**
 * Copyright (c) Andrew Swan 2008
 */
package com.andrewswan.lostcities.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The deck of cards used in a game of Lost Cities
 *
 * @author Andrew
 */
public class Deck {

	// Constants
	public static final int MAX_SIZE =
			Suit.values().length * FaceValue.values().length;

	protected static final Log LOGGER = LogFactory.getLog(Deck.class);

	// Properties
	private final Stack<Card> cards;

	/**
	 * Constructor for an empty deck
	 */
	public Deck() {
		this.cards = new Stack<Card>();
	}

	/**
	 * Copy constructor
	 *
	 * @param deck the deck to copy; can be <code>null</code> for an empty deck
	 */
	public Deck(final Deck deck) {
		this();
		if (deck != null) {
			this.cards.addAll(deck.cards);
		}
	}

	/**
	 * Resets this deck ready for a new game (no cards dealt)
	 */
	public void reset() {
		this.cards.clear();
		// Add one of each card in the game
		for (final Suit suit : Suit.values()) {
			for (final FaceValue faceValue : FaceValue.values()) {
				this.cards.add(new Card(suit, faceValue));
			}
		}
		LOGGER.debug("Deck reset to " + this.cards.size() + " cards");
		Collections.shuffle(cards);
	}

	/**
	 * Draws the top card from this deck
	 *
	 * @return a non-<code>null</code> card
	 * @throws IllegalStateException if the deck is empty
	 */
	public Card draw() {
		if (isEmpty()) {
			throw new IllegalStateException("Deck is empty");
		}
		return cards.pop();
	}

	/**
	 * Indicates whether this deck is empty, in other words no cards left to draw
	 *
	 * @return see above
	 */
	public boolean isEmpty() {
		return cards.isEmpty();
	}

	/**
	 * Returns the number of cards left in the deck (this is public knowledge in
	 * Lost Cities)
	 *
	 * @return zero or more
	 */
	public int size() {
		return cards.size();
	}

	/**
	 * Returns the cards for one player's starting hand
	 *
	 * @return a non-<code>null</code> list
	 */
	public List<Card> dealStartingHand() {
		final List<Card> startingHand = new ArrayList<Card>();
		for (int i = 0; i < Hand.LIMIT; i++) {
			startingHand.add(draw());
		}
		return startingHand;
	}
}
