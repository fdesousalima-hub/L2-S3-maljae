<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta content="IE=edge" http-equiv="X-UA-Compatible">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style media="screen">
      <#include ("Index.css") >
    </style>
    <title>maljae Result</title>
  </head>

  <body>
    <img src="https://upload.wikimedia.org/wikipedia/fr/thumb/c/c7/Logo_Universit%C3%A9_de_Paris_%282019%29-square-white.svg/1200px-Logo_Universit%C3%A9_de_Paris_%282019%29-square-white.svg.png" alt="ImgError">
    <div id="center">
        <div id="info" class="form">
    		<div>
  				<h1>Result of ${teamName}</h1>
       		</div>
       		<div>
  				<p>Members of your team :</p>
  					<#list students>
  						<ul>
    					<#items as student>
     						<li>${student}</li>
   						</#items>
  						</ul>
					</#list>
       		</div>
       		<div>
          		<p><br>
                The preferences of your team:</p>
          			<#list preferences>
  						<ol>
    					<#items as preference>
    						<li>${preference}</li>
    					</#items>
  						</ol>
					</#list>
       		</div>
       		<div>
       			<p><br>
              Your secret: ${secret}</label>
       		</div>
          <div>
      <h3 class="result"><br>Your subject: ${subject}</h2>
          </div>
          <a href="/team/log/${token}"><button>See your logs</button></a>
    	</div>
    </div>
  </body>
</html>
