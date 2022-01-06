package cm.enterprise.software.test.startup;

import cm.enterprise.software.exception.ProgramException;
import cm.enterprise.software.helper.Application;
import cm.enterprise.software.helper.Constants;
import cm.enterprise.software.test.controller.ControllerOfSuperModel;
import cm.enterprise.software.test.model.SuperModelTest;
import cm.enterprise.software.test.multilingualresource.ResourceSuperModelTest;
import cm.enterprise.software.test.view.View1SuperModel;
import cm.enterprise.software.test.view.View2SuperModel;

import java.awt.*;

import javax.swing.*;

import java.awt.event.*;
import java.util.Locale;

/**
 * Classe de test de la notion du SuperController
 * @author Charles Moute
 * @version 1.0.1, 23/07/2010
 */
public class TestSuperModel extends Application {

	
	private ControllerOfSuperModel controller;
	
	private JFrame frame, view1,view2 ; 
	
	private JComboBox langues ;
	private JButton showViews, hideViews,enableViews, disableViews;
	
	private JButton showView1,hideView1, enableView1, disableView1, validButton ;
	private JButton showView2,hideView2, enableView2, disableView2 ;
	
	private JPanel jpanel,panel1,panel2 ;
	
	
	/**
	 * Instancie une instance de test du super modele de test
	 * @throws ProgramException
	 */
	public TestSuperModel() throws ProgramException{
		
		addResource(ResourceSuperModelTest.class);
		
		controller = new ControllerOfSuperModel(new SuperModelTest());
		/*
		 * On instancie les vues qui seront utilisez pour le test.
		 * C'est vu aurait pu etre directement instancie dans le controleur
		 * a la place de l'instruction
		 * controller.addView(View1Model1.class,controller);
		 * on autrait eut dans le controleur :
		 * addView(View1Model1.class,this);
		 * Mais s'aurait ete une precaution unitle car  on ne va pas necessaire
		 * utiliser tous les types d'une vue.
		 */
		controller.installView(View1SuperModel.class,controller);
		controller.installView(View2SuperModel.class,controller);
		
		frame = new JFrame("Fenetre de control du super modele");
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
		view2 = new JFrame("View 2");
		view2.setLocation(700, 50);
		view2.setResizable(false);
		
		this.changeLanguage(Locale.getDefault().getLanguage());
			
	}
	
	
	public void test(){
		frame.setLocation(350,450);
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
		view1.getContentPane().add((JPanel)controller.getContentOf(View1SuperModel.class));
		view1.repaint();
		view1.pack();
		
		if (view2.getContentPane().getComponentCount()>0) view2.getContentPane().remove(0);
		view2.getContentPane().add((JPanel)controller.getContentOf(View2SuperModel.class));
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
		controller.showView(View1SuperModel.class);
	}
	
	/**
	 * Cache la vue 1
	 */
	public void hideView1(){
		view1.setVisible(true);
		controller.hideView(View1SuperModel.class);
	}
	
	/**
	 * Permet les interactions avec la vue 1
	 */
	public void enableView1(){
		view1.setVisible(true);
		controller.enableView(View1SuperModel.class);
	}
	
	/**
	 * Ne permet pas les interactions avec la vue 1
	 */
	public void disableView1(){	
		view1.setVisible(true);
		controller.disableView(View1SuperModel.class);
	}
	
	/**
	 * Valide les modifications de la vue 1
	 */
	public void validAction(){
		view1.setVisible(true);
		controller.getViewOfClass(View1SuperModel.class).validAction();
	}
	
	/**
	 * Affiche la vue 2
	 */
	public void showView2(){
		view2.setVisible(true);
		controller.showView(View2SuperModel.class);
	}
	
	/**
	 * Cache la vue 2
	 */
	public void hideView2(){
		view2.setVisible(true);
		controller.hideView(View2SuperModel.class);
	}
	
	/**
	 * Permet les interactions avec la vue 2
	 */
	public void enableView2(){
		view2.setVisible(true);
		controller.enableView(View2SuperModel.class);
	}
	
	/**
	 * Ne permet pas les interactions avec la vue 2
	 */
	public void disableView2(){
		view2.setVisible(true);
		controller.disableView(View2SuperModel.class);
	}
	
	
	@SuppressWarnings("static-access")
	public static void main(String[] args) throws ProgramException {

		TestSuperModel soft = new TestSuperModel();
		soft.test();
		System.out.println("author = "+soft.getResource(Constants.class).getValueOf("author"));
	}
}
