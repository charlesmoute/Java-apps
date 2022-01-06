package cm.enterprise.software.test.model.model1;

import cm.enterprise.software.component.model.base.Model;
import cm.enterprise.software.test.event.model1.Event2;
import cm.enterprise.software.test.listener.model1.Event2Listener;

/**
 * Represente un exemple de modele
 * @author Charles Moute
 * @version 1.0.1, 17/07/2010
 */
public class Model1 extends Model {

	/**
	 * Attribut du Model1
	 */
	private String attribute = null ;
	
	/**
	 * Valeur du Model1 
	 */
	private Integer value= 0 ;
	
	/**
	 * Cronstruit une instance vide du Model1
	 */
	public Model1(){
		this.setAttributes(null,0);
	}
	/**
	 * Construit une instance du Model1
	 * @param attrib Nom de l'attribut du Model1
	 * @param val Valeur de l'attribut
	 */
	public Model1(String attrib,Integer val){
		this.setAttributes(attrib,val);
	}
	
	/**
	 * Retourne la valeur de l'attribut de l'instance courante du Model1
	 * @return Valeur de l'attribut
	 */
	public Integer getValue() { return this.value; }
	
	/**
	 * Permet d'obtenir le nom  de l'attribut du Model1
	 * @return Nom de l'attribut.
	 */
	public String getAttribut() { return this.attribute; }
	
	/**
	 * Affecte une nom d'attribut avec une valeur au Model1
	 * @param attrib Nom du nouvel attricbut.
	 * @param val Valeur de l'attribut.
	 */
	public void setAttributes(String attrib,Integer val) {
		this.attribute = attrib ;
		this.value = val;
		this.fireEvent2Change();
	}
	
	/**
	 * Ajoute un ecouteur de l'evenement Event2 au modele
	 * @param listener Ecouteur a ajouter
	 */
	public void addEvent2Listener(Event2Listener listener){
		this.addListener(Event2Listener.class, listener);
	}
	
	/**
	 * Efface un ecouteur de la liste des ecouteurs de l'evenement Event2
	 * @param listener Ecouteur a supprimer.
	 */
	public void removeEvent2Listener(Event2Listener listener){
		this.removeListener(Event2Listener.class, listener);
	}
	
	/**
	 * Propage L'evenement de la modification d'un ou des deux parametres du couple
	 * (attribut,value) du modele.
	 */
	public void fireEvent2Change(){
		Event2Listener[] listenerList;
		listenerList = (Event2Listener[])this.getListeners(Event2Listener.class);
		for(Event2Listener listener: listenerList){
			listener.event2Changed(new Event2(this,this.getAttribut(),this.getValue()));			
		}
	}
}
