/**
 * Copyright (c) Andrew Swan 2008
 */
package com.andrewswan.lostcities.domain;

import junit.framework.TestCase;


/**
 * Unit test of the {@link Expedition} class.
 *
 * @author Andrew
 */
public class ExpeditionTest extends TestCase {

	// Constants
	private static final Suit SUIT = Suit.GREEN;	// arbitrary

	// Fixture
	private Expedition expedition;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		expedition = new Expedition(SUIT);
	}

	public void testEmptyExpedition() {
		assertEquals(0, expedition.getValue());
		assertEquals(0, expedition.size());
		assertTrue(expedition.isEmpty());
		assertNull(expedition.getTopCard());
		for (final FaceValue value : FaceValue.values()) {
			assertTrue(expedition.canAdd(value));
		}
	}

	public void testGetPotentialValueWithTenAdded() {
		// Set up
		expedition.add(FaceValue.TEN);
		final int startValue = expedition.getValue();

		// Invoke
		final int potentialValue = expedition.getPotentialValue(new Expedition(SUIT));

		// Check
		assertEquals(startValue, potentialValue);
	}
}
