/**
 * Copyright (c) Andrew Swan 2008
 */
package com.andrewswan.lostcities.domain.player;

import com.andrewswan.lostcities.domain.Game;
import com.andrewswan.lostcities.domain.move.CardDraw;
import com.andrewswan.lostcities.domain.move.CardPlay;
import com.andrewswan.lostcities.domain.player.strategy.Strategy;

/**
 * A computer (AI) player in the game of Lost Cities
 *
 * @author Andrew
 */
public class ComputerPlayer extends AbstractPlayer {

	// Properties
	private final Strategy strategy;

	/**
	 * Constructor
	 *
	 * @param strategy the strategy to be used by this computer player; can't be
	 *   <code>null</code>
	 */
	public ComputerPlayer(final Strategy strategy) {
		super(strategy.getName(), false);
		this.strategy = strategy;
	}

	/**
	 * Returns the {@link Move} in which this player plays a card from their hand
	 * to the table
	 *
	 * @param game the game being played; can't be <code>null</code>
	 * @return a non-<code>null</code> card play
	 */
	public CardPlay getPlayCardMove(final Game game) {
		// Delegate this decision to the strategy, providing it the full context
		return strategy.getPlayCardMove(game, this);
	}

	/**
	 * Returns the {@link Move} in which this player draws a card into their hand
	 * during the game.
	 *
	 * @param game the game being played; can't be <code>null</code>
	 * @return a non-<code>null</code> card draw
	 */
	public CardDraw getDrawCardMove(final Game game) {
		// Delegate this decision to the strategy, providing it the full context
		return strategy.getDrawCardMove(game, this);
	}
}
