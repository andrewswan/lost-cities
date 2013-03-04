// Expedition.java
package com.andrewswan.lostcities.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Stack;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * One player's tabled collection of a given suit, known as an Expedition in
 * this game.
 *
 * @author Andrew
 */
public class Expedition {

	// Constants
	protected static final Log LOGGER = LogFactory.getLog(Expedition.class);

	public static final int MAX_VALUE;

	static {
		// Make a new expedition in an arbitrary suit
		final Suit suit = Suit.RED;
		final Expedition expedition = new Expedition(suit);
		// Add every possible card
		for (final FaceValue value : FaceValue.values()) {
			expedition.add(new Card(suit, value));
		}
		MAX_VALUE = expedition.getValue();
	}

	// The number of bonus points awarded to a "big" expedition, i.e. one with
	// at least as many cards as the BONUS_THRESHOLD.
	private static final int BONUS = 20;

	// The point value of a started expedition before any of its cards' values are
	// added to it.
	private static final int BASE_VALUE = -20;

	// The minimum number of cards required in an expedition in order to earn the
	// bonus points for a "big" expedition.
	private static final int BONUS_THRESHOLD = 8;

	// Properties
	private final Suit suit;
	private final Stack<Card> cards;

	/**
	 * Constructor for an empty expedition in the given suit
	 *
	 * @param suit the suit of cards that will be played into this expedition
	 */
	public Expedition(final Suit suit) {
		this.cards = new Stack<Card>();
		this.suit = suit;
	}

	/**
	 * Constructor that creates a duplicate of the given expedition (copy
	 * constructor)
	 *
	 * @param expedition the expedition to copy; can't be <code>null</code>
	 */
	public Expedition(final Expedition expedition) {
		this(expedition.suit, expedition.cards);
	}

	/**
 	 * Constructor for an expedition containing the given cards
 	 *
	 * @param suit the suit of cards that will be played into this expedition
	 * @param cards the cards to put in the expedition; can be
	 *   <code>null</code> for none, otherwise must be in the correct iteration
	 *   order to be added legally
	 */
	private Expedition(final Suit suit, final Collection<Card> cards) {
		this(suit);
		if (cards != null) {
			// Make a defensive copy and validate each card added
			for (final Card card : cards) {
				add(card);
			}
		}
	}

	/**
	 * Returns the topmost card in this expedition
	 *
	 * @return <code>null</code> if this expedition is empty
	 */
	public Card getTopCard() {
		LOGGER.debug("Getting top card of " + cards.size());
		if (this.cards.isEmpty()) {
			return null;
		}
		return this.cards.peek();
	}

	/**
	 * Returns the face value of the topmost card in this expedition
	 *
	 * @return <code>null</code> if this expedition is empty
	 */
	public FaceValue getTopValue() {
		if (this.cards.isEmpty()) {
			return null;
		}
		return this.cards.peek().getFaceValue();
	}

	/**
	 * Returns the cards in this expedition
	 *
	 * @return a non-<code>null</code> copy of this list
	 */
	public List<Card> getCards() {
		return new ArrayList<Card>(this.cards);	// defensive copy
	}

	/**
	 * Adds a card of the given face value to this expedition
	 *
	 * @param value the value of card to add; can't be <code>null</code>
	 */
	public void add(final FaceValue value) {
		add(new Card(this.suit, value));
	}

	/**
	 * Adds the given card to this expedition
	 *
	 * @param card the card to add; can't be <code>null</code>, must be of the
	 *   same suit, must not already be in this expedition, must not have a lower
	 *   point value than the topmost card
	 * @throws IllegalArgumentException if the card can't legally be added
	 */
	public void add(final Card card) {
		if (!canAdd(card)) {
			throw new IllegalArgumentException(
					"Cannot add " + card + " to expedition " + this);
		}
		LOGGER.debug("Adding the card " + card);
		this.cards.push(card);
	}

	/**
	 * Indicates whether the given card can be added to this expedition
	 *
	 * @param card the card to be checked; can be <code>null</code> (although it
	 *   won't be addable)
	 * @return see above
	 */
	public boolean canAdd(final Card card) {
		return card != null && suit.equals(card.getSuit())
				&& !this.cards.contains(card) && !card.isLowerValueThan(getTopCard());
	}

	/**
	 * Indicates whether a card with the given face value can be added to this
	 * expedition (assuming it's in the right suit)
	 *
	 * @param value the value to be checked; can't be <code>null</code>
	 * @return see above
	 */
	public boolean canAdd(final FaceValue value) {
		return canAdd(new Card(this.suit, value));
	}

	/**
	 * Returns the point value of this expedition after any investment cards and
	 * "big expedition" bonus have been applied.
	 *
	 * @return a possibly negative number
	 */
	public int getValue() {
		int value = getUnmultipliedValue() * getMultiplier();
		if (this.cards.size() >= BONUS_THRESHOLD) {
			value += BONUS;
		}
		return value;
	}

	/**
	 * Returns the point value of this expedition, before any multiplication due
	 * to investment cards.
	 *
	 * @return a possibly negative number
	 * @deprecated used anywhere?
	 */
	@Deprecated
	public int getUnmultipliedValue() {
		if (this.cards.isEmpty()) {
			return 0;	// Rule: empty expeditions are worth zero points
		}
		int value = BASE_VALUE;

		// Add the face value of each card in the expedition
		for (final Card card : this.cards) {
			if (card.getPointValue() != null) {
				value += card.getPointValue();
			}
		}
		return value;
	}

	/**
	 * Returns the cards that can be played into this expedition, based on what
	 * cards have already been played into the opponent's expedition of the same
	 * suit (passed as a parameter to this method).
	 *
	 * @param otherExpedition the opponent's expedition in this suit; can't be
	 *   <code>null</code>, must be of the same suit
	 * @return a non-<code>null</code> list in ascending order of face value
	 */
	public List<Card> getPlayableCards(final Expedition otherExpedition)	{
		if (otherExpedition == null || !otherExpedition.suit.equals(this.suit)) {
			throw new IllegalArgumentException(
					"Invalid expedition " + otherExpedition);
		}
		final List<Card> playableCards = new ArrayList<Card>();
		// Loop through all possible face values
		for (final FaceValue faceValue : FaceValue.values()) {
			if (canAdd(faceValue)	&& !otherExpedition.contains(faceValue)) {
				// This value can be added and isn't already in the opponent's exped
				playableCards.add(new Card(this.suit, faceValue));
			}
		}
		return playableCards;
	}

	/**
	 * Returns the multiplier that will be applied to this expedition's basic
	 * value by virtue of the investment cards it contains
	 *
	 * @return 1 or more
	 * @deprecated used publically?
	 */
	@Deprecated
	public int getMultiplier() {
		int multiplier = 1;
		for (final Card card : this.cards) {
			if (card.isInvestmentCard()) {
				multiplier += 1;
			}
		}
		return multiplier;
	}

	/**
	 * Returns the maximum possible value of this expedition, given what cards of
	 * this suit are already in the opponent's expedition of the same suit
	 *
	 * @param oppoExped the opponent's expedition; can't be <code>null</code>,
	 *   must be in the same suit
	 * @return see above
	 */
	public int getPotentialValue(final Expedition oppoExped) {
		// Check the input
		if (oppoExped == null || !oppoExped.suit.equals(this.suit)) {
			throw new IllegalArgumentException("Invalid expedition " + oppoExped);
		}

		// Get the card values that are legally addable and not already in the
		// opponent's expedition.
		final List<FaceValue> addableValues = getAddableValues(oppoExped);

		// Add these to a copy of this expedition and see what it's worth
		final Expedition copy = new Expedition(this);

		for (final FaceValue value : addableValues) {
			copy.add(value);
		}

		return copy.getValue();
	}

	/**
	 * Returns the card values that are legally addable to <code>this</code>
	 * expedition and not already in the opponent's expedition
	 *
	 * @param opponentExpedition the opponent's expedition; can't be
	 *   <code>null</code>
	 * @return a non-<code>null</code> list (can be empty)
	 */
	private List<FaceValue> getAddableValues(final Expedition opponentExpedition) {
		// Work out which cards can legally be added to this expedition
		final List<FaceValue> addableValues =
				FaceValue.getValuesPlayableAfter(getTopCard().getFaceValue());

		// Remove the ones already in the opponent's expedition
		// Work backwards so removal doesn't invalidate our index variable
		for (int i = addableValues.size() - 1; i >= 0; i--) {
			if (opponentExpedition.contains(new Card(suit, addableValues.get(i)))) {
				addableValues.remove(i);
			}
		}
		return addableValues;
	}

	/**
	 * Returns the number of cards in this expedition
	 *
	 * @return zero or more
	 */
	public int size() {
		return this.cards.size();
	}

	/**
	 * Returns the additional points that this expedition would be worth if the
	 * given card was added to it.
	 *
	 * @param card the card to be checked; can't be <code>null</code>
	 * @return see above
	 */
	public int getWorth(final Card card) {
		if (card == null) {
			throw new IllegalArgumentException("Card can't be null");
		}
		if (canAdd(card)) {
			return getWorth(card.getFaceValue());
		}
		// Can't be added => not worth anything
		return 0;
	}

	/**
	 * Returns the additional points that this expedition would be worth if a card
	 * with the given face value was added to it.
	 *
	 * @param newValue the value to be checked; can't be <code>null</code>
	 * @return see above
	 */
	public int getWorth(final FaceValue newValue) {
		final Expedition trialExped = new Expedition(this);
		if (trialExped.canAdd(newValue)) {
			trialExped.add(newValue);
			return trialExped.getValue() - this.getValue();
		}
		// Can't be added => not worth anything
		return 0;
	}

	/**
	 * Indicates whether this expedition contains the given card
	 *
	 * @param card can be <code>null</code>
	 * @return <code>false</code> if the given card is <code>null</code> or simply
	 *   not in the expedition
	 */
	public boolean contains(final Card card) {
		if (card == null) {
			return false;
		}
		return this.cards.contains(card);
	}

	/**
	 * Indicates whether this expedition contains the card with the given face
	 * value
	 *
	 * @param value the value to be checked; can't be <code>null</code>
	 * @return see above
	 */
	public boolean contains(final FaceValue value) {
		return contains(new Card(this.suit, value));
	}

	/**
	 * Indicates whether this expedition contains the number (non-investment)
	 * cards between the two given values (non-inclusive).
	 *
	 * @param playedValue the lower value
	 * @param heldValue the higher value
	 * @return see above
	 */
	public boolean containsCardsBetween(
			final FaceValue playedValue, final FaceValue heldValue)
	{
		// For every possible value ...
		for (final FaceValue value : FaceValue.values()) {
			// ... if it's numeric and in the given range ...
			if (!value.isInvestmentCard() && value.compareTo(playedValue) > 0
					&& value.compareTo(heldValue) < 0)
			{
				// ... see if it's in this expedition
				if (!contains(value)) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Indicates whether this expedition is empty
	 *
	 * @return <code>false</code> if there are any cards in it
	 */
	public boolean isEmpty() {
		return this.cards.isEmpty();
	}
}
