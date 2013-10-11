<%@ page language="java"
	import="java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<meta charset="utf-8">
<style>

.link {
  fill: none;
  stroke: #aaa;
}

.node text {
  font: 10px sans-serif;
}

.node circle {
  stroke: #fff;
  stroke-width: 1.5px;
}

.node.active {
  fill: red;
}

</style>
<body>
<script src="http://d3js.org/d3.v2.js?2.9.6"></script>
<script>

var margin = {top: 40, right: 40, bottom: 40, left: 80},
    width = 960 - margin.left - margin.right,
    height = 500 - margin.top - margin.bottom;

var tree = d3.layout.tree()
    .size([height, width]);

var diagonal = d3.svg.diagonal()
    .projection(function(d) { return [d.y, d.x]; });

var svg = d3.select("body").append("svg")
    .attr("width", width + margin.left + margin.right)
    .attr("height", height + margin.top + margin.bottom)
  .append("g")
    .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

  var root = JSON.parse('${json}');
  var nodes = tree.nodes(root),
      links = tree.links(nodes);

  // Create the link lines.
  svg.selectAll(".link")
      .data(links)
    .enter().append("path")
      .attr("class", "link")
      .attr("d", diagonal);

  // Create the node circles.
  var node = svg.selectAll(".node")
      .data(nodes)
    .enter().append("g")
      .attr("class", function(d) { return "node " + d.type; })
      .attr("transform", function(d) { return "translate(" + d.y + "," + d.x + ")"; })
      .on("mouseover", function(d) { highlight(d.type); })
      .on("mouseout", function(d) { highlight(null); });

  node.append("circle")
      .attr("r", 4.5);

  node.append("text")
      .attr("x", -6)
      .attr("dy", ".35em")
      .attr("text-anchor", "end")
      .text(function(d) { return d.name; });


function highlight(type) {
  if (type == null) d3.selectAll(".node").classed("active", false);
  else d3.selectAll(".node." + type).classed("active", true);
}

</script>
</body>
</html>