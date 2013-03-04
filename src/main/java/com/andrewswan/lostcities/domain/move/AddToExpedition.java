/**
 * Copyright (c) Andrew Swan 2008
 */
package com.andrewswan.lostcities.domain.move;

import com.andrewswan.lostcities.domain.Card;
import com.andrewswan.lostcities.domain.Game;
import com.andrewswan.lostcities.domain.Suit;
import com.andrewswan.lostcities.domain.player.Player;

/**
 * A {@link Move} in which a given {@link Card} is being added to the relevant
 * {@link com.andrewswan.lostcities.domain.Expedition} of the {@link Player}
 * who currently holds it.
 *
 * @author Andrew
 */
public class AddToExpedition extends AbstractCardPlay {

	/**
	 * Constructor
	 *
	 * @param card the card being added to the expedition; can't be
	 *   <code>null</code>
	 */
	public AddToExpedition(final Card card) {
		super(card);
	}

	public void execute(final Game game, final Player player) {
		player.putInExpedition(this.card);
	}

	public Suit getDiscardedSuit() {
		return null;
	}
}
