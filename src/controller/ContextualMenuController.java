package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import model.Application;

public class ContextualMenuController implements ActionListener {
	
	private Application app;
	
	public ContextualMenuController(Application app) {
		this.app = app;
	}

	public void actionPerformed(ActionEvent e) {
		JMenuItem choix = (JMenuItem) e.getSource();
		app.cacherMenuCarte();
		if (choix.getName() == "choix_arrivee") {
			app.setNearestPointAsArrival();
		}
		else if (choix.getName() == "choix_depart") {
			app.setNearestPointAsStart();
		}
		else if (choix.getName() == "choix_anti_aliasing") {
			app.changeAntiAliasing();
		}
		else if (choix.getName() == "choix_itineraire_couleur") {
			app.choixCouleur();
		}
	}
}
