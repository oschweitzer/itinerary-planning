package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import model.Application;
import view.PanelControls;

/**
 * The Class ComboBoxController.
 */
public class ComboBoxController implements ActionListener {
	
	/** The app. */
	private Application app;
	
	/**
	 * Instantiates a new combo box controller.
	 *
	 * @param app the application
	 */
	public ComboBoxController(Application app) {
		this.app = app;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent evt)
	{
		@SuppressWarnings("rawtypes")
		JComboBox box_clique = (JComboBox) evt.getSource();
		String boxName = box_clique.getName();
		
		if (boxName.equals("jcbVilleDepart")) {
			app.selectFilter(PanelControls.jcbFlag.DEPART);
		}
		else if (boxName.equals("jcbVilleArrivee")) {
			app.selectFilter(PanelControls.jcbFlag.ARRIVEE);
		}
		else if (boxName.equals("jcbRueDepart")) {
			app.fillListPoints(PanelControls.jcbFlag.DEPART);
		}
		else if (boxName.equals("jcbRueArrivee")) {
			app.fillListPoints(PanelControls.jcbFlag.ARRIVEE);
		}
	}
}