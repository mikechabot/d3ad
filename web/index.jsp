<%@ page language="java"
	import="java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
  <head>
    <title>d3ad</title>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
    <link type="text/css" rel="stylesheet" href="css/bootstrap.min.css"/>
    <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css'>
    <link type="text/css" rel="stylesheet" href="css/style.css"/>
  </head>
  <body>
	<div class="container" style="border: 1px dotted black; width: 550px; margin-top: 20px;">
	<h2 class="center">d3ad</h2>
	<form class="form-horizontal" action="upload" method="post" enctype="multipart/form-data">
	  <div class="control-group">
	    <label class="control-label" for="output">Visual</label>
	    <div class="controls">
		<select name="output">
		  <option value="flower">Flower</option>
		  <option value="bubble">Bubble</option>
		  <option value="tree">Tree</option>
		  <option value="stack">Stack</option>
		  <option value="reingold">Reingold-Tilford</option>	  
		</select>      
	    </div>
	  </div>
	  <div class="control-group">
	    <label class="control-label" for="file">File</label>
	    <div class="controls">
	      <input type="file" name="file" placeholder="Choose file">
	    </div>
	  </div>
	  <div class="control-group">
	    <div class="controls">
	      <button type="submit" class="btn">Upload</button>
	    </div>
	  </div>
	</form>
	</div>
  </body>
</html>
