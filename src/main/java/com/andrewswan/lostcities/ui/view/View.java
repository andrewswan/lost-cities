package com.andrewswan.lostcities.ui.view;

import java.awt.event.ActionListener;
import java.util.Set;

import com.andrewswan.lostcities.domain.Card;
import com.andrewswan.lostcities.domain.Deck;
import com.andrewswan.lostcities.domain.DiscardPile;
import com.andrewswan.lostcities.domain.Game;
import com.andrewswan.lostcities.domain.Suit;
import com.andrewswan.lostcities.domain.player.Player;

/**
 * The "V" in the MVC pattern for the UI. This interface should not be coupled
 * to any specific presentation technology. Most (all?) methods should be void,
 * as the view should be event-driven, rather than returning the result of a
 * view update.
 *
 * @author Andrew
 */
public interface View {

	/**
	 * Registers the given listener with the deck
	 *
	 * @param listener the listener to register; can't be <code>null</code>
	 */
	void addDeckListener(ActionListener listener);

	/**
	 * Registers the given listener for discard pile actions
	 *
	 * @param listener the listener to register; can't be <code>null</code>
	 */
	void addDiscardPilesListener(ActionListener listener);

	/**
	 * Registers the given listener for actions relating to the human player's
	 * hand
	 *
	 * @param listener the listener to register; can't be <code>null</code>
	 */
	void addHandListener(ActionListener listener);
	
	/**
	 * Adds the given listener for "human card play" events
	 *
	 * @param listener the listener to add; can't be <code>null</code>
	 */
	void addHumanCardPlayListener(ActionListener listener);

	/**
	 * Adds the given listener for "new game" events
	 *
	 * @param listener the listener to add; can't be <code>null</code>
	 */
	void addNewGameListener(ActionListener listener);

	/**
	 * Prompts the human {@link Player} to draw a {@link Card}, either from the
	 * {@link Deck} or from a legal {@link DiscardPile}
	 *
	 * @param drawableDiscards the suits (if any) from whose discard piles the
	 *   player is allowed to draw a card; cannot be <code>null</code>
	 * @return a non-<code>null</code> card draw
	 */
	void promptForHumanPlayerCardDraw(Set<Suit> drawableDiscards);

	/**
	 * Updates this view to prompt the human player for how to play the given card
	 */
	void promptForHumanPlayerCardPlay(Card card);

	/**
	 * Sets this view to show the given game state
	 *
	 * @param game the game state to show; can't be <code>null</code>
	 */
	void setGameState(Game game);

	/**
	 * Instructs this view to show or hide itself in its current state
	 */
	void setVisible(boolean visible);
}