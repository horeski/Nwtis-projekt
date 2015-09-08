/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.hrvoreski.web.zrna;

import java.io.Serializable;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.foi.nwtis.hrvoreski.ejb.eb.HrvoreskiMeteoPortfelj;
import org.foi.nwtis.hrvoreski.ejb.eb.HrvoreskiAdresePortfelja;
import org.foi.nwtis.hrvoreski.ejb.sb.HrvoreskiAdresePortfeljaFacade;
import org.foi.nwtis.hrvoreski.ejb.sb.HrvoreskiMeteoPortfeljFacade;
import org.foi.nwtis.hrvoreski.konfiguracije.WebKonfiguracija;
import org.foi.nwtis.hrvoreski.web.kontrole.JMSAdresaStruktura;
import org.foi.nwtis.hrvoreski.web.kontrole.Korisnik;
import org.foi.nwtis.hrvoreski.ws.klijenti.Adresa;
import org.foi.nwtis.hrvoreski.ws.klijenti.MeteoWSKlijent;
import org.foi.nwtis.hrvoreski.ws.klijenti.WeatherData;

/**
 * Bean za pregled portfelja i obavljanje svih akcija vezaniz uz portfelje
 * @author Hrvoje
 */
@ManagedBean
@SessionScoped
public class PregledPortfelja implements Serializable {

    @Resource(mappedName = "jms/NWTiS_hrvoreski_1")
    private Queue _NWTiS_hrvoreski_1;
    @Resource(mappedName = "jms/NWTiS_QF_hrvoreski_1")
    private ConnectionFactory _NWTiS_QF_hrvoreski_1;

    @EJB
    private HrvoreskiAdresePortfeljaFacade hrvoreskiAdresePortfeljaFacade;
    @EJB
    private final HrvoreskiMeteoPortfeljFacade portfeljFacade;
    @EJB
    private final MeteoWSKlijent meteoWSKlijent;

    ServletContext cxt;
    private WebKonfiguracija konfig;
    private final Korisnik user;
    private List<HrvoreskiMeteoPortfelj> p;
    private Map<String, String> portfelja;
    private String odabraniMeteoPortfelj;
    private String noviPortfelj;
    private WeatherData meteoPodaci;
    private Map<String, String> adreseMapa;
    private Map<String, String> portfeljiMapa;
    private List<Adresa> adrese;
    private List<String> adreseMapaOdabrano;
    private List<String> portfeljiMapaOdabrano;
    private int maxAdresePortfelja;
    private String novaAdresa;
    private String meteoBlok = "hidden";
    private List<WeatherData> meteoPrikaz;

    public WebKonfiguracija getKonfig() {
        return konfig;
    }

    public void setKonfig(WebKonfiguracija konfig) {
        this.konfig = konfig;
    }

    /**
     * Konstruktor
     */
    public PregledPortfelja() {
        cxt = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        setKonfig((WebKonfiguracija) cxt.getAttribute("konfiguracija"));
        maxAdresePortfelja = konfig.getPortfelji();
        meteoWSKlijent = new MeteoWSKlijent();
        portfeljFacade = new HrvoreskiMeteoPortfeljFacade();
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession sesija = request.getSession();
        user = (Korisnik) sesija.getAttribute("korisnik");
        adreseMapa = dajAdreseMape();
    }

    public List<WeatherData> getMeteoPrikaz() {
        return meteoPrikaz;
    }

    public void setMeteoPrikaz(List<WeatherData> meteoPrikaz) {
        this.meteoPrikaz = meteoPrikaz;
    }

    public String getMeteoBlok() {
        return meteoBlok;
    }

    public void setMeteoBlok(String meteoBlok) {
        this.meteoBlok = meteoBlok;
    }

    public String getNovaAdresa() {
        return novaAdresa;
    }

    public void setNovaAdresa(String novaAdresa) {
        this.novaAdresa = novaAdresa;
    }

    public int getMaxAdresePortfelja() {
        return maxAdresePortfelja;
    }

    public void setMaxAdresePortfelja(int maxAdresePortfelja) {
        this.maxAdresePortfelja = maxAdresePortfelja;
    }

    public void setAdreseMapa(Map<String, String> adreseMapa) {
        this.adreseMapa = adreseMapa;
    }

    public Map<String, String> getPortfeljiMapa() {
        return portfeljiMapa;
    }

    public void setPortfeljiMapa(Map<String, String> portfeljiMapa) {
        this.portfeljiMapa = portfeljiMapa;
    }

    public List<String> getAdreseMapaOdabrano() {
        return adreseMapaOdabrano;
    }

    public void setAdreseMapaOdabrano(List<String> adreseMapaOdabrano) {
        this.adreseMapaOdabrano = adreseMapaOdabrano;
    }

    public List<String> getPortfeljiMapaOdabrano() {
        return portfeljiMapaOdabrano;
    }

    public void setPortfeljiMapaOdabrano(List<String> portfeljiMapaOdabrano) {
        this.portfeljiMapaOdabrano = portfeljiMapaOdabrano;
    }

    public void setPortfelja(Map<String, String> portfelja) {
        this.portfelja = portfelja;
    }

    public String getOdabraniMeteoPortfelj() {
        return odabraniMeteoPortfelj;
    }

    public void setOdabraniMeteoPortfelj(String odabraniMeteoPortfelj) {
        this.odabraniMeteoPortfelj = odabraniMeteoPortfelj;
    }

    public String getNoviPortfelj() {
        return noviPortfelj;
    }

    public void setNoviPortfelj(String noviPortfelj) {
        this.noviPortfelj = noviPortfelj;
    }

    public Map<String, String> getAdreseMapa() {
        return adreseMapa;
    }

    public WeatherData getMeteoPodaci() {
        return meteoPodaci;
    }

    public void setMeteoPodaci(WeatherData meteoPodaci) {
        this.meteoPodaci = meteoPodaci;
    }

    /**
     * Meteo podaci adresa selektiranog portefelja
     *
     * @return
     */
    public String dajMeteoPodatkeZaPortfelj() {
        Map<String, String> adrPort = odabraniPortfeljZaMeteo();
        meteoPrikaz = new ArrayList<>();
        for (String pp : adrPort.keySet()) {
            meteoPodaci = meteoWSKlijent.dajVazeceMeteoPodatkeZaAdresu(pp);
            WeatherData wd = new WeatherData();
            wd.setTemperature(meteoPodaci.getTemperature());
            wd.setHumidity(meteoPodaci.getHumidity());
            wd.setWindSpeed(meteoPodaci.getWindSpeed());
            wd.setPressureSeaLevel(meteoPodaci.getPressureSeaLevel());
            wd.setRainRate(meteoPodaci.getRainRate());
            wd.setSnowRate(meteoPodaci.getSnowRate());
            meteoPrikaz.add(wd);
        }
        meteoBlok = "visible";
        return "";
    }

    /**
     * Sakriva blok sa meteo podacima na pogledu
     *
     * @return null - vraća na isti pogled
     */
    public String dajSeMakni() {
        meteoBlok = "hidden";
        return "";
    }

    /**
     * Stavlja u posredničku mapu sve adrese iz selektiranog portfelja
     *
     * @return lista adresa odabranog portfelja
     */
    public Map<String, String> odabraniPortfeljZaMeteo() {
        Map<String, String> oPort = new TreeMap<>();
        oPort = getPortfeljiMapa();
        return oPort;
    }

    /**
     * Dohvaća sve adrese s meteo servisa
     *
     * @return lista adresa preko meteo servisa
     */
    public Map<String, String> dajAdreseMape() {
        adreseMapa = new TreeMap<>();
        adrese = meteoWSKlijent.dajSveAdrese();
        for (Adresa adresa : adrese) {
            adreseMapa.put(adresa.getAdresa(), Integer.toString((int) adresa.getIdadresa()));
        }
        adreseMapa = sortirajMapu(adreseMapa);
        return adreseMapa;

    }
    /**
     * Povuci ponovo adrese s servera
     * @return lista adresa
     */
    public Map<String, String> osvjezi() {
        adreseMapa = new TreeMap<>();
        adrese = meteoWSKlijent.dajSveAdrese();
        for (Adresa adresa : adrese) {
            adreseMapa.put(adresa.getAdresa(), Integer.toString((int) adresa.getIdadresa()));
        }
        adreseMapa = sortirajMapu(adreseMapa);
        return adreseMapa;
    }

    /**
     * Dohvaća sve portfelje korisnika
     *
     * @return lista portfelja
     */
    public Map<String, String> getPortfelja() {
        p = portfeljFacade.dohvatiPortelj(user.getKorisnik());
        portfelja = new TreeMap<>();
        for (HrvoreskiMeteoPortfelj port : p) {
            portfelja.put(port.getNaziv(), Integer.toString(port.getIdportfelj()));
        }
        portfelja = sortirajMapu(portfelja);
        return portfelja;
    }

    /**
     * Dohvaća sve adrese selektiranog portfelja, ukoliko one postoje naravno
     */
    public void dohvatiAdresePortfelja() {
        HrvoreskiMeteoPortfelj m = new HrvoreskiMeteoPortfelj();
        m.setIdportfelj(Integer.parseInt(odabraniMeteoPortfelj));
        List<HrvoreskiAdresePortfelja> aList = hrvoreskiAdresePortfeljaFacade.dajAdresePortfelja(m);
        portfeljiMapa = new TreeMap<>();
        for (HrvoreskiAdresePortfelja ap : aList) {
            for (Adresa a : adrese) {
                if (a.getIdadresa() == ap.getIdadresa()) {
                    portfeljiMapa.put(a.getAdresa(), Integer.toString((int) a.getIdadresa()));
                }
            }
        }
    }

    /**
     * Dodaje novi portfelj za ulogiranog korisnika
     *
     * @return
     */
    public String dodajPortfelj() {
        if ("".equals(noviPortfelj) || noviPortfelj.isEmpty()) {
            return "";
        } else {
            portfeljFacade.spremiPortfelj(user.getKorisnik(), noviPortfelj);
            noviPortfelj = null;
            noviPortfelj = "";
            return "";
        }
    }
    /**
     * Dodaje listu iz liste adresa u selektirani portfelj i u bazu
     * @return 
     */
    public String dodajAdresu() {
        for (String x : adreseMapaOdabrano) {
            System.out.println("ADRESA ID:" + x);
        }
        if (adreseMapaOdabrano.isEmpty()) {
            return "";
        }
        if (portfeljiMapa.size() + adreseMapaOdabrano.size() > maxAdresePortfelja) {
            System.out.println("Portfelj već sadrži max broj adresa! ");
            return "";
        }

        boolean ima = false;
        for (String odabrana : adreseMapaOdabrano) {
            for (String a : portfeljiMapa.keySet()) {
                if (!odabrana.equals(portfeljiMapa.get(a))) {
                    ima = false;
                } else {
                    ima = true;
                    break;
                }
            }

            //ako je nema dodaj 
            if (ima == false) {
                String naziv = "";
                for (String odabrana1 : adreseMapaOdabrano) {
                    for (String x : adreseMapa.keySet()) {
                        if (odabrana1.equals(adreseMapa.get(x))) {
                            naziv = x;
                            System.out.println("NAZIV ADRESE :" + x);
                        }
                    }
                }
                portfeljiMapa.put(naziv, odabrana);
                //dodaj to u bazu!

                HrvoreskiMeteoPortfelj noviP = new HrvoreskiMeteoPortfelj();
                for (HrvoreskiMeteoPortfelj h : p) {
                    if (h.getIdportfelj() == Integer.parseInt(odabraniMeteoPortfelj)) {
                        noviP = h;
                    }
                }
                HrvoreskiAdresePortfelja nova = new HrvoreskiAdresePortfelja();
                nova.setIdadresa(Integer.parseInt(odabrana));
                nova.setIdportfelj(noviP);
                hrvoreskiAdresePortfeljaFacade.create(nova);
            }
        }
        adreseMapaOdabrano = null;
        dohvatiAdresePortfelja();
        return "";
    }
    /**
     * Obrisat će selektiranu adresu iz portfelja i baze
     * @return 
     */
    public String obrisiAdresu() {
        if (portfeljiMapaOdabrano.isEmpty()) {
            return "";
        }
        for (String h : portfeljiMapaOdabrano) {
            System.out.println("ODABRANE : " + h);
        }
        for (String odabrana : portfeljiMapaOdabrano) {
            portfeljiMapa.values().remove(odabrana);
            for (String x : portfeljiMapa.keySet()) {
                System.out.println("U MAPI : " + x);

            }
            HrvoreskiMeteoPortfelj noviPa = new HrvoreskiMeteoPortfelj();
            for (HrvoreskiMeteoPortfelj ha : p) {
                if (ha.getIdportfelj() == Integer.parseInt(odabraniMeteoPortfelj)) {
                    noviPa = ha;

                }
            }
            HrvoreskiAdresePortfelja hap = new HrvoreskiAdresePortfelja();
            hap.setIdadresa(Integer.parseInt(odabrana));
            hap.setIdportfelj(noviPa);
            hrvoreskiAdresePortfeljaFacade.brisiAdresuPortfelja(hap);
        }
        portfeljiMapaOdabrano = null;
        dohvatiAdresePortfelja();
        return "";
    }

    /**
     * Sortiranje mapa
     *
     * @param adr ulazna
     * @return vraća sortiranu mapu prema HR znakovima
     */
    public LinkedHashMap<String, String> sortirajMapu(Map<String, String> adr) {
        TreeMap<String, String> sorted = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                Locale hrvatski = new Locale("HR");
                Collator usporedi = Collator.getInstance(hrvatski);
                return usporedi.compare(s1, s2);
            }
        });
        for (String k : adr.keySet()) {
            sorted.put(k, adr.get(k));
        }
        LinkedHashMap<String, String> linkedMap = new LinkedHashMap<>(sorted);
        return linkedMap;
    }
    

    private void saljiJMS(JMSAdresaStruktura poruka) throws JMSException {
        Connection connection = null;
        Session session = null;
        try {
            connection = _NWTiS_QF_hrvoreski_1.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer messageProducer = session.createProducer(_NWTiS_hrvoreski_1);
            ObjectMessage o = session.createObjectMessage();
            
            o.setObject(poruka);
            messageProducer.send(o);
            messageProducer.close();

        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (JMSException e) {
                    Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "Nemoguće zatvoriti sesiju!", e);
                }
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    private JMSAdresaStruktura createJMS(String adr) throws JMSException {
        JMSAdresaStruktura tm = new JMSAdresaStruktura(adr);
        return tm;
    }

    public String saljiAdresu() {
        if (novaAdresa.isEmpty() || null == novaAdresa) {
            return "";
        } else {
            String adresa;
            adresa = novaAdresa;
            System.out.println("ADRESA KOJA SE ŠALJE: " + adresa);
            try {
                JMSAdresaStruktura adrJMS = createJMS(adresa);
                saljiJMS(adrJMS);
            } catch (JMSException ex) {
                Logger.getLogger(PregledPortfelja.class.getName()).log(Level.SEVERE, null, ex);
            }
            return "";
        }

    }
    
    
}
