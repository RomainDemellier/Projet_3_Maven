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
				String combi = this.genereCombinaison(this.nbreChiffre);
				this.joueur1 = new Personne(this.nbreCases, combi, this.nbreChiffre);
				do {
					//Tant que l'utilisateur n'a pas trouvé la combinaison
					//secrète ou décidé de quitter
					gagne = this.joueur1.jouerMastermind(this.modeDeveloppeur);
					this.nbreEssai--;
				} while(!gagne && this.nbreEssai > 0);
				
				if(this.nbreEssai <= 0 && !gagne) {
					//Si le nombre d'essais est dépassé et que l'utilisateur n'a pas trouvé
					//la combinaison secrète une boîte de dialogue s'affiche
					jop.showMessageDialog(null, "Nombre d'essais dépassés.", "Fini", JOptionPane.INFORMATION_MESSAGE);
				}
				
			} else if(mode.equals("defenseur")) {
				//Si le mode est défenseur on va créer une ArrayList<String> qui va contenir
				//toutes les combinaisons possibles avec la méthode toutesLesCombinaisons()
				ArrayList<String> listeCombinaisons = toutesLesCombinaisons(new ArrayList<String>(),this.nbreCases);
				//L'utilisateur va pouvoir choisir la combinaison que devra trouver l'ordinateur
				//avec la méthode chooseCombi
				String combinaison = this.chooseCombi(this.nbreChiffre);
				//String proposition = this.genereCombinaison(this.nbreChiffre);
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
						this.nbreEssai--;
					} while(!gagne && this.nbreEssai > 0);
					
					if(this.nbreEssai <= 0 && !gagne) {
						//Si le nombre d'essais est dépassé et que l'ordinateur n'a pas trouvé
						//la combinaison secrète une boîte de dialogue s'affiche
						jop.showMessageDialog(null, "Nombre d'essais dépassés.", "Fini", JOptionPane.INFORMATION_MESSAGE);
					}
					
				} 
			} else {
				//Si le mode est duel l'utilisateur et l'ordinateur vont jouer
				//à tour de rôle. C'est l'utilisateur qui commence.
				//La combinaison que l'utilisateur devra trouver sera générée par l'ordinateur
				//avec genereCombinaison(). Celle que l'ordinateur devra trouver sera choisie
				//par l'utilisateur avec chooseCombi()
				combi1 = this.genereCombinaison(this.nbreChiffre);
				String proposition = this.genereCombinaison(this.nbreChiffre);
				this.joueur1 = new Personne(nbreCases, combi1,this.nbreChiffre);
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
						//On décrémente nbreEssai une fois que l'utilisateur et l'ordinateur
						//ont joué.
						this.nbreEssai--;
					}
				} while(!gagne && this.nbreEssai > 0);
				//Si un des deux joueurs a gagné ou que l'utilisateur a quitté
				//ou que le nombre d'essais est dépassé on sort de la boucle 
				if(this.nbreEssai <= 0 && !gagne) {
					//Si le nombre d'essais est dépassé et que ni l'utilisateur ni
					//l'ordinateur n'ont trouvé la combinaison secrète une boîte de dialogue s'affiche
					jop.showMessageDialog(null, "Nombre d'essais dépassés.", "Fini", JOptionPane.INFORMATION_MESSAGE);
				}
			}
				
	}
	
	/**
	 * La méthode toutesLesCombinaisons() va générer une ArrayList de String contenant toutes les
	 * combinaisons possibles en fonction du nombre de cases de la combinaison et du nombre de 
	 * chiffre utilisables pour le mastermind. Cette méthode est récursive.
	 * @param l ArrayList de String vide au premier appel
	 * @param nbreCases nombre de cases de la combinaison
	 * @return une ArrayList de String contenant toutes les combinaisons possibles
	 */
	
	private ArrayList<String> toutesLesCombinaisons(ArrayList<String> l, int nbreCases){
		ArrayList<String> liste = new ArrayList<String>();
		String str = "";
		if(nbreCases <= 0) {
			//Si le nombre de cases est inférieur ou égal à 0 on retourne l
			return l;
		} else if(l.isEmpty()) {
			//Si l est vide cela veut dire qu'il s'agit du premier appel de la méthode
			//On stocke dans liste toutes les combinaisons possibles à une case
			for(int i = 0;i < this.nbreChiffre;i++) {
				str += i;
				liste.add(str);
				str = "";
			}
			//On fait un appel récursif de la méthode avec la liste qu'on vient de générer
			//et on décrémente nbreCases.
			return toutesLesCombinaisons(liste,nbreCases-1);
		} else {
			//Si l n'est pas vide
			//l contient toutes les combinaisons possibles d'un certain nombre de cases n.
			for(int i = 0;i < l.size();i++) {
				str = l.get(i);
				for(int j = 0;j < this.nbreChiffre;j++) {
					//A partir de l on va construire une liste de toutes les combinaisons 
					//possibles avec n+1 cases.
					liste.add(str + j);
				}
				str = "";
			}
			//On fait un appel récursif de la méthode avec la liste qu'on vient de générer
			//et on décrémente nbreCases.
			return toutesLesCombinaisons(liste,nbreCases-1);
		}
	}
}



