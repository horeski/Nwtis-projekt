/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.hrvoreski.rest.serveri;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.RequestScoped;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import org.foi.nwtis.hrvoreski.web.kontrole.Korisnik;
import org.foi.nwtis.hrvoreski.web.slusaci.Kontekst;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;

/**
 * REST Web Service
 *
 * @author Hrvoje
 */
@Path("aktivniKorisnici")
@RequestScoped
public class AktivniKorisniciResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of AktivniKorisniciResource
     */
    public AktivniKorisniciResource() {
    }

    /**
     * Retrieves representation of an instance of
     * org.foi.nwtis.hrvoreski.rest.serveri.AktivniKorisniciResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getJson() {
        JSONObject jo = null;
        Kontekst ctx = Kontekst.getInstance();
        List<Korisnik> popisKorisnika = (List<Korisnik>) ctx.getKontekst().getAttribute("aktivniKorisnici");

        if (popisKorisnika == null) {
            popisKorisnika = new ArrayList();
        }
        for (Korisnik k : popisKorisnika) {
            jo = new JSONObject();
            try {
                jo.put("ime", k.getIme());
                jo.put("korIme", k.getKorisnik());
                jo.put("lozinka", k.getLozinka());
                jo.put("sesijaID", k.getSes_ID());
                jo.put("uloga", k.getUloga());
                return jo.toString();
            } catch (JSONException ex) {
                Logger.getLogger(AktivniKorisniciResource.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return "";
    }

    /**
     * PUT method for updating or creating an instance of
     * AktivniKorisniciResource
     *
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putJson(String content) {
    }
}
