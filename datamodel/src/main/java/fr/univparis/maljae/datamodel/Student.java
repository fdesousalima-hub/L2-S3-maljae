package fr.univparis.maljae.datamodel;

import org.json.*;
import java.util.*;
import java.lang.*;
import java.io.*;

public class Student implements Comparable<Student> {

  /**
  *method who compare the student's o email to the current student
    @param o
    @return an int
  */

    public int compareTo(Student o){
      Student s = (Student)o;
      String[] valuesThis = email.split("@");
      String signatureThis = valuesThis[0];
      String[] valuesO = s.email.split("@");
      String signatureO = valuesO[0];
      return signatureThis.compareTo(signatureO);
    }

    /**
     *email of the student
     *@see Student(JSONObject json)
     *@see Student(String email0, Boolean confirmed0, String name0, int studentNumber0)
     *@see getEmail()
     *@see String toString()
     *@see Student fromString(String s)
     *@see toJSON()
   */
    private String  email;
    /**
     *confirmed the account creation
     *@see Student(JSONObject json)
     *@see Student(String email0, Boolean confirmed0, String name0, int studentNumber0)
     *@see getConfirmed()
     *@see setConfirmed(Boolean b)
     *@see String toString()
     *@see Student fromString(String s)
     *@see toJSON()
   */
    private Boolean confirmed;
    /**
     *name of the student
     *@see Student(JSONObject json)
     *@see Student(String email0, Boolean confirmed0, String name0, int studentNumber0)
     *@see getName()
     *@see String toString()
     *@see Student fromString(String s)
     *@see toJSON()
 */
    private String name;
    /**
        *student number of the student
        *@see Student(JSONObject json)
        *@see Student(String email0, Boolean confirmed0, String name0, int studentNumber0)
        *@see getStudentNumber()
        *@see String toString()
        *@see Student fromString(String s)
        *@see toJSON()
    */
    private int studentNumber;

    /**
    * implement a file with the student information
      *@param json
      *          the file data for the new user
    */
    Student(JSONObject json){ //K: We create data files, with an email and a boolean "confirmed" to verify that an email is an email.
    	email = json.getString("email");
	    confirmed = json.getBoolean("confirmed");
	    name = json.getString("name");
	    studentNumber = json.getInt("studentNumber");
    }

    /**
     *@param email0
     *          the email for the new user
     *@param confirmed0
     *          the validation of the mail for the new user
     *@param name0
     *          the name of the new user
     *@param studentNumber0
     *          the student number of the new user
   */
    public Student(String email0, Boolean confirmed0, String name0, int studentNumber0) {
    	email = email0;
	     confirmed = confirmed0;
	     name = name0;
	     studentNumber = studentNumber0;
    }

    /**
      *@return email
      *            return the mail of the student
    */
    public String getEmail(){
	     return email;
    }

    /**
      *@return confirmed
      *            return the validation of the mail of the student
    */
    public Boolean getConfirmed(){
	     return confirmed;
    }
    /**
     *@return name
     *           returns the name of the student
   */
   public String getName(){
     return name;
   }

   /**
     *@return studentNumber
     *           returns the student number
   */
   public int getStudentNumber(){
     return studentNumber;
   }


    /**
      *@param b
      *       the new validation of the mail of the student
    */
    public void setConfirmed(Boolean b){
	     confirmed = b;
    }

    /**
          *@return email + "/" + confirmed
          *                              return the concatanation of the student's email and his validation
        */
    public String toString(){
	     return name + "(Email :"+email +", Student number :"+ studentNumber+")";
    }

    /**
      *@param s
      *       This function allows to create a Student from a String s
      *@return new Student(fields[0], Boolean.parseBoolean(fields[1]))
      *                                                               The element returned is a new Student with email = fields[O] (a String containing the email adress) and
      *                                                               confirmed = the boolean given by Boolean.parseBoolean depending on the String in fields[1]
      *                                                               (fields[1] is a String and Boolean.parseBoolean convert the string to true or false)
     * @throws InstantiationException
    */
    public static Student fromString(String s) throws InstantiationException{
        /*G:This function allows to create a Student from a String s.
            fields is filled with the elements between "/" in String s.
            The element returned is a new Student with email = fields[O] (a String containing the email adress) and
            confirmed = the boolean given by Boolean.parseBoolean depending on the String in fields[1]
            (fields[1] is a String and Boolean.parseBoolean convert the string to true or false)
        */
	     String[] fields = s.split("/");
	     return new Student(fields[0], Boolean.parseBoolean(fields[1]), fields[2], Integer.parseInt(fields[3]));
    }

    /**
      *@return json
      *           returns the completed object with an email and it's email confirmation
    */
    public JSONObject toJSON(){
	     JSONObject json = new JSONObject();
	     json.put("email", email);
	     json.put("confirmed", confirmed);
	     json.put("name", name);
	     json.put("studentNumber", studentNumber);
	     return json; //K: returns the completed object with an email and it's email confirmation
    }



}
