package cm.enterprise.software.test.model.model2;

import cm.enterprise.software.component.model.base.Model;
import cm.enterprise.software.test.event.model2.Event1;
import cm.enterprise.software.test.listener.model2.Event1Listener;

/**
 * Represente un exemple de modele
 * @author Charles Mouté
 * @version 1.0.1, 17/07/2010
 */
public class Model2 extends Model {

	/**
	 * Valeur du modele 2
	 */
	private String value = null;
	
	/**
	 * Cree une instance vide du Model2
	 */
	public Model2(){
		this(null);
	}
	/**
	 * Creer une instance du model 2 avec le parametre comme la valeur du modele
	 * @param value Valeur du modele
	 */
	public Model2(String value){
		this.setValue(value);
	}
	
	/**
	 * Permet d'obtenir la valeur du modele2
	 * @return Valeur du modele
	 */
	public String getValue() { return this.value; }
	
	/**
	 * Affecte une valeur au modele.
	 * @param value Nouvelle valeur du modele
	 */
	public void setValue(String value) { 
		this.value = value;
		this.fireEvent1Change();
	}
	
	/**
	 * Ajoute un ecouteur d'evenement 1 au model
	 * @param listener Ecouteur a ajouter
	 */
	public void addEvent1Listener(Event1Listener listener){
		this.addListener(Event1Listener.class, listener);
	}
	
	/**
	 * Efface l'ecouteur en parametre de la liste des ecouteurs de l'evenenement Event1
	 * @param listener Ecouteur a effacer.
	 */
	public void removeEvent1Listener(Event1Listener listener){
		this.removeListener(Event1Listener.class, listener);
	}
	
	/**
	 * Propage la modification de la valeur de l'attribut du model.
	 */
	public void fireEvent1Change(){
		Event1Listener[] listenerList;
		listenerList = (Event1Listener[])this.getListeners(Event1Listener.class);
		for(Event1Listener listener: listenerList){
			listener.event1Changed(new Event1(this,this.getValue()));			
		}
	}
}
