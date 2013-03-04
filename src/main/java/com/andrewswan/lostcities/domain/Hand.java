// Hand.java
package com.andrewswan.lostcities.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * One player's hand of cards
 *
 * @author Andrew
 */
public class Hand {

	// Constants
	public static final int LIMIT = 8;	// Maximum no. of cards in a player's hand

	// Instance properties
	final SortedSet<Card> cards;

	/**
	 * Constructor for an empty hand
	 */
	public Hand() {
		this.cards = new TreeSet<Card>();
	}

	/**
	 * Resets this hand ready for a new game
	 *
	 * @param startingHand the initial cards in this hand; can't be
	 *   <code>null</code>
	 */
	public void reset(final List<Card> startingHand) {
		this.cards.clear();
		for (final Card card : startingHand) {
			add(card);
		}
	}

	/**
	 * Indicates whether this hand is empty
	 *
	 * @return see above
	 */
	public boolean isEmpty() {
		return this.cards.isEmpty();
	}

	/**
	 * Indicates whether this hand is full
	 *
	 * @return <code>true</code> if it contains {@link #LIMIT} cards
	 */
	public boolean isFull() {
		return this.cards.size() == LIMIT;
	}

	/**
	 * Returns the cards in this hand in their natural order
	 *
	 * @return a non-<code>null</code> copy
	 */
	public List<Card> getCards() {
		return new ArrayList<Card>(cards);
	}

	/**
	 * Returns the card at the given index
	 *
	 * @param index must be from zero to the size of this hand minus one
	 * @return <code>null</code> if there's no card at that index
	 * @throws IllegalArgumentException if the index is less than zero or greater
	 *   than or equal to {@link #LIMIT}
	 */
	public Card getCard(final int index) {
		if (index < 0 || index >= LIMIT) {
			throw new IllegalArgumentException("Invalid index " + index);
		}
		if (index > cards.size() - 1) {
			return null;
		}
		return new ArrayList<Card>(cards).get(index);
	}

	/**
	 * Returns the number of cards in this hand
	 *
	 * @return a number from zero to {@link #LIMIT} inclusive
	 */
	public int size() {
		return cards.size();
	}

	/**
	 * Adds the given card to this hand
	 *
	 * @param card the card to add; can't be <code>null</code> or already in this
	 *   hand; the hand must not already be full
	 * @throws IllegalArgumentException if the card is <code>null</code> or
	 *   already in the hand
	 * @throws IllegalStateException if the hand is already full
	 */
	public void add(final Card card) {
		if (card == null || cards.contains(card)) {
			throw new IllegalArgumentException("Invalid card " + card);
		}
		if (size() == LIMIT) {
			throw new IllegalStateException("Hand is full");
		}
		this.cards.add(card);
	}

	/**
	 * Removes the given card from this hand
	 *
	 * @param card the card to remove; can't be <code>null</code>, must actually
	 *   be in this hand
	 * @throws IllegalArgumentException if the card was <code>null</code> or not
	 *   in this hand
	 */
	public void remove(final Card card) {
		if (card == null || !cards.contains(card)) {
			throw new IllegalArgumentException("Invalid card " + card);
		}
		cards.remove(card);
	}
}
