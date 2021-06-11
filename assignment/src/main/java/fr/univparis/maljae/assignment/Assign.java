package fr.univparis.maljae.assignment;

import java.io.File;
import java.io.IOException;

import fr.univparis.maljae.datamodel.Assignment;
import fr.univparis.maljae.datamodel.Configuration;
import fr.univparis.maljae.datamodel.Teams;

/**
 * main class of the programme
 *
 */
public class Assign {

	public static void main(String[] args) throws IOException{
		about();
		Configuration.setData();
		try {
			File file = Configuration.setConfig();
			loadConfiguration(file);
		}catch (Exception e) {
			System.out.println("Veuillez donner un fichier config.json en argument pour lancer le programme");
		}
		initializeTeams();
		launchAssign();
	}

	/**
	 * allow to know whitch version of the project is running
	 */
	public static void about() {
		System.out.println("maljae server version " + Configuration.version + " running.");
	}

	/**
	 * charge les configuration dans un fichier
	 *
	 * @param configFile
	 */
	public static void loadConfiguration(File file) throws Exception {
		Configuration.loadFrom(file);
	}

	/**
	 * do like a database of the groupes who are already creates
	 */
	public static void initializeTeams() throws IOException {
		Teams.loadFrom(new File(Configuration.getDataDirectory()));
		Assignment.loadFrom(new File("datamodel/src/test/resources/assignment.json"));
	}
	/**
	*method who sort the teams and then the students inside of them and then assign the differents tasks
	*/
	public static void launchAssign() throws IOException {
		Teams.sort();
		Teams.sortTeams();
		Teams.assign();
		Assignment.saveTo(new File("datamodel/src/test/resources/assignment.json"));
	}
}
