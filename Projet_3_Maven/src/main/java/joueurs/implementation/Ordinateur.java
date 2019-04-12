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
	private int nombreBienPlace;
	private int nombreEstPresent;
	private String listePresent;
	private String listeNonPresent;
	private String listePresentBis;
	private ArrayList<String> listeCombinaisons = new ArrayList<String>();
	private Logger logger = LogManager.getLogger(Ordinateur.class);
	
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
	
	public Boolean jouerPlusMoins(Boolean modeD) {
		int n = this.n;
		String combi = this.combinaison;
		Boolean gagne = false;
		String resultat = "";
		JOptionPane jop = new JOptionPane();
		this.afficheCombinaison(combi, modeD);
		
		if(resultat.equals("")) {
			if(proposition.equals(combi)) {
				resultat = comparaison(proposition);
				System.out.println("Proposition : " + this.proposition + " -> Réponse : " + resultat);
				logger.info("Proposition : " + this.proposition + " -> Réponse : " + resultat);
				jop.showMessageDialog(null, "L'ordinateur a trouvé la combinaison !", "Trouvé", JOptionPane.INFORMATION_MESSAGE);
				gagne = true;
			}else {
				resultat = comparaison(this.proposition);
				System.out.println("Proposition de l'ordinateur : " + this.proposition + " -> Réponse : " + resultat + "\n");
				logger.info("Proposition de l'ordinateur : " + this.proposition + " -> Réponse : " + resultat + "\n");
				genereProposition(resultat);
			}
		} else {
			if(proposition.equals(combi)) {
				resultat = comparaison(this.proposition);
				System.out.println("Proposition de l'ordinateur : " + this.proposition + " -> Réponse : " + resultat);
				logger.info("Proposition de l'ordinateur : " + this.proposition + " -> Réponse : " + resultat);
				jop.showMessageDialog(null, "L'ordinateur a trouvé la combinaison !", "Trouvé", JOptionPane.INFORMATION_MESSAGE);
				gagne = true;
			} else {
				resultat = comparaison(this.proposition);
				System.out.println("Proposition : " + this.proposition + " -> Réponse : " + resultat + "\n");
				logger.info("Proposition : " + this.proposition + " -> Réponse : " + resultat + "\n");
				genereProposition(resultat);
			}
		}
		return gagne;
	}
	
	
	public Boolean jouerMastermind(Boolean modeD) {
		Boolean gagne = false;
		JOptionPane jop = new JOptionPane();
		String resultat = "";
		this.afficheCombinaison(combinaison,modeD);
		int nombreCombinaisons = this.listeCombinaisons.size();
		int choixCombinaison = (int)(Math.random() * nombreCombinaisons);
		String combinaisonChoisi = this.listeCombinaisons.get(choixCombinaison);
		
		System.out.println("Combinaison choisi : " + combinaisonChoisi);
		
		if(combinaisonChoisi.equals(this.combinaison)) {
			jop.showMessageDialog(null, "L'ordinateur a trouvé la combinaison !", "Trouvé", JOptionPane.INFORMATION_MESSAGE);
			logger.info("L'ordinateur a trouvé la combinaison.");
			return true;
		} else {
			
			int[] tabResultat = new int[2];
			tabResultat = this.bienPlace(combinaison, combinaisonChoisi);
			int nbreBienPlace = tabResultat[0];
			int nbrePresent = tabResultat[1];
			resultat = this.resultatMastermind(nbreBienPlace, nbrePresent);
			System.out.println("Proposition de l'ordinateur : " + proposition + " -> Réponse : " + resultat);
			logger.info("Proposition de l'ordinateur : " + proposition + " -> Réponse : " + resultat);
			
			ListIterator li = this.listeCombinaisons.listIterator();
			while(li.hasNext()) {
				String str = (String)li.next();
				int[] tabResultat2 = new int[2];
				tabResultat2 = this.bienPlace(combinaisonChoisi,str);
				int nBP = tabResultat2[0];
				int nEP = tabResultat2[1];
				if(nbreBienPlace != nBP || nbrePresent != nEP) {
					li.remove();
				}
			}
			return false;
		}
	}
	
	public void afficheCombinaison(String str, Boolean modeD) {
		System.out.println("(Combinaison secrète que l'ordinateur doit trouver : " + str + ")");
		logger.info("(Combinaison secrète que l'ordinateur doit trouver : " + str + ")");
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

