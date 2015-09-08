<%-- 
    Document   : NeuspjesnaPrijava
    Created on : May 28, 2014, 9:05:40 PM
    Author     : Hrvoje
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Greška</title>
        <link href="${pageContext.servletContext.contextPath}/css/header.css" type="text/css" rel="stylesheet"/>
    </head>
    <body>
        <div class="header">
            Projekt - aplikacija 1 : POGREŠKA PRI LOGIRANJU
        </div>
        <div class="top">          
            <a class="box" href="${pageContext.servletContext.contextPath}/Kontroler">Index</a>
            <a class="box" href="${pageContext.servletContext.contextPath}/OdjavaKorisnika">Odjava</a>
            <a class="box" href="${pageContext.servletContext.contextPath}/UnosAdrese">Unos adrese</a>
            <a class="box" href="${pageContext.servletContext.contextPath}/IspisAdresa">Ispis adresa</a>
            <a class="box" href="${pageContext.servletContext.contextPath}/IspisMeteoPodataka">Ispis meteo podataka</a>
            <a class="box" href="${pageContext.servletContext.contextPath}/IspisZahtjevaUpravljackogServera">Zahtjevi servera</a>
            <a class="box" href="${pageContext.servletContext.contextPath}/IspisKorisnickihZahtjeva">Zahtjevi korisnika</a>
        </div>
        <div class="sadrzaj">
            <p>
            <c:if test="${not empty  param["failed"]}" >
                Pogrešno korisničko ime/lozinka</br>
            </c:if>   
        </p>
    </div>
</body>
</html>
