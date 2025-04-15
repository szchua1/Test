package servlet;

import da.CartItemDA;
import da.ProductDA;
import domain.CartItem;
import domain.Product;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/CheckoutServlet")
public class CheckoutServlet extends HttpServlet {
    private CartItemDA cartItemDA;
    private ProductDA productDA;

    @Override
    public void init() throws ServletException {
        try {
            cartItemDA = new CartItemDA();
            productDA = new ProductDA();
        } catch (SQLException e) {
            throw new ServletException("Cannot initialize DA classes", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer customerId = (Integer) session.getAttribute("customerId");

        if (customerId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            // Get cart items for the customer
            List<CartItem> cartItems = cartItemDA.getCartItemsByCustomerId(customerId);
            List<Product> products = new ArrayList<>();

            // Fetch the corresponding products
            if (cartItems != null && !cartItems.isEmpty()) {
                for (CartItem cartItem : cartItems) {
                    Product product = productDA.getRecord(cartItem.getProductId());
                    if (product != null) {
                        products.add(product);
                    }
                }
            }

            // Get values from the request
            double subtotal = Double.parseDouble(request.getParameter("subtotal"));
            double sst = Double.parseDouble(request.getParameter("sst"));
            double shippingCost = Double.parseDouble(request.getParameter("shippingCost"));
            double total = Double.parseDouble(request.getParameter("total"));
            String shippingOption = request.getParameter("shippingOption"); // For reference

            // Set attributes for checkout.jsp
            request.setAttribute("cartItems", cartItems);
            request.setAttribute("products", products);
            request.setAttribute("subtotal", subtotal);
            request.setAttribute("sst", sst);
            request.setAttribute("shippingCost", shippingCost);
            request.setAttribute("total", total);

            // Forward to checkout.jsp
            request.getRequestDispatcher("checkout.jsp").forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Database error while preparing checkout", e);
        }
    }

    @Override
    public void destroy() {
        try {
            if (cartItemDA != null) {
                cartItemDA.shutDown();
            }
            if (productDA != null) {
                productDA.shutDown();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}