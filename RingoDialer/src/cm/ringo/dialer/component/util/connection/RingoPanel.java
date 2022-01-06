package cm.ringo.dialer.component.util.connection;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import cm.enterprise.software.helper.Application;
import cm.ringo.dialer.helper.connection.constants.ConnectionConstants;
import cm.ringo.dialer.interfaces.Connection;

/**
 * Classe definissant un fond a l'effigie de Ringo
 * @author Charles Moute
 * @version 1.0.1, 8/8/2010
 */
public class RingoPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private ImageIcon img ;
	private JPanel content;

	public RingoPanel(JPanel panel) {
		
		if(Application.instanceOfModuleExists(Connection.class)){
			ConnectionConstants rsrc = Application.getModule(Connection.class).getResource(ConnectionConstants.class);
			img =  new ImageIcon(rsrc.getLogoPicture());
		}
		
		content = panel;
		if(content==null) content = new JPanel();
		content.setOpaque(false);
		this.add(content);			
		repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D surface = (Graphics2D)g;
		if(img!=null)surface.drawImage(img.getImage(), this.getWidth()/2, 0, this);
	}

}
