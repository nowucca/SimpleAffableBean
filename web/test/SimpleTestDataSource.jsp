<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<sql:query var="rs" dataSource="jdbc/simpleaffablebean">
  select category_id, name from category
</sql:query>

<html>
<head>
  <title>DB Test</title>
</head>
<body>

<h2>Results</h2>

<table border="1">
  <c:forEach var="row" items="${rs.rows}">
    <tr><td>${row.category_id}</td><td>${row.name}</td></tr>
    <tr></tr>
  </c:forEach>

</table>

</body>
</html>
