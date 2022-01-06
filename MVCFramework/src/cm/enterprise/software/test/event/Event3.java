package cm.enterprise.software.test.event;

import cm.enterprise.software.component.model.event.Event;
import cm.enterprise.software.test.model.SuperModelTest;

/**
 * Evenement du modele SuperModelTest
 * @author Charles Mouté
 * @version 1.0.1, 17/07/2010
 */
public class Event3 extends Event {

	private static final long serialVersionUID = 1L;

	/**
	 * Attribut d'une instance de super modele
	 */
	private String attribut ;
	
	/**
	 * Instancie un evenement dont la source le modele
	 * @param source
	 * @param newAttribut
	 */
	public Event3(SuperModelTest source,String newAttribut) {
		super(source);
		this.attribut = newAttribut ;
	}
	
	/**
	 * Permet d'obtenir le nouveau attribut de l'instance du SuperModelTest
	 * @return Nouvelle valeur de l'attribut
	 */
	public String getNewAttribut(){ return this.attribut; }

}
