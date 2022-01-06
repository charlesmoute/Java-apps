package helper;

import java.awt.Color;

/**
 * 
 * @author Charles&Marsien
 * <p>ConfigurationConstant definit la liste des constantes utilisees dans l'application</p>
 */
public class Configuration {
	
	/**
	 * <p>
	 * 	<h3><strong>Configuration application</strong></h3>
	 *  <ul>
	 *  	<li>Version du serveur</li>
	 *  	<li>Valeur 1.0</li>
	 *  </ul>
	 * </p>
	 */
	public static final String SERVER_VERSION = Configuration.NAME_SERVER+"/1.0";
	
	/**
	 * <p>
	 * 	<h3><strong>Configuration application</strong></h3>
	 *  <ul>
	 *  	<li>Port par defaut</li>
	 *  	<li>Valeur 8080.</li>
	 *  </ul>
	 * </p>
	 */
	public static final int DEFAULT_PORT = 8080;
	
	/**
	 * <p>
	 * 	<h3><strong>Configuration application</strong></h3>
	 *  <ul>
	 *  	<li>Nombre de connexion simultanee</li>
	 *  	<li>Valeur 0 pour infini.</li>
	 *  </ul>
	 * </p>
	 */	
	public static final int DEFAULT_THREAD_NUMBER = 0;
	
	/**
	 * <p>
	 * 	<h3><strong>Configuration application</strong></h3>
	 *  <ul>
	 *  	<li>Nom du serveur</li>
	 *  	<li>Valeur RAMServer.</li>
	 *  </ul>
	 * </p>
	 */
	public static final String NAME_SERVER = "RAMServer";
	
	/**
	 * <p>
	 * 	<h3><strong>Configuration application</strong></h3>
	 *  <ul>
	 *  	<li>Nom du client</li>
	 *  	<li>Valeur RAMClient.</li>
	 *  </ul>
	 * </p>
	 */
	public static final String NAME_CLIENT = "RAMClient";
	
	/**
	 * <p>
	 * 	<h3><strong>Configuration application</strong></h3>
	 *  <ul>
	 *  	<li>Type de serveur</li>
	 *  	<li>Valeur par defaut MultiThreadServer.</li>
	 *  </ul>
	 * </p>
	 */
	public static final String MULTITHREAD_SERVER = "MultiThreadServer";
	
	/**
	 * <p>
	 * 	<h3><strong>Configuration application</strong></h3>
	 *  <ul>
	 *  	<li>Type de serveur</li>
	 *  	<li>Valeur par defaut MultiThreadServer.</li>
	 *  </ul>
	 * </p>
	 */
	public static final String CHANNEL_SERVER = "ChannelServer";
	
	/**
	 * <p>
	 * 	<h3><strong>Configuration application</strong></h3>
	 *  <ul>
	 *  	<li>Repertoire racine des documents web</li>
	 *  	<li>Valeur webRoot.</li>
	 *  </ul>
	 * </p>
	 */
	public static final String WEB_DIRECTORY  = "./webRoot/";
	
	/**
	 * <p>
	 * 	<h3><strong>Configuration application</strong></h3>
	 *  <ul>
	 *  	<li>Cache du serveur</li>
	 *  	<li>Valeur cache.</li>
	 *  </ul>
	 * </p>
	 */
	public static final String CACHE_DIRECTORY  = "./cache/";
	
	/**
	 * <p>
	 * 	<h3><strong>Configuration application</strong></h3>
	 *  <ul>
	 *  	<li>Repertoire ou se trouve les fichiers log</li>
	 *  	<li>Valeur logFile.</li>
	 *  </ul>
	 * </p>
	 */
	public static final String LOG_DIRECTORY = "./logFile/";
	
	/**
	 * <p>
	 * 	<h3><strong>Configuration application</strong></h3>
	 *  <ul>
	 *  	<li>Repertoire ou se trouve les ressources de l'application</li>
	 *  	<li>Valeur resource.</li>
	 *  </ul>
	 * </p>
	 */
	public static final String RESOURCE_DIRECTORY = "./resource/";
	
	/**
	 * <p>
	 * 	<h3><strong>Configuration application</strong></h3>
	 *  <ul>
	 *  	<li>Repertoire ou se trouve les fichiers d'erreur de l'application</li>
	 *  	<li>Valeur errorFile.</li>
	 *  </ul>
	 * </p>
	 */
	public static final String ERROR_DIRECTORY = "./errorFile/";
	
	/**
	 * <p>
	 * 	<h3><strong>Configuration application</strong></h3>
	 *  <ul>
	 *  	<li>Nom du fichier de configuratiion</li>
	 *  	<li>Valeur config.</li>
	 *  </ul>
	 * </p>
	 */
	public static final String NAME_CONFIGURATION_FILE = "./config.properties";
	
	/**
	 * <p>
	 * 	<h3><strong>Configuration application</strong></h3>
	 *  <ul>
	 *  	<li>Reponse a une question</li>
	 *  	<li>Valeur yes.</li>
	 *  </ul>
	 * </p>
	 */
	public static final String YES = "yes";
	
	/**
	 * <p>
	 * 	<h3><strong>Configuration application</strong></h3>
	 *  <ul>
	 *  	<li>Reponse a une question</li>
	 *  	<li>Valeur no.</li>
	 *  </ul>
	 * </p>
	 */
	public static final String NO = "no";
	
	/**
	 * <p>
	 * 	<h3><strong>Configuration application</strong></h3>
	 *  <ul>
	 *  	<li>Couleur de l'arriere-plan</li>
	 *  	<li>Valeur Noire.</li>
	 *  </ul>
	 * </p>
	 */
	public static final Color BACKGROUND = Color.BLACK ;
	
	/**
	 * <p>
	 * 	<h3><strong>Configuration application</strong></h3>
	 *  <ul>
	 *  	<li>Couleur du texte</li>
	 *  	<li>Valeur Vert.</li>
	 *  </ul>
	 * </p>
	 */
	public static final Color FOREGROUND = Color.GREEN ;
	
	/**
	 * <p>
	 * 	<h3><strong>Configuration application</strong></h3>
	 *  <ul>
	 *  	<li>Couleur du caret</li>
	 *  	<li>Valeur Vert</li>
	 *  </ul>
	 * </p>
	 */
	public static final Color CARETCOLOR = Color.GREEN ;
}
