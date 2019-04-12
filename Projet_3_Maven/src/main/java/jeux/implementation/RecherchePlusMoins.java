package jeux.implementation;

import java.util.Scanner;

import javax.swing.JOptionPane;

import jeux.abstractClass.Jeu;
import joueurs.abstractClass.Joueur;
import joueurs.implementation.Ordinateur;
import joueurs.implementation.Personne;

/**
 * La classe RecherchePlusMoins hérite de la classe Jeu
 * @author romaindemellier
 *
 */

public class RecherchePlusMoins extends Jeu {
	
	/**
	 * Constructeur de RecherchePlusMoins
	 * @param mode le mode du jeu
	 * @param nbreC le nombre de cases de la combinaison
	 * @param nbreE le nombre d'essais
	 * @param modeD booléen à true si le mode développeur est actif
	 */
	
	public RecherchePlusMoins(String mode, int nbreC, int nbreE,Boolean modeD) {
		
		this.nbreCases = nbreC;
		this.nbreEssai = nbreE;
		this.mode = mode;
		this.modeDeveloppeur = modeD;
		String str = "";
		int nbreChiffres = 0;
		Boolean continuer = false;
		JOptionPane jop = new JOptionPane();
		JOptionPane jop2 = new JOptionPane();
		JOptionPane jop3 = new JOptionPane();
		String[] tab = {"Continuer","Quitter"};
		String combi ="";
		String combi1 ="";
		String combi2 ="";
		Boolean gagne = false;
		Scanner sc;
		
		if(mode.equals("challenger")) {
			//Si le mode est challenger la combinaison secrète va
			//être générée par l'ordinateur avec la méthode genereCombinaison() et le joueur sera une 
			//personne humaine
			combi = this.genereCombinaison();
			this.joueur1 = new Personne(nbreCases, combi);
			do {
				//Tant que l'utilisateur n'a pas trouvé la combinaison
				//secrète ou décidé de quitter
				gagne = this.joueur1.jouerPlusMoins(this.modeDeveloppeur);
			} while(!gagne);
				
		} else if(mode.equals("defenseur")) {
			//Si le mode est défenseur c'est l'utilisateur qui va 
			//choisir la combinaison secrète avec la méthode chooseCombi() et le joueur sera 
			//l'ordinateur
			String combinaison = this.chooseCombi();
			if(combinaison.charAt(0) != 'Q' && !combinaison.equals("NULL")) {
				this.joueur1 = new Ordinateur(nbreCases, combinaison);
				do {
					//On appelle la méthode jouerPlusMoins() de la classe Ordinateur.
					//Elle renvoit true si l'ordinateur a trouvé la combinaison
					gagne = this.joueur1.jouerPlusMoins(this.modeDeveloppeur);
					if(gagne) break;
					int choix = jop3.showOptionDialog(null, "Continuer ?", "", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, tab, tab[0]);
					if(choix == 1) {
						break;
					}
				} while(!gagne);
				//Si l'ordinateur a trouvé la combinaison on sort de la boucle
			} else {
				gagne = true;
			}
		} else {
			//Si le mode est duel l'utilisateur et l'ordinateur vont jouer
			//à tour de rôle. C'est l'utilisateur qui commence.
			//La combinaison que l'utilisateur devra trouver sera générée par l'ordinateur
			//avec genereCombinaison(). Celle que l'ordinateur devra trouver sera choisi
			//par l'utilisateur avec chooseCombi()
			combi1 = this.genereCombinaison();
			this.joueur1 = new Personne(nbreCases, combi1);
			combi2 = this.chooseCombi();
			this.joueur2= new Ordinateur(nbreCases, combi2);
			JOptionPane j = new JOptionPane();
			do {
				if(aQuiLeTour.equals("personne")) {
					j.showMessageDialog(null, "A vous de jouer !", "Changement de joueur", JOptionPane.INFORMATION_MESSAGE);
					//On appelle la méthode jouerPlusMoins() de la classe Personne.
					//Elle renvoit true si l'utilisateur a trouvé la combinaison ou si il a
					//décidé de quitter
					gagne = joueur1.jouerPlusMoins(this.modeDeveloppeur);
					//C'est maintenant au tour de l'ordinateur
					aQuiLeTour = "ordinateur";
				} else {
					j.showMessageDialog(null, "C'est au tour de l'ordinateur", "Changement de joueur", JOptionPane.INFORMATION_MESSAGE);
					//On appelle la méthode jouerPlusMoins() de la classe Ordinateur.
					//Elle renvoit true si l'ordinateur a trouvé la combinaison
					gagne = joueur2.jouerPlusMoins(this.modeDeveloppeur);
					//C'est au tour de l'utilisateur
					aQuiLeTour = "personne";
				}
			} while(!gagne);
			//Si un des deux joueurs a gagné ou que l'utilisateur a quitté
			//on sort de la boucle
		}
	}
}



