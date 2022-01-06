package component.view;


import helper.*;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;


public class Preference extends JDialog {

	private static final long serialVersionUID = 1L;
	
	private JPanel pan1,pan2,pan3,pan4;
	
	private JRadioButton multiThread,channel;//pour serverType
	private ButtonGroup groupe ;
	
	private JLabel webLabel,logLabel ; 
	private JTextField webText,logText ;
	
	private JLabel  portLabel,numThreadLabel;
	private JSpinner portSpinner,numThreadSpinner ; 
			
	private JButton applyButton,cancelButton,resetButton;
	
	public Preference(JFrame parent){
		super(parent);
		try { 
			Util.initializeConfiguration(); 
		} catch (Exception e) {
			Util.writeError("Preference.Preference(parent) :: Erreur lors du chargement du fichier de configuration.");
			JOptionPane.showMessageDialog(this, "Erreur lors du chargement des preferences", "Erreur", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		
		pan1 = new JPanel();
		pan1.setBorder(BorderFactory.createTitledBorder(null, "Type de Serveur",TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.TOP, UResource.FONT, UResource.COLOR));
		
		multiThread = new JRadioButton("Mutli-Thread");
		//multiThread.setText("Mutli-Thread");
		multiThread.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	serverTypeAction();
            }
        });

		channel = new JRadioButton("Canaux");
        //channel.setText("Canaux");
        channel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	serverTypeAction();
            }
        });

        groupe = new ButtonGroup();
        groupe.add(multiThread);
        groupe.add(channel);
        
        if(Util.getServerType().compareToIgnoreCase(Configuration.MULTITHREAD_SERVER)==0)
        	multiThread.setSelected(true);
        else channel.setSelected(true);
        
        GroupLayout layout1 = new GroupLayout(pan1);
        pan1.setLayout(layout1);
        layout1.setHorizontalGroup(
        		layout1.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout1.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(multiThread, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 86, Short.MAX_VALUE)
                .addComponent(channel)
                .addGap(40, 40, 40))
        );
        layout1.setVerticalGroup(
        		layout1.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout1.createSequentialGroup()
                .addGroup(layout1.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(multiThread)
                    .addComponent(channel))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
		
		pan2 = new JPanel();
		pan2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Chemin", TitledBorder.DEFAULT_JUSTIFICATION,TitledBorder.TOP,UResource.FONT, UResource.COLOR));
		
		webLabel = new JLabel("Dossier Racine");
        webText = new JTextField(Util.getWebFolder());
        
		logLabel = new JLabel("Dosiier Log");
        logText = new JTextField(Util.logFolder);

        GroupLayout layout2 = new GroupLayout(pan2);
        pan2.setLayout(layout2);
        layout2.setHorizontalGroup(
        		layout2.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout2.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout2.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(webLabel)
                    .addComponent(logLabel))
                .addGap(29, 29, 29)
                .addGroup(layout2.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(logText)
                    .addComponent(webText, GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout2.setVerticalGroup(
        		layout2.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout2.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout2.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(webLabel)
                    .addComponent(webText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout2.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(logLabel)
                    .addComponent(logText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27))
        );
		
        pan3 = new JPanel();
        pan3.setBorder(BorderFactory.createTitledBorder(null, "Autre Configuration", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.TOP, UResource.FONT, UResource.COLOR));

        portLabel = new JLabel("Numéro de port");
        portSpinner = new JSpinner();
        portSpinner.setValue(Util.getPort());
        
        numThreadLabel = new JLabel("Nombre de connnexion");
        numThreadSpinner = new JSpinner();
        numThreadSpinner.setValue(Util.getThreadNumber());
        numThreadSpinner.setToolTipText("0 revient a dire une infinite de connexion simultanées");

        GroupLayout layout3 = new GroupLayout(pan3);
        pan3.setLayout(layout3);
        layout3.setHorizontalGroup(
        		layout3.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout3.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout3.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(numThreadLabel)
                    .addComponent(portLabel))
                .addGap(36, 36, 36)
                .addGroup(layout3.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(numThreadSpinner, GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                    .addComponent(portSpinner, GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout3.setVerticalGroup(
        		layout3.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout3.createSequentialGroup()
                .addGroup(layout3.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(portLabel)
                    .addComponent(portSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout3.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(numThreadLabel)
                    .addComponent(numThreadSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
		
        
        pan4 = new JPanel();
        pan4.setBorder(BorderFactory.createEtchedBorder());

        resetButton = new JButton("Default");
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	resetButtonAction();
            }
        });

        applyButton = new JButton("Apply");
        applyButton.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		applyButtonAction();
        	}
        });

        cancelButton = new JButton("cancel");
        cancelButton.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		cancelButtonAction();
        	}
        });

        GroupLayout layout4 = new GroupLayout(pan4);
        pan4.setLayout(layout4);
        layout4.setHorizontalGroup(
        		layout4.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout4.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(resetButton)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(applyButton)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cancelButton)
                .addContainerGap(72, Short.MAX_VALUE))
        );
        layout4.setVerticalGroup(
        		layout4.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout4.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout4.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(resetButton)
                    .addComponent(applyButton)
                    .addComponent(cancelButton))
                .addContainerGap())
        );
        
        JPanel panel = new JPanel();
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pan2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pan1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pan3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pan4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pan1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pan2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pan3, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pan4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        
        this.getContentPane().add(panel);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		//this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Preference Application");
		this.setBounds(250, 150, 400, 375);
		this.pack();
		this.setResizable(false);
	}
	
	private void serverTypeAction() {
		if(multiThread.isSelected()) Util.setServerType(Configuration.MULTITHREAD_SERVER);
		else Util.setServerType(Configuration.CHANNEL_SERVER);
	}
	
	private void applyButtonAction(){
	
		File dir1 = new File(webText.getText()),dir2 = new File(logText.getText());
		if(!dir1.isDirectory() || !dir2.isDirectory()){
			JOptionPane.showMessageDialog(this, "L'un des chemins n'est pas correcte. Verifiez le.", "Attention", JOptionPane.WARNING_MESSAGE);
			Util.writeError("Preference.applyButtonAction() :: Erreur lors de l'enregistrement des preferences.Incoherence des dossiers selectionnees.");
			UResource.setMessage("Erreur","Modification non appliquée", TrayIcon.MessageType.ERROR);
		}else{
			if(multiThread.isSelected()) Util.setServerType(Configuration.MULTITHREAD_SERVER);
			else Util.setServerType(Configuration.CHANNEL_SERVER);
			Util.setWebFolder(webText.getText());
			Util.logFolder = logText.getText();
			Util.setPort(Integer.parseInt(portSpinner.getValue().toString()));
			Util.setThreadNumber(Integer.parseInt(numThreadSpinner.getValue().toString()));
			try { Util.saveConfiguration();	} catch (IOException e) {
				Util.writeError("Preference.applyButtonAction() :: Erreur lors de l'enregistrement des preferences.");
				UResource.setMessage("Erreur","Une erreur est survenu lors de l'enregistrement", TrayIcon.MessageType.ERROR);
			}
			UResource.setMessage("Info","Preferences prendront effet lors du prochain démarrage du Serveur.", TrayIcon.MessageType.INFO);
		}
	}
	
	private void cancelButtonAction(){ this.dispose(); }
	
	private void resetButtonAction() {
		Util.resetConfiguration();		
		if(Util.getServerType().compareToIgnoreCase(Configuration.MULTITHREAD_SERVER)==0)
			multiThread.setSelected(true);
		else channel.setSelected(true);		
		webText.setText(Util.getWebFolder());
		logText.setText(Util.logFolder);
		portSpinner.setValue(Util.getPort());
		numThreadSpinner.setValue(Util.getThreadNumber());			
	}

	
/*	public static void main(String[] arg){
		final Preference frame = new Preference(null);
		Image image = UResource.createIcon("logo.png", "resource").getImage();
		PopupMenu popup = new PopupMenu();
		
		MenuItem open = new MenuItem("Open");
		open.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				frame.setVisible(true);
			}
		});
		popup.add(open);
		
		MenuItem hide = new MenuItem("Hide");
		hide.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				frame.setVisible(false);
			}
		});
		popup.add(hide);
		
		MenuItem exit = new MenuItem("Exit");
		exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
		popup.add(exit);
		UResource.createSystemTray(image,"RamServer", popup);
		
		frame.setVisible(true);
	}*/
}
