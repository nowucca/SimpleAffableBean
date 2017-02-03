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

<c:forEach var="row" items="${rs.rows}">
  Foo ${row.category_id}<br/>
  Bar ${row.name}<br/>
</c:forEach>

</body>
</html>
