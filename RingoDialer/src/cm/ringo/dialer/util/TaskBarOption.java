package cm.ringo.dialer.util;

import java.awt.MenuItem;
import java.awt.event.ActionListener;

/**
 * Represente les options du menu associes a la barre des taches
 * @author Charles Moute
 * @version 1.0.1, 4/8/2010
 */

public class TaskBarOption extends MenuItem {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Cree une option de la barre de tache de libelle title et d'ecouteur
	 * d'action sur cette option listener.
	 * 
	 * @param title Libelle de l'option
	 * @param listener Ecouteur des actions sur l'option
	 */
	public TaskBarOption(String title,ActionListener listener){
		super(title);
		this.addActionListener(listener);
	}

}
