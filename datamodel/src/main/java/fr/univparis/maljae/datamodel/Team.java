package fr.univparis.maljae.datamodel;

import java.util.StringTokenizer;
import java.util.regex.*;
import java.util.concurrent.ThreadLocalRandom;
import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import org.apache.commons.io.FileUtils;
import org.json.*;


/**
  * The team of students.
  *The purpose of this class is to create team that will then be put in the teams list of the Teams.java's programm
  */
public class Team implements Comparable<Team>{ /* The purpose of this class is to create team that will then be put in the teams list of the Teams.java's programm */
 /**
  *method who compare the team's o first student's email to the current student
    @param o
    @return an int
  */
	public int compareTo(Team o){
	      Team t = (Team)o;
	      String[] valuesThis = getFirstStudent().getEmail().split("@");
	      String signatureThis = valuesThis[0];
	      String[] valuesO = t.getFirstStudent().getEmail().split("@");
	      String signatureO = valuesO[0];
	      return signatureThis.compareTo(signatureO);
	    }

/**
  *the identifier of the teams
  *@see getIdentifier()
  *@see Team(Student creator)
  *@see Team(File f)
  *@see preferencesToString()
  *@see updatePreferencesFromString(String s)
  *@see toString()
  */
    private String identifier;
    /**
      *@return preferences
      *                Return the list of user's preferences
    */
    public String getIdentifier(){ return identifier; }

    /**
      *liste chaine des preferences
      *@see getPreferences()
      *@see Team(Student creator)
      *@see Team(File f)
      *@see saveTo(File f)
      *@see preferencesToString()
      *@see updatePreferencesFromString(String s)
      *@see toString()
    */
    private ArrayList<Task> preferences;
    public ArrayList<Task> getPreferences(){ return preferences; }

    /**
     *members of the team in a list
     *@see Team(Student creator)
     *@see Team(File f)
     *@see saveTo(File f)
     *@see studentsToString()
     *@see updateStudentsFromString(String who, String s)
     *@see toString()
     *@see removeStudent(String email)
    */
    private ArrayList<Student> students;
    public ArrayList<Student> getStudents(){ return students; }

    public Student getFirstStudent() {
    	return this.students.get(0);
    }

		public Student getSecondStudents(){
			return this.students.get(1);
		}

    /**
      *secret number choose by each members
      *@see getSecret()
      *@see Team(Student creator)
      *@see Team(File f)
      *@see updateSecretFromString(String s)
      *@see toString()
      *@see saveTo(File f)
    */
    private Integer secret;
    /**
      *getter of secret
      *@return secret
      *             return the secret number of the member
    */
    public Integer getSecret(){ return secret; }
    /**
      * This function is used so that the students can input their secret number they need to chose in order to get assigned to a project
    */

    public void updateSecretFromString(String s){
	     secret = Integer.parseInt (s);
    }

    /**
       *This function give the different information on the team it create a random number that will be used for the team identification          get the preferences of the team so that the correct teacher can be assigned to that team and check if the maximum amount of
       *student in the team is not reached in our case not higher than 7
       *@param creator
    */
    public Team(String teamName ,Student creator) {
       /* This function give the different information on the team it create a random number that will be used for the team identification          get the preferences of the team so that the correct teacher can be assigned to that team and check if the maximum amount of
          student in the team is not reached in our case not higher than 7 */

	     identifier = teamName;
	     preferences = new ArrayList<Task>(Configuration.getTasks());
       students = new ArrayList<Student>(Configuration.getMaxNbUsersPerTeam());
	     students.add(creator);
	     secret = ThreadLocalRandom.current().nextInt(10, 100);
    }
    /**
      *this function is an equivalence to Team but here we used a file data
      *@param File f
    */
    public Team(File f) throws IOException{ /* Due to the ugliness of those following lines i'm kinda lost and though don't exactly understand                                           it but it looks like this function check if the team identifier is not equals to any other team                                           already existent to avoid having to team with the same identifier */
	     JSONObject json = new JSONObject(FileUtils.readFileToString(f, "utf-8"));
	     identifier = json.getString ("identifier");
	     if(!f.getName().equals(identifier + "-team.json")){
	        throw new RuntimeException ("Inconsistency in the data model: " + f.getName());
	     }
	     secret = json.getInt("secret");

	     preferences = new ArrayList<Task>(json.getJSONArray("preferences").length());
	     for(int i = 0; i < json.getJSONArray("preferences").length(); i++){
	        int pid = json.getJSONArray("preferences").getInt(i);
	        preferences.add(i, Configuration.getTask(pid));
	     }

	     students = new ArrayList<Student>(json.getJSONArray("students").length());
	     for(int i = 0; i < json.getJSONArray("students").length(); i++)
	        students.add(i, new Student(json.getJSONArray("students").getJSONObject(i)));
       }

    /**
       *This following function is used to save the team including the identifier,the students and the secret
       *@param File f
    */
    public void saveTo(File f) throws IOException{
	      JSONObject json = new JSONObject();

	      JSONArray preferences_json = new JSONArray();
	      for (int i = 0; i < preferences.size(); i++) {
	         if(preferences.get(i) != null){
		           preferences_json.put(preferences.get(i).getIdentifier());
	         }
	      }

	      JSONArray students_json = new JSONArray();
	      for(int i = 0; i < students.size(); i++){
	         students_json.put(students.get(i).toJSON());
	      }

        json.put("identifier", identifier);
        json.put("secret", secret);
        json.put("preferences", preferences_json);
	      json.put("students", students_json);

	      FileWriter fw = new FileWriter(f);
	      fw.write(json.toString(2));
	      fw.close();
    }

    /**
       *All the following function including the updatePreferencesFromString,studentsToString and updateStudentsFromString is
       *apparently used to convert the students name and the preferences of the team into Strings so that we can use them in the
       *Teams.java file
       *@return result
       *             the preferences of the user
     */
    public String[] preferencesToString(){
	     /* All the following function including the updatePreferencesFromString,
       studentsToString and updateStudentsFromString is
	        apparently used to convert the students name and the preferences of
          the team into Strings so that we can use them in the
          Teams.java file */
	     String[] result = new String[preferences.size()];
	     for(int i = 0; i < preferences.size(); i++){
	        result[i]= preferences.get(i).getTitle();
	     }
	     return result;
    }

    /**
      *Takes a String s that gives the new Preferences of the team
      *@param s
      */
    public void updatePreferencesFromString(String[] s){
	     ArrayList<Task> newPreferences = new ArrayList<Task>();
	     for(int i = 0; i < s.length; i++){
         if (Configuration.verifieTask(Integer.parseInt(s[i]))){
					 for(Task t:  Configuration.getTasks()){
						 if(Configuration.getTask(Integer.parseInt(s[i])).equals(t)) {
						 		newPreferences.add(Configuration.getTask(Integer.parseInt(s[i])));
						 		break;
						 }else {
						 System.out.println("The task is already taken");}
					 }
         }//we check with a function verifieTask that the task do exist and if it exist we do newPreferences = task
	     }
	     this.preferences = newPreferences;
    }

    /**
      *Prints the description of a Student
      *@return result
      *             the student's description
    */
    public String[] studentsToString(){
	     String result[] = new String[students.size()];
	     for(int i = 0; i < students.size(); i++){
	        result[i] = students.get(i).toString();
	     }
	     return result;
    }


    //We will not be using the function updateStudentsFromString because a student can only modify their preferences and secret number but thats it
    //therefore this function become useless

    /**
     *This function is describing the team by printing out his description, preferences, students in that team and secret number
     *@return description
     *                  return the information about student task preference and the identifier
     */
    public String toString(){
	     /* This function is describing the team by printing out his description,
       preferences, students in that team and secret number */
	     String description = identifier + "\n";
	     description += preferencesToString() + "\n";
	     description += studentsToString() + "\n";
	     description += "secret:" + secret;
	     return description;
    }

    /**
       *This function check if the Team's name is valid this function is used in the Teams.java's file
       *@return m.find()
       *               return if yes or not the name of the team is available
       */
    public static boolean isValidTeamFileName(String fname){
	     /* This function check if the Team's name is valid this function is used
       in the Teams.java's file */
	     Pattern p = Pattern.compile(".*-team.json");
	     Matcher m = p.matcher(fname);
	     return m.find();
    }

    /**
       *This function search in the list of students the one that we want to remove from a team this function is used in the teams.java file
   */
    public void removeStudent(String email){
	     /* This function search in the list of students the one that we want to
       remove from a team this function is used in the teams.jav file */
	     Student found = null;
	     for(Student student : students){
	        if(student.getEmail().equals(email)){
		          found = student;
		          break;
	       }
	    }
	    if(found != null){
        students.remove (found);
      }
    }
		/**
		*method who delete the file who contain data about the team to delete
		@param f
		*/
    public static void delete(File f){
      f.delete();}

			/**
			*method who add a student to a team
			@param email
			@param name
			@param studentNumber
			*/
    public void addStudent(String email, String name, int studentNumber) throws IOException, InstantiationException{
      this.students.add(new Student(email,false, name , studentNumber));
      this.saveTo(new File(Configuration.getDataDirectory() + "/"+ this.getIdentifier()+ "-team.json"));
    }

		/**
		*method who sort the students of the team
		*/
     public void sortStudents() {
         Collections.sort(students);
     }

		 /**
		 *method who check if the input email already exist in a team
		 @param email
		 @return a boolean true if the email already exist false if not
		 */
     public boolean existInTeam(String email) {
    	 for(Student s : students) {
    		 if(s.getEmail().equals(email)) {
    			 return true;
    		 }
    	 }
    	 return false;
     }
		 /**
		 *method who check if the input students number already exist in a team
		 @param numEtu
		 @return a boolean true if the numEtu already exist false if not
		 */

     public boolean numEtuExistInTeam(int numEtu) {
    	 for(Student s : students) {
    		 if(s.getStudentNumber() == numEtu) {
    			 return true;
    		 }
    	 }
    	 return false;
     }

}
