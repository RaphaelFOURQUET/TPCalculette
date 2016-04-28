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
	 * Booléen qui permet de savoir si le dernier clic est un opérateur.
	 */
	protected boolean isLastClicOperator = false;

	/**
	 * Booléen pour savoir si un opérateur a été sélectionné
	 */
	protected boolean clicOperateur = false;
	/**
	 * Operateur courant.
	 */
	protected Operateur operateur = Operateur.NoOp;	//enum operateurs

	/**
	 * Booléen pour savoir si nous devons effacer ce qui figure à l'écran et écrire un nouveau nombre
	 */
	protected boolean update = true;

	/**
	 * Nous allons utiliser une variable de type double pour nos calculs
	 */
	protected double nombre1 = 0;	//nombre a memoriser pour calcul (operande1)
	protected String nb1 = "0";	//pour la concatenation : nombre courant

	//Méthodes
	/**
	 * Concatene le nouveau chiffre(ou point) à la chaine representant notre nombre.
	 * @param s String représentant le nombre tapé.
	 */
	protected void addChiffre(String s) {
		if(update) {
			if(s.equals("."))
				this.nb1 = "0.";	// . revient à taper 0. 
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
	 * Action réalisée apres un appui sur un operateur.
	 * @param o Operateur cliqué.
	 */
	protected void appuiOperateur(Operateur o) {
		//RFRF : gestion appui sur 2 operateurs différents à la suite + gestion nombre negatifs.
		//SI on vient d appuyer sur un operateur, on doit juste le modifier ?
		if(isLastClicOperator)
			operateur = o;
		else {
			if(clicOperateur) {	//Si deja un operateur
				calculer();
			}
			else {	//premier operateur cliqué
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
	 * Action réalisée apres un appui sur =.
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
	 * Méthode permettant d'effectuer un calcul selon l'opérateur sélectionné
	 */
	private void calculer(){
		if(operateur == Operateur.PLUS){
			nombre1 = nombre1 + Double.valueOf(nb1);
			nb1 = String.valueOf(nombre1);	//Affichage à update
		}
		else if(operateur == Operateur.MOINS){
			nombre1 = nombre1 - Double.valueOf(nb1);
			nb1 = String.valueOf(nombre1);	//Affichage à update
		}
		else if(operateur == Operateur.MULT){
			nombre1 = nombre1 * Double.valueOf(nb1);
			nb1 = String.valueOf(nombre1);	//Affichage à update
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
		//nb1 = String.valueOf(nombre1);	//Affichage à update
	}

	/**
	 * DP Observer : informer nos Observers des modifications.
	 */
	private void informerObserver() {
		//Informer Observer
		this.setChanged();	//RFRF : ne pas oublier de marquer comme changé !
		this.notifyObservers();
	}

}
