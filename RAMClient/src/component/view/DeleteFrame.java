package component.view;

import java.awt.*;

import helper.*;

import javax.swing.*;

import component.model.*;
import java.awt.event.*;
import javax.swing.border.*;

public class DeleteFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	
	private JButton cancelButton,deleteButton;
    private JPanel panel;
    private JLabel urlLabel;
    private JTextField urlText;
    private HTTPConnection conex;
      
    //public DeleteFrame(JFrame parent)
    public DeleteFrame(){
    	//super(parent);
    	this.setTitle("DELETE Method - RAMClient");
    	Image image = UResource.createIcon("logo.png", "resource").getImage();
		this.setIconImage(image);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        
        panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder(null, "Delete", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,Configuration.FONT, Configuration.COLOR));

        urlLabel = new JLabel();
        urlLabel.setFont(Configuration.FONT);
        urlLabel.setText("URL");

        urlText = new JTextField();
        urlText.setFont(Configuration.FONT);
        
        deleteButton = new JButton("Delete");        
        deleteButton.setFont(new Font("Dialog", 0, 12));
        deleteButton.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent evt){
        		deleteButtonAction();
        	}
        });
        
        cancelButton = new JButton("Cancel");
        cancelButton.setFont(Configuration.FONT);
        cancelButton.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent evt){
        		cancelButtonAction();
        	}
        });
        

        GroupLayout panelLayout = new GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(panelLayout.createSequentialGroup()
                        .addComponent(urlLabel)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(urlText, GroupLayout.DEFAULT_SIZE, 313, Short.MAX_VALUE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
                        .addComponent(deleteButton)
                        .addGap(18, 18, 18)
                        .addComponent(cancelButton)))
                .addGap(10, 10, 10))
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(urlLabel)
                    .addComponent(urlText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(deleteButton)
                    .addComponent(cancelButton))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        this.pack();
        this.setLocation(250,50);
    }
    
    private void cancelButtonAction() {
    	this.dispose();
    }
    
    private void deleteButtonAction(){
    	
    	DeleteThread deleteThread = new DeleteThread(this);
    	deleteThread.start();
    	/*try { conex = new HTTPConnection();	} catch (Exception e) {
    		JOptionPane.showMessageDialog(this, e.getMessage(), "RAMClient", JOptionPane.ERROR_MESSAGE);
		}
    	
    	try { if(conex!=null) conex.delete(urlText.getText()); } catch (Exception e) {
    		JOptionPane.showMessageDialog(this, e.getMessage(), "RAMClient",JOptionPane.ERROR_MESSAGE);
		}
    	
    	if(conex!=null && conex.getResponseMessage()!=null && conex.getResponseMessage().compareTo("")!=0){
    		JOptionPane.showMessageDialog(this,conex.getResponseMessage(),
					"RAMClient", JOptionPane.INFORMATION_MESSAGE);
			conex.disconnect();
			this.dispose();
    	}else{
    		JOptionPane.showMessageDialog(this, "Le fichier n'a pas pu être suppprimé", "RAMClient",JOptionPane.ERROR_MESSAGE);
    	}*/
    	
    }
    
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
    
    class DeleteThread extends Thread {
    
    	JFrame parent;
    	
    	public DeleteThread(JFrame parent){
    		this.parent = parent ;
    	}
    	
    	public void run(){
    		SimuleThread thread = new SimuleThread(parent);
    		thread.start();
    		
    		try { conex = new HTTPConnection();	} catch (Exception e) {
    			thread.interrupt();
    			simule.setVisible(false);
        		JOptionPane.showMessageDialog(parent, e.getMessage(), "RAMClient", JOptionPane.ERROR_MESSAGE);
    		}
        	
        	try { if(conex!=null) conex.delete(urlText.getText()); } catch (Exception e) {
        		thread.interrupt();
    			simule.setVisible(false);
        		JOptionPane.showMessageDialog(parent, e.getMessage(), "RAMClient",JOptionPane.ERROR_MESSAGE);
    		}
        	
        	if(conex!=null && conex.getResponseMessage()!=null && conex.getResponseMessage().compareTo("")!=0){
        		thread.interrupt();
    			simule.setVisible(false);
        		JOptionPane.showMessageDialog(parent,conex.getResponseMessage(),
    					"RAMClient", JOptionPane.INFORMATION_MESSAGE);
    			conex.disconnect();
    			//parent.dispose();
        	}else{
        		thread.interrupt();
    			simule.setVisible(false);
        		JOptionPane.showMessageDialog(parent, "Le fichier n'a pas pu être suppprimé", "RAMClient",JOptionPane.ERROR_MESSAGE);
        	}
    	}
    }
    
    
    
    /*public static void main(String[] arg ){
    	new DeleteFrame().setVisible(true);
    }*/
	
}
