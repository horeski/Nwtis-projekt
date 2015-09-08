/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.hrvoreski.web.zrna;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

/**
 * Omogućuje usporedbu Stringova za prikaz, odnosno filtriranje
 * @author Hrvoje
 */
public class HRComparator implements Comparator<String>{
        
    @Override 
    public int compare(String s1, String s2) {
        Locale hrvatski = new Locale("HR");
        Collator usporedjivac = Collator.getInstance(hrvatski);
        return usporedjivac.compare(s1, s2);
    }

}
