/**
 * Copyright (c) Andrew Swan 2008
 */
package com.andrewswan.lostcities.domain.player;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.andrewswan.lostcities.domain.Card;
import com.andrewswan.lostcities.domain.Expedition;
import com.andrewswan.lostcities.domain.Suit;

/**
 * One of the two players in a game of Lost Cities
 *
 * @author Andrew
 */
public interface Player {

	/**
	 * A player's maximum possible score
	 */
	int MAX_SCORE = Suit.COUNT * Expedition.MAX_VALUE;

	/**
	 * Indicates whether this player can add the card at the given hand index to
	 * the relevant expedition.
	 *
	 * @param handIndex the zero-based index of the card being checked
	 * @return see above
	 */
	boolean canAddToExpedition(int handIndex);

	/**
	 * Removes the given card from this player's hand
	 *
	 * @param card the card to remove
	 * @throws IllegalArgumentException if the player didn't have that card
	 */
	void discard(Card card);

	/**
	 * Returns the cards in this player's hand
	 *
	 * @return a non-<code>null</code> copy of this list
	 */
	List<Card> getHand();

	/**
	 * Returns this player's name
	 *
	 * @return a non-blank name
	 */
	String getName();

	/**
	 * Returns this player's expedition in the given suit
	 *
	 * @param suit can't be <code>null</code>
	 * @return a non-<code>null</code> copy of this expedition
	 */
	Expedition getExpedition(Suit suit);

	/**
	 * Returns the expeditions belonging to this player
	 *
	 * @return a non-<code>null</code> map containing copies of this player's
	 *   expeditions, one for each suit in the game
	 */
	Map<Suit, Expedition> getExpeditions();

	/**
	 * Returns the cards in this player's hand for which there is no reason not to
	 * play them (at some point) into the relevant expedition. This excludes
	 * investment cards and those for which no expedition has started, as playing
	 * these cards can incur some risk.
	 *
	 * This method mainly exists for the benefit of the AI, but could also be used
	 * to give hints to a human player.
	 *
	 * @return a non-<code>null</code> list
	 */
	List<Card> getMustPlayCards();

	/**
	 * Returns any of this player's cards that are no-brainers for being added to
	 * their expedition, in other words cards that incur no risk and for which
	 * there are no intervening cards left to play (because they have all been
	 * played either by this player or the other player).
	 *
	 * @param otherPlayersExpeditions the other player's expeditions in each suit;
	 *   this map must be non-<code>null</code> and contain one {@link Expedition}
	 *   for each suit in the game
	 * @return a non-<code>null</code> set of any such cards
	 */
	Set<Card> getNoBrainerExpeditionPlays(
			Map<Suit, Expedition> otherPlayersExpeditions);

	/**
	 * Returns the number of points currently being scored by this player
	 *
	 * @return see above; can be positive, negative, or zero
	 */
	int getPoints();

	/**
	 * Returns the number of points this player would gain if the given card was
	 * added to this player's expedition in that suit
	 *
	 * @param card the card whose worth is being checked; can't be
	 *   <code>null</code>
	 * @return see above
	 */
	int getWorth(Card card);

	/**
	 * Indicates whether this player is a human being (required for the UI)
	 *
	 * @return <code>false</code> if they are an AI player
	 */
	boolean isHuman();

	/**
	 * Puts the given card from this player's hand into this player's expedition
	 * for that suit.
	 *
	 * @param card the card to add; can't be <code>null</code>, must currently be
	 *   in the player's hand
	 */
	void putInExpedition(Card card);

	/**
	 * Puts the given card into this player's hand.
	 *
	 * @param card the card to add; can't be <code>null</code>
	 */
	void putInHand(Card card);

	/**
	 * Resets this player ready to play a new game
	 *
	 * @param startingHand the player's starting hand; can't be <code>null</code>
	 */
	void reset(List<Card> startingHand);

	/**
	 * Sorts the given cards by decreasing expedition value to this player.
	 *
	 * @param cards the cards to sort; can't be <code>null</code>
	 */
	void sortByExpeditionValue(List<Card> cards);
}
