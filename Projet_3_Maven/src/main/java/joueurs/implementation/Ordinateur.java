package joueurs.implementation;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import jeux.abstractClass.Jeu;
import joueurs.abstractClass.Joueur;



public class Ordinateur extends Joueur {

	private String proposition = "";
	private String solution = "";
	private int nbreChiffre;
	private int nombreBienPlace;
	private int nombreEstPresent;
	private String listePresent;
	private String listeNonPresent;
	private String listePresentBis;
	private ArrayList<String> listeCombinaisons = new ArrayList<String>();
	
	public Ordinateur(int n, String combi) {
		this.n = n;
		this.combinaison = combi;
		for(int i = 0;i < n;i++) {
			this.proposition += '5';
		}
	}
	
	public Ordinateur(int n, String combi, String propo, int nbreChiffre) {
		this.n = n;
		this.combinaison = combi;
		this.proposition = propo;
		this.nbreChiffre = nbreChiffre;
		this.nombreBienPlace = -1;
		this.nombreEstPresent = -1;
		this.listePresent = "";
		this.listeNonPresent = "";
		this.listePresentBis = "";
	}
	
	public Ordinateur(int n, String combi, int nbreChiffre, ArrayList<String> listeC) {
		this.n = n;
		this.combinaison = combi;
		//this.proposition = propo;
		this.nbreChiffre = nbreChiffre;
		this.listeCombinaisons = listeC;
		/*this.nombreBienPlace = -1;
		this.nombreEstPresent = -1;
		this.listePresent = "";
		this.listeNonPresent = "";
		this.listePresentBis = "";*/
	}
	
	public Boolean jouerPlusMoins() {
		int n = this.n;
		String combi = this.combinaison;
		Boolean gagne = false;
		String resultat = "";
		JOptionPane jop = new JOptionPane();
		Jeu.afficheCombinaison(combi, 'o');
		
		if(resultat.equals("")) {
			if(proposition.equals(combi)) {
				resultat = comparaison(proposition);
				System.out.println("Proposition : " + this.proposition + " -> Réponse : " + resultat);
				jop.showMessageDialog(null, "L'ordinateur a trouvé la combinaison !", "Trouvé", JOptionPane.INFORMATION_MESSAGE);
				gagne = true;
			}else {
				resultat = comparaison(this.proposition);
				System.out.println("Proposition de l'ordinateur : " + this.proposition + " -> Réponse : " + resultat + "\n");
				genereProposition(resultat);
			}
		} else {
			if(proposition.equals(combi)) {
				resultat = comparaison(this.proposition);
				System.out.println("Proposition de l'ordinateur : " + this.proposition + " -> Réponse : " + resultat);
				jop.showMessageDialog(null, "L'ordinateur a trouvé la combinaison !", "Trouvé", JOptionPane.INFORMATION_MESSAGE);
				gagne = true;
			} else {
				resultat = comparaison(this.proposition);
				System.out.println("Proposition : " + this.proposition + " -> Réponse : " + resultat + "\n");
				genereProposition(resultat);
			}
		}
		return gagne;
	}
	
	/*public Boolean jouerMastermind() {
		int n = this.n;
		String combi = this.combinaison;
		String propo = this.proposition;
		int nbreBP;
		int nbreEP;
		String resultat = "";
		JOptionPane jop = new JOptionPane();
		Jeu.afficheCombinaison(combinaison, 'o');
		
		if(this.nombreBienPlace == -1) {
			this.nombreBienPlace = this.bienPlace(propo);
			this.nombreEstPresent = this.estPresent();
			resultat = this.resultatMastermind(this.nombreBienPlace, this.nombreEstPresent);
			System.out.println("Proposition de l'ordinateur : " + proposition + " -> Réponse : " + resultat + "\n");
		} else {
			propo = generePropositionMastermind();
			nbreBP = bienPlace(propo);
			nbreEP = estPresent();
			char c;
			if(nbreBP > this.nombreBienPlace) {	
			
				this.listePresentBis = this.listePresent;
				
				if(this.nombreEstPresent == 0 && nbreEP == 0) {
					c = this.proposition.charAt(this.solution.length());
					this.listeNonPresent = this.addCaractere(this.listeNonPresent, c);
					this.listePresent = this.removeChar(listePresent, c);
					this.listePresentBis = this.removeChar(listePresentBis, c);
				}
				this.nombreBienPlace = nbreBP;
				this.nombreEstPresent = nbreEP;
				this.proposition = propo;
				this.solution += this.proposition.charAt(this.solution.length());
				
			} else if(nbreBP < this.nombreBienPlace){
				
				this.listePresentBis = this.listePresent;
				
				c = propo.charAt(this.solution.length());
				this.solution += this.proposition.charAt(this.solution.length());
				if(nbreEP > this.nombreEstPresent) {
					//c = propo.charAt(this.solution.length());
					System.out.println("Dans nbreEP >");
					this.listePresent = this.addCaractere(listePresent, c);
					//this.listePresentBis = this.addCaractere(listePresentBis, c);
				} if(nbreEP == 0) {
					this.listeNonPresent = this.addCaractere(this.listeNonPresent, c);
					this.listePresent = this.removeChar(listePresent, c);
					this.listePresentBis = this.removeChar(listePresentBis, c);
				}
			} else {
				if(this.nombreBienPlace > 0) {
					if(nbreEP > this.nombreEstPresent) {
						c = propo.charAt(this.solution.length());
						
						this.listePresent = this.addCaractere(listePresent, c);
						//this.listePresentBis = this.addCaractere(listePresentBis, c);
					} else if(nbreEP < this.nombreEstPresent) {
						c = this.proposition.charAt(this.solution.length());
						
						this.listePresent = this.addCaractere(listePresent, c);
						//this.listePresentBis = this.addCaractere(listePresentBis, c);
						if(nbreEP == 0) {
							c = propo.charAt(this.solution.length());
							this.listeNonPresent = this.addCaractere(this.listeNonPresent, c);
							this.listePresent = this.removeChar(listePresent, c);
							this.listePresentBis = this.removeChar(listePresentBis, c);
						}
					} else if(nbreEP == 0){
						c = this.proposition.charAt(this.solution.length());
						this.listeNonPresent = this.addCaractere(this.listeNonPresent, c);
						this.listePresent = this.removeChar(listePresent, c);
						this.listePresentBis = this.removeChar(listePresentBis, c);
						c = propo.charAt(this.solution.length());
						this.listeNonPresent = this.addCaractere(this.listeNonPresent, c);
						this.listePresent = this.removeChar(listePresent, c);
						this.listePresentBis = this.removeChar(listePresentBis, c);
					}
				} else {
					if(this.nombreEstPresent == 0) {
						c = this.proposition.charAt(this.solution.length());
						this.listeNonPresent = this.addCaractere(this.listeNonPresent, c);
						this.listePresent = this.removeChar(listePresent, c);
						this.listePresentBis = this.removeChar(listePresentBis, c);
					}
					if(nbreEP == 0) {
						c = propo.charAt(this.solution.length());
						this.listeNonPresent = this.addCaractere(this.listeNonPresent, c);
						this.listePresent = this.removeChar(listePresent, c);
						this.listePresentBis = this.removeChar(listePresentBis, c);
					}
				}
				this.proposition = propo;
				this.nombreBienPlace = nbreBP;
				this.nombreEstPresent = nbreEP;
			}
			//this.nombreEstPresent = nbreEP;
			resultat = this.resultatMastermind(this.nombreBienPlace, this.nombreEstPresent);
			System.out.println("Proposition de l'ordinateur : " + proposition + " -> Réponse : " + resultat);
			System.out.println("Liste des présents : " + this.listePresent);
			System.out.println("Liste des présents bis : " + this.listePresentBis);
			System.out.println("Liste des non présents : " + this.listeNonPresent);
			if(this.solution.length() == this.n) {
				jop.showMessageDialog(null, "L'ordinateur a trouvé la combinaison !", "Trouvé", JOptionPane.INFORMATION_MESSAGE);
				return true;
			}
		}
		Boolean gagne = false;
		return gagne;
	}*/
	
	public Boolean jouerMastermind() {
		Boolean gagne = false;
		JOptionPane jop = new JOptionPane();
		String resultat = "";
		Jeu.afficheCombinaison(combinaison, 'o');
		int nombreCombinaisons = this.listeCombinaisons.size();
		int choixCombinaison = (int)(Math.random() * nombreCombinaisons);
		String combinaisonChoisi = this.listeCombinaisons.get(choixCombinaison);
		ArrayList<String> liste = new ArrayList<String>();
		
		System.out.println("Combinaison choisi : " + combinaisonChoisi);
		
		if(combinaisonChoisi.equals(this.combinaison)) {
			jop.showMessageDialog(null, "L'ordinateur a trouvé la combinaison !", "Trouvé", JOptionPane.INFORMATION_MESSAGE);
			return true;
		} else {
			int nbreBienPlace = this.bienPlace(combinaison, combinaisonChoisi);
			int nbrePresent = this.estPresent();
			resultat = this.resultatMastermind(nbreBienPlace, nbrePresent);
			System.out.println("Proposition de l'ordinateur : " + proposition + " -> Réponse : " + resultat);
			for(int i = this.listeCombinaisons.size() - 1;i >= 0;i--) {
				String str = this.listeCombinaisons.get(i);
				int nBP = this.bienPlace(combinaisonChoisi, str);
				int nEP = this.estPresent();
				if(nbreBienPlace == nBP && nbrePresent == nEP) {
					//this.listeCombinaisons.remove(i);
					liste.add(listeCombinaisons.get(i));
				}
			}
			this.listeCombinaisons = liste;
			return false;
		}
	}
	
	private void genereProposition(String res) {
		for(int i = 0;i < this.n;i++) {
			int q = Jeu.conversion(this.proposition.charAt(i));
			if(res.charAt(i) == '-') {
				q--;
			} else if(res.charAt(i) == '+') {
				q++;
			}
			char c = Jeu.conversion(q);
			char[] tab = this.proposition.toCharArray();
			tab[i] = c;
			this.proposition = String.valueOf(tab);
		}
	}
	
	private String generePropositionMastermind() {
		String propo = this.proposition;
		return incremente(propo,this.solution.length());
	}
	
	private String incremente(String propo, int index) {
		char c = propo.charAt(index);
		int n = Jeu.conversion(c);
		
		if(this.listePresentBis.length() > 0) {
			char cBis = this.listePresentBis.charAt(0);
			if(propo.charAt(index) == cBis) {
				this.listePresentBis = this.removeChar(listePresentBis, cBis);
				return incremente(propo, index);
			}
			if(index == propo.length() - 1) {
				propo = propo.substring(0, propo.length() - 1) + cBis;
			} else {
				propo = propo.substring(0, index)+ cBis + propo.substring(index + 1, propo.length());
			}
			this.listePresentBis = this.removeChar(listePresentBis, cBis);
			
			
		} else {
			
		
		
		do {
			if(n == this.nbreChiffre - 1) {
				n = 0;
			} else {
				n++;
			}
		} while(this.listeNonPresent.indexOf(Jeu.conversion(n)) != -1);
		c = Jeu.conversion(n);
		if(index == propo.length() - 1) {
			propo = propo.substring(0, propo.length() - 1) + c;
		} else {
			propo = propo.substring(0, index)+ c + propo.substring(index + 1, propo.length());
		}
		}
		return propo;
		
	}
	
	private String addCaractere(String liste,char c) {
		if(liste.indexOf(c) == -1) {
			liste += c;
		}
		return liste;
	}
	
	private String removeChar(String liste, char c) {
		String str = "";
		if(liste != null) {
		int index = liste.indexOf(c);
		if(index != -1) {
			str += c;
			liste = liste.replaceAll(str, "");
		}
		}
		return liste;
	}
}

