package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "ValidateOtp", urlPatterns = {"/ValidateOtp"})
public class ValidateOtp extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession mySession = request.getSession();
        String value = request.getParameter("otp");
        String otp = (String) mySession.getAttribute("otp");
        Cookie[] cookies = request.getCookies();
        String otpR = "";
        for (Cookie cooky : cookies) {
            if (cooky.getName().equals("otpR")) {
                otpR = cooky.getValue();
                break;
            }
        }
        RequestDispatcher dispatcher = null;

        if (value.equals(otpR)) {
            request.setAttribute("email", request.getParameter("email"));
            request.getRequestDispatcher("newPassword.jsp").forward(request, response);

        } else {
            request.setAttribute("message", "wrong otp");
            request.getRequestDispatcher("enterotp.jsp").forward(request, response);
        }
    }
}
