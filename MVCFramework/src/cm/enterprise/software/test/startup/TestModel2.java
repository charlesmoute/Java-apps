package cm.enterprise.software.test.startup;

import cm.enterprise.software.exception.ProgramException;
import cm.enterprise.software.helper.Application;
import cm.enterprise.software.test.controller.model2.ControllerOfModel2;
import cm.enterprise.software.test.model.model2.Model2;
import cm.enterprise.software.test.multilingualresource.model2.ResourceModel2;
import cm.enterprise.software.test.view.model2.View1Model2;
import cm.enterprise.software.test.view.model2.View2Model2;

import java.awt.*;
import java.awt.event.*;
import java.util.Locale;

import javax.swing.*;

/**
 * Cette classe est la classe de test du modele 2 avec tous les concepts
 * gravitant autour de lui.
 * @author Charles Moute
 * @version 1.0.1, 21/07/2010
 */
public class TestModel2 extends Application {

	ControllerOfModel2 controller;
	
	JFrame frame, view1,view2 ; 
	
	JComboBox langues ;
	JButton showViews, hideViews,enableViews, disableViews;
	
	JButton showView1,hideView1, enableView1, disableView1, validButton ;
	JButton showView2,hideView2, enableView2, disableView2 ;
	
	JPanel jpanel,panel1,panel2 ;
	
	/**
	 * Instancie une instance de test du model2
	 * @throws ProgramException
	 */
	public TestModel2() throws ProgramException{
		addResource(ResourceModel2.class);
		controller = new ControllerOfModel2(new Model2());
		//On instancie les vues qui seront utilisez pour le test.
		controller.installView(View1Model2.class,controller);
		controller.installView(View2Model2.class,controller);
		
		frame = new JFrame("Fenetre de control du model 2");
		Container contentPane = frame.getContentPane();
		
		jpanel = new JPanel(new BorderLayout());
		
		//Ajout de la premierligne de control
		panel1 = new JPanel(new FlowLayout());
		
		langues = new JComboBox(getLanguagesList());
		langues.setSelectedItem(Locale.getDefault().getLanguage());
		langues.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
					changeLanguage((String)langues.getSelectedItem());
			}
		});
		panel1.add(langues);
		
		showViews = new JButton("Show Views");
		showViews.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				showViews();
			}
		});
		panel1.add(showViews);
		
		hideViews = new JButton("Hide Views");
		hideViews.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				hideViews();
			}
		});
		panel1.add(hideViews);
		
		enableViews = new JButton("Enable Views");
		enableViews.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				enableViews();
			}
		});
		panel1.add(enableViews);
		
		disableViews = new JButton("Disable Views");
		disableViews.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				disableViews();
			}
		});
		panel1.add(disableViews);
		
		//Ajout de la deuxieme ligne de control
		panel2 = new JPanel(new FlowLayout());
		
		showView1 = new JButton("Show View 1");
		showView1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				showView1();
			}
		});
		panel2.add(showView1);
		
		hideView1 = new JButton("Hide View 1");
		hideView1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				hideView1();
			}
		});
		panel2.add(hideView1);
		
		enableView1 = new JButton("Enable View 1");
		enableView1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				enableView1();
			}
		});
		panel2.add(enableView1);
		
		disableView1 = new JButton("Disable View 1");
		disableView1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				disableView1();
			}
		});
		panel2.add(disableView1);
		
		validButton = new JButton("Valid Modification  of View 1");
		validButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				validAction();
			}
		});
		panel2.add(validButton);
		
		jpanel.add(panel1,BorderLayout.NORTH);
		jpanel.add(panel2,BorderLayout.SOUTH);		
		contentPane.add(jpanel,BorderLayout.NORTH);
		
		//Ajout de la derniere ligne de composants de control.
		panel2 = new JPanel(new FlowLayout());
		
		showView2 = new JButton("Show View 2");
		showView2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				showView2();
			}
		});
		panel2.add(showView2);
		
		hideView2 = new JButton("Hide View 2");
		hideView2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				hideView2();
			}
		});
		panel2.add(hideView2);
		
		enableView2 = new JButton("Enable View 2");
		enableView2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				enableView2();
			}
		});
		panel2.add(enableView2);
		
		disableView2 = new JButton("Disable View 2");
		disableView2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				disableView2();
			}
		});
		panel2.add(disableView2);
		
		contentPane.add(panel2,BorderLayout.SOUTH);
		
		//Parametrage de la vue 1 
		view1 = new JFrame("View 1");
		view1.setLocation(50, 50);
		view1.setResizable(false);
		
		//Parametrage de la vue 2
		view2 = new JFrame("view 2");
		view2.setLocation(550, 50);
		view2.setResizable(false);
		
		this.changeLanguage(Locale.getDefault().getLanguage());
			
	}
	
	
	public void test(){
		frame.setLocation(350, 250);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
	}
	
	/**
	 * Change le langage des vues du model 1
	 * @param language Langue a affecte au vues
	 */
	public void changeLanguage(String language){
		try {
			setCurrentLanguage(language);
		} catch (ProgramException e) {
			JOptionPane.showMessageDialog(frame, e.getMessage());
		}
		if (view1.getContentPane().getComponentCount()>0) view1.getContentPane().remove(0);
		view1.getContentPane().add((JPanel)controller.getContentOf(View1Model2.class));
		view2.repaint();
		view1.pack();
		
		if (view2.getContentPane().getComponentCount()>0) view2.getContentPane().remove(0);
		view2.getContentPane().add((JPanel)controller.getContentOf(View2Model2.class));
		view2.repaint();
		view2.pack();
	}
	
	/**
	 * Affiche les vues
	 */
	public void showViews(){
		view1.setVisible(true);
		view2.setVisible(true);
		controller.showViews();
	}
	
	/**
	 * Cache les vues 
	 */
	public void hideViews(){
		view1.setVisible(true);
		view2.setVisible(true);
		controller.hideViews();
	}
	
	/**
	 * Permet les interactions avec les vues 
	 */
	public void enableViews(){
		view1.setVisible(true);
		view2.setVisible(true);
		controller.enableViews();
	}
	
	/**
	 * Ne permet pas les interactions avec les vues
	 */
	public void disableViews(){
		view1.setVisible(true);
		view2.setVisible(true);
		controller.disableViews();
	}
	
	/**
	 * Affiche la vue 1
	 */
	public void showView1(){
		view1.setVisible(true);
		controller.showView(View1Model2.class);
	}
	
	/**
	 * Cache la vue 1
	 */
	public void hideView1(){
		view1.setVisible(true);
		controller.hideView(View1Model2.class);
	}
	
	/**
	 * Permet les interactions avec la vue 1
	 */
	public void enableView1(){
		view1.setVisible(true);
		controller.enableView(View1Model2.class);
	}
	
	/**
	 * Ne permet pas les interactions avec la vue 1
	 */
	public void disableView1(){	
		view1.setVisible(true);
		controller.disableView(View1Model2.class);
	}
	
	/**
	 * Valide les modifications de la vue 1
	 */
	public void validAction(){
		view1.setVisible(true);
		controller.getViewOfClass(View1Model2.class).validAction();
	}
	
	/**
	 * Affiche la vue 2
	 */
	public void showView2(){
		view2.setVisible(true);
		controller.showView(View2Model2.class);
	}
	
	/**
	 * Cache la vue 2
	 */
	public void hideView2(){
		view2.setVisible(true);
		controller.hideView(View2Model2.class);
	}
	
	/**
	 * Permet les interactions avec la vue 2
	 */
	public void enableView2(){
		view2.setVisible(true);
		controller.enableView(View2Model2.class);
	}
	
	/**
	 * Ne permet pas les interactions avec la vue 2
	 */
	public void disableView2(){
		view2.setVisible(true);
		controller.disableView(View2Model2.class);
	}
	
	
	public static void main(String[] args) throws ProgramException {

		TestModel2 soft = new TestModel2();
		soft.test();
	}

}
