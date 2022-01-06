package helper.component;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

/**
 * 
 * @author LeKeutch
 * @version 1.0
 *
 */

public class YooRoundedPane extends JComponent{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2626140328132580243L;

	/**
	 * @param args
	 */
	/**
	 *   <code>attribut</code> : content
	 *   <code>type</code> : JComponent
	 *   <code>role</code> : content contient le composant qui faut representer.
	 */	 
	private JComponent content;
	
	/**
	 *   <code>attribut</code> : borderPaint
	 *   <code>type</code> : Paint
	 *   <code>role</code> : il permet de donner une peinture au bord 
	 *       - soit une couleur, 
	 *       - soit du degrade ou 
	 *       - encore une teinture venant d'une figure ou d'une image
	 *   @see #backPaint
	 */
	private Paint borderPaint;
	
	/**
	 *   <code>attribut</code> : backPaint
	 *   <code>type</code> : Paint
	 *   <code>role</code> : il permet de donner une peinture au font du composant 
	 *       - soit une couleur, 
	 *       - soit du degrade ou 
	 *       - encore une teinture venant d'une figure ou d'une image
	 *   @see #borderPaint
	 */
	private Paint backPaint;
	
	/**
	 *   <code>attribut</code> : arcw, arch
	 *   <code>type</code> : double
	 *   <code>role</code> : ils s'occupent d'qrrondissement du composant conteneur.  
	 *   
	 */
	private double arcw,arch;
	
	/**
	 *   <code>attribut</code> : strokeBorder
	 *   <code>type</code> : int
	 *   <code>role</code> : grossissement des bords arrondis du composant. 
	 */
	private int strokeBorder;
	
	
	/**
	 *   <code>attributs</code> : top, let, right, bottom
	 *   <code>type</code> : int
	 *   <code>role</code> : Marges a respecter. 
	 */	
	private int top=5, left=5, right=5, bottom=6;
	
	/**
	 * 
	 * @param comp : conteneur
	 */
	public YooRoundedPane(JComponent comp){
	    this.setLayout(new BorderLayout());
		content = comp;
		
		if(content==null){
			content = new JPanel();
			content.setBackground(Color.WHITE);
		//	content.setBorder(BorderFactory.createEtchedBorder(Color.BLACK,Color.BLACK)); 
		}
		
		content.setOpaque(false);
		this.setBackground(Color.WHITE);
		
		this.setBorder(BorderFactory.createEmptyBorder(top,left,bottom,right));
		
		this.add(content);
		borderPaint = Color.BLACK;
		backPaint =  Color.WHITE;//new GradientPaint(new Point2D.Double(80,80),Color.DARK_GRAY.brighter(),new Point2D.Double(100,100),Color.BLACK.brighter(),true);
		arcw =arch =10;
		strokeBorder=1;
		repaint();
	}
	
	
	public YooRoundedPane(){
		this(null);
	}
	
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D  crayon = (Graphics2D)g;
		crayon.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		crayon.setPaint(backPaint);
		crayon.setStroke(new BasicStroke(strokeBorder,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
		RoundRectangle2D rr2 = new RoundRectangle2D.Double(2,2,this.getWidth()-6,this.getHeight()-5,arcw,arch);
		crayon.fill(rr2);		
		crayon.setPaint(borderPaint);		
		crayon.setStroke(new BasicStroke(strokeBorder,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
		crayon.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		RoundRectangle2D rr = new RoundRectangle2D.Double(2,2,this.getWidth()-6,this.getHeight()-5,arcw,arch);
		crayon.draw(rr);
		
	}
	
	/**
	 * 
	 * @param p :  type <code>Paint</code>  et est la nouvelle peinture de bord du conteur arrondi
	 */
	
	public void setBorderPaint(Paint p){
		borderPaint =p;
		repaint();
	}
	
	public void setBackPaint(Paint p){
		backPaint = p ;
		repaint();
	}
	
	public void setContent(JComponent comp){
		this.remove(content);
		content = comp;
		content.setOpaque(false);
		this.add(content);
		this.revalidate();
		
	}
	
	public void setArcs(double aw, double ah){
		arcw = aw; arch = ah;
		int lr =4,tb=4;
	    if(aw>10)
	    	 lr+= (int)(aw/10);
	    if(ah>10)
	    	tb+=(int)(ah/10);
	   top= bottom = tb+strokeBorder;
	    left= strokeBorder+lr;
	    right=left+1;
	    this.setBorder(BorderFactory.createEmptyBorder(top,left,bottom,right));
	    	
	    
		repaint();
	}
	
	public void setStrokeBorder(int stroke){
		int residu = stroke-strokeBorder;
		strokeBorder = stroke;
		top+=residu;
		left+=residu;
		bottom+=residu;
		right+=residu;
		this.setBorder(BorderFactory.createEmptyBorder(top,left,bottom,right));
		repaint();
	}
	
	public JComponent getContent(){ return content; }
	public Paint getBorderPaint(){ return borderPaint;}
	public Paint getBackPaint(){ return backPaint;}
	public int getStrokeBorder(){return strokeBorder;}
	
	public static void createFrame(String name){
		JFrame fen = new JFrame(name);
		fen.setBounds(10,10,400,300);
		fen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel pan = new JPanel();
		pan.setBackground(Color.CYAN.darker());
		JButton b = new JButton("Un nouveau Bouton");
		b.setOpaque(false);
		b.setForeground(new Color(200,200,200));
		b.setCursor(new Cursor(Cursor.HAND_CURSOR));
		b.setFocusPainted(false);
		b.setBorderPainted(false);
		b.setContentAreaFilled(false);
		YooRoundedPane grp = new YooRoundedPane(b);
		grp.setBackPaint(Color.DARK_GRAY);
		pan.add(grp);
		//pan.setBorder(BorderFactory.createEtchedBorder(Color.BLACK,Color.WHITE));
		YooRoundedPane rp = new YooRoundedPane(pan);
		rp.setArcs(25,25);
		JPanel comp = new JPanel();
		comp.setLayout(new BorderLayout());
		comp.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		rp.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		rp.setBackPaint(new GradientPaint(new Point2D.Double(50,50),new Color(100,100,100),new Point2D.Double(50,200),Color.BLACK));
		rp.setBorderPaint(Color.BLACK);
		/*rp.getContent().setOpaque(true);
		rp.getContent().setBackground(Color.BLACK);*/
		
		//rp.setArcs(40,40);
		comp.add(rp);
		comp.setBackground(Color.BLACK);
		fen.setContentPane(comp);
		fen.setVisible(true);
	}
}