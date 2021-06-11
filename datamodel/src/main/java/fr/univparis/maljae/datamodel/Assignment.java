package fr.univparis.maljae.datamodel;

//import java.util.regex.*;
//import java.util.concurrent.ThreadLocalRandom;
import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import org.apache.commons.io.FileUtils;
import org.json.*;

/**
  * This module stores tasks assignment.
  */
public class Assignment{

  /**
    *For each team, there must be exactly one task.
    *@see saveTo(File f)
    */
    private static HashMap<Team, Task> team_tasks = new HashMap<Team, Task>();


    /**
      *method who check if the team has a task and the task still available and add trace
      *@param team
      *@param task
      *           take a team and assign a task at this team
    */
    public static int assignTask(Team team, Task task){
	     if(team_tasks.containsKey(team)){
	        return 1;
	     }
	    int count = Collections.frequency(team_tasks.values(), task);
	    if (count>=Configuration.getDefaultNbTeamsPerSubject()) {
	    	return 2;
		}
	     team_tasks.put(team, task);
	     return 0;
    }


    /**
      *@return the team's tasks and it's trace
    */
    public static String show(){
	     String description = "";
	     description += team_tasks.toString() + "\n";
	     return description; //K: returns the team's tasks and it's trace
    }

    /**
      *@param f
      *       this feature allows you to "load" data about teams and tasks to be done.
    */
    public static void loadFrom(File f) throws IOException{
	     JSONObject json = new JSONObject(FileUtils.readFileToString(f, "utf-8"));
	     JSONArray assignment_json = json.getJSONArray("assignment");
	     for(int i = 0; i < assignment_json.length(); i++){
	        JSONArray team_task = assignment_json.getJSONArray(i);
	        Team team = Teams.getTeam(team_task.getString(0));
	        Task task = Configuration.getTask(Integer.parseInt(team_task.getString(1)));
	        assignTask(team, task); //K: For me, this feature allows you to "load" data about teams and tasks to be done.
	     }
    }

    /**
     * method who save every team assign to assignment.json
      *@param f
      *       this feature allows you to save data on a team and a task by saving identifiers, or a value
    */
    public static void saveTo(File f) throws IOException{
	     JSONObject json = new JSONObject();
	     JSONArray assignment_json = new JSONArray();
	     for(HashMap.Entry<Team, Task> assignment : team_tasks.entrySet()){
	        JSONArray team_task = new JSONArray();
	        team_task.put(assignment.getKey().getIdentifier());
	        team_task.put(assignment.getValue().getIdentifier());
	        assignment_json.put(team_task);
	     }
	     json.put("assignment", assignment_json);
	     FileWriter fw = new FileWriter(f);
	     fw.write(json.toString(2));
	     fw.close(); //K: For me, this feature allows you to save data on a team and a task by saving identifiers, or a value
    }
    /**
     * method who search a task in a certain file
     *@param t
     * this feature allows you to design a team in which you want to find the task
     *@return the task that was found
    */

    public static Task searchTaskInFile(Team t) throws IOException {
	    File f =new File("datamodel/src/test/resources/assignment.json");
	    JSONObject json = new JSONObject(FileUtils.readFileToString(f, "utf-8"));
	    JSONArray assignment_json = json.getJSONArray("assignment");
	    for(int i = 0; i < assignment_json.length(); i++){
	        JSONArray team_task = assignment_json.getJSONArray(i);
	        Team team = Teams.getTeam(team_task.getString(0));
	        if (t == team) {
		        return Configuration.getTask(team_task.getInt(1));
			}
	     }
	    throw new RuntimeException("Team is not on assignment.json");
    }
}
