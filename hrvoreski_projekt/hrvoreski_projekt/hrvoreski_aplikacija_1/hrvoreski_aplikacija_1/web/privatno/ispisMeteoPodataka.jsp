<%-- 
    Document   : ispisMeteoPodataka
    Created on : May 28, 2014, 9:13:21 PM
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
        <title>Ispis meteo podataka</title>
        <link href="${pageContext.servletContext.contextPath}/css/displaytag.css" type="text/css" rel="stylesheet"/>
        <link href="${pageContext.servletContext.contextPath}/css/header.css" type="text/css" rel="stylesheet"/>
    </head>
    <body>
        <div class="header">
            Projekt - aplikacija 1 : ISPIS METEO PODATAKA ZA ADRESE
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
            <sql:setDataSource
                var="baza"
                driver="${applicationScope.konfiguracija.getBpKonfiguracija().getDriver_database()}"
                url="${applicationScope.konfiguracija.getBpKonfiguracija().getServer_database()}${applicationScope.konfiguracija.getBpKonfiguracija().getUser_database()}"
                user="${applicationScope.konfiguracija.getBpKonfiguracija().getUser_username()}"
                password="${applicationScope.konfiguracija.getBpKonfiguracija().getUser_password()}"
                />
            <sql:transaction dataSource="${baza}">
                <sql:query var="podaci">
                    SELECT * FROM hrvoreski_meteo INNER JOIN hrvoreski_adrese ON hrvoreski_meteo.idAdresa=hrvoreski_adrese.idAdresa INNER JOIN hrvoreski_korisnik ON hrvoreski_adrese.korIme=hrvoreski_korisnik.korIme ORDER BY idMeteo DESC;
                </sql:query>

                <display:table name="${podaci.rows}" pagesize="10">
                    <display:column sortable="true" headerClass="sortable" property="idMeteo" title="MeteoID"/>
                    <display:column sortable="true" headerClass="sortable" property="temperatura" title="Temperatura"/>
                    <display:column sortable="true" headerClass="sortable" property="tlak" title="Tlak"/>
                    <display:column sortable="true" headerClass="sortable" property="vlaga" title="Vlaga"/>
                    <display:column sortable="true" headerClass="sortable" property="vjetar" title="Vjetar"/>
                    <display:column sortable="true" headerClass="sortable" property="kisa" title="Kiša"/>
                    <display:column sortable="true" headerClass="sortable" property="snijeg" title="Snijeg"/>
                    <display:column sortable="true" headerClass="sortable" property="vrijeme" title="Vrijeme"/>
                    <display:column sortable="true" headerClass="sortable" property="adresa" title="Adresa"/>
                    <display:column sortable="true" headerClass="sortable" property="korIme" title="Korisnik"/>
                </display:table>
            </sql:transaction>
        </div>

    </body>
</html>
