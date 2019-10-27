package model;

import java.util.regex.*;
import java.lang.Character;

/**
 * The Class RoadName.
 */
public class RoadName extends Object{

	/** The name. */
	String nom;
	
	// Format définissant les routes non ville ( A36, E54 etc...)
	/** The highway pattern. */
	Pattern autoroutePattern = Pattern.compile(".*A(\\d)+.*");
	
	/** The European pattern. */
	Pattern europeennePattern = Pattern.compile(".*E(\\d)+.*");
	
	/** The national pattern. */
	Pattern nationalePattern = Pattern.compile(".*N(\\d)+.*");
	
	/** The regional pattern. */
	Pattern departementalePattern = Pattern.compile(".*D(\\d)+.*");
	
	
	/**
	 * Instantiates a new road name.
	 *
	 * @param oNom the name
	 */
	public RoadName(Object oNom)
	{
		super();
		nom = oNom.toString();
	}
	
	/**
	 * Is a highway
	 *
	 * @return true, if successful
	 */
	public boolean estUneAutoroute(){
		Matcher m = autoroutePattern.matcher(nom);
		return m.matches();
	}
	
	/**
	 * Is an European road
	 *
	 * @return true, if successful
	 */
	public boolean estUneEuropeenne(){
		Matcher m = europeennePattern.matcher(nom);
		return m.matches();
	}
	
	/**
	 * Is a national road.
	 *
	 * @return true, if successful
	 */
	public boolean estUneNationale(){
		Matcher m = nationalePattern.matcher(nom);
		return m.matches();
	}
	
	/**
	 * Is a regional road.
	 *
	 * @return true, if successful
	 */
	public boolean estUneDepartementale(){
		Matcher m = departementalePattern.matcher(nom);
		return m.matches();
	}
	
	/**
	 * Is a town.
	 *
	 * @return true, if successful
	 */
	public boolean estUneVille(){
		return (!estUneAutoroute() && !estUneEuropeenne() && !estUneNationale() && !estUneDepartementale());
	}
	
	// Extrait la ville du nom de la route ( "ville - nomRoute" )
	/**
	 * Extract the town name.
	 *
	 * @return the town name
	 */
	public String extraireNomVille(){
		String nomVille = new String(nom);
		int indice = nomVille.indexOf('-');			
			
		// Si un '-' est trouvï¿½, on ne prend que ce qui viens avant
		if(indice > -1){
		 	
			// Prend le nom de la ville
			nomVille = nomVille.substring(0, indice);
			
			// Retire les eventuels espaces aprï¿½s le nom de ville
			while(Character.isWhitespace(nomVille.charAt(indice-1))  ){
				nomVille = nomVille.substring(0, indice-=1);
			}
		}
		// Sinon on garde tout le nom, donc pas de changements
		return nomVille;
	}
}
