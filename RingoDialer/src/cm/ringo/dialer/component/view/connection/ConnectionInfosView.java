package cm.ringo.dialer.component.view.connection;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JWindow;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import cm.enterprise.software.component.model.event.AnyChangedEvent;
import cm.enterprise.software.exception.ProgramException;
import cm.enterprise.software.helper.Application;
import cm.ringo.dialer.component.controller.connection.ConnectionController;
import cm.ringo.dialer.component.controller.connection.EntryController;
import cm.ringo.dialer.component.util.connection.RingoPanel;
import cm.ringo.dialer.component.view.connection.entry.EntryDefaultView;
import cm.ringo.dialer.helper.connection.constants.ConnectionConstants;
import cm.ringo.dialer.helper.connection.multilingual.ConnectionMultilingualResource;
import cm.ringo.dialer.interfaces.Connection;

/**
 * Represente la vue informationnele des parametres d'une connexion
 * @author Charles Moute
 * @version 1.0.1, 6/8/2010
 */
public class ConnectionInfosView extends ConnectionView {

	private JWindow frame ;
	private JButton hideButton, conexButton ;
	
	public ConnectionInfosView(ConnectionController controller)	throws ProgramException {
		super(controller);
				
		//ici on va instancier la classe vue EntryDefaultView pour le status
		EntryController subcontroller = controller.getSubController(EntryController.class) ; 
		subcontroller.installView(EntryDefaultView.class,subcontroller);
		subcontroller.getViewOfClass(EntryDefaultView.class).initView();
		subcontroller.getViewOfClass(EntryDefaultView.class).setVisible(true);

		/*FormLayout layout = new FormLayout(
				"5dlu, center:pref, center:default:grow, center:pref, 5dlu",
				"5dlu, pref, default:grow, pref, 5dlu");
		
		layout.setColumnGroups(new int[][]{{1,5}});
		//layout.setRowGroups(new int[][]{{1,5}});
		PanelBuilder builder = new PanelBuilder(layout);

		CellConstraints cc = new CellConstraints();
		JPanel infosPanel = (JPanel) subcontroller.getContentOf(EntryDefaultView.class);
		infosPanel.setOpaque(false);
		builder.add(infosPanel,cc.xyw(2, 2, 3));
		*/
		
		ConnectionMultilingualResource rsrc = null;
		if(Application.instanceOfModuleExists(Connection.class)){
			rsrc = Application.getModule(Connection.class).getResource(ConnectionMultilingualResource.class);
		}
		
		hideButton = new JButton("Hide");
		if(rsrc!=null) hideButton.setText(rsrc.getValueOf("hideLabel"));
		hideButton.setBorderPainted(false);
		hideButton.setContentAreaFilled(false);
		hideButton.setFocusPainted(false);
		// hideButton.setForeground(new Color(255, 254, 255));

		hideButton.addMouseListener(new MouseAdapter(){
			
			@Override
			public void mouseEntered(MouseEvent e) {
				JButton b = (JButton) e.getSource();
				b.setForeground(Color.BLUE);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				JButton b = (JButton) e.getSource();
				b.setForeground(Color.BLACK);
			}
						
		});
		
		hideButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				ConnectionInfosView.this.setVisible(false);
			}
		});
		// builder.add(hideButton,cc.xy(2,4));
		
		conexButton = new JButton("Disconnection");
		if(rsrc!=null) conexButton.setText(rsrc.getValueOf("disconnectionLabel"));
		conexButton.setBorderPainted(false);
		conexButton.setContentAreaFilled(false);
		conexButton.setFocusPainted(false);
		// conexButton.setForeground(new Color(255, 254, 255));

		conexButton.addMouseListener(new MouseAdapter(){
			
			@Override
			public void mouseEntered(MouseEvent e) {
				JButton b = (JButton) e.getSource();
				b.setForeground(Color.BLUE);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				JButton b = (JButton) e.getSource();
				b.setForeground(Color.BLACK);
			}
						
		});
		
		conexButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				dialup();
			}
		});
		//builder.add(conexButton,cc.xy(4, 4));
		
		frame = new JWindow();
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setBounds(d.width-350, d.height-350, 300, 250);
		frame.setAlwaysOnTop(true);
		
		getContent();
		/*RingoPanel contentPanel = new RingoPanel(builder.getPanel());
		if(frame.getContentPane().getComponentCount()>0) frame.getContentPane().removeAll();
		frame.getContentPane().add(contentPanel);*/
	}

	@Override
	public void disable() {
		EntryController subcontroller = ( (ConnectionController)controller).getSubController(EntryController.class) ;
		subcontroller.disableView(EntryDefaultView.class);
		hideButton.setEnabled(false);
		conexButton.setEnabled(false);
	}

	@Override
	public void enable() {
		EntryController subcontroller = ( (ConnectionController)controller).getSubController(EntryController.class) ;
		subcontroller.enableView(EntryDefaultView.class);
		hideButton.setEnabled(true);
		conexButton.setEnabled(true);
	}

	@Override
	public Object getContent() {
		FormLayout layout = new FormLayout(
				"5dlu, left:pref, center:pref:grow, left:pref, 5dlu",
				"3dlu, pref, 2dlu, pref, 3dlu");
		
		layout.setColumnGroups(new int[][]{{1,5}});
		layout.setRowGroups(new int[][]{{1,5}});
		PanelBuilder builder = new PanelBuilder(layout);

		CellConstraints cc = new CellConstraints();

		EntryController subcontroller = ((ConnectionController)controller).getSubController(EntryController.class) ;
		JPanel infosPanel = (JPanel) subcontroller.getContentOf(EntryDefaultView.class);
		infosPanel.setOpaque(false);
		builder.add(infosPanel,cc.xyw(2, 2, 3));

		ConnectionMultilingualResource rsrc = null;
		if(Application.instanceOfModuleExists(Connection.class)){
			rsrc = Application.getModule(Connection.class).getResource(ConnectionMultilingualResource.class);
		}
		
		if(rsrc!=null) hideButton.setText(rsrc.getValueOf("hideLabel"));
		builder.add(hideButton,cc.xy(2,4));
		
		if(rsrc!=null){
			ConnectionController conex = (ConnectionController)controller;
			if(conex.isActive()) conexButton.setText(rsrc.getValueOf("disconnectionLabel"));
			else conexButton.setText(rsrc.getValueOf("connectionLabel"));
		}
		builder.add(conexButton,cc.xy(4, 4));

		RingoPanel contentPanel = new RingoPanel(builder.getPanel());
		if(frame.getContentPane().getComponentCount()>0) frame.getContentPane().removeAll();
		frame.getContentPane().add(contentPanel);
		return frame;
	}

	@Override
	protected void hide() {
		frame.setVisible(false);
	}

	@Override
	protected void show() {
		//getContent();
		frame.setVisible(true);
	}

	/**
	 * Selon l'etat en cours lance ou arrete l'appel en cours.
	 */
	public void dialup(){
		ConnectionController c = (ConnectionController)controller ;
		c.notifyEvent(ConnectionConstants.DIALUP_HANGUP_ACTION);
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {

		if(("status").compareTo(evt.getPropertyName())==0){

			Integer status = (Integer)evt.getNewValue();
			
			if(status==ConnectionConstants.DIALING_STATE){
				conexButton.setEnabled(false);
				return;
			}
			
			ConnectionMultilingualResource rsrc = null;
			if(Application.instanceOfModuleExists(Connection.class)){
				rsrc = Application.getModule(Connection.class).getResource(ConnectionMultilingualResource.class);
			}
			
			if(rsrc!=null && status==ConnectionConstants.ACTIVE_STATE){
				conexButton.setText(rsrc.getValueOf("disconnectionLabel"));
				conexButton.setEnabled(true);
				return;
			}
			
			if(rsrc!=null && status==ConnectionConstants.INACTIVE_STATE){
				conexButton.setText(rsrc.getValueOf("connectionLabel"));
				conexButton.setEnabled(true);
				return;
			}
			
		}
		
	}

	@Override
	public void anyChanged(AnyChangedEvent event) {
		/*
		 * N'ecoute pas ce que les autres vues disent au travers de notifyEvent() du controlleur
		 */
	}
	
	/*public static void main(String[] arg) throws ProgramException{

		/* Application.addModule(Connection.class);
		Application.initModules();* /
		
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				try {
					Application.addModule(Connection.class);
					Application.initModules();
					Connection conex = Application.getModule(Connection.class);
					conex.installInfosView();					
					conex.showInfosView();
				} catch (ProgramException e) {
					e.printStackTrace();
				}
				
			}
		});
	}*/
	
}
