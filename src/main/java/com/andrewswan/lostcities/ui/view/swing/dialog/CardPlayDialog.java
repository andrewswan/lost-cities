/**
 * Copyright (c) Andrew Swan 2008
 */
package com.andrewswan.lostcities.ui.view.swing.dialog;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

import com.andrewswan.lostcities.domain.Card;
import com.andrewswan.lostcities.domain.move.AddToExpedition;
import com.andrewswan.lostcities.domain.move.CardPlay;
import com.andrewswan.lostcities.domain.move.Discard;

/**
 * Prompts the human player for what they want to do with a card that can either
 * be added to its expedition or discarded
 *
 * @author Andrew
 */
public class CardPlayDialog extends JDialog {

	// Constants
	private static final long serialVersionUID = 5550185207059436316L;
	
	protected static final Log LOGGER = LogFactory.getLog(CardPlayDialog.class);

	private static final char ADD_TO_EXPEDITION_MNEMONIC = 'A';
	private static final char DISCARD_MNEMONIC = 'D';

	private static final char[] ADD_KEYS = {'A', 'a'};
	private static final char[] DISCARD_KEYS = {'D', 'd'};

	private static final String ADD_TO_EXPEDITION_LABEL = "Add to Expedition";
	private static final String DISCARD_LABEL = "Discard";
	private static final String PROMPT_TEXT = "Do what with the %s?";
	private static final String TITLE = "Action";

	// Properties
	private final AbstractButton addToExpeditionButton;
	private final AbstractButton discardButton;
	private final JLabel promptLabel = new JLabel();
	private final List<ActionListener> actionListeners;

	private Card card;	// the card being asked about, can't be final
	private CardPlay choice;	// can't be final

	/**
	 * Constructor for a modal dialog with the given parent frame
	 *
	 * @param parent the frame to which this dialog is modal
	 */
	public CardPlayDialog(final Frame parent) {
		super(parent, TITLE, true);	// modal
		// Set up the contents
		this.addToExpeditionButton = getAddToExpeditionButton();
		this.discardButton = getDiscardButton();
		this.actionListeners = new ArrayList<ActionListener>();
		addContents();
		// Enable keyboard support
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(final KeyEvent keyEvent) {
				processKey(keyEvent.getKeyChar());
			}
		});
	}

	/**
	 * Sets up the contents of this dialog
	 */
	private void addContents() {
		// Set the layout
		setLayout(new BorderLayout());

		// Add the prompt text in the centre
		add(getPromptLabel(), BorderLayout.CENTER);

		// Add the buttons at the bottom (south)
		add(getButtonPanel(), BorderLayout.SOUTH);
	}

	/**
	 * Returns the component that prompts the user for a decision
	 *
	 * @return a non-<code>null</code> component
	 */
	private Component getPromptLabel() {
		this.promptLabel.setHorizontalAlignment(SwingConstants.CENTER);
		this.promptLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		// N.B. we set the text later when we know what card it is
		return this.promptLabel;
	}

	/**
	 * Returns the component containing the buttons that the user can click to
	 * indicate their desired action
	 *
	 * @return a non-<code>null</code> container
	 */
	private Container getButtonPanel() {
		final JPanel buttonPanel = new JPanel();
		buttonPanel.add(this.addToExpeditionButton);
		buttonPanel.add(this.discardButton);
		return buttonPanel;
	}

	/**
	 * Returns the "add to expedition" button
	 *
	 * @return a non-<code>null</code> component
	 */
	private AbstractButton getAddToExpeditionButton() {
		final JButton button = new JButton();
		button.setMnemonic(ADD_TO_EXPEDITION_MNEMONIC);
		button.setText(ADD_TO_EXPEDITION_LABEL);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent event) {
				addToExpedition();
			}
		});
		return button;
	}

	/**
	 * Returns the discard button
	 *
	 * @return a non-<code>null</code> component
	 */
	private AbstractButton getDiscardButton() {
		final JButton button = new JButton();
		button.setMnemonic(DISCARD_MNEMONIC);
		button.setText(DISCARD_LABEL);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent event) {
				discard();
			}
		});
		return button;
	}

	/**
	 * Handles the user pressing a key while this dialog is shown. Has package
	 * visibility to improve performance.
	 *
	 * @param key the pressed key
	 */
	void processKey(final char key) {
		LOGGER.debug("Key pressed = " + key);
		if (ArrayUtils.contains(ADD_KEYS, key)) {
			addToExpedition();
		}
		else if (ArrayUtils.contains(DISCARD_KEYS, key)) {
			discard();
		}
		else {
			LOGGER.debug("Ignoring keypress '" + key + "'");
		}
	}

	// ----------------------------- Handler Methods -----------------------------

	/**
	 * Handles the user choosing to add the card to the relevant expedition. Has
	 * package visibility to improve performance.
	 */
	void addToExpedition() {
		choice = new AddToExpedition(this.card);
		dispose();
	}

	/**
	 * Handles the user choosing to discard the card. Has package visibility to
	 * improve performance.
	 */
	void discard() {
		choice = new Discard(this.card);
		dispose();
	}

	/**
	 * Instructs this dialog to ask the user what to do with the given card
	 *
	 * @param cardToAskAbout cannot be <code>null</code>
	 */
	public void askAbout(final Card cardToAskAbout) {
		this.card = cardToAskAbout;
		this.choice = null;
		this.promptLabel.setText(String.format(PROMPT_TEXT, card.getDisplayName()));
		// Size it
		pack();
		// Centre it on the screen
		setLocationRelativeTo(null);
		// Show it
		setVisible(true);	// blocks until answered
		// When we get here, the user has made a choice; notify the listeners of it
		Assert.notNull(choice, "User should have chosen a card play at this point");
		final ActionEvent event = new ActionEvent(choice, 0, null);
		for (final ActionListener listener : this.actionListeners) {
			listener.actionPerformed(event);
		}
	}

	/**
	 * Adds the given listener to this dialog
	 * 
	 * @param listener a listener interested in card play choices; can't be
	 *   <code>null</code>
	 */
	public void addActionListener(final ActionListener listener) {
		Assert.notNull(listener, "Listener can't be null");
		this.actionListeners.add(listener);
	}
}
