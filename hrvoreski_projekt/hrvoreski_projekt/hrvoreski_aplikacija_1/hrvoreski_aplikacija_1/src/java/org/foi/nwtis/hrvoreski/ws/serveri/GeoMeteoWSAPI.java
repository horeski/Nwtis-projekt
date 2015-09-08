/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.hrvoreski.ws.serveri;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServlet;
import org.foi.nwtis.hrvoreski.konfiguracije.WebKonfiguracija;
import org.foi.nwtis.hrvoreski.web.podaci.Adresa;
import org.foi.nwtis.hrvoreski.web.podaci.Location;
import org.foi.nwtis.hrvoreski.web.podaci.WeatherData;
import org.foi.nwtis.hrvoreski.web.slusaci.Kontekst;

/**
 * Klasa koja obavlja sve pomoćne funkcije za web servis
 * @author Hrvoje
 */
public class GeoMeteoWSAPI extends HttpServlet {

    private WebKonfiguracija config;
    private String korisnickoIme;
    private String korisnickaLozinka;
    private String host;
    private String nazivBaze;
    private String driver;

    public GeoMeteoWSAPI() {
        Kontekst ctx = Kontekst.getInstance();
        config = (WebKonfiguracija) ctx.getKontekst().getAttribute("konfiguracija");
        this.driver = config.getBpKonfiguracija().getDriver_database();
        korisnickoIme = config.getBpKonfiguracija().getUser_username();
        korisnickaLozinka = config.getBpKonfiguracija().getUser_password();
        nazivBaze = config.getBpKonfiguracija().getUser_database();
        host = config.getBpKonfiguracija().getServer_database();
    }

    private Connection otvoriVezu() throws ClassNotFoundException, Exception, SQLException {
        Connection veza;
        if (config == null) {
            throw new Exception();
        }
        String url = host + nazivBaze;
        Class.forName(driver);
        veza = DriverManager.getConnection(url, korisnickoIme, korisnickaLozinka);
        return veza;

    }

    List<Adresa> dohvatiSveAdrese() {
        String sql = "SELECT * FROM hrvoreski_adrese";
        try {
            Connection con = otvoriVezu();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            List<Adresa> lista = new ArrayList<>();
            while (rs.next()) {
                long idAdresa = rs.getInt("idAdresa");
                String adresa = rs.getString("adresa");
                Location lokacija = new Location(rs.getString("latitude"), rs.getString("longitude"));
                Adresa a = new Adresa(idAdresa, adresa, lokacija);
                lista.add(a);
            }
            return lista;
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
            return null;
        } catch (Exception ex) {
            Logger.getLogger(GeoMeteoWSAPI.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    Integer dohvatiIdAdrese(String adresa) {
        String sql = "SELECT idAdresa FROM hrvoreski_adrese WHERE adresa = '" + adresa + "'";
        try {
            Connection con = otvoriVezu();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                return Integer.valueOf(rs.getString("idAdresa"));
            } else {
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(GeoMeteoWSAPI.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (Exception ex) {
            Logger.getLogger(GeoMeteoWSAPI.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    WeatherData dohvatiZadnjeMeteoPodatkeZaAdresu(Integer idAdresa) {
        String sql = "SELECT * FROM hrvoreski_meteo WHERE idAdresa = " + idAdresa;
        try {
            Connection con = otvoriVezu();
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery(sql);
            WeatherData wd = new WeatherData();

            if (rs.last()) {
                wd.setTemperature(rs.getFloat("temperatura"));
                wd.setPressureSeaLevel(rs.getFloat("tlak"));
                wd.setHumidity(rs.getFloat("vlaga"));
                wd.setWindSpeed(rs.getFloat("vjetar"));
                wd.setRainRate(rs.getFloat("kisa"));
                wd.setSnowRate(rs.getFloat("snijeg"));
                return wd;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(GeoMeteoWSAPI.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (Exception ex) {
            Logger.getLogger(GeoMeteoWSAPI.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    private List<WeatherData> dajMeteoPodatke(String upit) {
        try {
            Connection con = otvoriVezu();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(upit);
            List<WeatherData> wdLista = new ArrayList<>();

            while (rs.next()) {
                WeatherData wd = new WeatherData();
                wd.setTemperature(rs.getFloat("temperatura"));
                wd.setPressureSeaLevel(rs.getFloat("tlak"));
                wd.setHumidity(rs.getFloat("vlaga"));
                wd.setWindSpeed(rs.getFloat("vjetar"));
                wd.setRainRate(rs.getFloat("kisa"));
                wd.setSnowRate(rs.getFloat("snijeg"));
                wdLista.add(wd);
            }
            if (!wdLista.isEmpty()) {
                return wdLista;
            } else {
                return null;
            }

        } catch (SQLException ex) {
            Logger.getLogger(GeoMeteoWSAPI.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (Exception ex) {
            Logger.getLogger(GeoMeteoWSAPI.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    List<WeatherData> dohvatiSveMeteoPodatkeZaAdresu(Integer idAdresa) {
        String sql = "SELECT * FROM hrvoreski_meteo WHERE idAdresa = " + idAdresa;
        return dajMeteoPodatke(sql);
    }

    List<Adresa> dajRangListuAdresa(int topN) {
        String sql = "SELECT hrvoreski_adrese.idAdresa, hrvoreski_adrese.adresa, hrvoreski_adrese.latitude, "
                + "hrvoreski_adrese.longitude, COUNT(hrvoreski_meteo.idAdresa) as broj "
                + "FROM hrvoreski_meteo "
                + "INNER JOIN hrvoreski_adrese ON hrvoreski_meteo.idAdresa = hrvoreski_adrese.idAdresa "
                + "GROUP BY hrvoreski_meteo.idAdresa HAVING (COUNT(hrvoreski_meteo.idAdresa)>1) "
                + "ORDER BY broj DESC "
                + "LIMIT " + topN;
        try {
            Connection con = otvoriVezu();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            List<Adresa> lista = new ArrayList<>();
            while (rs.next()) {
                long idAdresa = rs.getInt("idAdresa");
                String adresa = rs.getString("adresa");
                Location lokacija = new Location(rs.getString("latitude"), rs.getString("longitude"));
                Adresa a = new Adresa(idAdresa, adresa, lokacija);
                lista.add(a);
            }
            return lista;
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
            return null;
        } catch (Exception ex) {
            Logger.getLogger(GeoMeteoWSAPI.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    List<WeatherData> dajZadnjihNMeteoPodatakaZaAdresu(String adresa, int n) {
        String sql = "SELECT temperatura, tlak, vlaga, vjetar, kisa, snijeg FROM hrvoreski_meteo "
                + "INNER JOIN hrvoreski_adrese ON hrvoreski_meteo.idAdresa = hrvoreski_adrese.idAdresa "
                + "WHERE hrvoreski_adrese.adresa = '" + adresa
                + "' ORDER BY hrvoreski_meteo.idMeteo DESC "
                + "LIMIT " + n;
        return dajMeteoPodatke(sql);
    }

    List<WeatherData> getIntervalMeteoPodatke(String adresa, String odDatum, String doDatum) {        
        String sql = "SELECT * FROM hrvoreski_meteo INNER JOIN hrvoreski_adrese ON hrvoreski_meteo.idAdresa"
                + " = hrvoreski_adrese.idAdresa WHERE hrvoreski_adrese.adresa = "
                + "'" + adresa + "' AND vrijeme BETWEEN '" + odDatum + "' AND '" + doDatum + "' ";
        return dajMeteoPodatke(sql);
        
        
    }

}
