/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.hrvoreski.mb;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import org.foi.nwtis.hrvoreski.sb.MessagesStorage;
import org.foi.nwtis.hrvoreski.web.kontrole.JMSPorukaStruktura;

/**
 * Message bean koji prima poruke JMS email objekta
 *
 * @author Hrvoje
 */
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/NWTiS_hrvoreski_2")
})
public class EmailMessageBean implements MessageListener {

    @EJB
    private MessagesStorage messagesStorage;

    /**
     * Konstruktor
     */
    public EmailMessageBean() {
    }
    
    @Override
    public void onMessage(Message message) {
        System.out.println("Pristigla nova JMS poruka za email !");

        try {
            ObjectMessage om = (ObjectMessage) message;
            JMSPorukaStruktura poruka = (JMSPorukaStruktura) om.getObject();
            int id = (int) new Date().getTime();
            poruka.setBrisi(id);
            messagesStorage.dodajPoruku(poruka);
        } catch (JMSException ex) {
            Logger.getLogger(EmailMessageBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
   
}
