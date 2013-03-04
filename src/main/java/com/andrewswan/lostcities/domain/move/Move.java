/**
 * Copyright (c) Andrew Swan 2008
 */
package com.andrewswan.lostcities.domain.move;

import com.andrewswan.lostcities.domain.Game;
import com.andrewswan.lostcities.domain.player.AbstractPlayer;
import com.andrewswan.lostcities.domain.player.Player;

/**
 * A move made by a {@link AbstractPlayer} in the game of Lost Cities.
 *
 * An example of the GoF "Command" pattern.
 *
 * @author Andrew
 */
public interface Move {

	/**
	 * Executes this move in the given game for the given player
	 *
	 * @param game the game to which the move applies; can't be <code>null</code>
	 */
	void execute(Game game, Player player);
}
