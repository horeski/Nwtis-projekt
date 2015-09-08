<%-- 
    Document   : unosAdrese
    Created on : May 29, 2014, 9:13:21 PM
    Author     : Hrvoje
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Unos adrese</title>
        <link href="${pageContext.servletContext.contextPath}/css/displaytag.css" type="text/css" rel="stylesheet"/>
        <link href="${pageContext.servletContext.contextPath}/css/header.css" type="text/css" rel="stylesheet"/>
    </head>
    <body>
        <div class="header">
            Projekt - aplikacija 1 : UNOS ADRESE
        </div>
        <div class="login">
            <c:set var="userName" value="${sessionScope.korisnik}" scope="page" />          
            <p> <c:if test="${empty  pageContext.request.remoteUser}" >
                    Prijavljeni korisnik: --- </br>
                </c:if>
                <c:if test="${not empty pageContext.request.remoteUser}">
                    Prijavljeni korisnik: ${pageContext.request.remoteUser}</br>
                </c:if>
            </p>
        </div>
        <div class="top">          
            <a class="box" href="${pageContext.servletContext.contextPath}/Kontroler">Index</a>
            <a class="box" href="${pageContext.servletContext.contextPath}/OdjavaKorisnika">Odjava</a>
            <a class="box" href="${pageContext.servletContext.contextPath}/UnosAdrese">Unos adrese</a>
            <a class="box" href="${pageContext.servletContext.contextPath}/IspisAdresa">Ispis adresa</a>
            <a class="box" href="${pageContext.servletContext.contextPath}/IspisMeteoPodataka">Ispis meteo podataka</a>
            <a class="box" href="${pageContext.servletContext.contextPath}/IspisZahtjevaUpravljackogServera">Zahtjevi servera</a>
            <a class="box" href="${pageContext.servletContext.contextPath}/IspisKorisnickihZahtjeva">Zahtjevi korisnika</a>
            <a class="box" href="${pageContext.servletContext.contextPath}/Dokumentacija">Dokumentacija</a>
        </div>
        <div class="sadrzaj">
            <form method="POST" action="${pageContext.servletContext.contextPath}/UnosAdrese">
                <label for="adresa">Adresa: </label>
                <input name="adresa" id="adresa" type="text" style="width:400px"/>
                <input type="submit" value=" Unos adrese "/>
            </form>
        </div>
    </body>
</html>