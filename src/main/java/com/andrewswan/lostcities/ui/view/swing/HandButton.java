package com.andrewswan.lostcities.ui.view.swing;

import com.andrewswan.lostcities.domain.Card;

/**
 * A button that displays a {@link Card} in a
 * {@link com.andrewswan.lostcities.domain.player.Player}'s
 * {@link com.andrewswan.lostcities.domain.Hand}.
 *
 * @author Andrew
 */
public class HandButton extends CardButton {

	// Constants
	private static final long serialVersionUID = -1453156052056055356L;

	/**
	 * Constructor for a disabled button showing no card
	 */
	public HandButton() {
		// Empty
	}

	/**
	 * Sets this button to show the given card
	 *
	 * @param card the card to show; can't be <code>null</code>
	 * @param index the index of the card in the hand, zero-based
	 */
	public void show(final Card card, final int index) {
		if (card == null) {
			throw new IllegalArgumentException("Card to show can't be null");
		}
		final String cardName = card.getDisplayName();
		set(IconFactory.getCardIcon(card), cardName, cardName);
		// Set the action command to be this card's index within the hand, so that
		// the controller knows which card in the hand was clicked.
		setActionCommand(String.valueOf(index));
	}
}
