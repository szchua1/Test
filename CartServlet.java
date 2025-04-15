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

@WebServlet("/CartServlet")
public class CartServlet extends HttpServlet {
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer customerId = (Integer) session.getAttribute("customerId");

        // If customerId is null, proceed to cart.jsp without redirecting
        if (customerId != null) {
            try {
                // Fetch cart items for the customer
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

                // Set attributes for cart.jsp
                request.setAttribute("cartItems", cartItems);
                request.setAttribute("products", products);

            } catch (SQLException e) {
                throw new ServletException("Database error while retrieving cart items", e);
            }
        }

        // Forward to cart.jsp regardless of customerId
        request.getRequestDispatcher("cart.jsp").forward(request, response);
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