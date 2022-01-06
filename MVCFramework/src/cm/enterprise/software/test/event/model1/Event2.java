package cm.enterprise.software.test.event.model1;

import cm.enterprise.software.component.model.event.Event;
import cm.enterprise.software.test.model.model1.Model1;

/**
 * Evenement du model 1 
 * @author Charles Moute
 * @version 1.0.1, 16/07/2010
 */
public class Event2 extends Event {

	private static final long serialVersionUID = 1L;

	/**
	 * Nouvelle valeur d'attribut pour une instance de Model1
	 */
	private String attribute;
	
	/**
	 * Nouvelle valeur pour une instance de Model1  
	 */
	private Integer value;
	
	
	/**
	 * Instancie un evenement pour Model1 avec les nouvelles valeurs en parametre.
	 * @param source Source de l'evement
	 * @param attrib Nouveau parametre 1 de la source
	 * @param val Nouveau parametre 2 de la source
	 */
	public Event2(Model1 source, String attrib,Integer val) {
		super(source);
		this.attribute = attrib ;
		this.value = val ;
	}
	
	/**
	 * Permet d'obtenir la nouvelle valeur d'une instance de Model1
	 * @return Valeur attribut.
	 */
	public String getAttribute() { return this.attribute; }
	
	/**
	 * Permet d'obtenir la valeur d'une instance de Model1
	 * @return Valeur d'une instance de Model1
	 */
	public int getValue() { return this.value; }

}
