package fr.univparis.maljae.webserver;
import java.io.*;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;


import fr.univparis.maljae.datamodel.Team;
import fr.univparis.maljae.datamodel.Configuration;
import fr.univparis.maljae.datamodel.Student;

public class Notifier {

  /**
   *Function send email
   *@param email
   *@param subject
   *@param text
 * @throws IOException
 * @throws MessagingException
 * @throws AddressException
  */
    public static void sendEmail(String email, String subject, String text) throws IOException{
      // We define the identifier of the mail sender
          final String username = "maljae.team@gmail.com";
          final String password = "TeamSeven";

          // We define the recipient email
          String to = email;

          // We define the host and port identifier
          String host = "smtp.gmail.com";
          String port = "587";

          // Get system properties
          Properties properties = System.getProperties();

          // Setup mail server
                //Set host
          properties.setProperty("mail.smtp.host", host);
                //Set port
          properties.setProperty("mail.smtp.port", port);
                //Set the authentification true
          properties.setProperty("mail.smtp.auth", "true");
                //Set true to have the server send an email using the STARTTLS protocol so that it can send it
          properties.setProperty("mail.smtp.starttls.enable", "true");


          // Connect to the mail sender
          Session session = Session.getInstance(properties,new javax.mail.Authenticator() {
                             protected PasswordAuthentication getPasswordAuthentication() {
                             return new PasswordAuthentication(username, password);}});

          try {

             // Email creation
             MimeMessage message = new MimeMessage(session);

             // Set the email of the mail sender
             message.setFrom(new InternetAddress(username));

             // Set the email of the mail recipient
             message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

             // Set the subject of the mail
             message.setSubject(subject);

             // Set the message of the mail
             message.setText(text);

             // Send email
             Transport.send(message);
             System.out.println("Sent message successfully....");
          } catch (MessagingException e) {
  			System.out.println("No Connection");
  			keepEmail(email, subject,text);
          }
    }


    /**
      *Function that send email when the team is created
      *@param host
      *@param token
     * @throws IOException
    */

    public static void sendEmailForAll(Team team, String subject, String text) throws IOException{
      for(Student student: team.getStudents()){
        	sendEmail(student.getEmail(), subject, text);
      }
    }

    private static void keepEmail(String email, String subject, String text) throws IOException {
    	String filename = Configuration.getDataDirectory () + "/emails/" + email + "-email.json";
    	JSONObject json = new JSONObject();
    	json.put("email", email);
    	json.put("subject", subject);
    	json.put("text", text);
	    FileWriter fw = new FileWriter(filename);
	    fw.write(json.toString(2));
	    fw.close();

	}


	public static void sendTeamCreation(String host, Token token) throws IOException {
      // Create the message
	     String message = "Hello!\n" +
  	                    "You have requested the creation of a team.\n" +
  	                    "Here are the links to perform actions on this team :\n" +
  	                    "- To edit your team informations :\n" +
  	                    "  http://" + host + "/team/edit/" + token.toString() + "\n" +
  	                    "- To delete your team :\n" +
  	                    "  http://" + host + "/team/delete/" + token.toString() + "\n" +
                        "- To leave your team :\n" +
                        "  http://" + host + "/team/deleteMember/" + token.toString() +"\n";

       // Send email
	     sendEmailForAll(token.getTeam(), "[maljae] Team Creation", message);
    }
    /**
      *Function that send email when the team is modified
      *@param host
      *@param token
    */
    public static void sendTeamEdit(String host, Token token) throws IOException {
      // Create the message

      String message = "Hello!\n" +
                    "Someone has edited your team.\n" +
                    "Here are the links to perform actions on this team :\n" +
                    "- To re-edit your team informations :\n" +
                    "  http://" + host + "/team/edit/" + token.toString() + "\n" +
                    "- To delete your team :\n" +
                    "  http://" + host + "/team/delete/" + token.toString() + "\n" +
                    "- To leave your team :\n" +
                    "  http://" + host + "/team/deleteMember/" + token.toString() +"\n";




      // Send email
      sendEmailForAll(token.getTeam(),  "[maljae] Team Edit", message);
    }
    /**
      *Function that send email when the team is joined
      *@param host
      *@param token
    */
    public static void sendTeamJoin(String host, Token token) throws IOException {
      // Create the message
      // Fixe me : show the name of the new mem
      String message = "Hello!\n" +
                    "Someone join your team\n" +
                    "Here are the links to perform actions on this team :\n" +
                    "- To edit your team informations :\n" +
                    "  http://" + host + "/team/edit/" + token.toString() + "\n" +
                    "- To delete your team :\n" +
                    "  http://" + host + "/team/delete/" + token.toString() + "\n" +
                    "- To leave your team :\n" +
                    "  http://" + host + "/team/deleteMember/" + token.toString() +"\n";


      // Send email
      sendEmailForAll(token.getTeam(),  "[maljae] Team Join", message);
    }
    /**
      *Function that send email when the team is delete
      *@param host
      *@param token
    */
    public static void sendTeamDelete(String host, Token token) throws IOException {
      // Create the message
      String message = "Hello!\n" +
                    "Your team is delete...\n" +
                    "Here is the link to create a new Team : \n" +
                    " http://" + host + "\n" +
                    "PS : Someone can also invite you in a team";

      // Send email
      sendEmailForAll(token.getTeam(),  "[maljae] Team Delete", message);
    }
    /**
      *Function that send email when a students want to join your team
      *@param host
      *@param token
      *@param mail
     * @throws MessagingException
     * @throws AddressException
    */
    public static void sendTeamInvit(String host,Token token,String mail) throws IOException, AddressException, MessagingException{
      String message = "Hello!\n" +
                    "Someone has requested you to join the team "+token.getTeam().getIdentifier()+"\n" +
                    "If you want to join the team, click the link ! \n" +
                    " http://" + host  + "/team/confirmMail/" + token.toString() + "\n" +
                    "But you can also create your team with : \n" +
                    " http://" + host + "\n" ;
      sendEmail(mail, "[maljae] Invitation to a Team", message);
    }
    /**
      *Function that send email when a student leave your team
      *@param host
      *@param token
    */
    public static void sendMemberDeleteTeam(String host, Token token) throws IOException{
      String message = "Hello!\n" +
                    "Someone has left your team" +
                    "Here are the links to perform actions on this team :\n" +
                    "- To edit your team informations :\n" +
                    "  http://" + host + "/team/edit/" + token.toString() + "\n" +
                    "- To delete your team :\n" +
                    "  http://" + host + "/team/delete/" + token.toString() + "\n" +
                    "- To leave your team :\n" +
                    "  http://" + host + "/team/deleteMember/" + token.toString() +"\n";
      sendEmailForAll(token.getTeam(),  "[maljae] A member leaves your Team", message);
    }
    /**
      *Function that send email when a you leave your team
      *@param host
      *@param email
      *@param token
     * @throws MessagingException
     * @throws AddressException
    */
    public static void sendMemberDeleteStudent(String host, String email, Token token) throws IOException, AddressException, MessagingException{
      String message = "Hello!\n" +
                    "You leave your team" + "\n"+
                    "You can create a new team with :\n"+
                    "  http://" + host +"\n";
      sendEmail(email, "[maljae] You leave your Team", message);
    }


	public static void sendAllMailKeep() throws JSONException, IOException {
		File folder = new File(Configuration.getDataDirectory () + "/emails/");
    	folder.mkdir();
		for (File file : folder.listFiles()) {
			JSONObject json = new JSONObject(FileUtils.readFileToString (file, "utf-8"));
			String email = json.getString("email");
			String subject = json.getString("subject");
			String text = json.getString("text");
			file.delete();
			sendEmail(email, subject, text);
		}
	}



}
