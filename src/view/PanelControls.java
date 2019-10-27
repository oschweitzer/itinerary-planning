package view;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.*;
import javax.swing.event.ChangeListener;

import model.Application;

import org.jdesktop.swingx.autocomplete.*;

/**
 * The Class PanelControls.
 */
@SuppressWarnings("serial")
public class PanelControls extends JPanel {
	
	/** The HAUTEUR. */
	private final int HAUTEUR = 150;
	
	/** The JLabel departure. */
	private JLabel jlDepart;
	
	/** The JLabel arrival. */
	private JLabel jlArrivee;
	
	/** The JLabel town. */
	private JLabel jlVille;
	
	/** The JLabel street. */
	private JLabel jlRue;
	
	/** The JLabel point. */
	private JLabel jlPoint;
	
	/** The JComboBox departure town. */
	@SuppressWarnings("rawtypes")
	private JComboBox jcbVilleDepart;
	
	/** The JComboBox arrival town. */
	@SuppressWarnings("rawtypes")
	private JComboBox jcbVilleArrivee;
	
	/**
	 * The Enum jcbFlag.
	 */
	public enum jcbFlag {
		/** The DEPART. */
		DEPART, 
		 /** The ARRIVEE. */
		 ARRIVEE, 
		 /** The BOTH. */
		 BOTH};
	
	/** The JComboBox departure street. */
	@SuppressWarnings("rawtypes")
	private JComboBox jcbRueDepart;
	
	/** The JComboBox arrival street. */
	@SuppressWarnings("rawtypes")
	private JComboBox jcbRueArrivee;
	
	/** The JComboBox departure point. */
	@SuppressWarnings("rawtypes")
	private JComboBox jcbPointDepart;
	
	/** The JComboBox arrival point. */
	@SuppressWarnings("rawtypes")
	private JComboBox jcbPointArrivee;
	
	/** The JButton ok. */
	private JButton jbOk;
	
	/** The JLabel zoom. */
	private JLabel jlZoom;
	
	/** The JSlider zoom. */
	private JSlider jsZoom;
	
	/** The JButton plus zoom. */
	private JButton jbZoomPlus;
	
	/** The JButton minus zoom. */
	private JButton jbZoomMoins;
	
	/** The JButton global zoom. */
	private JButton jbZoomGlobal;
	
	/** The JButton close-up zoom. */
	private JButton jbZoomGrosPlan;
	
	/** The JButton real zoom. */
	private JButton jbZoomReel;
	
	/** The JPanel east container. */
	private JPanel jpConteneurEst;
	
	/** The JPanel west container. */
	private JPanel jpConteneurOuest;
	
	/** The constraints. */
	private GridBagConstraints contraintes;

	/**
	 * Instantiates a new panel controls.
	 */
	@SuppressWarnings("rawtypes")
	public PanelControls() 
	{
		super();
		
		//Creation du Panel Ouest
		jpConteneurOuest = new JPanel(new GridBagLayout());
		jpConteneurOuest.setMaximumSize(new Dimension(600, HAUTEUR));
		
		// Creation des contraintes communes
		contraintes = new GridBagConstraints();
		contraintes.insets = new Insets(5, 5, 5, 5);
		contraintes.anchor = GridBagConstraints.LINE_START;
		contraintes.weightx = 0.0;
		
		// Ajout du label Ville
		jlVille = new JLabel("Ville :");
		jlVille.setFont(jlVille.getFont().deriveFont(Font.BOLD));
		contraintes.gridx = 0;
		contraintes.gridy = 1;
		jpConteneurOuest.add(jlVille, contraintes);
		
		// Ajout du label Rue
		jlRue = new JLabel("Rue :");
		jlRue.setFont(jlVille.getFont().deriveFont(Font.BOLD));
		contraintes.gridx = 0;
		contraintes.gridy = 2;
		jpConteneurOuest.add(jlRue, contraintes);
		
		// Ajout du label Points
		jlPoint = new JLabel("Point :");
		jlPoint.setFont(jlPoint.getFont().deriveFont(Font.BOLD));
		contraintes.gridx = 0;
		contraintes.gridy = 3;
		jpConteneurOuest.add(jlPoint, contraintes);
		
		// Contrainte pour les autres composants
		contraintes.anchor = GridBagConstraints.CENTER;
		
		// Ajout du label Depart
		jlDepart = new JLabel("D\u00e9part :");
		jlDepart.setFont(jlDepart.getFont().deriveFont(Font.BOLD));
		contraintes.gridx = 1;
		contraintes.gridy = 0;
		jpConteneurOuest.add(jlDepart, contraintes);
		
		// Ajout de la ComboBox de la ville départ
		jcbVilleDepart = new JComboBox();
		jcbVilleDepart.setName("jcbVilleDepart");
		jcbVilleDepart.setPreferredSize(new Dimension(175,20));
		jcbVilleDepart.setEditable(true);
		
		contraintes.gridx = 1;
		contraintes.gridy = 1;
		jpConteneurOuest.add(jcbVilleDepart, contraintes);
		
		// Ajout de la ComboBox de la rue de départ
		jcbRueDepart = new JComboBox();
		jcbRueDepart.setName("jcbRueDepart");
		jcbRueDepart.setPreferredSize(new Dimension(175,20));
		contraintes.gridx = 1;
		contraintes.gridy = 2;
		jpConteneurOuest.add(jcbRueDepart, contraintes);
		
		// Ajout de la ComboBox du point de départ
		jcbPointDepart = new JComboBox();
		jcbPointDepart.setPreferredSize(new Dimension(175,20));
		contraintes.gridx = 1;
		contraintes.gridy = 3;
		jpConteneurOuest.add(jcbPointDepart, contraintes);
		
		// Ajout du label Arrivee
		jlArrivee = new JLabel("Arriv\u00e9e :");
		jlArrivee.setFont(jlArrivee.getFont().deriveFont(Font.BOLD));
		contraintes.gridx = 2;
		contraintes.gridy = 0;		
		jpConteneurOuest.add(jlArrivee, contraintes);
		
		// Ajout de la ComboBox de la ville d'arrivée
		jcbVilleArrivee = new JComboBox();
		jcbVilleArrivee.setName("jcbVilleArrivee");
		jcbVilleArrivee.setPreferredSize(new Dimension(175,20));
		contraintes.gridx = 2;
		contraintes.gridy = 1;
		jpConteneurOuest.add(jcbVilleArrivee, contraintes);
		
		// Ajout de la ComboBox de la rue d'arrivée
		jcbRueArrivee = new JComboBox();
		jcbRueArrivee.setName("jcbRueArrivee");
		jcbRueArrivee.setPreferredSize(new Dimension(175,20));
		contraintes.gridx = 2;
		contraintes.gridy = 2;
		jpConteneurOuest.add(jcbRueArrivee, contraintes);
		
		// Ajout de la ComboBox du point de départ
		jcbPointArrivee = new JComboBox();
		jcbPointArrivee.setPreferredSize(new Dimension(175,20));
		contraintes.gridx = 2;
		contraintes.gridy = 3;
		jpConteneurOuest.add(jcbPointArrivee, contraintes);
		
		// Bouton pour valider la requete
		jbOk = new JButton("Go");
		jbOk.setFont(new Font(jbOk.getFont().getFontName(), Font.BOLD, 15));
		jbOk.setName("jbOk");
		contraintes.gridx = 3;
		contraintes.gridy = 1;
		contraintes.gridheight = 3;
		contraintes.gridwidth = GridBagConstraints.RELATIVE;
		contraintes.fill = GridBagConstraints.VERTICAL;
		contraintes.anchor = GridBagConstraints.LINE_START;
		jpConteneurOuest.add(jbOk, contraintes);
		
		// Creation du Panel Est
		jpConteneurEst = new JPanel(new GridBagLayout());
		jpConteneurEst.setMaximumSize(new Dimension(400, HAUTEUR));
		
		// Contraintes communes
		contraintes.gridheight = 1;
		contraintes.weightx = 1.0;
		contraintes.fill = GridBagConstraints.NONE;
		
		// Label des zooms
		jlZoom = new JLabel("Zoom :");
		jlZoom.setFont(jlZoom.getFont().deriveFont(Font.BOLD));
		contraintes.gridx = 0;
		contraintes.gridy = 0;
		contraintes.gridwidth = 3;
		contraintes.anchor = GridBagConstraints.CENTER;
		jpConteneurEst.add(jlZoom, contraintes);
		
		contraintes.gridwidth = 1;
		
		// Boutton pour Zoomer
		jbZoomPlus = new JButton();
		jbZoomPlus.setName("jbZoomPlus");
		jbZoomPlus.setText("+");
		jbZoomPlus.setPreferredSize(new Dimension(60, 60));
		contraintes.gridx = 2;
		contraintes.gridy = 1;
		contraintes.anchor = GridBagConstraints.LINE_START;
		jpConteneurEst.add(jbZoomPlus, contraintes);
		
		//Slider de zoom
		jsZoom = new JSlider();
		jsZoom.setMinimum((int) (Application.ZOOM_MIN*100));
		jsZoom.setMaximum((int) (Application.ZOOM_MAX*100));
		jsZoom.setValue((int) (Application.ZOOM_INITIAL*100));
		jsZoom.setMajorTickSpacing(10);
		jsZoom.setPaintTicks(true);
		jsZoom.setPaintLabels(true);
		contraintes.gridx = 1;
		contraintes.gridy = 1;
		jpConteneurEst.add(jsZoom,contraintes);

		// Boutton pour Dezoomer
		jbZoomMoins = new JButton();
		jbZoomMoins.setName("jbZoomMoins");
		jbZoomMoins.setText("-");
		jbZoomMoins.setPreferredSize(new Dimension(60, 60));
		contraintes.gridx = 0;
		contraintes.gridy = 1;
		contraintes.anchor = GridBagConstraints.LINE_END;
		jpConteneurEst.add(jbZoomMoins, contraintes);
		
		//Boutton pour avoir une vue d'ensemble (où on ne vois rien d'ailleurs)
		jbZoomGlobal = new JButton("Vue Globale");
		jbZoomGlobal.setName("jbZoomGlobal");
		jbZoomGlobal.setPreferredSize(new Dimension(100, 20));	
		contraintes.gridx = 0;
		contraintes.gridy = 2;
		contraintes.anchor = GridBagConstraints.CENTER;
		//jpConteneurEst.add(jbZoomGlobal, contraintes);
		
		// Boutton pour remettre le Zoom a la normale
		jbZoomReel = new JButton("R\u00e9initialiser");
		jbZoomReel.setName("jbZoomReel");
		jbZoomReel.setPreferredSize(new Dimension(100, 20));
		contraintes.gridx = 1;
		contraintes.gridy = 2;
		contraintes.anchor = GridBagConstraints.CENTER;
		jpConteneurEst.add(jbZoomReel, contraintes);
		
		//Boutton pour mettre la vue en gros plan
		jbZoomGrosPlan = new JButton("Gros plan");
		jbZoomGrosPlan.setName("jbZoomGrosPlan");
		jbZoomGrosPlan.setPreferredSize(new Dimension(100, 20));	
		contraintes.gridx = 2;
		contraintes.gridy = 2;
		contraintes.anchor = GridBagConstraints.CENTER;
		//jpConteneurEst.add(jbZoomGrosPlan, contraintes);
		
		// Creation du Layout general(de type BoxLayout)	
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		add(Box.createHorizontalGlue());
		add(jpConteneurOuest);
		add(Box.createHorizontalGlue());
		add(jpConteneurEst);
		add(Box.createHorizontalGlue());
	}
	
	/**
	 * Add roads into ComboBox
	 *
	 * @param route the road
	 * @param flag the flag
	 */
	@SuppressWarnings("unchecked")
	public void ajouterRouteDansCombobox(String route, jcbFlag flag) {
		switch(flag){
		case DEPART:
			jcbRueDepart.addItem(route);
			break;
		
		case ARRIVEE:
			jcbRueArrivee.addItem(route);
			break;
			
		case BOTH:
			jcbRueDepart.addItem(route);
			jcbRueArrivee.addItem(route);
			break;
		}	
	}
	
	/**
	 * Add towns into ComboBox
	 *
	 * @param ville the ville
	 */
	@SuppressWarnings("unchecked")
	public void ajouterVilleDansCombobox(String ville){
		jcbVilleDepart.addItem(ville);
		AutoCompleteDecorator.decorate(jcbVilleDepart);
		jcbVilleArrivee.addItem(ville);
		AutoCompleteDecorator.decorate(jcbVilleArrivee);
	}
	
	/**
	 * Gets the town name.
	 *
	 * @param flag the flag
	 * @return the town name
	 */
	public String getNomVille(jcbFlag flag) {
		String ret = "";
		switch (flag) {
			case DEPART: ret = (String)jcbVilleDepart.getSelectedItem(); break;
			case ARRIVEE: ret = (String)jcbVilleArrivee.getSelectedItem(); break;
		default:
			break;
		}
		return ret;
	}
	
	/**
	 * Gets the road name.
	 *
	 * @param flag the flag
	 * @return the road name
	 */
	public String getNomRoute(jcbFlag flag) {
		String ret = "";
		switch (flag) {
			case DEPART: ret = (String)jcbRueDepart.getSelectedItem(); break;
			case ARRIVEE: ret = (String)jcbRueArrivee.getSelectedItem(); break;
		default:
			break;
		}
		return ret;
	}
	
	/**
	 * Gets the point number.
	 *
	 * @param flag the flag
	 * @return the point number
	 */
	public int getNumPoint(jcbFlag flag) {
		int ret = -1;
		switch (flag) {
			case DEPART: ret = (Integer)jcbPointDepart.getSelectedItem(); break;
			case ARRIVEE: ret = (Integer)jcbPointArrivee.getSelectedItem(); break;
		default:
			break;
		}
		return ret;
	}
	
	/**
	 * Sets the points.
	 *
	 * @param flag the flag
	 * @param liste the list
	 */
	@SuppressWarnings("unchecked")
	public void setPoints(jcbFlag flag, Vector<Integer> liste)
	{
		@SuppressWarnings("rawtypes")
		JComboBox cmb = new JComboBox();
		
		switch (flag)
		{
			case DEPART: cmb = jcbPointDepart; break;
			case ARRIVEE: cmb = jcbPointArrivee; break;
		default:
			break;
		}
		
		cmb.removeAllItems();
		if (liste.size() > 0) {
			for (@SuppressWarnings("rawtypes")
			Iterator it = liste.iterator(); it.hasNext();) {
				cmb.addItem(it.next());
			}
		}
	}
	
	/**
	 * Modify text of button OK
	 *
	 * @param texte the text
	 */
	public void changerTexteBoutonOk(String texte) {
		jbOk.setText(texte);
	}

	/**
	 * Add listener to the OK button
	 *
	 * @param ecouteur the listener
	 */
	public void ajouterEcouteurAuBoutonOk(ActionListener ecouteur) {
		jbOk.addActionListener(ecouteur);
	}
	
	/**
	 * Add listener to the minus zoom button
	 *
	 * @param ecouteur the listener
	 */
	public void ajouterEcouteurAuBoutonZoomMoins(ActionListener ecouteur) {
		jbZoomMoins.addActionListener(ecouteur);
	}
	
	/**
	 * Add listener to the plus zoom button
	 *
	 * @param ecouteur the listener
	 */
	public void ajouterEcouteurAuBoutonZoomPlus(ActionListener ecouteur) {
		jbZoomPlus.addActionListener(ecouteur);
	}

	/**
	 * Add listener to the real zoom button
	 *
	 * @param ecouteur the listener
	 */
	public void ajouterEcouteurAuBoutonZoomReel(ActionListener ecouteur) {
		jbZoomReel.addActionListener(ecouteur);
	}

	/**
	 * Add listener to the global zoom button
	 *
	 * @param ecouteur the listener
	 */
	public void ajouterEcouteurAuBoutonZoomGlobal(ActionListener ecouteur) {
		jbZoomGlobal.addActionListener(ecouteur);	
	}
	
	/**
	 * Add listener to the close-up zoom button
	 *
	 * @param ecouteur the ecouteur
	 */
	public void ajouterEcouteurAuBoutonZoomGrosPlan(ActionListener ecouteur) {
		jbZoomGrosPlan.addActionListener(ecouteur);	
	}
	
	/**
	 * Add listener to the slider
	 *
	 * @param ecouteur the listener
	 */
	public void ajouterEcouteurAuSlider(ChangeListener ecouteur) {
		jsZoom.addChangeListener(ecouteur);
	}
	
	/**
	 * Add listener departure town
	 *
	 * @param ecouteur the listener
	 */
	public void ajouterEcouteurVilleDepart(ActionListener ecouteur) {
		jcbVilleDepart.addActionListener(ecouteur);
	}
	
	/**
	 * Add listener departure street
	 *
	 * @param ecouteur the listener
	 */
	public void ajouterEcouteurRueDepart(ActionListener ecouteur) {
		jcbRueDepart.addActionListener(ecouteur);
	}
	
	/**
	 * Add listener arrival town
	 *
	 * @param ecouteur the listener
	 */
	public void ajouterEcouteurVilleArrivee(ActionListener ecouteur) {
		jcbVilleArrivee.addActionListener(ecouteur);
	}
	
	/**
	 * Add listener arrival street
	 *
	 * @param ecouteur the listener
	 */
	public void ajouterEcouteurRueArrivee(ActionListener ecouteur) {
		jcbRueArrivee.addActionListener(ecouteur);
	}
	
	/**
	 * Is town already there
	 *
	 * @param ville the town
	 * @return true, if successful
	 */
	public boolean villeDejaPresente(String ville) {
		for(int i=0;i<jcbVilleDepart.getItemCount();i++){
			if(jcbVilleDepart.getItemAt(i).toString().equals(ville)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Clear street combo box
	 * 
	 * @param flag the flag
	 */
	public void viderRueComboBox(jcbFlag flag) {
		switch(flag){
		case ARRIVEE:
			jcbRueArrivee.removeAllItems();
			break;
		
		case DEPART:
			jcbRueDepart.removeAllItems();
			break;
			
		case BOTH:
			jcbRueDepart.removeAllItems();
			jcbRueArrivee.removeAllItems();
			break;
		}	
	}
	
	/**
	 * Sets the slider value.
	 *
	 * @param val the new slider value
	 */
	public void setSliderValue(int val){
		jsZoom.setValue(val);
	}
	
	/**
	 * Sets the minus zoom icon.
	 *
	 * @param icon the new minus zoom icon
	 */
	public void setIconZoomMoins(ImageIcon icon) {
		jbZoomMoins.setIcon(icon);
	}
	
	/**
	 * Sets the plus zoom icon.
	 *
	 * @param icon the new plus zoom icon
	 */
	public void setIconZoomPlus(ImageIcon icon) {
		jbZoomPlus.setIcon(icon);
	}
}
