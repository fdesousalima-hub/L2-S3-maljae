<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta content="IE=edge" http-equiv="X-UA-Compatible">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style media="screen">
      <#include ("Index.css") >
    </style>
    <title>maljae edit team</title>
  </head>
  <body>
      <img src="https://upload.wikimedia.org/wikipedia/fr/thumb/c/c7/Logo_Universit%C3%A9_de_Paris_%282019%29-square-white.svg/1200px-Logo_Universit%C3%A9_de_Paris_%282019%29-square-white.svg.png" alt="ImgError">
    <div id="center">
    	<div id="info" class="form">
    		<div>
  				<h1 id="titre"> Information about ${teamName}</h1>
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
            <p>_____________</p>
          </div>
          <div id="add">
            <a href="/team/invite/${token}"><button>Add a member</button></a>
          </div>
    	</div>
    	<div id="update">
       			<form id="except" action="/team/update/${token}" method="post" class="form">
       				<div>
  						<h1 id="titre">Update ${teamName}</h1>
       				</div>
       				<div>
  						<label for="preferences">Enter the preferences of your team:</label></br>
   						<#list 1..tasksNumber as i>
    						<select name="pref${i}">
   	 							<#list 0..tasksNames?size-1 as j>
                    <#if i==j+1>
	   								<option value="${j+1}" selected>${tasksNames[j]}</option>
                    <#else>
                    <option value="${j+1}">${tasksNames[j]}</option>
                    </#if>
     							</#list>
        					</select>
						</#list>
        			</div>
        			<div>
                <p><br></p>
        				<label for="secret">Enter your secret: </label>
        				<input type="number" name="secret" id="secret" value="${secret}" required>
        			</div>
        			<div>
  						<input type="submit" value="Update">
       				</div>
       			</form>
    	</div>
      	<div class="list">
          <h1 id="titre"> Tasks List </h1>
        	<iframe class="li" src="/Task.txt"></iframe>
      	</div>
    </div>
  </body>
</html>
