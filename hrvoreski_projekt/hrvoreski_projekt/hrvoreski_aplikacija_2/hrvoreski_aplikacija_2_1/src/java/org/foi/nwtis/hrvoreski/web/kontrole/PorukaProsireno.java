/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.foi.nwtis.hrvoreski.web.kontrole;

import java.util.Date;
import java.util.List;
import javax.mail.Flags;

/**
 *
 * @author Hrvoje
 */
public class PorukaProsireno extends Poruka{
    
    private String sadrzaj;

    public PorukaProsireno(String id, Date poslano, String salje, String predmet, String vrsta, int velicina, int brojPrivitaka, Flags zastavice, List<PrivitakPoruke> privitciPoruke, boolean brisati, boolean procitano, String sadrzaj) {
        super(id, poslano, salje, predmet, vrsta, velicina, brojPrivitaka, zastavice, privitciPoruke, brisati, procitano);
        this.sadrzaj = sadrzaj;
    }

    public String getSadrzaj() {
        return sadrzaj;
    }

    public void setSadrzaj(String sadrzaj) {
        this.sadrzaj = sadrzaj;
    }
    
    
}
