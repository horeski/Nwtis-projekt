/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.hrvoreski.web.kontroleri;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.foi.nwtis.hrvoreski.konfiguracije.WebKonfiguracija;
import org.foi.nwtis.hrvoreski.web.zrna.UnosAdrese;

/**
 * Obavlja preusmjeravanjem temeljeno po zadanim poslovnim pravilima
 * @author Hrvoje
 */
public class Kontroler extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String zahtjev = request.getServletPath();
        String odrediste = null;
        WebKonfiguracija config = (WebKonfiguracija) getServletContext().getAttribute("konfiguracija");
        HttpSession sesija = request.getSession();
        switch (zahtjev) {
            case "/Kontroler":
                odrediste = "/jsp/index.jsp";
                break;
            case "/PrijavaKorisnika":
                odrediste = "/jsp/login.jsp";
                break;
            case "/OdjavaKorisnika":
                sesija.invalidate();
                odrediste = "/Kontroler";
                break;
            case "/UnosAdrese":
                request.setCharacterEncoding("UTF-8");
                String adresa = request.getParameter("adresa");
                if (adresa != null && adresa.length() > 0) {
                    String korIme = request.getUserPrincipal().getName();
                    UnosAdrese ua = new UnosAdrese();
                    ua.dodaj(adresa, config , korIme);
                    odrediste = "/jsp/index.jsp";
                }
                else{
                    odrediste = "/privatno/unosAdrese.jsp";                   
                }
                break;
            case "/IspisAdresa":
                odrediste = "/privatno/ispisAdresa.jsp";
                break;
            case "/IspisMeteoPodataka":
                odrediste = "/privatno/ispisMeteoPodataka.jsp";
                break;
            case "/IspisZahtjevaUpravljackogServera":
                odrediste = "/privatno/ispisZahtjevaUpravljackogServera.jsp";
                break;
            case "/IspisKorisnickihZahtjeva":
                odrediste = "/privatno/ispisKorisnickihZahtjeva.jsp";
                break;
            case "/Dokumentacija":
                odrediste = "/dok/index.html";
                break;
            default:
                ServletException up = new ServletException("Zahtjev nije poznat !");
                throw up;
        }

        response.sendRedirect(getServletContext().getContextPath() + odrediste);

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
