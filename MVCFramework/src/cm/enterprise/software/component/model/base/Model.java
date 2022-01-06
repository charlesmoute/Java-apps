package cm.enterprise.software.component.model.base;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.event.EventListenerList;
import cm.enterprise.software.component.model.listener.Listener;
import cm.enterprise.software.component.model.listener.AnyListener;

/**
 * Model represente l'ossature que doit avoir tous les models qui seront implementes.
 * @author Charles Moute
 * @version 28/06/2010, version 1.0.1
 */
public abstract class Model {

	/**
	 * Represente la liste des ecouteurs du model
	 */
	private EventListenerList listeners = new EventListenerList(); 
		
	/**
	 * Support de changement des proprietes du modele courant.
	 */
	private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
	
	/**
	 * Ajoute un ecouteur listener de type le premier parametre.
	 * @param type Type de l'ecouteur a ajouter
	 * @param listener Ecouteur a ajouter
	 */
	public <T extends Listener> void addListener(Class<T> type,T listener){
		if(type==null || listener==null) return;
		listeners.add(type, listener);
	}
	
	/**
	 * Efface l'ecouteur de listener instance de la classe T
	 * @param type Type de l'ecouteur a effacer 
	 * @param listener Ecouteur a effacer
	 */
	public <T extends Listener> void removeListener(Class<T> type, T listener){
		if(type==null || listener==null) return;
		listeners.remove(type, listener);
	}
		
	/**
	 * Ajoute un ecouteur sur le changement de n'importe quel informations.
	 * @param listener Ecouteur a ajouter
	 */
	public void addAnyChangeListener(AnyListener listener){
		if(listener==null) return;
		listeners.add(AnyListener.class,listener);
	}
	
	/**
	 * Efface l'ecouteur de n'importe quel informations en parametre.
	 * @param listener Ecouteur a effacer
	 */
	public void  removeAnyChangeListener(AnyListener listener){
		if(listener==null) return;
		listeners.remove(AnyListener.class, listener);
	}
	
	/**
	 * Propage n'importe quel changement a travers l'objet infos. 
	 * @param infos Objet vehiculant n'importe quelle information.
	 */
	protected void fireAnyChange(Object infos){
		AnyListener[] listenerList;
		listenerList = (AnyListener[])listeners.getListeners(AnyListener.class);
		for(AnyListener listener: listenerList){
			listener.anyChanged(new cm.enterprise.software.component.model.event.AnyChangedEvent(this,infos));
		}
	}
	
	/**
	 * Retourne la liste des ecouteurs du model controlle.
	 * @return La liste des ecouteurs du modele
	 */
	public EventListenerList getListenerList(){
		return listeners;
	}
	
	/**
	 * Retourne la liste des ecouteurs du model controlle, de type la classe en parametre.
	 * @param type Type des ecouteurs a retourner.
	 * @return La liste des ecouteurs de type le premier parametre.
	 */
	public <T extends Listener> T[] getListeners(Class<T> type){ 
		return listeners.getListeners(type);
	}	
	
	/**
	 * Ajoute un ecouteur de changement des proprietes du modele
	 * @param listener Ecouteur a ajouter.
	 * @see #getPropertyChangeListeners()
	 * @see #removePropertyChangeListener(PropertyChangeListener)
	 */
	protected void addPropertyChangeListener(PropertyChangeListener listener) {
		if (listener == null)  return ;
		changeSupport.addPropertyChangeListener(listener);
	}
	
	/**
	 * Efface un ecouteur de changement des proprietes du modele
	 * @param listener Ecouteur a supprimer.
	 * @see #addPropertyChangeListener(PropertyChangeListener)
	 * @see #getPropertyChangeListeners()
	 */
	protected void removePropertyChangeListener(PropertyChangeListener listener) {
		if (listener == null) return;
		changeSupport.removePropertyChangeListener(listener);
	}
	
	/**
	 * Permet d'obtenir la liste des ecouteurs de changement des proprietes du model. 
	 * @return La liste des ecouteurs de changement des proprietes du model.
	 * @see #addPropertyChangeListener(PropertyChangeListener)
	 * @see #removePropertyChangeListener(PropertyChangeListener)
	 */
	protected PropertyChangeListener[] getPropertyChangeListeners() {
        return changeSupport.getPropertyChangeListeners();
    }
    
    /**
     * Ajoute un ecouteur, listener, de changement de la propriete propriete, propertyName, du model
     * @param propertyName Propriete dont on veut ecouter les changements
     * @param listener Ecouteur de la propriete de nom le premier parametre
     */
	protected void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		if (listener == null) return;
		changeSupport.addPropertyChangeListener(propertyName, listener);
    }
    
    /**
     * Efface l'ecouteur, listener, de changement de la propriete, propertyName
     * @param propertyName Nom de la propriete dont les changements sont en ecoutes
     * @param listener Ecouteur de changement de propertyName a supprimer.
     */
	protected void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		if (listener == null) return;
		changeSupport.removePropertyChangeListener(propertyName, listener);
    }

    /**
     * Obtient la liste des ecouteurs de changement de la propriete propertyName
     * @param propertyName Nom de la propriete dont on veut obtenir les ecouteurs
     * @return La liste des ecouteurs de changement de la propriete propertyName
     */
	protected PropertyChangeListener[] getPropertyChangeListeners(String propertyName) {
		return changeSupport.getPropertyChangeListeners(propertyName);
    }
    
    /**
     * Propegae le changement de la propriete propertName, de oldValue a newValue
     * @param propertyName Nom de la propriete a propager le changement
     * @param oldValue Ancienne valeur de propertyName
     * @param newValue Nouvelle valeur de propertyName
     */
    protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
    	if ((oldValue != null && newValue != null && oldValue.equals(newValue))) return;
    	changeSupport.firePropertyChange(propertyName, oldValue, newValue);
    }

    /**
     * Propage le changement de la propriete propertName, de oldValue a newValue
     * @param propertyName Nom de la propriete a propager le changement
     * @param oldValue Ancienne valeur de propertyName
     * @param newValue Nouvelle valeur de propertyName
     */
    protected void firePropertyChange(String propertyName, boolean oldValue, boolean newValue) {
    	if (oldValue == newValue) return;
    	changeSupport.firePropertyChange(propertyName, oldValue, newValue);
    }
    
    /**
     * Propage le changement de la propriete propertName, de oldValue a newValue
     * @param propertyName Nom de la propriete a propager le changement
     * @param oldValue Ancienne valeur de propertyName
     * @param newValue Nouvelle valeur de propertyName
     */
    protected void firePropertyChange(String propertyName, int oldValue, int newValue) {
    	if (oldValue == newValue) return;
    	changeSupport.firePropertyChange(propertyName, oldValue, newValue);
    }
    
    /**
     * Propage le changement de la propriete propertName, de oldValue a newValue
     * @param propertyName Nom de la propriete a propager le changement
     * @param oldValue Ancienne valeur de propertyName
     * @param newValue Nouvelle valeur de propertyName
     */
    protected void firePropertyChange(String propertyName, byte oldValue, byte newValue) {
    	if (oldValue == newValue) return;
    	changeSupport.firePropertyChange(propertyName, oldValue, newValue);
    }
    
    /**
     * Propage le changement de la propriete propertName, de oldValue a newValue
     * @param propertyName Nom de la propriete a propager le changement
     * @param oldValue Ancienne valeur de propertyName
     * @param newValue Nouvelle valeur de propertyName
     */
    protected void firePropertyChange(String propertyName, char oldValue, char newValue) {
    	if (oldValue == newValue) return;
    	changeSupport.firePropertyChange(propertyName, oldValue, newValue);
    }
    
    /**
     * Propage le changement de la propriete propertName, de oldValue a newValue
     * @param propertyName Nom de la propriete a propager le changement
     * @param oldValue Ancienne valeur de propertyName
     * @param newValue Nouvelle valeur de propertyName
     */
    protected void firePropertyChange(String propertyName, short oldValue, short newValue) {
    	if (oldValue == newValue) return;
    	changeSupport.firePropertyChange(propertyName, oldValue, newValue);
    }
    
    /**
     * Propage le changement de la propriete propertName, de oldValue a newValue
     * @param propertyName Nom de la propriete a propager le changement
     * @param oldValue Ancienne valeur de propertyName
     * @param newValue Nouvelle valeur de propertyName
     */
    protected void firePropertyChange(String propertyName, long oldValue, long newValue) {
    	if (oldValue == newValue) return;
    	changeSupport.firePropertyChange(propertyName, oldValue, newValue);
    }
    
    /**
     * Propage le changement de la propriete propertName, de oldValue a newValue
     * @param propertyName Nom de la propriete a propager le changement
     * @param oldValue Ancienne valeur de propertyName
     * @param newValue Nouvelle valeur de propertyName
     */
    protected void firePropertyChange(String propertyName, float oldValue, float newValue) {
    	if (oldValue == newValue) return;
    	changeSupport.firePropertyChange(propertyName, oldValue, newValue);
    }
    
    /**
     * Propage le changement de la propriete propertName, de oldValue a newValue
     * @param propertyName Nom de la propriete a propager le changement
     * @param oldValue Ancienne valeur de propertyName
     * @param newValue Nouvelle valeur de propertyName
     */
    protected void firePropertyChange(String propertyName, double oldValue, double newValue) {
    	if (oldValue == newValue) return;
    	changeSupport.firePropertyChange(propertyName, oldValue, newValue);
    }
}
