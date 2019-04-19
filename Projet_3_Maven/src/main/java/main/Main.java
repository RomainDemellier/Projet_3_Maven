package main;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.swing.JOptionPane;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

//import jdk.internal.jline.internal.Log;
import jeux.abstractClass.Jeu;
import jeux.implementation.Mastermind;
import jeux.implementation.RecherchePlusMoins;

public class Main {

	private static Logger logger = LogManager.getLogger(Main.class);
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println("Début du programme !");
		logger.info("Début du programme");
		final Properties prop = new Properties();
		InputStream input = null;
		int nbreCases = -1;
		int nbreEssais = -1;
		int nbreChiffre = -1;
		Boolean modeDeveloppeur = true;
		
		try {
			
			//On lit le fichier config.properties
			input = new FileInputStream("src/main/resources/config.properties");
			

			// load a properties file
			prop.load(input);
			//On affecte aux trois variables ci-dessous les propriétés qui se
			//trouvent dans le fichier config.properties soient le nombre de cases
			//qu'aura la combinaison, le nombre d'essais et le nombre de chiffres
			//pour le mastermind (entre 4 et 10)
			
			nbreCases = Integer.parseInt(prop.getProperty("jeu.nombreCases"));
			nbreEssais = Integer.parseInt(prop.getProperty("jeu.nombreEssais"));
			nbreChiffre = Integer.parseInt(prop.getProperty("mastermind.nombreChiffre"));
			String modeD = prop.getProperty("developpeur.active");
			if(modeD.equals("true")) {
				modeDeveloppeur = true;
			} else {
				modeDeveloppeur = false;
			}

		} catch (final IOException | NumberFormatException ex) {
			logger.error(ex.toString());
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (final IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		String str = "";
		Boolean continuer = true;
		Boolean config = (nbreCases != -1 && nbreEssais != -1 && nbreChiffre != -1);
		JOptionPane jop = new JOptionPane();
		JOptionPane jop2 = new JOptionPane();
		JOptionPane jop3 = new JOptionPane();
		int nbreChiffres;
		String[] jeux = {"recherche+-","mastermind"};
		String[] modes = {"challenger","defenseur","duel"};

			do {
				Jeu jeu;
				//Boîte de dialogue pour choisir le jeu
				//Résultat dans la variable jeuChoisi
				String jeuChoisi = (String)jop2.showInputDialog(null, "Veuillez choisir le jeu", "Choix du jeu", JOptionPane.QUESTION_MESSAGE, null, jeux, jeux[0]);
				String mode;
				if(jeuChoisi != null) {
					//Si jeuChoisi != null seconde boîte de dialogue pour choisir le mode
					//Résultat dans la variable mode
					mode = (String)jop2.showInputDialog(null, "Veuillez choisir le mode de jeu", "Choix du mode", JOptionPane.QUESTION_MESSAGE, null, modes, modes[0]);
				} else {
					mode = null;
				}

				if(jeuChoisi != null && mode != null) {
					if(jeuChoisi.equals("recherche+-") && config) {
						//Si l'utilisateur a choisi le jeu recherche+-
						//on lance ce jeu
						jeu = new RecherchePlusMoins(mode,nbreCases,nbreEssais,modeDeveloppeur);
					} else {
						if(config) {
							//Sinon on lance le jeu mastermind
							jeu = new Mastermind(mode,nbreCases,nbreEssais,nbreChiffre,modeDeveloppeur);
						}
					}
				}
				//Lorsque une partie est finie ou que l'utilisateur a quitté l'un des deux jeux
				//une boîte de dialogue s'affiche et demande si on jouer à nouveau
				//Résustat dans la variable n
				int n = jop3.showConfirmDialog(null, "Voulez vous jouer à nouveau ?", "Continuer ou non", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if(n == 1) {
					//Si l'utilisateur ne veut pas continuer il a appuyé sur non et n vaut 1
					//on affecte false à continuer et le programme s'arrête
					continuer = false;
				}
			} while(continuer);
		logger.info("Fin du programme");
	}

}

