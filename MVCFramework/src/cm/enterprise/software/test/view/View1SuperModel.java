package cm.enterprise.software.test.view;

import cm.enterprise.software.component.model.event.AnyChangedEvent;
import cm.enterprise.software.exception.ProgramException;
import cm.enterprise.software.helper.Application;
import cm.enterprise.software.test.controller.ControllerOfSuperModel;
import cm.enterprise.software.test.controller.model1.ControllerOfModel1;
import cm.enterprise.software.test.controller.model2.ControllerOfModel2;
import cm.enterprise.software.test.event.Event3;
import cm.enterprise.software.test.model.SuperModelTest;
import cm.enterprise.software.test.multilingualresource.ResourceSuperModelTest;
import cm.enterprise.software.test.util.MessageOfSuperModelTest;
import cm.enterprise.software.test.view.model1.View1Model1;
import cm.enterprise.software.test.view.model2.View1Model2;

import java.awt.*;

import javax.swing.*;
import java.awt.event.*;
import java.util.Locale;

//import cm.enterprise.software.test.event.model2.Event1;
//import cm.enterprise.software.test.event.model1.Event2;

/**
 * Exemple 1 de vue de super modele. Cette vue permet la
 * modification du super model de test. Cette vue sera 
 * constitue a partir des vues de ses sous modeles.
 * @author Charles Moute
 * @version 1.0.1, 17/07/2010
 *
 */
public class View1SuperModel extends ViewSuperModel {

	private JPanel jpanel,panel1,panel2,contentPanel;
	private JPanel view1, view2 ;
	private JLabel label;
	private JTextField value ;
	private JButton validButton;
	
	public View1SuperModel(ControllerOfSuperModel controller)throws ProgramException {
		super(controller);
		
		/*
		 * Vu que l'on veut utiliser les vues des sous modeles on va les instancier.
		 * Si on ne les avait pas deja ajoutees, on les ajouterait avant de l'instancier.
		 * Supposons que l'on ait defini d'autres vues pour nos sous modeles, independaments
		 * des vues par defaut que proposent les sous modeles, on les ajouterait ici avant de
		 * les instancier. 
		 * Exemple: 
		 * - Pour ajouter une vue VueX au sous model d'instance 1 de classe Model1
		 *   et de controleur ControllerOfModel1 de notre sous model on aurait fait :
		 *   controller.getSubController(ControllerOfModel1.class).addClassOfView(VueX.class)
		 * - Pour Instancier cette vue, une fois qu'elle a deja ete ajoute au model :
		 *   controller.getSubController(ControllerOfModel1.class).addView(VueX.class);
		 */
		
		/*
		 * Instanciation de la vue View1Model1.class car le type de vue View1Model1.class est deja ajoute
		 * au controller du model 1, Voire le code source de ControllerOfModel1.
		 * On donne a la vue que l'on instancie, le controlleur instancie pour la gestion du sous model 1
		 * On aurait pu lui donner un autre controlleur, alors bien que la vue serait instancie
		 * par ce controlleur elle aurait deux controlleurs, je vous laisse imaginer les degats dans le
		 * cas d'une mauvaise gestion.
		 */
		ControllerOfModel1 model1Controller = controller.getSubController(ControllerOfModel1.class);
		model1Controller.installView(View1Model1.class,model1Controller);
		
		/*
		 * Instanciation de la vue View1Model2.class car le type de vue View1Model2.class est deja ajoute
		 * au controller du model 2, Voire le code source de ControllerOfModel2.
		 * On donne a la vue que l'on instancie, le controlleur instancie pour la gestion du sous model 1
		 * On aurait pu lui donner un autre controlleur, alors bien que la vue serait instancie
		 * par ce controlleur elle aurait deux controlleurs, je vous laisse imaginer les degats dans le
		 * cas d'une mauvaise gestion.
		 */
		ControllerOfModel2 model2Controller = controller.getSubController(ControllerOfModel2.class);
		model2Controller.installView(View1Model2.class,model2Controller);
		
		//Construction de la super vue.
		contentPanel = new JPanel(new BorderLayout());
		
		jpanel = new JPanel(new BorderLayout());
		panel1 = new JPanel(new FlowLayout());
		
		label = new JLabel();
		label.setPreferredSize(new Dimension(230,25));
		panel1.add(label);
		
		value = new JTextField();
		value.setPreferredSize(new Dimension(255,23));
		panel1.add(value);
		
		panel1.setBorder(BorderFactory.createTitledBorder("Attribut Super Model"));
		jpanel.add(panel1,BorderLayout.NORTH);
		
		view1 = new JPanel(new BorderLayout());
		//Ajout de la vue du model 1 au panneau.
		JPanel contentSubView = (JPanel) controller.getSubController(ControllerOfModel1.class).getContentOf(View1Model1.class);
		view1.add(contentSubView,BorderLayout.NORTH);
		view1.setBorder(BorderFactory.createTitledBorder("Vue du Model 1"));
		jpanel.add(view1,BorderLayout.SOUTH);
		contentPanel.add(jpanel,BorderLayout.NORTH);
		
		jpanel = new JPanel(new BorderLayout());
		
		view2 = new JPanel(new BorderLayout());
		//Ajout de la vue du model 2 au panneau
		contentSubView = (JPanel) controller.getSubController(ControllerOfModel2.class).getContentOf(View1Model2.class);
		view2.add(contentSubView,BorderLayout.NORTH);
		view2.setBorder(BorderFactory.createTitledBorder("Vue du model 2"));
		jpanel.add(view2,BorderLayout.NORTH);
		
		panel2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		validButton = new JButton();
		validButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				validAction();
			}
		});
		panel2.add(validButton);
		jpanel.add(panel2,BorderLayout.SOUTH);		
		contentPanel.add(jpanel,BorderLayout.SOUTH);
	}

	@Override
	public void disable() {
		label.setEnabled(false);
		value.setEnabled(false);
		validButton.setEnabled(false);
		ControllerOfSuperModel csm = (ControllerOfSuperModel)controller;
		csm.getSubController(ControllerOfModel1.class).disableView(View1Model1.class);
		csm.getSubController(ControllerOfModel2.class).disableView(View1Model2.class);
	}

	@Override
	public void enable() {
		label.setEnabled(true);
		value.setEnabled(true);
		validButton.setEnabled(true);
		ControllerOfSuperModel csm = (ControllerOfSuperModel)controller;
		csm.getSubController(ControllerOfModel1.class).enableView(View1Model1.class);
		csm.getSubController(ControllerOfModel2.class).enableView(View1Model2.class);
	}

	@Override
	public Object getContent() {
		
		/*
		 * Partie de la gestion des parametres multilingues de la super vue.
		 *  
		 */
		
		if(Application.instanceOfResourceExists(ResourceSuperModelTest.class)){
			
			/*
			 * Vu qu'une instance de gestion du multilinguisme de la super ressource
			 * existe, nous pouvons, compte tenu de la definition dans la classe
			 * de resourceSuperModelTest, dire que la gestion multilingue des sous
			 * ressources est aussi definie. Donc nous pouvons intiliser les parametres
			 * multilingue de notre super vue et laissez le soin aux controleur des sous
			 * vues de faire le travail pour les sous vues, mais nous aurons aussi le faire
			 *  de la meme maniere* que l'on la fait dans les exemples de Model1 et Model2. 
			 *  En  parametrant la methode invokeMethodXXX du bon objet. Mais ceci n'est valable
			 *  que dans le cas ou notre vue se definit entierement les interfaces des sous modeles,
			 *  ceci implique qu'il ecoute l'emission des evenements en provenance des sous modeles.  
			 */
			//Initalisation des parametres multilingues de la super ressource
			ResourceSuperModelTest rsrc = Application.getResource(ResourceSuperModelTest.class);
			label.setText(rsrc.getValueOf("attributeLabel"));
			validButton.setText(rsrc.getValueOf("validateLabel"));
		}
				
		/*
		 * Partie de l'initialisation des valeurs des champs modifiables 
		 */
		
		if(controller!=null){
			/*
			 * Ceci sera toujours valide si vous instanciez la vue par le controleur.
			 * Si au contraire, vous instanciez directement la classe, eh beuh
			 * a vous de vous assurez de la validiter de ce parametre.
			 */
			SuperModelTest m = (SuperModelTest) controller.getModel();
			value.setText(m.getValueOfAtttrribute());			
		}
		
		ControllerOfSuperModel csm = (ControllerOfSuperModel)controller;
				
		/*
		 * Vu que nous utilisons directement les vues des sous composants
		 * nous devons toujours les recuperer depuis leur controlleur
		 * qui se charge de la mise a jour de la vue. 
		 */
		if(view1.getComponentCount()>0) view1.remove(0);
		view1.add((JPanel)csm.getSubController(ControllerOfModel1.class).getContentOf(View1Model1.class));
		
		if(view2.getComponentCount()>0) view2.remove(0);
		view2.add((JPanel)csm.getSubController(ControllerOfModel2.class).getContentOf(View1Model2.class));
				
		return contentPanel;
	}

	@Override
	public void hide() {
		contentPanel.setVisible(false);
		ControllerOfSuperModel csm = (ControllerOfSuperModel)controller;
		csm.getSubController(ControllerOfModel1.class).hideView(View1Model1.class);
		csm.getSubController(ControllerOfModel2.class).hideView(View1Model2.class);
	}

	@Override
	public void show() {
		ControllerOfSuperModel csm = (ControllerOfSuperModel)controller;
		csm.getSubController(ControllerOfModel1.class).showView(View1Model1.class);
		csm.getSubController(ControllerOfModel2.class).showView(View1Model2.class);
		contentPanel.setVisible(true);
	}
	
	public void validAction(){
		
		//Construction du message d'information des vues du super modele.
		MessageOfSuperModelTest message = new MessageOfSuperModelTest(MessageOfSuperModelTest.SUPERMODEL_NOTIFICATION,new String(value.getText()));
		
		//On va d'abord notifier le changement de la valeur du super modele
		ControllerOfSuperModel csm = (ControllerOfSuperModel)controller ;
		csm.notifyEvent(message);
		
		//Comme l'action doit aussi valider les eventuels changements des sous modeles
		
		//Lancement de l'eventuelle modification du sous modele Model1
		csm.getSubController(ControllerOfModel1.class).getViewOfClass(View1Model1.class).validAction();
				
		//Lancement de l'eventuelle modification du sous modele Model2
		csm.getSubController(ControllerOfModel2.class).getViewOfClass(View1Model2.class).validAction();
		
		/*
		 * Il faut noter que getViewOfClass(ClasseDeVue) ne retourne que la premier instance de la vue
		 * car il n'est censé qu'avoir une seul instance de cette classe, mais si vous avez
		 * redefinir les fonctions de telle sorte que vous puissiez avoir plusieurs instances
		 * vous pouvez utiliseze getAllViews() pour parcourir les vues a la recherche de celle
		 * que vous voulez ou vous pouvez cree une fonction getViewOfClass(classeDevue, int numeroInstance)
		 * qui vous permettra d'obtenir l'instance de type de classe classeDevue que vous souhaitez.
		 * Bref la seul limite est votre imagination. 
		 */
	}

	@Override
	public void anyChanged(AnyChangedEvent event) {
		//if(!event.getSource().equals(controller.getModel())){
			String name = this.getClass().getSimpleName();
			System.out.println(name+" a recu le message : "+event.getInformationOnChange().toString());
		//}
	}
	
	/*
	 * Les fonctions d'ecoutes ci dessous sont desactivees car nous utilisons les vues des sous modeles
	 * et laissons chaque composant s'auto gerer, Si l'on voulait les gerer nous meme, il aurait
	 * suffit que la classe parente de cette vue, ou tout simple la vue courante, implemente 
	 *  les ecouteurs adequates.
	 */
	
	/*@Override
	public void event1Changed(Event1 event) {
		String name = event.getSource().getClass().getSimpleName();
		System.out.println("Instance de "+name+" a comme valeur = "+event.getValue());
	}

	@Override
	public void event2Changed(Event2 evt) {
		String name = evt.getSource().getClass().getSimpleName();
		System.out.println("Instance de "+name+" a comme valeur = ("+evt.getAttribute()+","+evt.getValue()+")");	
	}*/
	
	@Override
	public void event3Changed(Event3 evt) {
		String name = evt.getSource().getClass().getSimpleName();
		System.out.println(this.getClass().getSimpleName()+" dit Instance de "+name+" a comme nouvel attribut = "+evt.getNewAttribut());
		value.setText(evt.getNewAttribut());
	}
	
	public static void main(String[] arg) throws ProgramException{
		
		//Ajout de la ressource multilingue du super model de test.
		Application.addResource(ResourceSuperModelTest.class);
		/*
		 * Si on ne definit pas la langue courante avec Helper.setCurrentLanguage(language);
		 * C'est la langue par defaut de chaque ressource qui sera utilisez. Nous on va definir
		 * la langue Anglaise comme notre language courante. pour l'application
		 */
		Application.setCurrentLanguage(Locale.US.getLanguage());
		
		JFrame frame = new JFrame("Vue 1 SuperModelTest");
		ControllerOfSuperModel controller = new ControllerOfSuperModel(new SuperModelTest()); 
		View1SuperModel view = new View1SuperModel(controller);
		frame.getContentPane().add((JPanel)view.getContent());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.pack();
		frame.setLocation(50, 50);
		frame.setVisible(true);
	
	}

}
