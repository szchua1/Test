package servlet;

import da.CartItemDA;
import domain.CartItem;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/AddToCartServlet")
public class AddToCartServlet extends HttpServlet {
    private CartItemDA cartItemDA;

    @Override
    public void init() throws ServletException {
        try {
            cartItemDA = new CartItemDA();
        } catch (Exception e) {
            throw new ServletException("Cannot initialize CartItemDA", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer customerId = (Integer) session.getAttribute("customerId");

        int productId = Integer.parseInt(request.getParameter("productId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        try {
            int cartItemId = 1;
            while (cartItemDA.getRecord(cartItemId) != null) {
                cartItemId++;
            }

            CartItem cartItem = new CartItem(cartItemId, customerId != null ? customerId : 0, productId, quantity);
            cartItemDA.addRecord(cartItem);

            response.sendRedirect("products.jsp");
        } catch (Exception e) {
            throw new ServletException("Error adding to cart", e);
        }
    }
}