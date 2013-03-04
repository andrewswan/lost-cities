/**
 * Copyright (c) Andrew Swan 2008
 */
package com.andrewswan.lostcities.domain.player.strategy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.andrewswan.lostcities.domain.Game;
import com.andrewswan.lostcities.domain.move.CardDraw;
import com.andrewswan.lostcities.domain.move.CardPlay;
import com.andrewswan.lostcities.domain.move.Discard;
import com.andrewswan.lostcities.domain.move.DrawFromDeck;
import com.andrewswan.lostcities.domain.player.Player;

/**
 * A conservative but low-odds strategy in which the player simply discards the
 * first card from their hand then draws one from the deck.
 *
 * @author Andrew
 */
public class DrawAndDiscardStrategy implements Strategy {

	// Constants
	protected static final Log LOGGER =
			LogFactory.getLog(DrawAndDiscardStrategy.class);

	public String getName() {
		return "Mr. Draw and Discard";
	}

	public CardPlay getPlayCardMove(final Game game, final Player player) {
		LOGGER.debug("I always discard the lowest card in my hand");
		return new Discard(player.getHand().get(0));
	}

	public CardDraw getDrawCardMove(final Game game, final Player player) {
		LOGGER.debug("I always draw from the deck");
		return new DrawFromDeck();
	}
}
