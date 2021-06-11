package fr.univparis.maljae.webserver;

import fr.univparis.maljae.datamodel.Assignment;
import fr.univparis.maljae.datamodel.Configuration;
import fr.univparis.maljae.datamodel.Task;
import fr.univparis.maljae.datamodel.Team;
import fr.univparis.maljae.datamodel.Teams;
import fr.univparis.maljae.datamodel.Trace;
import io.javalin.Javalin;
import io.javalin.plugin.rendering.JavalinRenderer;
import io.javalin.plugin.rendering.template.JavalinFreemarker;
import io.javalin.plugin.rendering.template.TemplateUtil;

/**
  *API for team management.
*/
public class TeamController{

	public static void installHome(Javalin app) {
	     app.get("/home/", ctx -> {
                 ctx.render("/public/home.ftl", TemplateUtil.model("Error", 0));
	     });
	     }

    public static void installTeamCreate(Javalin app) {
	     app.post("/team/create", ctx -> { String email = ctx.formParam("email");
                        String teamName = ctx.formParam("teamName");
	     								   String name = ctx.formParam("name");
	     								   String studentNumber = ctx.formParam("studentNumber");
	     								   int studentNbr = Integer.parseInt(studentNumber);
	     									if(Teams.exist(email) || Teams.numEtuExist(studentNbr)) {
	     										System.out.println("Email or Student number already exist");
	     										ctx.render("/public/home.ftl", TemplateUtil.model("Error", 1));
	     									}
	     									else {
                        Team newteam = Teams.createTeam(teamName,email, name, studentNbr);
                        Token newtoken = Token.createToken(newteam, email);
                        String host = ctx.host ();
                       Trace.writeTeamCreation(newtoken.getTeam());

		                                    	 System.out.println("reussi");
		                                     Notifier.sendTeamCreation (host, newtoken);
		                                     ctx.redirect("/team-creation-done.html");
		                                     }
		                                     });
    }

    public static void installTeamEdit(Javalin app){
	     app.get("/team/edit/:token", ctx -> { Token token = Token.getToken(ctx.pathParam("token"));
		                                         String teamName = token.getTeam().getIdentifier();
		                                         Team team = Teams.getTeam(teamName);
		                                         ctx.render("/public/edit-team.ftl", TemplateUtil.model("teamName", teamName,
			                                                                                              "secret", team.getSecret(),
			                                                                                              "students", team.studentsToString(),
			                                                                                              "preferences", team.preferencesToString(),
			                                                                                              "tasksNames", Configuration.getTasksName(),
			                                                                                              "tasksNumber",Configuration.getTasks().size(),
			                                                                                              "token", token.toString()));});
    }

    public static void installTeamUpdate(Javalin app){
	     app.post("/team/update/:token", ctx -> { Token token = Token.getToken(ctx.pathParam("token"));
		                                            String who = token.getEmail();
		                                            String teamName = token.getTeam().getIdentifier();
		                                            Team team = Teams.getTeam(teamName);
		                                            String host = ctx.host ();
		                                            team.updateSecretFromString(ctx.formParam("secret"));
		                                            String[] pref= new String[Configuration.getTasks().size()];
		                                            for (int i = 0; i < Configuration.getTasks().size(); i++) {
														                        pref[i] = ctx.formParam("pref"+(i+1));
													                      }
		                                            team.updatePreferencesFromString(pref);
		                                            Teams.saveTeam(team);
		                                            Trace.writeTeamEdit(team);
		                                            Notifier.sendTeamEdit (host, token);
		                                            ctx.redirect("/team-update-done.html");});
    }

    public static void installTeamDelete(Javalin app){
      app.get("/team/delete/:token",ctx ->{Token token = Token.getToken(ctx.pathParam("token"));
                                            String teamName = token.getTeam().getIdentifier();
                                            Team team = Teams.getTeam(teamName);
                                            ctx.render("/public/delete-team.ftl", TemplateUtil.model("teamName", teamName,
                                                                                                   "token", token.toString()));});
    }

    public static void installTeamDeleteConfirm(Javalin app){
      app.post("/team/deleteConfirm/:token", ctx -> { Token token = Token.getToken(ctx.pathParam("token"));
                                               String teamName = token.getTeam().getIdentifier();
                                               Team team = Teams.getTeam(teamName);
                                               String host = ctx.host();
                                               Teams.deleteTeam(team);
                                               Trace.writeTeamDelete(team);
                                               Notifier.sendTeamDelete(host, token);
                                               ctx.redirect("/team-delete-done.html");});
    }

    public static void installInviteToTeam(Javalin app){
      app.get("/team/invite/:token",ctx ->{Token token = Token.getToken(ctx.pathParam("token"));
                                            String teamName = token.getTeam().getIdentifier();
                                            Team team = Teams.getTeam(teamName);
                                            ctx.render("/public/invite-team.ftl", TemplateUtil.model("teamName", teamName,
																																																		"Error", 0,
                                                                                                   "token", token.toString()));});
    }

    public static void installInviteToTeamSend(Javalin app){
	     app.post("/team/inviteSend/:token", ctx -> { Token token = Token.getToken(ctx.pathParam("token"));
		                                            String who = token.getEmail();
												String teamName = token.getTeam().getIdentifier();
                                                String mail = ctx.formParam("mail");
		                                            if(Teams.exist(mail) ) {
		   		                                    	 System.out.println("Student already in team");
																								 ctx.render("/public/invite-team.ftl", TemplateUtil.model("teamName", teamName,
																																																				 "Error", 1,
																																																				"token", token.toString()));
		   		                                     	}
		                                            else {
                                                String host = ctx.host ();
                                                Trace.writeTeamInvitationSend(token.getTeam(),who,mail);
                                                Notifier.sendTeamInvit(host, token, mail);
		                                            ctx.redirect("/team-invit-send.html");
                                                }
                                                });}

    public static void installConfirmMailToJoin(Javalin app){
        app.get("/team/confirmMail/:token", ctx -> { Token token = Token.getToken(ctx.pathParam("token"));
                                                      String teamName = token.getTeam().getIdentifier();
                                                      ctx.render("/public/confirmMail.ftl", TemplateUtil.model("teamName", teamName,
																																																				 "Error", 0,
                                                                                                        "token", token.toString()));});}

    public static void installJoinTeamDone(Javalin app){
      app.post("/team/join/:token", ctx -> { Token token = Token.getToken(ctx.pathParam("token"));
                                            String email = ctx.formParam("email");
                                            String name = ctx.formParam("name");
											String teamName = token.getTeam().getIdentifier();
                                            String studentNumber = ctx.formParam("studentNumber");
                                            int studentNbr = Integer.parseInt(studentNumber);
                                            if(Teams.exist(email) || Teams.numEtuExist(studentNbr)) {
                                             System.out.println("Student already in team");
																						 ctx.render("/public/confirmMail.ftl", TemplateUtil.model("teamName", teamName,
																																																"Error", 1,
																																															 "token", token.toString()));
                                            }
                                            else{
                                              String host = ctx.host();
                                              Teams.joinTeam(email,teamName, name, studentNbr);
                                              Trace.writeTeamJoin(token.getTeam(),email);
                                              Notifier.sendTeamJoin(host,token);
                                              ctx.render("/public/team-join-done.ftl", TemplateUtil.model("teamName", teamName,
                                                                                                         "token", token.toString()));
                                            }});
    }


    public static void installMemberDelete(Javalin app){
      app.get("/team/deleteMember/:token",ctx ->{ Token token = Token.getToken(ctx.pathParam("token"));
                                           String email = ctx.formParam("email");
                                           ctx.render("/public/delete-member.ftl", TemplateUtil.model("email", email,
																					 																												"Error", 0,
                                                                                                   "token", token.toString()));});
    }

    public static void installMemberDeleteConfirm(Javalin app){
      app.post("/team/deleteMemberConfirm/:token", ctx -> { Token token = Token.getToken(ctx.pathParam("token"));
                                               String email = ctx.formParam("email");
                                               String host = ctx.host();
                                               String teamName = token.getTeam().getIdentifier();
                                               Team team = Teams.getTeam(teamName);
                                               if(Teams.removeFromExistingTeam(email, team)){
                                                 String emailNew = team.getFirstStudent().getEmail();
                                                 token.UpdateEmailTokens(emailNew);
                                                 Notifier.sendMemberDeleteStudent(host, email, token);
                                                 Notifier.sendMemberDeleteTeam(host,token);
                                                 ctx.redirect("/member-delete-done.html");
                                               }else{
																								 ctx.render("/public/delete-member.ftl", TemplateUtil.model("email", email,
	 	 																					 																												"Error", 1,
	 	                                                                                                    "token", token.toString()));
                                               }});
    }

    public static void installResult(Javalin app){
	     app.get("/team/result/:token", ctx -> { Token token = Token.getToken(ctx.pathParam("token"));
		                                         String teamName = token.getTeam().getIdentifier();
		                                         Team team = Teams.getTeam(teamName);
		                                         Task subject = Assignment.searchTaskInFile(team);
		                                         ctx.render("/public/result.ftl", TemplateUtil.model("subject", subject.getIdentifier(),
		                                        		 											   "teamName", teamName,
			                                                                                           "secret", team.getSecret(),
			                                                                                           "students", team.studentsToString(),
			                                                                                           "preferences", team.preferencesToString(),
			                                                                                           "token", token.toString()));});
   }
   public static void installLog(Javalin app){
     app.get("/team/log/:token",ctx ->{Token token = Token.getToken(ctx.pathParam("token"));
                                           String teamName = token.getTeam().getIdentifier();
                                           Team team = Teams.getTeam(teamName);
                                           ctx.render("/public/log.ftl", TemplateUtil.model("teamName", teamName,
                                                                                                  "token", token.toString()));});
   }

    public static void install(Javalin app){
	     JavalinRenderer.register(JavalinFreemarker.INSTANCE, ".ftl");
	     installHome(app);
	     installTeamCreate(app);
	     installTeamEdit(app);
	     installTeamUpdate(app);
       installTeamDelete(app);
       installTeamDeleteConfirm(app);
       installMemberDelete(app);
       installMemberDeleteConfirm(app);
       installInviteToTeam(app);
       installInviteToTeamSend(app);
       installConfirmMailToJoin(app);
       installJoinTeamDone(app);
       installResult(app);
       installLog(app);
    }
}
