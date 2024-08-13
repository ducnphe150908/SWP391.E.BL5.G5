/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
<<<<<<<< HEAD:src/java/controller/LogoutController.java
package controller;
========

package controller.Owner;
>>>>>>>> b4ff39cc17ec447d4f8ed11f96fcc71aadf8b3e8:src/java/controller/Owner/EditNewsFormController.java

import dao.EditNewsDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
<<<<<<<< HEAD:src/java/controller/LogoutController.java
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "LogoutController", urlPatterns = {"/logout"})
public class LogoutController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
========
import model.News;

/**
 *
 * @author pc
 */
@WebServlet(name="EditNewsController", urlPatterns={"/formeditnews"})
public class EditNewsFormController extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
>>>>>>>> b4ff39cc17ec447d4f8ed11f96fcc71aadf8b3e8:src/java/controller/Owner/EditNewsFormController.java
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
<<<<<<<< HEAD:src/java/controller/LogoutController.java
         HttpSession session = request.getSession(false); // Fetch session if exists
        if (session != null) {
            session.invalidate(); // Invalidate the session
========
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet EditNewsController</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EditNewsController at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
>>>>>>>> b4ff39cc17ec447d4f8ed11f96fcc71aadf8b3e8:src/java/controller/Owner/EditNewsFormController.java
        }
        response.sendRedirect("login.jsp");
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
<<<<<<<< HEAD:src/java/controller/LogoutController.java
            throws ServletException, IOException {
        processRequest(request, response);
    }
========
    throws ServletException, IOException {
        int id =Integer.parseInt( request.getParameter("id"));
        EditNewsDAO dao = new EditNewsDAO();
        News news = dao.getNewsById(id);
        
        request.setAttribute("news", news);
        request.getRequestDispatcher("Owner/Editnews.jsp").forward(request, response);
     
    } 
>>>>>>>> b4ff39cc17ec447d4f8ed11f96fcc71aadf8b3e8:src/java/controller/Owner/EditNewsFormController.java

    /**
     * Handles the HTTP <code>POST</code> method.
     *
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
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
