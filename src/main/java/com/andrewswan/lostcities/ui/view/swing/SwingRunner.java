/**
 * Copyright (c) Andrew Swan 2008
 */
package com.andrewswan.lostcities.ui.view.swing;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.andrewswan.lostcities.ui.view.View;

/**
 * Runs this program as a Swing application (as opposed to an applet)
 *
 * @author Andrew
 */
public class SwingRunner {

	// Constants
	private static final String SPRING_CONFIG_FILE = "spring/swing-app-beans.xml";

	/**
	 * Main method
	 *
	 * @param args ignored
	 */
	public static void main(final String[] args) {
		/*
		 * We're using the MVC pattern, with a structure like this:
		 *
		 *        Model (domain) <--> Controller <--> View
		 *
		 * So the only job of the main method is to instantiate these elements, wire
		 * them together, and display the initial view. From then on, the controller
		 * will detect any user gestures made in the view and take the appropriate
		 * action, i.e. update the view and or model as necessary. Changes in model
		 * state will be detected by the controller which will then update the view
		 * as necessary.
		 */
		final ApplicationContext applicationContext =
				new ClassPathXmlApplicationContext(SPRING_CONFIG_FILE);
		// Make the view visible and ready to receive user input
		final View view = (View) applicationContext.getBean("view");
		view.setVisible(true);
	}
}
