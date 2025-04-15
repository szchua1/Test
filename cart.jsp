<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, domain.CartItem, domain.Product" %>
<!DOCTYPE html>
<html lang="zxx" class="no-js">
<head>
    <!-- Mobile Specific Meta -->
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Favicon-->
    <link rel="shortcut icon" href="img/fav.png">
    <!-- Author Meta -->
    <meta name="author" content="CodePixar">
    <!-- Meta Description -->
    <meta name="description" content="">
    <!-- Meta Keyword -->
    <meta name="keywords" content="">
    <!-- meta character set -->
    <meta charset="UTF-8">
    <!-- Site Title -->
    <title>Sport and Fitness - Cart</title>

    <!-- CSS -->
    <link rel="stylesheet" href="css/linearicons.css">
    <link rel="stylesheet" href="css/owl.carousel.css">
    <link rel="stylesheet" href="css/font-awesome.min.css">
    <link rel="stylesheet" href="css/themify-icons.css">
    <link rel="stylesheet" href="css/nice-select.css">
    <link rel="stylesheet" href="css/nouislider.min.css">
    <link rel="stylesheet" href="css/bootstrap.css">
    <link rel="stylesheet" href="css/main.css">
    <style>
        .shipping_box {
            margin: 0;
            padding: 0;
        }
        .shipping_box ul.list {
            list-style: none !important;
            padding: 0 !important;
            margin: 0 !important;
        }
        .shipping_box ul.list li {
            display: flex !important;
            align-items: center !important;
            padding: 5px 0 !important; /* Consistent spacing between options */
            font-size: 14px !important;
            line-height: 1.4 !important;
        }
        .shipping_box ul.list li input[type="radio"] {
            width: 12px !important; /* Smaller radio button */
            height: 12px !important; /* Smaller radio button */
            margin: 0 8px 0 0 !important; /* Space between radio button and label */
            vertical-align: middle !important;
        }
        .shipping_box ul.list li.active {
            font-weight: bold;
            color: #ffba00; /* Highlight color for active option */
        }
    </style>
</head>
<body>
    <!-- Start Header Area -->
    <jsp:include page="header.jsp" />
    <!-- End Header Area -->

    <!-- Start Banner Area -->
    <section class="banner-area organic-breadcrumb">
        <div class="container">
            <div class="breadcrumb-banner d-flex flex-wrap align-items-center justify-content-end">
                <div class="col-first">
                    <h1>Shopping Cart</h1>
                    <nav class="d-flex align-items-center">
                        <a href="index.jsp">Home<span class="lnr lnr-arrow-right"></span></a>
                        <a href="CartServlet">Cart</a>
                    </nav>
                </div>
            </div>
        </div>
    </section>
    <!-- End Banner Area -->

    <!--================Cart Area =================-->
    <section class="cart_area">
        <div class="container">
            <div class="cart_inner">
                <div class="table-responsive">
                    <%
                        Integer customerId = (Integer) session.getAttribute("customerId");
                        if (customerId == null) {
                    %>
                        <p>Please <a href="login.jsp">log in</a> to view your cart and proceed to checkout.</p>
                    <%
                        } else {
                            List<CartItem> cartItems = (List<CartItem>) request.getAttribute("cartItems");
                            List<Product> products = (List<Product>) request.getAttribute("products");
                            double subtotal = 0;
                            double sstRate = 0.06; // 6% SST
                            double sst = 0;
                            double shippingCost = 0;
                            String selectedShipping = "standard"; // Default to Standard Shipping

                            if (cartItems == null || cartItems.isEmpty() || products == null || products.isEmpty()) {
                    %>
                        <p>No products added to the cart.</p>
                        <a class="gray_btn" href="products.jsp">Continue Shopping</a>
                    <%
                            } else {
                                // Calculate subtotal
                                for (CartItem cartItem : cartItems) {
                                    for (Product product : products) {
                                        if (cartItem.getProductId() == product.getProductId()) {
                                            double itemTotal = cartItem.getQuantity() * product.getPrice();
                                            subtotal += itemTotal;
                                        }
                                    }
                                }

                                // Calculate SST (6% of subtotal)
                                sst = subtotal * sstRate;

                                // Determine shipping cost and selected option
                                if (subtotal >= 1000) {
                                    shippingCost = 0; // Free shipping for RM 1000 and above
                                    selectedShipping = "free";
                                } else {
                                    shippingCost = 25.00; // Standard shipping
                                    selectedShipping = "standard";
                                }

                                // Calculate total (subtotal + SST + shipping)
                                double total = subtotal + sst + shippingCost;
                    %>
                    <table class="table">
                        <thead>
                            <tr>
                                <th scope="col">Product</th>
                                <th scope="col">Price</th>
                                <th scope="col">Quantity</th>
                                <th scope="col">Total</th>
                                <th scope="col">Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                for (CartItem cartItem : cartItems) {
                                    for (Product product : products) {
                                        if (cartItem.getProductId() == product.getProductId()) {
                                            double itemTotal = cartItem.getQuantity() * product.getPrice();
                            %>
                            <tr>
                                <td>
                                    <div class="media">
                                        <div class="d-flex">
                                            <% if (product.getImage() != null) { %>
                                                <img src="ImageServlet?productId=<%= product.getProductId() %>" alt="Product Image" style="max-width: 100px; max-height: 100px;">
                                            <% } else { %>
                                                <img src="img/cart.jpg" alt="Product Image">
                                            <% } %>
                                        </div>
                                        <div class="media-body">
                                            <p><%= product.getName() %></p>
                                        </div>
                                    </div>
                                </td>
                                <td>
                                    <h5>RM <%= String.format("%.2f", product.getPrice()) %></h5>
                                </td>
                                <td>
                                    <div class="product_count">
                                        <form action="UpdateCartServlet" method="post" id="updateForm_<%= cartItem.getCartItemId() %>">
                                            <input type="hidden" name="cartItemId" value="<%= cartItem.getCartItemId() %>">
                                            <input type="text" name="quantity" id="sst_<%= cartItem.getCartItemId() %>" maxlength="12" value="<%= cartItem.getQuantity() %>" title="Quantity:" class="input-text qty" readonly>
                                            <button type="button" onclick="updateQuantity('<%= cartItem.getCartItemId() %>', 1)" class="increase items-count"><i class="lnr lnr-chevron-up"></i></button>
                                            <button type="button" onclick="updateQuantity('<%= cartItem.getCartItemId() %>', -1)" class="reduced items-count"><i class="lnr lnr-chevron-down"></i></button>
                                        </form>
                                    </div>
                                </td>
                                <td>
                                    <h5>RM <%= String.format("%.2f", itemTotal) %></h5>
                                </td>
                                <td>
                                    <form action="RemoveFromCartServlet" method="post">
                                        <input type="hidden" name="cartItemId" value="<%= cartItem.getCartItemId() %>">
                                        <button type="submit" class="gray_btn">Remove</button>
                                    </form>
                                </td>
                            </tr>
                            <%
                                        }
                                    }
                                }
                            %>                           
                            <tr>
                                <td></td>
                                <td></td>
                                <td>
                                    <h5>Subtotal</h5>
                                </td>
                                <td>
                                    <h5>RM <%= String.format("%.2f", subtotal) %></h5>
                                </td>
                                <td></td>
                            </tr>
                            <tr>
                                <td></td>
                                <td></td>
                                <td>
                                    <h5>SST (6%)</h5>
                                </td>
                                <td>
                                    <h5>RM <%= String.format("%.2f", sst) %></h5>
                                </td>
                                <td></td>
                            </tr>                 
                            <tr class="shipping_area">
                                <td></td>
                                <td></td>
                                <td>
                                    <h5>Shipping</h5>
                                </td>
                                <td></td>
                                <td>
                                    <br/>
                                    <div class="shipping_box">
                                        <ul class="list">
                                            <li class="<%= selectedShipping.equals("free") ? "active" : "" %>">
                                                <input type="radio" id="freeShipping" name="shippingOption" value="free" <%= selectedShipping.equals("free") ? "checked" : "" %> disabled>
                                                <label for="freeShipping">Free Shipping (RM 1000 and above): RM 0.00</label>
                                            </li>
                                            <li class="<%= selectedShipping.equals("standard") ? "active" : "" %>">
                                                <input type="radio" id="standardShipping" name="shippingOption" value="standard" <%= selectedShipping.equals("standard") ? "checked" : "" %> disabled>
                                                <label for="standardShipping">Standard Shipping: RM 25.00</label>
                                            </li>
                                        </ul>
                                    </div>
                                </td>                             
                            </tr>
                            <tr>
                                <td></td>
                                <td></td>
                                <td>
                                    <h5>Total</h5>
                                </td>
                                <td>
                                    <h5>RM <%= String.format("%.2f", total) %></h5>
                                </td>
                                <td></td>
                            </tr>
                            <tr class="out_button_area">
                                <td></td>
                                <td></td>
                                <td></td>
                                <td>
                                    <div class="checkout_btn_inner d-flex align-items-center">
                                        <a class="gray_btn" href="products.jsp">Continue Shopping</a>
                                        <form action="CheckoutServlet" method="post">
                                            <input type="hidden" name="subtotal" value="<%= subtotal %>">
                                            <input type="hidden" name="sst" value="<%= sst %>">
                                            <input type="hidden" name="shippingCost" value="<%= shippingCost %>">
                                            <input type="hidden" name="total" value="<%= total %>">
                                            <input type="hidden" name="shippingOption" value="<%= selectedShipping %>">
                                            <button type="submit" class="primary-btn">Proceed to Checkout</button>
                                        </form>
                                    </div>
                                </td>
                                <td></td>
                            </tr>
                        </tbody>
                    </table>
                    <%
                            }
                        }
                    %>
                </div>
            </div>
        </div>
    </section>
    <!--================End Cart Area =================-->

    <!-- Start Footer Area -->
    <jsp:include page="footer.jsp" />
    <!-- End Footer Area -->

    <script src="js/vendor/jquery-2.2.4.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js" integrity="sha384-b/U6ypiBEHpOf/4+1nzFpr53nxSS+GLCkfwBdFNTxtclqqenISfwAzpKaMNFNmj4" crossorigin="anonymous"></script>
    <script src="js/vendor/bootstrap.min.js"></script>
    <script src="js/jquery.ajaxchimp.min.js"></script>
    <script src="js/jquery.nice-select.min.js"></script>
    <script src="js/jquery.sticky.js"></script>
    <script src="js/nouislider.min.js"></script>
    <script src="js/jquery.magnific-popup.min.js"></script>
    <script src="js/owl.carousel.min.js"></script>
    <!--gmaps Js-->
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCjCGmQ0Uq4exrzdcL6rvxywDDOvfAu6eE"></script>
    <script src="js/gmaps.min.js"></script>
    <script src="js/main.js"></script>
    <script>
        function updateQuantity(cartItemId, change) {
            var input = document.getElementById('sst_' + cartItemId);
            var currentQty = parseInt(input.value);
            var newQty = currentQty + change;

            if (newQty < 1) {
                return; // Prevent quantity from going below 1
            }

            input.value = newQty;
            document.getElementById('updateForm_' + cartItemId).submit();
        }
    </script>
</body>
</html>