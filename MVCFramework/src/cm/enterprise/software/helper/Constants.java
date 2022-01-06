package cm.enterprise.software.helper;

import cm.enterprise.software.exception.ProgramException;
import cm.enterprise.software.util.Resource;

/**
 * Constants rassemble, en son sein, l'ensemble des constantes communes a toutes les classes du projet.
 * 
 * @author Charles Mouté
 * @version 1.0.1, 23/06/2010
 */
public class Constants  extends Resource {
	
	/**
	 * Contruit un ensemble de constantes utilisables par l'ensemble du projet.
	 */
	public Constants(){
		/*
		 * L'appel de super(), ici est particulierement fait pour initialiser
		 * le nom de la ressource au nom de la classe et la valeur au chemin
		 * d'acces a la classe. 
		 */
		super();
		//Definition de quelques constantes pour l' application.
		try {
			this.definesParameter("author","about","copyright","comment","resourcePath1","resourcePath2");
			//Affectation des valeurs de la ressource
			this.setValueTo("author", "Charles Moute");
			this.setValueTo("about", "Application cree a partir de MVCFramework.");
			this.setValueTo("comment", "File created by MVCFramework");
			this.setValueTo("copyright", "Tout droit reserve a l'auteur");
			//Chemin d'acces aux ressources internes du framework
			this.setValueTo("resourcePath1", "cm/enterprise/software/resource/");
			//Chemin d'acces aux ressources de l'application 
			this.setValueTo("resourcePath2", "./resource/");
		} catch (ProgramException e) {
			e.printStackTrace();
		}
	}
}
