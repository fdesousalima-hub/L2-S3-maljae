<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta content="IE=edge" http-equiv="X-UA-Compatible">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style media="screen">
      <#include ("Index.css") >
    </style>
    <title>maljae delete member</title>
  </head>

  <body>
    <img src="https://upload.wikimedia.org/wikipedia/fr/thumb/c/c7/Logo_Universit%C3%A9_de_Paris_%282019%29-square-white.svg/1200px-Logo_Universit%C3%A9_de_Paris_%282019%29-square-white.svg.png" alt="ImgError">
    <div id="center">
      <form action="/team/deleteMemberConfirm/${token}" method="post" class="form">
        <h1> Leave your team </h1>
        <div>
          <p> Enter your email to leave your team</p>
          <input type="email" name="email" required>
        </div>
        <div>
        <label for="confirme">Confirm removal:</label>
        <input type="radio" name="secret" required>
        </div>
        <div>
  	       <input type="submit" value="Delete">
           <#if Error == 1>
           <h3>What you've entered is not valid !!!</h3>
           </#if>
        </div>
      </form>
    </div>
  </body>
</html>
