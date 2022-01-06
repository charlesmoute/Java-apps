package cm.enterprise.software.test.model;

import cm.enterprise.software.component.model.base.SuperModel;
import cm.enterprise.software.exception.ProgramException;
import cm.enterprise.software.test.event.Event3;
import cm.enterprise.software.test.listener.Event3Listener;
import cm.enterprise.software.test.model.model1.Model1;
import cm.enterprise.software.test.model.model2.Model2;

/**
 * Represente un super modele de test
 * @author Charles Moute
 * @version 1.0.1, 17/07/2010
 */

public class SuperModelTest extends SuperModel {

	private String attribute = null ;
	
	/**
	 * Cree une instance vide du super modele.
	 * @throws ProgramException
	 */	
	public SuperModelTest() throws ProgramException{
		this(null);
	}
	
	/**
	 * Cree une instance de SuperModelTest avec comme valeur de 
	 * l'attribut le parametre.
	 * @param valueOfAttribute Valeur de l'attribut du super modele de test
	 * @throws ProgramException
	 */
	@SuppressWarnings("unchecked")
	public SuperModelTest(String valueOfAttribute) throws ProgramException{
		//Ajout des classes de sous modeles au super modele
		this.addClassOfModel(Model1.class,Model2.class);
		//Instanciation de ces sous modeles
		this.installSubModel(Model1.class, "",0);
		this.installSubModel(Model2.class, "");
		this.setValueToAttribute(valueOfAttribute);
	}
	
	/**
	 * Affecte une nouvellle valeur a l'attribut
	 * @param newAttribute Nouvelle valeur de l'attribut
	 */
	public void setValueToAttribute(String newAttribute){
		this.attribute = newAttribute ;
		this.fireEvent3Change();
	}
	
	
	/**
	 * Retourne la valeur de l'attribut du super modele
	 * @return La valeur de l'attribut
	 */
	public String getValueOfAtttrribute() { return this.attribute; }
	
	/**
	 * Affecte de nouvelles valeurs au model 1 du super model
	 * @param attrib New Attribute
	 * @param val New Value
	 */
	public void setValueToModel1(String attrib,Integer val){
		Model1 m = this.getSubModelList(Model1.class)[0];
		m.setAttributes(attrib, val);
	}
	
	/**
	 * Permet d'obtenir l'attribut du model1
	 * @return Attribut du sous model 1
	 */
	public String getAttributeOfModel1() {
		Model1 m = this.getSubModelList(Model1.class)[0];
		return m.getAttribut();
	}
	
	/**
	 * Permet d'obtenir la valeur du model1
	 * @return Valeur du sous model 1
	 */
	public Integer getValueOfModel1() {
		Model1 m = this.getSubModelList(Model1.class)[0];
		return m.getValue();
	}
	
	/**
	 * Affecte une nouvelle valeur au model 2 du super model
	 * @param val New Value
	 */
	public void setValueToModel2(String val){
		Model2 m = this.getSubModelList(Model2.class)[0];
		m.setValue(val);
	}
	
	/**
	 * Permet d'obtenir la valeur du model2
	 * @return Valeur du sous model 2
	 */
	public String getValueOfModel2() {
		Model2 m = this.getSubModelList(Model2.class)[0];
		return m.getValue();
	}
		
	/**
	 * Ajoute une instance de EventListener a la liste des ecouteurs du super model. 
	 * @param listener Ecouteur a ajouter
	 */
	public void addEvent3Listener(Event3Listener listener){
		this.addListener(Event3Listener.class, listener);
	}
	
	/**
	 * Efface l'instance en parametre de la liste
	 * @param listener Ecouteur a effacer
	 */
	public void removeEvent3Listener(Event3Listener listener){
		this.removeListener(Event3Listener.class, listener);
	}
	
	/**
	 * Propage le changement de l'evenement 3.
	 */
	public void fireEvent3Change(){
		
		Event3Listener[] listenerList;
		listenerList = this.getListeners(Event3Listener.class);
		for(Event3Listener listener: listenerList){
			listener.event3Changed(new Event3(this,attribute));			
		}
		
	}

}
