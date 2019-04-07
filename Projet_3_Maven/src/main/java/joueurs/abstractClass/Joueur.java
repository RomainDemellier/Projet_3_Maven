package joueurs.abstractClass;

import jeux.abstractClass.Jeu;

public abstract class Joueur {

	/**
	 * Entier représentant le nombre de cases de la combinaison
	 */
	protected int n;
	/**
	 * String représentant la combinaison à trouver
	 */
	protected String combinaison;
	
	/**
	 * String qui va servir à stocker les caractères de la proposition
	 * qui ne sont pas bien placés, initialisé à ""
	 */
	protected String resteProposition;
	
	/**
	 * String qui va servir à stocker les caractères de la combinaison
	 * qui n'ont pas été trouvés
	 */
	protected String resteCombinaison;

	/**
	 * méthode abstraite définie dans les classes Personne et Ordinateur
	 * @return booléen vrai si on a trouvé la combinaison ou si on
	 * veut arrêter
	 */
	public abstract Boolean jouerPlusMoins();
	/**
	 * méthode abstraite définie dans les classes Personne et Ordinateur
	 * @return booléen vrai si on a trouvé la combinaison ou si on
	 * veut arrêter
	 */
	public abstract Boolean jouerMastermind();
	
	/**
	 * méthode qui va comparer la chaîne saisie par l' utilisateur ou
	 * générée par l'ordinateur selon le mode de jeu avec la combinaison secrète
	 * @param str chaîne saisie ou générée
	 * @return String composée de '+', '-', '='
	 */
	protected String comparaison(String str) {
		//res va contenir le résultat; c'est lui qui sera renvoyé; 
		//initialisé à une chaîne vide
		String res = "";
		for(int i = 0;i < this.n;i++) {
			//Conversion de combinaison.charAt(i) en entier
			int chiffreCombi = Jeu.conversion(this.combinaison.charAt(i));
			//Conversion de str.charAt(i) en entier
			int chiffreSaisi = Jeu.conversion(str.charAt(i));
			if(chiffreCombi > chiffreSaisi) {
				//Si le chiffre de la combinaison > à celui saisi
				//alors on ajoute "+" à res
				res += "+";
			} else if (chiffreCombi < chiffreSaisi) {
				//Si le chiffre de la combinaison < à celui saisi
				//alors on ajoute "-" à res
				res += "-";
			} else {
				//Si ils sont égaux on ajoute "=" à res 
				res += "=";
			}
		}
		return res;
	}
	
	/**
	 * 
	 */
	
	protected int bienPlace(String str) {
		
		String combi = this.combinaison;
		this.resteProposition = "";
		this.resteCombinaison = "";
		
		//cette variable va contenir les caractères de la proposition
		//qui ne sont pas bien placés
		//String resteProposition = "";
		
		//cette variable va contenir les caractères de la combinaison 
		//secrète qui n'ont pas été trouvés
		//String resteCombinaison = "";
		
		//compteur va nous indiquer le nombre de caractères bien placés
		int compteur = 0;
		int l = str.length();
		String message = "";
		
		for(int i = 0;i < l;i++) {
			if(str.charAt(i) == combi.charAt(i)) {
				//Si les deux caractères sont égaux on incrémente compteur
				compteur++;
			} else {
				//Si les deux caractères sont différents on les place
				//respectivement dans resteProposition et dans resteCombinaison
				resteProposition += str.charAt(i);
				resteCombinaison += combi.charAt(i);
			}
		}
		return compteur;
	}
	
	protected int bienPlace(String combinaison, String str) {
		this.resteProposition = "";
		this.resteCombinaison = "";
		int compteur = 0;
		
		for(int i = 0;i < combinaison.length();i++) {
			if(combinaison.charAt(i) == str.charAt(i)) {
				compteur++;
			} else {
				this.resteProposition += str.charAt(i);
				this.resteCombinaison += combinaison.charAt(i);
			}
		}
		return compteur;
	}
	
	/**
	 * Méthode qui permet de connaître le nombre de caractères présents
	 * @param restePropo est la proposition sans les caractères présents
	 * @param resteCombi est la combinaison secrète les caractères trouvés
	 * @param message contient le message indiquant le nombre de carctères
	 * 		  présents
	 * @return un message qui va contenir le message de la méthode bienPlace
	 * 	 	   plus le nombre de caractères bien placés	
	 */
	
	protected int estPresent() {
		
		
		//compteur va nous indiquer le nombre de caractères présents
		int compteur = 0;
		
		//On parcourt restePropo
		for(int i = 0;i < this.resteProposition.length();i++) {
			
			//On teste la présence du caractère restePropo.charAt(i)
			//dans resteCombi
			int presenceIndex = this.resteCombinaison.indexOf(this.resteProposition.charAt(i));
			if(presenceIndex != -1) {
				//System.out.println("Presence à l'index : " + presenceIndex);
				//Si le caractère est présent on incrémente compteur
				compteur++;
				//Puis on retire ce caractère de resteCombi
				//On distingue deux cas
				//Soit le caractère est au bout de la chaîne
				//Soit il n'est pas en bout de chaîne
				if(presenceIndex == this.resteCombinaison.length() - 1) {
					this.resteCombinaison = this.resteCombinaison.substring(0, this.resteCombinaison.length() - 1);
				} else {
					this.resteCombinaison = this.resteCombinaison.substring(0, presenceIndex) +
								 this.resteCombinaison.substring(presenceIndex + 1, this.resteCombinaison.length());
				}
			}
		}
		
		return compteur;
	}
	
	public String resultatMastermind(int nbreBP, int nbreEP) {
		String resultat = "";
		resultat += nbreBP;
		if(nbreBP > 1 || nbreBP == 0) {
			resultat += " sont bien placés , ";
		} else {
			resultat += " est bien placé , ";
		}
		resultat += nbreEP;
		if(nbreEP > 1 || nbreEP == 0) {
			resultat += " sont présents.";
		} else {
			resultat += " est présent.";
		}
		return resultat;
	}

}
