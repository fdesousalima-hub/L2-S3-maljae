<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta content="IE=edge" http-equiv="X-UA-Compatible">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style media="screen">
      <#include ("Index.css") >
    </style>
    <title>maljae registration</title>
  </head>

  <body>
    <img src="https://upload.wikimedia.org/wikipedia/fr/thumb/c/c7/Logo_Universit%C3%A9_de_Paris_%282019%29-square-white.svg/1200px-Logo_Universit%C3%A9_de_Paris_%282019%29-square-white.svg.png" alt="ImgError">
    <div id="center">
      <form action="/team/create" method="post" class="form">
        <h1>Registration</h1>
        <p>To create a team</p>
        <div>
      <label for="teamName">Enter your Team Name: </label>
      <input  name="teamName" id="teamName" required>
        </div>
        <div>
      <label for="name">Enter your name: </label>
      <input  name="name" id="name" required>
        </div>
        <div>
      <label for="studentNumber">Enter your student number: </label>
      <input type="number" name="studentNumber" id="studentNumber" required>
        </div>
        <div>
      <label for="email">Enter your email: </label>
      <input type="email" name="email" id="email" required>
        </div>
        <div>
      <input type="submit" value="Create !">
        </div>
        <#if Error == 1>
        <h3>What you've entered is not valid !!!</h3>
        </#if>
      </form>
    </div>
  </body>
  </html>
