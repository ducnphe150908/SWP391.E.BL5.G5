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
import util.Sendmail;
//doaiwjdoajdawijodaw
@WebServlet(name = "ForgotPassword", urlPatterns = {"/forgotPassword"})
public class ForgotPassword extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("forgotPassword.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AccountDAO dao = new AccountDAO();
        String email = request.getParameter("email");
        boolean checkmail = dao.checkMailRegister(email);
        RequestDispatcher dispatcher = null;

        int otpvalueLength = 6;

        Random rand = new Random();
        String string = "0123456789";
        String randomOtp = "";
        HttpSession mySession = request.getSession();

        if (checkmail) {
            for (int i = 0; i < otpvalueLength; i++) {
                char c = string.charAt(rand.nextInt(string.length()));
                randomOtp = randomOtp + c;
            }
            
            Cookie cookie = new Cookie("otpR", randomOtp);
            cookie.setMaxAge(5 * 60);
            response.addCookie(cookie);
            
            String to = email;
            Sendmail.sendMailForgotPassword(to, randomOtp);
            
            request.setAttribute("message", "OTP is sent to your email id");
            mySession.setAttribute("otp", randomOtp);
            mySession.setAttribute("email", email);
            request.getRequestDispatcher("forgotPassword.jsp").forward(request, response);
        }else{
             request.setAttribute("message", "Email have not register");
             request.getRequestDispatcher("forgotPassword.jsp").forward(request, response);
        }
    }
}
