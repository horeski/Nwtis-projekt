/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.hrvoreski.kontrole;

import javax.servlet.ServletException;

/**
 * Neuspješna prijava exception
 * @author Hrvoje
 */
public class NeuspjesnaPrijava extends ServletException {

    /**
     * Creates a new instance of
     * <code>NeuspjesnaPrijava</code> without detail message.
     */
    public NeuspjesnaPrijava() {
    }

    /**
     * Constructs an instance of
     * <code>NeuspjesnaPrijava</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public NeuspjesnaPrijava(String msg) {
        super("Opis greške: " + msg);
    }
}
