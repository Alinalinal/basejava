<%--
  Created by IntelliJ IDEA.
  User: alinabohoslavec
  Date: 05.10.2022
  Time: 15:39
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <style>
        table, th, td {
            border: 1px solid black;
            border-collapse: collapse;
            margin: auto;
        }

        td {
            text-align: center;
        }
    </style>
</head>

<body>
<c:if test="${empty resumes}">
    <h1>Table resume is empty!</h1>
</c:if>
<table class="resume-table" style="width:500px">
    <caption><h4>Resume table</h4></caption>
    <tr style="height:40px">
        <th>UUID</th>
        <th>FULL NAME</th>
    </tr>

    <c:forEach var="entry" items="${resumes}">
        <tr style="height:40px">
            <td>${entry.key}</td>
            <td>${entry.value}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
