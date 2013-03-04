package com.andrewswan.lostcities.ui.view.swing;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.andrewswan.lostcities.domain.Card;
import com.andrewswan.lostcities.domain.Expedition;
import com.andrewswan.lostcities.domain.FaceValue;
import com.andrewswan.lostcities.domain.Suit;

/**
 * A GUI component that shows that state of an {@link Expedition}
 *
 * @author Andrew Swan
 */
public class ExpedLabel extends JLabel {

	// Constants
	private static final int HEIGHT_PIXELS = 100;
	private static final int WIDTH_PIXELS = 80;

	/**
	 * Constructor
	 */
	public ExpedLabel() {
		setBackground(SwingView.FELT);
		setHorizontalTextPosition(SwingConstants.CENTER);
		setSize(WIDTH_PIXELS, HEIGHT_PIXELS);
		setVerticalTextPosition(SwingConstants.BOTTOM);
		reset();
	}

	/**
	 * Set this label to show the card with the given suit and value
	 *
	 * @param suit can't be <code>null</code>
	 * @param value can't be <code>null</code>
	 */
	public void setPicture(final Suit suit, final FaceValue value) {
		setIcon(IconFactory.getCardIcon(suit, value));
	}

	/**
	 * Set this label to show the given card
	 *
	 * @param card the card to show; can't be <code>null</code>
	 */
	public void setPicture(final Card card) {
		setIcon(IconFactory.getCardIcon(card));
	}

	/**
	 * Sets this label to show no card (i.e. empty pile)
	 */
	public void setNoCard() {
		setIcon(IconFactory.getNoCardIcon());
	}

	/**
	 * Resets this label to its state at the start of a new game
	 */
	public void reset() {
		setNoCard();
		setScore(0);
		setToolTipText("No cards");
	}

	/**
	 * Instructs this label to show the top card (if any) of the given expedition
	 *
	 * @param expedition the expedition whose top card to show; can be empty but
	 *   can't be <code>null</code>
	 */
	public void showTopCard(final Expedition expedition) {
		if (expedition.isEmpty()) {
			reset();
		}
		else {
			setPicture(expedition.getTopCard());
		}
	}

	/**
	 * Sets the score to be shown by this label
	 * 
	 * @param score the score to show; can be zero, negative, or positive
	 */
	public void setScore(final int score) {
		setText(String.valueOf(score));	// TODO show max possible score
	}
}
