package com.alexsgi;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

import java.io.IOException;
import java.math.BigInteger;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class CollatzConjectureSimulation {

    private static final BigInteger BIG_INTEGER_THREE = BigInteger.valueOf(3);
    private static final BigInteger BIG_INTEGER_FOUR = BigInteger.valueOf(4);
    private static final BigInteger BIG_INTEGER_MAX_VALUE = new BigInteger("99999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999");
    private static final int hopsAmount = Integer.MAX_VALUE;
    private static Session session;
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss dd.MM.yyyy");
    private static Properties userData;
    private static boolean sendMails = true;

    // TODO: Stackoverflow error 273 digits
    public static void main(String[] args) throws MessagingException, IOException {
        System.out.println("Initializing mail session ...");
        loadProperty();
        initMail();
        System.out.println("Sending start mail ...");
        sendMail(String.format("Starting CC simulation at %s by %s on %s", dtf.format(LocalDateTime.now()), System.getProperty("user.name"), InetAddress.getLocalHost().getHostName()));
        System.out.println("Mail init finished");
        BigInteger n = new BigInteger("24061550197982");
        while (n.compareTo(BIG_INTEGER_MAX_VALUE) < 1) {
            if (startSimulation(n).compareTo(BigInteger.ZERO) == 0) {
                System.out.println("Found a possible candidate: " + n);
                sendMail("Potential candidate found: " + n + "<br>" + "The number didn't get into a loop even after " + hopsAmount + " hops.");
            }
            n = n.add(BigInteger.ONE);
        }
        System.out.println("Finished calculations, shutting down ...");
        sendMail("End of the calculations with n = " + n + " at " + dtf.format(LocalDateTime.now()));
    }

    public static BigInteger startSimulation(BigInteger bigInteger) {
        System.out.print("Input: " + bigInteger + " ");
        return run(bigInteger, hopsAmount);
    }

    private static BigInteger run(BigInteger n, int hops) {
        BigInteger remain;
        remain = n.mod(BigInteger.TWO);
        n = (remain.equals(BigInteger.ZERO)) ? n.divide(BigInteger.TWO) : n.multiply(BIG_INTEGER_THREE).add(BigInteger.ONE);
        if (n.compareTo(BIG_INTEGER_FOUR) == 0 || n.compareTo(BigInteger.TWO) == 0 || n.compareTo(BigInteger.ONE) == 0) {
            System.out.println("ends in loop (took " + (hopsAmount - hops) + " hops)");
            return n;
        } else {
            if (hops <= 0) {
                System.out.println("for this amount of hops a loop wasn't found (max. " + hopsAmount + " hops)");
                return BigInteger.ZERO;
            }
            return run(n, --hops);
        }
    }

    private static void initMail() {
        if(!sendMails) return;
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", userData.getProperty("host"));
        prop.put("mail.smtp.port", userData.getProperty("port"));
        prop.put("mail.smtp.ssl.trust", userData.getProperty("host"));

        session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userData.getProperty("user"), userData.getProperty("password"));
            }
        });
    }

    private static void loadProperty() throws IOException {
        userData = new Properties();
        userData.load(CollatzConjectureSimulation.class.getClassLoader().getResourceAsStream("mail.prop"));
        sendMails = Boolean.parseBoolean(userData.getProperty("enabled"));
    }

    private static void sendMail(String msg) throws MessagingException {
        if(!sendMails) return;
        if (session == null) throw new MessagingException("Session not initialized");
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(userData.getProperty("user")));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(userData.getProperty("receiver")));
        message.setSubject(userData.getProperty("subject"));
        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(msg, "text/html");
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);
        message.setContent(multipart);
        Transport.send(message);
    }

}
