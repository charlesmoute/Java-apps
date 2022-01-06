package cm.ringo.dialer.component.model.base.connection;

import java.beans.PropertyChangeListener;

import cm.enterprise.software.component.model.base.Model;
import cm.ringo.dialer.helper.connection.constants.ConnectionConstants;

/**
 * Ce modele gere les parametres d'une entree de connexion. Il est un sous modele
 * d'une connexion.
 *  
 * @author Charles Moute
 * @version 1.0.1, 5/8/2010
 */

public class EntryModel extends Model {
	
	/**
	 * Nom de l'entree de connexion
	 */
	private String entryName ;
	
	/**
	 * Taille des paquets recus en byte
	 */
	private long bytesReceived = 0;
	
	/**
	 * Taille des paquets transmis en byte
	 */
	private long bytesTransmitted = 0;
	
	/**
	 * Vitesse, en bytes/S, de la connexion a laquelle l'entree est liee
	 */
	private long connectionspeed = 0;
	
	/**
	 * Represente le temps de la connection depuis l'etablissement de la connexion de l'entree.
	 */
	private String time = "00:00:00";
	
	/**
	 * L'etat de l'entree de connexion. Trois valeurs possibles :
	 * <ul>
	 * <li>INACTIVE_STATE (0) : Etat inactif</li> 
	 * <li>DIALING_STATE  (1) : Etat en cours d'appel</li>
	 * <li>ACTIVE_STATE   (2) : Etat actif</li>
	 * </ul>
	 */
	private int status = ConnectionConstants.INACTIVE_STATE ;
	
	public EntryModel(String entryName){
		this.entryName = entryName ;
	}
	
	/**
	 * Permet d'intialiser les parametres de l'entree de connexion
	 */
	private void initEntryParameter(){
		this.setBytesReceived(0);
		this.setBytesTransmitted(0);
		this.setConnectionSpeed(0);
		this.setTime("00:00:00");
	}
	
	/**
	 * Affecte le parametre comme nom de l'entree de la connection.
	 * @param entryName Nom d'entree de connexion.
	 */
	public void setEntryName(String entryName) {		
		String oldValue = this.entryName;
		this.entryName = entryName;
		this.firePropertyChange("entryName", oldValue,entryName);
	}

	/**
	 * Permet d'obtenir le nom de l'entree de connexion
	 * @return Le nom associe a l'entree de connexion.
	 */
	public String getEntryName() {
		return entryName;
	}

	/**
	 * Affecte le nombre de bytes recus par l'entree de connexion.
	 * @param bytesReceived Le nombre de bytes recus par l'entree de connexion.
	 */
	public void setBytesReceived(long bytesReceived) {
		if(bytesReceived<0) return ;
		long oldValue = this.bytesReceived;
		this.bytesReceived = bytesReceived;
		this.firePropertyChange("bytesReceived", oldValue, bytesReceived);
	}

	/**
	 * Permet d'obtenir le nombre de bytes recus par l'entree de connexion
	 * @return Le nombre de bytes recus par l'entree de connexion  
	 */
	public long getBytesReceived() {
		return bytesReceived;
	}

	/**
	 * Affecte le nombre de bytes transmis par l'entree de connexion
	 * @param bytesTransmitted Nombre de bytes recus pas l'entree de connexion.
	 */
	public void setBytesTransmitted(long bytesTransmitted) {
		if(bytesTransmitted<0) return ;
		long oldValue = this.bytesTransmitted;
		this.bytesTransmitted = bytesTransmitted;
		this.firePropertyChange("bytesTransmitted", oldValue, bytesTransmitted);
		
	}

	/**
	 * Permet d'obtenir les bytes transimis par l'entree de connexion
	 * @return Les bytes transmis par l'entree de connexion
	 */
	public long getBytesTransmitted() {
		return bytesTransmitted;
	}

	/**
	 * Affecte la vitesse associe a la connexion de l'entree
	 * @param connectionspeed La vitesse associe a la connexion de l'entree
	 */
	public void setConnectionSpeed(long connectionspeed) {
		if(connectionspeed<0) return;
		long oldValue = this.connectionspeed;
		this.connectionspeed = connectionspeed;
		this.firePropertyChange("connectionspeed", oldValue, connectionspeed);
	}

	/**
	 * Permet d'obtenir la vitesse associe a la connexion de l'entree
	 * @return Vitesse associe a la connexion de l'entree
	 */
	public long getConnectionSpeed() {
		return connectionspeed;
	}
	
	/**
	 * Permet d'affecter le temps de connection ecoule a l'entree
	 * @param time Temps ecoule depuis l'etablissement de la connexion de l'entree.
	 */
	public void setTime(String time){
		String oldValue = this.time ;
		this.time = time ;
		this.firePropertyChange("time", oldValue, time);
	}
	
	/**
	 * Permet d'obtenir le temps ecoule depuis l'etablissement de la connexion
	 * @return Le temps ecoule depuis l'etablissement de la connexion.
	 */
	public String getTime() { return this.time; }
	
	/**
	 * Affecte l'etat de l'entree de connexion. Trois valeurs possibles :
	 * <ul>
	 * 	<li>INACTIVE_STATE (0) : Etat inactif</li> 
	 * 	<li>DIALING_STATE  (1) : Etat en cours d'appel</li>
	 * 	<li>ACTIVE_STATE   (2) : Etat actif</li>
	 * </ul> 
	 * Ces variables sont disponibles dans ConnectionConstants.
	 * @param status Status de l'entree de connexion.
	 * @see cm.ringo.dialer.helper.connection.constants.ConnectionConstants
	 */
	public void setStatus(int status){
		
		if(status!=ConnectionConstants.INACTIVE_STATE && status!=ConnectionConstants.DIALING_STATE&& status!=ConnectionConstants.ACTIVE_STATE)
			return ;
		
		this.initEntryParameter();
		int oldValue = this.status;
		this.status = status ;
		this.firePropertyChange("status", oldValue, status);		
	}
	
	/**
	 * Permet d'obtenir le status courant de l'entree de connexion.
	 * @return Le status de l'entree de connexion.
	 */
	public int getStatus(){ return this.status; }

	
	/**
	 * Ajoute un ecouteur du changement du nom de l'entree de la connexion
	 * @param listener Ecouteur pour le changement du nom de l'entree de connexion
	 */
	public void addEntryNameChangeListener(PropertyChangeListener listener){
		 if(listener==null) return ;
		 this.addPropertyChangeListener("entryName", listener);
	}
	
	/**
	 * Efface l'ecouteur en parametre de la liste des ecouteurs du changement du nom de
	 * l'entree de connexion
	 * @param listener Ecouteur a effacer.
	 */
	public void removeEntryNameChangeListener(PropertyChangeListener listener){
		if(listener==null) return ;
		this.removePropertyChangeListener("entryName",listener);
	}
	
	/**
	 * Ajoute un ecouteur du changement des bytes recus
	 * @param listener Ecouteur a ajouter
	 */
	public void addBytesReceivedChangeListener(PropertyChangeListener listener){
		 if(listener==null) return ;
		 this.addPropertyChangeListener("bytesReceived", listener);
	}
	
	
	/**
	 * Efface un ecouteur de la liste de ecouteurs du changement du taux de bytes recus.
	 * @param listener Ecouteur a supprimer.
	 */
	public void removeBytesReceivedChangeListener(PropertyChangeListener listener){
		if(listener==null) return ;
		this.removePropertyChangeListener("bytesReceived",listener);
	}
	
	/**
	 * Ajoute un ecouteur a la liste des ecouteurs du changement du taux de bytes transmis.
	 * @param listener Ecouteur a ajouter.
	 */
	public void addBytesTransmittedChangeListener(PropertyChangeListener listener){
		 if(listener==null) return ;
		 this.addPropertyChangeListener("bytesTransmitted", listener);
	}
	
	/**
	 * Efface l'ecouteur en parametre de la liste des ecouteurs du changement du aux de bytes
	 * transmis.
	 * @param listener Ecouteur a supprimer.
	 */
	public void removeBytesTransmittedChangeListener(PropertyChangeListener listener){
		if(listener==null) return ;
		this.removePropertyChangeListener("bytesTransmitted",listener);
	}
	
	/**
	 * Ajoute un ecouteur a la liste des ecouteurs du changement de vitesse de connection
	 * @param listener Ecouteur a ajouter
	 */
	public void addConnectionSpeedChangeListener(PropertyChangeListener listener){
		 if(listener==null) return ;
		 this.addPropertyChangeListener("connectionspeed", listener);
	}
	
	/**
	 * Efface de le parametre de la liste des ecouteurs du changement de vitesse de connection
	 * @param listener Ecouteur a supprimer.
	 */
	public void removeConnectionSpeedChangeListener(PropertyChangeListener listener){
		if(listener==null) return ;
		this.removePropertyChangeListener("connectionspeed",listener);
	}
	
	/**
	 * Ajoute un ecouteur a la liste des ecouteurs du changement de status
	 * @param listener Ecouteur a ajouter
	 */
	public void addStatusChangeListener(PropertyChangeListener listener){
		 if(listener==null) return ;
		 this.addPropertyChangeListener("status", listener);
	}
	
	/**
	 * Efface de le parametre de la liste des ecouteurs du changement de status de l'entree de connection
	 * @param listener Ecouteur a supprimer.
	 */
	public void removeStatusChangeListener(PropertyChangeListener listener){
		if(listener==null) return ;
		this.removePropertyChangeListener("status",listener);
	}
	
	/**
	 * Ajoute un ecouteur de la duree de connection au modele
	 * @param listener Ecouteur a ajouter
	 */
	public void addTimeChangeListener(PropertyChangeListener listener){
		 if(listener==null) return ;
		 this.addPropertyChangeListener("time", listener);
	}
	
	/**
	 * Efface un ecouteur de la duree de connexion du modele
	 * @param listener Ecouteur a supprimer.
	 */
	public void removeTimeChangeListener(PropertyChangeListener listener){
		if(listener==null) return ;
		this.removePropertyChangeListener("time",listener);
	}
}
