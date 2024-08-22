/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.Owner;

import dao.NewDAO;
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
 * @author quanb
 */
@WebServlet(name = "UpdateNewsController", urlPatterns = {"/updatenews"})
@MultipartConfig
public class UpdateNewsController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

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
            throws ServletException, IOException {

    }

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
        int id_raw =Integer.parseInt(request.getParameter("id"));
//        int id = 0;
//
//        try {
//            id = Integer.parseInt(id_raw);
//        } catch (NumberFormatException e) {
//            request.setAttribute("error", "Invalid ID format");
//            request.getRequestDispatcher("Owner/EditNews.jsp").forward(request, response);
//            return; // Ngừng xử lý nếu ID không hợp lệ
//        }

        String name = request.getParameter("name");
        String description = request.getParameter("description");
        Part imagePart = request.getPart("img");
        String createAt = request.getParameter("date");

        if (name == null || name.isEmpty() || 
            description == null || description.isEmpty() || 
            imagePart == null || 
            createAt == null || createAt.isEmpty()) {
            
            request.setAttribute("error", "All fields are required");
            request.getRequestDispatcher("Owner/EditNews.jsp").forward(request, response);
            return;
        }

        byte[] photo = convertInputStreamToByteArray(imagePart.getInputStream());
        String imgBase64 = Base64.getEncoder().encodeToString(photo);

        NewDAO newsDAO = new NewDAO();
        News news = new News(name, description, imgBase64, createAt);
        news.setNewId(id_raw);

        int result = newsDAO.updateNews(news);
        if (result > 0) {
            response.sendRedirect("displayNews"); // Cập nhật thành công
        } else {
            request.setAttribute("errorMessage", "Error updating news");
            request.getRequestDispatcher("Owner/EditNews.jsp").forward(request, response);
        }
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
}
