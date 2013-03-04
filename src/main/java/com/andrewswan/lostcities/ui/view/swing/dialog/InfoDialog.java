// InfoDialog.java
package com.andrewswan.lostcities.ui.view.swing.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

/**
 * A non-modal dialog that displays some information in a text area
 *
 * @author Andrew
 */
public abstract class InfoDialog extends JDialog implements ActionListener {

	// Class variables
	private int width;
	private int height;

	// Instance variables
	private final JScrollPane scrollPane = new JScrollPane();
	protected JTextPane textPane = new JTextPane();
	private final JPanel btnPanel = new JPanel();
	private final FlowLayout flowLayout2 = new FlowLayout();
	private final JButton okButton = new JButton("OK");

	/**
	 * Constructor
	 *
	 * @param title
	 */
	protected InfoDialog(final String title) {
		super((Frame) null, title, false);
		jbInit();
	}

	private void jbInit() {
		// Set size of dialog and centre it on the screen
		final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		final int screenWidth = (int) screenSize.getWidth();
		final int screenHeight = (int) screenSize.getHeight();

		width = screenWidth * 3 / 4;
		height = screenHeight * 3 / 4;
		setSize(width, height);
		setLocation((screenWidth - width) / 2, (screenHeight - height) / 2);

		// Set up content pane and add components to it
		getContentPane().setLayout(new BorderLayout());

		textPane.setFont(new java.awt.Font("SansSerif", 0, 12));
		textPane.setText("(empty information dialog)");
		btnPanel.setBorder(BorderFactory.createEtchedBorder());
		this.addKeyListener(new InfoDialog_this_keyAdapter(this));
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		scrollPane.setBorder(null);
		scrollPane.getViewport().add(textPane, null);
		textPane.setBorder(BorderFactory.createEtchedBorder());
		textPane.setEditable(false);
		textPane.setBackground(okButton.getBackground());

		getContentPane().add(btnPanel, BorderLayout.SOUTH);
		btnPanel.setLayout(flowLayout2);
		btnPanel.add(okButton, null);
		okButton.addActionListener(this);
	}

	public void actionPerformed(final ActionEvent e) {
		if (e.getSource() == okButton) {
			dispose();
		}
	}

	void this_keyPressed(final KeyEvent e) {
		if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
			dispose();
		}
	}
}

class InfoDialog_this_keyAdapter extends java.awt.event.KeyAdapter {

	InfoDialog adaptee;

	InfoDialog_this_keyAdapter(final InfoDialog adaptee) {
		this.adaptee = adaptee;
	}

	@Override
	public void keyPressed(final KeyEvent e) {
		adaptee.this_keyPressed(e);
	}
}
