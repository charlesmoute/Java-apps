package model.coding;

import helper.Util;

import java.util.*;

import model.base.*;

/**
 * 
 * @author Charles
 * Cette classe permet de realiser le codage de Shanon et Fano.
 */
public class ShanonFano {
	
	/**
	 * Table contenant le resultat du codage
	 **/
	ArrayList<Element> source;
	
	/**
	 * <p>Construit une instance de Shanon Fano avec comme source de donnees le parametre.</p>
	 * @param source Source de donnees.
	 * @throws Exception Erreur lancee lorsque le code ne se deroule pas correctement
	 */
	public ShanonFano(ArrayList<Symbol> source) throws Exception {
		this.source = Util.inverse(Util.sort(Util.createElementSource(source)));
	}
	
	/**
	 * <p>Affecte comme source de donnees de Shanon Fano.</p>
	 * @param source Source de donnees.
	 * @throws Exception Erreur lancee lorsque le code ne se deroule pas correctement
	 */
	public void setSource(ArrayList<Symbol> source) throws Exception {
		this.source = Util.inverse(Util.sort(Util.createElementSource(source)));
	}
	
	/**
	 * @return La source de donnees.
	 * @throws Exception Erreur lancee lorsque le code ne se deroule pas correctement
	 */
	public ArrayList<Symbol> getSource() throws Exception  { return Util.getSymbolSource(source);}
	
	
	/**
	 * @return Resultat du codage de ShanonFano. 
	 */
	public ArrayList<Element> getResult() { return this.source; }
	
	
	/**
	 * @return L'ensemble des triplets de la forme (symbole,probabilite,code symbole)
	 * @throws Exception Erreur lancee lorsque le code ne s'est pas correctement execute.
	 */
	public void execAlgorithm() throws Exception {
		if(source!=null){
			for(Element e:source) e.setCode("");
			ArrayList<Double> probabilities = Util.getProbabilities(this.source);
			ShanonFanoAlgorithm(probabilities,0,probabilities.size()-1);
		}
	}
	 
	
	private void ShanonFanoAlgorithm(ArrayList<Double> probabilities,int begin,int end) throws Exception {
		int index =  bestSplit(probabilities,begin,end) ;
		double soe1 = Util.somme(probabilities, begin, index);
		double soe2 = Util.somme(probabilities, index+1, end);
		if(soe1<=soe2){
			setCode(begin, index,"0");
			setCode(index+1,end,"1");
		}else{
			setCode(begin, index,"1");
			setCode(index+1,end,"0");
		}
		
		if(begin!=index) ShanonFanoAlgorithm(probabilities,begin,index);		
		if((index+1)!=end) ShanonFanoAlgorithm(probabilities,index+1,end);
		
	}
	
	private int bestSplit(ArrayList<Double> probabilities,int begin,int end) throws Exception {
		int i,index = -1;		
		double soe=0,soe1,soe2;		
		for(i=begin;i<(end-1);i++){
			soe1 = Util.somme(probabilities, begin,i);
			soe2 = Util.somme(probabilities, i+1,end);
			if(index==-1) {soe = Math.abs(soe1-soe2); index=i; }
			else if(Math.abs(soe1-soe2)< soe) { index = i; soe = Math.abs(soe1-soe2); }
		}
		if(index==-1) index = begin;
		return index;
	}
	
	private void setCode(int begin, int end, String code){		
		for(int i=begin;i<=end;i++){
			Element e = source.get(i);
			e.setCode(e.getCode()+code);
		}		
	}
	
	public String toString() {
		String value = null;
		if(source!=null){
			value="";
			for(int i=0;i<source.size();i++) 
				value+=source.get(i).toString()+"\n";
		}
		return value;
	}
	
	public static void main(String[] arg) throws Exception {
		ArrayList<String> alphabet = null;
		ArrayList<Double> probabilities = null;
		probabilities = Util.roundList(probabilities, 2);
		
		alphabet = new ArrayList<String>();
		alphabet.add("1");
		alphabet.add("2");		
		alphabet.add("3");
		alphabet.add("4");
		alphabet.add("5");
		alphabet.add("6");
		alphabet.add("7");
		
		probabilities = new ArrayList<Double>();
		probabilities.add(new Double(0.25));
		probabilities.add(new Double(0.20));
		probabilities.add(new Double(0.15));
		probabilities.add(new Double(0.15));
		probabilities.add(new Double(0.10));
		probabilities.add(new Double(0.10));
		probabilities.add(new Double(0.05));
		
		
		ArrayList<Symbol> source = Util.createSource(alphabet, probabilities);
		
		ShanonFano shanonFano = new ShanonFano(source);
		shanonFano.execAlgorithm();
		System.out.println(shanonFano);
		
	}
}
