package com.andrewswan.lostcities.ui.view.swing.dialog;

/**
 * A dialog that explains the rules of the game.
 *
 * @author Andrew
 */
public class RulesDialog extends InfoDialog {

	// Constants
	private static final int HEIGHT_PIXELS = 400;
	private static final int WIDTH_PIXELS = 800;

	private static final String TITLE = "Rules";

	private static final String RULES =
		"Players aim to score points by successfully completing one to five" +
		" expeditions, represented by five different coloured suits.  Each suit" +
		" has nine numbered cards (valued 2 to 10) and three Investment cards" +
		" (marked with a handshake symbol).\n\nThe worth of an expedition is" +
		" equal to the sum of the numbered cards, minus 20, with the result being" +
		" multiplied if investment cards have been played.  One investment card" +
		" doubles the net value of an expedition, two triples it, and playing all" +
		" three multiplies it by four. Of course, the net value of an expedition" +
		" can be negative, zero, or positive, depending on whether the total face" +
		" values exceed the 20-point minimum.\n\nRestrictions on playing cards," +
		" within any suit:\n\n1. Investment cards can only be played before" +
		" numbered cards.\n\n2. Numbered cards must always be bigger than those" +
		" already played (e.g. once the 4 is played, the 2 and the 3 [and any" +
		" investment cards in that suit] can only be discarded).\n\nA player's" +
		" turn consists of playing a card (either into an expedition or into the" +
		" appropriate discard pile), then drawing a card (either from the deck or" +
		" from one of the discard piles, noting that a player cannot draw a card" +
		" he just discarded).\n\nThe game ends when the last card has been drawn" +
		" from the deck.\n\nA bonus of 20 points is awarded to any expeditions" +
		" that have eight or more cards in them.";

	/**
	 * Constructor
	 */
	public RulesDialog() {
		super(TITLE);
		textPane.setText(RULES);
		setSize(WIDTH_PIXELS, HEIGHT_PIXELS);
		setLocationRelativeTo(null);	// centres this dialog
	}
}
