package controller;

import dao.AccountDAO;
import model.*;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import javax.mail.Authenticator;

@WebServlet(name="ForgotPassword", urlPatterns={"/forgotPassword"})
public class ForgotPassword extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("forgotPassword.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String email = request.getParameter("email");

        RequestDispatcher dispatcher = null;

        int otpvalueLength = 6;

        Random rand = new Random();

        String string = "0123456789";

        String randomOtp = "";

        HttpSession mySession = request.getSession();

        if (email != null || !email.equals("")) {
            for (int i = 0; i < otpvalueLength; i++) {
                char c = string.charAt(rand.nextInt(string.length()));
                randomOtp = randomOtp + c;
            }
            Cookie cookie = new Cookie("otpR", randomOtp);

            cookie.setMaxAge(5 * 60);

            response.addCookie(cookie);

            String to = email;

            // Get the session object
            String email1 = "ngtatricuong2003@gmail.com";
            String appPassword = "ckpe rukz brui pvef";
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
                        + "<p><a href='http://localhost:9999/SWP391.E.BL5.G55/enterotp.jsp' style='display: inline-block; padding: 10px 20px; font-size: 16px; color: #ffffff; background-color: #007bff; text-decoration: none; border-radius: 5px;'>Click here to enter OTP</a></p>"
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
            //=======================================================

            dispatcher = request.getRequestDispatcher("forgotPassword.jsp");
            request.setAttribute("message", "OTP is sent to your email id");
            mySession.setAttribute("otp", randomOtp);
            mySession.setAttribute("email", email);
            dispatcher.forward(request, response);
        }

    }

}
