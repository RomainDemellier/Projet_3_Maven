package jeux.abstractClass;

import javax.swing.JOptionPane;

import joueurs.abstractClass.Joueur;

/**
 * Classe abstraite Jeu 
 * Les classes RecherchePlusMoins et Mastermind vont hériter de Jeu
 * @author romaindemellier
 *
 */

public abstract class Jeu {

	/**
	 * Chaque jeu a un joueur.
	 */
	protected Joueur joueur1;
	
	/**
	 * Deuxième joueur dans le mode duel
	 */
	protected Joueur joueur2;
	/**
	 * Dans le mode duel la variable aQuiLeTour permet de savoir qui doit jouer.
	 * Elle est initialisé à "personne" (C'est un joueur "humain" qui commence)
	 */
	protected String aQuiLeTour = "personne";
	/**
	 * Le nombre de cases de la combinaison.
	 */
	protected int nbreCases;
	/**
	 * Le nombre d'essais (de tours) qu'il est possible de faire.
	 */
	protected int nbreEssai;
	
	/**
	 * Le mode du jeu
	 */
	
	protected String mode;
	
	/**
	 * modeDeveloppeur vaut true si le mode deéveloppeur est actif false sinon
	 */
	
	protected Boolean modeDeveloppeur;

	/**
	 * La méthode genereCombinaison permet de créer une combianison
	 * de façon aléatoire
	 * 
	 * @return la combinaison
	 * 	
	 */
	protected  String genereCombinaison() {
		int n = this.nbreCases;
		int chiffreAleatoire;
		String str = "";
		for(int i = 0;i < n;i++) {
			//chiffreAleatoire va contenir un entier compris entre 0 et 9
			chiffreAleatoire = (int)(Math.random()*10);
			//On convertit le chiffre en caractère
			//et on l'ajoute à la combinaison
			str += conversion(chiffreAleatoire);
		}
		//afficheCombinaison(str,'p');
		return str;
	}
	
	
	/**
	 * La méthode genereCombinaison(int nbreChiffre) permet de créer une combinaison
	 * de façcon aléatoire pour le jeu mastermind
	 * 
	 * @param nbreChiffre 
	 * 				paramètre qui indique le nombre de chiffres utilisables pour le mastermind 
	 * @return la combinaison
	 */
	
	protected  String genereCombinaison(int nbreChiffre) {
		int n = this.nbreCases;
		int chiffreAleatoire;
		String str = "";
		for(int i = 0;i < n;i++) {
			//chiffreAleatoire va contenir un entier compris entre 0 et nbreChiffre
			chiffreAleatoire = (int)(Math.random()*nbreChiffre);
			//On convertit le chiffre en caractère
			//et on l'ajoute à la combinaison
			str += conversion(chiffreAleatoire);
		}
		//afficheCombinaison(str,'p');
		return str;
	}
	
	/**
	 * Affiche le combinaison secrète
	 * @param str la combinaison secrète 
	 */
	
	/*public void afficheCombinaison(String str, char c, Boolean modeD) {
		if(modeD) {
			if(c == 'p') {
				System.out.println("(Combinaison secrète que vous devez trouver : " + str + ")");
			} else {
				System.out.println("(Combinaison secrète que l'ordinateur doit trouver : " + str + ")");
			}
		} else {
			String combiCache = "";
			for(int i = 0;i < this.nbreCases;i++) {
				combiCache += '*';
			}
			if(c == 'p') {
				System.out.println("(Combinaison secrète que vous devez trouver : " + combiCache + ")");
			} else {
				System.out.println("(Combinaison secrète que l'ordinateur doit trouver : " + combiCache + ")");
			}
		}
	}*/
	
	public static Boolean stringComposeChiffres(String str) {
		
		int longueur = str.length();
		for(int i = 0;i < longueur;i++) {
			if(!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	
	public static Boolean verifieComprisEntre(String str, int nbreChiffre) {
		int longueur = str.length();
		char c;
		
		for(int i = 0;i < longueur;i++) {
			c = str.charAt(i);
			if(Jeu.conversion(c) >= nbreChiffre) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Permet à l'utilisateur de choisir la combinaison secrète
	 * dans le mode défenseur
	 * 
	 * @return la combinaison saisi par l'utilisateur
	 */
	
	protected String chooseCombi() {
		int n = this.nbreCases;
		String str = "";
		Boolean arreter = false;
		//Boolean continuer = true;
		JOptionPane jop = new JOptionPane();
		//Tant que l'utilisateur n'a pas saisi une combinaison de
		//chiffres de nbreCases
 		do {
			str = jop.showInputDialog(null, "Veuillez choisir la combinaison de " + n + " chiffres que l'ordinateur devra trouver");
			if(str == null) {
				return "NULL";
			}
			
			//On teste la longueur de la chaîne saisie et
			//si elle est composée uniquement de chiffres
			if(str.length() != n || !Jeu.stringComposeChiffres(str)) {
				jop.showMessageDialog(null, "Vous n'avez pas saisi une combinaison de " + n + " chiffres", "Attention", JOptionPane.WARNING_MESSAGE);
				continue;
			} else {
				arreter = true;
			}
		} while(!arreter);
		//System.out.println("str : " + str);
		//afficheCombinaison(str,'o');
		return str;
	}
	
	/**
	 * Permet à l'utilisateur de choisir la combinaison secrète
	 * dans le mode défenseur pour le jeu mastermind
	 * @param nbreChiffre
	 * 				paramètre qui indique le nombre de chiffres utilisables pour le mastermind 
	 * @return la combinaison saisie par l'utilisateur
	 */
	protected String chooseCombi(int nbreChiffre) {
		int n = this.nbreCases;
		String str = "";
		Boolean arreter = false;
		//Boolean continuer = true;
		JOptionPane jop = new JOptionPane();
		//Tant que l'utilisateur n'a pas saisi une combinaison de
		//chiffres de nbreChiffre
 		do {
			str = jop.showInputDialog(null, "Veuillez choisir pour l'ordinateur une combinaison de " + n + " chiffres compris entre 0 et " + (nbreChiffre-1));
			if(str == null) {
				return "NULL";
			}
			
			//On teste la longueur de la chaîne saisie et
			//si elle est composée uniquement de chiffres
			if(str.length() != n || !Jeu.stringComposeChiffres(str) || !verifieComprisEntre(str, nbreChiffre)) {
				jop.showMessageDialog(null, "Vous n'avez pas saisi une combinaison de " + n + " chiffres compris entre 0 et " + (nbreChiffre-1), "Attention", JOptionPane.WARNING_MESSAGE);
				continue;
			} else {
				arreter = true;
			}
		} while(!arreter);
		//System.out.println("str : " + str);
		//afficheCombinaison(str,'o');
		return str;
	}
	
	/**
	 * Méthode static qui convertit un entier en caractère
	 * @param q entier à convertir en caractère
	 * @return le caractère correspondant à q 
	 */
	
	public static char conversion(int q) {
		
		switch(q) {
		
			case 0:
				return '0';
			case 1:
				return '1';
			case 2:
				return '2';
			case 3:
				return '3';
			case 4:
				return '4';
			case 5:
				return '5';
			case 6:
				return '6';
			case 7:
				return '7';
			case 8:
				return '8';
			case 9:
				return '9';
			default:
				return 'U';
			
		}
	}
	
	/**
	 * Méthode static qui convertit un caractère en entier
	 * @param c caractère à convertir en entier
	 * @return entier correspondant à c
	 */
	
	public static int conversion(char c) {
		
		switch(c) {
		
			case '0':
				return 0;
			case '1':
				return 1;
			case '2':
				return 2;
			case '3':
				return 3;
			case '4':
				return 4;
			case '5':
				return 5;
			case '6':
				return 6;
			case '7':
				return 7;
			case '8':
				return 8;
			case '9':
				return 9;
			default:
				return -1;
			
		}
}
}
