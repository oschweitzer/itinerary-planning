package view;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import model.Application;

/**
 * The Class PanelInformations.
 */
@SuppressWarnings("serial")
public class PanelInformations extends JPanel{

	/** The JLabel infos. */
	private JLabel lblInfos;
	
	/** The DefaultListModel infos. */
	@SuppressWarnings("rawtypes")
	private DefaultListModel dlmInfos; 
	
	/** The JList infos. */
	@SuppressWarnings("rawtypes")
	private JList jlInfos;
	
	/** The JScrollPane infos. */
	private JScrollPane jspInfos;
	
	/** The JLabel road map. */
	private JLabel lblFeuilleRoute;
	
	/** The DefaultListModel road map. */
	@SuppressWarnings("rawtypes")
	private DefaultListModel dlmFeuilleRoute; 
	
	/** The JList road map. */
	@SuppressWarnings("rawtypes")
	private JList jlFeuilleRoute;
	
	/** The JScrollPane road map. */
	private JScrollPane jspFeuilleRoute;
	
	/** The inside border. */
	private Border jspBorder, outsideBorder, insideBorder;
	
	/** The information panel height. */
	private final int INFOS_HAUTEUR = 300;
	
	// Donnees des infos
	
	/** The first message */
	private String message1;
	
	/** The second message */
	private String message2;
	
	/** The unit system. */
	private String su;
	
	/** The journey length. */
	private String longueur_trajet;
	
	/** The point id. */
	private int x, y, idPoint;
	
	/** The arrival. */
	private int depart = -1, arrivee = -1;
	
	/** The zoom. */
	float zoom = Application.ZOOM_INITIAL;
	
	/**
	 * Instantiates a new panel informations.
	 *
	 * @param l the l
	 * @param h the h
	 * @param su the su
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public PanelInformations(int l, int h, String su) {
		super();
		this.su = su;
		
		// Creation du Layout (de type BoxLayout)
		setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
		
		// Ajout du label informations
		lblInfos = new JLabel("Informations :");
		lblInfos.setFont(lblInfos.getFont().deriveFont(Font.BOLD));
		lblInfos.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
		lblInfos.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(lblInfos);
		
		// Creation de la bordure des listes
		outsideBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		// A ameliorer
		insideBorder = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		jspBorder = BorderFactory.createCompoundBorder(outsideBorder, insideBorder);
		
		//Init feuille route
		dlmFeuilleRoute = new DefaultListModel();
		jlFeuilleRoute = new JList(dlmFeuilleRoute);
		
		// Ajout de la liste scrollable d'informations
		dlmInfos = new DefaultListModel();
		jlInfos = new JList(dlmInfos);
		jlInfos.setLayoutOrientation(JList.VERTICAL);
		jlInfos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jlInfos.setVisibleRowCount(-1);
		jspInfos = new JScrollPane(jlInfos);
		jspInfos.setPreferredSize(new Dimension((int)l, INFOS_HAUTEUR));
		jspInfos.setMinimumSize(new Dimension((int)l, INFOS_HAUTEUR));
		jspInfos.setBorder(jspBorder);
		add(jspInfos);
		setMessage("Bienvenue !", "Choisissez un itin\u00e9raire.");
		
		// Ajout du label Feuille de route
		lblFeuilleRoute = new JLabel("Feuille de route :");
		lblFeuilleRoute.setFont(lblFeuilleRoute.getFont().deriveFont(Font.BOLD));
		lblFeuilleRoute.setAlignmentX(Component.CENTER_ALIGNMENT);
		//add(lblFeuilleRoute);
		
		// Ajout de la liste scrollable feuille de route
		
		jlFeuilleRoute.setLayoutOrientation(JList.VERTICAL);
		jlFeuilleRoute.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jlFeuilleRoute.setVisibleRowCount(-1);
		jlFeuilleRoute.setCellRenderer(new AfficheurElementListe());
		jspFeuilleRoute = new JScrollPane(jlFeuilleRoute);
		jspFeuilleRoute.add(jlFeuilleRoute);
		jspFeuilleRoute.setPreferredSize(new Dimension((int)l, (int)(h * (float)4/5)));
		jspFeuilleRoute.setBorder(jspBorder);
		jspFeuilleRoute.setVisible(false);
		add(jspFeuilleRoute);
		
		
	}
	
	/**
	 * Add road.
	 *
	 * @param route the road
	 * @param chemin_image the image path
	 */
	@SuppressWarnings("unchecked")
	public void ajouterRoute(String route, String chemin_image) {
		dlmFeuilleRoute.addElement(new ElementListe(route, chemin_image));
	}
	
	/**
	 * Add road.
	 *
	 * @param route the road
	 */
	@SuppressWarnings("unchecked")
	public void ajouterRoute(String route) {
		dlmFeuilleRoute.addElement(new ElementListe(route));
	}
	
	/**
	 * Reset information.
	 */
	public void reinitialiserInfos() {
		dlmInfos.removeAllElements();
	}
	
	/**
	 * Reset roads.
	 */
	public void reinitialiserRoutes() {
		dlmFeuilleRoute.removeAllElements();
	}
	
	/**
	 * Sets the message.
	 *
	 * @param mess1 the mess1
	 * @param mess2 the mess2
	 */
	public void setMessage(String mess1, String mess2) {
		message1 = mess1;
		message2 = mess2;
		refaireInfos();
	}
	
	/**
	 * Sets the journey length.
	 *
	 * @param longueur the new journey length
	 */
	public void setLongueurTrajet(String longueur) {
		longueur_trajet = longueur;
		refaireInfos();
	}
	
	/**
	 * Update coordinates.
	 *
	 * @param x the x
	 * @param y the y
	 * @param idPoint the point id
	 */
	public void updateCoord(int x, int y, int idPoint) {
		this.x = x;
		this.y = y;
		this.idPoint = idPoint;
		refaireInfos();
	}
	
	/**
	 * Update zoom.
	 *
	 * @param zoom the zoom
	 */
	public void updateZoom(float zoom) {
		this.zoom = zoom;
		refaireInfos();
	}
	
	/**
	 * Update departure.
	 *
	 * @param d the departure
	 */
	public void updateDepart(int d) {
		depart = d;
		refaireInfos();
	}	
	
	/**
	 * Update arrival.
	 *
	 * @param a the arrival
	 */
	public void updateArrivee(int a) {
		arrivee = a;
		refaireInfos();
	}
	
	/**
	 * Redo information.
	 */
	@SuppressWarnings("unchecked")
	private void refaireInfos() {
		dlmInfos.removeAllElements();
		if(message1 != null)
			dlmInfos.addElement(message1);
		if(message2 != null)
			dlmInfos.addElement(message2);
		dlmInfos.addElement(new String("Systeme d'unit\u00e9s : " + su));
		
		dlmInfos.addElement(new String("Point courant :"+idPoint));
		dlmInfos.addElement(new String("Coordonn\u00e9e X : " + x));
		dlmInfos.addElement(new String("Coordonn\u00e9e Y : " + y));
		dlmInfos.addElement(new String("Zoom : " + (int)(zoom * 100) + " %"));
		dlmInfos.addElement(new String(" "));
		dlmInfos.addElement(new String("Longueur du trajet : " + ((longueur_trajet == null) ? "-" : longueur_trajet)));
		dlmInfos.addElement(new String("D\u00e9part : " + ((depart == -1) ? "-" : "Point "+ new Integer(depart).toString())));
		dlmInfos.addElement(new String("Arriv\u00e9e : " + ((arrivee == -1) ? "-" : "Point "+ new Integer(arrivee).toString())));
		dlmInfos.addElement(new String(" "));
		dlmInfos.addElement(new String("Itineraire: "));
		for(Object s: dlmFeuilleRoute.toArray()){
			dlmInfos.addElement(s);
		}
	}
	
	public ArrayList<String> getItinerary(){
		ArrayList<String> itinerary = new ArrayList<String>();
		itinerary.add("Depart : "+ depart + " Arrivee : "+arrivee);
		itinerary.add("Longueur du trajet : " + ((longueur_trajet == null) ? "-" : longueur_trajet));
		itinerary.add("Votre itineraire: "+System.getProperty("line.separator")+System.getProperty("line.separator"));
		for(Object s: dlmFeuilleRoute.toArray()){
			itinerary.add(s.toString());
		}
		return itinerary;
	}
}

/**
 * 
 * The class ElementListe
 *
 */
class ElementListe {
	  private final String texte;
	  private final String chemin_image;
	  private ImageIcon image;
	  
	  /**
	   * Initialize the list element with a text
	   * 
	   * @param texte The text
	   */
	  public ElementListe(String texte) {
		  this.texte = texte;
		  this.chemin_image = "";
	  }

	  /**
	   * Initialize the list element with a text and the image path
	   * 
	   * @param texte The text
	   * @param chemin_image The image path
	   */
	  public ElementListe(String texte, String chemin_image) {
		  this.texte = texte;
		  this.chemin_image = chemin_image;
	  }

	  /**
	   * Gets the title
	   * 
	   * @return the title
	   */
	  public String getTitle() {
		  return texte;
	  }
	  
	  /**
	   * Gets the image path
	   * 
	   * @return the image path
	   */
	  public String getCheminImage() {
		  return chemin_image;
	  }

	  /**
	   * Gets the image
	   * 
	   * @return the image
	   */
	  public ImageIcon getImage() {
		  if (image == null) {
			  image = new ImageIcon(chemin_image);
		  }
		  return image;
	  }
	  
	  // Override standard toString method to give a useful result
	  public String toString() {
		  return texte;
	  }
}

/**
 * 
 * The class AfficheurElementListe
 *
 */
@SuppressWarnings({ "serial", "rawtypes" })
class AfficheurElementListe extends JLabel implements ListCellRenderer {
	
	private final Color HIGHLIGHT_COLOR = new Color(0, 0, 128);
	
	public AfficheurElementListe() {
		setOpaque(true);
	    setIconTextGap(5);
	}
	
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) 
	{
	    ElementListe element = (ElementListe) value;
		setText(element.getTitle());
		if (element.getCheminImage() != "") {
			setIcon(element.getImage());
		}
		else {
			setIcon(new ImageIcon());
		}
	    if (isSelected) {
	      setBackground(HIGHLIGHT_COLOR);
	      setForeground(Color.white);
	    }
	    else {
	      setBackground(Color.white);
	      setForeground(Color.black);
	    }
		return this;
	}

}