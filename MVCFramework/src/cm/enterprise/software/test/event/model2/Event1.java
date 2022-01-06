package cm.enterprise.software.test.event.model2;

import cm.enterprise.software.component.model.event.Event;
import cm.enterprise.software.test.model.model2.Model2;

/**
 * Evenement test pour le modele 2
 * @author Charles Moute
 * @version 1.0.1, 16/07/2010
 */
public class Event1 extends Event {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Nouvelle valeur pour une instance de Model2
	 */
	private String value;
	
	/**
	 * Instancie l'evenement 1 pour le model en parametre
	 * @param source Model a l'origine de l'evenement.
	 * @param value Nouevelle valeur de l'attribut du model.
	 */
	public Event1(Model2 source, String value) {
		super(source);
		this.value = value ;
	}

	/**
	 * Permet d'obtenir la nouvelle valeur de la source.
	 * @return La nouvelle valeur de la source
	 */
	public String getValue() { return value ; }
}
