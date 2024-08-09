/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import static com.sun.activation.registries.LogSupport.log;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Sendmail {

    static final String email1 = "ngtatricuong2003@gmail.com";
    static final String appPassword = "ckpe rukz brui pvef";

    public static void sendMailForgotPassword(String to, String randomOtp) throws UnsupportedEncodingException {
        String smtpHost = "smtp.gmail.com";
        int smtpPort = 587;

        Properties props = new Properties();
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", smtpPort);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email1, appPassword);
            }
        };
        Session session = Session.getInstance(props, auth);
        try {
            MimeMessage message = new MimeMessage(session);

            // Set sender and recipient
            message.setFrom(new InternetAddress(email1, "Admin"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set subject
            message.setSubject("Please Verify Your Account");

            // Create HTML email body
            String emailBody = "<html>"
                    + "<body>"
                    + "<p>Hi,</p>"
                    + "<p>For security reasons, please verify your account with the OTP below:</p>"
                    + "<p><strong>Your OTP is:</strong> <span style='font-size: 24px; font-weight: bold; color: #007bff;'>" + randomOtp + "</span></p>"
                    + "<p>This OTP code is valid for 5 minutes. Please do not share this code with anyone.</p>"
                    + "<p><a href='http://localhost:9999/WebApplication5/enterotp.jsp' style='display: inline-block; padding: 10px 20px; font-size: 16px; color: #ffffff; background-color: #007bff; text-decoration: none; border-radius: 5px;'>Click here to enter OTP</a></p>"
                    + "<p>If you did not request this, please ignore this email.</p>"
                    + "<p>Best regards,<br>ApartmentAdmin</p>"
                    + "</body>"
                    + "</html>";

            // Set the content of the email
            message.setContent(emailBody, "text/html; charset=UTF-8");

            // Send the email
            Transport.send(message);

            // Confirm successful sending
            System.out.println("Message sent successfully");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static boolean sendEmail(String to, String code) throws UnsupportedEncodingException {
        // email account information
        String smtpHost = "smtp.gmail.com";
        int smtpPort = 587;

        Properties props = new Properties();
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", smtpPort);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email1, appPassword);
            }
        };

        Session session = Session.getInstance(props, auth);

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(email1, "Admin"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject("Verification");
            message.setText("Your verification code is: " + code);

            Transport.send(message);

            return true;
        } catch (MessagingException e) {
            e.printStackTrace(); // Log error details to the console
            log("Error sending email: " + e.getMessage(), e); // Write errors to the servlet log
            return false;
        }
    }

}
