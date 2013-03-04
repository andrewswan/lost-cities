/**
 * Copyright (c) Andrew Swan 2008
 */
package com.andrewswan.lostcities.domain.player;

/**
 * A human playing the game of Lost Cities
 *
 * @author Andrew
 */
public class HumanPlayer extends AbstractPlayer {

	/**
	 * Constructor
	 *
	 * @param name this player's name; can't be blank
	 */
	public HumanPlayer(final String name) {
		super(name, true);
	}
}
