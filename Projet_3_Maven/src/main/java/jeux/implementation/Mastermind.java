package jeux.implementation;

import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import jeux.abstractClass.Jeu;
import joueurs.abstractClass.Joueur;
import joueurs.implementation.Ordinateur;
import joueurs.implementation.Personne;

/**
 * La classe Mastermind hérite de la classe Jeu
 * @author romaindemellier
 *
 */

public class Mastermind extends Jeu {

	private int nbreChiffre;
	
	/**
	 * Constructeur de Mastermind
	 * @param mode le mode choisi
	 * @param nbreC le nombre de cases de la combinaison
	 * @param nbreE le nombre d'essais
	 * @param nbreChi le nombre de chiffres utilisables
	 */
	
	public Mastermind(String mode, int nbreC, int nbreE, int nbreChi, Boolean modeD) {
		this.mode = mode;
		this.nbreCases = nbreC;
		this.nbreEssai = nbreE;
		this.nbreChiffre = nbreChi;
		this.modeDeveloppeur = modeD;
		String str = "";
		Boolean continuer = false;
		JOptionPane jop = new JOptionPane();
		JOptionPane jop2 = new JOptionPane();
		String combi1 = "";
		String combi2 = "";
		String[] tab = {"Continuer","Quitter"};
		
			Boolean gagne = false;
			if(mode.equals("challenger")) {
				//Si le mode est challenger la combinaison secrète va
				//être générée par l'ordinateur avec la méthode genereCombinaison() et le joueur sera une 
				//personne humaine
				String combi = this.genereCombinaison();
				this.joueur1 = new Personne(this.nbreCases, combi);
				do {
					//Tant que l'utilisateur n'a pas trouvé la combinaison
					//secrète ou décidé de quitter
					gagne = this.joueur1.jouerMastermind(this.modeDeveloppeur);
				} while(!gagne);
				
			} else if(mode.equals("defenseur")) {
				//Si le mode est défenseur on va créer une ArrayList<String> qui va contenir
				//toutes les combinaisons possibles avec la méthode toutesLesCombinaisons()
				ArrayList<String> listeCombinaisons = toutesLesCombinaisons(new ArrayList<String>(),this.nbreCases);
				System.out.println("Nombre de combinaisons : " + listeCombinaisons.size());
				//L'utilisateur va pouvoir choisir la combinaison que devra trouver l'ordinateur
				//avec la méthode chooseCombi
				String combinaison = this.chooseCombi(this.nbreChiffre);
				//On génère une proposition avec la méthode genereCombinaison avec un entier
				//en paramètre qui indique le nombre de chiffres utilisables
				String proposition = this.genereCombinaison(this.nbreChiffre);
				if(combinaison.charAt(0) != 'Q' && !combinaison.equals("NULL")) {
					//Si l'utilisateur n'a pas décidé de quitter et que la combinaison != NULL
					//on instancie un objet de la classe Ordinateur
					this.joueur1 = new Ordinateur(nbreCases, combinaison, nbreChiffre, listeCombinaisons);
					do {
						//On appelle la méthode jouerMastermind() de la classe Ordinateur
						//Elle renvoit true si l'ordinateur a trouvé la combinaison
						gagne = this.joueur1.jouerMastermind(this.modeDeveloppeur);
						if(gagne) break;
						int choix = jop2.showOptionDialog(null, "Continuer ?", "", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, tab, tab[0]);
						if(choix == 1) {
							break;
						}
					} while(!gagne);
					//Si l'ordinateur a trouvé la combinaison on sort de la boucle
				} 
			} else {
				//Si le mode est duel l'utilisateur et l'ordinateur vont jouer
				//à tour de rôle. C'est l'utilisateur qui commence.
				//La combinaison que l'utilisateur devra trouver sera générée par l'ordinateur
				//avec genereCombinaison(). Celle que l'ordinateur devra trouver sera choisi
				//par l'utilisateur avec chooseCombi()
				combi1 = this.genereCombinaison(this.nbreChiffre);
				String proposition = this.genereCombinaison(this.nbreChiffre);
				this.joueur1 = new Personne(nbreCases, combi1);
				combi2 = this.chooseCombi(this.nbreChiffre);
				//on crée une ArrayList<String> qui va contenir toutes les combinaisons possibles avec la méthode toutesLesCombinaisons()
				ArrayList<String> listeCombinaisons = toutesLesCombinaisons(new ArrayList<String>(),this.nbreCases);
				//Joueur joueur2= new Ordinateur(nbreCases, combi2, proposition, this.nbreChiffre);
				this.joueur2 = new Ordinateur(nbreCases, combi2, nbreChiffre, listeCombinaisons);
				JOptionPane j = new JOptionPane();
				do {
					if(aQuiLeTour.equals("personne")) {
						j.showMessageDialog(null, "A vous de jouer !", "Changement de joueur", JOptionPane.INFORMATION_MESSAGE);
						//Tant que l'utilisateur n'a pas trouvé la combinaison
						//secrète ou décidé de quitter
						gagne = joueur1.jouerMastermind(this.modeDeveloppeur);
						//C'est au tour de l'ordinateur
						aQuiLeTour = "ordinateur";
					} else {
						j.showMessageDialog(null, "C'est au tour de l'ordinateur", "Changement de joueur", JOptionPane.INFORMATION_MESSAGE);
						//On appelle la méthode jouerMastermind() de la classe Ordinateur
						//Elle renvoit true si l'ordinateur a trouvé la combinaison
						gagne = joueur2.jouerMastermind(this.modeDeveloppeur);
						//C'est au tour de l'utilisateur
						aQuiLeTour = "personne";
					}
				} while(!gagne);
			}
				
	}
	
	private ArrayList<String> toutesLesCombinaisons(){
		ArrayList<String> liste = new ArrayList<String>();
		String str = "";
		for(int i1 = 0;i1 < this.nbreChiffre;i1++) {
			for(int i2 = 0;i2 < this.nbreChiffre;i2++) {
				for(int i3 = 0;i3 < this.nbreChiffre;i3++) {
					for(int i4 = 0;i4 < this.nbreChiffre;i4++) {
						for(int i5 = 0;i5 < this.nbreChiffre;i5++) {
							for(int i6 = 0;i6 < this.nbreChiffre;i6++) {
								for(int i7 = 0;i7 < this.nbreChiffre;i7++) {
									for(int i8 = 0;i8 < this.nbreChiffre;i8++) {
										str += i1;
										str += i2;
										str += i3;
										str += i4;
										str += i5;
										str += i6;
										str += i7;
										str += i8;
										liste.add(str);
										str = "";
									}
								}
							}
						}
					}
				}
			}
		}
		return liste;
	}
	
	private ArrayList<String> toutesLesCombinaisons(ArrayList<String> l, int nbreCases){
		ArrayList<String> liste = new ArrayList<String>();
		String str = "";
		if(nbreCases <= 0) {
			return l;
		} else if(l.isEmpty()) {
			for(int i = 0;i < this.nbreChiffre;i++) {
				str += i;
				liste.add(str);
				str = "";
			}
			return toutesLesCombinaisons(liste,nbreCases-1);
		} else {
			for(int i = 0;i < l.size();i++) {
				str = l.get(i);
				for(int j = 0;j < this.nbreChiffre;j++) {
					liste.add(str + j);
				}
				str = "";
			}
			return toutesLesCombinaisons(liste,nbreCases-1);
		}
	}
}



