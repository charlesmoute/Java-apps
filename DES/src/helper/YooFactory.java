/**
 *Classe: YooFactory
 *Package: go.tools
 */
package helper;

/**
 * @author LeKeutch
 *
 */

import java.net.*;
import javax.swing.*;

public class YooFactory {

	public static String RESSOURCES_DIRECTORY_NAME="medias";
	
	/**
	 *  Obtenir URL de la classe YooFactory.class
	 *  @return url de YooFactory.class
	 */
	public static URL getMeURL(){
		URL url;
		url = YooFactory.class.getResource("YooFactory.class");
		return url;
	}
	

	/**
	 *  Obtenir URL d'une image du dossier images
	 */
	
	public static URL getImageURL(String pathImage){
		return YooFactory.class.getResource(YooFactory.RESSOURCES_DIRECTORY_NAME+"/"+pathImage);
	}
	
	/**
	 * creer une imageIcon d'une image du dossier images 
	 * @param pathImage
	 * @return l'icone de l'image passe en parametre
	 */
	
	public static ImageIcon getImageIcon(String pathImage){
		return new ImageIcon(getImageURL(pathImage));
	}
	
}
