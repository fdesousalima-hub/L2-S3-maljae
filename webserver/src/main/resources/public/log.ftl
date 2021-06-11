<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta content="IE=edge" http-equiv="X-UA-Compatible">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style media="screen">
      <#include ("Index.css") >
    </style>
    <title>maljae log</title>
  </head>

  <body>
    <img src="https://upload.wikimedia.org/wikipedia/fr/thumb/c/c7/Logo_Universit%C3%A9_de_Paris_%282019%29-square-white.svg/1200px-Logo_Universit%C3%A9_de_Paris_%282019%29-square-white.svg.png" alt="ImgError">
    <div id="center">
      <div class="form">
        <div>
  	<h1>Log of ${teamName}</h1>
        </div>
        <div>
          <iframe src="/Trace/${teamName}-trace.txt" width=500 height=400 scrolling=auto> </iframe>
        </div>
    <a href="/team/result/${token}"><button>Back</button></a>
    </div>
    </div>
  </body>
</html>
