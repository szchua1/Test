package servlet;

import da.CartItemDA;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/RemoveFromCartServlet")
public class RemoveFromCartServlet extends HttpServlet {
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
        int cartItemId = Integer.parseInt(request.getParameter("cartItemId"));
        try {
            cartItemDA.deleteRecord(cartItemId);
            response.sendRedirect("CartServlet");
        } catch (Exception e) {
            throw new ServletException("Error removing from cart", e);
        }
    }
}