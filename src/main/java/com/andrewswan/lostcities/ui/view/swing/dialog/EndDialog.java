// EndDialog.java
package com.andrewswan.lostcities.ui.view.swing.dialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * A dialog that shows the result of the game
 * 
 * @author Andrew
 */
public class EndDialog extends JDialog implements ActionListener {

	// Class variables
	private static final boolean MODAL = true;
	private static final int WIDTH_PIXELS = 300;
	private static final int HEIGHT_PIXELS = 150;

	// Instance variables

	JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
	JButton againButton = new JButton("Play Again");
	JButton exitButton = new JButton("Exit Lost Cities");

	String choice;
	FlowLayout flowLayout1 = new FlowLayout();
	BorderLayout borderLayout1 = new BorderLayout();
	JPanel textPanel = new JPanel();
	JLabel resultLabel = new JLabel();
	FlowLayout flowLayout2 = new FlowLayout();

	// Abstract constructor (we never call this, just used to spawn other
	// contructors)
	private EndDialog(final Frame owner) {
		super(owner, "Game Over", MODAL);
		jbInit();

		// Set sizes
		setSize(WIDTH_PIXELS, HEIGHT_PIXELS);
		setLocationRelativeTo(null);	// centres this dialog

		// Register event listeners
		againButton.addActionListener(this);
		exitButton.addActionListener(this);
	}

	private void jbInit() {
		// Set up content pane and add buttons to it
		getContentPane().setLayout(borderLayout1);
		buttonPanel.setLayout(flowLayout1);
		resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
		resultLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		textPanel.setLayout(flowLayout2);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		buttonPanel.add(againButton);
		buttonPanel.add(exitButton);
		this.getContentPane().add(textPanel, BorderLayout.CENTER);
		textPanel.add(resultLabel, null);
		againButton.setSize(80, 40);
		againButton.setEnabled(true);
		exitButton.setSize(100, 30);
	}

	/**
	 * Constructor for when the game has a winner
	 * 
	 * @param owner
	 * @param winner
	 * @param winScore
	 * @param loseScore
	 */
	public EndDialog(final Frame owner, final int winner, final int winScore,
			final int loseScore)
	{
		this(owner);
		String resultStr = "The winner was Player " + (winner + 1) + ", ";

		resultStr += "with a score of " + winScore + " to " + loseScore + ".";
		resultLabel.setText(resultStr);
		pack();
	}

	/**
	 * Constructor for when the game is drawn
	 * 
	 * @param owner
	 * @param score
	 */
	public EndDialog(final Frame owner, final int score) {
		this(owner);
		resultLabel.setText(
				"The game was tied, with a score of " + score + " all.");
		pack();
	}

	public void actionPerformed(final ActionEvent e) {
		if (e.getSource() == againButton) {
			choice = "Again";
		}
		if (e.getSource() == exitButton) {
			choice = "Exit";
		}
		// Free up screen resources
		dispose();
	}

	/**
	 * Returns the user's answer to this dialog
	 * 
	 * @return
	 */
	public String getChoice() {
		return choice;
	}
}
