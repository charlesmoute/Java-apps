package cm.enterprise.software.test.view.model1;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

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
 * Exemple 2 de vue pour le modele Model1. Cette vue a pour vocation d'afficher
 * les valeurs du modele 1, ceci chaque fois que ce dernier change.
 * @author Charles Moute
 * @version 1.0.1, 17/07/2010
 */
public class View2Model1 extends ViewModel1 {

	private JLabel label,valLabel,attribute ;
	private JSpinner value ;
	
	private JPanel contentPanel ;
	
	public View2Model1(ControllerOfModel1 controller) {
		super(controller);
		contentPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		label = new JLabel();
		contentPanel.add(label);
		attribute = new JLabel("");
		attribute.setPreferredSize(new Dimension(100,25));
		contentPanel.add(attribute);
		valLabel = new JLabel("");
		contentPanel.add(valLabel);
		value = new JSpinner(new SpinnerNumberModel());
		value.setPreferredSize(new Dimension(50,23));
		value.setEnabled(false);
		contentPanel.add(value);		
	}

	@Override
	public void disable() {
		label.setEnabled(false);
		attribute.setEnabled(false);
		valLabel.setEnabled(false);
		//value.setEnabled(false);
	}

	@Override
	public void enable() {
		label.setEnabled(true);
		attribute.setEnabled(true);
		valLabel.setEnabled(true);
		//value.setEnabled(true);
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
		
		ResourceModel1 resource = null;
		if(Application.instanceOfResourceExists(ResourceModel1.class)) resource = Application.getResource(ResourceModel1.class);
		else if(Application.instanceOfModuleExists(TModule.class)){
			resource = Application.getModule(TModule.class).getResource(ResourceModel1.class);
		}
				
		if(resource!=null){
			label.setText(resource.getValueOf("attributeLabel")+": ");
			valLabel.setText(resource.getValueOf("valueLabel"));
		}
		
		/*
		 * Partie de l'initialisation des valeurs des champs modifiables 
		 */
		if(controller!=null){
			Model1 m = (Model1) (((ControllerOfModel1)controller).getModel());
			attribute.setText(""+m.getAttribut());
			value.setValue(m.getValue());
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
			String name = event.getSource().getClass().getSimpleName();
			System.out.println("Vue 2 du model1 a recu"+" a recu le message : "+event.getInformationOnChange().toString()+" en provenance de "+name);
		}
	}

	@Override
	public void event2Changed(Event2 evt) {
		String name = evt.getSource().getClass().getSimpleName();
		System.out.println("Vue 2 du model1 dit : Instance de "+name+" a comme valeur = ("+evt.getAttribute()+","+evt.getValue()+")");
		attribute.setText(evt.getAttribute());
		value.setValue(evt.getValue());
	}
	
	public static void main(String[] arg) throws ProgramException{
		Application.addResource(ResourceModel1.class);
		JFrame frame = new JFrame("Vue 2 Model 1");
		/*
		 * Attention ici le controlleur est null parce que l'on veut juste tester la vue.
		 * Mais en temps normale, si vous choissisez d'extansier la vue directement comme 
		 * ci-dessous vous devez vous assurez de passer un controleur correcte avec le 
		 * modele correcte. Nous vous conseillons d'utilisez toujours le controleur pour
		 * avoir le control sur le model et les vues.
		*/ 
		View2Model1 view = new View2Model1(null);
		frame.getContentPane().add((JPanel)view.getContent());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
}
