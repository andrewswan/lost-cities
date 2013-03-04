/**
 * Copyright (c) Andrew Swan 2008
 */
package com.andrewswan.lostcities.domain.player;

/**
 * Unit test of the {@link HumanPlayer} class.
 *
 * @author Andrew
 */
public class HumanPlayerTest extends PlayerTestCase<HumanPlayer> {

	// Constants
	private static final String NAME = "Fred";

	@Override
	protected HumanPlayer getPlayer() {
		return new HumanPlayer(NAME);
	}

	public void testGetName() {
		assertEquals(NAME, getPlayer().getName());
	}

	public void testIsHuman() {
		assertTrue(getPlayer().isHuman());
	}
}
