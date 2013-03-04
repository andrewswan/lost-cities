/**
 * Copyright (c) Andrew Swan 2008
 */
package com.andrewswan.lostcities.domain;

/**
 * The phases of a player's turn
 *
 * @author Andrew
 */
public enum Phase {

	/**
	 * The phase in which a player plays a card into either an {@link Expedition}
	 * or a {@link DiscardPile}.
	 */
	PLAY,

	/**
	 * The phase in which a player draws a card from either the {@link Deck} or a
	 * {@link DiscardPile}.
	 */
	DRAW;
	
	/**
	 * Returns the opposite phase to this one
	 * 
	 * @return a non-<code>null</code> phase
	 */
	public Phase opposite() {
		switch (this) {
			case DRAW:
				return PLAY;
			case PLAY:
				return DRAW;
			default:
				throw new UnsupportedOperationException("Unsupported phase " + this);
		}
	}
}
