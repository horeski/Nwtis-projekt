<%-- 
    Document   : login
    Created on : May 28, 2014, 9:11:25 PM
    Author     : Hrvoje
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>NWTiS Projekt - Prijava</title>
        <link href="${pageContext.servletContext.contextPath}/css/header.css" type="text/css" rel="stylesheet"/>
    </head>
    <body>     
        <div class="header">
            Projekt - aplikacija 1 : LOGIN
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
            <form method="POST" action="j_security_check">
                <table>
                    <tr>
                        <td>
                            <label for="j_username">Korisničko ime</label>
                        </td>
                        <td>
                            <input name="j_username" id="korIme" type="text" size="25"/><br/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label for="j_password">Lozinka</label>
                        </td>
                        <td>
                            <input name="j_password" id="lozinka" type="password" size="25"><br/>
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td><input type="submit" value=" Prijavi se "></td>
                    </tr>
                </table>
            </form>
        </div>
    </body>
</html>
