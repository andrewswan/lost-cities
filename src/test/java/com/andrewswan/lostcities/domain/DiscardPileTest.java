/**
 * Copyright (c) Andrew Swan 2008
 */
package com.andrewswan.lostcities.domain;

import junit.framework.TestCase;


/**
 * Unit test of the {@link DiscardPile}
 *
 * @author Andrew
 */
public class DiscardPileTest extends TestCase {

	// Constants
	private static final Suit SUIT = Suit.YELLOW;	// arbitrary
	private static final Suit OTHER_SUIT = Suit.BLUE;

	// Fixture
	private DiscardPile discardPile;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.discardPile = new DiscardPile(SUIT);
	}

	public void testInitialState() {
		assertEquals(0, discardPile.size());
		assertTrue(discardPile.isEmpty());
		assertNull(discardPile.inspectTopDiscard());
		assertNull(discardPile.drawTopDiscard());
	}

	public void testDiscardNullCard() {
		assertInvalidDiscard(null);
	}

	public void testDiscardCardTwice() {
		// Set up
		final Card card = new Card(SUIT, FaceValue.TEN);	// arbitrary value
		discardPile.add(card);

		// Invoke
		assertInvalidDiscard(card);	// tries to discard it again
	}

	public void testDiscardCardOfWrongSuit() {
		assertInvalidDiscard(new Card(OTHER_SUIT, FaceValue.TEN));	// arbitrary value
	}

	/**
	 * Asserts that the given card is rejected by the test Suit as a discard.
	 *
	 * @param card the card to discard; can be <code>null</code>
	 */
	private void assertInvalidDiscard(final Card card) {
		try {
			discardPile.add(card);
			fail("Expected a " + IllegalArgumentException.class);
		}
		catch (final IllegalArgumentException expected) {
			// Success
		}
	}

	public void testDrawTopDiscard() {
		// Set up
		final Card discard1 = new Card(SUIT, FaceValue.TWO);
		final Card discard2 = new Card(SUIT, FaceValue.THREE);
		discardPile.add(discard1);
		discardPile.add(discard2);
		assertEquals(2, discardPile.size());
		assertFalse(discardPile.isEmpty());

		// Invoke
		final Card topDiscard = discardPile.drawTopDiscard();

		// Check
		assertSame(discard2, topDiscard);
		assertEquals(1, discardPile.size());	// No longer there, good!
	}

	public void testInspectTopDiscard() {
		// Set up
		final Card discard1 = new Card(SUIT, FaceValue.TWO);
		final Card discard2 = new Card(SUIT, FaceValue.THREE);
		discardPile.add(discard1);
		discardPile.add(discard2);
		assertEquals(2, discardPile.size());

		// Invoke
		final Card topDiscard = discardPile.inspectTopDiscard();

		// Check
		assertSame(discard2, topDiscard);
		assertEquals(2, discardPile.size());	// Still there, good!
	}
}
