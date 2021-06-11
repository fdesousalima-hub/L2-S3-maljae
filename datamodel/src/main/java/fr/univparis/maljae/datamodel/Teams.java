package fr.univparis.maljae.datamodel;

import java.io.*;
import java.util.*;
import org.json.*;
//import java.util.Collections;

/**
   *This module collects teams.
   *The purpose of this whole class is to create a list of team
  */
public class Teams{ /* The purpose of this whole class is to create a list of team */
    /*Comparator<Team> compareByEmail = (Team o1, Team o2) ->
                               o1.students.get(0).getEmail().compareTo( o2.students.get(0).getEmail() );*/



    /* FIXME: This may be not the right data structure... */
    /**
      *the coolection team in listFiles
      *@see loadFrom(File d)
      *@see Team getTeam(String identifier)
      *@see removeFromExistingTeam(String email)
      *@see Team createTeam(String email)
    */
    private static final ArrayList<Team> teams = new ArrayList<Team>();
    /**
       *This function load the different team and their name and check if the name is valid if so the team is add to the list of team if not we will need to add a exit condition
       *@param d
   */
    public static void loadFrom(File d) throws IOException{
	     /*This function load the different team and their name and check if the name is valid if so the team is add to the list of team if          not we will need to add a exit condition */
	     for(File f : d.listFiles()){
	        if (Team.isValidTeamFileName(f.getName())){
            teams.add (new Team (f));
          }
	     }
    }
    /**
    *method who assign a task to each team based on their preferences and the availability of the task
    */
    public static void assign() {
    	for (Team team: teams) {
    		for (int j = 0; j < team.getPreferences().size(); j++) {
    			switch (Assignment.assignTask(team, team.getPreferences().get(j))) {
    			case 0:
    				System.out.println("The "+team.getIdentifier()+ "get a task");
					j=team.getPreferences().size();
    				break;
				case 1:
					System.out.println("The "+team.getIdentifier()+ " team already has a task");
					j=team.getPreferences().size();
					break;

				case 2:
					System.out.println("The "+team.getPreferences().get(j).getIdentifier()+" task already added");
					break;
				}
    		}
		}
    }

    /**
      *method who check if a team already exist or not
      *@param identifier
      *@return the team if it as been found throw an exception if not
    */
    public static Team getTeam(String identifier)throws IOException{
	     for(Team team : teams){
	        if (team.getIdentifier().equals(identifier)){
            return team;
          }
	     }
       throw new RuntimeException("Team not found !!");
    }

    /**
       *This function sole purpose is to remove a user from one of the existing team to do so the programm will remove the student
        *based on his eamil
        *@param email
     */
    public static boolean removeFromExistingTeam(String email, Team t) throws IOException{
	     for(Team team : teams){
         if(team==t){
           for(Student student : team.getStudents()){
           if(student.getEmail().equals(email)){
             t.removeStudent(email);
             if (t.getStudents().size() == 0){
               deleteTeam(t);}
               else{
                   saveTeam(team);
               }
             return true;
        }else{
          System.out.println("This student doesn't exist !!!");
          return false;
        }
       }
	    }
     }

     return false;

    }

    /**
       *This function is used to put one of the student that got erased from one of the team into another one to do so it will get the student's email and create an other team in this student in it
       *@param email
       *return newTeam
       *              replace the ne team added in the collection
     * @throws InstantiationException
    */
    public static Team createTeam(String teamName,String email, String name , int studentNumber) throws IOException {
	     Team newTeam = new Team(teamName,new Student(email, true, name , studentNumber));
	     teams.add(newTeam);
	     String filename = Configuration.getDataDirectory() + "/" +
	                       newTeam.getIdentifier() + "-team.json";
	     newTeam.saveTo(new File(filename));
	     return newTeam;
    }

    /**
       *Based on the name of this function i can only assume that it save the team into a file that the user need to specifie and so
       * the team enter the database
       *@param team
    */
    public static void saveTeam(Team team) throws IOException{
	     String filename = Configuration.getDataDirectory() + "/" +
	                       team.getIdentifier() + "-team.json";

	     team.saveTo(new File(filename));
    }

    /**
      *method who get the different secrets numbers of the teams
      *@param Team
      *@return int
    */
    private static int SecretNumbers(Team t){
      int res = 0;
      for(Team team2 : teams){
          if(t != team2)
            res += team2.getSecret();
    }
      return res;
    }
    /**
    *method who sort the Teams
    */
    public static void sortTeams(){
      for (int i = 0; i<teams.size(); i++){
        int chiffreAlgo = (i + ((SecretNumbers(teams.get(i))) % ((teams.size()-1) - i + 1)));
        Collections.swap(teams,i,chiffreAlgo);
      }
    }
    /**
    *method who delete a team
    *@param team
    */
    public static void deleteTeam(Team team) throws IOException{
      /* This function is used to delete a Team by removing the ".json" file which correspond to the team */
      teams.remove(team);
      Team.delete(new File(Configuration.getDataDirectory() + "/" +team.getIdentifier() + "-team.json"));
      Team.delete(new File(Configuration.getDataDirectory() + "/token" +team.getIdentifier() + ".json"));
      Team.delete(new File(Configuration.getDataDirectory() + "/" +team.getIdentifier() + "-trace.txt"));}

      /**
      *method who allows you to join a team
      *@param email
      *@param id
      *@param name
      *@param studentNumber
      */
    public static void joinTeam(String email,String id, String name,int studentNumber)throws IOException, InstantiationException{
      Team team = getTeam(id);
      team.addStudent(email,name,studentNumber);
    }

    /**
    *method who sort the students inside of a team
    */
     public static void sort(){
       for(Team team : teams){
         team.sortStudents();

       }
       Collections.sort(teams);
}
  /**
  *method who check if an email is already in a team
  *@param email
  *@return boolean true if the email do exist false if not
  */
     public static boolean exist(String email) {
    	 for(Team t : teams) {
    		 if(t.existInTeam(email)) {
    			 return true;
    		 }
    	 }
    	 return false;
     }
     /**
     *method who display the teams
     */
     public static void display() {
    	 for(Team t : teams) {
    		 System.out.println(t);
    	 }
     }
     /**
     *method who check if a students number already exist in a team
     *@param numEtu
     *@return boolean true if the numEtu already exist false if not
     */
     public static boolean numEtuExist(int numEtu) {
    	 for(Team t : teams) {
    		 if(t.numEtuExistInTeam(numEtu)) {
    			 return true;
    		 }
    	 }
    	 return false;
     }
}
