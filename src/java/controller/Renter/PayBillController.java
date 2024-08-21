/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.Renter;

import dao.BillDAO;
import dao.PaymentDAO;
import dao.RenterDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Renter;

/**
 *
 * @author nguye
 */
@WebServlet(name="PayBillController", urlPatterns={"/PayBillController"})
public class PayBillController extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet PayBillController</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PayBillController at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
          PaymentDAO dao = new PaymentDAO();
        RenterDAO renterDAO = new RenterDAO();
        BillDAO billDAO = new BillDAO();
        HttpSession session = request.getSession();
        //get email and password 
        String email = (String) session.getAttribute("email");
        String password = (String) session.getAttribute("password");

        // get renterID for balance
        int renterID = 0;
        List<Renter> getBasicRenter = (List<Renter>) renterDAO.getRenterDetail(email, password);
        for (Renter renter : getBasicRenter) {
            renterID = renter.getRenterID();
        }

        if (renterID != 0) {
            double balanceRenter = Double.parseDouble(request.getParameter("balance"));
            double totalAmountToPay = Double.parseDouble(request.getParameter("totalAmountToPay"));
            double moneyAfterPayment = balanceRenter - totalAmountToPay;
            // get from renterbilldetail.jsp
            int roomID = Integer.parseInt(request.getParameter("roomID"));
            int updateMoney = dao.updateRenterMoney(renterID, moneyAfterPayment);
            int updateBillAfterPayment = billDAO.updateBillAfterPayment(roomID);

            if (updateMoney > 0 && updateBillAfterPayment > 0) {                
                request.getSession().setAttribute("paymentSuccess", true);
            } else {
                request.getSession().setAttribute("paymentSuccess", false);
            }
            response.sendRedirect("RenterRoomDetail");
        }
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
