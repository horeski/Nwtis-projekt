<%-- 
    Document   : NeuspjesnaPrijava
    Created on : Jun 1, 2014, 10:48:36 PM
    Author     : Hrvoje
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Greška</title>
    </head>
    <body>
        <h1>Desila se greška</h1>
        <p>
    <c:if test="${not empty  param["failed"]}" >
        Pogrešno korisničko ime ili lozinka !</br>
    </c:if>


</p></br>

<a href="${pageContext.servletContext.contextPath}/Kontroler">Početna stranica</a></br>
</body>
</html>
