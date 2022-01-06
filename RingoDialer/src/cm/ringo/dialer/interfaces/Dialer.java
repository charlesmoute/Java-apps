package cm.ringo.dialer.interfaces;

import java.util.Locale;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;

import com.l2fprod.gui.plaf.skin.Skin;
import com.l2fprod.gui.plaf.skin.SkinLookAndFeel;

import cm.enterprise.software.exception.ProgramException;
import cm.enterprise.software.helper.Application;
import cm.enterprise.software.interfaces.Module;
import cm.ringo.dialer.component.controller.DialerController;
import cm.ringo.dialer.component.model.base.DialerModel;
import cm.ringo.dialer.component.view.DefaultView;
import cm.ringo.dialer.helper.DialerConstants;
import cm.ringo.dialer.helper.DialerMultilingualResource;

/**
 * Represente le Dialer dans sa totalite. Vous n'avez besoin que de cette classe
 * pour manipuler le Dialer en entier. Il encapsule toute la complexite de la manipulation
 * du Dialer.
 * 
 * @author Charles Moute
 * @version 1.0.1, 4/8/2010
 */

public class Dialer extends Module {

	public Dialer(){
		
		//Definition de quelques parametres du module
		try {
			
			this.definesParameter("copyright","version");
			
			//Affectation des valeurs immuables au parametre du module
			constantInitialization();
			
			//Definition des langues prises en compte par le dialer			
			this.addLanguage(Locale.FRENCH.getLanguage(),Locale.US.getLanguage());
			
			//Definition des ressources du dialer
			this.addResource(DialerConstants.class);//Constantes du dialer
			this.addResource(DialerMultilingualResource.class);
			
			//Definition des modules dont le module est dependant
			Application.addModule(Connection.class);
			
			//Definition du controleur du dialer
			this.addController(DialerController.class, new DialerModel());
			
			//Definition de la langue par defaut de la ressource.
			this.setDefaultLanguage(Locale.ENGLISH.getLanguage());
			
		} catch (ProgramException e) {
			e.printStackTrace();
		}
	}

	private void constantInitialization(){
		
		//Affectation des parametres du module 
		this.setModuleAuthor("Charles Moute");
		this.setModuleName("RingoDialer");
		this.setAbout("Ringo Dialer est le client PPPOE de Ringo SA.");
		
		//Affectation des valeurs au parametre definies
		this.setValueTo("copyright", "Tout droit reservé Ringo S.A");
		this.setValueTo("version", "1.0.1");
	}
	

	@Override
	public void initModule() {
		
		constantInitialization();
		
		//Initialisation des modules dont il depend
		Application.getModule(Connection.class).initModule();
		
		//Definition de la langue courante a la langue par defaut.
		this.setCurrentLanguage(this.getDefaultLanguage());
		
	}
	
	@Override
	public void setCurrentLanguage (String language){
		try {
			super.setCurrentLanguage(language);
			Application.getModule(Connection.class).setCurrentLanguage(language);
			this.getController(DialerController.class).notifyEvent(language);
			
		} catch (ProgramException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Permet d'obtenir les droits du dialer
	 * @return Les droits du dialer
	 */
	public String getCopyright() { return this.getValueOf("copyright"); }
		
	/**
	 * Permet d'initialiser la vue par defaut. Cette operation permettra
	 * l'appel aux differentes fonctions XXXDefaultView() et defaultViewXXX()
	 */
	public void installDefaultView(){
		
		try {
			DialerController controller = Dialer.this.getController(DialerController.class);
			controller.installView(DefaultView.class, controller);
		} catch (ProgramException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Permet la suppression de la vue par defaut. Cette operation invalidera
	 * l'appel aux differentes fonctions XXXDefaultView() et defaultViewXXX()
	 */
	public void uninstallDefaultView(){
		
		this.getController(DialerController.class).hideView(DefaultView.class);
		try {

			Dialer.this.getController(DialerController.class).uninstallView(DefaultView.class);
			
			JFrame.setDefaultLookAndFeelDecorated(false);
			JDialog.setDefaultLookAndFeelDecorated(false);
			Skin skin = SkinLookAndFeel.loadDefaultThemePack();
			SkinLookAndFeel.setSkin(skin);
			UIManager.setLookAndFeel(new SkinLookAndFeel());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Permet d'obtenir l'instance de la vue par defaut du client PPPOE.
	 * Avant d'appel cette fonction assurez vous d'avoir fait, au moins une fois 
	 * appel a initDefaultView et jamais a uninstallDefaultView() apres le precedent 
	 * appel.
	 * @return La vue par defaut du dialer.
	 * @see #installDefaultView()
	 * @see #uninstallDefaultView()
	 */
	public JFrame getDefaultView(){
		DialerController controller = this.getController(DialerController.class);
		return (JFrame)controller.getContentOf(DefaultView.class);
	}
	
	/**
	 * Permet de rendre visible la vue par defaut.
	 * Avant d'appel cette fonction assurez vous d'avoir fait, au moins une fois 
	 * appel a initDefaultView et jamais a uninstallDefaultView() apres le precedent 
	 * appel.
	 * @see #installDefaultView()
	 * @see #uninstallDefaultView()
	 */
	public void showDefaultView(){
		DialerController controller = this.getController(DialerController.class);
		controller.showView(DefaultView.class);
	}
	
	/**
	 * Permet de cacher la vue par defaut.
	 * Avant d'appel cette fonction assurez vous d'avoir fait, au moins une fois 
	 * appel a initDefaultView et jamais a uninstallDefaultView() apres le precedent 
	 * appel.
	 * @see #installDefaultView()
	 * @see #uninstallDefaultView()
	 */
	public void hideDefaultView(){
		DialerController controller = this.getController(DialerController.class);
		controller.hideView(DefaultView.class);
	}
	
	/**
	 * Permet les interactions avec la vue par defaut.
	 * Avant d'appel cette fonction assurez vous d'avoir fait, au moins une fois 
	 * appel a initDefaultView et jamais a uninstallDefaultView() apres le precedent 
	 * appel.
	 * @see #installDefaultView()
	 * @see #uninstallDefaultView()
	 */
	public void enableDefaultView(){
		DialerController controller = this.getController(DialerController.class);
		controller.enableView(DefaultView.class);
	}
	
	/**
	 * Interdit les interactions avec la vue par defaut.
	 * Avant d'appel cette fonction assurez vous d'avoir fait, au moins une fois 
	 * appel a initDefaultView et jamais a uninstallDefaultView() apres le precedent 
	 * appel.
	 * @see #installDefaultView()
	 * @see #uninstallDefaultView()
	 */
	public void disableDefaultView(){
		DialerController controller = this.getController(DialerController.class);
		controller.disableView(DefaultView.class);
	}
	
	/**
	 * Permet de savoir si la vue par defaut est visible.
	 * Avant d'appel cette fonction assurez vous d'avoir fait, au moins une fois 
	 * appel a initDefaultView et jamais a uninstallDefaultView() apres le precedent 
	 * appel.
	 * @return true si la vue par defaut est visible, false dans le cas contraire.
	 * @see #installDefaultView()
	 * @see #uninstallDefaultView()
	 */
	public boolean defaultViewIsVisible(){
		DialerController controller = this.getController(DialerController.class);		
		return controller.getViewOfClass(DefaultView.class).isVisible();
	}
	
	/**
	 * Permet de savoir si les interactions avec la vue par defaut sont permises.
	 * Avant d'appel cette fonction assurez vous d'avoir fait, au moins une fois 
	 * appel a initDefaultView et jamais a uninstallDefaultView() apres le precedent 
	 * appel.
	 * @return true si les interactions avec la vue par defaut sont permises, et false dans le cas contraire
	 * @see #installDefaultView()
	 * @see #uninstallDefaultView()
	 */
	public boolean defaultViewIsEnable(){
		DialerController controller = this.getController(DialerController.class);		
		return controller.getViewOfClass(DefaultView.class).isEnable();
	}

}
