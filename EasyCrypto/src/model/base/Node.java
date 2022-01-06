package model.base;

/**
 * 
 * @author Charles 
 * <p> Cette Classe Represente un noeud d'un arbre. Qui peut etre soit une feuille,
 * soit un arbre ayant deux sous-arbres.
 * </p>
 */

public class Node {
	
	/**
	 * leaf represente le noeud lorsqu'il n'a pas de sous-arbre gauche et droit.
	 */
	private Element leaf=null;
	
	/**
	 * left est le sous-arbre gauche attache au noeud.
	 */
	private Node left=null;
	
	/**
	 * right est le sous-arbre droit attache au noeud.
	 */
	private Node right=null;
	
	/**
	 * value est la valeur du noeud
	 */
	private int value ;
	
	/**
	 * isLeaf dit si le noeud est considere comme une feuille ou plutot commme un sous-arbre.
	 */
	private boolean isLeaf ;
	/**
	 * <p> Contruit un noeud comme etant une feulle de l'arbre.</p>
	 * @param symbol  Symbole associe au noeud.
	 * @param probability Probabilite d'occurence du Noeud.
	 * @param code Code associe a symbol.
	 * @throws Exception Erreur lancee lorsque le code ne se deroule pas correctement
	 */
	public Node(String symbol,double probability,String code) throws Exception{
		this.leaf = new Element(symbol,probability,code);
		this.isLeaf = true;
	}
	
	/**
	 * <p> Construit un noeud feuille ayant pour code la chaine vide.</p>
	 * @param symbol Symbole a associer a la valeur de la feuille.
	 * @param probability Probabilite d'occurence de symbol.
	 * @throws Exception Erreur lancee lorsque le code ne se deroule pas correctement
	 */
	public Node(String symbol,double probability) throws Exception {
		this(symbol,probability,null);
	}
	
	/**
	 * <p> Construit un noeud feuille ayant pour code la chaine vide et probabilite 0.</p>
	 * @param symbol Symbole a associer a la valeur de la feuille.
	 * @throws Exception Erreur lancee lorsque le code ne se deroule pas correctement
	 */
	public Node(String symbol) throws Exception {
		this(symbol,0,null);
	}
	
	/**
	 * <p> Construit un noeud feuille ayant pour symbole et code la chaine vide.</p>
	 * @param probability Probabilite d'occurence de symbol.
	 * @throws Exception Erreur lancee lorsque le code ne se deroule pas correctement
	 */
	public Node(double probability) throws Exception{
		this(null,probability,null);
	}
	/*
	 * <p>Construit une noeud comme etant un arbre ayant deux sous-arbre.</p>
	 */
	public Node(Node left,Node right){
		this.left = left;
		this.right = right;
		this.isLeaf = false;
	}
	
	/**
	 * <p>Dit si le neoud est une feuille</p>
	 * @return True si le noeud represente une feuille.
	 */
	public boolean isLeaf() { return isLeaf; }
		
	/**
	 * @return Le symbole associe au noeud. 
	 */
	public String getSymbol() {
		if(isLeaf()) return this.leaf.getSymbol();
		return null;
	}
	
	/**
	 * @return La probabilite du noeud.
	 */
	public double getProbability() {
		if(isLeaf) return this.leaf.getProbability();
		double proba = 0 ;
		if(left!=null) proba+=left.getProbability();
		if(right!=null) proba+=right.getProbability();
		return proba;
	}
	
	/**
	 * <p>Considere que le noeud courant une feuille de valeur de leaf.</p>
	 * @param leaf Feuille initialisateur du noeud courant.
	 */
	public void setNode(Element leaf){
		this.leaf = leaf;
		this.isLeaf = true;
		this.left = this.right = null;		
	}
	
	/**
	 * <p>Considere que le noeud est un sous-arbre avec left et right comme respectivement
	 * les sous-arbres  gauche et droit.
	 * </p>
	 * @param left Sous-arbre gauche.
	 * @param right Sous-arbre droit.
	 */
	public void setNode(Node left,Node right){
	    this.left = left;
	    this.right = right;
	    this.isLeaf = false;
		this.leaf = null;
	}
	
	/**
	 * @return Le sous-arbre gauche attache au noeud.
	 */
	public Node getLeftNode() {
		if(isLeaf) return null;
		return this.left;
	}
	
	public Node getRightNode() {
		if(isLeaf) return null;
		return this.right;
	}
	
	/**
	 * @return Le code associe au noeud feuille.
	 */
	public String getcode() { 
		if(isLeaf())return leaf.getCode(); 
		return null;
	}
	
	/**
	 * <p> Affecte une valeur entiere au  noeud.</p>
	 * @param value Valeur associe au noeud.
	 */
	public void setValue(int value) { this.value = value; }
	
	/**
	 * @return La valeur entiere associe au noeud.
	 */
	public int getValue() {return this.value; }
	
	public String toString(){		
		if(isLeaf)  return "Leaf("+this.leaf+")";
		return "Node( "+left+" , "+right+" )";
	}
	
}
