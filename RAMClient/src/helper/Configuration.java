package helper;

import java.awt.Color;
import java.awt.Font;

public class Configuration {

	/**
	 * <p>
	 * 	<h3><strong>Configuration application</strong></h3>
	 *  <ul>
	 *  	<li>Dossier de cache du client</li>
	 *  	<li>Valeur ./cache/.</li>
	 *  </ul>
	 * </p>
	 */
	public static final String CACHE_DIRECTORY  = "./cache/";
	
	/**
	 * <p>
	 * 	<h3><strong>Configuration application</strong></h3>
	 *  <ul>
	 *  	<li>Font de la plupart des textes du client.</li>
	 *  	<li>Valeur Font-Familly = DialogInput et Font-size = 14.</li>
	 *  </ul>
	 * </p>
	 */
	public static final Font FONT = new Font("DialogInput", 0, 14);
	
	/**
	 * <p>
	 * 	<h3><strong>Configuration application</strong></h3>
	 *  <ul>
	 *  	<li>Couleur de certains textes du client</li>
	 *  	<li>Valeur RVB(76,128,235).</li>
	 *  </ul>
	 * </p>
	 */
	public static final Color COLOR = new Color(76,128,235);
	
	/**
	 * <p>
	 * 	<h3><strong>Configuration application</strong></h3>
	 *  <ul>
	 *  	<li>Dossier Resource du client</li>
	 *  	<li>Valeur ./resource/.</li>
	 *  </ul>
	 * </p>
	 */
	public static final String RESOURCE_DIRECTORY = "./resource/";
}
