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
	public abstract Boolean jouerPlusMoins(Boolean modeD);
	/**
	 * méthode abstraite définie dans les classes Personne et Ordinateur
	 * @return booléen vrai si on a trouvé la combinaison ou si on
	 * veut arrêter
	 */
	public abstract Boolean jouerMastermind(Boolean modeD);
	
	public abstract void afficheCombinaison(String str, Boolean modeD);
	
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
	
	protected int[] bienPlace(String combinaison, String str) {
		int tabResultat[] = new int[2];
		this.resteProposition = "";
		this.resteCombinaison = "";
		int compteur = 0;
		
		//On commence par calculer le nombre de "caractères" bien placés
		for(int i = 0;i < combinaison.length();i++) {
			//On parcourt la combinaison secrète
			if(combinaison.charAt(i) == str.charAt(i)) {
				//Si combinaison.charAt(i) == str.chatAt(i) str étant la proposition
				//de l'ordinateur, on incrémente compteur
				compteur++;
			} else {
				//On stocke dans resteProposition et dans resteCombinaison
				//les autres caractères. Ces deux chaînes nous permettront
				//de calculer le nombre de caractères présents.
				this.resteProposition += str.charAt(i);
				this.resteCombinaison += combinaison.charAt(i);
			}
		}
		tabResultat[0] = compteur;
		
		compteur = 0;
		
		//On calcule maintenant le nombre de "caractères" présents
		//On parcourt restePropo
		for(int i = 0;i < this.resteProposition.length();i++) {
			
			//On teste la présence du caractère restePropo.charAt(i)
			//dans resteCombi
			int presenceIndex = this.resteCombinaison.indexOf(this.resteProposition.charAt(i));
			if(presenceIndex != -1) {
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
		tabResultat[1] = compteur;
		return tabResultat;
	}
	
	/**
	 * Méthode qui sert à afficher le nombre de bien placés et de présents dans le 
	 * jeu mastermind de la proposition par rapport à la combinaison secrète
	 * @param nbreBP nombre de bien placés
	 * @param nbreEP nombre de présents
	 * @return une chaîne de caractères indiquant le nombre de bien placés et
	 * 		   le nombre de présents dans le jeu mastermind
	 */
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
