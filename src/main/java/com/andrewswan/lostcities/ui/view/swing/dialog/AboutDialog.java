// AboutDialog.java
package com.andrewswan.lostcities.ui.view.swing.dialog;

import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.andrewswan.lostcities.domain.Game;

/**
 * The classic "Help -> About" dialog
 *
 * @author Andrew
 */
public class AboutDialog extends JDialog implements ActionListener {

	// Constants
	private static final int WIDTH_PIXELS = 250;
	private static final int HEIGHT_PIXELS = 215;

	private static final long serialVersionUID = 3917311048868046305L;

	private static final String TITLE = "About";

	// Properties
	private final JButton okButton = new JButton("OK");

	/**
	 * Constructor
	 */
	public AboutDialog() {
		super((Frame) null, TITLE, false);

		// Set size of dialog
		setSize(WIDTH_PIXELS, HEIGHT_PIXELS);

		// Set up content pane and add buttons to it
		final JLabel labelOne = new JLabel("Game Design by Reiner Knizia");
		final JLabel labelTwo = new JLabel("Lost Cities is (C) 1999 KOSMOS Verlag");
		final JLabel labelThree = new JLabel("Java Implementation by Andrew Swan");
		final JLabel labelFour = new JLabel("(andrew.i.swan@gmail.com)");
		final JLabel labelFive = new JLabel("Program Version " + Game.VERSION);
		final JPanel buttonPanel = new JPanel(new GridLayout(1, 3));

		labelOne.setHorizontalAlignment(SwingConstants.CENTER);
		labelTwo.setHorizontalAlignment(SwingConstants.CENTER);
		labelThree.setHorizontalAlignment(SwingConstants.CENTER);
		labelFour.setHorizontalAlignment(SwingConstants.CENTER);
		labelFive.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().setLayout(new GridLayout(6, 1));
		getContentPane().add(labelOne);
		getContentPane().add(labelTwo);
		getContentPane().add(labelThree);
		getContentPane().add(labelFour);
		getContentPane().add(labelFive);
		getContentPane().add(buttonPanel);

		buttonPanel.add(new JLabel());
		buttonPanel.add(okButton);
		buttonPanel.add(new JLabel());

		setLocationRelativeTo(null);	// centres this dialog

		// Register event listeners
		okButton.addActionListener(this);
	}

	public void actionPerformed(final ActionEvent e) {
		if (e.getSource() == okButton) {
			dispose();
		}
	}
}
