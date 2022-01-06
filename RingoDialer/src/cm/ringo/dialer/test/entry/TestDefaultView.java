package cm.ringo.dialer.test.entry;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import cm.enterprise.software.exception.ProgramException;
import cm.enterprise.software.helper.Application;
import cm.ringo.dialer.component.controller.connection.EntryController;
import cm.ringo.dialer.component.model.base.connection.EntryModel;
import cm.ringo.dialer.component.util.connection.Horloge;
import cm.ringo.dialer.component.util.connection.RingoPanel;
import cm.ringo.dialer.component.view.connection.entry.EntryDefaultView;
import cm.ringo.dialer.interfaces.Connection;


/**
 * Classe de test de la vue par defaut d'une entree de connexion.
 * @author Charles Mouté
 * @version 1.0.1, 8/8/2010
 */

public class TestDefaultView {

	private EntryController controller;
	private JFrame frame ;
	
	Object obj = new Object(); //juste pour permettre la synchronisation
	
	public TestDefaultView(){
		try {
			//Definition de l'application de test.
			Application.addModule(Connection.class);
			Application.initModules();
			Application.setCurrentLanguage(Locale.getDefault().getLanguage());
			
			//En temps normal ainsi definit c'est connection que l'on devrait gerer mais coe on veut tester entry
			
			controller = new EntryController(new EntryModel("Test"));
			controller.installView(EntryDefaultView.class,controller);
			controller.getViewOfClass(EntryDefaultView.class).initView();
			
		} catch (ProgramException e) {
			e.printStackTrace();
		}
	}
	
	int count = 1 ;
	
	ActionListener task  = new ActionListener(){
		public void actionPerformed(ActionEvent evt){
	
			
			EntryModel model = (EntryModel)controller.getModel();
			//synchronized(model){
				model.setEntryName("Test"+count);
				model.setBytesReceived(model.getBytesReceived()+1);
				model.setBytesTransmitted(model.getBytesTransmitted()+2);
				model.setConnectionSpeed(model.getConnectionSpeed()+3);
				model.setTime(horloge.getTime());
				count++;
			//}
		}
	};
	
	Horloge horloge ;
	
	public void test(){
		
		frame = new JFrame("Test EntryDefaultView");
		frame.setLocation(250, 250);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel vue = (JPanel) controller.getContentOf(EntryDefaultView.class);
		//JPanel contentPanel = (JPanel) controller.getContentOf(EntryDefaultView.class);
		RingoPanel contentPanel = new RingoPanel(vue);
		frame.add(contentPanel);
		
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
		
		horloge = new Horloge(task);// Tache repete toutes les secondes
		horloge.start();
	}
	
	public static void main(String[] arg){
	
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				TestDefaultView soft = new TestDefaultView();
				soft.test();
			}
		});
	}
	
}
