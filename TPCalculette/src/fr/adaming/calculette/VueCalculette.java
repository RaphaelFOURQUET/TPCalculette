/**
 * 
 */
package fr.adaming.calculette;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author INTI-0332
 *	Parte interface graphique de ma calculette.
 */
@SuppressWarnings("serial")
public class VueCalculette extends JFrame {
	/**
	 * Calculette Observer
	 */
	protected Calculette calculette;

	/**
	 * Tableau stockant les éléments à afficher dans la calculatrice
	 */
	private String[] tab_string = {"0", "1", "2", "3", "4", "5", "6", "7", "8",
			"9", ".", "=", "C", "+", "-", "*", "/"};
	/**
	 * Un bouton par élément à afficher
	 */
	private JButton[] tab_button = new JButton[tab_string.length];

	/**
	 * JLabel pour affichage du resultat.
	 */
	protected JLabel labelRes = new JLabel("0");

	/**
	 * Constructeur VueCalculette sans paramêtre.
	 */
	public VueCalculette() {
		this.calculette = new Calculette();

		this.setTitle("Calculette");
		this.setSize(200, 200);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLocationRelativeTo(null);

		init();

		initLayouts();

		//RFRF : On rend la frame visible.
		this.setVisible(true);

		//S'ajouter à la liste des Observer
		//On place un observer sur la calculette.
		this.calculette.addObserver(new Observer(){
			@Override
			public void update(Observable o, Object arg) {
				// Auto-generated method stub
				//System.out.println("Test : Update !");
				labelRes.setText(calculette.nb1);	//RFRF ou par arguments mais necessite cast .

			}
		});

	}

	/**
	 * Creation des Buttons et de leurs Listeners.
	 */
	private void init() {
		
		//On parcourt le tableau initialisé afin de créer nos boutons
		for(int i = 0; i < tab_string.length; i++){
			tab_button[i] = new JButton(tab_string[i]);
			switch(i){
			//Pour chaque élément situé à la fin du tableau et qui n'est pas un chiffre
			//on définit le comportement à avoir grâce à un listener
			case 11 :
				tab_button[i].addActionListener(new EgalListener());
				break;
			case 12 :
				tab_button[i].setForeground(Color.RED);
				tab_button[i].addActionListener(new ResetListener());
				break;
			case 13 :
				tab_button[i].addActionListener(new PlusListener());
				break;
			case 14 :
				tab_button[i].addActionListener(new MoinsListener());
				break;
			case 15 :
				tab_button[i].addActionListener(new MultiListener());
				break;
			case 16 :
				tab_button[i].addActionListener(new DivListener());
				break;
			default :
				//Par défaut, ce sont les premiers éléments du tableau donc des chiffres et . , on affecte alors le bon listener
				tab_button[i].addActionListener(new ChiffreListener());
				break;
			}
		}
	}


	/**
	 * Initialisation des layouts requis et positionnement des Buttons.
	 */
	private void initLayouts() {
		//On définit le layout à utiliser sur le content pane
		this.setLayout(new BorderLayout());

		//JPanels
		JPanel panChiffre = new JPanel();
		JPanel panEcran = new JPanel();
		JPanel panOperateur = new JPanel();

		this.getContentPane().add(panChiffre, BorderLayout.CENTER);
		this.getContentPane().add(panOperateur, BorderLayout.EAST);
		this.getContentPane().add(panEcran, BorderLayout.NORTH);

		//Jlabel ajouté au panel nord
		panEcran.add(labelRes);


		//chiffre
		JPanel chiffreL1 = new JPanel();

		chiffreL1.setLayout(new BoxLayout(chiffreL1, BoxLayout.LINE_AXIS));
		chiffreL1.add(tab_button[1]);
		chiffreL1.add(tab_button[2]);
		chiffreL1.add(tab_button[3]);

		JPanel chiffreL2 = new JPanel();

		chiffreL2.setLayout(new BoxLayout(chiffreL2, BoxLayout.LINE_AXIS));
		chiffreL2.add(tab_button[4]);
		chiffreL2.add(tab_button[5]);
		chiffreL2.add(tab_button[6]);

		JPanel chiffreL3 = new JPanel();

		chiffreL3.setLayout(new BoxLayout(chiffreL3, BoxLayout.LINE_AXIS));
		chiffreL3.add(tab_button[7]);
		chiffreL3.add(tab_button[8]);
		chiffreL3.add(tab_button[9]);

		JPanel chiffreL4 = new JPanel();

		chiffreL4.setLayout(new BoxLayout(chiffreL4, BoxLayout.LINE_AXIS));
		chiffreL4.add(tab_button[0]);
		chiffreL4.add(tab_button[10]);
		chiffreL4.add(tab_button[11]);

		//On positionne maintenant ces lignes en colonne
		panChiffre.setLayout(new BoxLayout(panChiffre, BoxLayout.PAGE_AXIS));
		panChiffre.add(chiffreL1);
		panChiffre.add(chiffreL2);
		panChiffre.add(chiffreL3);
		panChiffre.add(chiffreL4);

		panOperateur.setLayout(new BoxLayout(panOperateur, BoxLayout.PAGE_AXIS));
		panOperateur.add(tab_button[12]);
		panOperateur.add(tab_button[13]);
		panOperateur.add(tab_button[14]);
		panOperateur.add(tab_button[15]);
		panOperateur.add(tab_button[16]);
	}

	
	/**
	 * Listener utilisé pour les chiffres et .
	 * @author INTI-0332
	 *
	 */
	class ChiffreListener implements ActionListener {
		public void actionPerformed(ActionEvent event){

			//On modifie calculette en recuperant le chiffre associe au Button.
			calculette.addChiffre(((JButton)event.getSource()).getText());
		}
	}

	/**
	 * Listener affecté au bouton =
	 * @author INTI-0332
	 *
	 */
	class EgalListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0){
			calculette.appuiEgal();
		}
	}
	
	/**
	 * Listener affecté au bouton +
	 * @author INTI-0332
	 *
	 */
	class PlusListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0){
			calculette.appuiOperateur(Operateur.PLUS);
		}
	}
	/**
	 * Listener affecté au bouton -
	 * @author INTI-0332
	 *
	 */
	class MoinsListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0){
			calculette.appuiOperateur(Operateur.MOINS);
		}
	}

	/**
	 * Listener affecté au bouton *
	 * @author INTI-0332
	 *
	 */
	class MultiListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0){
			calculette.appuiOperateur(Operateur.MULT);
		}
	}

	/**
	 * Listener affecté au bouton /
	 * @author INTI-0332
	 *
	 */
	class DivListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0){
			calculette.appuiOperateur(Operateur.DIV);
		}
	}

	/**
	 * Listener affecté au bouton de remise à zéro
	 * @author INTI-0332
	 *
	 */
	class ResetListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0){
			//reset
			calculette.reset("0");
		}
	}

}
