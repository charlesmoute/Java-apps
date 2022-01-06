package cm.enterprise.software.test.view.model1;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import cm.enterprise.software.component.model.event.AnyChangedEvent;
import cm.enterprise.software.exception.ProgramException;
import cm.enterprise.software.helper.Application;
import cm.enterprise.software.test.controller.model1.ControllerOfModel1;
import cm.enterprise.software.test.event.model1.Event2;
import cm.enterprise.software.test.interfaces.TModule;
import cm.enterprise.software.test.model.model1.Model1;
import cm.enterprise.software.test.multilingualresource.model1.ResourceModel1;
import cm.enterprise.software.test.view.ViewModel1;

/**
 * Exemple 1 de vue  pour le modele Model1. Cette vue a pour vocation premiere
 * de donner une vue pour la modification des donnees du modele 1.
 * @author Charles Moute
 * @version 1.0.1, 17/07/2010
 */
public class View1Model1 extends ViewModel1 {

	private JLabel attributeLabel,valueLabel ;
	private JTextField attributeField,valueField;
	private JButton validButton;
	
	private JPanel contentPanel ;
	
	public View1Model1(ControllerOfModel1 controller) {
		super(controller);
		attributeLabel = new JLabel("");
		valueLabel = new JLabel("");
		Dimension dim = new Dimension(255,25);
		attributeField = new JTextField("");
		attributeField.setPreferredSize(dim);
		valueField = new JTextField("");		
		valueField.setPreferredSize(dim);
		contentPanel = new JPanel(new BorderLayout());

		JPanel jpanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		jpanel.add(attributeLabel);
		jpanel.add(attributeField);
		contentPanel.add(jpanel,BorderLayout.NORTH);
		
		JPanel panel = new JPanel(new BorderLayout());
		
		jpanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		jpanel.add(valueLabel);
		jpanel.add(valueField);
		panel.add(jpanel,BorderLayout.NORTH);
		
		jpanel = new JPanel(new FlowLayout());
		validButton = new JButton("");
		validButton.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent evt){
				validAction();
			}
			
		});
		jpanel.add(validButton);
		panel.add(jpanel,BorderLayout.SOUTH);
		
		contentPanel.add(panel, BorderLayout.SOUTH);				
	}

	@Override
	public void disable() {
		attributeLabel.setEnabled(false);
		attributeField.setEnabled(false);
		valueLabel.setEnabled(false);
		valueField.setEnabled(false);
		validButton.setEnabled(false);
	}

	@Override
	public void enable() {
		attributeLabel.setEnabled(true);
		attributeField.setEnabled(true);
		valueLabel.setEnabled(true);
		valueField.setEnabled(true);
		validButton.setEnabled(true);
	}

	@Override	
	public Object getContent() {
		
		/*
		 * Partie de la gestion des parametres multilingues de la vue.
		 * L'on va proceder en deux manieres, la premierre
		 * celle ou la condition teste que la ressource est directement associee
		 * a l'assistant de l'application, ceci permet de considerer que le model1 
		 * est une ressource non reutilisable.
		 * La deuxieme celle ou la condition teste si le module TModule existe, ceci
		 * permet de considerer le model 1 comme faisant parti de ce module TModule.
		 * Les instructions suivantes ne sont pas necessaires. Si votre model doit faire 
		 * partir directement de l'assistant, la premiere condition seul est necessaire, si 
		 * au contraire vous developpez un module, alors seul la deuxieme condition est necessaire
		 * si, comme dans notre cas votre module composant peut etre directement utilise et
		 * aussi etre implemente par un module alors vous pouvez mettre les deux conditions.
		 * Seul limitre, l'imagination personnelle. 
		 */
		
		ResourceModel1 resource = null ;
		
		if(Application.instanceOfResourceExists(ResourceModel1.class)) resource = Application.getResource(ResourceModel1.class);
		else if(Application.instanceOfModuleExists(TModule.class)){
			resource = Application.getModule(TModule.class).getResource(ResourceModel1.class);
		}
		
		
		if(resource!=null){
			attributeLabel.setText(resource.getValueOf("attributeLabel"));
			valueLabel.setText(resource.getValueOf("valueLabel"));
			valueField.setToolTipText(resource.getValueOf("valueToolTip"));
			validButton.setText(resource.getValueOf("validLabel"));
		}
		
		/*
		 * Partie de l'initialisation des valeurs des champs modifiables 
		 */
		if(controller!=null){
			Model1 m = (Model1) (((ControllerOfModel1)controller).getModel());
			attributeField.setText(m.getAttribut());
			valueField.setText(""+m.getValue().intValue());
		}
		return contentPanel;
	}

	@Override
	public void hide() {		
		contentPanel.setVisible(false);
	}

	@Override
	public void show() {
		contentPanel.setVisible(true);
	}

	@Override
	public void anyChanged(AnyChangedEvent event) {
		if(controller!=null && !event.getSource().equals(controller.getModel())){
			String name = event.getSource().getClass().getSimpleName();
			System.out.println("Vue 1 du model 1 a recu le message : "+event.getInformationOnChange().toString()+" en provenance de "+name);
		}
	}

	@Override
	public void event2Changed(Event2 evt) {
		String name = evt.getSource().getClass().getSimpleName();
		System.out.println("Vue 1 du Model 1 dit : Instance de "+name+" a comme valeur = ("+evt.getAttribute()+","+evt.getValue()+")");
	}
	
	public void validAction(){
		int value = 0 ;
		try{
			value = Integer.parseInt(valueField.getText());
		}catch(Exception err){			
			Container container = contentPanel.getParent();
			int count = 0 ;
			// La on recherche parmi les 10 derniers ancetres du contenu celui qui est une fenetre
			while((count<10) && !(container instanceof JFrame)){
				if(container==null) break;
				container = container.getParent();
				count++;
			}
			JFrame frame = (count==10)? null:(JFrame) container;
			//On affiche un message d'erreur en utilisant l'ancetre pour bloquer la boite de dialigue
			JOptionPane.showMessageDialog(frame, valueField.getToolTipText());
		}
		
		if(controller!=null){
			controller.notifyEvent(new String(attributeField.getText()));
			controller.notifyEvent(new Integer(value));
		}
	}
	
	public static void main(String[] arg) throws ProgramException{
		Application.addResource(ResourceModel1.class);
		JFrame frame = new JFrame("Vue 1 Model 1");
		/*
		 * Attention ici le controlleur est null parce que l'on veut juste tester la vue.
		 * Mais en temps normale, si vous choissisez d'extansier la vue directement comme 
		 * ci-dessous vous devez vous assurez de passer un controleur correcte avec le 
		 * modele correcte. Nous vous conseillons d'utilisez toujours le controleur pour
		 * avoir le control sur le model et les vues.
		*/ 
		View1Model1 view = new View1Model1(null);
		frame.getContentPane().add((JPanel)view.getContent());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
}
