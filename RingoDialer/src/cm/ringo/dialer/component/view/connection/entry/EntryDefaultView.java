package cm.ringo.dialer.component.view.connection.entry;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.beans.PropertyChangeEvent;

import javax.swing.*;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;


import cm.enterprise.software.component.model.event.AnyChangedEvent;
import cm.enterprise.software.helper.Application;
import cm.ringo.dialer.component.controller.connection.EntryController;
import cm.ringo.dialer.component.model.base.connection.EntryModel;
import cm.ringo.dialer.helper.connection.multilingual.ConnectionMultilingualResource;
import cm.ringo.dialer.helper.connection.multilingual.EntryMultilingualResource;
import cm.ringo.dialer.interfaces.Connection;

/**
 * Represente la vue par defaut d'une entree de connexion. Elle presente particulierement
 * les parametres dynamiques de l'entree de connexion.
 * @author Charles Moute
 * @version 1.0.1, 6/8/2010
 */
public class EntryDefaultView extends EntryView {

	/**
	 * Libelles des parametres d'une entree de connexion
	 */
	private JLabel nameLabel, brLabel, btLabel, csLabel, cdLabel;
	
	/**
	 * Valeurs des parametres d'une entree de connexion
	 */
	private JLabel bytesReceived,bytesTransmitted,speedLabel,durationLabel ;
	
	private JPanel contentPanel ;
	
	public EntryDefaultView(EntryController controller) {
		super(controller);
		
		EntryModel model = (EntryModel)controller.getModel();
		
		FormLayout layout = new FormLayout(
				"5dlu,left:pref, 10dlu, right:pref:grow,5dlu",
				"5dlu, pref, 5dlu, pref, 3dlu, pref, 3dlu, pref, 3dlu, pref, 3dlu, pref, 3dlu, pref, 5dlu");
		
		layout.setColumnGroups(new int[][]{{1,5},{2,4}});
		layout.setRowGroups(new int[][]{{1,13},{2,12},{3,11},{4,6,8,10},{5,7,9}}); 
		PanelBuilder builder = new PanelBuilder(layout);
		
		CellConstraints cc = new CellConstraints();
		
		Dimension dim1 = new Dimension(125,25);
		//Dimension dim2 = new Dimension(80,25);
		
		Font font = new Font(Font.DIALOG,Font.BOLD,12);
		Color color = new Color(30,35,55); //Color.WHITE ;
		
		//Ajout du Label du nom de la connexion
		nameLabel = new JLabel(model.getEntryName()+" Connection");
		
		nameLabel.setForeground(color);
		nameLabel.setFont(font);		
		nameLabel.setPreferredSize(dim1);
		builder.add(nameLabel,cc.xyw(1, 2,5));
		
		//Ajout de la ligne des bytes recus
		brLabel = new JLabel("Bytes Received");
		brLabel.setForeground(color);
		brLabel.setFont(font);		
		brLabel.setPreferredSize(dim1);
		builder.add(brLabel,cc.xy(2, 6));
		
		bytesReceived = new JLabel("");
		bytesReceived.setFont(font);		
		bytesReceived.setForeground(color);
		bytesReceived.setPreferredSize(dim1);
		builder.add(bytesReceived,cc.xyw(4, 6,2));

		//Ajout de la ligne des bytes transmis
		btLabel = new JLabel("Bytes Trasmitted");
		btLabel.setForeground(color);
		btLabel.setFont(font);		
		btLabel.setPreferredSize(dim1);
		builder.add(btLabel,cc.xy(2, 8));
		
		bytesTransmitted = new JLabel("");
		bytesTransmitted.setFont(font);
		bytesTransmitted.setForeground(color);
		bytesTransmitted.setPreferredSize(dim1);
		builder.add(bytesTransmitted,cc.xyw(4, 8,2));

		//Ajout de la ligne vitesse connexion
		csLabel = new JLabel("Connection Speed");
		csLabel.setForeground(color);
		csLabel.setFont(font);		
		csLabel.setPreferredSize(dim1);
		builder.add(csLabel,cc.xy(2, 10));
		
		speedLabel = new JLabel("");
		speedLabel.setFont(font);
		speedLabel.setForeground(color);
		speedLabel.setPreferredSize(dim1);
		builder.add(speedLabel,cc.xyw(4, 10,2));

		//Ajout de la ligne duree connexion
		cdLabel = new JLabel("Connection Duration");
		cdLabel.setForeground(color);
		cdLabel.setFont(font);		
		cdLabel.setPreferredSize(dim1);
		builder.add(cdLabel,cc.xy(2, 12));
		
		durationLabel = new JLabel("");
		durationLabel.setFont(font);
		durationLabel.setForeground(color);
		durationLabel.setPreferredSize(dim1);
		builder.add(durationLabel,cc.xyw(4, 12,2));
		contentPanel = builder.getPanel(); 
	}

	/**
	 * Initialise la vue par des valeurs par defaut.
	 */
	public void initView(){
		bytesReceived.setText("----");
		bytesTransmitted.setText("----");
		speedLabel.setText("----");
		durationLabel.setText(" --- : --- : --- ");
	}
	
	
	@Override
	public void disable() {

		nameLabel.setEnabled(false);
		
		brLabel.setEnabled(false);
		bytesReceived.setEnabled(false);
		
		btLabel.setEnabled(false);
		bytesTransmitted.setEnabled(false);
		
		csLabel.setEnabled(false);
		speedLabel.setEnabled(false);
		
		cdLabel.setEnabled(false);
		durationLabel.setEnabled(false);
		
	}

	@Override
	public void enable() {
		
		nameLabel.setEnabled(true); 
		
		brLabel.setEnabled(true);
		bytesReceived.setEnabled(true);
		
		btLabel.setEnabled(true);
		bytesTransmitted.setEnabled(true);
		
		csLabel.setEnabled(true);
		speedLabel.setEnabled(true);
		
		cdLabel.setEnabled(true);
		durationLabel.setEnabled(true);
		
	}

	@Override
	public Object getContent() {
		if(Application.instanceOfModuleExists(Connection.class)){
		
			ConnectionMultilingualResource resource = Application.getModule(Connection.class).getResource(ConnectionMultilingualResource.class);
			EntryModel model = (EntryModel) ((EntryController)controller).getModel();
			nameLabel.setText(model.getEntryName()+" "+resource.getValueOf("connectionLabel"));
			
			EntryMultilingualResource rsrc = Application.getModule(Connection.class).getResource(EntryMultilingualResource.class);
			brLabel.setText(rsrc.getValueOf("bytesReceivedLabel"));
			btLabel.setText(rsrc.getValueOf("bytesTransmittedLabel"));
			csLabel.setText(rsrc.getValueOf("speedconnectionLabel"));
			cdLabel.setText(rsrc.getValueOf("timeLabel"));
		}
		return contentPanel;
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
		
		EntryMultilingualResource rsrc = Application.getModule(Connection.class).getResource(EntryMultilingualResource.class);
		String unit = rsrc.getValueOf("unit");
		
		if( ("entryName").compareTo(evt.getPropertyName())==0){
			String value = (String) evt.getNewValue();
			nameLabel.setText(""+value);
		}
		
		if( ("bytesReceived").compareTo(evt.getPropertyName())==0){
			Long value = (Long)evt.getNewValue();
			String msg = "----" ;
			if(value>0) msg = value+" "+unit ;
			bytesReceived.setText(msg);
		}
		
		if( ("bytesTransmitted").compareTo(evt.getPropertyName())==0){
			Long value = (Long)evt.getNewValue();
			String msg = "----" ;
			if(value>0) msg = value+" "+unit ;
			bytesTransmitted.setText(msg);
		}
		
		if( ("connectionspeed").compareTo(evt.getPropertyName())==0){
			Long value = (Long)evt.getNewValue();
			String msg = "----" ;
			if(value>0) msg = value+" "+unit ;
			speedLabel.setText(msg);
		}
		
		if( ("time").compareTo(evt.getPropertyName())==0){
			String value = (String) evt.getNewValue();
			durationLabel.setText(""+value);
		}
		
	}

	@Override
	public void anyChanged(AnyChangedEvent event) {
		//Pour l'instant l'ecout des autres vues ne nous interesse pas.
	}

}
