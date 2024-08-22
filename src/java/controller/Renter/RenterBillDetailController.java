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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Bill;
import model.Renter;

/**
 *
 * @author nguye
 */
@WebServlet(name="RenterBillDetailController", urlPatterns={"/RenterBillDetailController"})
public class RenterBillDetailController extends HttpServlet {
   
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
            out.println("<title>Servlet RenterBillDetailController</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RenterBillDetailController at " + request.getContextPath () + "</h1>");
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
        BillDAO dao = new BillDAO();
        PaymentDAO payment = new PaymentDAO();
        RenterDAO renterDAO = new RenterDAO();
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
        // get renter's balance
        double balanceRenter = payment.displayMoney(renterID);
        
        int roomID = Integer.parseInt(request.getParameter("roomID"));
        
        // total bill for room
        Bill renterBillDetail = dao.getBillDetailByRoomID(roomID);
        
        if (renterBillDetail != null) {
        // format date
        SimpleDateFormat sdf_input = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        SimpleDateFormat sdf_output = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try {
            date = sdf_input.parse(renterBillDetail.getDeadline());
        } catch (ParseException ex) {
            Logger.getLogger(RenterBillDetailController.class.getName()).log(Level.SEVERE, null, ex);
        }
        String deadLine = sdf_output.format(date);
        renterBillDetail.setDeadline(deadLine);
        request.setAttribute("bill", renterBillDetail);
        }
        
        request.setAttribute("roomID", roomID);
        request.setAttribute("balanceRenter", balanceRenter);
        request.getRequestDispatcher("Renter/RenterBillDetail.jsp").forward(request, response);
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
        processRequest(request, response);
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
