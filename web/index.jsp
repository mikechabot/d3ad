<%@ page language="java"
	import="java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
  <head>
    <title>PerformanceFlower</title>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
    <link type="text/css" rel="stylesheet" href="css/bootstrap.min.css"/>
    <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css'>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link type="text/css" rel="stylesheet" href="css/style.css"/>
    
    <style type="text/css">
circle.node {
  cursor: pointer;
  stroke: #000;
  stroke-width: .5px;
}

circle.node.directory {
  stroke: #9ecae1;
  stroke-width: 2px;
}

circle.node.collapsed {
  stroke: #555;
}

.nodetext {
  fill: #252929;
  font-weight: bold;
  text-shadow: 0 0 0.2em white;
}

line.link {
  fill: none;
  stroke: #9ecae1;
  stroke-width: 1.5px;
}
    </style>
  </head>
  <body>
	<div class="container">
	<h2>PerformanceFlower</h2>
		<form action="upload" method="post" enctype="multipart/form-data">
		  <fieldset>
		    <input type="file" name="file">
		    <button type="submit" class="btn">Upload</button>
		  </fieldset>
		</form>
	<strong><c:out value="${filename}"/></strong>
	<div id="visualization"></div>
	</div>
    <script type="text/javascript" src="js/d3/d3.js"></script>
    <script type="text/javascript" src="js/d3/d3.geom.js"></script>
    <script type="text/javascript" src="js/d3/d3.layout.js"></script>
    <script type="text/javascript" src="js/CodeFlower.js"></script>
    <script type="text/javascript" src="js/dataConverter.js"></script>
    <script type="text/javascript">
    var currentCodeFlower = new CodeFlower("#visualization", 600, 600);
    var json = JSON.parse('${json}');
    currentCodeFlower.update(json);
    </script>
  </body>
</html>
