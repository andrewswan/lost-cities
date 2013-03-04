/**
 * Copyright (c) Andrew Swan 2008
 */
package com.andrewswan.lostcities.domain.player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.andrewswan.lostcities.domain.Card;
import com.andrewswan.lostcities.domain.Expedition;
import com.andrewswan.lostcities.domain.Hand;
import com.andrewswan.lostcities.domain.Suit;

/**
 * One of two players in the game of Lost Cities
 *
 * @author Andrew
 */
public abstract class AbstractPlayer implements Player {

	// Constants
	protected static final Log LOGGER = LogFactory.getLog(Player.class);

	// Properties
	private final boolean human;
	private final Hand hand;
	private final Map<Suit, Expedition> expeditions;
	private final String name;

	/**
	 * Constructor for a player with no cards in their hand
	 *
	 * @param name this player's name; can't be blank
	 * @param human whether this player is a human being; <code>false</code> if
	 *   they are an AI player
	 */
	protected AbstractPlayer(final String name, final boolean human) {
		if (StringUtils.isBlank(name)) {
			throw new IllegalArgumentException("Invalid name '" + name  + "'");
		}
		this.expeditions = new HashMap<Suit, Expedition>();
		this.hand = new Hand();
		this.human = human;
		this.name = name;
	}

	public void reset(final List<Card> startingHand) {
		// Overwrite this player's existing expeditions in each suit
		for (final Suit suit : Suit.values()) {
			this.expeditions.put(suit, new Expedition(suit));
		}
		// Reset their hand to contain the given cards
		this.hand.reset(startingHand);
	}

	public List<Card> getHand() {
		return hand.getCards();
	}

	public String getName() {
		return this.name;
	}

	public boolean isHuman() {
		return this.human;
	}

	public void putInExpedition(final Card card) {
		if (card == null) {
			throw new IllegalArgumentException("Card can't be null");
		}
		// Remove the card from this player's hand
		this.hand.remove(card);
		// Find the relevant expedition and add the card to it
		this.expeditions.get(card.getSuit()).add(card);
	}

	/**
	 * Puts the given card into this player's hand
	 *
	 * @param card the card to be put into this player's hand; can't be
	 *   <code>null</code>
	 */
	public void putInHand(final Card card) {
		this.hand.add(card);
	}

	public boolean canAddToExpedition(final int handIndex) {
		final Card card = this.hand.getCard(handIndex);
		final Expedition expedition = this.expeditions.get(card.getSuit());
		return expedition.canAdd(card);
	}

	public void discard(final Card card) {
		this.hand.remove(card);
	}

	public Expedition getExpedition(final Suit suit) {
		return new Expedition(this.expeditions.get(suit));	// defensive copy
	}

	public Map<Suit, Expedition> getExpeditions() {
		final Map<Suit, Expedition> expeditionCopies = new HashMap<Suit, Expedition>();
		for (final Suit suit : this.expeditions.keySet()) {
			expeditionCopies.put(suit, getExpedition(suit));
		}
		return expeditionCopies;
	}

	public List<Card> getMustPlayCards() {
		final List<Card> playableCards = new ArrayList<Card>();
		for (final Card card : this.hand.getCards()) {
			final Expedition expedition = this.expeditions.get(card.getSuit());
			if (!expedition.isEmpty() && expedition.canAdd(card)
					&& !card.isInvestmentCard())
			{
				playableCards.add(card);
			}
		}
		return playableCards;
	}

	public int getPoints() {
		int points = 0;
		for (final Expedition expedition : this.expeditions.values()) {
			points += expedition.getValue();
		}
		return points;
	}

	public int getWorth(final Card card) {
		return this.expeditions.get(card.getSuit()).getWorth(card);
	}

	public Set<Card> getNoBrainerExpeditionPlays(
			final Map<Suit, Expedition> otherPlayersExpeditions)
	{
		final Set<Card> noBrainers = new HashSet<Card>();
		for (final Card card : hand.getCards()) {
			// Investment cards incur risk; so we ignore them here
			if (!card.isInvestmentCard()) {
				final Suit suit = card.getSuit();
				final Expedition ourExpedition = this.expeditions.get(suit);
				final Expedition otherExpedition = otherPlayersExpeditions.get(suit);
				final List<Card> playableCards =
						ourExpedition.getPlayableCards(otherExpedition);
				if (!playableCards.isEmpty() && card.equals(playableCards.get(0))) {
						// This is the lowest numbered card not yet played in this suit =>
						// it's a no brainer to add to our expedition.
						LOGGER.debug("The " + card + " is a no-brainer.");
						noBrainers.add(card);
				}
			}
		}
		return noBrainers;
	}

	/**
	 * Sorts the given cards by decreasing expedition value to this player.
	 *
	 * Has package access to allow unit testing.
	 *
	 * @param player the player for whom this AI is making the decisions
	 * @param cards the cards to sort; can't be <code>null</code>
	 */
	public void sortByExpeditionValue(final List<Card> cards) {
		LOGGER.debug("Unsorted cards: " + cards);
		Collections.sort(cards, new Comparator<Card>() {
			public int compare(final Card card1, final Card card2) {
				// Get the additional point values of both cards
				final int pointValue1 = getWorth(card1);
				final int pointValue2 = getWorth(card2);
				return pointValue1 - pointValue2;	// means decreasing order
			}
		});
		LOGGER.debug("Sorted cards: " + cards);
	}
}
