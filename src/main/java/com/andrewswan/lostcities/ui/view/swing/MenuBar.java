/**
 * Copyright (c) Andrew Swan 2008
 */
package com.andrewswan.lostcities.ui.view.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import org.apache.commons.lang.mutable.MutableBoolean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.andrewswan.lostcities.ui.view.swing.dialog.AboutDialog;
import com.andrewswan.lostcities.ui.view.swing.dialog.RulesDialog;
import com.andrewswan.lostcities.ui.view.swing.dialog.StrategyDialog;
import com.andrewswan.lostcities.ui.view.swing.dialog.UsageDialog;

/**
 * The main menu bar for the Swing {@link SwingView}.
 *
 * @author Andrew
 */
public class MenuBar extends JMenuBar {

	// Constants
	protected static final Log LOGGER = LogFactory.getLog(MenuBar.class);
	
	private static final char GAME_EXIT_MNEMONIC = 'x';
	private static final char GAME_NEW_MNEMONIC = 'N';
	
	// -- Required for serialization
	private static final long serialVersionUID = 2164821922835922905L;

	// -- Menu labels
	private static final String
	 	// Game menu
	  GAME_LABEL = "Game",
	  GAME_EXIT_LABEL = "Exit...",
	  GAME_NEW_LABEL = "New...",
	  // Help menu
	  HELP_LABEL = "Help",
	  HELP_ABOUT_LABEL = "About...",
	  HELP_RULES_LABEL = "Rules...",
	  HELP_STRATEGY_LABEL = "Strategy...",
	  HELP_USAGE_LABEL = "Using This Program...",
	  // Options menu
	  OPTIONS_LABEL = "Options",
	  OPTIONS_HIDE_RUNNING_SCORES_LABEL = "Hide Running Scores",
	  OPTIONS_SHOW_RUNNING_SCORES_LABEL = "Show Running Scores";

	// -- Hotkeys
	private static final KeyStroke
	  GAME_EXIT_KEY =
	  		KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_DOWN_MASK),
	  GAME_NEW_KEY = KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0),
		HELP_USAGE_KEY = KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0);

	// Properties
	// -- "Game" menu items
	private final JMenuItem gameExit;
	private final JMenuItem gameNew;
	private final JMenuItem optionsShowRunningScores;

	// Lazy-loaded GUI components (with default visibility to improve performance)
	AboutDialog aboutDialog;
	RulesDialog rulesDialog;
	StrategyDialog strategyDialog;
	UsageDialog usageDialog;

	/**
	 * Constructor
	 *
	 * @param showRunningScores whether the view is showing the running scores
	 */
	public MenuBar(final MutableBoolean showRunningScores) {
		this.gameExit = new JMenuItem(GAME_EXIT_LABEL);
		this.gameNew = new JMenuItem(GAME_NEW_LABEL);
		this.optionsShowRunningScores =	new JMenuItem();
		add(getGameMenu());
		add(getOptionsMenu(showRunningScores));
		add(getHelpMenu());
	}

	// ------------------------- Initialisation Methods --------------------------

	/**
	 * Returns the "Game" menu for this menu bar
	 *
	 * @return a non-<code>null</code> menu
	 */
	private JMenu getGameMenu() {
		final JMenu gameMenu = new JMenu(GAME_LABEL);
		gameMenu.setMnemonic('G');

		// New Game
		gameMenu.add(gameNew);
		gameNew.setAccelerator(GAME_NEW_KEY);
		gameNew.setMnemonic(GAME_NEW_MNEMONIC);

		// Separator
		gameMenu.addSeparator();

		// Exit Game
		gameMenu.add(gameExit);
		gameExit.setAccelerator(GAME_EXIT_KEY);
		gameExit.setMnemonic(GAME_EXIT_MNEMONIC);

		return gameMenu;
	}

	/**
	 * Returns the "Options" menu for this menu bar
	 *
	 * @param showRunningScores whether the view is showing the running scores
	 * @return a non-<code>null</code> menu
	 */
	private JMenu getOptionsMenu(final MutableBoolean showRunningScores) {
		final JMenu optionsMenu = new JMenu(OPTIONS_LABEL);
		optionsMenu.setMnemonic('O');

		// Show Running Scores
		optionsMenu.add(optionsShowRunningScores);
		setShowRunningScores(showRunningScores.booleanValue());
		gameNew.setMnemonic('R');

		return optionsMenu;
	}

	@Override
	public JMenu getHelpMenu() {
		final JMenu helpMenu = new JMenu(HELP_LABEL);
		helpMenu.setMnemonic('H');

		// Rules
		final JMenuItem helpRules = new JMenuItem(HELP_RULES_LABEL);
		helpRules.addActionListener(new HelpRulesListener());
		helpRules.setMnemonic('R');
		helpMenu.add(helpRules);

		// Strategy
		final JMenuItem helpStrategy = new JMenuItem(HELP_STRATEGY_LABEL);
		helpStrategy.addActionListener(new HelpStrategyListener());
		helpStrategy.setMnemonic('S');
		helpMenu.add(helpStrategy);

		// Usage
		final JMenuItem helpUsage = new JMenuItem(HELP_USAGE_LABEL);
		helpUsage.addActionListener(new HelpUsageListener());
		helpUsage.setAccelerator(HELP_USAGE_KEY);
		helpUsage.setMnemonic('U');
		helpMenu.add(helpUsage);

		// Separator
		helpMenu.addSeparator();

		// About
		final JMenuItem helpAbout = new JMenuItem(HELP_ABOUT_LABEL);
		helpAbout.addActionListener(new HelpAboutListener());
		helpAbout.setMnemonic('A');
		helpMenu.add(helpAbout);

		return helpMenu;
	}

	/*
	 * ------------------ Domain Listener Registration Methods -------------------
	 *
	 * These methods register listeners for events with significance outside of
	 * the view, i.e. that affect or require information from the controller
	 * and/or the model.
	 */

	/**
	 * Adds the given listener to the "Exit Game" menu item
	 *
	 * @param listener the listener to add; can't be <code>null</code>
	 */
	public void addExitGameListener(final ActionListener listener) {
		this.gameExit.addActionListener(listener);
	}

	/**
	 * Adds the given listener to the "New Game" menu item
	 *
	 * @param listener the listener to add; can't be <code>null</code>
	 */
	public void addNewGameListener(final ActionListener listener) {
		this.gameNew.addActionListener(listener);
	}

	/**
	 * Adds the given listener to the "Show Running Scores" menu item
	 *
	 * @param listener the listener to add; can't be <code>null</code>
	 */
	public void addShowRunningScoresListener(final ActionListener listener) {
		this.optionsShowRunningScores.addActionListener(listener);
	}

	/*
	 * ---------------------- Non-Domain-Related Listeners -----------------------
	 *
	 * These inner classes are handlers for events that only affect the view, in
	 * other words do not need to be sent to the controller for handling.
	 */

	class HelpAboutListener implements ActionListener {

		public void actionPerformed(final ActionEvent e) {
			LOGGER.debug("Detected a 'Help -> About' event");
			if (MenuBar.this.aboutDialog == null) {
				MenuBar.this.aboutDialog = new AboutDialog();
			}
			MenuBar.this.aboutDialog.setVisible(true);
		}
	}

	class HelpRulesListener implements ActionListener {

		public void actionPerformed(final ActionEvent e) {
			LOGGER.debug("Detected a 'Help -> Rules' event");
			if (MenuBar.this.rulesDialog == null) {
				MenuBar.this.rulesDialog = new RulesDialog();
			}
			MenuBar.this.rulesDialog.setVisible(true);
		}
	}

	class HelpStrategyListener implements ActionListener {

		public void actionPerformed(final ActionEvent e) {
			LOGGER.debug("Detected a 'Help -> Strategy' event");
			if (MenuBar.this.strategyDialog == null) {
				MenuBar.this.strategyDialog = new StrategyDialog();
			}
			MenuBar.this.strategyDialog.setVisible(true);
		}
	}

	class HelpUsageListener implements ActionListener {

		public void actionPerformed(final ActionEvent e) {
			LOGGER.debug("Detected a 'Help -> Usage' event");
			if (MenuBar.this.usageDialog == null) {
				MenuBar.this.usageDialog = new UsageDialog();
			}
			MenuBar.this.usageDialog.setVisible(true);
		}
	}

	// ----------------------------- Update Methods ------------------------------

	public void setShowRunningScores(final boolean enabled) {
		if (enabled) {
			this.optionsShowRunningScores.setText(OPTIONS_HIDE_RUNNING_SCORES_LABEL);
		}
		else {
			this.optionsShowRunningScores.setText(OPTIONS_SHOW_RUNNING_SCORES_LABEL);
		}
	}
}
