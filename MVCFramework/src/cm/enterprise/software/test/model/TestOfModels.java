package cm.enterprise.software.test.model;

import cm.enterprise.software.component.model.base.Model;
import cm.enterprise.software.exception.ProgramException;
import cm.enterprise.software.test.model.model1.Model1;

/**
 * Class de test des modeles et du super modele.
 * @author Charles Moute
 * @version 1.0.1, 17/07/2010
 */
public class TestOfModels {

	private SuperModelTest model = null ;
	
	/**
	 * Instancie un instance de la classe de test.
	 * @throws ProgramException 
	 */
	public TestOfModels() throws ProgramException{
		
		//Creation du Super Model
		model = new SuperModelTest("SuperModel");
	}
	
	/**
	 * Fonction de test du super modele
	 * @throws ProgramException  
	 */
	public void test() throws ProgramException {
	
		//Parametres propres au super modele.
		System.out.println(" Test des concepts de Model et SuperModel");
		
		System.out.println("----------------------------------------------------------------------------------");
		System.out.println(" Valeur du model1 actuellement connu par le super modele");
		System.out.println(" Attribut = "+model.getAttributeOfModel1()+"; Valeur = "+model.getValueOfModel1());
		System.out.println(" Affectation des valeurs newAttribute et 3 au modele 1 du super modele");
		model.setValueToModel1("newAttribute", 3);
		System.out.println(" Valeur du model1 actuellement connu par le super modele");
		System.out.println(" Attribut = "+model.getAttributeOfModel1()+"; Valeur = "+model.getValueOfModel1());
		System.out.println("----------------------------------------------------------------------------------");
		System.out.println(" Valeur du model2 actuellement connu par le super modele");
		System.out.println(" Valeur = "+model.getValueOfModel2());
		System.out.println(" Affectation de la  valeur newValue au modele 2 du super modele");
		model.setValueToModel2("newValue");
		System.out.println(" Valeur du model2 actuellement connu par le super modele");
		System.out.println(" Valeur = "+model.getValueOfModel2());
		System.out.println("----------------------------------------------------------------------------------");
		System.out.println(" Valeur de l'attribut du super modele actuellement connu = "+model.getValueOfAtttrribute());
		System.out.println(" Affectation de la valeur TestSuperModel a l'attribut du super modele");
		model.setValueToAttribute("TestSuperModel");
		System.out.println(" Valeur de l'attribut du super modele = "+model.getValueOfAtttrribute());
		//Test de getSubModelCount(),getSubModelCount(Class) et invoke(...)
		System.out.println("----------------------------------------------------------------------------------");
		System.out.println(" Nombre de sous model du super model = "+model.getSubModelCount());
		for(Class<? extends Model> type: model.getClassOfModelList())
			System.out.println(" Nombre de sous model de classe "+type.getSimpleName()+" = "+model.getSubModelCount(type));
		System.out.println("----------------------------------------------------------------------------------");
		System.out.println(" Invocation de la method setAttributes de l'instance 1 de la classe Model1.class avec les valeurs  d'attribut invocation et de valeur 1 ");
		model.invokeMethodOfSubModel(Model1.class,"setAttributes", "invocation",1);
		String attribut = (String)model.invokeMethodOfSubModel(Model1.class,"getAttribut");
		Integer value = (Integer) model.invokeMethodOfSubModel(Model1.class,"getValue");
		System.out.println(" Valeurs de l'instance 1 du Model1: Attribut = "+attribut+"; Valeur = "+value);
	}

	public static void main(String[] args) throws ProgramException {
		
		TestOfModels soft = new TestOfModels();
		soft.test();
	}

}
