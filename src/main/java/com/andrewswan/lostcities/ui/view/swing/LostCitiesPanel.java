// LostCities.java
// Java 2 implementation of the "Lost Cities" card game
// By Andrew Swan, April 2001
// Instantiated by an instance of LostCitiesLauncher.class (a JApplet)
package com.andrewswan.lostcities.ui.view.swing;

//import java.awt.BorderLayout;
//import java.awt.Color;
//import java.awt.FlowLayout;
//import java.awt.GridLayout;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//import java.awt.event.WindowAdapter;
//import java.awt.event.WindowEvent;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.swing.JFrame;
//import javax.swing.JLabel;
//import javax.swing.JMenuItem;
//import javax.swing.JPanel;
//import javax.swing.JPopupMenu;
//import javax.swing.SwingConstants;
//import javax.swing.SwingUtilities;
//import javax.swing.UIManager;
//import javax.swing.border.TitledBorder;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//
//import com.andrewswan.lostcities.domain.Card;
//import com.andrewswan.lostcities.domain.Expedition;
//import com.andrewswan.lostcities.domain.FaceValue;
//import com.andrewswan.lostcities.domain.Game;
//import com.andrewswan.lostcities.domain.Hand;
//import com.andrewswan.lostcities.domain.Suit;
//import com.andrewswan.lostcities.domain.player.Player;
//import com.andrewswan.lostcities.model.ScoreTableModel;
//import com.andrewswan.lostcities.ui.swing.view.dialog.AboutDialog;
//import com.andrewswan.lostcities.ui.swing.view.dialog.CardDialog;
//import com.andrewswan.lostcities.ui.swing.view.dialog.EndDialog;
//import com.andrewswan.lostcities.ui.swing.view.dialog.HandDialog;
//import com.andrewswan.lostcities.ui.swing.view.dialog.InstrDialog;
//import com.andrewswan.lostcities.ui.swing.view.dialog.RulesDialog;
//import com.andrewswan.lostcities.ui.swing.view.dialog.ScoresDialog;
//import com.andrewswan.lostcities.ui.swing.view.dialog.StratDialog;

public class LostCitiesPanel /* extends JPanel implements ActionListener */ {

//	// ------------------------ Constants (GUI-related) --------------------------
//
//	// Dimensions
//	public static final int TASKBAR_WIDTH = 2 * 25;
//
//	private static final int HEIGHT_PIXELS = 571;
//	private static final int WIDTH_PIXELS = 775;
//
//	// Positions
//	private static final int FRAME_X = 0;
//	private static final int FRAME_Y = 0;
//
//	// Text
//	private static final String FRAME_TITLE = "Lost Cities";
//
//	// --------------------- Final properties (GUI-related) ----------------------
//
//	// Borders
//	private final TitledBorder titledBorder1 = new TitledBorder("");
//
//	// Buttons
//	private final LostCitiesButton drawButton = new LostCitiesButton();
//	private final LostCitiesButton[] handButtons;	// for the human player
//	private final Map<Suit, LostCitiesButton> discardButtons;
//
//	// Containers
//	final JFrame frame = new JFrame(FRAME_TITLE);
//	private final JPanel handBtnPanel = new JPanel();
//	private final JPanel handPanel = new JPanel();
//	private final JPanel labelPanel = new JPanel();
//	private final JPanel mainPanel = new JPanel();
//	private final JPanel pnlBtnsAndLbls = new JPanel();
//	private final JPanel pnlFiller = new JPanel();
//	private final JPanel pnlRight = new JPanel();
//
//	// Labels
//	private final ExpedLabel playerOneExpedLabels[] = new ExpedLabel[Suit.COUNT];
//	private final ExpedLabel playerTwoExpedLabels[] = new ExpedLabel[Suit.COUNT];
//	private final JLabel discardPilesLabel = new JLabel();
//	private final JLabel enemyExpedsLabel = new JLabel();
//	private final JLabel handLabel = new JLabel();
//	private final JLabel ownExpedsLabel = new JLabel();
//
//	// Layouts
//	private final BorderLayout borderLayout1 = new BorderLayout();
//	private final FlowLayout flowLayout4 = new FlowLayout();
//	private final GridLayout gridLayout1 = new GridLayout();
//	private final GridLayout gridLayout2 = new GridLayout();
//	private final GridLayout gridLayout3 = new GridLayout();
//	private final GridLayout gridLayout4 = new GridLayout();
//
//	// Menu
//	private final JPopupMenu popupMenuExped = new JPopupMenu();
//	private final LostCitiesMenu mainMenu;
//
//	// ------------------- Non-final properties (GUI-related) --------------------
//
//	// Dialogs
//	private HandDialog otherHandDialog;
//
//	// Labels
//	private JLabel playerOneTotalLabel = new JLabel();
//	private JLabel playerTwoTotalLabel = new JLabel();
//
//	// State
//	private boolean showOtherHand;
//	private boolean showRunningScores;
//
//	// ---------------------- Properties (domain-related) ------------------------
//
//	private Game game;
//
//	/**
//	 * Constructor
//	 *
//	 * @param version
//	 * @param codeBase
//	 * @param music
//	 */
//	public LostCitiesPanel(Game game, URL codeBase, AudioClip music)	{
//		// Use a LinkedHashMap to preserve the display order of these buttons
//		this.discardButtons = new LinkedHashMap<Suit, LostCitiesButton>();
//		this.game = game;
//		this.handButtons = new LostCitiesButton[Hand.LIMIT];
//		// Start the music
//		this.music = music;
//		this.showRunningScores = true;
//		music.loop();
//
//		// Initialise game properties
//		// -- Tell the file-handling classes where they are running from
//		LostCitiesButton.setCodeBase(codeBase);
//		// Preload the button images to improve game speed
//		LostCitiesButton.preloadImages(this);
//		ScoreTableModel.setCodeBase(codeBase);
//
//
//		// Initialise the GUI components
//		mainMenu = new LostCitiesMenu(this);
//		try {
//			initialiseGuiWidgets();
//		}
//		catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//	}
//
//	private void initialiseGuiWidgets() throws Exception {
//		// Main layout of this panel
//		setLayout(new BorderLayout());
//		frame.getContentPane().add(this); // Add this panel to the frame
//		frame.getContentPane().setBackground(FELT);
//
//		// Label panel
//		labelPanel.setBackground(FELT);
//		labelPanel.setLayout(gridLayout1);
//		gridLayout1.setRows(3);
//		gridLayout1.setColumns(1);
//		add(labelPanel, BorderLayout.WEST);
//
//		// Label panel -> Components
//		enemyExpedsLabel.setText("Enemy Expeds:");
//		labelPanel.add(enemyExpedsLabel, null);
//		ownExpedsLabel.setText("Your Expeds:");
//		labelPanel.add(ownExpedsLabel, null);
//		discardPilesLabel.setText("Discard Piles:");
//		labelPanel.add(discardPilesLabel, null);
//
//		// Main panel
//		mainPanel.setBackground(FELT);
//		mainPanel.setLayout(borderLayout1);
//		add(mainPanel, BorderLayout.CENTER);
//
//		// Hand panel
//		handPanel.setBackground(FELT);
//		handPanel.setBorder(titledBorder1);
//		handPanel.setLayout(flowLayout4);
//		add(handPanel, BorderLayout.SOUTH);
//
//		// Hand panel -> Components
//		handLabel.setHorizontalAlignment(SwingConstants.LEFT);
//		handLabel.setText("Hand:");
//		handPanel.add(handLabel, null);
//
//		// Hand panel -> Hand button panel (for human player)
//		handBtnPanel.setLayout(gridLayout2);
//		gridLayout2.setColumns(Hand.LIMIT);
//		gridLayout2.setHgap(2);
//		handPanel.add(handBtnPanel, null);
//
//		// Main Panel -> Right panel
//		pnlRight.setBackground(FELT);
//		pnlRight.setLayout(gridLayout4);
//		gridLayout4.setRows(3);
//		mainPanel.add(pnlRight, BorderLayout.EAST);
//
//		// Main Panel -> Buttons and labels panel
//		pnlBtnsAndLbls.setBackground(FELT);
//		pnlBtnsAndLbls.setLayout(gridLayout3);
//		gridLayout3.setColumns(5);
//		gridLayout3.setRows(3);
//		mainPanel.add(pnlBtnsAndLbls, BorderLayout.WEST);
//
//		// Main Panel -> Filler panel
//		pnlFiller.setBackground(FELT);
//		mainPanel.add(pnlFiller, BorderLayout.CENTER);
//
//		// -- ?
//
//		popupMenuExped.setEnabled(false);
//
//		// Old stuff
//
//		// Start Row 1 (opponent's expeditions)
//		for (int i = 0; i < Suit.COUNT; i++) {
//			ExpedLabel expedLabel = new ExpedLabel();
//			expedLabel.addMouseListener(
//					new MouseAdapter() {
//						@Override
//          	public void mouseClicked(MouseEvent e) {
//          	 showPopupMenu(e);
//          	}
//          }
//			);
//			pnlBtnsAndLbls.add(expedLabel);
//			playerOneExpedLabels[i] = expedLabel;
//		}
//		playerOneTotalLabel =
//				new JLabel("Total: 0 (max. " + Suit.COUNT * Expedition.MAX_VALUE + ")");
//		playerOneTotalLabel.setForeground(Color.black);
//		pnlRight.add(playerOneTotalLabel);
//
//		// Start Row 2 (discard piles & draw pile)
//		// Initialise and add the discard pile label and buttons
//		for (Suit suit : Suit.values()) {
//			LostCitiesButton discardButton = new LostCitiesButton();
//			discardButton.addActionListener(this);
//			discardButton.setText("0 cards");
//			discardButton.setToolTipText("No cards");
//			this.discardButtons.put(suit, discardButton);
//			this.pnlBtnsAndLbls.add(discardButton);
//		}
//
//		// Set up and add the draw pile button
//		drawButton.addActionListener(this);
//		drawButton.setText("Draw Pile: " + game.getDeckSize() + " left");
//		pnlRight.add(drawButton);
//
//		// Start Row 3 (own expeditions)
//		for (Suit suit : Suit.values()) {
//			ExpedLabel expedLabel = new ExpedLabel();
//			expedLabel.addMouseListener(
//					new MouseAdapter() {
//						@Override
//						public void mouseClicked(MouseEvent e) {
//							showPopupMenu(e);
//						}
//					}
//			);
//			pnlBtnsAndLbls.add(expedLabel);
//			playerTwoExpedLabels[suit.ordinal()] = expedLabel;
//		}
//
//		playerTwoTotalLabel =
//				new JLabel("Total: 0 (max. " + Suit.COUNT	* Expedition.MAX_VALUE + ")");
//		playerTwoTotalLabel.setForeground(Color.black);
//		pnlRight.add(playerTwoTotalLabel);
//
//		// Start Row 4 (hand buttons)
//		// Initialise and add the hand buttons
//		List<Card> humanPlayersCards = game.getPlayerTwo().getHand();
//		for (int i = 0; i < handButtons.length; i++) {
//			LostCitiesButton handButton =
//					new LostCitiesButton(humanPlayersCards.get(i));
//			handButton.addActionListener(this);
//			handButton.setEnabled(true);
//			handButtons[i] = handButton;
//			handBtnPanel.add(handButton);
//		}
//
//		// Fire up the frame
//		frame.setLocation(FRAME_X, FRAME_Y);
//		// Set up the menu for the parent frame
//		mainMenu.addActionListener(this);
//		// Set up event handling for the frame
//		frame.addWindowListener(
//				new WindowAdapter() {
//					@Override
//					public void windowClosing(WindowEvent e) {
//						// music.stop(); // fix this
//						frame.dispose(); // free up system resources
//					}
//				}
//		);
//		MyWindowAdapter myWindowAdapter = new MyWindowAdapter(); // inner class
//		frame.addWindowListener(myWindowAdapter);
//		frame.setJMenuBar(mainMenu);
//		frame.pack();
//		frame.setSize(WIDTH_PIXELS, HEIGHT_PIXELS);
//		frame.setResizable(false);
//		frame.setVisible(true);
//	}
//
//	// ***************** Methods for player turn ******************
//
//	private void playerDeckDraw() {
//		// Player 2 (human) draws a card from the deck
//		deckDraw(PLAYER2);
//		drawButton.setEnabled(false);
//		for (Suit suit : Suit.values()) {
//			discardButtons.get(suit).setEnabled(false);
//		}
//		refreshHandPics(PLAYER2);
//		if (deck.getSize() > 0 && computerTurn()) {
//			enableHand(true);
//		}
//		else {
//			gameOver();
//		}
//	}
//
//	/**
//	 *
//	 * @param discardedSuit can be <code>null</code>
//	 * @param cardNum
//	 */
//	private void finishHumanMove(Suit discardedSuit, int cardNum) {
//		// Deal the player a card if forced to draw, otherwise give them the
//		// choice
//		boolean mustDrawNew = true;
//
//		for (Suit suit : Suit.values()) {
//			if (suit.hasDiscards() && !suit.equals(discardedSuit)) {
//				discardButtons.get(suit).setEnabled(true);
//				mustDrawNew = false;
//			}
//		}
//		if (mustDrawNew) {
//			// no legal discard piles to draw from, so auto deck draw
//			playerDeckDraw();
//		}
//		else {
//			drawButton.setEnabled(true);
//			handButtons[cardNum].setPic();
//			enableHand(false);
//		}
//	}
//
//	/**
//	 * Updates the images showing the human player's current hand; only bothers
//	 * doing this if the human is the active player (the AI doesn't look at the
//	 * images).
//	 *
//	 * @param player the zero-indexed number of the active player
//	 */
//	private void refreshHandPics(Player player) {
//		if (player == PLAYER2) {
//			for (int cardNum = 0; cardNum < handButtons.length; cardNum++) {
//				Card thisCard = playerHand.getCard(cardNum);
//				handButtons[cardNum].setPic(thisCard);
//			}
//		}
//	}
//
//	/**
//	 * Enables or disables all the player's hand buttons
//	 *
//	 * @param enabled the enabled state to set
//	 */
//	private void enableHand(boolean enabled) {
//		for (LostCitiesButton handButton : handButtons) {
//			handButton.setEnabled(enabled);
//		}
//	}
//
//	/**
//	 * Plays the card at the given index of the given hand into the relevant
//	 * expedition
//	 *
//	 * @param hand the hand from which to play a card
//	 * @param cardNum the index of the card to play
//	 */
//	private void play(Hand hand, int cardNum) {
//		Card thisCard = hand.getCard(cardNum);
//		int otherPlayer = 1 - player;
//		int suitIndex = thisCard.getSuit().ordinal();
//		Expedition ownExped = exped[player][suitIndex];
//		Expedition otherExped = exped[otherPlayer][suitIndex];
//
//		hand.remove(thisCard);
//		ownExped.add(thisCard);
//
//		// Refresh totals on exped buttons for both players
//		expedLabel[player][suitIndex].setPic(thisCard);
//		refreshGameTotals();
//		if (showRunningScores) {
//			String valueStr = ownExped.getValue() + " (max. ";
//
//			valueStr += ownExped.getPotentialValue(otherExped);
//			// expedButton[player][suitIndex].setText(valueStr + ")");
//			expedLabel[player][suitIndex].setText(valueStr + ")");
//			valueStr = otherExped.getValue() + " ("
//					+ otherExped.getPotentialValue(ownExped);
//			expedLabel[otherPlayer][suitIndex].setText(valueStr + ")");
//		}
//		else {
//			expedLabel[player][suitIndex].setText(null);
//		}
//	}
//
//	private void discard(int player, int cardNum) {
//		// Remove the card from the hand and place it into the discard pile
//		Hand hand = hands[player];
//		Card thisCard = hand.getCard(cardNum);
//		hand.remove(thisCard);
//		final Suit suit = thisCard.getSuit();
//		thisCard.discard();
//		LostCitiesButton discardButton = discardButtons.get(suit);
//		discardButton.setPic(thisCard);
//		discardButton.setText(suit.getNumberOfDiscards() + " card(s)");
//	}
//
//	private void deckDraw(int player) {
//		// Give this player the top card from the deck
//		Card newCard = deck.drawCard();
//		drawButton.setText(deck.getSize() + " left");
//		hands[player].addCard(newCard);
//	}
//
//	private void drawDiscard(int player, Suit suit) {
//		// Give this player the top card from the indicated discard pile
//		final LostCitiesButton discardButton = discardButtons.get(suit);
//		Card newCard = suit.drawTopDiscard();
//		hands[player].addCard(newCard);
//		if (suit.hasDiscards()) {
//			discardButton.setPic(suit, suit.inspectTopDiscard().getValue());
//		}
//		else {
//			discardButton.setPic();
//		}
//		discardButton.setText(suit.getNumberOfDiscards() + " card(s)");
//	}
//
//	private void refreshGameTotals() {
//		// Refresh both players' game totals (current and maximum)
//		int totalOne = 0;
//		int totalTwo = 0;
//		int maxTotalOne = 0;
//		int maxTotalTwo = 0;
//
//		for (Suit suit : Suit.values()) {
//			final int suitIndex = suit.ordinal();
//			totalOne += exped[PLAYER1][suitIndex].getValue();
//			totalTwo += exped[PLAYER2][suitIndex].getValue();
//			maxTotalOne +=
//					exped[PLAYER1][suitIndex].getPotentialValue(exped[PLAYER2][suitIndex]);
//			maxTotalTwo +=
//					exped[PLAYER2][suitIndex].getPotentialValue(exped[PLAYER1][suitIndex]);
//		}
//		playerOneTotalLabel.setText(
//				"Total: " + totalOne + " (max. " + maxTotalOne + ")");
//		totalLabel[PLAYER2].setText(
//				"Total: " + totalTwo + " (max. " + maxTotalTwo + ")");
//	}
//
//	private void gameOver() {
//		// Show the winner and game stats
//		int oneScore = getScore(PLAYER1);
//		int twoScore = getScore(PLAYER2);
//
//		EndDialog endDialog;
//		if (oneScore > twoScore) {
//			endDialog = new EndDialog(frame, PLAYER1, oneScore, twoScore);
//		}
//		else if (twoScore > oneScore) {
//			endDialog = new EndDialog(frame, PLAYER2, twoScore, oneScore);
//		}
//		else {
//			// Scores are equal
//			endDialog = new EndDialog(frame, oneScore);
//		}
//		endDialog.setVisible(true);
//		// tableModel.addScore(twoScore, playerName, "Today", AI_VERSION);
//		if (endDialog.getChoice() == "Exit") {
//			endDialog.dispose();
//			frame.dispose();
//			music.stop();
//			return;
//		}
//		endDialog.dispose();
//		LOGGER.debug("User chose to play again.");
//		gameNew();
//	}
//
//	private int getScore(int player) {
//		// Returns the total score for the indicated player
//		int total = 0;
//
//		for (Suit suit : Suit.values()) {
//			total += exped[player][suit.ordinal()].getValue();
//		}
//		return total;
//	}
//
//	// **************** Methods for computer's turn ***************
//
//	private boolean computerTurn() {
//		// The computer has a turn - returns true if draw pile still has cards left
//		LOGGER.debug("<< Beginning the computer's turn. >>");
//
//		// Play or discard a card
//		Suit suitDiscarded = computerPlay();
//
//		// Pick up from the draw pile or a discard pile
//		return computerDraw(suitDiscarded);
//	}
//
//
//
//	// ****************** Menu Handling Methods ********************
//
//	public void setMusic(boolean state) {
//		// Turn the background music on or off
//		if (state) {
//			LOGGER.debug("Turning music on.");
//			music.loop();
//		}
//		else {
//			LOGGER.debug("Turning music off.");
//			music.stop();
//		}
//	}
//
//	public void gameNew() {
//		// Respond to "Game->New" menu item:
//
//		// 1. Reset the deck data (need to do this BEFORE dealing new hands)
//		deck = new LostCitiesDeck(this);
//
//		// 2. Redeal the players' hands and reset their expeditions
//		for (int player = PLAYER1; player < PLAYERS; player++) {
//			// Reset the hand
//			hands[player] = new Hand(this, player);
//			refreshHandPics(player);
//
//			// Reset the expeditions
//			for (Suit suit : Suit.values()) {
//				exped[player][suit.ordinal()] = new Expedition(suit);
//				// Reset expedButtons
//				expedLabel[player][suit.ordinal()] = new ExpedLabel();
//			}
//
//			// Reset the total score
//			totalLabel[player].setText(
//					"Total: 0 (max. " + Suit.COUNT * Expedition.MAX_VALUE + ")");
//		}
//
//		// Refresh the computer's hand if it's visible
//		if (showOtherHand) {
//			int xPos = otherHandDialog.getX();
//			int yPos = otherHandDialog.getY();
//
//			otherHandDialog.dispose();
//			otherHandDialog =
//					new HandDialog(frame, computerHand, mainMenu, xPos, yPos);
//			otherHandDialog.setVisible(true);
//		}
//
//		// Reset the colour of the computer's total (in case it went red)
//		playerOneTotalLabel.setForeground(Color.black);
//		// Re-enable the human's hand
//		for (LostCitiesButton handButton : handButtons) {
//			handButton.setEnabled(true);
//		}
//
//		// 3. Reset the deck GUI (need to do this AFTER dealing new hands to get
//		// correct size)
//		drawButton.setText(deck.getSize() + " left");
//		drawButton.setEnabled(false);
//
//		// 4. Reset the discard piles for each suit
//		for (Suit suit : Suit.values()) {
//			// Reset the domain data
//			suit.reset();
//			// Reset the GUI
//			// TODO this repeats some other code in this class
//			LostCitiesButton discardButton = discardButtons.get(suit);
//			discardButton.setPic();
//			discardButton.setToolTipText("No cards");
//			discardButton.setText("0 cards");
//			discardButton.setEnabled(false);
//		}
//	}
//
//	public void showHighScores() {
//		// Respond to "Game->High Scores..." menu item
//		new ScoresDialog(frame, tableModel);
//	}
//
//	public void exit() {
//		// Respond to "Game->Exit" menu item
//		music.stop();
//		frame.dispose(); // free up system resources
//	}
//
//	public void showInfo() {
//		// Respond to "Help->About..." menu item
//		new AboutDialog(frame, version).setVisible(true);
//	}
//
//	public void showInstr() {
//		// Respond to "Help->Using this Program..." menu item
//		new InstrDialog(frame).setVisible(true);
//	}
//
//	public void showStrat() {
//		// Respond to "Help->Strategy..." menu item
//		new StratDialog(frame).setVisible(true);
//	}
//
//	public void showRules() {
//		// Respond to "Help->Rules..." menu item
//		new RulesDialog(frame).setVisible(true);
//	}
//
//	public void setRunningScores(boolean state) {
//		// Respond to "Options->Show/Hide Running Scores" menu item
//		this.showRunningScores = state;
//		if (state) {
//			LOGGER.debug("Turning running scores on.");
//			for (int player = 0; player < PLAYERS; player++) {
//				totalLabel[player].setVisible(true);
//				for (Suit suit : Suit.values()) {
//					int suitIndex = suit.ordinal();
//					String valueStr = exped[player][suitIndex].getValue() + " (";
//					valueStr += exped[player][suitIndex].getPotentialValue(
//							exped[1 - player][suitIndex]);
//					expedLabel[player][suitIndex].setText(valueStr + ")");
//				}
//			}
//		}
//		else {
//			LOGGER.debug("Turning running scores off.");
//			for (int player = 0; player < PLAYERS; player++) {
//				totalLabel[player].setVisible(false);
//				for (Suit suit : Suit.values()) {
//					expedLabel[player][suit.ordinal()].setText(null);
//				}
//			}
//		}
//	}
//
//	public void setOtherHand(boolean showOtherHand) {
//		// Respond to "Options->Show/Hide Other Hand" menu item
//		if (showOtherHand) {
//			otherHandDialog = new HandDialog(frame, computerHand, mainMenu);
//			otherHandDialog.setVisible(true);
//		}
//		else {
//			otherHandDialog.dispose();
//		}
//		this.showOtherHand = showOtherHand;
//	}
//
//	public void setLAF(String lafSpec) {
//		// Respond to "Options->Look and Feel" menu items - set chosen LAF
//		try {
//			UIManager.setLookAndFeel(lafSpec);
//			SwingUtilities.updateComponentTreeUI(frame);
//			frame.pack();
//		}
//		catch (Exception lafException) {
//			// Just log it, not fatal
//			LOGGER.warn(lafException.getMessage(), lafException);
//		}
//	}
//
//	// ***************** Event Handling Methods ********************
//
//	// Action Event Handler
//	public void actionPerformed(ActionEvent e) {
//		// Check if player clicked draw pile
//		if (e.getSource() == drawButton) {
//			playerDeckDraw();
//			return;
//		}
//
//		// Check if player clicked discard pile
//		if (checkDiscardButtons(e)) {
//			return;
//		}
//
//		// Check if player clicked a card in hand
//		if (checkHandButtons(e)) {
//			return;
//		}
//
//		// Check if player chose a menu item
//		mainMenu.checkMenus(e);
//	}
//
//	private boolean checkDiscardButtons(ActionEvent e) {
//		// Check if player clicked one of the discard pile buttons
//		for (Suit suit : Suit.values()) {
//			// It's correct to use the == operator here instead of equals()
//			if (e.getSource() == discardButtons.get(suit)) {
//				LOGGER.debug("Player chooses discard pile " + suit + ".");
//				drawDiscard(PLAYER2, suit);
//				refreshHandPics(PLAYER2);
//				drawButton.setEnabled(false);
//				// Disable each suit's discard pile button
//				for (LostCitiesButton button : discardButtons.values()) {
//					button.setEnabled(false);
//				}
//				if (computerTurn()) {
//					enableHand(true);
//				}
//				else {
//					gameOver();
//				}
//				return true;
//			}
//		}
//		return false;
//	}
//
//	private boolean checkHandButtons(ActionEvent e) {
//		// Check if player clicked one of the buttons for the cards in hand
//		for (int buttonNum = 0; buttonNum < handButtons.length; buttonNum++)
//			if (e.getSource() == handButtons[buttonNum]) {
//				LOGGER.debug("Player clicked hand button " + buttonNum);
//				// Player chose to play or discard a card from their hand
//				Card thisCard = playerHand.getCard(buttonNum);
//
//				LOGGER.debug("This is a " + thisCard);
//				if (thisCard.mustDiscard(exped[PLAYER2][thisCard.getSuit().ordinal()])) {
//					LOGGER.debug("Can't go in expedition - must discard.");
//					discard(PLAYER2, buttonNum);
//					finishHumanMove(thisCard.getSuit(), buttonNum);
//				}
//				else {
//					// Ask player what they want to do
//					CardDialog myDialog = new CardDialog(frame);
//
//					myDialog.setVisible(true);
//					if (myDialog.getChoice() == "Play") {
//						play(PLAYER2, buttonNum);
//						finishHumanMove(null, buttonNum);
//					}
//					if (myDialog.getChoice() == "Discard") {
//						discard(PLAYER2, buttonNum);
//						finishHumanMove(thisCard.getSuit(), buttonNum);
//					}
//				}
//				return true;
//			}
//		return false;
//	}
//
//	void showPopupMenu(MouseEvent event) {
//		LostCitiesButton sourceButton = (LostCitiesButton) event.getComponent();
//		for (int player = 0; player < PLAYERS; player++) {
//			for (Suit suit : Suit.values()) {
//				if (event.getSource() == expedLabel[player][suit.ordinal()]) {
//					int expedSize = exped[player][suit.ordinal()].size();
//					LOGGER.debug("This exped has " + expedSize + " cards.");
//					if (expedSize > 0) {
//						LOGGER.debug("Clicked player " + player + "'s exped no. " + suit);
//						// Add the played cards to the popup menu as menu items
//						// JMenuItem testItem = new JMenuItem("Hello");
//						popupMenuExped.removeAll();
//						// popupMenuExped.add(testItem);
//						for (Card card : exped[player][suit.ordinal()].getCards()) {
//							Integer cardVal = card.getPointValue();
//							popupMenuExped.add(new JMenuItem(Card.getText(cardVal)));
//						}
//						int baseValue = exped[player][suit.ordinal()].getUnmultipliedValue();
//
//						if (baseValue < 0) {
//							JMenuItem shortBy = new JMenuItem("(Short by " + -baseValue + ")");
//							shortBy.setForeground(Color.red);
//							popupMenuExped.add(shortBy);
//						}
//						sourceButton.add(popupMenuExped);
//						popupMenuExped.show(sourceButton, event.getX(), event.getY());
//					}
//				}
//			}
//		}
//	}
//
//	class MyWindowAdapter extends WindowAdapter {
//
//		// Responds to main program window being closed
//		@Override
//		public void windowClosing(WindowEvent e) {
//			if (e.getSource() == frame) {
//				music.stop();
//			}
//		}
//	}
//
//	public LostCitiesDeck getDeck() {
//		return deck;
//	}
}
