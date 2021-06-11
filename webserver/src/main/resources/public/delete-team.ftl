<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta content="IE=edge" http-equiv="X-UA-Compatible">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style media="screen">
      <#include ("Index.css") >
    </style>
    <title>maljae delete team</title>
  </head>

  <body>
    <img src="https://upload.wikimedia.org/wikipedia/fr/thumb/c/c7/Logo_Universit%C3%A9_de_Paris_%282019%29-square-white.svg/1200px-Logo_Universit%C3%A9_de_Paris_%282019%29-square-white.svg.png" alt="ImgError">
    <div id="center">
      <form class="delete" action="/team/deleteConfirm/${token}" method="post" class="form">
        <div>
        	<h1 id="titre"> Delete ${teamName}</h1>
        	<label for="confirme">Confirm removal: </label>
        	<input type="radio" name="secret" required>
        </div>
        <div>
  	       <input type="submit" value="Supprimer">
        </div>
      </form>
    </div>
  </body>
</html>
