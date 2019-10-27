package model;
import java.util.Vector;


/**
 * The Class Road.
 */
public class Road 
{
	// Sens de la route
	/** The direction. */
	private int sens;
	
	// Liste des points
	/** The points list. */
	private Vector<Integer> points;
	
	// Constructeur
	/**
	 * Instantiates a new road.
	 *
	 * @param s the direction
	 */
	public Road(int s) {
		points = new Vector<Integer>();
		sens = s;
	}
	
	// Ajoute un numéro de point à la route
	/**
	 * Add a point number.
	 *
	 * @param num the number
	 */
	public void ajouterNumPoint(Integer num) {
		points.add(num);
	}

	// Renvoie le point de position pos
	/**
	 * Gets the number of the point.
	 *
	 * @param pos the position
	 * @return the number of the point
	 */
	public Integer getNumPoint(int pos) {
		return points.get(pos);
	}
	
	// Renvoie le nombre de points constituants la route
	/**
	 * Gets the number of points.
	 *
	 * @return the number of points
	 */
	public int getNombrePoints() {
		return points.size();
	}
	
	// Renvoie la liste des points
	/**
	 * Gets the points.
	 *
	 * @return the points
	 */
	public Vector<Integer> getPoints() {
		return points;
	}

	/**
	 * Gets the direction.
	 *
	 * @return the direction
	 */
	public int getSens() {
		return sens;
	}
}
