/**
 * Copyright (c) Andrew Swan 2008
 */
package com.andrewswan.lostcities.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.math.RandomUtils;

/**
 * The face values of the cards in a game of Lost Cities
 *
 * @author Andrew
 */
public enum FaceValue {

	INVESTMENT_1 (null),

	INVESTMENT_2 (null),

	INVESTMENT_3 (null),

	TWO (2),

	THREE (3),

	FOUR (4),

	FIVE (5),

	SIX (6),

	SEVEN (7),

	EIGHT (8),

	NINE (9),

	TEN (10);

	/**
	 * The number of cards in each suit.
	 */
	public static final int COUNT = values().length;

	/**
	 * Returns a random face value
	 *
	 * @param random the random number generator to use; can't be
	 *   <code>null</code>
	 * @return a non-<code>null</code> value
	 */
	public static FaceValue getRandom(final Random random) {
		// Pick a random number from 0 to the maximum FaceValue index, inclusive
		final int index = RandomUtils.nextInt(random, values().length);
		return values()[index];
	}

	/**
	 * Returns the face values that are playable into an expedition after a card
	 * with the given face value
	 *
	 * @param value can be <code>null</code> for none
	 * @return a non-<code>null</code> list in the order those values could be
	 * 	played
	 */
	public static List<FaceValue> getValuesPlayableAfter(final FaceValue baseValue) {
		if (baseValue == null) {
			// All face values are playable
			return Arrays.asList(values());
		}
		// A face value was given; start making a list of those not lower
		final List<FaceValue> playableValues = new ArrayList<FaceValue>();
		for (final FaceValue value : values()) {
			if (baseValue.isInvestmentCard()) {
				// Any other face value apart from this one can be played after it
				if (!value.equals(baseValue)) {
					playableValues.add(value);
				}
			}
			else {
				// The base value is a numbered card; only higher numbers can be played
				if (value.ordinal() > baseValue.ordinal()) {
					playableValues.add(value);
				}
			}
		}
		return playableValues;
	}

	// Properties
	private final Integer points;	// can be null

	/**
	 * Constructor
	 *
	 * @param points the point value of a card with this face value; can be
	 *   <code>null</code> if it has no intrinsic point value, i.e. an investment
	 *   card
	 */
	private FaceValue(final Integer points) {
		this.points = points;
	}

	/**
	 * Returns the display name of this value
	 *
	 * @return a non-blank name
	 */
	public String getDisplayName() {
		if (points == null) {
			return "X";	// was "Investment" but that made the hand buttons too wide
		}
		return points.toString();
	}

	/**
	 * Returns the point value of a card with this value
	 *
	 * @return <code>null</code> if it has no point value, i.e. is an investment
	 *   card
	 */
	public Integer getPoints() {
		return points;
	}

	/**
	 * Indicates whether this face value belongs to one of the three investment
	 * cards in each suit
	 *
	 * @return <code>false</code> if it's a numbered card
	 */
	public boolean isInvestmentCard() {
		return points == null;
	}
}
