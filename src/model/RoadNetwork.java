package model;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


/**
 * The Class RoadNetwork.
 */
public class RoadNetwork
{
	// Variable de stockage des routes
	/** The roads. */
	private HashMap<String, Road> routes;
	
	// Variable de stockage des points
	/** The points. */
	private HashMap<Integer, Point> points;
	
	// Nom du fichier Xml
	/** The XML filename. */
	private String nomFichierXml;
	
	// Nom du fichier image de la carte : lu lors du parsage Xml
	/** The image filename. */
	private String nomFichierImage;
	
	// Nombre de connexions : une connexion est un chemin possible entre 2 points
	/** The number of connections. */
	private int nombreConnexions;
	
	// Constructeur
	/**
	 * Instantiates a new road network.
	 */
	public RoadNetwork() {
		routes = new HashMap<String, Road>();
		points = new HashMap<Integer, Point>();
	}
	
	// Parseur Xml
	/**
	 * Parses the xml.
	 *
	 * @param fichierXml the XML file.
	 */
	public void parseXml(String fichierXml) 
	{
		// Initialisation des membres de l'objet
		nomFichierXml = fichierXml;
		routes.clear();
		points.clear();
		nombreConnexions = 0;
		
		// Variable générales pour le fichier XML
		Element xmlRacine;
		int i, j, k;
		
		// Variables pour le traitement des points
		NodeList balisesPoint;
		NodeList balisePoints;
		int x, y;
		Integer num;

		// Variables pour le traitement des rues
		NodeList balisesRue;
		NodeList baliseRues;
		NodeList balisesPt;
		Road route;
		String nomRoute;
		int sens;
		boolean premierPoint;
		
		try {
			// Initialisation du document XML
			DocumentBuilderFactory fabrique = DocumentBuilderFactory.newInstance();
			DocumentBuilder constructeur = fabrique.newDocumentBuilder();
			File xml = new File(nomFichierXml);
			Document document = constructeur.parse(xml);
			
			// Lecture et traitement des donnï¿½es
			xmlRacine = document.getDocumentElement();
			
			// Lecture du nom de l'image source
			nomFichierImage = xmlRacine.getAttributes().getNamedItem("src").getNodeValue();
			
			// Lecture des points
			balisePoints = xmlRacine.getElementsByTagName("points");
			for (i = 0; i < balisePoints.getLength(); i++) {
				balisesPoint = balisePoints.item(i).getChildNodes();
				for (j = 0; j < balisesPoint.getLength(); j++) {
					if (balisesPoint.item(j).getNodeName() == "point") {
						num = Integer.decode(balisesPoint.item(j).getAttributes().getNamedItem("num").getNodeValue());
						x = (int) Double.parseDouble(balisesPoint.item(j).getAttributes().getNamedItem("x").getNodeValue());
						y = (int) Double.parseDouble(balisesPoint.item(j).getAttributes().getNamedItem("y").getNodeValue());
						points.put(num, new Point(x, y));
					}
				}
			}
			
			// Lecture des rues
			baliseRues = xmlRacine.getElementsByTagName("rues");
			for (i = 0; i < baliseRues.getLength(); i++) {
				balisesRue = baliseRues.item(i).getChildNodes();
				for (j = 0; j < balisesRue.getLength(); j++) {
					if (balisesRue.item(j).getNodeName() == "rue") {
						// Lecture des donnï¿½es de la rue
						nomRoute = balisesRue.item(j).getAttributes().getNamedItem("nom").getNodeValue();
						nomRoute = nomRoute.substring(0, 1).toUpperCase() + nomRoute.substring(1);
						sens = Integer.parseInt(balisesRue.item(j).getAttributes().getNamedItem("sens").getNodeValue());
						route = new Road(sens);
						balisesPt = balisesRue.item(j).getChildNodes();
						
						// Lecture des points des rues
						premierPoint = true;
						for (k = 0; k < balisesPt.getLength(); k++) {
							if (balisesPt.item(k).getNodeName() == "pt") {
								route.ajouterNumPoint(Integer.decode(balisesPt.item(k).getAttributes().getNamedItem("num").getNodeValue()));
								if (!premierPoint) {
									nombreConnexions++;
								}
								premierPoint = false;
							}
						}
						routes.put(nomRoute, route);
					}
				}
			}
			
		}
		catch(ParserConfigurationException pce) { System.out.println("Erreur de configuration du parseur DOM lors de l'appel fabrique.newDocumentBuilder();"); }
		catch(SAXException se) { System.out.println("Erreur lors du parsing du document lors de l'appel construteur.parse(xml)"); }
		catch(IOException ie) { System.out.println("Erreur d'entrï¿½e/sortie lors de l'appel construteur.parse(xml)"); }
	}
	
	// Renvoie l'objet Point2D d'identifiant num
	/**
	 * Gets the point.
	 *
	 * @param num the id
	 * @return the point
	 */
	public Point getPoint(Integer num) {
		return points.get(num);
	}
	
	// Renvoie la liste des numéros de points
	/**
	 * Gets the points list.
	 *
	 * @return the points list
	 */
	@SuppressWarnings("rawtypes")
	public Set getListePoints() {
		return points.keySet();
	}
	
	// Renvoie le nombre de points enregistrés dans le réseau routier
	/**
	 * Gets the points number.
	 *
	 * @return the points number
	 */
	public int getNombrePoints() {
		return points.size();
	}
	
	// Renvoie le nombre de connexions
	/**
	 * Gets the connections number.
	 *
	 * @return the connections number
	 */
	public int getNombreConnexions() {
		return nombreConnexions;
	}
	
	// Renvoie l'objet Route d'identifiant nom
	/**
	 * Gets the road.
	 *
	 * @param nom the name
	 * @return the road
	 */
	public Road getRoute(String nom) {
		return routes.get(nom);
	}
	
	// Renvoie la liste des noms des routes
	/**
	 * Gets the roads list.
	 *
	 * @return the roads list
	 */
	@SuppressWarnings("rawtypes")
	public Set getListeRoutes() {
		return routes.keySet();
	}
	
	// Renvoie le nombre de routes enregistrï¿½es dans le rï¿½seau routier
	/**
	 * Gets the roads number.
	 *
	 * @return the roads number
	 */
	public int getNombreRoutes() {
		return routes.size();
	}
	
	// Renvoie le nom du fichier image correspondant au fichier Xml
	/**
	 * Gets the image filename.
	 *
	 * @return the image filename
	 */
	public String getNomFichierImage() {
		return nomFichierImage;
	}
	
	// Renvoie le nom du fichier Xml
	/**
	 * Gets the XML filename.
	 *
	 * @return the XML filename
	 */
	public String getNomFichierXml() {
		return nomFichierXml;
	}
}
