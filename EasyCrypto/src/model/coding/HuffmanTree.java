package model.coding;

import java.util.*;

import model.base.*;
import helper.*;

/**
 * 
 * @author Charles
 * <p>Cette classe represente la classe gerant le codage de Huffman.
 * Elle stocke ce codage sous la forme d'un arbre, d'ou le nom de la
 * classe.</p>
 *
 */
public class HuffmanTree {

	/**
	 * Table des paires (symbole,probabilite) triees par ordre decroissant de probabilite.
	 */
	private ArrayList<Symbol> source;
	
	/**
	 * Represente l'arbre de Huffman.
	 */
	private LinkedList<Node> tree ;
	
	/**
	 * <p>Construit une instance de l'arbre de Huffman a partir d'une liste de symboles.</p>
	 * @param source Liste de symboles.
	 * @throws Exception Erreur lancee lorsque le code ne se deroule pas correctement
	 */
	public HuffmanTree(ArrayList<Symbol> source) throws Exception {
		this.source = Util.inverseSource(Util.sortSource(source)) ;
		this.tree = null;
	}
	
	/**
	 * <p>Affecte le  parametre commme source de donnees de l'arbre a construire.</p>
	 * @param source Liste de syymboles.
	 * @throws Exception Erreur lancee lorsque le code ne se deroule pas correctement
	 */
	public void setSource(ArrayList<Symbol> source) throws Exception {
		this.source = Util.inverseSource(Util.sortSource(source)) ;
		this.tree = null;
	}
	
	/**
	 * @return La source de donnees de l'arbre.
	 */
	public ArrayList<Symbol> getSource() {return this.source;}
	
	/**
	 * @return L'arbre de Huffman.
	 */
	public Node getResult() {
		if(tree==null) return null;
		return this.tree.get(0);
	}
	
	/**
	 * <p> Execute l'algorithme de Huffman a partir de la source de donnees.</p>
	 * @see #getResult()
	 * @throws Exception Erreur lancee lorsque le code ne se deroule pas correctement
	 */
	public void execAlgorithm() throws Exception {
		buildNodeList();
		while(tree!=null && tree.size()>1){
			Node l = tree.get(0);
			l.setValue(0);
			tree.remove(0);
			Node r = tree.get(0);
			r.setValue(1);
			tree.remove(0);
			Node node = new Node(l,r);
			tree.addFirst(node);
			sortNodeList();
		}
		LinkedList<Node> arbre = new LinkedList<Node>();		
		arbre.add(buildCodeList(tree.get(0),""));
		tree = arbre ;
	}
	
	/* Construit la liste des noeuds a partir de la source.*/
	private void buildNodeList() throws Exception {
		tree = null;
		if(source!=null){
			tree = new LinkedList<Node>();
			ArrayList<Symbol> list = Util.inverseSource(this.source);
			for(int i=0;i<list.size();i++)
				tree.add(new Node(list.get(i).getSymbol(),list.get(i).getProbability()));			
		}
	}	
	
	private void sortNodeList(){
		if(tree!=null) {
			Node aux ;
			int n = tree.size()-1,i,k ;			
			for(k=0;k<n;k++){
				for(i=0;i<n-k;i++){
					if(tree.get(i).getProbability()>tree.get(i+1).getProbability()){
						aux = tree.get(i);
						tree.set(i, tree.get(i+1));
						tree.set(i+1, aux);
					}
				}
			}
		}
	}
	
	private Node buildCodeList(Node arbre,String code) throws Exception {
		if(arbre!=null){
			if(arbre.isLeaf()) return (new Node(arbre.getSymbol(),arbre.getProbability(),code));
			Node l = buildCodeList(arbre.getLeftNode(),code+"0");
			Node r = buildCodeList(arbre.getRightNode(),code+"1");
			return new Node(l,r);
		}
		return null;
	}
	

	public String toString() {
		if(tree==null) return "";
		return ""+tree.get(0);
	}
	
	public static void main(String[] args) throws Exception {	
		ArrayList<String> alphabet = null;
		ArrayList<Double> probabilities = null;
		
		alphabet = new ArrayList<String>();
		alphabet.add("a");
		alphabet.add("b");
		alphabet.add("c");
		alphabet.add("d");
		alphabet.add("e");
		alphabet.add("f");
		
		probabilities = new ArrayList<Double>();
		probabilities.add(new Double(0.35));
		probabilities.add(new Double(0.10));
		probabilities.add(new Double(0.19));
		probabilities.add(new Double(0.25));
		probabilities.add(new Double(0.06));
		probabilities.add(new Double(0.05));
				
		ArrayList<Symbol> source = Util.createSource(alphabet, probabilities);		
		
		HuffmanTree tree = new HuffmanTree(source);
		tree.execAlgorithm();
				
		System.out.println("\n\nArbre\n "+tree.toString());		
		
	}

}
