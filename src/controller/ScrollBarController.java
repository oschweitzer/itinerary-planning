package controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import model.Application;

/**
 * The Class ScrollBarController.
 */
public class ScrollBarController implements MouseListener{

	/** The application. */
	private Application app;
	
	/**
	 * Instantiates a new scroll bar controller.
	 *
	 * @param app the application
	 */
	public ScrollBarController(Application app){
		this.app = app;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	public void mouseClicked(MouseEvent e) {}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	public void mouseEntered(MouseEvent e) {}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	public void mouseExited(MouseEvent e) {}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	public void mousePressed(MouseEvent e) {}

	//update le centre apres un deplacement a partir de la Scroll Bar
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	public void mouseReleased(MouseEvent e) {
		app.updateCentre();
	}
	
}