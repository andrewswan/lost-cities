/**
 * Copyright (c) Andrew Swan 2008
 */
package com.andrewswan.lostcities.domain.move;

import com.andrewswan.lostcities.domain.Suit;

/**
 * A {@link Move} in which a player plays a
 * {@link com.andrewswan.lostcities.domain.Card} into the relevant expedition or
 * discard pile.
 *
 * @author Andrew
 */
public interface CardPlay extends Move {

	/**
	 * Returns the suit that was discarded as a result of this card play
	 *
	 * @return <code>null</code> if it wasn't a discard
	 */
	Suit getDiscardedSuit();
}
