/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.foi.nwtis.hrvoreski.web.filteri;

import java.io.IOException;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.foi.nwtis.hrvoreski.web.kontrole.Korisnik;

/**
 * Filter za kontrolu pristupa u aplikaciji
 * @author Hrvoje
 */
@WebFilter(filterName = "KontrolaPristupa", urlPatterns = {"/faces/privatni/*"}, dispatcherTypes = {DispatcherType.FORWARD, DispatcherType.ERROR, DispatcherType.REQUEST, DispatcherType.INCLUDE})
public class KontrolaPristupa implements Filter {
       
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        HttpServletResponse res = (HttpServletResponse) response;

        if (session == null || session.getAttribute("korisnik") == null) {
            res.sendRedirect(req.getContextPath() + "/faces/javni/login.xhtml");
        } else {
            Korisnik korisnik =(Korisnik) session.getAttribute("korisnik");          
            chain.doFilter(req, res); // korisnik postoji, nastavi dalje
       }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
