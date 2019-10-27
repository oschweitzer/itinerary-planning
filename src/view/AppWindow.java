package view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;


/**
 * The Class AppWindow.
 */
@SuppressWarnings("serial")
public class AppWindow extends JFrame{

	//couleur du tracé ( commun a Carte et Contrôle )
	/** The Constant DefaultItineraireColor. */
	public final static Color DefaultItineraireColor = Color.GREEN;
	
	/** The control panel height */
	private final int CONTROLES_HAUTEUR = 150;
	
	/** The information panel width */
	private final int INFOS_LARGEUR = 250;
	
	// Dimensions de l'écran
	/** The screen dimensions. */
	private Dimension dimEcran;
    
    // Dimensions utilisables du bureau
    /** The usable width */
    private int largeurUtil;
    
    /** The usable height */
    private int hauteurUtil;
	
	// Pannels
	/** The view panel */
	private PanelView pnlVue;
	
	/** The control panel */
	private PanelControls pnlCtrl;
	
	/** The information panel */
	private PanelInformations pnlInfo;
	
	/** The menu bar. */
	private MenuBar menuBar;
	
	
	// Bordure des Panels
	/** The raised bevel. */
	Border raisedbevel = BorderFactory.createRaisedBevelBorder();
	
	/** The lowered bevel. */
	Border loweredbevel = BorderFactory.createLoweredBevelBorder();
	
	/** The coumpound border. */
	Border coumpoundBorder = BorderFactory.createCompoundBorder(raisedbevel, loweredbevel);
	
	/**
	 * Instantiates a new app window.
	 *
	 * @param lienCarte the map link
	 * @param su the unit system
	 */
	public AppWindow(String lienCarte, String su) {	
		super("Itinerary planner");
		
		// Recuperer l'apparence par defaut du systeme
		String nativeLF = UIManager.getSystemLookAndFeelClassName();
		
		// Installation de l'apparence
		try {
			UIManager.setLookAndFeel(nativeLF);
		} 
		catch (InstantiationException e) {}
		catch (ClassNotFoundException e) {}
		catch (UnsupportedLookAndFeelException e) {}
		catch (IllegalAccessException e) {}
		
		// Recuperation de la taille de l'ecran
        Toolkit tk = Toolkit.getDefaultToolkit();
        dimEcran = tk.getScreenSize();
        
        // Recuperation des rebords
        Insets insets = tk.getScreenInsets(getGraphicsConfiguration()); 
        
        // Calcul de la taille utilisable sur le bureau
        largeurUtil = (int)(dimEcran.getWidth()-insets.left-insets.right); 
        hauteurUtil = (int)(dimEcran.getHeight()-insets.top-insets.bottom); 
        setPreferredSize(new Dimension(largeurUtil,hauteurUtil));
        
        int h,l;
        
		//Creation of the menu bar
		menuBar = new MenuBar();
		setJMenuBar(menuBar);
		
		//Temp
		//MenuControler menuControler = new MenuControler();
		//fileExportItinerary.addActionListener(menuControler);
		
		// Création du PanelControles
        h = CONTROLES_HAUTEUR;
        l = largeurUtil;
		pnlCtrl = new PanelControls();
		pnlCtrl.setPreferredSize(new Dimension(l, h));
		getContentPane().add(pnlCtrl ,BorderLayout.NORTH);
		
		// Création de la carte
		Map carte = new Map(lienCarte, 40);
		
		// Création du PanelVue avec la carte
		h = hauteurUtil - CONTROLES_HAUTEUR;
		l = largeurUtil - INFOS_LARGEUR;
		pnlVue = new PanelView(carte);
		pnlVue.setPreferredSize(new Dimension(l, h));
		pnlVue.setBorder(coumpoundBorder);
		getContentPane().add(pnlVue, BorderLayout.CENTER);
		
		// Création du PanelInformations
		l = INFOS_LARGEUR;
		h = hauteurUtil;
		pnlInfo = new PanelInformations(l, h, su);
		pnlInfo.setPreferredSize(new Dimension(l,h));
		getContentPane().add(pnlInfo ,BorderLayout.EAST);
		
		// Mise à la taille du bureau pour le mode fenêtre normal
		pack();
		setLocation(insets.left, insets.top);
		
		// Mise en mode fenetre aggrandie
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        // Opération de fermeture
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * Gets the Control panel.
	 *
	 * @return the control panel
	 */
	public PanelControls getPanneauControles() {
		return pnlCtrl;
	}

	/**
	 * Gets the Information panel
	 *
	 * @return the information panel
	 */
	public PanelInformations getPanneauInfos() {
		return pnlInfo;
	}

	/**
	 * Gets the view panel.
	 *
	 * @return the view panel
	 */
	public PanelView getPanneauVue() {
		return pnlVue;
	}
	
	/**
	 * Gets the menu bar items.
	 *
	 * @return the menu bar items
	 */
	public JMenuItem[] getMenuBarItems(){
		return menuBar.getItems();
	}
	
}
