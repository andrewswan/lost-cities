/**
 * Copyright (c) Andrew Swan 2008
 */
package com.andrewswan.lostcities.ui.view.swing;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.andrewswan.lostcities.domain.Card;
import com.andrewswan.lostcities.domain.FaceValue;
import com.andrewswan.lostcities.domain.Suit;

/**
 * Loads the {@link Icon}s required by the other GUI classes
 *
 * @author Andrew
 */
public class IconFactory {

	private static final String CARD_BACK_FILE_NAME = "back.jpg";

	private static final String BLANK_CARD_FILE_NAME = "blank_card.gif";

	// Constants
	protected static final Log LOGGER = LogFactory.getLog(IconFactory.class);

	private static final Map<Suit, Map<FaceValue, Icon>> CARD_IMAGES;

	private static final String IMAGE_PATH = "images/";

	// Class properties
	private static Icon cardBack;
	private static Icon noCard;

	static {
		// Create the nested map of icons by suit and face value
		CARD_IMAGES = new HashMap<Suit, Map<FaceValue, Icon>>();
		for (final Suit suit : Suit.values()) {
			CARD_IMAGES.put(suit, new HashMap<FaceValue, Icon>());
		}
	}

	/**
	 * Constructor is private to prevent instantiation
	 */
	private IconFactory() {
		// Empty
	}

	/**
	 * Returns the image to show for a card back
	 *
	 * @return a non-<code>null</code> image
	 */
	public static Icon getCardBackIcon() {
		// Lazy-load
		if (cardBack == null) {
			cardBack = getIcon(CARD_BACK_FILE_NAME);
		}
		return cardBack;
	}

	/**
	 * Returns the image to show for no card at all
	 *
	 * @return a non-<code>null</code> image
	 */
	public static Icon getNoCardIcon() {
		// Lazy-load
		if (noCard == null) {
			noCard = getIcon(BLANK_CARD_FILE_NAME);
		}
		return noCard;
	}

	/**
	 * Returns the icon with the given filename
	 *
	 * @param filename must be a valid system resource
	 * @return
	 */
	private static Icon getIcon(final String filename) {
		final URL url = ClassLoader.getSystemResource(IMAGE_PATH + filename);
		if (url == null) {
			throw new IllegalArgumentException(
					"Invalid file name '" + filename + "'");
		}
		return new ImageIcon(url);
	}

	/**
	 * Returns the icon to show for the given card
	 *
	 * @param card the card being shown; can't be <code>null</code>
	 * @return a non-<code>null</code> image
	 */
	public static Icon getCardIcon(final Card card) {
		return getCardIcon(card.getSuit(), card.getFaceValue());
	}

	/**
	 * Returns the icon to show for a card of the given suit and value
	 *
	 * @param suit the suit being shown; can't be <code>null</code>
	 * @param faceValue the value being shown; can't be <code>null</code>
	 * @return a non-<code>null</code> image
	 */
	public static Icon getCardIcon(final Suit suit, final FaceValue faceValue) {
		Icon icon = CARD_IMAGES.get(suit).get(faceValue);
		if (icon == null) {
			// This icon isn't loaded yet; do it now
			final String fileName = getImageFileName(suit, faceValue);
			icon = getIcon(fileName);
			CARD_IMAGES.get(suit).put(faceValue, icon);
		}
		return icon;
	}

	/**
	 * Returns the file name for the image of the card with the given suit and
	 * value
	 *
	 * @param suit the suit being shown; can't be <code>null</code>
	 * @param faceValue the value being shown; can't be <code>null</code>
	 * @return a non-blank filename
	 */
	private static String getImageFileName(final Suit suit, final FaceValue faceValue) {
		Integer points = faceValue.getPoints();
		if (points == null) {
			points = 0;	// Investment card
		}
		return suit.getName().toLowerCase() + points + ".jpg";
	}
}
