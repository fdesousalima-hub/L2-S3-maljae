package fr.univparis.maljae.datamodel;

import java.io.File;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import org.apache.commons.io.FileUtils;
import org.json.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
  *This module gives access to the configuration of the maljae instance.
 */
public class Configuration{
    /**
    *Version. We following semantic versioning conventions.
  */
  public static final String version = "0.1";

    /**
      *Data directory. This is the place where we will put data files.
      *@see getDataDirectory()
    */
    private static String dataDirectory = "./maljae-data";

    public static void setData(){
      Scanner sc = new Scanner(System.in);
      String path = " ";
      File f;
      while(path.equals(" ")){
        System.out.println("Enter a dataDirectory : ");
        try{
          path = sc.nextLine();
        f = new File(path);
        if (!f.exists()) f.mkdirs();}
        catch(Exception e){sc = new Scanner(System.in);}}
      dataDirectory = path;}

    public static File setConfig(){
      Scanner sc=new Scanner(System.in);
      String path = " ";
      File f = new File(path);
      while (true){
        System.out.println("Enter a config File : ");
        try{
          path = sc.nextLine();
          f = new File(path);
          if(f.isFile()){
            loadFrom(f);    // this line is made to verify that the file is validated by throwing an exception if this is not the case
            return f;}
          else
            System.out.println("Is not a file");}
        catch(Exception e){sc = new Scanner(System.in);}}}

    public static String getDataDirectory(){
      return dataDirectory;
    }

    private static String traceDirectory = "./webserver/src/main/resources/public/Trace/";

    public static String getTraceDirectory(){
      return traceDirectory;
    }

    /**
      *Dates are supposed to be written with the following format.
      */
    private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    /**
      *Opening date. Before this date, nobody can create a team.
      *@see showDataRange()
      *@see loadFrom(File f)
      */
    private static Date openingDate = null;

    /**
      *Closing date. After this date, nobody can change team-related data.
      *@see showDataRange()
      *@see loadFrom(File f)
      */
    private static Date closingDate = null;

    /**
      *Display the date range
      *@return df.format(openingDate) + " - " + df.format(closingDate)
      *                                                              return the time for choose and change a team
      */
    public static String showDateRange(){
	     return df.format(openingDate) + " - " + df.format(closingDate);
    }

    /**
      *Minimal number of users per team.
      *@see getMinNbUsersPerTeam()
      */
    private static int minNbUsersPerTeam = -1;
    public static int getMinNbUsersPerTeam(){ return minNbUsersPerTeam; }

    /** Maximal number of users per team. Must be greater than minNbUsersPerTeam. */
    private static int maxNbUsersPerTeam = -1;
    public static int getMaxNbUsersPerTeam(){ return maxNbUsersPerTeam; }

    /** Default number of teams per subject. */
    private static int defaultNbTeamsPerSubject = -1;
    public static int getDefaultNbTeamsPerSubject(){ return defaultNbTeamsPerSubject; }

    /**
     *Task descriptions.
     *@see getTask(String identifier)
     *@see getTasks()
     */
    private static ArrayList<Task> tasks = new ArrayList<Task>();
    /**
       *@return tasks
       *            return all the tasks
    */

    public static ArrayList<Task> getTasks(){
	     return tasks;
    }
    /**
      *@param identifier
      *                 identifier of the task choose
      *@return this.tasks.get(i)
      *            if the given ID is equal to the ID of the given array, return the corresponding task in the array.
    */
    public static Task getTask(int identifier){
	     for(int i = 0; i < tasks.size(); i++){
	        int tid = tasks.get(i).getIdentifier();
	        if(tid == identifier) return tasks.get(i); //K: if the given ID is equal to the ID of the given array, return the corresponding array.
	     }
	     return null;
    }
    /**
      *Method who return the names of the tasks in an array
      @return the names of the tasks
    */
    public static String[] getTasksName() {
    	String res[]= new String[tasks.size()];
    	for(int i = 0; i < tasks.size(); i++){
	        res[i] = tasks.get(i).getTitle();
	     }
	     return res;
    }
    /**
    *method who check if the task exist
    @param t
    @return a boolean true if the task was found false if not
    */
    public static boolean verifieTask(int t){
    	for(Task task : tasks){
    		if(task.getIdentifier()==t)
    			return true;
    	}
    	return false;
    }

    /**
       *@return tasks
       *            return all the tasks
    */
    /**
      *Load configuration file in memory.
      *@param f
      *       the file configuration acctually empty
    */
    /* FIXME: This function is badly written! */
    public static void loadFrom(File f) throws Exception{
	     JSONObject json = new JSONObject(FileUtils.readFileToString (f, "utf-8"));

	     openingDate = df.parse(json.getString("opening_date"));
	     closingDate = df.parse(json.getString("closing_date"));

	     minNbUsersPerTeam = json.getJSONObject("nb_users_per_team").getInt("min");
	     maxNbUsersPerTeam = json.getJSONObject("nb_users_per_team").getInt("max");

	     defaultNbTeamsPerSubject = json.getJSONObject("nb_teams_per_subject").getInt("default");

	     JSONArray json_tasks = json.getJSONArray("tasks");
	     for(int index = 0; index < json_tasks.length(); index++){
	        tasks.add(new Task(json_tasks.getJSONObject(index)));
	     }
    }
}
