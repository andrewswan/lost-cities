/**
 * Copyright (c) Andrew Swan 2008
 */
package com.andrewswan.lostcities.domain;

import junit.framework.TestCase;

import com.andrewswan.EasyMockContainer;
import com.andrewswan.lostcities.domain.player.Player;

/**
 * Unit test of a {@link Game}.
 *
 * @author Andrew
 */
public class GameTest extends TestCase {

	// Fixture
	private EasyMockContainer mocks;
	private Player mockPlayerOne;
	private Player mockPlayerTwo;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		mocks = new EasyMockContainer();
		mockPlayerOne = mocks.createStrictMock(Player.class);
		mockPlayerTwo = mocks.createStrictMock(Player.class);
	}

	public void testNewGame() {
		// Set up
		mocks.replay();

		// Invoke
		final Game game = new Game(mockPlayerOne, mockPlayerTwo);

		// Check
		mocks.verify();
		assertEquals(0, game.getRound());
	}
}
