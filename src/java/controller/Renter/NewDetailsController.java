package controller.Renter;

import dao.NewDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.News;

@WebServlet(name="NewDetailsController", urlPatterns={"/newDetails"})
public class NewDetailsController extends HttpServlet {
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String raw_id = request.getParameter("id");
        int id = Integer.parseInt(raw_id);
        
        NewDAO newsDAO = new NewDAO();
        News news = newsDAO.getNewsById(id);
        
        request.setAttribute("n", news);
        request.getRequestDispatcher("Renter/new-details.jsp").forward(request, response); // Forward to JSP for display
        
    } 

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    }

}
