package joueurs.implementation;

import java.util.ArrayList;
import java.util.ListIterator;

import javax.swing.JOptionPane;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import jeux.abstractClass.Jeu;
import joueurs.abstractClass.Joueur;



public class Ordinateur extends Joueur {

	private String proposition = "";
	private String solution = "";
	private int nbreChiffre;
	private int nbreProposition = 1;
	private ArrayList<String> listeCombinaisons = new ArrayList<String>();
	private Logger logger = LogManager.getLogger(Ordinateur.class);
	
	/**
	 * Constructeur de la classe Ordinateur.
	 * Va être appelé dans le jeu Recherche+-.
	 * @param n le nombre de cases de la combinaison
	 * @param combi la combinaison secrète que l'ordi doit trouver
	 */
	public Ordinateur(int n, String combi) {
		this.n = n;
		this.combinaison = combi;
		for(int i = 0;i < n;i++) {
			//Pour le jeu recherche+- la proposition initiale ne sera composée
			//qu'avec des 5
			this.proposition += '5';
		}
	}
	
	/**
	 * Constructeur de la classe Ordinateur
	 * Va être appelé dans le jeu mastermind
	 * @param n nombre de cases
	 * @param combi la combinaison secrète que l'ordi doit trouver
	 * @param nbreChiffre nombre de chiffres utilisables pour le mastermind
	 * @param listeC liste contenant toutes les combinaisons possibles en fonction
	 * 				 du nombre de cases et du nombre de chiffres utilisables
	 */
	public Ordinateur(int n, String combi, int nbreChiffre, ArrayList<String> listeC) {
		this.n = n;
		this.combinaison = combi;
		this.nbreChiffre = nbreChiffre;
		this.listeCombinaisons = listeC;
	}
	
	/**
	 * Méthode de la classe Ordinateur appelé dans la classe RecherchePlusMoins
	 * @param modeD booléen qui indique si on est en mode développeur ou non
	 * @return un booléen : true si l'ordinateur a trouvé la combinaison secrète
	 * 						false sinon
	 */
	public Boolean jouerPlusMoins(Boolean modeD) {
		int n = this.n;
		String combi = this.combinaison;
		String resultat = "";
		JOptionPane jop = new JOptionPane();
		
		if(this.nbreProposition == 1) {
			System.out.println();
		}
		
		this.afficheCombinaison(combi, modeD);
		
		if(proposition.equals(combi)) {
			//Si la proposition est égale à la combinaison secrète une boîte de dialogue
			//apparaît indiquant que l'ordinateur a trouvé la combinaison et gagne prend la valeur true
			jop.showMessageDialog(null, "L'ordinateur a trouvé la combinaison !\n" + proposition, "Trouvé", JOptionPane.INFORMATION_MESSAGE);
			logger.info("L'ordinateur a trouvé la combinaison.");
			return true;
		} else {
			//Sinon la méthode comparaison retourne un résultat qui indique case par case
			//si la valeur de la combinaison secrète à l' indice j est égale, inférieure ou supérieure
			//à la valeur de la proposition faite par l'ordinateur à l'indice j 
			resultat = comparaison(this.proposition);
			//On affiche le résultat dans la console
						
			System.out.println("Proposition " + nbreProposition + " de l'ordinateur : " + this.proposition + " -> Réponse : " + resultat + "\n");
			logger.info("Proposition : " + this.proposition + " -> Réponse : " + resultat);
			//On génère une nouvelle proposition découlant de la proposition précédente
			//et du resultat de la méthode comparaison
			genereProposition(resultat);
			nbreProposition++;
		}
		return false;
	}
	
	/**
	 * Méthode de la classe Ordinateur appelé dans la classe Mastermind
	 * @param modeD booléen qui indique si on est en mode développeur ou non
	 * @return un booléen : true si l'ordinateur a trouvé la combinaison secrète
	 * 						false sinon
	 */
	public Boolean jouerMastermind(Boolean modeD) {
		Boolean gagne = false;
		JOptionPane jop = new JOptionPane();
		String resultat = "";
		
		if(this.nbreProposition == 1) {
			System.out.println();
		}
		
		this.afficheCombinaison(combinaison,modeD);
		int nombreCombinaisons = this.listeCombinaisons.size();
		//choixCombinaison est un entier pris au hasard compris entre 0 et nombreCombinaisons - 1
		int choixCombinaison = (int)(Math.random() * nombreCombinaisons);
		//On choisit la combinaison à l'indice choixCombinaison dans listeCombinaisons
		String proposition = this.listeCombinaisons.get(choixCombinaison);
		
		if(proposition.equals(this.combinaison)) {
			//Si la combinaison choisie est égale à la combinaison secrète
			//une boîte de dialogue apparaît indiquant que l'ordinateur a trouvé la combinaison
			jop.showMessageDialog(null, "L'ordinateur a trouvé la combinaison !\n" + proposition, "Trouvé", JOptionPane.INFORMATION_MESSAGE);
			logger.info("L'ordinateur a trouvé la combinaison.");
			return true;
		} else {
			
			//Sinon on va stocker dans le tableau d'entiers tabResultat le nombre de
			//chiffres bien placés ainsi que le nombre de chiffres présents de la 
			//combinaison choisie par rapport à la combinaison secrète
			int[] tabResultat = new int[2];
			tabResultat = this.bienPlace(combinaison, proposition);
			//On affecte nbreBienPlace le nombre de bien placés
			int nbreBienPlace = tabResultat[0];
			//On affecte à nbrePresent le nombre de présents
			int nbrePresent = tabResultat[1];
			resultat = this.resultatMastermind(nbreBienPlace, nbrePresent);
			//On affiche le résultat (nombre de bien placés, nombre de présents)
			
			System.out.println("Proposition " + nbreProposition +  " de l'ordinateur : " + proposition + " -> Réponse : " + resultat + "\n");
			logger.info("Proposition de l'ordinateur : " + proposition + " -> Réponse : " + resultat);
			
			nbreProposition++;
			
			ListIterator li = this.listeCombinaisons.listIterator();
			while(li.hasNext()) {
				//On parcourt la liste listeCombinaisons avec un iterator
				//On sait que la combinaison choisie a nbreBienPlace de bien placés et nbrePresent de présents
				//par rapport à la combinaison secrète.
				//Mais l'inverse est aussi vrai.
				//On peut donc éliminer toutes les combinaisons de listeCombinaisons qui n'ont pas exactement
				//nbreBienPlace de bien placés et nbrePresent de présents par rapport à la combinaison choisie
				String str = (String)li.next();
				int[] tabResultat2 = new int[2];
				tabResultat2 = this.bienPlace(proposition,str);
				int nBP = tabResultat2[0];
				int nEP = tabResultat2[1];
				if(nbreBienPlace != nBP || nbrePresent != nEP) {
					li.remove();
				}
			}
			return false;
		}
	}
	
	/**
	 * Méthode qui affiche dans la console la combinaison secrète que l'ordi doit trouver
	 * @param str la combinaison à afficher
	 * @param modeD 
	 */
	public void afficheCombinaison(String str, Boolean modeD) {
		System.out.println("(Combinaison secrète que l'ordinateur doit trouver : " + str + ")");
		logger.info("(Combinaison secrète que l'ordinateur doit trouver : " + str + ")");
	}
	
	/**
	 * Méthode qui à partir de la proposition courante et du résultat, va générer
	 * une nouvelle proposition dans le jeu RecherchePlusMoins
	 * @param res indique case par case si la valeur de la combinaison secrète à l'indice j est 
	 * 			  "=" pour égale, "-" pour inférieure, "+" pour supérieure à la valeur de la proposition 
	 * 			 faite par l'ordinateur à l'indice j 
	 */
	private void genereProposition(String res) {
		for(int i = 0;i < this.n;i++) {
			//On parcourt la proposition et on convertit en entier la caractère de la proposition
			//à l'indice i
			int q = Jeu.conversion(this.proposition.charAt(i));
			if(res.charAt(i) == '-') {
				//Si res.charAt(i) = '-' on décrémente q
				q--;
			} else if(res.charAt(i) == '+') {
				//Sinon on incrémente q
				q++;
			}
			//On convertit q en caractère
			char c = Jeu.conversion(q);
			//Puis on remplace le caractère de la proposition à l'indice i par c
			char[] tab = this.proposition.toCharArray();
			tab[i] = c;
			this.proposition = String.valueOf(tab);
		}
	}
	
}

