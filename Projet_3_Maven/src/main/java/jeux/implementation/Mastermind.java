package jeux.implementation;

import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import jeux.abstractClass.Jeu;
import joueurs.abstractClass.Joueur;
import joueurs.implementation.Ordinateur;
import joueurs.implementation.Personne;

public class Mastermind extends Jeu {

	private int nbreChiffre;
	
	public Mastermind(String mode, int nbreC, int nbreE, int nbreChi) {
		this.mode = mode;
		this.nbreCases = nbreC;
		this.nbreEssai = nbreE;
		this.nbreChiffre = nbreChi;
		String str = "";
		Boolean continuer = false;
		JOptionPane jop = new JOptionPane();
		JOptionPane jop2 = new JOptionPane();
		String combi1 = "";
		String combi2 = "";
		String[] tab = {"Continuer","Quitter"};
		
			Boolean gagne = false;
			if(mode.equals("challenger")) {
				String combi = this.genereCombinaison();
				//this.afficheCombinaison(combi);
				this.joueur = new Personne(this.nbreCases, combi);
				do {
					gagne = this.joueur.jouerMastermind();
				} while(!gagne);
				
			} else if(mode.equals("defenseur")) {
				ArrayList<String> listeCombinaisons = toutesLesCombinaisons(new ArrayList<String>(),this.nbreCases);
				System.out.println("Nombre de combinaisons : " + listeCombinaisons.size());
				String combinaison = this.chooseCombi(this.nbreChiffre);
				String proposition = this.genereCombinaison(this.nbreChiffre);
				if(combinaison.charAt(0) != 'Q' && !combinaison.equals("NULL")) {
					//this.joueur = new Ordinateur(nbreCases, combinaison, proposition,nbreChiffre);
					//this.joueur = new Ordinateur(nbreCases, combinaison, proposition,nbreChiffre);
					this.joueur = new Ordinateur(nbreCases, combinaison, nbreChiffre, listeCombinaisons);
					do {
						//gagne = this.joueur.jouerMastermind();
						gagne = this.joueur.jouerMastermind();
						if(gagne) break;
						int choix = jop2.showOptionDialog(null, "Continuer ?", "", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, tab, tab[0]);
						if(choix == 1) {
							break;
						}
					} while(!gagne);
				} 
			}else {
					combi1 = this.genereCombinaison(this.nbreChiffre);
					String proposition = this.genereCombinaison(this.nbreChiffre);
					Joueur joueur1 = new Personne(nbreCases, combi1);
					combi2 = this.chooseCombi(this.nbreChiffre);
					ArrayList<String> listeCombinaisons = toutesLesCombinaisons(new ArrayList<String>(),this.nbreCases);
					//Joueur joueur2= new Ordinateur(nbreCases, combi2, proposition, this.nbreChiffre);
					Joueur joueur2 = new Ordinateur(nbreCases, combi2, nbreChiffre, listeCombinaisons);
					JOptionPane j = new JOptionPane();
					do {
						if(aQuiLeTour.equals("personne")) {
							j.showMessageDialog(null, "A vous de jouer !", "Changement de joueur", JOptionPane.INFORMATION_MESSAGE);
							gagne = joueur1.jouerMastermind();
							aQuiLeTour = "ordinateur";
						} else {
							j.showMessageDialog(null, "C'est au tour de l'ordinateur", "Changement de joueur", JOptionPane.INFORMATION_MESSAGE);
							gagne = joueur2.jouerMastermind();
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



