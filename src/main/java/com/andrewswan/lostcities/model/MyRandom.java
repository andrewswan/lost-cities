// MyRandom.java
// Generates random numbers to specification
package com.andrewswan.lostcities.model;

import java.util.Random;

import org.apache.commons.lang.math.RandomUtils;

/**
 * A convenient random number generator.
 *
 * @author Andrew
 * @deprecated encapsulate within relevant enums
 */
@Deprecated
public class MyRandom {

	/**
	 * Generates a random integer in the given range
	 *
	 * @param start the minimum value, inclusive
	 * @param finish the maximum value, inclusive
	 * @param r the random number generator to invoke
	 * @return see above
	 */
	public static int randInt(final int start, final int finish, final Random r) {
		return RandomUtils.nextInt(r, finish - start + 1) + start;
	}
}
