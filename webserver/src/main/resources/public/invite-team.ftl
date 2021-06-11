<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta content="IE=edge" http-equiv="X-UA-Compatible">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style media="screen">
      <#include ("Index.css") >
    </style>
    <title>maljae invitation</title>
  </head>

  <body>
    <img src="https://upload.wikimedia.org/wikipedia/fr/thumb/c/c7/Logo_Universit%C3%A9_de_Paris_%282019%29-square-white.svg/1200px-Logo_Universit%C3%A9_de_Paris_%282019%29-square-white.svg.png" alt="ImgError">
    <div id="center">
      <form action="/team/inviteSend/${token}" method="post" class="form">
        <div>
        	<p>Who would you like to invite to the team ${teamName} ?</p>
        	<input type="mail" name="mail" required>
        </div>
        <div>
  	       <input type="submit" value="Send invitation">
           <#if Error == 1>
           <h3>What you've entered is not valid !!!</h3>
           </#if>
        </div>
      </form>
    </div>
  </body>
</html>
