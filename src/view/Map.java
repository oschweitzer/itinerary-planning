/*
 * Class Carte :
 * Dérivée de ImageScrollable (pour l'image)
 * Contient et dessine l'itinï¿½raire
 */

package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import model.Application;
import controller.MapController;

/**
 * The Class Map.
 */
@SuppressWarnings("serial")
public class Map extends ImageScrollable
{
	
	//Image représentant la carte
	/** The map image. */
	Image imgCarte;
	
	/** The image width. */
	int imgLargeur;
	
	/** The image height. */
	int imgHauteur;
	
	/** The affine transform. */
	AffineTransform affineTransform;
	
	/** The zoom percentage. */
	private float pourcentage_zoom;
	
	// Constantes de l'itinéraire
	/** The itinerary outline thickness. */
	private final int itineraire_outline_epaisseur = 4;
    
    /** The itinerary thickness. */
    private final int itineraire_epaisseur = 5;
    
    /** The itinerary departure color. */
    private final Color itineraire_couleur_depart = Color.GREEN;
    
    /** The itinerary arrival color. */
    private final Color itineraire_couleur_arrivee = Color.RED;
    
    /** The itineraire_epaisseur_points. */
    private final int itineraire_epaisseur_points = 20;
    
    /** The itinerarycolor scale. */
    private final Color itineraire_echelle_couleur = Color.BLACK;
    
    /** The itinerary departure x scale */
    private final int itineraire_echelle_depart_x = 10;
    
    /** The itinerary departure y scale */
    private final int itineraire_echelle_depart_y = 20;
    
    /** The itinerary scale thickness. */
    private final int itineraire_echelle_epaisseur = 4;
    
    /** The departure letter. */
    private final String lettre_depart = "S";
    
    /** The arrival letter. */
    private final String lettre_arrivee = "A";
    
    /** The letters color */
    private final Color lettres_couleur = Color.WHITE;
    
    /** The letters gap. */
    private final int lettres_decalage = 3;
    
    // Données de l'itinéraire
    /** The itinerary. */
    private Vector<Point> itineraire;
    
    /** The itinerary scale */
    private String itineraire_echelle = "";
    
    /** The itinerary scale size in pixel. */
    private int itineraire_echelle_taille_px = 10;
	
	/** The itinerary outline color. */
	private Color itineraire_couleur_outline = Color.WHITE;
    
    /** The itinerary color */
    private Color itineraire_couleur = Color.BLUE;
    
    /** Is itinerary unique point a departure point. */
    private boolean itineraire_point_unique_est_point_depart = true;
    
    // Données du point le plus proche
    /** The closest point color. */
    private final Color point_proche_couleur = Color.BLACK;
    
    /** The closest point size. */
    private final int point_proche_taille = 10;
    
    /** The x departure data. */
    private final int donnees_depart_x = 15;
    
    /** The y departure data. */
    private final int donnees_depart_y = 0;
    
    /** The y gap data. */
    private final int donnees_decalage_y = 15;
    
    /** The closest point data color. */
    private final Color point_proche_donnees_couleur = Color.BLACK;
    
    /** The background rectangle color. */
    private final Color rectangle_couleur_fond = new Color(185,211,238);
    
    /** The rectangle width. */
    private final int rectangle_largeur = 250;
    
    /** The rectangle padding. */
    private final int rectangle_marge_interne = 10;
    
    /** The closest point. */
    private Point point_proche;
    
    /** The closest point data. */
    private Vector<String> point_proche_donnees;
    
    /** Are closest point data displayed. */
    private boolean point_proche_donnees_afficher = true;
    
    // Menu contextuel
    /** The x departure menu */
    private final int menu_depart_x = 5;
    
    /** The y departure menu */
    private final int menu_depart_y = 30;
	
	/** The menu. */
	private JPopupMenu menu;
	
	/** The departure choice */
	private JMenuItem choix_depart;
	
	/** The arrival choice */
	private JMenuItem choix_arrivee;
	
	/** The anti-aliasing choice */
	private JCheckBoxMenuItem choix_anti_aliasing;
	
	/** The anti_aliasing. */
	private boolean anti_aliasing = true;
	
	/** The itinerary color choice. */
	private JMenuItem choix_itineraire_couleur;
    
	/**
	 * Instantiates a new map.
	 *
	 * @param imgPath the image path
	 * @param m the m
	 */
	public Map(String imgPath, int m) {
		super(m);
		imgCarte = Toolkit.getDefaultToolkit().getImage(imgPath);
		
		init();
	}
	
	/**
	 * Initialization
	 */
	private void init(){
		imgLargeur = imgCarte.getWidth(null);
		imgHauteur = imgCarte.getHeight(null);
		updateZoom(Application.ZOOM_INITIAL);
		
		itineraire = new Vector<Point>();
		point_proche = new Point(-1, -1);
		point_proche_donnees = new Vector<String>();
		
		setBackground(Color.white);
		setOpaque(true);
		
		// Initialisation du menu
		menu = new JPopupMenu();
		
		// Option "chosir comme point de départ"
		BufferedImage dessin_depart = new BufferedImage(15, 15, BufferedImage.TYPE_INT_RGB);
		Graphics2D g21 = dessin_depart.createGraphics();
		g21.setColor(itineraire_couleur_depart);
		g21.fillRect(0, 0, 15, 15);
		ImageIcon icone_depart = new ImageIcon(dessin_depart);
		choix_depart = new JMenuItem("Choisir comme point de d\u00e9part", icone_depart);
		choix_depart.setName("choix_depart");
		menu.add(choix_depart);
		
		// Option "chosir comme point de destination"
		BufferedImage dessin_arrivee = new BufferedImage(15, 15, BufferedImage.TYPE_INT_RGB);
		Graphics2D g22 = dessin_arrivee.createGraphics();
		g22.setColor(itineraire_couleur_arrivee);
		g22.fillRect(0, 0, 15, 15);
		ImageIcon icone_arrivee = new ImageIcon(dessin_arrivee);
		choix_arrivee = new JMenuItem("Choisir comme point de destination", icone_arrivee);
		choix_arrivee.setName("choix_arrivee");
		menu.add(choix_arrivee);
		
		menu.addSeparator();
		
		// Option "Changer la couleur du tracé"
		choix_itineraire_couleur = new JMenuItem("Changer la couleur du trac\u00e9...");
		choix_itineraire_couleur.setName("choix_itineraire_couleur");
		menu.add(choix_itineraire_couleur);
		setItineraireCouleur(itineraire_couleur);
		
		// Option "Filtre anti-aliasing"
		choix_anti_aliasing = new JCheckBoxMenuItem("Filtre anticr\u00e8nelage");
		choix_anti_aliasing.setSelected(true);
		choix_anti_aliasing.setName("choix_anti_aliasing");
		menu.add(choix_anti_aliasing);
		
		// Ajout du menu à la carte
		add(menu);
		
		// Initialisation de l'anti-aliasing
		anti_aliasing = !anti_aliasing;
		changerAntiAliasing();
	}
    
    /**
     * Update zoom.
     *
     * @param zoom the zoom
     */
    public void updateZoom(float zoom) {
    	pourcentage_zoom = zoom;
    	super.setMaxUnitIncrement(zoom);
    	affineTransform = new AffineTransform();
    	affineTransform.scale(pourcentage_zoom, pourcentage_zoom);
    	int larg = (int)(imgLargeur * pourcentage_zoom);
    	int haut = (int)(imgHauteur * pourcentage_zoom);
    	setSize(new Dimension(larg, haut));
		setBorder(BorderFactory.createEmptyBorder(haut, larg, 0, 0));
    }
    
    /**
     * Add map listeners
     *
     * @param ecouteur the listener
     */
    public void ajouterEcouteurCarte(MapController ecouteur) {
	    addMouseMotionListener(ecouteur); //gere le drag de souris
		addMouseListener(ecouteur); //gere les changements de forme du pointeur
		addMouseWheelListener(ecouteur); //gere la molette
    }
    
    /**
     * Add menu listeners
     *
     * @param a the listener
     */
    public void ajouterEcouteurMenu(ActionListener a) {
    	choix_depart.addActionListener(a);
    	choix_arrivee.addActionListener(a);
    	choix_anti_aliasing.addActionListener(a);
    	choix_itineraire_couleur.addActionListener(a);
    }
    
    /**
     * Add point.
     *
     * @param pt the point
     */
    public void ajouterPoint(Point pt) {
    	itineraire.add(pt);
    }
    
    /**
     * Remove points
     */
    public void viderPoints() {
    	itineraire.removeAllElements();
    }
    
    /**
     * Display menu.
     */
    public void afficherMenu() {
    	if ((point_proche.getX() != -1) && (point_proche.getY() != -1)) {
	    	Point pt = new Point(point_proche);
	    	// Annulation du déplacement de la carte
	    	pt.translate((int) -getVisibleRect().getX() + menu_depart_x, (int) -getVisibleRect().getY() + menu_depart_y);
	    	menu.setLocation(pt);
	    	menu.setVisible(true);
	    	point_proche_donnees_afficher = false;
	    	repaint();
    	}
    }
    
    /**
     * Hide menu.
     */
    public void hideMenu() {
    	menu.setVisible(false);
    	point_proche_donnees_afficher = true;
    	repaint();
    }
    
    /**
     * Sets the scale size.
     *
     * @param taille the new scale size
     */
    public void setScaleSize(int taille) {
    	itineraire_echelle_taille_px = taille;
    }
    
    /**
     * Sets the scale.
     *
     * @param echelle the new scale
     */
    public void setScale(String echelle) {
    	itineraire_echelle = echelle;
    }
    
    /* (non-Javadoc)
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    synchronized public void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	Graphics2D g2 = (Graphics2D) g;
    	g2.setColor(getBackground());

    	g2.drawImage(imgCarte, affineTransform, null);
    	
    	// Traçage de l'échelle
    	g2.setColor(itineraire_echelle_couleur);
    	g2.setStroke(new BasicStroke(itineraire_echelle_epaisseur));
    	int depart_x = ((int) getVisibleRect().getMinX()) + itineraire_echelle_depart_x;
    	int depart_y = ((int) getVisibleRect().getMinY()) + itineraire_echelle_depart_y;
    	g2.drawString(itineraire_echelle, depart_x, depart_y - 5);
    	g2.drawLine(depart_x, depart_y, depart_x + itineraire_echelle_taille_px * 2, depart_y);
    	g2.drawLine(depart_x, depart_y - 2, depart_x, depart_y + 2);
    	g2.drawLine(depart_x + itineraire_echelle_taille_px, depart_y - 2, depart_x + itineraire_echelle_taille_px, depart_y + 2);
    	g2.drawLine(depart_x + itineraire_echelle_taille_px*2, depart_y - 2, depart_x + itineraire_echelle_taille_px*2, depart_y + 2);
    	
    	// Mise en place d'un anti-aliasing
    	if (anti_aliasing) {
    		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    	}
    	
    	// Traçage de l'itinéraire
    	if (itineraire.size() > 1) {
    		Point pt_depart = new Point();
	    	Point pt = new Point(0, 0);
	    	Point prec = new Point(-1, -1);
	    	g2.setColor(itineraire_couleur);
	    	g2.setStroke(new BasicStroke(itineraire_epaisseur));
	    	
	    	for (@SuppressWarnings("rawtypes")
			Iterator it = itineraire.iterator(); it.hasNext();) {
	    		pt = (Point) it.next();
	    		if (prec.getX() == -1) {
	    			// Premier point
	    			pt_depart = new Point(pt);
	    		}
	    		else {
	    			// Traçage de la droite eliant les 2 points
	    			g2.drawLine((int)prec.getX(), (int)prec.getY(), (int)pt.getX(), (int)pt.getY());
	    		}
	    		prec.setLocation(pt);
	    	}
	    	
	    	// Premier point : traçage du point de départ
	    	tracePointDepart(g2, pt_depart);
	    	
	    	// Dernier point : traçage du point d'arrivée
			tracePointArrivee(g2, pt);
    	}
    	else if (itineraire.size() == 1) {
    		if (itineraire_point_unique_est_point_depart) {
    			tracePointDepart(g2, itineraire.get(0));
    		}
    		else {
    			tracePointArrivee(g2, itineraire.get(0));
    		}
    	}
    
    	// Traçage du point le plus proche de la souris
    	if ((point_proche.getX() != -1) && (point_proche.getY() != -1))
    	{
    		tracePoint(g2, point_proche, point_proche_couleur, point_proche_taille);
        	// Tracage des donnees du point le plus proche de la souris
    		
        	if (point_proche_donnees_afficher)
        	{
        		int x = (int)point_proche.getX();
        		int y = (int)point_proche.getY();
        		
    	    	// Rectangle
        		g2.setColor(Color.BLACK);
        		g2.drawRect(x + rectangle_marge_interne, y - rectangle_marge_interne, rectangle_largeur, donnees_decalage_y * point_proche_donnees.size() + rectangle_marge_interne);
        		g2.setColor(rectangle_couleur_fond);
        		g2.fillRect(x + rectangle_marge_interne, y - rectangle_marge_interne, rectangle_largeur, donnees_decalage_y * point_proche_donnees.size() + rectangle_marge_interne);
        		
        		// Contenu
        		String donnee;
    	    	int donnees_x = x + donnees_depart_x;
    	    	int donnees_y = y + donnees_depart_y;
    	    	g2.setColor(point_proche_donnees_couleur);
    	    	g2.setFont(g2.getFont().deriveFont(Font.BOLD));
    	    	for (@SuppressWarnings("rawtypes")
				Iterator it_donnees = point_proche_donnees.iterator(); it_donnees.hasNext();) {
    	    		donnee = (String) it_donnees.next();
    	    		g2.drawString(donnee, donnees_x, donnees_y);
    	    		donnees_y += donnees_decalage_y;
    	    	}
        	}
	    }
    }
    
    /**
     * Draw the departure point.
     *
     * @param g2 the graphic
     * @param pt the point
     */
    private void tracePointDepart(Graphics2D g2, Point pt) {
    	tracePointDepartOuArrivee(g2, pt, itineraire_couleur_depart, lettre_depart);
    }
    
    /**
     * Draw the arrival point.
     *
     * @param g2 the graphic
     * @param pt the point
     */
    private void tracePointArrivee(Graphics2D g2, Point pt) {
    	tracePointDepartOuArrivee(g2, pt, itineraire_couleur_arrivee, lettre_arrivee);
    }
    
    /**
     * Draw the departure or arrival point.
     *
     * @param g2 the graphic
     * @param pt the point
     * @param couleur the color
     * @param lettre the letter
     */
    private void tracePointDepartOuArrivee(Graphics2D g2, Point pt, Color couleur, String lettre) {
    	Color couleur_precedente = g2.getColor();
    	tracePoint(g2, pt, couleur, itineraire_epaisseur_points);
    	g2.setColor(lettres_couleur);
    	g2.drawString(lettre, (int)pt.getX() - lettres_decalage, (int)pt.getY() + lettres_decalage);
    	g2.setColor(couleur_precedente);
    }
    
	/**
	 * Draw point.
	 *
	 * @param g2 the graphic
	 * @param pt the point
	 * @param c the color
	 * @param taille the size
	 */
	public void tracePoint(Graphics2D g2, Point pt, Color c, int taille) {
		Color couleur_precedente = g2.getColor();
		g2.setColor(c);
		g2.fillOval((int)pt.getX() - taille/2, (int)pt.getY() - taille/2, taille, taille);
		g2.setColor(couleur_precedente);
	}

	/**
	 * Gets the itinerary color.
	 *
	 * @return the itinerary color
	 */
	public Color getItineraireCouleur() {
		return itineraire_couleur;
	}

	/**
	 * Sets the itinerary color.
	 *
	 * @param couleur the new itinerary color
	 */
	public void setItineraireCouleur(Color couleur) {
		itineraire_couleur = couleur;
		BufferedImage dessin_couleur = new BufferedImage(15, 15, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = dessin_couleur.createGraphics();
		g2.setColor(itineraire_couleur);
		g2.fillRect(0, 0, 15, 15);
		ImageIcon icone_couleur = new ImageIcon(dessin_couleur);
		choix_itineraire_couleur.setIcon(icone_couleur);
	}
	
	/**
	 * Sets the closest point.
	 *
	 * @param pt the new closest point
	 */
	public void setPointProche(Point pt) {
		point_proche = pt;
		point_proche_donnees.removeAllElements();
	}
	
	/**
	 * Gets the closest point.
	 *
	 * @return the closest point
	 */
	public Point getPointProche() {
		return point_proche;
	}
	
	/**
	 * Add closest point data.
	 *
	 * @param route the road
	 */
	public void ajouterDonneeAuPointProche(String route) {
		point_proche_donnees.add(route);
	}

	/**
	 * Modify anti aliasing.
	 */
	public void changerAntiAliasing() {
		anti_aliasing = !anti_aliasing;
		choix_anti_aliasing.setSelected(anti_aliasing);
		repaint();
	}
	
	/**
	 * Sets the unique point type.
	 *
	 * @param est_depart the new unique point type
	 */
	public void setTypePointUnique(boolean est_depart) {
		itineraire_point_unique_est_point_depart = est_depart;
	}

	/**
	 * Gets the height.
	 *
	 * @return the image height
	 */
	public int getHauteur() {
		return imgHauteur;
	}

	/**
	 * Gets the width.
	 *
	 * @return the image width
	 */
	public int getLargeur() {
		return imgLargeur;
	}

	/**
	 * Gets the itinerary outline color.
	 *
	 * @return the itinerary outline color
	 */
	public Color getItineraire_couleur_outline() {
		return itineraire_couleur_outline;
	}

	/**
	 * Sets the itinerary outline color.
	 *
	 * @param est_depart the new itinerary outline color
	 */
	public void setItineraire_couleur_outline(Color itineraire_couleur_outline) {
		this.itineraire_couleur_outline = itineraire_couleur_outline;
	}

	/**
	 * Gets the itinerary outline thickness.
	 *
	 * @return the itinerary outline thickness
	 */
	public int getItineraire_outline_epaisseur() {
		return itineraire_outline_epaisseur;
	}
}
