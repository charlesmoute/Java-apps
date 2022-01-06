package helper;

/**
 * 
 * @author Charles&Marsien
 * <p>Cette classe contient toutes les constantes du protocole HTTP utilisées dans le projet.</p>
 */
public class HTTP {

	/**
	 * <p>
	 * 	<h3><strong>Constante HTTP</strong></h3>
	 *  <ul>
	 *  	<li>Version du protocole HTTP</li>
	 *  	<li>Valeur HTTP/1.1</li>
	 *  </ul>
	 * </p>
	 */
	public static final String HTTP_VERSION = "HTTP/1.1";
	
	/**
	 * <p>
	 * 	<h3><strong>Constante HTTP</strong></h3>
	 *  <ul>
	 *  	<li>Ligne contenant seulement le retour chariot et le line feed.</li>
	 *  	<li>Valeur "\n\r"</li>
	 *  </ul>
	 * </p>
	 */
	public static final String CRLF = "\r\n";
	
	/**
	 * <p>
	 * 	<h3><strong>Constante HTTP de succes</strong></h3>
	 *  <ul>
	 *  	<li>Code 200</li>
	 *  	<li>Description Succes d'une requete</li>
	 *  </ul>
	 * </p>
	 */
	public static final String SUCCESS_STATUS = "200 OK Succes";
	
	/**
	 * <p>
	 * 	<h3><strong>Constante HTTP de succes</strong></h3>
	 *  <ul>
	 *  	<li>Code 201</li>
	 *  	<li>Description Succes de creation d'une ressource.</li>
	 *  </ul>
	 * </p>
	 */
	public static final String  CREATED_STATUS = "201 Ressource creee" ;
	
	/**
	 * <p>
	 * 	<h3><strong>Constante HTTP de succes</strong></h3>
	 *  <ul>
	 *  	<li>Code 202</li>
	 *  	<li>Description Requete acceptee.</li>
	 *  </ul>
	 * </p>
	 */
	public static final String ACCEPTED_STATUS = "202 Requete accepte";
	
	/**
	 * <p>
	 * 	<h3><strong>Constante HTTP de succes</strong></h3>
	 *  <ul>
	 *  	<li>Code 204</li>
	 *  	<li>Description Pas de contenu.</li>
	 *  </ul>
	 * </p>
	 */
	public static final String NOT_CONTENT_STATUS = "204 Pas de contenu";
	
	/**
	 * <p>
	 * 	<h3><strong>Constante HTTP d'erreur due au client</strong></h3>
	 *  <ul>
	 *  	<li>Code 400</li>
	 *  	<li>Description Mauvaise requete</li>
	 *  </ul>
	 * </p>
	 */
	public static final String BAD_REQUEST_STATUS = "400 Mauvaise requete";
	
	/**
	 * <p>
	 * 	<h3><strong>Constante HTTP d'erreur due au client</strong></h3>
	 *  <ul>
	 *  	<li>Code 404</li>
	 *  	<li>Description Ressource non trouvee</li>
	 *  </ul>
	 * </p>
	 */
	public static final String NOT_FOUND_STATUS = "404 Ressource non trouvee";
	
	
	/**
	 * <p>
	 * 	<h3><strong>Constante HTTP d'erreur due au client</strong></h3>
	 *  <ul>
	 *  	<li>Code 405</li>
	 *  	<li>Description Methode non autorisee</li>
	 *  </ul>
	 * </p>
	 */
	public static final String UNAUTHORIZED_STATUS = "405 Methode non autorisee";
	
	/**
	 * <p>
	 * 	<h3><strong>Constante HTTP d'erreur due au serveur</strong></h3>
	 *  <ul>
	 *  	<li>Code 500</li>
	 *  	<li>Description Erreur interne du serveur</li>
	 *  </ul>
	 * </p>
	 */
	public static final String INTERNAL_ERROR_STATUS = "500 Erreur interne du serveur";
	
	/**
	 * <p>
	 * 	<h3><strong>Constante HTTP d'erreur due au serveur</strong></h3>
	 *  <ul>
	 *  	<li>Code 503</li>
	 *  	<li>Description Service web inutilisable</li>
	 *  </ul>
	 * </p>
	 */
	public static final String UNUSABLE_WEB_STATUS = "503 Service web inutilisable";
	
	
	/**
	 * <p>
	 * 	<h3><strong>Constante de Methode HTTP</strong></h3>
	 *  <ul>
	 *  	<li>Methode GET</li>
	 *  	<li>Description Envoie d'une ressource</li>
	 *  </ul>
	 * </p>
	 */
	public static final String GET_METHOD = "GET";
	
	/**
	 * <p>
	 * 	<h3><strong>Constante de Methode HTTP</strong></h3>
	 *  <ul>
	 *  	<li>Methode HEAD</li>
	 *  	<li>Description Envoie l'entete d'une ressource</li>
	 *  </ul>
	 * </p>
	 */
	public static final String HEAD_METHOD = "HEAD";
	
	/**
	 * <p>
	 * 	<h3><strong>Constante de Methode HTTP</strong></h3>
	 *  <ul>
	 *  	<li>Methode PUT</li>
	 *  	<li>Description Cree une ressource sur le serveur<br>La ressource est un fichier</li>
	 *  </ul>
	 * </p>
	 */
	public static final String PUT_METHOD = "PUT";
	
	/**
	 * <p>
	 * 	<h3><strong>Constante de Methode HTTP</strong></h3>
	 *  <ul>
	 *  	<li>Methode DELETE</li>
	 *  	<li>Description Detruit une ressource sur le serveur</li>
	 *  </ul>
	 * </p>
	 */
	public static final String DELETE_METHOD = "DELETE";
			
	/**
	 * <p>
	 * 	<h3><strong>Constante des entetes de messages de requete</strong></h3>
	 *  <ul>
	 *  	<li>Entete Accept</li>
	 *  	<li>Description Type attendu par le client</li>
	 *  </ul>
	 * </p>
	 */
	public static final String ACCEPT_HEADER = "Accept";
	
	/**
	 * <p>
	 * 	<h3><strong>Constante des entetes de messages de requete</strong></h3>
	 *  <ul>
	 *  	<li>Entete Accept-Charset</li>
	 *  	<li>Description Codage attendu</li>
	 *  </ul>
	 * </p>
	 */
	public static final String ACCEPT_CHARSET_HEADER = "Accept-Charset";
	
	/**
	 * <p>
	 * 	<h3><strong>Constante des entetes de messages de requete</strong></h3>
	 *  <ul>
	 *  	<li>Entete Accept-Encoding</li>
	 *  	<li>Description Algo de compression utilise</li>
	 *  </ul>
	 * </p>
	 */
	public static final String ACCEPT_ENCODING_HEADER = "Accept-Encoding";
	
	/**
	 * <p>
	 * 	<h3><strong>Constante des entetes de messages de requete</strong></h3>
	 *  <ul>
	 *  	<li>Entete Accept-Language</li>
	 *  	<li>Description Langage attendu par le client</li>
	 *  </ul>
	 * </p>
	 */
	public static final String ACCEPT_LANGUAGE_HEADER = "Accept-Language";
	
	/**
	 * <p>
	 * 	<h3><strong>Constante des entetes de messages de requete</strong></h3>
	 *  <ul>
	 *  	<li>Entete From</li>
	 *  	<li>Description Adresse email de l'usager l'usager</li>
	 *  </ul>
	 * </p>
	 */
	public static final String FROM_HEADER = "FROM";
	
	/**
	 * <p>
	 * 	<h3><strong>Constante des entetes de messages de requete</strong></h3>
	 *  <ul>
	 *  	<li>Entete User-Agent</li>
	 *  	<li>Description La partie hote de l’URL</li>
	 *  </ul>
	 * </p>
	 */
	public static final String HOST_HEADER = "HOST";
	
	/**
	 * <p>
	 * 	<h3><strong>Constante des entetes de messages de requete</strong></h3>
	 *  <ul>
	 *  	<li>Entete Referer</li>
	 *  	<li>Description Reference de la ressource</li>
	 *  </ul>
	 * </p>
	 */
	public static final String REFERER_HEADER = "REFERER";
	
	/**
	 * <p>
	 * 	<h3><strong>Constante des entetes de messages de requete</strong></h3>
	 *  <ul>
	 *  	<li>Entete User-Agent</li>
	 *  	<li>Description Information ayant trait a l'usager</li>
	 *  </ul>
	 * </p>
	 */
	public static final String USER_AGENT_HEADER = "User-Agent";
	
	/**
	 * <p>
	 * 	<h3><strong>Constante des entetes de messages de reponse</strong></h3>
	 *  <ul>
	 *  	<li>Entete Location</li>
	 *  	<li>Description Redirection de la recherche</li>
	 *  </ul>
	 * </p>
	 */
	public static final String LOCATION_HEADER = "Location";
	
	/**
	 * <p>
	 * 	<h3><strong>Constante des entetes de messages de reponse</strong></h3>
	 *  <ul>
	 *  	<li>Entete Retry-After</li>
	 *  	<li>Description Date pour reessayer, losqu'une ressource n'est pas trouvee</li>
	 *  </ul>
	 * </p>
	 */
	public static final String RETRY_AFTER_HEADER = "Retry-After";
	
	
	/**
	 * <p>
	 * 	<h3><strong>Constante des entetes de messages de reponse</strong></h3>
	 *  <ul>
	 *  	<li>Entete Server</li>
	 *  	<li>Description Version du logiciel utilise par le serveur</li>
	 *  </ul>
	 * </p>
	 */
	public static final String SERVER_HEADER = "Server";
	
		
	/**
	 * <p>
	 * 	<h3><strong>Constante entete de contenu</strong></h3>
	 *  <ul>
	 *  	<li>Entete Allow</li>
	 *  	<li>Description Liste de methodes de requetes autorisees par le serveur</li>
	 *  </ul>
	 * </p>
	 */
	public static final String ALLOW_HEADER = "Allow";
		
	/**
	 * <p>
	 * 	<h3><strong>Constante entete de contenu</strong></h3>
	 *  <ul>
	 *  	<li>Entete Content-Length</li>
	 *  	<li>Description Nombre d’octets du contenu</li>
	 *  </ul>
	 * </p>
	 */
	public static final String CONTENT_LENGTH_HEADER = "Content-Length";
	
	/**
	 * <p>
	 * 	<h3><strong>Constante entete de contenu</strong></h3>
	 *  <ul>
	 *  	<li>Entete Content-Type</li>
	 *  	<li>Description Type du contenu du document</li>
	 *  </ul>
	 * </p>
	 */
	public static final String CONTENT_TYPE_HEADER = "Content-Type";
	
}
