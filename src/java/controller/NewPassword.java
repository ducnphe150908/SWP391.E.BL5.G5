package controller;

import dao.AccountDAO;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/newPassword")
public class NewPassword extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        String newPassword = request.getParameter("password");
        String confPassword = request.getParameter("newpassword");

        RequestDispatcher dispatcher = null;
        if (newPassword != null && confPassword != null && newPassword.equals(confPassword)
                && (newPassword.length() >= 6 && newPassword.length() <= 32)
                && (confPassword.length() >= 6 && confPassword.length() <= 32)) {
            try {
                if (!newPassword.matches("^(?=.*\\d)[A-Za-z\\d]{8,}$")) {
                    request.setAttribute("status", "Please just enter your password at least 8 characters and 1 numbers and do not have"
                            + " any special characters");
                    request.getRequestDispatcher("newPassword.jsp").forward(request, response);
                } else {
                    AccountDAO udbc = new AccountDAO();
                    udbc.updateUserPassword(email, newPassword);
                    request.setAttribute("status", "Reset Success");
                    request.getRequestDispatcher("login.jsp").forward(request, response);;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (newPassword == null || newPassword.isEmpty() && confPassword == null || confPassword.isEmpty()) {
            request.setAttribute("errorMessage", "Please enter your password here");
            request.getRequestDispatcher("newPassword.jsp").forward(request, response);
        } else {
            request.setAttribute("status", "Reset Failed");
            request.getRequestDispatcher("newPassword.jsp").forward(request, response);

        }
    }

}
