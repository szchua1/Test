package servlet;

import da.CustomerDA;
import domain.Customer;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private CustomerDA customerDA;

    @Override
    public void init() throws ServletException {
        try {
            customerDA = new CustomerDA();
        } catch (Exception e) {
            throw new ServletException("Cannot initialize CustomerDA", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            Customer customer = null;
            int customerId = 1;
            while (true) {
                Customer temp = customerDA.getRecord(customerId);
                if (temp == null) break;
                if (temp.getEmail().equals(email) && temp.getPassword().equals(password)) {
                    customer = temp;
                    break;
                }
                customerId++;
            }

            if (customer != null) {
                HttpSession session = request.getSession();
                session.setAttribute("customerId", customer.getCustomerId());
                session.setAttribute("customerName", customer.getName());
                response.sendRedirect("index.jsp"); // Redirect to home page after login
            } else {
                request.setAttribute("error", "Invalid email or password");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
    }
}