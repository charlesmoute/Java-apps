/**
 * 
 */
package helper.component;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
//import javax.swing.event.*;
/**
 * @author Admin
 *
 */
public class YooDialog  extends JDialog implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	private JTable table;
	private JButton ok;
	public YooDialog(Frame owner,   String name, final String[][] data, final String[] nameCols){
		super(owner, name, true);		
		this.setBounds(owner.getX()+ owner.getWidth()/2 -250, owner.getY()+ owner.getHeight()/2 -150, 500, 300);
		
		Container contenu = this.getContentPane();
		
		table = new JTable(data, nameCols);
		
		JScrollPane jsp = new JScrollPane(table);
		
		YooDivPane ydp = new YooDivPane(jsp);
		
		ydp.setMargin(20);
		ydp.setBackDiv(Color.black);
		
		YooRoundedPane yrp = new YooRoundedPane(ydp);
		yrp.setBackPaint(Color.black);
		yrp.setBorderPaint(Color.black);
		
		yrp.setArcs(25,25);
		
		YooDivPane ydp1 = new YooDivPane(yrp);
		ydp1.setMargin(10);
		ydp1.setBackDiv(Color.white);
		contenu.add(ydp1);
		
		contenu.setBackground(Color.white);
		
		
		JPanel okP = new JPanel();
		okP.setBackground(Color.white);
		ok = new  JButton("Ok");
		ok.addActionListener(this);		
		okP.add(ok);
		
		contenu.add(okP, BorderLayout.SOUTH);	
	}
	
	public void actionPerformed(ActionEvent ae){
		this.setVisible(false);
	}
}
