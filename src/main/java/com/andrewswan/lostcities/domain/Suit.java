package com.andrewswan.lostcities.domain;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;


/**
 * The suits of cards used in a game of Lost Cities
 *
 * @author Andrew
 */
public enum Suit {

	// These are in board order from left to right

	RED ("Red"),

	GREEN ("Green"),

	WHITE ("White"),

	BLUE ("Blue"),

	YELLOW ("Yellow");

	/**
	 * The number of suits in the game
	 */
	public static final int COUNT = values().length;

	/**
	 * Returns a random suit
	 *
	 * @return a non-<code>null</code> suit
	 */
	public static Suit getRandom() {
		// Pick a number from 0 to the maximum Suit index, inclusive
		final int index = RandomUtils.nextInt(values().length);
		return values()[index];
	}

	// Properties
	private final String name;

	/**
	 * Constructor
	 *
	 * @param name the name of this suit; can't be blank
	 */
	private Suit(final String name) {
		if (StringUtils.isBlank(name)) {
			throw new IllegalArgumentException("Invalid name '" + name + "'");
		}
		this.name = name;
	}

	/**
	 * Returns the name of this suit
	 *
	 * @return a non-blank name
	 */
	public String getName() {
		return name;
	}
}
