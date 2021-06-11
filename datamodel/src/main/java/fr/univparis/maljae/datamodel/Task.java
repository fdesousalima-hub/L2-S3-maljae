package fr.univparis.maljae.datamodel;

import java.net.*;
import org.json.JSONObject;

/**
  *This class of objects represent task description.
  */
public class Task {

    /* Those attributes describe Task and give a title an identifier and a short description of a task that the group need to do */

    /**
      *the identifier of the task
      *@see Task(JSONObject o)
      *@see toString()
      *@see getIdentifier()
    */
    private int identifier;
    /**
      *the title of the task
      *@see Task(JSONObject o)
      *@see toString()
    */
    private String title;
    /**
      *the url of the task
      *@see Task(JSONObject o)
      *@see toString()
    */
    private String url;
    /**
      *the description of the task
      *@see Task(JSONObject o)
      *@see toString()
    */
    private String description;

    /**
      * implement a file with the task information
      *@param o
      *          the file data for the new task
    */
    Task(JSONObject o){
	     /* This is a simple constructor to initialise the object task */
	      this.identifier = o.getInt("identifier");
	      this.title = o.getString("title");
	      this.url = o.getString("url");
	      this.description = o.getString("description");
    }

    /**
      *@return  this.identifier + "\n" + this.title + "\n" + this.url + "\n" + this.description
      *                                                                                       return the task representation
    */
    public String toString(){
	     /* This function is used to present the task by giving away his title and description */
	     return this.identifier + "\n" +
	            this.title + "\n" +
	            this.url + "\n" +
	            this.description;
    }

    /**
      *@return identifier
      *                 return the identifier of the task;
    */
    public int getIdentifier(){
      /* This is a simple getter that we have to make because of the private tag of identifier
        futhermore we need to add a few getter for title url and description that are, in this state totally useless because of the
        private string */
	     return identifier;
    }
    public String getTitle() {
		return title;
	}
}
