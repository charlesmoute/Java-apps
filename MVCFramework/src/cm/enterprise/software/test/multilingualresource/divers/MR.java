package cm.enterprise.software.test.multilingualresource.divers;

import cm.enterprise.software.exception.ProgramException;
import cm.enterprise.software.util.MultilingualResource;

/**
 * Cette classe definit une ressource multilingue sans ressource initiale.
 * Nous ajouterons les ressources composantes depuis l'exterieur et configurerons
 * la ressource depuis l'exterieur.
 * @author Charles Moute
 * @version 1.0.1, 19/07/2010
 */
public class MR extends MultilingualResource {

	public MR() throws ProgramException {
		super();		
	}

	@Override
	protected void load(String language) throws ProgramException {
		// TODO Auto-generated method stub
		
	}

}
