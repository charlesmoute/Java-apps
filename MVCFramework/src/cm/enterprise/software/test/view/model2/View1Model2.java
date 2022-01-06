package cm.enterprise.software.test.view.model2;

import cm.enterprise.software.component.model.event.AnyChangedEvent;
import cm.enterprise.software.exception.ProgramException;
import cm.enterprise.software.helper.Application;
import cm.enterprise.software.test.controller.model2.ControllerOfModel2;
import cm.enterprise.software.test.event.model2.Event1;
import cm.enterprise.software.test.model.model2.Model2;
import cm.enterprise.software.test.multilingualresource.model2.ResourceModel2;
import cm.enterprise.software.test.view.ViewModel2;
import java.awt.*;

import javax.swing.*;
import java.awt.event.*;

/**
 * Exemple 1 de vue pour le modele Model2. Cette vue sera utilise pour l'edition
 * du modele 2
 * @author Charles Moute
 * @version 1.0.1, 17/07/2010
 */
public class View1Model2 extends ViewModel2 {

	private JLabel  label ;
	private JTextField value ;
	private JButton validButton ;
	
	private JPanel contentPanel ;
	
	public View1Model2(ControllerOfModel2 controller) {
		super(controller);
		contentPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		label = new JLabel("");
		Dimension dim = new Dimension(100,23);
		label.setPreferredSize(dim);
		contentPanel.add(label);
		value = new JTextField();
		value.setPreferredSize(dim);
		contentPanel.add(value);
		validButton = new JButton("");
		validButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				validAction();
			}
		});
		contentPanel.add(validButton);		
	}

	@Override
	public void disable() {
		label.setEnabled(false);
		value.setEnabled(false);
		validButton.setEnabled(false);
	}

	@Override
	public void enable() {
		label.setEnabled(true);
		value.setEnabled(true);
		validButton.setEnabled(true);
	}

	@Override
	public Object getContent() {
		/*
		 * Partie de la gestion des parametres multilingues de la vue 
		 */
		if(Application.instanceOfResourceExists(ResourceModel2.class)){
			ResourceModel2 resource = Application.getResource(ResourceModel2.class);			
			label.setText(resource.getValueOf("valueLabel"));
			validButton.setText(resource.getValueOf("validLabel"));
		}
		
		/*
		 * Partie de l'initialisation des valeurs des champs modifiables de la vue 
		 */
		if(controller!=null){
			Model2 m = (Model2)(((ControllerOfModel2)controller).getModel());
			value.setText(m.getValue());
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
		if(!event.getSource().equals(controller.getModel())){
			String name = this.getClass().getSimpleName();
			System.out.println(name+" a recu le message : "+event.getInformationOnChange().toString());
		}
	}

	@Override
	public void event1Changed(Event1 event) {
		String name = event.getSource().getClass().getSimpleName();
		System.out.println("Instance de "+name+" a comme valeur = "+event.getValue());
	}

	public void validAction(){
		
		//Notification du possible changement de valeur.
		if(controller!=null) controller.notifyEvent(new String(value.getText()));
		
		
	}
	
	public static void main(String[] arg) throws ProgramException{
		Application.addResource(ResourceModel2.class);
		JFrame frame = new JFrame("Vue 1 Model 2");
		/*
		 * Attention ici le controlleur est null parce que l'on veut juste tester la vue.
		 * Mais en temps normale, si vous choissisez d'extansier la vue directement comme 
		 * ci-dessous vous devez vous assurez de passer un controleur correcte avec le 
		 * modele correcte. Nous vous conseillons d'utilisez toujours le controleur pour
		 * avoir le control sur le model et les vues.
		*/ 
		View1Model2 view = new View1Model2(null);
		frame.getContentPane().add((JPanel)view.getContent());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
}
