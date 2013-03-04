/**
 * Copyright (c) Andrew Swan 2008
 */
package com.andrewswan.lostcities.domain.move;

import com.andrewswan.lostcities.domain.Game;
import com.andrewswan.lostcities.domain.Suit;
import com.andrewswan.lostcities.domain.player.Player;

/**
 * A {@link Move} in which the player draws a card from a discard pile
 *
 * @author Andrew
 */
public class DrawFromDiscardPile implements CardDraw {

	// Properties
	private final Suit suit;

	/**
	 * Constructor for drawing from the given suit's discard pile
	 *
	 * @param suit the suit whose discard pile is being drawn from; can't be
	 *   <code>null</code>
	 */
	public DrawFromDiscardPile(final Suit suit) {
		if (suit == null) {
			throw new IllegalArgumentException("Suit can't be null");
		}
		this.suit = suit;
	}

	public void execute(final Game game, final Player player) {
		game.drawFromDiscardPile(player, this.suit);
	}
}
