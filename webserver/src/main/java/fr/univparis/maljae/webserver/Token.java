package fr.univparis.maljae.webserver;

import java.util.*;
import java.io.*;
import java.lang.Math;
import org.apache.commons.io.FileUtils;
import org.json.*;
import fr.univparis.maljae.datamodel.Configuration;
import fr.univparis.maljae.datamodel.Teams;
import fr.univparis.maljae.datamodel.Team;


/**
  *This class stores as tokens the email and the team of a student(?)
  *in a HashMap (like a sql data base with the key/value system)
*/
public final class Token {

  /**
    *data base with key/value system
  */
    private static HashMap<Integer, Token> tokens = new HashMap<Integer, Token>();

    /**
      *a token identifier
      *@see  Token(Team team0, String email0, Integer raw0)
      *@see createToken(Team team0, String email0)
      *@see getToken(String raw)
      *@see loadFromFile(Integer raw)
      *@see toString()
    */
    private Integer raw = 0;
    /**
      *a team
      *@see  Token(Team team0, String email0, Integer raw0)
      *@see createToken(Team team0, String email0)
      *@see loadFromFile(Integer raw)
      *@see saveToFile()
      *@see getTeam()
    */
    private Team team;
    /**
      *email team
      *@see  Token(Team team0, String email0, Integer raw0)
      *@see createToken(Team team0, String email0)
      *@see loadFromFile(Integer raw)
      *@see saveToFile()
      *@see getEmail()
    */
    private String email;


    /**
        *Token Constructor
        *@param team0
        *@param email0
        *@param raw0
    */
    Token(Team team0, String email0, Integer raw0){
	     this.team = team0;
	     this.email = email0;
	     this.raw = raw0;
	     tokens.put(raw0, this);
    }


    /**
       *Creates a token with the given arguments
       *@param team0
       *@param email0
       *@return tokens
       *             the token created
   */
    public static Token createToken(Team team0, String email0) throws IOException{
	     Integer raw = Math.abs(team0.hashCode () + email0.hashCode());
	     Token token = new Token(team0, email0, raw);
	     token.saveToFile();
	     return token;
    }

    /**
      *Getter function for a Token
      *@param raw0
      *@return result
      *             token if existe else null
    */
    public static Token getToken(String raw) throws IOException{
	     Integer i = Integer.parseInt(raw);
	     Token result = null;
	     result = tokens.get(i);
	     if(result == null){
         result = loadFromFile(i);
       }
	     return result;
    }

    /**
      *Apparently it creates a token based on a JSON file that contains the needed information
      *@param raw0
      *@return token
      */
    public static Token loadFromFile(Integer raw) throws IOException{
	     File f = new File (Configuration.getDataDirectory() + "/token" + raw + ".json");
	     JSONObject json = new JSONObject(FileUtils.readFileToString(f, "utf-8"));
	     Team team = Teams.getTeam(json.getString("team"));
	     String email = json.getString("email");
	     return new Token(team, email, raw);
    }

    /**
       *Apparently it creates a JSON file based on a token that already exists
    */
    public void saveToFile() throws IOException{
	     File f = new File (Configuration.getDataDirectory() + "/token" + raw + ".json");
	     FileWriter fw = new FileWriter(f);
	     JSONObject json = new JSONObject();
	     json.put("team", team.getIdentifier());
	     json.put("email", email);
	     fw.write(json.toString());
	     fw.close();
    }

    public void UpdateEmailTokens(String email) throws IOException{
      this.email = email;
      this.saveToFile();
    }

    /**
      *getter eamil
      *@return email
    */
    String getEmail(){ return email; }

    /**
      *getter team
      *@return team
    */
    Team getTeam(){ return team; }

    /**
      *function returning a token
    */
    @Override
    public String toString(){ return raw.toString(); }
}
