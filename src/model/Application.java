package model;

import static java.lang.Math.atan2;
import static java.lang.Math.sin;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;

import view.AboutWindow;
import view.AppWindow;
import view.PanelControls;
import controller.ButtonsController;
import controller.ComboBoxController;
import controller.ContextualMenuController;
import controller.MapController;
import controller.MenuController;
import controller.ScrollBarController;
import controller.SliderController;


/**
 * The Class Application.
 *
 */

public class Application {
	// Quelques constantes
	/** The Constant ZOOM_MAX. */
	public final static float ZOOM_MAX = (float) 1;
	
	/** The Constant ZOOM_MIN. */
	public final static float ZOOM_MIN = (float)0.1;
	
	/** The Constant ZOOM_INITIAL. */
	public final static float ZOOM_INITIAL = (float) 0.5;
	
	/** The Constant CRAN_ZOOM_PLUS. */
	public final static float CRAN_ZOOM_PLUS = (float) 0.1;
	
	/** The Constant CRAN_ZOOM_MOINS. */
	public final static float CRAN_ZOOM_MOINS = (float) -0.1;
	
	/** The Constant RETOUR_ZOOM_INITIAL. */
	public final static float RETOUR_ZOOM_INITIAL = -2;
	
	/** The DOSSIE r_ images. */
	private final String DOSSIER_IMAGES = "img/";
	
	/** The ECHELL e_ carte. */
	private final double ECHELLE_CARTE = 7.5; // <ECHELLE_CARTE> metres = 1 px
	
	/** The ECHELL e_ taille. */
	private final int ECHELLE_TAILLE = 30;
	
	// Constantes des coordonnées 
	/** The LAMBER t_ haut_ gauche. */
	private final Point LAMBERT_HAUT_GAUCHE = new Point(897990, 2324046);
	
	/** The LAMBER t_ ba s_ droite. */
	private final Point LAMBERT_BAS_DROITE = new Point(971518, 2272510);
	
	/** The PIXEL s_ ba s_ droite. */
	private final Point PIXELS_BAS_DROITE = new Point(9807, 6867);
	
	/** The SYSTEM e_ unite. */
	private final String SYSTEME_UNITE = "Lambert II";
	
	/** The MARG e_ redimensionnemen t_ auto. */
	private final int MARGE_REDIMENSIONNEMENT_AUTO = 50;
	
	// Constantes pour le filtre de Rues
	/** The Constant TOUTES. */
	public final static String TOUTES = "Toutes";
	
	/** The Constant AUTOROUTE. */
	public final static String AUTOROUTE = "Autoroutes";
	
	/** The Constant EUROPEENNE. */
	public final static String EUROPEENNE = "Europ\u00e9enne";
	
	/** The Constant NATIONALE. */
	public final static String NATIONALE = "Nationale";
	
	/** The Constant DEPARTEMENTALE. */
	public final static String DEPARTEMENTALE = "D\u00e9partementale";
	
	/**
	 * The Enum rueFlag.
	 */
	public enum rueFlag {
/** The ALL flag. */
ALL,
/** The AUTOROUTE. */
AUTOROUTE,
/** The EUROPEENNE. */
EUROPEENNE,
/** The NATIONALE. */
NATIONALE,
/** The DEPARTEMENTALE. */
DEPARTEMENTALE,
/** The AUTRE. */
AUTRE};
	
	// Vue
	/** The fenetre. */
	private AppWindow fenetre;
	
	/** The about window. */
	private AboutWindow aboutWindow;
	
	/** The lien carte. */
	private String lienCarte;
	
	// Controlleur
	/** The controlleur_boutons. */
	private ButtonsController controlleur_boutons;
	
	/** The controlleur_slider. */
	private SliderController controlleur_slider;
	
	/** The controlleur_carte. */
	private MapController controlleur_carte;
	
	/** The controlleur_scroll_bar. */
	private ScrollBarController controlleur_scroll_bar;
	
	/** The controlleur_combo_box. */
	private ComboBoxController controlleur_combo_box;
	
	/** The controlleur_menu_contextuel. */
	private ContextualMenuController controlleur_menu_contextuel;
	
	/** The controlleur_menu_bar. */
	private MenuController controlleur_menu_bar;
	
	// Réseau routier
	/** The road network. */
	private RoadNetwork reseau_routier;
	
	/** The shortest path. */
	private ShortestPath plus_court_chemin;
	
	/** The path. */
	private Vector<ItineraryState> chemin;
	
	// Point le plus proche de la souris
	/** The closest point to the mouse. */
	private int point_proche_souris = -1;
	
	//Point au centre de l'ecran (memorise en coord taille reelle)
	/** The middle point of the screen. */
	private Point ptCentre = new Point(-1, -1);
	
	// Numéros des points de départ et d'arrivée
	/** The departure. */
	private int depart = -1;
	
	/** The arrival. */
	private int arrivee = -1;
	
	/** The zoom percentage. */
	private float pourcentage_zoom = ZOOM_INITIAL;
	
	/** The old zoom. */
	private float old_zoom = ZOOM_INITIAL;
	
	/**
	 * Instantiates a new application.
	 *
	 * @param fichierXml the xml file
	 */
	public Application(String fichierXml) {
		// Construction des différents éléments de l'application
		reseau_routier = new RoadNetwork();
		reseau_routier.parseXml(fichierXml);
		
		lienCarte = DOSSIER_IMAGES + reseau_routier.getNomFichierImage();
		new ImageIcon(lienCarte);
		
		
		controlleur_boutons = new ButtonsController(this);
		controlleur_slider = new SliderController(this);
		controlleur_carte = new MapController(this);
		controlleur_scroll_bar = new ScrollBarController(this);
		controlleur_combo_box = new ComboBoxController(this);
		controlleur_menu_contextuel = new ContextualMenuController(this);
		controlleur_menu_bar = new MenuController(this);
		
		fenetre = new AppWindow(lienCarte, SYSTEME_UNITE);
		aboutWindow = new AboutWindow();
		fenetre.setVisible(true);
		
		plus_court_chemin = new ShortestPath();
		plus_court_chemin.init(reseau_routier, ZOOM_INITIAL);
		chemin = new Vector<ItineraryState>();
		
		// Initialisations des différents éléments
		fenetre.getPanneauVue().getCarte().setScaleSize(ECHELLE_TAILLE);
		miseEnPlaceImages();
		remplirListesVilles();
		remplirListesRoutes(rueFlag.ALL, PanelControls.jcbFlag.BOTH);
		miseEnPlaceEcouteurs();
		initialiserListesPoints();
		updateCentre();
		afficherCarte();
	}
	
	
	/**
	 * Setting up the images
	 */
	private void miseEnPlaceImages() {
		fenetre.setIconImage(new ImageIcon("img/logoBelfort.gif").getImage());
		//fenetre.getPanneauControles().setIconZoomMoins(new ImageIcon(DOSSIER_IMAGES + "loupe_moins.gif"));
		//fenetre.getPanneauControles().setIconZoomPlus(new ImageIcon(DOSSIER_IMAGES + "loupe_plus.gif"));
	}
	
	/**
	 * Setting up the listeners
	 */
	private void miseEnPlaceEcouteurs() {
		// Met en place les différents écouteurs pour les interractions avec l'utilisateur
		fenetre.getPanneauControles().ajouterEcouteurAuBoutonOk(controlleur_boutons);
		fenetre.getPanneauControles().ajouterEcouteurAuBoutonZoomMoins(controlleur_boutons);
		fenetre.getPanneauControles().ajouterEcouteurAuBoutonZoomPlus(controlleur_boutons);
		fenetre.getPanneauControles().ajouterEcouteurAuBoutonZoomReel(controlleur_boutons);
		fenetre.getPanneauControles().ajouterEcouteurAuBoutonZoomGlobal(controlleur_boutons);
		fenetre.getPanneauControles().ajouterEcouteurAuBoutonZoomGrosPlan(controlleur_boutons);
		fenetre.getPanneauControles().ajouterEcouteurAuSlider(controlleur_slider);
		fenetre.getPanneauControles().ajouterEcouteurVilleDepart(controlleur_combo_box);
		fenetre.getPanneauControles().ajouterEcouteurVilleArrivee(controlleur_combo_box);
		fenetre.getPanneauControles().ajouterEcouteurRueDepart(controlleur_combo_box);
		fenetre.getPanneauControles().ajouterEcouteurRueArrivee(controlleur_combo_box);
		fenetre.getPanneauVue().getCarte().ajouterEcouteurMenu(controlleur_menu_contextuel);
		fenetre.getPanneauVue().getCarte().ajouterEcouteurCarte(controlleur_carte);
		fenetre.getPanneauVue().ajouterEcouteurScrollBar(controlleur_scroll_bar);
		for( JMenuItem item : fenetre.getMenuBarItems()){ item.addActionListener(controlleur_menu_bar);}
	}
	
	/**
	 * Fill the towns list
	 */
	public void remplirListesVilles() {
		// Remplit les liste des villes
		Object[] listeRoutes = reseau_routier.getListeRoutes().toArray();
		Arrays.sort(listeRoutes);
		fenetre.getPanneauControles().ajouterVilleDansCombobox(TOUTES);
		fenetre.getPanneauControles().ajouterVilleDansCombobox(AUTOROUTE);
		fenetre.getPanneauControles().ajouterVilleDansCombobox(EUROPEENNE);
		fenetre.getPanneauControles().ajouterVilleDansCombobox(NATIONALE);
		fenetre.getPanneauControles().ajouterVilleDansCombobox(DEPARTEMENTALE);
		
		String ville;
		RoadName rue;
		for (int l = 0; l < reseau_routier.getNombreRoutes(); l++) {			
			rue = new RoadName(listeRoutes[l]);
			if(rue.estUneVille()){
				ville = rue.extraireNomVille();
				if(!fenetre.getPanneauControles().villeDejaPresente(ville))
					fenetre.getPanneauControles().ajouterVilleDansCombobox(ville);
			}
		}
	}
	
	/**
	 * Fill the roads list
	 *
	 * @param filtre the filter
	 * @param flag the flag
	 */
	public void remplirListesRoutes(rueFlag filtre, PanelControls.jcbFlag flag) {
		Object[] listeRoutes = reseau_routier.getListeRoutes().toArray();
		Arrays.sort(listeRoutes);
		
		//Reinitialisation de la comboBox
		fenetre.getPanneauControles().viderRueComboBox(flag);
		
		//Remplissage en fonction du flag
		for (int l = 0; l < reseau_routier.getNombreRoutes(); l++) {		
			RoadName rue = new RoadName(listeRoutes[l]);
			
			switch (filtre){
			case ALL :
				fenetre.getPanneauControles().ajouterRouteDansCombobox(listeRoutes[l].toString(),flag);
				break;
				
			case AUTOROUTE :
				if(rue.estUneAutoroute())
					fenetre.getPanneauControles().ajouterRouteDansCombobox(listeRoutes[l].toString(),flag);
				break;
				
			case EUROPEENNE :
				if(rue.estUneEuropeenne())
					fenetre.getPanneauControles().ajouterRouteDansCombobox(listeRoutes[l].toString(),flag);
				break;
				
			case NATIONALE :	
				if(rue.estUneNationale())
					fenetre.getPanneauControles().ajouterRouteDansCombobox(listeRoutes[l].toString(),flag);
				break;
				
			case DEPARTEMENTALE :
				if(rue.estUneDepartementale())
					fenetre.getPanneauControles().ajouterRouteDansCombobox(listeRoutes[l].toString(),flag);
				break;
				
			default : break;
			}
		}
	}
	
	/**
	 * Fill the roads list
	 *
	 * @param filtre the filter
	 * @param flag the flag
	 */
	public void remplirListesRoutes(String filtre, PanelControls.jcbFlag flag) {
		Object[] listeRoutes = reseau_routier.getListeRoutes().toArray();
		Arrays.sort(listeRoutes);
	
		// Reinitialisation de la comboBox
		fenetre.getPanneauControles().viderRueComboBox(flag);
		
		// Insertion uniquement si la ville correspond
		for (int l = 0; l < reseau_routier.getNombreRoutes(); l++) {
			RoadName rue = new RoadName(listeRoutes[l]);
			if( rue.extraireNomVille().equals(filtre)) {
				fenetre.getPanneauControles().ajouterRouteDansCombobox(listeRoutes[l].toString(),flag);
			}
		}
	}
	
	/**
	 * Fill list points.
	 *
	 * @param flag the flag
	 */
	public void fillListPoints(PanelControls.jcbFlag flag) {
		String nom_route = fenetre.getPanneauControles().getNomRoute(flag);
		if (nom_route != null) {
			Vector<Integer> liste_points = reseau_routier.getRoute(nom_route).getPoints();
			fenetre.getPanneauControles().setPoints(flag, liste_points);
		}
	}
	
	// Selectionne le filtre a envoyer à la comboBox
	/**
	 * Select filter.
	 *
	 * @param flag the flag
	 */
	public void selectFilter(PanelControls.jcbFlag flag) {
		String selection = fenetre.getPanneauControles().getNomVille(flag);
		rueFlag rFlag = rueFlag.AUTRE;
		if(selection.equals(TOUTES))
			rFlag = rueFlag.ALL;
		else if(selection.equals(AUTOROUTE))
			rFlag = rueFlag.AUTOROUTE;
		else if(selection.equals(EUROPEENNE))
			rFlag = rueFlag.EUROPEENNE;
		else if(selection.equals(NATIONALE))
			rFlag = rueFlag.NATIONALE;
		else if(selection.equals(DEPARTEMENTALE))
			rFlag = rueFlag.DEPARTEMENTALE;
		
		if(rFlag == rueFlag.AUTRE)
			remplirListesRoutes(selection, flag);
		else	
			remplirListesRoutes(rFlag, flag);

	}
	
	/**
	 * Initialize the points lists
	 */
	private void initialiserListesPoints() {
		fillListPoints(PanelControls.jcbFlag.DEPART);
		fillListPoints(PanelControls.jcbFlag.ARRIVEE);
	}
	
	/**
	 * Display the map
	 */
	private void afficherCarte() {
		fenetre.getPanneauVue().getCarte().setScale(convertirUniteDistance(ECHELLE_TAILLE, pourcentage_zoom));
		fenetre.getPanneauVue().revalidate();
		recentrerVue(ptCentre);
	}
	
	/**
	 * Read combo box for itinerary search.
	 */
	public void readComboBoxForItinerarySearch() {
		// Lecture de la demande de l'utilisateur
		setDepart(fenetre.getPanneauControles().getNumPoint(PanelControls.jcbFlag.DEPART));
		setArrivee(fenetre.getPanneauControles().getNumPoint(PanelControls.jcbFlag.ARRIVEE));
		// Mise à jour de l'affichage
		chercherItineraire();
		repositionnerVue();
	}
	
	/**
	 * Search the itinerary
	 */
	private void chercherItineraire() {
		// Résoue l'itinéraire et ajoute les points à la carte
		fenetre.getPanneauVue().getCarte().viderPoints();
		if (depart == arrivee) {
			fenetre.getPanneauInfos().setMessage("Erreur !", "Veuillez choisir 2 points diff\u00e9rents !");
			setDepart(arrivee);
			setArrivee(-1);
			fenetre.getPanneauVue().getCarte().setTypePointUnique(true);
		}
		if ((depart >= 0) && (arrivee >= 0)) {
	    	chemin = plus_court_chemin.solve(depart, arrivee);
	    	ItineraryState pos;
	    	for (Iterator<ItineraryState> it = chemin.iterator(); it.hasNext();)
	    	{
	    		pos = (ItineraryState) it.next();
	    		fenetre.getPanneauVue().getCarte().ajouterPoint(plus_court_chemin.getNodeCoords(pos.n));
	    	}
	    	
	    	afficherListeRoutes();
		}
		else {
			if (depart >= 0) {
				fenetre.getPanneauVue().getCarte().ajouterPoint(plus_court_chemin.getNodeCoords(depart));
			}
			else if (arrivee >= 0) {
				fenetre.getPanneauVue().getCarte().ajouterPoint(plus_court_chemin.getNodeCoords(arrivee));
			}
		}
	}
	
	/**
	 * Display the roads list
	 */
	private void afficherListeRoutes() {
		fenetre.getPanneauInfos().reinitialiserInfos();
		
		// Erreur si chemin vide (non trouvé)
		if (chemin.isEmpty()) {
			fenetre.getPanneauInfos().setMessage("Erreur !", "Aucun chemin n'a pu etre trouv\u00e9 !");
		}
		else {
			// Remplissage de la feuille de route
			fenetre.getPanneauInfos().setMessage(null, null);
			String nomRoute = "", nomRoutePrec = "";
			ItineraryState pos = null;
			int lenRoute = 0;
			int lenTotale = 0;
			int idEdge;
			int numPt, numPtPrec = -1, numPtPrec2 = -1;
			String gaucheDroite;
			fenetre.getPanneauInfos().reinitialiserRoutes();
			for (Iterator<ItineraryState> it = chemin.iterator(); it.hasNext();) {
				pos = (ItineraryState) it.next();
				numPt = pos.n;
				if (numPtPrec >= 0) {
					idEdge = plus_court_chemin.findEdge(numPt, numPtPrec);
					if (idEdge >= 0) {
						nomRoute = plus_court_chemin.getEdgeName(idEdge);
						lenRoute += plus_court_chemin.getEdgeLength(idEdge);
						if ((!nomRoutePrec.equals(nomRoute)) || (!it.hasNext())) {
							if (numPtPrec2 != -1) {
								gaucheDroite = determinerGaucheDroite(numPtPrec2, numPtPrec, numPt);
							}
							else {
								gaucheDroite = "tout_droit";
							}
							fenetre.getPanneauInfos().ajouterRoute(nomRoute + " (" + convertirUniteDistance(lenRoute, 1) + ")", DOSSIER_IMAGES + "tourner_" + gaucheDroite + ".gif");
							lenTotale += lenRoute;
							lenRoute = 0;
						}
						nomRoutePrec = nomRoute;
					}
					else
						fenetre.getPanneauInfos().ajouterRoute("Erreur : Route non trouv\u00e9e ! (" + numPtPrec + "|" + numPt + ")");
				}
				numPtPrec2 = numPtPrec;
				numPtPrec = numPt;
			}
			
			// Mise en place des informations
			fenetre.getPanneauInfos().setLongueurTrajet(convertirUniteDistance(lenTotale, 1));
		}
	}
	
	/**
	 * Repositioning the view
	 */
	private void repositionnerVue() {
		if (chemin.size() > 1) {
			// Cherche le rectangle occupé
			int minx = 100000, miny = 100000, maxx = 0, maxy = 0;
			ItineraryState pos;
			Point pt;
			int x, y;
			for (Iterator<ItineraryState> it = chemin.iterator(); it.hasNext();) {
				pos = (ItineraryState) it.next();
				pt = reseau_routier.getPoint(pos.n);
				x = (int)pt.getX();
				y = (int)pt.getY();
				if (x < minx) minx = x;
				if (x > maxx) maxx = x;
				if (y < miny) miny = y;
				if (y > maxy) maxy = y;
			}
		
			// Calcul du nouveau zoom
			old_zoom = pourcentage_zoom;
			int largeur = maxx - minx;
			int hauteur = maxy - miny;
			Dimension taille_ecran = fenetre.getPanneauVue().getSize();
			double ratio_ecran = taille_ecran.getWidth() / taille_ecran.getHeight();
			int marge_dynamique;
			if (largeur >= ratio_ecran*hauteur) { // Largeur
				pourcentage_zoom = (float) (taille_ecran.getWidth() / largeur);
				marge_dynamique = 2*(int) (MARGE_REDIMENSIONNEMENT_AUTO/pourcentage_zoom);
				pourcentage_zoom = (float) (taille_ecran.getWidth() / (largeur + marge_dynamique));
			}
			else { // Hauteur
				pourcentage_zoom = (float) (taille_ecran.getHeight() / hauteur);
				marge_dynamique = 2*(int) (MARGE_REDIMENSIONNEMENT_AUTO/pourcentage_zoom);
				pourcentage_zoom = (float) (taille_ecran.getHeight() / (hauteur + marge_dynamique));
			}
			// Arrondissement et mise en place du zoom
			pourcentage_zoom = ((float) Math.round(pourcentage_zoom * 100)) / 100;
			setZoom();
			
			// Recentrage de la vue
			minx = (int)((minx * pourcentage_zoom) - (taille_ecran.getWidth() - largeur*pourcentage_zoom) / 2);
			miny = (int)((miny * pourcentage_zoom) - (taille_ecran.getHeight() - hauteur*pourcentage_zoom) / 2);
			minx = fenetre.getPanneauVue().resituerX(minx);
			miny = fenetre.getPanneauVue().resituerY(miny);
			
			fenetre.getPanneauVue().getViewport().setViewPosition(new Point(minx, miny));
		}
	}
	
	/**
	 * Move the map
	 *
	 * @param x the x
	 * @param y the y
	 */
	public void deplacerCarte(int x, int y) {
		fenetre.getPanneauVue().deplacerCarte(x, y);
		updateCentre();
	}
	
	//memorise le point centre de l'ecran.
	/**
	 * Update middle point.
	 */
	public void updateCentre() {
		//On conserve le point au centre de l'écran
		Dimension dim = fenetre.getPanneauVue().getViewport().getSize();
		Point coin = new Point(fenetre.getPanneauVue().getViewport().getViewPosition());
		ptCentre = new Point((int)((coin.getX()/pourcentage_zoom) + dim.getWidth()/(2*pourcentage_zoom)), (int)((coin.getY()/pourcentage_zoom) + dim.getHeight()/(2*pourcentage_zoom)));
	}

	//Positionne la vue sur le pt passé en param (generalement ptCentre)
	/**
	 * Recenter view.
	 *
	 * @param pt the point
	 */
	public void recentrerVue(Point pt) {
		//Mise en mémoire du point Centre
		ptCentre = pt;
		
		//centrage de la vue sur le point
		Dimension dim = fenetre.getPanneauVue().getViewport().getSize();
		
		int newX = (int)((float)pt.getX() * pourcentage_zoom) - (int)((float)dim.getWidth()/(float)2);
		int newY = (int) ((float)pt.getY() * pourcentage_zoom) - (int)((float)dim.getHeight()/(float)2);
		
		newX = fenetre.getPanneauVue().resituerX(newX);
		newY = fenetre.getPanneauVue().resituerY(newY);
		
		fenetre.getPanneauVue().getViewport().setViewPosition(new java.awt.Point(newX,newY));
	}
	
	/**
	 * Gets the zoom.
	 *
	 * @return the zoom
	 */
	public float getZoom() {
		return pourcentage_zoom;
	}
	
	/**
	 * Modify zoom.
	 *
	 * @param mod the mod
	 */
	public void modifyZoom(float mod) {
		// Modifie le zoom
		old_zoom = pourcentage_zoom;
		if (mod == RETOUR_ZOOM_INITIAL) {
			pourcentage_zoom = ZOOM_INITIAL;
		}
		else {
			pourcentage_zoom += mod;
		}
		setZoom();
	}
	
	/**
	 * Sets the zoom.
	 */
	private void setZoom() {
		if (pourcentage_zoom > ZOOM_MAX) pourcentage_zoom = ZOOM_MAX;
		if (pourcentage_zoom < ZOOM_MIN) pourcentage_zoom = ZOOM_MIN;
		if (old_zoom != pourcentage_zoom) {
			fenetre.getPanneauInfos().updateZoom(pourcentage_zoom);
			fenetre.getPanneauVue().getCarte().updateZoom(pourcentage_zoom);
			fenetre.getPanneauVue().getCarte().setMaxUnitIncrement(pourcentage_zoom);
			fenetre.getPanneauControles().setSliderValue((int) (pourcentage_zoom*100));
			plus_court_chemin.init(reseau_routier, pourcentage_zoom);
			afficherCarte();
			chercherItineraire();
			point_proche_souris = -1;
			fenetre.getPanneauVue().getCarte().setPointProche(new Point(-1, -1));
		}
	}
	
	/**
	 * Convert distance unit.
	 *
	 * @param px the pixel
	 * @param zoom the zoom
	 * @return the string
	 */
	private String convertirUniteDistance(double px, float zoom) {
		// Conversion dans l'unité de mesure
		String unite = "m";
		double m = (double)(px * (double)ECHELLE_CARTE * (double)((double)1 / (double)zoom));
		if (m > 1000) {
			m /= 1000;
			unite = "km";
		}
		// Arrondissement à 2 chiffres après la virgule
		m = ((double) Math.round(m * 100)) / 100;
		return new String(m + " " + unite);
	}
	
	/**
	 * Modify the view cursor
	 *
	 * @param curseur the cursor
	 */
	public void modifierCurseurVue(int curseur) {
		fenetre.getPanneauVue().getCarte().setCursor(new Cursor(curseur));
	}
	
	/**
	 * Choose the color
	 */
	public void choixCouleur() {
		Color newColor = JColorChooser.showDialog(
                fenetre, "Choisissez la nouvelle couleur du trac\u00e9",
                fenetre.getPanneauVue().getCarte().getItineraireCouleur());
		if (newColor != null){
			fenetre.getPanneauVue().getCarte().setItineraireCouleur(newColor);
		}
		chercherItineraire();
		
	}

	/**
	 * Update coordonates
	 *
	 * @param X the x
	 * @param Y the y
	 */
	public void updateCoord(int X, int Y) 
	{
		Point pointSouris = new Point(X,Y);
		String nom_route;
		Integer num_point;
		Vector<Integer> points_route;
		@SuppressWarnings("rawtypes")
		Iterator it_routes;
		@SuppressWarnings("rawtypes")
		Iterator it_points;

        // Affichage du point le plus proche sur la carte
		Point pointProchePrecedent = fenetre.getPanneauVue().getCarte().getPointProche();
		int idPointProche = plus_court_chemin.cherchePointProche(pointSouris);
		Point pointProche = plus_court_chemin.getNodeCoords(idPointProche);
		
		
		if (!pointProchePrecedent.equals(pointProche)) 
		{
			// On ne redessine que si le point change
			point_proche_souris = idPointProche;
			fenetre.getPanneauVue().getCarte().hideMenu();
			fenetre.getPanneauVue().getCarte().setPointProche(pointProche);
			
			// Affichage des coordonnees du point
			Point nouvelles_coords = getLambertCoords(reseau_routier.getPoint(idPointProche));
			fenetre.getPanneauVue().getCarte().ajouterDonneeAuPointProche("Id point: "+new Integer(idPointProche).toString());
			fenetre.getPanneauVue().getCarte().ajouterDonneeAuPointProche("Coordonn\u00e9es : (" + (int)nouvelles_coords.getX() + "," + (int)nouvelles_coords.getY() + ")");
			
			// Recherche des routes du point
			@SuppressWarnings("rawtypes")
			Set liste_routes = reseau_routier.getListeRoutes();
						
			for (it_routes = liste_routes.iterator(); it_routes.hasNext();) {
				nom_route = (String) it_routes.next();
				points_route = reseau_routier.getRoute(nom_route).getPoints();
				for (it_points = points_route.iterator(); it_points.hasNext();) {
					num_point = (Integer) it_points.next();
					if (num_point == idPointProche) {
						fenetre.getPanneauVue().getCarte().ajouterDonneeAuPointProche("Route : " + nom_route);
					}
				}
			}
			fenetre.getPanneauVue().revalidate();
			fenetre.repaint();
		}
		
		// Rafraichissement des informations
		Point pt_lambert = getLambertCoords(new Point((int) (X / pourcentage_zoom), (int) (Y / pourcentage_zoom)));
		fenetre.getPanneauInfos().updateCoord((int)pt_lambert.getX(), (int)pt_lambert.getY(), idPointProche);
	}
	
	/**
	 * Display the context menu.
	 */
	public void afficherMenuContextuel() {
		fenetre.getPanneauVue().getCarte().afficherMenu();
	}
	
	/**
	 * Sets the nearest point as start.
	 */
	public void setNearestPointAsStart() {
		setDepart(point_proche_souris);
		fenetre.getPanneauVue().getCarte().setTypePointUnique(true);
		chercherItineraire();
	}	
	
	/**
	 * Sets the nearest point as arrival.
	 */
	public void setNearestPointAsArrival() {
		setArrivee(point_proche_souris);
		fenetre.getPanneauVue().getCarte().setTypePointUnique(false);
		chercherItineraire();
	}
	
	/**
	 * Hide the map menu.
	 */
	public void cacherMenuCarte() {
		fenetre.getPanneauVue().getCarte().hideMenu();
	}
	
	/**
	 * Gets the lambert coords.
	 *
	 * @param pt_pixels the pt_pixels
	 * @return the lambert coords
	 */
	private Point getLambertCoords(Point pt_pixels) {
		int etendue_x = (int) (LAMBERT_BAS_DROITE.getX() - LAMBERT_HAUT_GAUCHE.getX());
		int etendue_y = (int) (LAMBERT_BAS_DROITE.getY() - LAMBERT_HAUT_GAUCHE.getY());
		double lambert_zero_x = pt_pixels.getX() * etendue_x / PIXELS_BAS_DROITE.getX();
		double lambert_zero_y = pt_pixels.getY() * etendue_y / PIXELS_BAS_DROITE.getY();
		int x = (int) (LAMBERT_HAUT_GAUCHE.getX() + lambert_zero_x);
		int y = (int) (LAMBERT_HAUT_GAUCHE.getY() + lambert_zero_y);
		return new Point(x, y);
	}
	
	/**
	 * Determine left right.
	 *
	 * @param id1 the id1
	 * @param id2 the id2
	 * @param id3 the id3
	 * @return the string
	 */
	private String determinerGaucheDroite(int id1, int id2, int id3) {
		Point p1 = reseau_routier.getPoint(id1);
		Point p2 = reseau_routier.getPoint(id2);
		Point p3 = reseau_routier.getPoint(id3);

		//déterminer l'angle entre les deux droites
		
		//clacul de l'angle du précédent arc par rapport à l'origine
		double angle1 = (atan2((p2.getY()-p1.getY()),(p2.getX()-p1.getX())));
		
		//calcul de l'angle de l'arc deux fois précédent par rapport a l'origine
		double angle2 = (atan2((p3.getY()-p1.getY()),(p3.getX()-p1.getX())));
		
		//soustraction de l'un par rapport à l'autre pour avoir leur angle relatif
		double angle = angle2-angle1;
		
		if(sin(angle)<-0.1)
			return "gauche";
		else if (sin(angle)>0.1)
			return "droite";
		else
			return "tout_droit";
	}
	
	/**
	 * Modify the anti-aliasing.
	 */
	public void changeAntiAliasing() {
		fenetre.getPanneauVue().getCarte().changerAntiAliasing();
	}
	
	/**
	 * Sets the depart.
	 *
	 * @param d the new departure
	 */
	private void setDepart(int d) {
		depart = d;
		fenetre.getPanneauInfos().updateDepart(depart);
	}
	
	/**
	 * Sets the arrivee.
	 *
	 * @param a the new arrival
	 */
	private void setArrivee(int a) {
		arrivee = a;
		fenetre.getPanneauInfos().updateArrivee(arrivee);
	}

	/**
	 * Determine the global zoom.
	 */
	public void determinerZoomGlobal() {
		float i=ZOOM_MAX;
		int largeurMin =(int) fenetre.getPanneauVue().getViewport().getViewSize().getWidth();
		int hauteurMin =(int) fenetre.getPanneauVue().getViewport().getViewSize().getHeight();
		int largeurCarte = fenetre.getPanneauVue().getCarte().getLargeur();
		int hauteurCarte = fenetre.getPanneauVue().getCarte().getHauteur();
		//tant que la carte ne rentre pas en entier dans le ViewPort, on diminue le zoom
		while(i>ZOOM_MIN || ( (float)(largeurCarte * i) > (float)largeurMin && (float)(hauteurCarte * i) > (float)hauteurMin) )
			i-=0.01;
		pourcentage_zoom = i;
		
		setZoom();
	}
	
	/**
	 * Save export.
	 */
	public void saveExport(){

			try{
				
				JFileChooser jFileChooser = new JFileChooser();
				jFileChooser.setSelectedFile(new File("fileToSave.txt"));
				 int retrival = jFileChooser.showSaveDialog(fenetre);
				 if (retrival == JFileChooser.APPROVE_OPTION) {
					try 
						{
						FileWriter fw = new FileWriter(jFileChooser.getSelectedFile()+".txt");
						
						for(String s : fenetre.getPanneauInfos().getItinerary())
						{
							fw.write(s);
							fw.write(System.getProperty("line.separator"));
						}
						
						fw.close();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}				
			catch(Exception ex)
			{}
	}
	
	/**
	 * About.
	 */
	public void about(){
		aboutWindow.setVisible(true);
		
	}
	
	/**
	 * Close.
	 */
	public void close(){
		fenetre.dispose();
	}
}

	
