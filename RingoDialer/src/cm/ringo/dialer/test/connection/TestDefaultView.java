package cm.ringo.dialer.test.connection;

import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import cm.enterprise.software.exception.ProgramException;
import cm.enterprise.software.helper.Application;
import cm.ringo.dialer.interfaces.Connection;

/**
 * Classe de test de la vue par defaut de connexion
 * @author Charles Mouté
 * @version 1.0.1, 8/8/2010
 */
public class TestDefaultView {

	private Connection module;
	private JFrame frame ;
	
	public TestDefaultView(){
		
		try {
			//Definition de l'application de test.
			Application.addModule(Connection.class);
			Application.initModules();
			Application.setCurrentLanguage(Locale.getDefault().getLanguage());
			
			module = Application.getModule(Connection.class);
			
		} catch (ProgramException e) {
			e.printStackTrace();
		}
	}
	
	public void test(){
		
		frame = new JFrame("Test ConnectionDefaultView");
		frame.setLocation(250, 250);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		module.installDefaultView();
		JPanel contentPanel = module.getContentOfDefaultView();
		frame.add(contentPanel);

		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
		
	}
	
	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				TestDefaultView soft = new TestDefaultView();
				soft.test();
			}
		});
	}

}
