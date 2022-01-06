package cm.ringo.dialer.test.connection;

import java.util.Locale;

import javax.swing.SwingUtilities;

import cm.enterprise.software.exception.ProgramException;
import cm.enterprise.software.helper.Application;
import cm.ringo.dialer.interfaces.Connection;

/**
 * Classe de test des information d'une connexion
 * @author Charles Mouté
 * @version 1.0.1, 8/8/2010
 */
public class TestInfosView {

	private Connection module;
	
	public TestInfosView(){
		//Definition de l'application de test.
		try {
			
			Application.addModule(Connection.class);
			Application.initModules();
			Application.setCurrentLanguage(Locale.getDefault().getLanguage());
			
		} catch (ProgramException e) {
			e.printStackTrace();
		}
		
		module = Application.getModule(Connection.class);
		module.installInfosView();
	}
	
	public void test(){
		module.showInfosView();
	}
	
	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				TestInfosView soft = new TestInfosView();
				soft.test();
			}
		});
	}

}
