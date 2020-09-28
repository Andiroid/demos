
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%--    <spring:url value="/resources/main.css" var="mainCSS" />--%>
<%--    <link href="${mainCSS}" rel="stylesheet"/>--%>
    <title>Patientenliste</title>
</head>
<body>
<div align="center" style="font-family:verdana;">
    <h1>Patientenliste</h1>
    <table border="1">
        <tr>
            <th>Nr</th>
            <th>Vorname</th>
            <th>Nachname</th>
            <th>Geburtsdatum</th>
            <th>Adresse</th>
            <th>SVNR</th>
            <th>Geschlecht</th>
            <th>Versicherung</th>
            <th>Bearbeitung</th>
        </tr>
        <c:forEach var="contact" items="${listPatients}" varStatus="status">
            <tr>
                <td>${contact.id}</td>
                <td>${contact.firstname}</td>
                <td>${contact.lastname}</td>
                <td>${contact.birthday}</td>
                <td>${contact.address}</td>
                <td>${contact.ssn}</td>
                <td>${contact.gender}</td>
                <td>${contact.insurance}</td>
                <td>
                    <form:form action="edit" method="POST">
                        <input type="hidden" name="nr" value="${contact.id}" />
                        <input  class="btn" type="submit" value="edit" class="link"/>
                    </form:form>
                    <form:form action="delete" method="POST">
                        <input type="hidden" name="nr" value="${contact.id}" />
                        <input class="btn" type="submit" value="delete" class="link"/>
                    </form:form>
                </td>
            </tr>
        </c:forEach>
    </table>
    <br>
    <form:form action="add" method="POST">
        <input type="hidden" name="nr" value="-1"/>
        <input type="submit" value="Neuer Patient"/>
    </form:form>
</div>

<style>
    table {
        font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
        border-collapse: collapse;
        width: 100%;
    }

    table td, table th {
        border: 1px solid #ddd;
        padding: 8px;
    }

    table tr:nth-child(even){background-color: #f2f2f2;}

    table tr:hover {background-color: #ddd;}

    table th {
        padding-top: 12px;
        padding-bottom: 12px;
        text-align: left;
        background-color: #4CAF50;
        color: white;
    }
    .btn{
        margin: 0px 10px;
        float:left;
    }
</style>
</body>
</html>