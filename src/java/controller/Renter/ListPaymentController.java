/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.Renter;

import com.google.gson.Gson;
import dao.PaymentDAO;
import dao.PenaltyDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import model.Account;
import model.Payment;
import model.Penalty;

/**
 *
 * @author DAT
 */
@WebServlet(name = "ListPaymentController", urlPatterns = {"/paymentList"})
public class ListPaymentController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Account account = (Account) req.getSession().getAttribute("user");
        if (account == null) {
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        } else {
            PaymentDAO dbPayment = new PaymentDAO();
            ArrayList<Payment> payments = dbPayment.findByUserId(account.getUserID());

            req.setAttribute("payments", payments);

            Gson gson = new Gson();
            String jsonData = gson.toJson(payments);

            req.setAttribute("dataList", jsonData);
            req.getRequestDispatcher("Renter/list-payment.jsp").forward(req, resp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        
    }

}
