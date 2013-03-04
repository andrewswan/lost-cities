// card.java
package com.andrewswan.lostcities.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * One of the 60 cards in the deck
 *
 * @author Andrew
 */
public class Card implements Comparable<Card> {

	// Constants
	protected static final Log LOGGER = LogFactory.getLog(Card.class);

	/**
	 * Returns the display name of a card with the given suit and value
	 *
	 * @param suit can't be <code>null</code>
	 * @param value can't be <code>null</code>
	 * @return a non-blank display name
	 */
	public static String getDisplayName(final Suit suit, final FaceValue value) {
		return suit.getName() + " " + value.getDisplayName();
	}

	// Instance properties
	private final FaceValue value;
	private final Suit suit;

	/**
	 * Constructor
	 *
	 * @param suit the suit to which this card belongs; can't be <code>null</code>
	 * @param value the face value of this card; can't be <code>null</code>
	 */
	public Card(final Suit suit, final FaceValue value) {
		if (suit == null) {
			throw new IllegalArgumentException("Suit can't be null");
		}
		if (value == null) {
			throw new IllegalArgumentException("Value can't be null");
		}
		this.suit = suit;
		this.value = value;
	}

//	/**
//	 * Indicates whether the only valid play of this card is to discard it
//	 *
//	 * @param exped the active player's expedition in this card's suit; can't be
//	 *   <code>null</code>, must be of the same suit
//	 * @return <code>false</code> if the card can be added to the given expedition
//	 */
//	public boolean mustDiscard(Expedition exped) {
//		if (exped == null) {
//			throw new IllegalArgumentException("Expedition can't be null");
//		}
//		if (exped.isEmpty()) {
//			// Any card is playable into an empty expedition
//			return false;
//		}
//		// The expedition has one or more cards in it; check the top value
//		exped.getTopCard();
//
//		// Check what card we are trying to play
//		Integer pointValue = this.value.getPoints();
//
//		if (pointValue < 3) {
//			pointValue = 0; // Make all investment cards equal
//		}
//
//		// Check the highest card played so far to this suit's expedition
//		int topExpedValue = exped.getTopCard();
//
//		// If top card is an Investment, or if exped is empty
//		if (topExpedValue < 3 || exped.isEmpty()) {
//			topExpedValue = 0; // Make all investment cards equal
//		}
//		LOGGER.debug("Top value for the exped is " + topExpedValue);
//
//		// Check if it can go in expedition or must be discarded
//		return pointValue < topExpedValue;
//	}

	/**
	 * Returns the point value of this card
	 *
	 * @return <code>null</code> if it's an investment card
	 */
	public Integer getPointValue() {
		return this.value.getPoints();
	}

	/**
	 * Returns this card's suit
	 *
	 * @return a non-<code>null</code> suit
	 */
	public Suit getSuit() {
		return this.suit;
	}

	/**
	 * Indicates whether this card is an investment card, in other words has no
	 * face value of its own
	 *
	 * @return see above
	 */
	public boolean isInvestmentCard() {
		return value.isInvestmentCard();
	}

	/**
	 * Indicates whether this card is lower than the given card
	 *
	 * @param otherCard the card to which this card is being compared; can be
	 *   <code>null</code>
	 * @return <code>true</code> if it's from a "lower" suit"
	 */
	public boolean isLowerValueThan(final Card otherCard) {
		if (otherCard == null) {
			return false;
		}
		return compareTo(otherCard) < 0;
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof Card)) {
			return false;
		}
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public String toString() {
		// For debugging
		return getDisplayName();
	}

	/**
	 * Returns this card's face value
	 *
	 * @return a non-<code>null</code> value (the point value might be
	 *   <code>null</code> though)
	 */
	public FaceValue getFaceValue() {
		return value;
	}

	public int compareTo(final Card otherCard) {
		if (this.suit.equals(otherCard.suit)) {
			// Same suit, so compare face values
			return this.value.compareTo(otherCard.value);
		}
		// Different suits; compare the suits
		return this.suit.compareTo(otherCard.suit);
	}

	/**
	 * Returns this card's display name
	 *
	 * @return a non-blank name
	 */
	public String getDisplayName() {
		return getDisplayName(this.suit, this.value);
	}
}
