package cm.ringo.dialer.component.view.connection;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import cm.enterprise.software.component.model.event.AnyChangedEvent;
import cm.enterprise.software.exception.OperationFailureException;
import cm.enterprise.software.exception.ProgramException;
import cm.enterprise.software.helper.Application;
import cm.enterprise.software.helper.Helper;
import cm.ringo.dialer.component.controller.connection.ConnectionController;
import cm.ringo.dialer.component.controller.connection.EntryController;
import cm.ringo.dialer.component.util.connection.RedBackgroundPanel;
import cm.ringo.dialer.component.view.connection.entry.EntryStatusView;
import cm.ringo.dialer.helper.DialerConstants;
import cm.ringo.dialer.helper.connection.EntryHelper;
import cm.ringo.dialer.helper.connection.constants.ConnectionConstants;
import cm.ringo.dialer.helper.connection.multilingual.ConnectionMultilingualResource;
import cm.ringo.dialer.interfaces.Connection;
import cm.ringo.dialer.interfaces.Dialer;

/**
 * Represente la vue par defaut de l'ecran de connexion
 * @author Charles Moute
 * @version 1.0.1, 6/8/2010
 */

public class ConnectionDefaultView extends ConnectionView implements ActionListener {
	
	private JLabel status,infos; //Il est la sous vue de l'entree  EntryStatusView
	private JLabel logo,nameLabel,passwordLabel;
	private JTextField nameField ;
	private JPasswordField passwordField;
	private JCheckBox rememberMeCheckBox;
	private JButton bouton ;
	
	private JPanel contentPanel ; 
	
	public ConnectionDefaultView(ConnectionController controller) throws ProgramException {
		super(controller);
		
		final String file = Application.getModule(Dialer.class).getResource(DialerConstants.class).getUserPropertiesFile();
		
		//ici on va instancier la classe vue EntryStatusView pour le status
		EntryController subcontroller = controller.getSubController(EntryController.class) ; 
		subcontroller.installView(EntryStatusView.class,subcontroller);
		status = (JLabel)subcontroller.getContentOf(EntryStatusView.class);
		
		int HEIGHT1 = 23 ;
		int WIDTH1 = 80;
		int WIDTH2 = 100;
		
		Dimension labelDimension = new Dimension(WIDTH1,HEIGHT1);
		Dimension fieldDimension = new Dimension(WIDTH2,HEIGHT1);
		Color fontColor = new Color(255, 254, 255);
		
		FormLayout layout = new FormLayout(
											"left:pref:grow, center:pref, left:pref, left:pref:grow",
											"5dlu, pref, 10dlu, 90px, 2dlu, pref, 2dlu, pref, 5dlu, pref, 2dlu, pref,"
										   +" pref, pref, "+
											" 5dlu, pref, 5dlu, pref, 5dlu"); 
		
		// "10dlu,180px, pref,3dlu" "5dlu, pref, 10dlu, 80px, 2dlu, pref, 2dlu, pref, 5dlu, pref, 2dlu, pref, 5dlu, pref, 5dlu, pref, 5dlu"
		layout.setColumnGroups(new int[][]{{1,4}}); //,{2,4}
		layout.setRowGroups(new int[][]{{1,17},{2,16},{4,14},{6,10},{8,12}});
				
		PanelBuilder builder = new PanelBuilder(layout);
		
		CellConstraints cc = new CellConstraints();

		Font font = new Font("Arial", 0, 12);
		
		//Ajout du status d'information
		status.setForeground(fontColor);
		status.setFont(font);
		status.setPreferredSize(labelDimension);
		builder.add(status, cc.xyw(1, 2, 4));
		
		//Ajout du logo
		logo = new JLabel();
		if(Application.instanceOfModuleExists(Connection.class)){
			ConnectionConstants rsrc = Application.getModule(Connection.class).getResource(ConnectionConstants.class);
			ImageIcon icon = new ImageIcon(rsrc.getSplashLogoPicture());
			logo.setPreferredSize(new Dimension(icon.getIconWidth(),icon.getIconWidth()));
			logo.setIcon(icon);
		}
		builder.add(logo,cc.xy(2, 4));
	
		ConnectionMultilingualResource rsrc = null ;
		
		if(Application.instanceOfModuleExists(Connection.class)){
			rsrc = Application.getModule(Connection.class).getResource(ConnectionMultilingualResource.class);  
		}
		
		//Ajout du label de nom
		nameLabel = new JLabel();
		if(rsrc!=null) nameLabel.setText(rsrc.getUserNameLabel());
		nameLabel.setForeground(fontColor);
		nameLabel.setFont(font);
		nameLabel.setPreferredSize(new Dimension(150, 23));
		builder.add(nameLabel,cc.xyw(1, 6, 4));
		
		//Ajout du textField de nom
		nameField = new JTextField(controller.getUsername());
		nameField.setPreferredSize(new Dimension(150, 23));
		nameField.addActionListener(this);
		builder.add(nameField,cc.xyw(1, 8, 4));
		
		//Ajout du label Password
		passwordLabel = new JLabel("");
		if(rsrc!=null) passwordLabel.setText(rsrc.getPasswordLabel());
		passwordLabel.setForeground(fontColor);
		passwordLabel.setFont(font);
		passwordLabel.setPreferredSize(labelDimension);
		builder.add(passwordLabel,cc.xyw(1, 10, 4));
		
		//Ajout du textfield de password
		passwordField = new JPasswordField(controller.getPassword());
		passwordField.setPreferredSize(fieldDimension);
		passwordField.addActionListener(this);
		builder.add(passwordField,cc.xyw(1, 12, 4));
		
		//
		rememberMeCheckBox = new JCheckBox("", controller.getRememberMe());
		rememberMeCheckBox.setSelected(controller.getRememberMe());
		builder.add(rememberMeCheckBox, cc.xyw(1, 13, 4));	
		rememberMeCheckBox.setFont(font);
		rememberMeCheckBox.setForeground(fontColor);
		rememberMeCheckBox.setFont(font);
		rememberMeCheckBox.setPreferredSize(labelDimension);
		rememberMeCheckBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ConnectionController controller_ = ((ConnectionController) ConnectionDefaultView.this.controller);
				controller_.setRememberMe(rememberMeCheckBox.isSelected());
				
				controller_.setUsername(nameField.getText());
				if(controller_.getRememberMe()){
				 	controller_.setPassword(new String(passwordField.getPassword()));
				}else
					try {
						Helper.setProperties("xywz", "", file, "Ringo SA");
					} catch (OperationFailureException e) {
						e.printStackTrace();
					}
			}
		});
		
		//Ajout du bouton connection/deconnection
		bouton = new JButton("Connection");
		if(rsrc!=null) bouton.setText(rsrc.getValueOf("connectionLabel"));
		bouton.setBorderPainted(false);
		bouton.setFocusPainted(false);
		// bouton.setForeground(new Color(255, 254, 255));
		
		bouton.addMouseListener(new MouseAdapter(){
			
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
		bouton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				dialup();
			}
		});
		builder.add(bouton,cc.xyw(2, 14, 2));
		
		//Ajout d'un label d'infos de connexion.
		infos = new JLabel();
		infos.setFont(new Font(Font.DIALOG,Font.ITALIC+Font.BOLD,12));
		infos.setForeground(fontColor);
		infos.setText("");
		infos.setPreferredSize(labelDimension);
		builder.add(infos, cc.xyw(1,16,4));
		
		contentPanel = builder.getPanel();
	}

	/**
	 * Lance l'appel ou l'arret selon l'etat de connexion
	 */
	public void dialup(){
		ConnectionController c = (ConnectionController)controller;
		c.setUsername(nameField.getText());
		c.setPassword(new String(passwordField.getPassword()));
		controller.notifyEvent(ConnectionConstants.DIALUP_HANGUP_ACTION);
	}
	
	@Override
	public void disable() {
		nameField.setEnabled(false);
		passwordField.setEnabled(false);
		rememberMeCheckBox.setEnabled(false);
	}

	@Override
	public void enable() {
		nameField.setEnabled(true);
		passwordField.setEnabled(true);
		rememberMeCheckBox.setEnabled(true);
	}
	
	/**
	 * Annule les interactions avec l'unique bouton de l'interface
	 */
	public void boutonDisabel(){
		bouton.setEnabled(false);
	}
	
	/**
	 * Permet les interactions avec l'unique bouton de l'interface
	 */
	public void boutonEnable(){
		bouton.setEnabled(true);
	}

	@Override
	public Object getContent() {
		if(Application.instanceOfModuleExists(Connection.class)){
			
			ConnectionController c = (ConnectionController)controller;
			ConnectionMultilingualResource rsrc = Application.getModule(Connection.class).getResource(ConnectionMultilingualResource.class);
			
			status.setText(EntryHelper.getMessageOfStatus(c.getState()));
						
			nameLabel.setText(rsrc.getUserNameLabel());
			nameField.setText(c.getUsername());
			
			passwordLabel.setText(rsrc.getPasswordLabel());
			passwordField.setText(c.getPassword());
			rememberMeCheckBox.setText(rsrc.getRememberMeLabel());
			
			if(c.isActive())
				bouton.setText(rsrc.getValueOf("disconnectionLabel"));
			else bouton.setText(rsrc.getValueOf("connectionLabel"));
		}
		return  new RedBackgroundPanel(contentPanel);
	}

	@Override
	public void hide() {
		contentPanel.setVisible(false);
	}

	@Override
	public void show() {
		contentPanel.setVisible(true);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		
		if( ("message").compareTo(evt.getPropertyName())==0 ){
			infos.setText((String)evt.getNewValue());
		}
		
		if( ("username").compareTo(evt.getPropertyName())==0 ){
			nameField.setText((String)evt.getNewValue());
		}
		
		if( ("password").compareTo(evt.getPropertyName())==0 ){
			passwordField.setText((String)evt.getNewValue());
		}
		
		if( ("rememberMe").compareTo(evt.getPropertyName())==0 ){
			rememberMeCheckBox.setSelected((Boolean)evt.getNewValue());
		}		
		
		if( ("state").compareTo(evt.getPropertyName())==0 ){
			ConnectionMultilingualResource rsrc = null ;
			if(Application.instanceOfModuleExists(Connection.class))
				rsrc = Application.getModule(Connection.class).getResource(ConnectionMultilingualResource.class);
			
			Integer state = (Integer)evt.getNewValue();
			switch(state){
				case ConnectionConstants.INACTIVE_STATE : 	bouton.setText(rsrc.getValueOf("connectionLabel"));
															this.boutonEnable();
															this.enable();
															break;
				case ConnectionConstants.DIALING_STATE	:	this.boutonDisabel();
															this.disable();
															break;
				case ConnectionConstants.ACTIVE_STATE	:	bouton.setText(rsrc.getValueOf("disconnectionLabel"));
															this.boutonEnable();
															this.disable();
															break;
			}
			
		}

	}

	@Override
	public void anyChanged(AnyChangedEvent event) {
		/*
		 * N'ecoute pas ce que les autres vues disent
		 */
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		dialup();
	}

}
