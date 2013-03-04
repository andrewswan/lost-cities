/**
 * Copyright (c) Andrew Swan 2008
 */
package com.andrewswan.lostcities.domain;

import java.util.Stack;

/**
 * The discard pile for one particular {@link Suit}
 *
 * @author Andrew
 */
public class DiscardPile {

	// Properties
	private final Stack<Card> cards;
	private final Suit suit;

	/**
	 * Constructor for an empty discard pile of the given suit
	 *
	 * @param suit the suit to be discarded into this pile; can't be
	 *   <code>null</code>
	 */
	public DiscardPile(final Suit suit) {
		this.cards = new Stack<Card>();
		this.suit = suit;
	}

	/**
	 * Constructor that creates a duplicate of the given discard pile (this is
	 * called a "copy constructor")
	 *
	 * @param discardPile the discard pile to copy; can't be <code>null</code>
	 */
	public DiscardPile(final DiscardPile discardPile) {
		this(discardPile.suit);
		this.cards.addAll(discardPile.cards);
	}

	/**
	 * Adds the given card to this discard pile
	 *
	 * @param card the card to add; can't be <code>null</code>, must be the right
	 *   suit, can't already be on this discard pile
	 * @throws IllegalArgumentException if the card is invalid for any of the
	 *   above reasons
	 */
	public void add(final Card card) {
		if (card == null || this.cards.contains(card) ||
				!card.getSuit().equals(this.suit))
	  {
			throw new IllegalArgumentException("Invalid card " + card);
		}
		this.cards.push(card);
	}

	/**
	 * Returns the top card from this discard pile (removing it)
	 *
	 * @return <code>null</code> if there weren't any discards in this pile
	 */
	public Card drawTopDiscard() {
		if (this.cards.isEmpty()) {
			return null;
		}
		return this.cards.pop();
	}

	/**
	 * Returns the suit for which this is the discard pile
	 *
	 * @return a non-<code>null</code> suit
	 */
	public Suit getSuit() {
		return this.suit;
	}

	/**
	 * Reports what card is on top of this discard pile, if any
	 *
	 * @return <code>null</code> if it's empty
	 */
	public Card inspectTopDiscard() {
		if (this.cards.isEmpty()) {
			return null;
		}
		return this.cards.peek();
	}

	/**
	 * Returns the number of cards in this discard pile
	 *
	 * @return 0 if it's empty
	 */
	public int size() {
		return this.cards.size();
	}

	/**
	 * Indicates whether this discard pile is empty
	 *
	 * @return see above
	 */
	public boolean isEmpty() {
		return cards.isEmpty();
	}
}
