package servlet;

import da.CartItemDA;
import domain.CartItem;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/UpdateCartServlet")
public class UpdateCartServlet extends HttpServlet {
    private CartItemDA cartItemDA;

    @Override
    public void init() throws ServletException {
        try {
            cartItemDA = new CartItemDA();
        } catch (SQLException e) {
            throw new ServletException("Cannot initialize CartItemDA", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            // Get parameters
            int cartItemId = Integer.parseInt(request.getParameter("cartItemId"));
            int newQuantity = Integer.parseInt(request.getParameter("quantity"));

            // Fetch the existing cart item
            CartItem cartItem = cartItemDA.getRecord(cartItemId);
            if (cartItem != null) {
                // Update the quantity
                cartItem.setQuantity(newQuantity);
                cartItemDA.updateRecord(cartItem);
            }

            // Redirect back to the cart page to refresh
            response.sendRedirect("CartServlet");

        } catch (SQLException e) {
            throw new ServletException("Database error while updating cart item", e);
        }
    }

    @Override
    public void destroy() {
        try {
            if (cartItemDA != null) {
                cartItemDA.shutDown();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}