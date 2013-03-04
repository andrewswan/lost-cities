package com.andrewswan.lostcities.domain;

import junit.framework.TestCase;

/**
 * Unit test of the {@link Card} domain class.
 *
 * @author Andrew
 */
public class CardTest extends TestCase {

	public void testSuitCannotBeNull() {
		try {
			new Card(null, FaceValue.EIGHT);	// arbitrary value
			fail("Expected a " + IllegalArgumentException.class);
		}
		catch (final IllegalArgumentException expected) {
			// Success
		}
	}

	public void testFaceValueCannotBeNull() {
		try {
			new Card(Suit.BLUE, null);	// arbitrary suit
			fail("Expected a " + IllegalArgumentException.class);
		}
		catch (final IllegalArgumentException expected) {
			// Success
		}
	}

	public void testTwoIsLowerThanThreeInSameSuit() {
		// Set up
		final Card two = new Card(Suit.BLUE, FaceValue.TWO);
		final Card three = new Card(two.getSuit(), FaceValue.THREE);

		// Invoke and check
		assertTrue(two.isLowerValueThan(three));
	}

	public void testThreeIsLowerThanTwoInHigherSuit() {
		// Set up
		final Suit[] suits = Suit.values();

		final Card lowerSuitCard = new Card(suits[0], FaceValue.TEN);
		final Card higherSuitCard = new Card(suits[1], FaceValue.TWO);

		// Invoke and check
		assertTrue(lowerSuitCard.isLowerValueThan(higherSuitCard));
	}

	public void testCardIsNotLowerThanItself() {
		// Set up
		final Card card = new Card(Suit.BLUE, FaceValue.TWO);	// arbitrary

		// Invoke and check
		assertFalse(card.isLowerValueThan(card));
	}

	public void testCardIsNotLowerThanNull() {
		// Set up
		final Card card = new Card(Suit.BLUE, FaceValue.TWO);	// arbitrary

		// Invoke and check
		assertFalse(card.isLowerValueThan(null));
	}
}
