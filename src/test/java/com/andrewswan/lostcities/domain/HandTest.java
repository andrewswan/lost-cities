/**
 * Copyright (c) Andrew Swan 2008
 */
package com.andrewswan.lostcities.domain;

import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

/**
 * Unit test of the {@link Hand} class.
 *
 * @author Andrew
 */
public class HandTest extends TestCase {

	// Fixture
	private Hand hand;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		hand = new Hand();
	}

	public void testNewHand() {
		assertEquals(0, hand.size());
		for (int i = 0; i < Hand.LIMIT; i++) {
			assertNull(hand.getCard(i));
		}
		assertTrue(hand.getCards().isEmpty());
		assertTrue(hand.isEmpty());
		assertFalse(hand.isFull());
	}

	public void testAddCard() {
		// Set up
		final Card card = new Card(Suit.BLUE, FaceValue.FIVE);	// arbitrary

		// Invoke
		hand.add(card);

		// Check
		assertFalse(hand.getCards().isEmpty());
		assertFalse(hand.isEmpty());
		assertFalse(hand.isFull());
		assertEquals(1, hand.size());
		assertEquals(Arrays.asList(card), hand.getCards());
		assertEquals(card, hand.getCard(0));
	}

	public void testReset() {
		// Set up seven arbitrary cards
		hand.add(new Card(Suit.BLUE, FaceValue.TWO));
		hand.add(new Card(Suit.RED, FaceValue.FOUR));
		hand.add(new Card(Suit.WHITE, FaceValue.TWO));
		hand.add(new Card(Suit.YELLOW, FaceValue.FIVE));
		hand.add(new Card(Suit.BLUE, FaceValue.INVESTMENT_1));
		hand.add(new Card(Suit.GREEN, FaceValue.INVESTMENT_1));
		hand.add(new Card(Suit.GREEN, FaceValue.INVESTMENT_2));
		final List<Card> newHand = Arrays.asList(
				new Card(Suit.BLUE, FaceValue.SIX),
				new Card(Suit.GREEN, FaceValue.SEVEN),
				new Card(Suit.RED, FaceValue.EIGHT),
				new Card(Suit.WHITE, FaceValue.NINE),
				new Card(Suit.YELLOW, FaceValue.TEN),
				new Card(Suit.BLUE, FaceValue.FIVE),
				new Card(Suit.GREEN, FaceValue.SIX),
				new Card(Suit.RED, FaceValue.SEVEN)
		);

		// Invoke
		hand.reset(newHand);

		// Check
		assertEquals(newHand.size(), hand.size());
	}
}
