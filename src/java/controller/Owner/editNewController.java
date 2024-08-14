/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.Owner;

import dao.EditNewsDAO;
import dao.NewDAO;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Base64;
import model.News;

/**
 *
 * @author quan
 */
@WebServlet(name="UpdateNewsController", urlPatterns={"/editNews"})
@MultipartConfig
public class editNewController extends HttpServlet {
   
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
            out.println("<title>Servlet EditNews</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EditNews at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 
    
    private EditNewsDAO newsDAO;

    public void init() {
        newsDAO = new EditNewsDAO();
    }

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
        response.setContentType("text/html;charset=UTF-8");
        
        int id = Integer.parseInt(request.getParameter("id"));
        
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        Part imagePart = request.getPart("img");
        String createAt = request.getParameter("creatAt");

        if (title == null || title.isEmpty() || description == null || description.isEmpty() || imagePart == null || createAt == null || createAt.isEmpty()) {
            request.setAttribute("error", "All fields are required.");
            request.getRequestDispatcher("Owner/DisplayNews.jsp").forward(request, response);
            return;
        }

        byte[] photo = convertInputStreamToByteArray(imagePart.getInputStream());
        String imgBase64 = Base64.getEncoder().encodeToString(photo);

        News news = new News(id,title, description, imgBase64, createAt);
        int result = newsDAO.updateNews(news);
        
        if (result > 0) {
            response.sendRedirect("home.jsp");
        } else {
            request.setAttribute("errorMessage", "Error updating news");
            RequestDispatcher dispatcher = request.getRequestDispatcher("Owner/DisplayNews.jsp");
            dispatcher.forward(request, response);
        }
    }

    private byte[] convertInputStreamToByteArray(InputStream inputStream) throws IOException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            return outputStream.toByteArray();
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
