package cm.ringo.dialer.component.interfaces.connection;

import cm.ringo.dialer.component.model.base.connection.EntryModel;

/**
 * Represente les differents operations permettant de manipuler les connexions PPPOE
 * a travers le Systeme.
 * @author Charles Moute
 * @version 1.0.1, 8/07/2010
 */

public interface OperatingSystem {

	/**
	 * 
	 * Ajoute une entree de connexion, au systeme, selon le modele en parametre. 
	 * @param entry Le modele de l'entree de connexion a creer.
	 * @return 0 si l'operation est un succes. 
	 */
	public int addEntry(EntryModel entry); 
	
	/**
	 * Lance l'appel de l'entree en parametre.
	 * @param entryName Nom de l'entree a appeler.
	 */
	public void dial(String entryName) ;
	
	/**
	 * Lance l'appel de l'entree en parametre.
	 * @param entryName Nom de l'entree a appeler.
	 * @param username Nom de l'utilisateur
	 * @param password Mot de passe utilisateur.
	 */
	public void dial(String entryName,String username,String password) ;
	
	/**
	 * Permet de savoir si l'entree en parametre existe.
	 * @param entryName Entree a verifier l'existence dans le systeme.
	 * @return true si l'entree a deja ete ajoute dans le systeme.
	 */
	public boolean entryExists(String entryName);
	
	
	/**
	 * Permet de dire si l'entree en parametre est active
	 * @param entryName Entree a verifier l'activite.
	 * @return true si l'entree est active.
	 */
	public boolean entryIsActive(String entryName);
	
	/**
	 * Permet de savoir si l'entree eb parametre est en cours d'appel.
	 * @param entryName Entree a verifier l'etat.
	 * @return true si l'entree est entrain d'etre appelee.
	 */
	public boolean entryIsDialing(String entryName);
	
	/**
	 * Permet d'obtenir l'adresse IP de l'entree en parametre.
	 * @param entryName Entree dont on veut connaitre l'adresse IP qui lui a ete assignee.
	 * @return L'adresse IP sous forme de a.b.c.d
	 */
	public String getIPAddress(String entryName);
	
	/**
	 * Permet d'obtenir le nombre de bytes recues par l'entree en parametre.
	 * @param entryName Nom d'une entree.
	 * @return Le nombre de bits recu par l'entree en parametre lors d'une connexion.
	 */
	public long getBytesReceived(String entryName);
	
	
	/**
	 * Permet d'obtenir le nombre de bytes transmis par l'entree en parametre.
	 * @param entryName Nom d'une entree.
	 * @return Le nombre de bits recu par l'entree lors d'une connexion.
	 */
	public long getBytesTransmitted(String entryName);
	
	/**
	 * Permet d'obtenir le temps mis lors de la connexion de l'entree en paraametre. 
	 * @param entry Nom d'une entree
	 * @return Le temps de connexion de l'entree en parametre.
	 */
	public long getConnectionDuration(String entry);
	
	/**
	 * Retourne la vitesse de la connexion de l'entree en parametre
	 * @param entry Nom d'une entree.
	 * @return La vitesse de connexion d'une entree.
	 */
	public long getConnectionSpeed(String entry);

	/**
	 * Retourne l'entree en parametre sous forme d'un model manipulable par l'application 
	 * et ce independament du systeme.
	 * @param entry Nom  de l'entree dont on souhaite obtenir le modele.
	 * @return Le modele de l'entree en parametre si il existe.
	 */
	public EntryModel getEntryModel(String entry);

	/**
	 * Permet de retourne le temps limite d'une tentative d'appel.
	 * @return  Le nombre de millisecondes apres lequel une connexion en cours est interrompu. Ce temps represente le temps de latence pour l'etablissement d'une connexion.
	 */
	public long getDialingTimeout();
	
	/**
	 * Permet d'obtenir le temps limite d'inactivite autorisee par l'entree en parametre. 
	 * @param entryName Nom de l'entree dont le temps d'inactivite a considerer est retournee.
	 * @return Le nombre de secondes apres lequel la connection est terminee, ceci du a une inactivite.
	 */
	public int getIdleTimeout(String entryName);
		
	/**
	 * Permet d'obtenir le temps ecoule avant une interruption d'appel.
	 * @return Le nombre de millisecondes apres lequel une connexion est interrompu.
	 */
	public int getHangUpTimeout();
	
	/**
	 * Renvoit la liste des noms des equipements prenant en charge le protocole PPP. 
	 * @return Les noms des appareils PPP disponibles.
	 */
	public String[] getPPPDeviceNames();
	
	/**
	 * Met un terme a une connexion.
	 * @param entry Entree a deconnecte
	 * @return true si la deconnexion de l'entree c'est passee correctement.
	 */
	public boolean hangUp(String entry);
	
	/**
	 * Renomme l'entree en premier parametre, avec le second parametre.
	 * @param entry Modele d'entree a renommer
	 * @param newName Nouveau nom du modele.
	 */
	public boolean renameEntry(EntryModel entry,String newName);
	
	/**
	 * Efface l'entree en parametre.
	 * @param entryName Nom de l'entree a supprimer.
	 * @return true si l'entree en parametre a ete supprime.
	 */
	public boolean removeEntry(String entryName);
		
	/**
	 * Affecte un temps limite de tentatives d'appel.
	 * @param time Temps d'arret, en ms, d'une connexion en cours d'etablissement.
	 */
	public void setDialTimeOut(int time);
	
	/**
	 * Affecte un temps limite d'appel.
	 * @param time Temps d'arret, en ms, d'une connexion.
	 */
	public void setHangUpTimeout(int time);
	
	/**
	 * Modifie l'entree de nom entryName, en lui affectant un nom utilisateur et password.
	 * @param entry Nom de l'entree a modifier.
	 * @param idleTimeout Temps d'arret due a une inactivite.
	 * @return 0 si l'operation s'est deroule avec succes. 
	 */
	 
	public int setProperties(EntryModel entry,int idleTimeout);
	
	/**
	 * Modifie le modele entry, en lui affectant un nom utilisateur et password.
	 * @param entry Modele de l'entree a modifier.
	 * @param userName Nom utilisateur a affecter.
	 * @param password Mot de passe a affecter.
	 * @return 0 si l'operation s'est deroule avec succes.
	 */
	public int setProperties(EntryModel entry,String userName,String password);
	
	/**
	 * Modifie le modele de l'entree entry, en lui affectant un temps d'arret suite a une inactivite, un nom utilisateur et password.
	 * @param entry Modele de l'entree a modifier.
	 * @param idleTimeout Temps d'arret due a une inactivite. 
	 * @param userName Nom utilisateur a affecter.
	 * @param password Mot de passe a affecter.
	 * @return 0 si l'operation s'est deroule avec succes.
	 */
	public int setProperties(EntryModel entry,int idleTimeout,String userName,String password);	
	
	
	
	public void renewNetworkInterfaces();
}
