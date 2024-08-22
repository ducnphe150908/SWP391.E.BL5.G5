package controller.Renter;

import model.User;
import model.Account;
import model.News;
import dao.RenterDAO;
import dao.RenterDAO;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class RenterHomeController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        String password = (String) session.getAttribute("password");
        // Retrieve the account object from the session
        Account account = (Account) session.getAttribute("user");
        request.setAttribute("email", email);
        request.setAttribute("password", password);

        // Check if the account object exists in the session
        if (account != null) {
            RenterDAO dao1 = new RenterDAO();
            List<News> listN = dao1.getAllNews();
            request.setAttribute("ListN", listN);

            RenterDAO dao = new RenterDAO();
            List<User> list = dao.getRenterDetailByAccountAndPassword(email, password);
            request.setAttribute("ListRP", list);
            request.getRequestDispatcher("Renter/RenterHome.jsp").forward(request, response);
        } else {
            response.sendRedirect("login.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // This method might be used for handling form submissions in the future
        // For now, you can leave it empty
    }
}
