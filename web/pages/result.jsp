<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<html>
<head>
    <fmt:setLocale value="${locale}"/>
    <fmt:setBundle basename="property.text" var="bundle" scope="request"/>

    <fmt:message bundle = "${bundle}" key ="id" var = "id"/>
    <fmt:message bundle = "${bundle}" key ="name" var = "name"/>
    <fmt:message bundle = "${bundle}" key ="dosage" var = "dosage"/>
    <fmt:message bundle = "${bundle}" key ="fabricators" var = "fabricators"/>
    <fmt:message bundle = "${bundle}" key ="group" var = "group"/>
    <fmt:message bundle = "${bundle}" key ="analogs" var = "analogs"/>
    <fmt:message bundle = "${bundle}" key ="vitamins" var = "vitamins"/>
    <fmt:message bundle = "${bundle}" key ="back" var = "back"/>
</head>
<body>
<table border="1" cellpadding="5" cellspacing="1" >
    <tr>
        <th>${id}</th>
        <th>${name}</th>
        <th>${dosage}</th>
        <th>${fabricators}</th>
        <th>${group}</th>
        <th>${analogs}</th>
        <th>${vitamins}</th>
    </tr>
    <c:forEach items="${medicamentSet}" var="medicament" >
        <tr>
            <td>${medicament.id}</td>
            <td>${medicament.name}</td>
            <td>${medicament.dosage}</td>
            <td>${medicament.fabricators}</td>
            <td>${medicament.group}</td>
            <td>${medicament.analogs}</td>
            <td></td>
        </tr>
    </c:forEach>
    <c:forEach items="${supplementSet}" var="supplement" >
        <tr>
            <td>${supplement.id}</td>
            <td>${supplement.name}</td>
            <td>${supplement.dosage}</td>
            <td>${supplement.fabricators}</td>
            <td></td>
            <td></td>
            <td>${supplement.vitamins}</td>
        </tr>
    </c:forEach>
</table>
    <a href="../index.jsp">${back}</a>
    </br>
</body>
</html>
