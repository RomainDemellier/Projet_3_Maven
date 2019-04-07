package jeux.implementation;

import java.util.Scanner;

import javax.swing.JOptionPane;

import jeux.abstractClass.Jeu;
import joueurs.abstractClass.Joueur;
import joueurs.implementation.Ordinateur;
import joueurs.implementation.Personne;

public class RecherchePlusMoins extends Jeu {
	
	/**
	 * Constructeur de RecherchePlusMoins
	 * @param mode le mode du jeu
	 * @param nbreC le nombre de cases de la combinaison
	 * @param nbreE le nombre d'essais
	 */
	
	public RecherchePlusMoins(String mode, int nbreC, int nbreE) {
		
		this.nbreCases = nbreC;
		this.nbreEssai = nbreE;
		this.mode = mode;
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
			//être générée par l'ordinateur et le joueur sera une 
			//personne humaine
			combi = this.genereCombinaison();
			this.joueur = new Personne(nbreCases, combi);
			do {
				//Tant que l'utilisateur n'a pas trouvé la combinaison
				//secrète ou décidé de quitter
				gagne = this.joueur.jouerPlusMoins();
			} while(!gagne);
				
		} else if(mode.equals("defenseur")) {
			//Si le mode est défenseur c'est l'utilisateur qui va 
			//choisir la combinaison secrète et le joueur sera 
			//l'ordinateur
			String combinaison = this.chooseCombi();
			if(combinaison.charAt(0) != 'Q' && !combinaison.equals("NULL")) {
				this.joueur = new Ordinateur(nbreCases, combinaison);
				do {
					gagne = this.joueur.jouerPlusMoins();
					if(gagne) break;
					int choix = jop3.showOptionDialog(null, "Continuer ?", "", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, tab, tab[0]);
					if(choix == 1) {
						break;
					}
				} while(!gagne);
			} else {
				gagne = true;
			}
		} else {
			combi1 = this.genereCombinaison();
			Joueur joueur1 = new Personne(nbreCases, combi1);
			combi2 = this.chooseCombi();
			Joueur joueur2= new Ordinateur(nbreCases, combi2);
			JOptionPane j = new JOptionPane();
			do {
				if(aQuiLeTour.equals("personne")) {
					j.showMessageDialog(null, "A vous de jouer !", "Changement de joueur", JOptionPane.INFORMATION_MESSAGE);
					gagne = joueur1.jouerPlusMoins();
					aQuiLeTour = "ordinateur";
				} else {
					j.showMessageDialog(null, "C'est au tour de l'ordinateur", "Changement de joueur", JOptionPane.INFORMATION_MESSAGE);
					gagne = joueur2.jouerPlusMoins();
					aQuiLeTour = "personne";
				}
			} while(!gagne);
		}
	}
}



