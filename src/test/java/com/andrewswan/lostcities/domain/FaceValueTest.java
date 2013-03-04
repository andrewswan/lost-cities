/**
 * Copyright (c) Andrew Swan 2008
 */
package com.andrewswan.lostcities.domain;

import java.util.Arrays;

import com.andrewswan.lostcities.domain.FaceValue;

import junit.framework.TestCase;

/**
 * Unit test of the {@link FaceValue} enumerated type.
 *
 * @author Andrew
 */
public class FaceValueTest extends TestCase {

	public void testGetValuesPlayableAfterNull() {
		assertValuesPlayableAfter(null, FaceValue.values());
	}

	public void testGetValuesPlayableAfterTwo() {
		assertValuesPlayableAfter(FaceValue.TWO,
				FaceValue.THREE, FaceValue.FOUR, FaceValue.FIVE, FaceValue.SIX,
				FaceValue.SEVEN, FaceValue.EIGHT, FaceValue.NINE, FaceValue.TEN);
	}

	public void testGetValuesPlayableAfterInvestmentOne() {
		assertValuesPlayableAfter(FaceValue.INVESTMENT_1,
				FaceValue.INVESTMENT_2, FaceValue.INVESTMENT_3, FaceValue.TWO,
				FaceValue.THREE, FaceValue.FOUR, FaceValue.FIVE, FaceValue.SIX,
				FaceValue.SEVEN, FaceValue.EIGHT, FaceValue.NINE, FaceValue.TEN);
	}

	public void testGetValuesPlayableAfterInvestmentThree() {
		assertValuesPlayableAfter(FaceValue.INVESTMENT_3,
				FaceValue.INVESTMENT_1, FaceValue.INVESTMENT_2, FaceValue.TWO,
				FaceValue.THREE, FaceValue.FOUR, FaceValue.FIVE, FaceValue.SIX,
				FaceValue.SEVEN, FaceValue.EIGHT, FaceValue.NINE, FaceValue.TEN);
	}

	public void testGetValuesPlayableAfterTen() {
		assertValuesPlayableAfter(FaceValue.TEN);
	}

	/**
	 * Asserts that the given array of face values are those playable after the
	 * given base value
	 *
	 * @param baseValue can be <code>null</code>
	 * @param expectedValuesAfter the expected face values; can be none
	 */
	private void assertValuesPlayableAfter(
			final FaceValue baseValue, final FaceValue... expectedValuesAfter)
	{
		assertEquals(Arrays.asList(expectedValuesAfter),
				FaceValue.getValuesPlayableAfter(baseValue));
	}
}
