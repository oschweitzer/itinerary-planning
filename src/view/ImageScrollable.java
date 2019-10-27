package view;

import java.awt.*;
import javax.swing.*;

/**
 * The Class ImageScrollable.
 */
@SuppressWarnings("serial")
public class ImageScrollable extends JLabel
                               implements Scrollable{
	//constante unite de scroll minimum
	/** The MI n_ uni t_ increment. */
	private final int MIN_UNIT_INCREMENT = 5;
	
    /** The initial max unit increment. */
    private int initialMaxUnitIncrement = MIN_UNIT_INCREMENT;
    
    /** The current max unit increment. */
    private int currentMaxUnitIncrement = MIN_UNIT_INCREMENT;
    
    /** The missing picture. */
    private boolean missingPicture = false;
    
    /**
     * Instantiates a new image scrollable.
     *
     * @param m the m
     */
    public ImageScrollable(int m) {
        super();

        initialMaxUnitIncrement = m;
        currentMaxUnitIncrement = m;

        //Let the user scroll by dragging to outside the window.
        setAutoscrolls(true); //enable synthetic drag events
    }
    
    /* (non-Javadoc)
     * @see javax.swing.JComponent#getPreferredSize()
     */
    public Dimension getPreferredSize() {
        if (missingPicture) {
            return new Dimension(320, 480);
        } else {
            return super.getPreferredSize();
        }
    }

    /* (non-Javadoc)
     * @see javax.swing.Scrollable#getPreferredScrollableViewportSize()
     */
    public Dimension getPreferredScrollableViewportSize() {
        return getPreferredSize();
    }

    /* (non-Javadoc)
     * @see javax.swing.Scrollable#getScrollableUnitIncrement(java.awt.Rectangle, int, int)
     */
    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
        //Get the current position.
        int currentPosition = 0;
        if (orientation == SwingConstants.HORIZONTAL) {
            currentPosition = visibleRect.x;
        } else {
            currentPosition = visibleRect.y;
        }

        //Return the number of pixels between currentPosition
        //and the nearest tick mark in the indicated direction.
        if (direction < 0) {
            int newPosition = currentPosition -
                             (currentPosition / currentMaxUnitIncrement)
                              * currentMaxUnitIncrement;
            return (newPosition == 0) ? currentMaxUnitIncrement : newPosition;
        } else {
            return ((currentPosition / currentMaxUnitIncrement) + 1)
                   * currentMaxUnitIncrement
                   - currentPosition;
        }
    }

    /* (non-Javadoc)
     * @see javax.swing.Scrollable#getScrollableBlockIncrement(java.awt.Rectangle, int, int)
     */
    public int getScrollableBlockIncrement(Rectangle visibleRect,
                                           int orientation,
                                           int direction) {
        if (orientation == SwingConstants.HORIZONTAL) {
            return visibleRect.width - currentMaxUnitIncrement;
        } else {
            return visibleRect.height - currentMaxUnitIncrement;
        }
    }

    /* (non-Javadoc)
     * @see javax.swing.Scrollable#getScrollableTracksViewportWidth()
     */
    public boolean getScrollableTracksViewportWidth() {
        return false;
    }

    /* (non-Javadoc)
     * @see javax.swing.Scrollable#getScrollableTracksViewportHeight()
     */
    public boolean getScrollableTracksViewportHeight() {
        return false;
    }

    /**
     * Sets the max unit increment.
     *
     * @param zoom the new max unit increment
     */
    public void setMaxUnitIncrement(float zoom) {
        int temp = (int) (initialMaxUnitIncrement * zoom);
        currentMaxUnitIncrement = (temp > MIN_UNIT_INCREMENT) ? temp : MIN_UNIT_INCREMENT;
    }
}
