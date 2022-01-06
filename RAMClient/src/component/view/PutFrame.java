package component.view;

import helper.*;

import javax.swing.*;

import java.awt.Image;
import java.awt.event.*;
import javax.swing.border.*;

import component.model.*;

public class PutFrame extends JFrame{

	private static final long serialVersionUID = 1L;

	private JLabel urlLabel;
    private JTextField urlText,fileText;
	
    private JRadioButton fileRadio,textRadio;
    private JTextArea textArea;
    
	private JButton fileButton,cancelButton,uploadButton;
    private JPanel controlPanel,panel;
    private JScrollPane scrollPane;
    
    private JLabel statusBar ;
    
    
    private ButtonGroup group = new ButtonGroup();
    
    JFileChooser explorer ;
    
    private HTTPConnection client ;
    //JFrame parent
    public PutFrame(){
    	//super(parent);
    	this.setTitle("PUT Method - RAMClient");
    	Image image = UResource.createIcon("logo.png", "resource").getImage();
		this.setIconImage(image);
    	urlLabel = new JLabel();
        urlText = new JTextField();
        panel = new JPanel();
        textRadio = new JRadioButton();
        scrollPane = new JScrollPane();
        textArea = new JTextArea();
        fileRadio = new JRadioButton();
        fileText = new JTextField();
        fileButton = new JButton();
        controlPanel = new JPanel();
        uploadButton = new JButton();
        cancelButton = new JButton();
    
        group.add(textRadio);
        group.add(fileRadio);

        statusBar = new JLabel("En attente...");
        statusBar.setIcon(UResource.createIcon("uploading.gif", "resource"));
        
        urlLabel.setFont(Configuration.FONT);
        urlLabel.setText("URL");

        urlText.setFont(Configuration.FONT);        

        panel.setBorder(BorderFactory.createTitledBorder(null, "Put", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, Configuration.FONT, Configuration.COLOR));

        textRadio.setFont(Configuration.FONT);
        textRadio.setSelected(true);
        textRadio.setText("Texte");
        textRadio.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		buttonRadioAction() ;
        	}
        });

        textArea.setColumns(20);
        textArea.setFont(Configuration.FONT);
        textArea.setRows(5);
        textArea.setLineWrap(true);
        scrollPane.setViewportView(textArea);

        fileRadio.setFont(Configuration.FONT);
        fileRadio.setText("Fichier");
        fileRadio.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		buttonRadioAction() ;
        	}
        });

        fileText.setEditable(false);
        fileText.setFont(Configuration.FONT);

        fileButton.setFont(Configuration.FONT);
        fileButton.setText("...");
        fileButton.setEnabled(false);
        fileButton.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		fileButtonAction();
        	}
        });

        GroupLayout panelLayout = new GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE))
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(fileRadio)
                            .addComponent(textRadio)
                            .addGroup(GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(fileText, GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(fileButton)))))
                .addContainerGap())
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addComponent(textRadio)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 236, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(fileRadio)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(fileButton)
                    .addComponent(fileText))
                .addContainerGap())
        );

        controlPanel.setBorder(BorderFactory.createTitledBorder(null, "", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, Configuration.FONT));

        uploadButton.setFont(Configuration.FONT);
        uploadButton.setText("Upload");
        uploadButton.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		uploadButtonAction();
        	}
        });

        cancelButton.setFont(Configuration.FONT);
        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		cancelButtonAction();
        	}
        });

        GroupLayout controlPanelLayout = new GroupLayout(controlPanel);
        controlPanel.setLayout(controlPanelLayout);
        controlPanelLayout.setHorizontalGroup(
            controlPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, controlPanelLayout.createSequentialGroup()
                .addContainerGap(235, Short.MAX_VALUE)
                .addComponent(uploadButton)
                .addGap(26, 26, 26)
                .addComponent(cancelButton)
                .addContainerGap())
        );
        controlPanelLayout.setVerticalGroup(
            controlPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(controlPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(cancelButton)
                .addComponent(uploadButton))
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(urlLabel)
                        .addGap(31, 31, 31)
                        .addComponent(urlText, GroupLayout.DEFAULT_SIZE, 382, Short.MAX_VALUE))
                    .addComponent(controlPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(urlLabel)
                    .addComponent(urlText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(controlPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        
        
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setLocation(250, 150);
        pack();
    }
	
    
    private void buttonRadioAction() {
    	if(textRadio.isSelected()){
    		textArea.setEnabled(true);
    		fileButton.setEnabled(false);
    		fileText.setText("");
    	}
    	else{
    		fileButton.setEnabled(true);
    		textArea.setEnabled(false);
    		textArea.setText("");
    	}
    }
    
    private void fileButtonAction() {
    	 if(explorer==null) explorer = new JFileChooser();    	 
    	 int result = explorer.showOpenDialog(this);
    	 if(result== JFileChooser.APPROVE_OPTION)
    	   fileText.setText(explorer.getSelectedFile().getName());
    }
    
    private void uploadButtonAction() {
    	PutThread uploadThread = new PutThread(this);
    	uploadThread.start();
    	/*try { client = new HTTPConnection(); } catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "RAMClient",JOptionPane.ERROR_MESSAGE);
		}
    	    	
    	if(textRadio.isSelected())
			try { client.put(urlText.getText(),textArea.getText()); } catch (Exception e) { 
				JOptionPane.showMessageDialog(this, e.getMessage(), "RAMClient", JOptionPane.ERROR_MESSAGE);				
			}
		else
			try { 
				if(!fileText.getText().isEmpty())
					client.put(urlText.getText(),explorer.getSelectedFile());
				else
					JOptionPane.showMessageDialog(this,"Veuillez séletionner un fichier", "Erreur", JOptionPane.ERROR_MESSAGE);
			} catch (Exception e) { 
				JOptionPane.showMessageDialog(this, e.getMessage(), "RAMClient", JOptionPane.ERROR_MESSAGE);				
			}
			
			
			if(client!=null && client.getResponseMessage()!=null && client.getResponseMessage().trim().compareTo("")!=0){
				JOptionPane.showMessageDialog(this,client.getResponseMessage(),
						"RAMClient", JOptionPane.INFORMATION_MESSAGE);
				client.disconnect();
				this.dispose();
			}else JOptionPane.showMessageDialog(this,"Le fichier n'a pas pu être créer",
					"RAMClient", JOptionPane.INFORMATION_MESSAGE);*/
    }
    
    private void cancelButtonAction() {
    	this.dispose();
    }
    
    //http://127.0.0.1:8080/
	//G:\Emmanu\Projet\workspace\RAMClient
    
    private SimulationFrame simule = new SimulationFrame(this,"loading.gif");
    
    class SimuleThread extends Thread {
    	JFrame parent;
    	
    	public SimuleThread(JFrame parent){
    		this.parent = parent ;
    	}
    	
    	public void run() {    		    		
    		simule.showDialog(parent.getX()+parent.getWidth()/2 - simule.getWidth()/2, parent.getY() + parent.getHeight()/2 - simule.getHeight()/2 );
    	}    		
    }
   
    
    class PutThread extends Thread {
    	
    	JFrame parent;
    	
    	public PutThread(JFrame parent){
    		this.parent = parent ;
    	}
    	
    	public void run() {

    		SimuleThread thread = new SimuleThread(parent);
    		thread.start();
    		
    		try { client = new HTTPConnection(); } catch (Exception e) {
    			thread.interrupt();
    			simule.setVisible(false);
    			JOptionPane.showMessageDialog(parent, e.getMessage(), "RAMClient",JOptionPane.ERROR_MESSAGE);
    		}
        	    	
        	if(textRadio.isSelected())
    			try { client.put(urlText.getText(),textArea.getText()); } catch (Exception e) {
    				thread.interrupt();
        			simule.setVisible(false);
    				JOptionPane.showMessageDialog(parent, e.getMessage(), "RAMClient", JOptionPane.ERROR_MESSAGE);
    			}    			
    		else    			
    			try { 
    				if(!fileText.getText().isEmpty())
    					client.put(urlText.getText(),explorer.getSelectedFile());
    				else{
    					thread.interrupt();
    	    			simule.setVisible(false);
    					JOptionPane.showMessageDialog(parent,"Veuillez séletionner un fichier", "Erreur", JOptionPane.ERROR_MESSAGE);
    				}
    			} catch (Exception e) {
    				thread.interrupt();
        			simule.setVisible(false);
    				JOptionPane.showMessageDialog(parent, e.getMessage(), "RAMClient", JOptionPane.ERROR_MESSAGE);				
    			}
    			
    			
    			if(client!=null && client.getResponseMessage()!=null && client.getResponseMessage().trim().compareTo("")!=0){
    				thread.interrupt();
        			simule.setVisible(false);
    				JOptionPane.showMessageDialog(parent,client.getResponseMessage(),
    						"RAMClient", JOptionPane.INFORMATION_MESSAGE);
    				client.disconnect();    				
    				//parent.dispose();
    			}else{
    				thread.interrupt();
        			simule.setVisible(false);
    				JOptionPane.showMessageDialog(parent,"Le fichier n'a pas pu être créer",
    					"RAMClient", JOptionPane.INFORMATION_MESSAGE);
    			}
    	}
    }
    
    /*public static void main(String[] arg){
    	new PutFrame().setVisible(true);
    }*/
    
    
}
