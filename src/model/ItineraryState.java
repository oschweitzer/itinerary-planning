package model;

/**
 * The Class ItineraryState. Store a shortest path element
 */
public class ItineraryState
{
	// Numero de la Node (numéro du point du reseau routier)
	/** The number of the node. */
	public int n;
	
	// Autres donnees
	/** The goal. */
	public int g;

	
	/**
	 * Instantiates a new itinerary state.
	 *
	 * @param nde the node
	 * @param goal the goal
	 */
	public ItineraryState(int nde, int goal)
	{
		n = nde;
		g = goal;
	}
}
