package servlet;

import da.CartItemDA;
import da.PaymentDA;
import domain.Payment;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ConfirmOrderServlet")
public class ConfirmOrderServlet extends HttpServlet {
    private PaymentDA paymentDA;
    private CartItemDA cartItemDA;

    @Override
    public void init() throws ServletException {
        try {
            paymentDA = new PaymentDA();
            cartItemDA = new CartItemDA();
        } catch (Exception e) {
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
            Payment payment = (Payment) session.getAttribute("payment");
            payment.setPaymentId((int) (System.currentTimeMillis() % Integer.MAX_VALUE));
            payment.setCustomerId(customerId);
            double subtotal = Double.parseDouble(request.getParameter("subtotal"));
            double tax = Double.parseDouble(request.getParameter("tax"));
            double deliveryCharge = Double.parseDouble(request.getParameter("deliveryCharge"));
            payment.setAmount(subtotal + tax + deliveryCharge);
            payment.setPaymentDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            paymentDA.addRecord(payment);

            int cartItemId = 1;
            while (true) {
                domain.CartItem cartItem = cartItemDA.getRecord(cartItemId);
                if (cartItem == null) break;
                if (cartItem.getCustomerId() == customerId) {
                    cartItemDA.deleteRecord(cartItemId);
                }
                cartItemId++;
            }

            session.setAttribute("transactionId", payment.getPaymentId());
            session.setAttribute("deliveryDate", new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000L)));
            response.sendRedirect("confirmation.jsp");
        } catch (Exception e) {
            throw new ServletException("Database error", e);
        }
    }
}