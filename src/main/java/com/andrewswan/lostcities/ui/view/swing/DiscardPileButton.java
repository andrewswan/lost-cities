package com.andrewswan.lostcities.ui.view.swing;

import org.springframework.util.Assert;

import com.andrewswan.lostcities.domain.Card;
import com.andrewswan.lostcities.domain.Suit;

/**
 * A button that displays the
 * {@link com.andrewswan.lostcities.domain.DiscardPile} for a given
 * {@link com.andrewswan.lostcities.domain.Suit}.
 *
 * @author Andrew
 */
public class DiscardPileButton extends CardButton {

	// Properties
	private final Suit suit;

	/**
	 * Constructor for a button showing an empty discard pile
	 * 
	 * @param suit the suit for which this is the discard pile; can't be
	 *   <code>null</code>
	 */
	public DiscardPileButton(final Suit suit) {
		Assert.notNull(suit, "Suit can't be null");
		this.suit = suit;
	}

	/**
	 * Sets this button to show the given card
	 *
	 * @param card the card to show; can't be <code>null</code>
	 */
	public void setCard(final Card card) {
		Assert.notNull(card, "Card can't be null");
		final String cardName = card.getDisplayName();
		set(IconFactory.getCardIcon(card), cardName, cardName);
		// Set the action command to be this pile's suit, so that the controller
		// knows which discard pile button was clicked.
		setActionCommand(suit.name());
	}
}
