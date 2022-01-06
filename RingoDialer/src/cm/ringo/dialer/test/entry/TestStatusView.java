package cm.ringo.dialer.test.entry;

import java.util.Locale;

import javax.swing.*;
import java.awt.event.*;

import cm.enterprise.software.exception.ProgramException;
import cm.enterprise.software.helper.Application;
import cm.ringo.dialer.component.controller.connection.EntryController;
import cm.ringo.dialer.component.model.base.connection.EntryModel;
import cm.ringo.dialer.component.util.connection.Horloge;
import cm.ringo.dialer.component.view.connection.entry.EntryStatusView;
import cm.ringo.dialer.interfaces.Connection;

/**
 * Classe de test de la vue du Status d'une entree de connexion
 * @author Charles Mouté
 * @version 1.0.1, 7/8/2010
 */
public class TestStatusView {

	private EntryController controller ;
	
	JFrame frame ;
	
	Object obj = new Object(); //juste pour permettre la synchronisation
	
	public TestStatusView() throws ProgramException{
		
		//Imperatif car certaines parametres de entree connection viennent de la
		Application.addModule(Connection.class);
		Application.initModules();
		Application.setCurrentLanguage(Locale.getDefault().getLanguage());
		
		controller = new EntryController(new EntryModel("Test"));
		controller.installView(EntryStatusView.class,controller);
	}

	
	ActionListener task  = new ActionListener(){
		public void actionPerformed(ActionEvent evt){
	
			EntryModel model = (EntryModel)controller.getModel();
			
			synchronized(obj){
				int status = model.getStatus();
				status = (status+1)%3;
				model.setStatus(status);
			}
		}
	};
	
	Horloge horloge ;
	
	public void test(){
		frame = new JFrame("Test de la vue du status");
		frame.add((JLabel)controller.getContentOf(EntryStatusView.class));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(250, 250, 300, 80);
		frame.setResizable(false);
		frame.setVisible(true);
	
		horloge = new Horloge(5000,task);// Tache repete toutes les 5 secondes
		horloge.start();
		
	}
	
	public static void main(String[] arg){
		
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				TestStatusView soft;
				try {
					soft = new TestStatusView();
					soft.test();
				} catch (ProgramException e) {
					e.printStackTrace();
				}
			}
		});
	}
}
