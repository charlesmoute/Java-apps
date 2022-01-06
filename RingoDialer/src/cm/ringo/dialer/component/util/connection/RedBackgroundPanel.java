package cm.ringo.dialer.component.util.connection;

import java.awt.Color;
// import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

/**
 * Classe definissant un fond d'un degrade rouge.
 * @author Charles Moute
 * @version 1.0.1, 8/8/2010
 */
public class RedBackgroundPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel content;
	// private Color color1 = new Color(192,80,77);
	// private Color color2 = new Color(146,54,51);
	
	// private Color color1 = new Color(227, 32, 41);
	// private Color color2 = new Color(227, 32, 41);	
	// private GradientPaint backPaint = new GradientPaint(120,140,color1,180,200,color2);

	public RedBackgroundPanel(JPanel panel){			
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
		surface.setPaint(new Color(226, 22, 48));
		// surface.setBackground(color);
		Rectangle2D zone = new Rectangle2D.Double(0, 0, content.getWidth()+200, content.getHeight()+200);
		surface.fill(zone);
		surface.draw(zone);		
	}

}
