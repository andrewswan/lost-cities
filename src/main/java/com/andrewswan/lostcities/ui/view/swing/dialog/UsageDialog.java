// InstrDialog.java
package com.andrewswan.lostcities.ui.view.swing.dialog;


public class UsageDialog extends InfoDialog {

	// Constants
	private static final int HEIGHT_PIXELS = 520;
	private static final int WIDTH_PIXELS = 800;

	private static final String TITLE = "Usage";

	private static final String INSTRUCTIONS =
		"This window explains how to use this program. Before reading this," +
		" please be sure you understand the rules of the game 'Lost Cities'" +
		" (see Help->Rules...).\n\nThe screen shows four rows of cards (top to" +
		" bottom):\n\n- the opponent's expeditions\n- the five discard piles and" +
		" the deck\n- your expeditions\n- your hand\n\nTo play or draw a card," +
		" simply click on it once with the mouse.  Only those cards which can" +
		" legally be played or drawn will be active at any given time. If you" +
		" have no choice about which card to draw (usually because the discard" +
		" piles are empty), the computer will automatically draw a card for you." +
		"\n\nPlease note that the 'Options' menu allows you to set various" +
		" program options, such as:\n\n- whether the music is on or off\n- the" +
		" 'look and feel' of the graphical interface\n- whether the running" +
		" scores are shown for each player\n- whether the other player's hand is" +
		" visible (debug mode only)\n\nBeneath the card symbols are some numbers," +
		" which have the following meaning:\n\n- expeditions (e.g. '10 (36)':" +
		" shows the current and maximum value of this expedition\n- cards in hand" +
		" (e.g. '2': shows the value of this card for greater legibility\n-" +
		" discard piles (e.g. '1': shows the number of cards in each pile\n\n" +
		"All card symbols have 'tool tips' (text that appears when the mouse" +
		" hovers over a card) that shows the face value of a card.";

	/**
	 * Constructor
	 */
	public UsageDialog() {
		super(TITLE);
		textPane.setText(INSTRUCTIONS);
		setSize(WIDTH_PIXELS, HEIGHT_PIXELS);
		setLocationRelativeTo(null);	// centres this dialog
	}
}
