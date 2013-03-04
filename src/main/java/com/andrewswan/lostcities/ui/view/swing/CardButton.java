package com.andrewswan.lostcities.ui.view.swing;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.andrewswan.lostcities.domain.Card;

/**
 * A button that displays a {@link Card}
 *
 * @author Andrew
 */
public abstract class CardButton extends JButton {

	// -------------------------------- Constants --------------------------------

	protected static final Log LOGGER = LogFactory.getLog(CardButton.class);

	private static final long serialVersionUID = -8454397493037289040L;

	// Colours
	private static final Color DISABLED_COLOUR = Color.lightGray;
	private static final Color ENABLED_COLOUR = Color.white;

	// Layout
	private static final int HEIGHT_PIXELS = 90;
	private static final int WIDTH_PIXELS = 60;

	/**
	 * Constructor for a disabled button showing no card
	 */
	protected CardButton() {
		setEnabled(false);
		setHorizontalTextPosition(SwingConstants.CENTER);
		setMaximumSize(new Dimension(WIDTH_PIXELS, HEIGHT_PIXELS));
		setSize(WIDTH_PIXELS, HEIGHT_PIXELS);
		setNoCard();
		setVerticalTextPosition(SwingConstants.BOTTOM);
	}

	/**
	 * Sets this button to show no card at all
	 */
	public void setNoCard() {
		set(IconFactory.getNoCardIcon(), null, null);
	}

	/**
	 * Sets the display properties of this button
	 *
	 * @param icon the icon for both the enabled and disabled button states
	 * @param text the button's text
	 * @param toolTipText the button's tool tip (hover) text
	 */
	protected final void set(final Icon icon, final String text, final String toolTipText) {
		setDisabledIcon(icon);
		setIcon(icon);
		setText(text);
		setToolTipText(toolTipText);
	}

	@Override
	public void setEnabled(final boolean enabled) {
		super.setEnabled(enabled);
		if (enabled) {
			setBackground(ENABLED_COLOUR);
		}
		else {
			setBackground(DISABLED_COLOUR);
		}
	}
}
