package joueurs.implementation;

import javax.swing.JOptionPane;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import jeux.abstractClass.Jeu;
import joueurs.abstractClass.Joueur;

public class Personne extends Joueur {

	private int nbreChiffre;
	private Logger logger = LogManager.getLogger(Personne.class);
	
	/**
	 * Constructeur de Personne
	 * @param n
	 * 			nombre de chiffres de la combinaison
	 * @param combi
	 * 			la combinaison
	 */
	public Personne(int n, String combi) {
		this.n = n;
		this.combinaison = combi;
	}
	
	public Personne(int n, String combi, int nbreC) {
		this.n = n;
		this.combinaison = combi;
		this.nbreChiffre = nbreC;
	}
	

	/**
	 * Méthode de la classe Personne appelé dans la classe RecherchePlusMoins
	 * @param modeD booléen qui indique si on est en mode développeur ou non
	 * @return un booléen : vrai si l'utilisateur a trouvé la combinaison
	 * 		   secrète ou si il décide de quitter le jeu
	 */
	public Boolean jouerPlusMoins(Boolean modeD) {
		int n = this.n;
		String combinaison = this.combinaison;
		String str = "";
		Boolean continuer = true;
		Boolean arreter = false;
		String resultat = "";
		JOptionPane jop = new JOptionPane();
		this.afficheCombinaison(combinaison,modeD);
		
		do {
			str = jop.showInputDialog(null, "Veuillez saisir une combinaison de " + n + " chiffres");
			if(str == null) {
				//Si l'utilisateur a cliqué sur le bouton annuler de jop
				//str vaut null, on sort de la boucle et arreter vaut true
				arreter = true; 
				break;
			}
			
			if(str.isEmpty()) {
				//Si l'utilisateur n'a rien saisi et qu'il tape sur le bouton ok
				//on revient au début de la boucle
				continue;
			}
			if(str.charAt(0) == 'Q') {
				//Si l'utilisateur a saisi un Q pour quitter on sort de la boucle
				//et arreter vaut true
				continuer = false;
				arreter = true;
			} else if(str.length() != n || !Jeu.stringComposeChiffres(str)) {
				//Si la longueur de la chaîne saisie est différente de n ou
				//si cette chaîne n'est pas exclusivement composée de chiffres
				//on affiche un message via une boîte de dialogue et on retourne
				//au début de la boucle
				jop.showMessageDialog(null, "Vous n'avez pas saisi une combinaison de " + n + " chiffres", "Attention", JOptionPane.WARNING_MESSAGE);
				continue;
			} else {
				//Arrivé ici l'utilisateur a saisi une chaîne valide
				//On affecte false à continuer pour sortir de la boucle
				continuer = false;
				if(str.equals(combinaison)) {
					//Si l'utilisateur a trouvé la combinaison secrète on
					//l'indique via une boîte de dialogue et on affecte
					//true à arreter 
					resultat = comparaison(str);
					System.out.println("Proposition : " + str + " -> Réponse : " + resultat);
					logger.info("Proposition : " + str + " -> Réponse : " + resultat);
					jop.showMessageDialog(null, "Bravo, vous avez trouvé la combinaison !", "Gagné", JOptionPane.INFORMATION_MESSAGE);
					arreter = true;
				} else {
					//Si l'utilisateur n'a pas trouvé la combinaison secrète
					//on affiche dans la console le résultat de comparaison(str)
					resultat = comparaison(str);
					System.out.println("Proposition : " + str + " -> Réponse : " + resultat + "\n");
					logger.info("Proposition : " + str + " -> Réponse : " + resultat);
				}
			}
		} while (continuer);
		return arreter;
	}
	
	
	/**
	 * Méthode de la classe Personne appelé dans la classe Mastermind
	 * @param modeD booléen qui indique si on est en mode développeur ou non
	 * @return un booléen : vrai si l'utilisateur a trouvé la combinaison
	 * 		   secrète ou si il décide de quitter le jeu
	 */
	public Boolean jouerMastermind(Boolean modeD) {
		int n = this.n;
		String combinaison = this.combinaison;
		String proposition = "";
		Boolean continuer = true;
		Boolean arreter = false;
		String resultat = "";
		JOptionPane jop = new JOptionPane();
		this.afficheCombinaison(combinaison,modeD);
		
		do {
			proposition = jop.showInputDialog(null, "Veuillez saisir une combinaison de " + n + " chiffres compris entre 0 et " + (this.nbreChiffre-1));
			//Si l'utilisateur a cliqué sur le bouton annuler de jop
			//proposition vaut null, on sort de la boucle et arreter vaut true
			if(proposition == null) {
				arreter = true;
				break;
			}
			
			if(proposition.isEmpty()) {
				//Si l'utilisateur n'a rien saisi et qu'il tape sur le bouton ok
				//on revient au début de la boucle
				continue;
			}
			if(proposition.charAt(0) == 'Q') {
				//Si l'utilisateur a saisi un Q pour quitter on sort de la boucle
				//et arreter vaut true
				continuer = false;
				arreter = true;
			} else if(proposition.length() != n || !Jeu.stringComposeChiffres(proposition) || !Jeu.verifieComprisEntre(proposition, nbreChiffre)) {
				jop.showMessageDialog(null, "Vous n'avez pas saisi une combinaison de " + n + " chiffres compris entre 0 et " + (this.nbreChiffre-1), "Attention", JOptionPane.WARNING_MESSAGE);
				//Si la longueur de la chaîne saisie est différente de n ou
				//si cette chaîne n'est pas exclusivement composée de chiffres
				//on affiche un message via une boîte de dialogue et on retourne
				//au début de la boucle
				continue;
			} else {
				continuer = false;
				if(proposition.equals(combinaison)) {
					//Si l'utilisateur a trouvé la combinaison secrète on
					//l'indique via une boîte de dialogue et on affecte
					//true à arreter 
					jop.showMessageDialog(null, "Bravo, vous avez trouvé la combinaison !", "Gagné", JOptionPane.INFORMATION_MESSAGE);
					arreter = true;
				} else {
					//Sinon on va stocker dans le tableau d'entiers tabResultat le nombre de
					//chiffres bien placés ainsi que le nombre de chiffres présents de la 
					//proposition par rapport à la combinaison secrète
					int[] tabResultat = new int[2];
					tabResultat = this.bienPlace(combinaison, proposition);
					//On affecte nbreBienPlace le nombre de bien placés
					int nbreBienPlace = tabResultat[0];
					//On affecte à nbrePresent le nombre de présents
					int nbrePresent = tabResultat[1];
					resultat = this.resultatMastermind(nbreBienPlace, nbrePresent);
					//On affiche le résultat (nombre de bien placés, nombre de présents)
					System.out.println("Proposition : " + proposition + " -> Réponse : " + resultat + "\n");
					logger.info("Proposition : " + proposition + " -> Réponse : " + resultat + "\n");
				}
			}
		} while (continuer);
		return arreter;
	}
	
	
	/**
	 * Méthode qui affiche la combinaison secrète si le mode développeur est actif
	 * une combinaison cachée sinon
	 * @param str la combinaison secrète à afficher
	 * @param modeD booléen qui indique si on est en mode développeur ou non
	 */
	public void afficheCombinaison(String str, Boolean modeD) {
		if(modeD) {
			//Si on est en mode développeur on affiche la combinaison secrète
			System.out.println("(Combinaison secrète que vous devez trouver : " + str + ")");
			logger.info("(Combinaison secrète que vous devez trouver : " + str + ")");
		} else {
			//Sinon on affiche on cache la combinaison
			String combiCache = "";
			for(int i = 0;i < this.n;i++) {
				combiCache += '*';
			}
			System.out.println("(Combinaison secrète que vous devez trouver : " + combiCache + ")");
			logger.info("(Combinaison secrète que vous devez trouver : " + combiCache + ")");
		}
	}
}

