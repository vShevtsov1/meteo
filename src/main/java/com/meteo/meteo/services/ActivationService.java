package com.meteo.meteo.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Properties;

@Service
public class ActivationService {

    @Value("${meteo.email.user}")
    private String mailUser;
    @Value("${meteo.email.password}")
    private String mailPassword;
    private final HttpServletRequest request;
    private final TokenServices tokenServices;

    public ActivationService(HttpServletRequest request, TokenServices tokenServices) {
        this.request = request;
        this.tokenServices = tokenServices;
    }

    public String getURLBase(HttpServletRequest request) throws MalformedURLException {
        URL requestURL = new URL(request.getRequestURL().toString());
        String port = requestURL.getPort() == -1 ? "" : ":" + requestURL.getPort();
        return requestURL.getProtocol() + "://" + requestURL.getHost() + port;
    }

    public void sendEmail(String token) throws NoSuchProviderException {
        System.out.println(mailUser);
        System.out.println(mailPassword);
        // Recipient's email ID needs to be mentioned.
        String to = tokenServices.getMail(token);

        // Sender's email ID needs to be mentioned
        String from = mailUser;

        // Assuming you are sending email from through gmails smtp
        String host = "smtp.office365.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtps.starttls.enable","true");
        properties.put("mail.smtps.port", "587");
        properties.put("mail.smtps.host", host);
        //properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtps.auth", "true");

        // Get the Session object.// and pass username and password
        Session session = Session.getDefaultInstance(properties);

        // Used to debug SMTP issues
        session.setDebug(true);
        Transport transport = session.getTransport();

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("Confrim e-mail in meteo-collector app");

            String link = "<h1>To confirm your e-mail, follow this link</h1>" + "<a href = " + getURLBase(request) + "/auth/activate?token="
                    + token + ">click here</a>";
            message.setContent(link, "text/html");
            // Now set the actual message
            System.out.println("sending...");
            // Send message
            transport.connect
                    ("smtp.office365.com", 587, mailUser, mailPassword);

            transport.sendMessage(message,message.getRecipients(Message.RecipientType.TO));
            System.out.println("Sent message successfully....");
            transport.close();
        } catch (MessagingException | MalformedURLException mex) {
            mex.printStackTrace();
        }
    }
}
