package fr.univparis.maljae.webserver;

import fr.univparis.maljae.datamodel.Assignment;
import fr.univparis.maljae.datamodel.Configuration;
import fr.univparis.maljae.datamodel.Student;
import fr.univparis.maljae.datamodel.Teams;
import io.javalin.*;
import java.io.*;

/**
 * A webserver
 *
 */

/*C-allow to know whitch version of the project is running*/
public class App{
    /**
      *allow to know whitch version of the project is running
    */
    public static void about(){
        System.out.println("maljae server version "
			                     + Configuration.version
			                     + " running.");
    }

    /**
      *allow to configurate the preferences of the project
      *@return app
      *          lance le projet
      */
    public static Javalin launch(){
	     Javalin app = Javalin.create(config -> { config.addStaticFiles("public/");
		                                            config.enableWebjars();}).start(8080);
       app.error(404, ctx -> {
         ctx.redirect("./");
       });
       return app;
    }

    /**
      *charge les configuration dans un fichier
      *@param configFile
    */
    public static void loadConfiguration(File f) throws Exception{
	     Configuration.loadFrom(f);
    }

    /**
      *do like a database of the groupes who are already creates
    */
    public static void initialize() throws IOException{
    	 Notifier.sendAllMailKeep();
	     new File(Configuration.getDataDirectory()).mkdirs();
	     Teams.loadFrom(new File(Configuration.getDataDirectory()));
    }

    /**
      *like other main it executes the previous functions
    */
    public static void main(String[] args) throws Exception{
	     about();
	     Configuration.setData();
	     File file = Configuration.setConfig();
	     loadConfiguration(file);
	     initialize();
	     Javalin app = launch();
	     TeamController.install(app);
    }


}
