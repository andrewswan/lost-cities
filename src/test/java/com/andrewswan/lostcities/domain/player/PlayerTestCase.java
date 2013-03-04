/**
 * Copyright (c) Andrew Swan 2008
 */
package com.andrewswan.lostcities.domain.player;

import java.util.Map;

import com.andrewswan.lostcities.domain.Expedition;
import com.andrewswan.lostcities.domain.Suit;

import junit.framework.TestCase;

/**
 * Unit test of the {@link AbstractPlayer}. Parameterised with the player type
 * <code>P</code>.
 *
 * @author Andrew
 */
public abstract class PlayerTestCase<P extends AbstractPlayer>
  extends TestCase
{
	// Fixture
	private P player;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.player = getPlayer();
	}

	/**
	 * Subclasses must return an instance of the player under test
	 *
	 * @return a non-<code>null</code> player
	 */
	protected abstract P getPlayer();

	public void testExpeditions() {
		// Set up

		// Invoke
		final Map<Suit, Expedition> expeditions = this.player.getExpeditions();

		// Check
		assertNotNull(expeditions);
	}
}
