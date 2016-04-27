/**
 * 
 */
package fr.adaming.calculette;

/**
 * @author INTI-0332
 *	Enumeration des operateurs de ma calculette.
 */
public enum Operateur {
	NoOp(""),
	PLUS("+"),
	MOINS("*"),
	MULT("*"),
	DIV("/");

	/**
	 * Symbole mathématique de l'opérateur
	 */
	private String symbole = "";
	

	//Constructeur  
	/**
	 * Constructeyr Operateur
	 * @param symbole
	 */
	Operateur(String symbole){
		this.symbole = symbole;
	}

	/**
	 * Renvoie le symbole assoçie à l'operateur.
	 */
	public String toString(){
		return symbole;
	} 

}
