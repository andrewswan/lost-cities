/**
 * Copyright (c) Andrew Swan 2008
 */
package com.andrewswan.lostcities.domain.move;

import com.andrewswan.lostcities.domain.Game;
import com.andrewswan.lostcities.domain.player.Player;

/**
 * A {@link Move} in which the player draws a card from the deck
 *
 * @author Andrew
 */
public class DrawFromDeck implements CardDraw {

	public void execute(final Game game, final Player player) {
		game.drawFromDeck(player);
	}
}
