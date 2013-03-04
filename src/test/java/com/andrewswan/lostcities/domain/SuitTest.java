/**
 *
 */
package com.andrewswan.lostcities.domain;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Unit test of the {@link Suit} enumeration.
 *
 * @author Andrew
 */
public class SuitTest extends TestCase {

	// Constants
	protected static final Log LOGGER = LogFactory.getLog(SuitTest.class);

	// The number of random suits to generate; more is a better test but is slower
	private static final int RANDOM_SUITS = 10000;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testNamesAreUnique() {
		// Set up
		final Set<String> names = new HashSet<String>();

		// Invoke
		for (final Suit suit : Suit.values()) {
			names.add(suit.getName());
		}

		// Check
		assertEquals(Suit.values().length, names.size());
	}

	public void testGetRandomSuit() {
		// Set up
		final int[] counts = new int[Suit.values().length];

		// Invoke
		for (int i = 0; i < RANDOM_SUITS; i++) {
			final Suit suit = Suit.getRandom();
			counts[suit.ordinal()]++;
		}

		// Debug
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Random suit counts: " + Arrays.toString(counts));
		}
	}

}
