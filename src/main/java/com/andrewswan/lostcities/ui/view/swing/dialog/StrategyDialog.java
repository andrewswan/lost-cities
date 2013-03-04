// StratDialog.java
package com.andrewswan.lostcities.ui.view.swing.dialog;

/**
 * A dialog that provides strategy tips.
 *
 * @author Andrew
 */
public class StrategyDialog extends InfoDialog {

	// Constants
	private static final int HEIGHT_PIXELS = 450;
	private static final int WIDTH_PIXELS = 800;

	private static final long serialVersionUID = -1814919534856564627L;

	private static final String TITLE = "Strategy";

	private static final String STRATEGY =
		"Here are some key strategies for playing Lost Cities:\n\n" +
		"1. Don't start an expedition unless you believe you have a reasonable" +
		" chance of making the 20 point minimum.  For example, look at what cards" +
		" the other player has already played in that colour.\n\n" +
		"2. Don't skip numbers in an expedition if you can avoid it, because that" +
		" reduces your potential total, reduces your chances of scoring the 20" +
		" point bonus (for eight or more cards in an expedition), and gives your" +
		" opponent more safe discards.\n\n" +
		"3. Avoid discarding cards that your opponent definitely wants - better" +
		" to discard something that they might NOT want.\n\n" +
		"4. Keep an eye on how many cards are left in the deck, versus how many" +
		" more cards you want to play from your hand (bearing in mind that some" +
		" of them will be useless).  If you have more cards to play than are left" +
		" in the deck, you may want to start drawing junk from the discard piles" +
		" in order to give yourself enough turns to play the cards left in your" +
		" hand (but see next point).\n\n" +
		"5. As the game draws to a close, both players usually want to play some" +
		" more cards. However, if the other player is more desperate to play" +
		" cards than you (perhaps because they have invested more heavily), it is" +
		" better to draw only from the deck in order to hasten the finish, even" +
		" if it means failing in an expedition or two.  Their score will suffer" +
		" more than yours - remember, your aim is to beat the other player, not" +
		" to score big totals!\n\n" +
		"6. When playing a human, consider bluffing by discarding a card in a" +
		" suit you are collecting but have not yet commenced. The other player" +
		" might be stuck for a convenient discard, and be happy to throw out the" +
		" same suit himself (which you can then pick up).\n\n" +
		"Have fun working out some of your own strategies for Lost Cities!";

	/**
	 * Constructor
	 */
	public StrategyDialog() {
		super(TITLE);
		textPane.setText(STRATEGY);
		setSize(WIDTH_PIXELS, HEIGHT_PIXELS);
		setLocationRelativeTo(null);	// centres this dialog
	}
}
