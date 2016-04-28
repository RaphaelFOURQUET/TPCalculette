/**
 * 
 */
package fr.adaming.calculette;

import java.util.Observable;

/**
 * @author INTI-0332
 *
 */
public class Calculette extends Observable {

	/**
	 * Bool�en qui permet de savoir si le dernier clic est un op�rateur.
	 */
	protected boolean isLastClicOperator = false;

	/**
	 * Bool�en pour savoir si un op�rateur a �t� s�lectionn�
	 */
	protected boolean clicOperateur = false;
	/**
	 * Operateur courant.
	 */
	protected Operateur operateur = Operateur.NoOp;	//enum operateurs

	/**
	 * Bool�en pour savoir si nous devons effacer ce qui figure � l'�cran et �crire un nouveau nombre
	 */
	protected boolean update = true;

	/**
	 * Nous allons utiliser une variable de type double pour nos calculs
	 */
	protected double nombre1 = 0;	//nombre a memoriser pour calcul (operande1)
	protected String nb1 = "0";	//pour la concatenation : nombre courant

	//M�thodes
	/**
	 * Concatene le nouveau chiffre(ou point) � la chaine representant notre nombre.
	 * @param s String repr�sentant le nombre tap�.
	 */
	protected void addChiffre(String s) {
		if(update) {
			if(s.equals("."))
				this.nb1 = "0.";	// . revient � taper 0. 
			else this.nb1 = s;
			update = false;
		}
		else {	//sinon
			if(!s.equals("."))	//si != .	car un seul . par nombre possible.
				this.nb1 = nb1+s;
			else {	//sinon ajouter seulement si pas deja present.
				if(!nb1.contains(s))
					this.nb1 = nb1+s;
			}
		}
		this.isLastClicOperator = false;

		this.informerObserver();
	}

	/**
	 * Reinitialise valeurs par defaut
	 */
	protected void reset(String str) {
		this.isLastClicOperator = false;
		this.clicOperateur = false;
		this.operateur = Operateur.NoOp;
		this.update = true;
		this.nombre1 = 0;
		this.nb1 = str;

		this.informerObserver();

	}

	/**
	 * Action r�alis�e apres un appui sur un operateur.
	 * @param o Operateur cliqu�.
	 */
	protected void appuiOperateur(Operateur o) {
		//RFRF : gestion appui sur 2 operateurs diff�rents � la suite + gestion nombre negatifs.
		//SI on vient d appuyer sur un operateur, on doit juste le modifier ?
		if(isLastClicOperator)
			operateur = o;
		else {
			if(clicOperateur) {	//Si deja un operateur
				calculer();
			}
			else {	//premier operateur cliqu�
				try {
					nombre1 = Double.valueOf(nb1);
				} catch(NumberFormatException e) {
					//Dans le cas ou on avait affiche une erreur sur ecran calculette (comme / par 0)
					nombre1 = 0;
				}
				//nb1 = "0";	//reset afffichage
			}

			update = true;	//declencher update affichage 
			operateur = o;
			clicOperateur = true;
		}
		this.isLastClicOperator = true;

		informerObserver();
	}

	/**
	 * Action r�alis�e apres un appui sur =.
	 */
	protected void appuiEgal() {
		calculer();
		//nombre1 = 0;	//reset operation mais pas affichage
		clicOperateur = false;
		update = true;
		operateur = Operateur.NoOp;
		this.isLastClicOperator = false;

		informerObserver();

	}

	/**
	 * M�thode permettant d'effectuer un calcul selon l'op�rateur s�lectionn�
	 */
	private void calculer(){
		if(operateur == Operateur.PLUS){
			nombre1 = nombre1 + Double.valueOf(nb1);
			nb1 = String.valueOf(nombre1);	//Affichage � update
		}
		else if(operateur == Operateur.MOINS){
			nombre1 = nombre1 - Double.valueOf(nb1);
			nb1 = String.valueOf(nombre1);	//Affichage � update
		}
		else if(operateur == Operateur.MULT){
			nombre1 = nombre1 * Double.valueOf(nb1);
			nb1 = String.valueOf(nombre1);	//Affichage � update
		}
		else if(operateur == Operateur.DIV){
			try{
				Double d = Double.valueOf(nb1);
				if(d == 0) {	//Gestion du /0
					throw new ArithmeticException();	//Non declenche par division flottante par defaut.
				}
				nombre1 = nombre1 / d;
			} catch(ArithmeticException e) {
				System.out.println("ArithmeticException");
				reset("Error / by 0");	//Pour affichage
			}
		}
		//nb1 = String.valueOf(nombre1);	//Affichage � update
	}

	/**
	 * DP Observer : informer nos Observers des modifications.
	 */
	private void informerObserver() {
		//Informer Observer
		this.setChanged();	//RFRF : ne pas oublier de marquer comme chang� !
		this.notifyObservers();
	}

}
