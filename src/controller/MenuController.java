package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


import model.Application;

/**
 * The Class MenuController.
 */

//MouseInputListener interface utilise MouseMotionListener + MouseListener
public class MenuController implements ActionListener {
	
	/** The application. */
	private Application app;
	
	/**
	 * Instantiates a new menu controller.
	 *
	 * @param app the application
	 */
	public MenuController(Application app) {
		this.app = app;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent evt) {
		JMenuItem selectedMenuItem = (JMenuItem) evt.getSource();
		String itemName = selectedMenuItem.getText();
		if (itemName.equals("Export Itinerary")) {
			app.saveExport();
		}
		if(itemName.equals("Exit")){
			app.close();
		}
		if(itemName.equals("About")){
			app.about();
		}
		if(itemName.equals("AntiAliasing")){
			app.changeAntiAliasing();
		}
	}
}