/**
 * Copyright (c) Andrew Swan 2008
 */
package com.andrewswan.lostcities.ui.view.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import org.apache.commons.lang.mutable.MutableBoolean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

import com.andrewswan.lostcities.domain.Card;
import com.andrewswan.lostcities.domain.DiscardPile;
import com.andrewswan.lostcities.domain.Expedition;
import com.andrewswan.lostcities.domain.Game;
import com.andrewswan.lostcities.domain.Hand;
import com.andrewswan.lostcities.domain.Phase;
import com.andrewswan.lostcities.domain.Suit;
import com.andrewswan.lostcities.domain.player.Player;
import com.andrewswan.lostcities.ui.view.View;
import com.andrewswan.lostcities.ui.view.swing.dialog.CardPlayDialog;

/**
 * The "V" in the MVC pattern for our Swing GUI. Responsible for updating all
 * other view elements (buttons, labels, etc.)
 *
 * @author Andrew
 */
public class SwingView extends JFrame implements View {

	/**
	 * Required for Serializability.
	 */
	private static final long serialVersionUID = 820472441217100761L;

	private static final String START_MESSAGE =
		"Welcome to LostCities V" + Game.VERSION +
		" - use the menu to start a new game";

	// -------------------------------- Constants --------------------------------

	// A logger
	protected static final Log LOGGER = LogFactory.getLog(LostCitiesPanel.class);

	// Colours
	public static final Color FELT = new Color(0, 138, 0);

	// Layout
	private static final int HAND_BUTTON_GAP = 5;
	private static final int MAIN_BORDER_WIDTH = 5;
	private static final int PLAYED_CARD_X_GAP = 5;
	private static final int PLAYED_CARD_Y_GAP = 5;

	// Text
	private static final String DISCARD_PILES_LABEL = "Discard Piles";
	private static final String FRAME_TITLE = "Lost Cities";
	private static final String PLAYER_ONE_EXPEDITIONS_LABEL = "Player One";
	private static final String PLAYER_TOTAL_LABEL = "Score: %d";
	private static final String PLAYER_TWO_EXPEDITIONS_LABEL = "Player Two";
	private static final String PLAYER_TWO_HAND_LABEL = "Player 2";

	// -------------------------------- Properties -------------------------------

	// These are the components that send events and receive updates
	private final CardPlayDialog cardDialog;
	private final DeckButton deckButton;
	private final HandButton[] playerTwoHandButtons;
	private final JLabel playerOneTotalLabel;
	private final JLabel playerTwoTotalLabel;
	private final JLabel statusBar;
	private final JPopupMenu popupExpedMenu;
	private final Map<Suit, DiscardPileButton> discardPileButtons;
	private final MenuBar menuBar;
	private final MutableBoolean showRunningScores;
	final Map<Suit, ExpedLabel> playerOneExpeditionLabels;
	final Map<Suit, ExpedLabel> playerTwoExpeditionLabels;

	/**
	 * Constructor
	 */
	public SwingView() {
		super(FRAME_TITLE);
		// Content
		this.cardDialog = new CardPlayDialog(this);
		this.deckButton = new DeckButton();
		this.discardPileButtons = new HashMap<Suit, DiscardPileButton>();
		this.playerOneExpeditionLabels = new HashMap<Suit, ExpedLabel>();
		this.playerOneTotalLabel = new JLabel();
		this.playerTwoExpeditionLabels = new HashMap<Suit, ExpedLabel>();
		this.playerTwoTotalLabel = new JLabel();
		this.playerTwoHandButtons = new HandButton[Hand.LIMIT];
		this.popupExpedMenu = new JPopupMenu();
		this.popupExpedMenu.setEnabled(false);
		this.showRunningScores = new MutableBoolean(true);
		this.statusBar = new JLabel();
		// Menu Bar
		menuBar = new MenuBar(this.showRunningScores);
		menuBar.addShowRunningScoresListener(new ShowRunningScoresListener());
		setJMenuBar(menuBar);
		// Only adding one component to this frame allows it to have a border
		add(getFramePanel());
		setResizable(false);
		pack();
		setLocationRelativeTo(null);
	}

	/**
	 * Returns this frame's main panel
	 *  
	 * @return a non-<code>null</code> component
	 */
	private Component getFramePanel() {
		final JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(getFeltPanel(), BorderLayout.CENTER);
		mainPanel.add(this.statusBar, BorderLayout.SOUTH);
		this.statusBar.setText(START_MESSAGE);
		return mainPanel;
	}

	/**
	 * Returns the panel that simulates the felt-covered game table.
	 *
	 * @return a non-<code>null</code> component
	 */
	private Component getFeltPanel() {
		final JPanel framePanel = new JPanel(new BorderLayout());
		framePanel.setBackground(FELT);
		framePanel.setBorder(BorderFactory.createEmptyBorder(MAIN_BORDER_WIDTH,
				MAIN_BORDER_WIDTH, MAIN_BORDER_WIDTH, MAIN_BORDER_WIDTH));
		framePanel.add(getMainPanel(), BorderLayout.CENTER);
		framePanel.add(getTableLabelPanel(), BorderLayout.WEST);
		framePanel.add(getPlayerTwoHandPanel(), BorderLayout.SOUTH);
		return framePanel;
	}

	/**
	 * Returns the component that shows the main play area, containing both
	 * players' expeditions, the discard piles, and the draw pile
	 *
	 * @return a non-<code>null</code> component
	 */
	private Component getMainPanel() {
		final JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(FELT);
		panel.add(getPlayedCardPanel(), BorderLayout.WEST);
		panel.add(getTotalsAndDeckPanel(), BorderLayout.EAST);
		return panel;
	}

	/**
	 * Returns the component that shows the played cards, namely both players'
	 * expeditions and the discard piles
	 *
	 * @return a non-<code>null</code> component
	 */
	private Component getPlayedCardPanel() {
		// Create a grid panel with a column for each suit
		final JPanel panel = new JPanel(
				new GridLayout(3, Suit.COUNT, PLAYED_CARD_X_GAP, PLAYED_CARD_Y_GAP));
		panel.setBackground(FELT);

		// Add player one's expedition buttons to row 1
		for (final Suit suit : Suit.values()) {
			final ExpedLabel expeditionLabel = new ExpedLabel();
			expeditionLabel.addMouseListener(new PopupMenuMouseListener());
			this.playerOneExpeditionLabels.put(suit, expeditionLabel);
			panel.add(expeditionLabel);
		}

		// Add the discard pile buttons to row 2
		for (final Suit suit : Suit.values()) {
			final DiscardPileButton discardPileButton = new DiscardPileButton(suit);
			this.discardPileButtons.put(suit, discardPileButton);
			panel.add(discardPileButton);
		}

		// Add player two's expedition buttons to row 3
		for (final Suit suit : Suit.values()) {
			final ExpedLabel expeditionLabel = new ExpedLabel();
			expeditionLabel.addMouseListener(new PopupMenuMouseListener());
			this.playerTwoExpeditionLabels.put(suit, expeditionLabel);
			panel.add(expeditionLabel);
		}

		return panel;
	}

	/**
	 * Returns the component that shows the total scores for each player as well
	 * as the draw pile.
	 *
	 * @return a non-<code>null</code> component
	 */
	private Component getTotalsAndDeckPanel() {
		final JPanel panel = new JPanel(new GridLayout(3, 1));
		panel.setBackground(FELT);
		panel.add(this.playerOneTotalLabel);
		panel.add(this.deckButton);
		panel.add(this.playerTwoTotalLabel);
		return panel;
	}

	/**
	 * Returns the component that labels the play areas on the "table"
	 *
	 * @return a non-<code>null</code> component
	 */
	private Component getTableLabelPanel() {
		final JPanel panel = new JPanel(new GridLayout(3, 1));
		panel.add(new JLabel(PLAYER_ONE_EXPEDITIONS_LABEL));
		panel.add(new JLabel(DISCARD_PILES_LABEL));
		panel.add(new JLabel(PLAYER_TWO_EXPEDITIONS_LABEL));
		panel.setBackground(FELT);
		return panel;
	}

	/**
	 * Returns the component that displays player two's hand
	 *
	 * @return a non-<code>null</code> component
	 */
	private Component getPlayerTwoHandPanel() {
		final JPanel panel = new JPanel();
		panel.add(new JLabel(PLAYER_TWO_HAND_LABEL));
		panel.setBackground(FELT);
		final JPanel handButtonPanel =
				new JPanel(new GridLayout(1, Hand.LIMIT, HAND_BUTTON_GAP, 0));
		handButtonPanel.setBackground(FELT);
		for (int i = 0; i < Hand.LIMIT; i++) {
			final HandButton handButton = new HandButton();
			playerTwoHandButtons[i] = handButton;
			handButtonPanel.add(handButton);
		}
		panel.add(handButtonPanel);
		return panel;
	}

	// --------------------- Listener Registration Methods -----------------------

	public void addDeckListener(final ActionListener listener) {
		Assert.notNull(listener, "Listener can't be null");
		this.deckButton.addActionListener(listener);	// ignores nulls!
	}

	public void addDiscardPilesListener(final ActionListener listener) {
		Assert.notNull(listener, "Listener can't be null");
		for (final DiscardPileButton discardPileButton :
				this.discardPileButtons.values())
		{
			discardPileButton.addActionListener(listener);	// ignores nulls!
		}
	}

	public void addHandListener(final ActionListener listener) {
		Assert.notNull(listener, "Listener can't be null");
		for (final HandButton handButton : this.playerTwoHandButtons) {
			handButton.addActionListener(listener);	// ignores nulls!
		}
	}
	
	public void addHumanCardPlayListener(ActionListener listener) {
		Assert.notNull(listener, "Listener can't be null");
		// The card play dialog is the only component that generates these events
		this.cardDialog.addActionListener(listener);
	}

	public void addNewGameListener(final ActionListener listener) {
		Assert.notNull(listener, "Listener can't be null");
		// The menu bar is the only component that generates these events
		this.menuBar.addNewGameListener(listener);
	}

	// -------------------------- View Update Methods ----------------------------

	public void promptForHumanPlayerCardPlay(final Card card) {
		this.cardDialog.askAbout(card);
	}

	/**
	 * Updates the components that show the cards in player two's hand
	 *
	 * @param cards the cards to show; can't be <code>null</code>
	 */
	private void setPlayerTwoHand(final List<Card> cards) {
		LOGGER.debug("Setting player two's hand");

		// Set the pictures for the given cards, starting from the left
		for (int i = 0; i < cards.size(); i++) {
			this.playerTwoHandButtons[i].show(cards.get(i), i);
		}

		// Set the remaining pictures to no card
		for (int i = cards.size(); i < this.playerTwoHandButtons.length; i++) {
			this.playerTwoHandButtons[i].setNoCard();
		}

		validate();
	}

	/**
	 * Returns the text to show on a player's total score label
	 *
	 * @param total the player's total score
	 * @return some non-<code>null</code> display text
	 */
	private String getPlayerTotalText(final int total) {
		return String.format(PLAYER_TOTAL_LABEL, total);
	}

	/**
	 * Updates the components that show the state of the given discard pile
	 *
	 * @param discardPile the discard pile to show; can't be <code>null</code>
	 */
	private void setTopDiscard(final DiscardPile discardPile) {
		if (discardPile == null) {
			throw new IllegalArgumentException("Discard pile cannot be null");
		}
		final DiscardPileButton discardButton =
				this.discardPileButtons.get(discardPile.getSuit());
		if (discardPile.isEmpty()) {
			// Display no card
			discardButton.setNoCard();
			discardButton.setText("(no cards)");
		}
		else {
			// Display the top card
			final Card topCard = discardPile.inspectTopDiscard();
			discardButton.setCard(topCard);
			discardButton.setText(topCard.getDisplayName());
		}
	}

	// ------------------------------ Inner Classes ------------------------------

	/**
	 * A mouse listener that responds to mouse events by showing a popup menu.
	 *
	 * Has default visibility to improve performance, as recommended by the
	 * compiler in Eclipse.
	 *
	 * @author Andrew
	 */
	class PopupMenuMouseListener extends MouseAdapter {
		@Override
  	public void mouseClicked(final MouseEvent event) {
			ExpedLabel expedLabel =
					checkExpeditionLabels(event, playerOneExpeditionLabels.values());
			if (expedLabel == null) {
				// Wasn't one of player one's expeditions; must be player two's
				expedLabel =
						checkExpeditionLabels(event, playerTwoExpeditionLabels.values());
			}
			final CardButton sourceButton = (CardButton) event.getComponent();
			LOGGER.debug("Source button is " + sourceButton);
			// TODO the logic below probably belongs in the controller
//			int expedSize = exped[player][suit.ordinal()].size();
//			LOGGER.debug("This exped has " + expedSize + " cards.");
//			if (expedSize > 0) {
//				LOGGER.debug("Clicked player " + player + "'s exped no. " + suit);
//				// Add the played cards to the popup menu as menu items
//				popupExpedMenu.removeAll();
//				for (Card card : exped[player][suit.ordinal()].getCards()) {
//					Integer cardVal = card.getPointValue();
//					popupExpedMenu.add(new JMenuItem(Card.getText(cardVal)));
//				}
//				int baseValue = exped[player][suit.ordinal()].getUnmultipliedValue();
//
//				if (baseValue < 0) {
//					JMenuItem shortBy = new JMenuItem("(Short by " + -baseValue + ")");
//					shortBy.setForeground(Color.red);
//					popupExpedMenu.add(shortBy);
//				}
//				sourceButton.add(popupExpedMenu);
//				popupExpedMenu.show(sourceButton, event.getX(), event.getY());
//			}
		}

		/**
		 * Returns the expedition label that was the source of the given event
		 *
		 * @param event the event that occurred; can't be <code>null</code>
		 * @param playerExpeditionLabels the labels to check; can't be
		 *   <code>null</code>
		 * @return <code>null</code> if none of those labels were the event source
		 */
		private ExpedLabel checkExpeditionLabels(
				final MouseEvent event, final Collection<ExpedLabel> playerExpeditionLabels)
		{
			for (final ExpedLabel expeditionLabel : playerExpeditionLabels) {
				if (event.getSource() == expeditionLabel) {
					return expeditionLabel;
				}
			}
			return null;
		}
	}

	/**
	 * Listens for the menu bar indicating that the user wishes to show or hide
	 * the running scores (i.e. toggle this setting).
	 *
	 * Has default visibility to improve performance, as recommended by the
	 * compiler in Eclipse.
	 *
	 * @author Andrew
	 */
	static class ShowRunningScoresListener implements ActionListener {

		public void actionPerformed(final ActionEvent e) {
			LOGGER.debug("Detected a 'Show/Hide Runnings Scores' event");
			// TODO Auto-generated method stub
		}
	}

	public void setGameState(final Game game) {
		// Update player one's display details
		final Player playerOne = game.getPlayerOne();
		setExpeditionLabels(
				this.playerOneExpeditionLabels, playerOne.getExpeditions());
		this.playerOneTotalLabel.setText(getPlayerTotalText(playerOne.getPoints()));

		// Update the discard piles
		for (final Suit suit : Suit.values()) {
			setTopDiscard(game.getDiscardPile(suit));
		}

		// Update the deck
		this.deckButton.show(game.getDeck());

		// Update player two's display details
		final Player playerTwo = game.getPlayerTwo();
		setExpeditionLabels(
				this.playerTwoExpeditionLabels, playerTwo.getExpeditions());
		this.playerTwoTotalLabel.setText(getPlayerTotalText(playerTwo.getPoints()));
		setPlayerTwoHand(playerTwo.getHand());

		// Enable/disable the various screen controls based on the player and phase
		final boolean activePlayerHuman = game.isActivePlayerHuman();
		final boolean enableDrawButtons =
				activePlayerHuman && Phase.DRAW.equals(game.getPhase());
		final boolean enablePlayButtons =
				activePlayerHuman && Phase.PLAY.equals(game.getPhase());

		// -- Deck
		this.deckButton.setEnabled(enableDrawButtons);

		// -- Discard Pile buttons
		for (final Entry<Suit, DiscardPileButton> entry :
				this.discardPileButtons.entrySet())
		{
			final boolean empty = game.getDiscardPile(entry.getKey()).isEmpty();
			entry.getValue().setEnabled(enableDrawButtons && !empty);
		}

		// -- Player two hand buttons
		for (final HandButton handButton : this.playerTwoHandButtons) {
			handButton.setEnabled(enablePlayButtons);
		}
		
		// -- Status bar
		if (enableDrawButtons) {
			this.statusBar.setText("Click on the card you want to pick up");
		}
		else if (enablePlayButtons) {
			this.statusBar.setText(
				"Click on the card you want to play from your hand");
		}
		else {
			this.statusBar.setText("Computer thinking...");
		}

		pack();
	}

	/**
	 * Sets the given labels to show the top cards of the given expeditions
	 *
	 * @param labels the labels to be updated; can't be <code>null</code>
	 * @param expeditions the expeditions whose top cards are to be shown; can't
	 *   be <code>null</code>
	 */
	private void setExpeditionLabels(
			final Map<Suit, ExpedLabel> labels,	final Map<Suit, Expedition> expeditions)
	{
		Assert.notNull(expeditions, "Expeditions can't be null");
		Assert.notNull(labels, "Labels can't be null");
		for (final Suit suit : Suit.values()) {
			final Expedition expedition = expeditions.get(suit);
			final ExpedLabel label = labels.get(suit);
			label.showTopCard(expedition);
			label.setScore(expedition.getValue());
		}
	}

	public void promptForHumanPlayerCardDraw(final Set<Suit> drawableDiscards) {
		// Enable or disable each discard pile button as appropriate
		for (final Suit suit : Suit.values()) {
			discardPileButtons.get(suit).setEnabled(drawableDiscards.contains(suit));
		}

		// Enable the deck button
		deckButton.setEnabled(true);
	}
}
