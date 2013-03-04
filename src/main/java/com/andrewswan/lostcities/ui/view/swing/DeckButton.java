package com.andrewswan.lostcities.ui.view.swing;

import com.andrewswan.lostcities.domain.Deck;

/**
 * A button that displays the {@link com.andrewswan.lostcities.domain.Deck}.
 *
 * @author Andrew
 */
public class DeckButton extends CardButton {

	// Constants
	private static final long serialVersionUID = -876529420980141214L;
	
	private static final String CARD_BACK_TEXT = "Draw Pile";

	/**
	 * Constructor
	 */
	public DeckButton() {
		// Empty
	}

	/**
	 * Sets this button to show the given deck state; this method should be called
	 * every time the state of the deck changes.
	 *
	 * @param deck the deck to show; can't be <code>null</code>
	 */
	public void show(final Deck deck) {
		if (deck.isEmpty()) {
			setNoCard();
		}
		else {
			// Show the back of a card
			set(IconFactory.getCardBackIcon(), null, CARD_BACK_TEXT);
		}
		setText(deck.size() + " card(s)");
	}
}
