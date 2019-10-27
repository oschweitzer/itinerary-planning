package controller;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.event.MouseInputListener;

import model.Application;

/**
 * The Class MapController.
 */


//MouseInputListener interface utilise MouseMotionListener + MouseListener
public class MapController implements MouseInputListener, MouseWheelListener {
	
	/** The application. */
	private Application app;
	
	// Constantes pour la molette
	/** The scroll wheel rate. */
	private final float MOLETTE_TAUX = (float)-0.07; // (de)zoom par unite de molette
	
	/** The scroll wheel ticks. */
	private final int MOLETTE_TICKS = 2; // le zoom n'est effectué que tous les <MOLETTE_TICKS> ticks de la molette, afin d'éviter des rafraichissement trop fréquents
	
	// Nombre actuel de ticks de la molette
	/** The number of ticks. */
	private int nbTicks = 0;
	
	// Coordonnées du point de départ du Drag
	/** The previous x. */
	private int prevX = 0;
	
	/** The previous y. */
	private int prevY = 0;
	
	/**
	 * Instantiates a new map controller.
	 *
	 * @param app the application
	 */
	public MapController(Application app) {
		this.app = app;
	}
    
    //Methods required by the MouseMotionListener interface
    /* (non-Javadoc)
     * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
     */
    public void mouseMoved(MouseEvent e) {
    	app.updateCoord(e.getX(),e.getY());
    }
    
    /* (non-Javadoc)
     * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
     */
    public void mouseDragged(MouseEvent e) {
    	app.deplacerCarte(prevX-e.getX(),prevY-e.getY());
    }
    
    //Methods required by the MouseListener interface
     /* (non-Javadoc)
     * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
     */
    public void mousePressed(MouseEvent e) {
    	 // Click gauche enfoncé
    	 if (e.getButton() == 1) {
	    	 app.modifierCurseurVue(Cursor.MOVE_CURSOR);
	    	 prevX = e.getX();
	    	 prevY = e.getY();
    	 }
     }
     
     /* (non-Javadoc)
      * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
      */
     public void mouseReleased(MouseEvent e) {
    	 app.modifierCurseurVue(Cursor.HAND_CURSOR);
     }
     
     /* (non-Javadoc)
      * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
      */
     public void mouseEntered(MouseEvent e) {
    	 app.modifierCurseurVue(Cursor.HAND_CURSOR);
     }
     
     /* (non-Javadoc)
      * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
      */
     public void mouseExited(MouseEvent e) {
    	 app.modifierCurseurVue(Cursor.DEFAULT_CURSOR);
     }
     
     /* (non-Javadoc)
      * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
      */
     public void mouseClicked(MouseEvent e) {
    	 // Click droit
    	 if (e.getButton() == 3) {
    		 app.setNearestPointAsArrival();
    	 }
    	 
    	 if (e.getButton() == 1)
    	 {
    		 app.setNearestPointAsStart();
    		 
    	 }
     }
    

     // Gestion de la molette de la souris
     /* (non-Javadoc)
      * @see java.awt.event.MouseWheelListener#mouseWheelMoved(java.awt.event.MouseWheelEvent)
      */
     public void mouseWheelMoved(MouseWheelEvent e) {
    	 if (++nbTicks >= MOLETTE_TICKS) {
    		 nbTicks = 0;
    		 app.modifyZoom(e.getWheelRotation() * MOLETTE_TAUX);
    	 }
     }
}
