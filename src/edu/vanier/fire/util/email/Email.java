/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vanier.fire.util.email;

import com.lynden.gmapsfx.javascript.object.LatLong;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Email {

    /**
     * method to send an email to the contacts if location is under danger
     * @param latLong the location set by user
     */
    public static void send(LatLong latLong) {
        double lat = latLong.getLatitude();
        double lon = latLong.getLongitude();
        final String username = "dorafightclub@gmail.com";
        final String password = "dorafc123";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            for (Integer key : new ContactController().getAllContacts().keySet()) {
                message.addRecipients(Message.RecipientType.TO, 
                        InternetAddress.parse(new ContactController().getAllContacts().get(key).getEmail()));
            }
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(new InfoController().getInfo().getEmail()));
            message.setSubject("Fire Watcher Notification");
            message.setText(String.format("Location: %.2d, %.2d is in projected fire path", lat, lon));

            Transport.send(message);

            System.out.println("Email sent");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
