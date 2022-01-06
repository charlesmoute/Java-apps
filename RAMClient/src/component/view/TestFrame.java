package component.view;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TestFrame  extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	private JButton launch;
	private SimulationFrame dialog=null;
	
	public TestFrame(){
	
		this.setBounds(50, 50, 350, 80);
		
		this.setTitle("Lanceur");
		
		Container con = this.getContentPane();
		
		launch = new JButton("charger");
		launch.addActionListener(this);
		
	    this.setLayout(new FlowLayout());
	    con.add(launch);
		
	    con.setBackground(Color.BLACK);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
	}
	
	
	public void actionPerformed(ActionEvent ae){
		
	  if(dialog == null) dialog = new SimulationFrame(this,"loading1.gif");
	  dialog.showDialog(this.getX()+this.getWidth()/2 - dialog.getWidth()/2, this.getY() + this.getHeight()/2 - dialog.getHeight()/2 );	  	
	}
	
	/*public static void main(String[] list){
		new TestFrame();
	}*/
	
}
