package com.andrewswan.lostcities.domain;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

/**
 * Unit test of the {@link Deck} class.
 *
 * @author Andrew
 */
public class DeckTest extends TestCase {

	// Fixture
	private Deck deck;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.deck = new Deck();
	}

	public void testNewDeck() {
		// Check the initial state
		assertTrue(deck.isEmpty());
		assertEquals(0, deck.size());
	}
	
	public void testResetDeck() {
		// Invoke
		deck.reset();
		final int sizeAfterReset = deck.size();
		// Draw all the cards and make sure we have each one (relies on Card#equals)
		final Set<Card> cards = new HashSet<Card>();
		while (!deck.isEmpty()) {
			cards.add(deck.draw());
		}
		
		// Check
		assertEquals(Deck.MAX_SIZE, cards.size());
		assertEquals(0, deck.size());
		assertEquals(Deck.MAX_SIZE, sizeAfterReset);
		assertTrue(deck.isEmpty());
	}
}
