package helper;

import java.util.*;
import java.text.*;
import javax.swing.*;
import javax.swing.table.*;

import model.base.*;


/**
 * 
 * @author Charles
 * <p> La classe Util est une classe utilitaire. Elle contient toutes ou la plupart
 * des fonctions utiles dans les différents modeles.</p>
 */


public class Util {
	
	/**
	 * <p> Arrondi number sous l'ordre order. Si order est negatif le nombre est 
	 * retournee sans etre arrondi. Si par contre l'ordre est 0 on utilse Math.round pour
	 * arrondir le nombre.</p>
	 * @param number Nombre a arrondir.
	 * @param order  Nombre representant l'ordre de l'arrondi.
	 * @return L'arrondi d'ordre 2 du parametre.
	 */
	public static final double round(double number,int order) {	
		double rounded ;		
		
		if(order<0) return number ;
		if(order==0) Math.round(number);
		
		String decimalFormat ="0.";			
		for(int i=1;i<=order;i++) decimalFormat+="0";		
		DecimalFormat format  = new DecimalFormat(decimalFormat);
		rounded = Double.parseDouble(format.format(number).replace(',', '.'));
		return rounded ;
	}
	
	
	/**
	 * <p>Arrondi les elements de list a l'ordre order.</p>
	 * @param list Liste d'elements a arrondir.
	 * @param order Ordre d'arrondi des elements.
	 * @return Liste d'elements de list arrondis.
	 */
	public static final ArrayList<Double>roundList(ArrayList<Double> list, int order) {
		ArrayList<Double> table = null;
		if(list!=null){
			table = new ArrayList<Double>(list.size());
			for(int i=0; i<list.size();i++){
				table.add(new Double(Util.round(list.get(i).doubleValue(), order)));
			}
		}
		return table;
	}
	
	/**
	 * @param text  Texte duquel on doit determiner l'alphabet.
	 * @return L'alphabet du texte passe en parametre. 
	 */
	public static final ArrayList<String> createAlphabet(String text){
		ArrayList<String> alphabet = null;
		if(text!=null){
			alphabet = new ArrayList<String>();
			for(int i=0;i<text.length();i++){
				String symbol = ""+text.charAt(i);
				if(!alphabet.contains(symbol))
					alphabet.add(new String(symbol));				
			}
		}
		return alphabet; 
	}
	
	/**
	 * @param text Texte duquel on doit obtenir le nombre total de caractere.
	 * @return Le nombre de caractere contenu dans le message.
	 */
	public static final int getSymbolCount(String text) {
		if(text==null) return 0 ;
		return text.length(); 
	}
	
	/**
	 * @param text Texte duquel on doit determiner le nombre d'occurence de symbol.
	 * @param symbol Chaine ou caractere dont on veut determiner le nombre d'occurence.
	 * @return Le nombre d'occurence de symbol dans text.
	 */
	public static final int getSymbolCount(String text,String symbol) {
		if(text==null || symbol==null) return 0;
		int count = 0,i;
		i =  text.indexOf(symbol,0);
		while(i!=-1){
			count++;			
			i= text.indexOf(symbol,i+1);
		}
		return count; 
	}
	
	
	/**	 
	 * @param text Texte utilise pour determiner la probabilite d'occurence de symbol.
	 * @param symbol Chaine dont on veut determiner la probabilite.
	 * @return La probabilite d'occurence de symbol dans text.
	 */
	public static final double getOccurenceProbability(String text,String symbol) {
		double probability = 0 ;
		if(text!=null && symbol!=null){
			double total = Util.getSymbolCount(text);
			double count = Util.getSymbolCount(text, symbol);
			probability = count/total ;
		}
		return probability;
	}
	
	/**
	 * @param text  Texte duquel les probabilites d'occurences des symboles de l'alaphabet seront obtenues.
	 * @param alphabet Ensemble de symboles dont on veut les probabilites d'occurence.
	 * @return L'ensemble des probabilites d'occurence des symboles de l'alphabet.
	 */
	public static final ArrayList<Double> getOccurenceProbabilities(String text,ArrayList<String> alphabet){
		ArrayList<Double> result = null;
		if(text!=null && alphabet!=null){
			result = new ArrayList<Double>(alphabet.size());
			for(int i=0;i<alphabet.size();i++){
				result.add(new Double(Util.getOccurenceProbability(text, alphabet.get(i))));
			}
		}
		return result;
	}
	
	/**
	 * @param alphabet Ensemble des symboles.
	 * @param probabilities Ensemble des probabilites d'occurence de chaque symbole.
	 * @return La liste des symboles associes de leurs probabilites d'occurence.
	 * @throws Exception Erreur lancee lorsque le code ne se deroule pas correctement
	 */
	public static final ArrayList<Symbol> createSource(ArrayList<String> alphabet,ArrayList<Double> probabilities) throws Exception{
		ArrayList<Symbol> source = null;
		if(alphabet!=null && probabilities!=null && alphabet.size()==probabilities.size()) {
			source = new ArrayList<Symbol>(alphabet.size());
			for(int i=0;i<alphabet.size();i++){
				source.add(new Symbol(alphabet.get(i),probabilities.get(i).doubleValue()));
			}
		}
		return source;
	}
	
	
	/**
	 * @param source Ensemble de paires (symbole,probabilite).
	 * @param codes Ensemble des codes de chaque paire de source.
	 * @return Ensemble de triplet (symbole,probabilite,code).
	 * @throws Exception Erreur lancee lorsque le code ne se deroule pas correctement
	 */
	public static final ArrayList<Element> createElementSource(ArrayList<Symbol> source) throws Exception{
		ArrayList<Element> result = null;
		if(source!=null) {
			result = new ArrayList<Element>(source.size());
			for(int i=0;i<source.size();i++){
				result.add(new Element(source.get(i).getSymbol(),source.get(i).getProbability()));
			}
		}
		return result;
	}
	
	/**
	 * @param source Ensemble de paires (symbole,probabilite).
	 * @param codes Ensemble des codes de chaque paire de source.
	 * @return Ensemble de triplet (symbole,probabilite,code).
	 * @throws Exception Erreur lancee lorsque le code ne se deroule pas correctement
	 */
	public static final ArrayList<Element> createElementSource(ArrayList<Symbol> source,ArrayList<String> codes) throws Exception{
		ArrayList<Element> result = null;
		if(source!=null && codes!=null && source.size()==codes.size()) {
			result = new ArrayList<Element>(source.size());
			for(int i=0;i<source.size();i++){
				result.add(new Element(source.get(i).getSymbol(),source.get(i).getProbability(),codes.get(i)));
			}
		}
		return result;
	}
	
	/**
	 * @param alphabet Ensemble des symboles.
	 * @param probabilities Ensemble des probabilites d'occurence de chaque symbole.
	 * @param codes listes des codes associes de l'alphabet.
	 * @return La liste des symboles associes de leurs probabilites d'occurence et de leurs codes.
	 * @throws Exception Erreur lancee lorsque le code ne se deroule pas correctement
	 */
	public static final ArrayList<Element> createElementSource(ArrayList<String> alphabet,ArrayList<Double> probabilities,ArrayList<String> codes) throws Exception{
		ArrayList<Element> source = null;
		if(alphabet!=null && probabilities!=null&& alphabet.size()==probabilities.size()&& alphabet.size()==codes.size()) {
			source = new ArrayList<Element>(alphabet.size());
			for(int i=0;i<alphabet.size();i++){
				source.add(new Element(alphabet.get(i),probabilities.get(i).doubleValue(),codes.get(i)));
			}
		}
		return source;
	}
	
	/**
	 * @param source Ensemble de paire (symbole, probabilite d'occurence).
	 * @return La liste des symboles de la source. 
	 */	
	public static final ArrayList<String> getSourceAlphabet(ArrayList<Symbol> source) {	
		ArrayList<String> result = null;
		if(source!=null){
			result = new ArrayList<String>(source.size());
			for(int i=0;i<source.size();i++){
				result.add(new String(source.get(i).getSymbol()));
			}
		}
		return result;
	}
	
	/**
	 * @param source Ensemble de paire (symbole,probabilite d'occurence).
	 * @return La liste des probabilites d'occurence de la source.
	 */
	public static final ArrayList<Double> getSourceProbabilities(ArrayList<Symbol> source) {	
		ArrayList<Double> result = null;
		if(source!=null){
			result = new ArrayList<Double>(source.size());
			for(int i=0;i<source.size();i++){
				result.add(new Double(source.get(i).getProbability()));
			}
		}
		return result;
	}
	
	/**
	 * @param source Ensemble de triplet (symbole, probabilite d'occurence,code symbole).
	 * @return La liste des symboles de la source. 
	 */	
	public static final ArrayList<String> getAlphabet(ArrayList<Element> source) {	
		ArrayList<String> result = null;
		if(source!=null){
			result = new ArrayList<String>(source.size());
			for(int i=0;i<source.size();i++){
				result.add(new String(source.get(i).getSymbol()));
			}
		}
		return result;
	}
	
	/**
	 * @param source Ensemble de triplet (symbole,probabilite d'occurence,code symmbole).
	 * @return La liste des probabilites d'occurence de la source.
	 */
	public static final ArrayList<Double> getProbabilities(ArrayList<Element> source) {	
		ArrayList<Double> result = null;
		if(source!=null){
			result = new ArrayList<Double>(source.size());
			for(int i=0;i<source.size();i++){
				result.add(new Double(source.get(i).getProbability()));
			}
		}
		return result;
	}
	/**
	 * @param source Ensemble de triplet (symbole,probabilite d'occurence,codes).
	 * @return Ensembles des paires (symbole,probabilite d'occurence).
	 * @throws Exception Erreur lancee lorsque le code ne se deroule pas correctement
	 */
	public static final ArrayList<Symbol> getSymbolSource(ArrayList<Element> source) throws Exception {	
		ArrayList<Symbol> result = null;
		if(source!=null){
			result = new ArrayList<Symbol>(source.size());
			for(int i=0;i<source.size();i++){
				result.add(new Symbol(source.get(i).getSymbol(),source.get(i).getProbability()));
			}
		}
		return result;
	}
	
	/**
	 * <p>Cree la source a partir d'un table</p>
	 * @param table Table a partir de laquelle on cree la source.
	 * @return La source creee a partir du table.
	 * @throws Exception Erreur lancee lorsque le code ne se deroule pas correctement
	 */
	public static final ArrayList<Symbol> createSource(JTable table) throws Exception {
		ArrayList<Symbol> source = null;
		if(table!=null && table.getColumnCount()==2 ){
			source = new ArrayList<Symbol>(table.getRowCount());
			for(int i=0;i<table.getRowCount();i++){
				try{
					String value = (table.getValueAt(i,0).toString()==null)? "":table.getValueAt(i,0).toString();
					if(value.compareToIgnoreCase(Constant.SPACE)==0) value = " ";
					source.add(new Symbol(value,Double.parseDouble(table.getValueAt(i,1).toString())));
				}catch(NumberFormatException e){
					JOptionPane.showMessageDialog(null, "Une valeur de probabilité n'est pas uun double", "Attention!!", 
							JOptionPane.WARNING_MESSAGE);
					return null;
				}
			}
		}
		return source;
	}
	
	/**
	 * @param source Source de donnees.
	 * @return Une table initialise avec le contenu de la source.
	 */
	public static final JTable initializeTable(ArrayList<Symbol> source) {
		JTable table = null;
		if(source!=null){
			table = new JTable();		
			
			DefaultTableModel model = new DefaultTableModel();
			String[] colonnes = {"Symbole","Probabilité"};
			model.setColumnIdentifiers(colonnes);			
			for(int i = 0; i<source.size();i++){
				String value = (source.get(i).getSymbol().compareToIgnoreCase(" ")==0)? Constant.SPACE:source.get(i).getSymbol();
				if(value==null) value = "";
				model.addRow(new Object[]{value,source.get(i).getProbability()});
			}
			table.setModel(model);
		}
		return table;
	}
	
	/**
	 * @param source Source a partir de laquelle l'entropy sera calculee.
	 * @return L'entropie de la source.
	 */
	public static final double computeEntropy(ArrayList<Symbol> source) {
		double entropy = 0 ;
		ArrayList<Double> probabilities = Util.getSourceProbabilities(source);
		if(probabilities!=null){
			for(int i=0;i<probabilities.size();i++) entropy+=probabilities.get(i)* (Math.log(probabilities.get(i))/Math.log(2)) ;
			entropy*=(-1);
		}
		return entropy;
	}
	
	/**
	 * @param source Source a partir de laquelle la longueur moyenne sera calculee.
	 * @return La longueur moyenne de la source.
	 */
	public static final double computeMiddleLength(ArrayList<Element> source){
		double length = 0 ;
		if(source!=null){
			for(int i=0;i<source.size();i++){
				length+= source.get(i).getProbability()*source.get(i).getCode().length();
			}
		}		
		return length;
	}
	
	
	/**
	 * <p> Calculer l'efficacite d'un codage.</p>
	 * @param entropy Entropie qui sera utilisee pour calculer l'efficacite.
	 * @param middleLength Longueur moyenne utilisee pour calculer l'efficacite.
	 * @param d Nombre de caractere du codage.
	 * @return Efficacite d'un codage.
	 */
	public static final double computeCodingEfficiency(double entropy,double middleLength,int d ){
		 return entropy/(middleLength*(Math.log(d)/Math.log(2)));
	}
	
	/**
	 * <p></p>
	 * @param efficiency Efficacite
	 * @return Valeur de la redondance.
	 */
	public static final double computeRedundancy(double efficiency){
		return 1- efficiency;
	}
	
	/**
	 * <p>Evalue l'efficacite d'une compressiion.</p>
	 * @param compressedSize Taille du compresse.
	 * @param originalSize Taille de l'original.
	 * @return Efficacite de la compression.
	 */
	public static final double computeCompressionEfficiency(double compressedSize,double originalSize){
		return compressedSize/originalSize;
	}
	
	/**
	 * <p>Evalue le facteur de compression.</p>
	 * @param compressionEfficiency Efficacite de la compression.
	 * @return Facteur de la compression.
	 */
	public static final double computeCompressionFactor(double compressionEfficiency){
		return 1/compressionEfficiency;
	}
	
	/**
	 * <p>Dit si une liste de reel est une liste triee dans l'ordre croissant.</p>
	 * @param source Liste de reel.
	 * @return True si la liste de reel est triee dans l'ordre croissant.
	 */
	public static final boolean isIncreasingList(ArrayList<Double>source) {
		boolean isIncreasing = true ;
		if(source!=null){
			for(int i=0;i<(source.size()-1);i++){
				if(source.get(i)>source.get(i+1)){
					isIncreasing = false ;
					break;
				}
			}
		}
		return isIncreasing;
	}
	
	/**
	 * <p>Dit si une source de donnees est une source triee dans l'ordre croissant des probabilites.</p>
	 * @param source Source de donnees.
	 * @return True si la source est triee dans l'ordre croissant.
	 */
	public static final boolean isIncreasingSource(ArrayList<Symbol> source) {
		if(source!=null) return Util.isIncreasingList(Util.getSourceProbabilities(source));		
		return true;
	}
	/**
	 * <p>Dit si une liste de reel est une liste triee dans l'ordre decroissant.</p>
	 * @param source Liste de reel.
	 * @return True si la liste de reel est triee dans l'ordre decroissant.
	 */
	public static final boolean isDecreasingList(ArrayList<Double>source) {
		boolean isDecreasing = true ;
		if(source!=null){
			for(int i=0;i<(source.size()-1);i++){
				if(source.get(i)<source.get(i+1)){
					isDecreasing = false ;
					break;
				}
			}
		}
		return isDecreasing;
	}
	
	/**
	 * <p>Dit si une source de donnees est une source triee dans l'ordre decroissant des probabilites.</p>
	 * @param source Source de donnees.
	 * @return True si la source est triee dans l'ordre decroissant.
	 */
	public static final boolean isDecreasingSource(ArrayList<Symbol> source) {
		if(source!=null) return Util.isDecreasingList(Util.getSourceProbabilities(source));		
		return true;
	}
	
	/**
	 * <p>Dit si la source est une liste croissante.</p>
	 * @param source Source d'element.
	 * @return True si la liste d'element est triee de facon croissante.
	 * @throws Exception Erreur lancee lorsque le code ne se deroule pas correctement
	 */
	public static final boolean isIncreasing(ArrayList<Element> source)throws Exception {		
		if(source!=null) return Util.isIncreasingSource(Util.getSymbolSource(source));
		return true;
	}
	
	/**
	 * <p>Dit si la source est une liste decroissante.</p>
	 * @param source Source d'element.
	 * @return True si la liste d'element est triee de facon decroissante.
	 * @throws Exception Erreur lancee lorsque le code ne se deroule pas correctement
	 */
	public static final boolean isDecreasing(ArrayList<Element> source) throws Exception {		
		if(source!=null) return Util.isDecreasingSource(Util.getSymbolSource(source));
		return true;
	}
	
	/**
	 * @param text Texte a verifier la structure.
	 * @return true si text est sous la forme d'un fax.
	 */
	public static final boolean isFaxText(String text) {
		ArrayList<String> alphabet = Util.createAlphabet(text);
		if(alphabet==null) return false;		
		return (alphabet.size()==2 && ( (alphabet.get(0).compareToIgnoreCase("B")==0 && alphabet.get(1).compareToIgnoreCase("N")==0) ||(alphabet.get(0).compareToIgnoreCase("N")==0 && alphabet.get(1).compareToIgnoreCase("B")==0) ));
	}
	
	
	
	/**	 
	 * @param table Source de donnees
	 * @param begin Index de debut pour la somme.
	 * @param end   Index de fin pour la somme.
	 * @return La somme des donnees des elements allant de begin a end.
	 */
	public static final double somme(ArrayList<Double> table,int begin,int end) {		
		double soe = 0 ;
		if(table==null) return soe;
		if(begin<0 || end>=table.size()) return soe;
		for(int i=begin;i<=end;i++) soe+=table.get(i).doubleValue();
		return soe;
	}
	
	
	/**
	 * <p>Tri par ordre croissantde la source.</p>
	 * @param source Source de donnees a trier.
	 * @return Source Triee.
	 */
	public static final ArrayList<Double> sortList(ArrayList<Double> source){
		ArrayList<Double> table = null;
		if(source!=null){
			double aux;
			int i,k;
			table = new ArrayList<Double>();
			for(i=0;i<source.size();i++) table.add(new Double(source.get(i).doubleValue()));
			
			int n = table.size()-1 ;			
			for(k=0;k<n;k++){
				for(i=0;i<n-k;i++){
					if(table.get(i)>table.get(i+1)){
						aux = table.get(i);
						table.set(i, table.get(i+1));
						table.set(i+1, aux);
					}
				}
			}
		}
		return table;	
	}	
	
	/**
	 * <p>Inverse la source de donnees.</p>
	 * @param source Source de donnees a inverser.
	 * @return Source inversee.
	 */
	public static final ArrayList<Double> inverseList(ArrayList<Double> source){
		ArrayList<Double> table = null ;
		if(source!=null) {
			table = new ArrayList<Double>();
			for(int i=(source.size()-1);i>=0;i--) table.add(new Double(source.get(i)));
		}
		return table;
	}
	
	/**
	 * <p>Tri par ordre croissantde la source.</p>
	 * @param source Source de donnees a trier.
	 * @return Source triee.
	 * @throws Exception Erreur lancee lorsque le code ne se deroule pas correctement
	 */
	public static final ArrayList<Symbol> sortSource(ArrayList<Symbol> source) throws Exception{
		ArrayList<Symbol> table = null;
		if(source!=null){
			Symbol aux;
			int i,k;
			table = new ArrayList<Symbol>();
			for(i=0;i<source.size();i++) table.add(new Symbol(source.get(i).getSymbol(),source.get(i).getProbability()));
			
			int n = table.size()-1 ;			
			for(k=0;k<n;k++){
				for(i=0;i<n-k;i++){
					if(table.get(i).getProbability()>table.get(i+1).getProbability()){
						aux = table.get(i);
						table.set(i, table.get(i+1));
						table.set(i+1, aux);
					}
				}
			}
		}
		return table;	
	}
	
	
	/**
	 * <p>Inverse la source de donnees.</p>
	 * @param source Source de donnees a inverser.
	 * @return Source inversee.
	 * @throws Exception Erreur lancee lorsque le code ne se deroule pas correctement
	 */
	public static final ArrayList<Symbol> inverseSource(ArrayList<Symbol> source) throws Exception{
		ArrayList<Symbol> table = null ;
		if(source!=null) {
			table = new ArrayList<Symbol>();
			for(int i=(source.size()-1);i>=0;i--) table.add(new Symbol(source.get(i).getSymbol(),source.get(i).getProbability()));
		}
		return table;
	}
	
	
	/**
	 * <p>Tri par ordre croissantde la source.</p>
	 * @param source Source de donnees a trier.
	 * @return Source Triee.
	 * @throws Exception Erreur lancee lorsque le code ne se deroule pas correctement
	 */
	public static final ArrayList<Element> sort(ArrayList<Element> source) throws Exception {
		ArrayList<Element> table = null;
		if(source!=null){
			Element aux;
			int i,k;
			table = new ArrayList<Element>();
			for(i=0;i<source.size();i++) table.add(new Element(source.get(i).getSymbol(),source.get(i).getProbability(),source.get(i).getCode()));
			
			int n = table.size()-1 ;			
			for(k=0;k<n;k++){
				for(i=0;i<n-k;i++){
					if(table.get(i).getProbability()>=table.get(i+1).getProbability()){
						aux = table.get(i);
						table.set(i, table.get(i+1));
						table.set(i+1, aux);
					}
				}
			}
		}
		return table;	
	}
	
	
	/**
	 * <p>Inverse la source de donnees.</p>
	 * @param source Source de donnees a inverser.
	 * @return Source inversee.
	 * @throws Exception Erreur lancee lorsque le code ne se deroule pas correctement
	 */
	public static final ArrayList<Element> inverse(ArrayList<Element> source) throws Exception {
		ArrayList<Element> table = null ;
		if(source!=null) {
			table = new ArrayList<Element>();
			for(int i=(source.size()-1);i>=0;i--) table.add(new Element(source.get(i).getSymbol(),source.get(i).getProbability(),source.get(i).getCode()));
		}
		return table;
	}
	
	
	
	public static void main(String[] args) throws Exception {	
		String text = "Reseaux application multimedia";		
		ArrayList<String> alphabet = Util.createAlphabet(text);
		ArrayList<Double> probabilities = Util.getOccurenceProbabilities(text, alphabet);
		probabilities = Util.roundList(probabilities, 2);
		
		alphabet = new ArrayList<String>();
		alphabet.add("aa");
		alphabet.add("ab");
		alphabet.add("ba");
		alphabet.add("bb");
		alphabet.add("ac");
		alphabet.add("ca");
		alphabet.add("bc");
		alphabet.add("cb");
		alphabet.add("cc");
		
		probabilities = new ArrayList<Double>();
		probabilities.add(new Double(0.36));
		probabilities.add(new Double(0.18));
		probabilities.add(new Double(0.18));
		probabilities.add(new Double(0.09));
		probabilities.add(new Double(0.06));
		probabilities.add(new Double(0.06));
		probabilities.add(new Double(0.03));
		probabilities.add(new Double(0.03));
		probabilities.add(new Double(0.01));
		
		
		ArrayList<Symbol> source = Util.createSource(alphabet, probabilities);
		//for(Symbol symbol: source) System.out.println(symbol+" ");
		model.coding.ShanonFano shanonFano = new model.coding.ShanonFano(source);
		shanonFano.execAlgorithm();
		System.out.println("Resultat Shanon-Fano = "+shanonFano.getResult());
	}

}
