/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.Renter;

import dao.PaymentDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import model.Account;
import model.Payment;

/**
 *
 * @author DAT
 */
@WebServlet(name = "CreatePaymentController", urlPatterns = {"/createPayment"})
public class CreatePaymentController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Account account = (Account) req.getSession().getAttribute("user");
        if (account == null) {
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("Renter/createPayment.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Account account = (Account) req.getSession().getAttribute("user");
        if (account == null) {
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        } else {
            PaymentDAO dbPayment = new PaymentDAO();
            String strMoney = req.getParameter("money");
            double money = Double.parseDouble(strMoney);
            Payment payment = new Payment();
            payment.setMoney(money);
            payment.setStatus(0);
            payment.setUserId(account.getUserID());
            dbPayment.insert(payment);
            resp.sendRedirect("paymentList");
        }

    }

}
