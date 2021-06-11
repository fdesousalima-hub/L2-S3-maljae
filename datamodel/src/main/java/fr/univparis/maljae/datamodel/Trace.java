package fr.univparis.maljae.datamodel;

import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Trace{
  /**
  *method who write the different info of a team like the name or the secretnumbers
  *@param team
  *@param string
  */
  public static void writeTeamInfo(Team t,String s) throws IOException{
    BufferedWriter file = new BufferedWriter(new FileWriter(Configuration.getTraceDirectory() + "/" + t.getIdentifier() + "-trace.txt",true));
    s = getDate() + " : " + s + "\n";
    file.write(s);
    file.close();}
    /**
    *method who write the different info of a team like the name or the secretnumbers
    *@param string
    */
  public static void writeGeneralInfoTeam(String s)throws IOException{
    BufferedWriter file = new BufferedWriter(new FileWriter(Configuration.getTraceDirectory() + "/" + "GeneralTrace.txt",true));
    s = getDate() + " : " + s + "\n";
    file.write(s);
    file.close();}

    /**
    *method who get today's date
    */
  public static String getDate(){
    return new SimpleDateFormat("EEEEE dd MMMMM yyyy HH:mm:ss").format(new Date());}

    /**
      *method who tell the students some info on their teams that they will need later like the identifier
      *@param team
    */
  public static void writeTeamCreation(Team t)throws IOException{
    writeTeamInfo(t,"Creation de votre équipe, l'identifiant de l'equipe est : "+t.getIdentifier());
    writeGeneralInfoTeam("L'equipe "+t.getIdentifier()+" a ete cree");}

    /**
      *method who confirm to the students that their team have been deleted
      *@param team
    */
  public static void writeTeamDelete(Team t)throws IOException{
    writeGeneralInfoTeam("L'equipe "+t.getIdentifier()+" a ete supprimer");}

    /**
      *method who inform the students that someone try to join their team
      *@param team
      *@param inviteur
      *@param invite
    */
  public static void writeTeamInvitationSend(Team t,String inviteur,String invite) throws IOException{
    writeTeamInfo(t,inviteur+" a demandé a "+invite+" a rejoindre votre équipe");}
    /**
      *method who confirm to the students that the student have join their team
      *@param team
    */
  public static void writeTeamJoin(Team t,String mail)throws IOException{
    writeTeamInfo(t,mail+" a rejoint votre équipe");}
    /**
      *method who confirm to the students that their team's have been modified
      *@param team
    */
  public static void writeTeamEdit(Team t)throws IOException{
    writeTeamInfo(t,"Les parametres de votre équipe ont changé");}
}
