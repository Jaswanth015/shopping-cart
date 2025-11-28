<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.shashi.service.impl.*, com.shashi.beans.*, java.util.*" %>

<%
    // -------------------- SESSION CHECK --------------------
    if (session.getAttribute("username") == null) {
        response.sendRedirect("login.jsp?message=Session Expired, Login Again!!");
        return;
    }

    String userType = (String) session.getAttribute("usertype");
    if (userType == null || !"CUSTOMER".equalsIgnoreCase(userType)) {
        response.sendRedirect("login.jsp?message=Access Denied, Login as customer!!");
        return;
    }

    String userName = (String) session.getAttribute("username");

    // -------------------- FETCH PRODUCTS --------------------
    ProductServiceImpl prodDao = new ProductServiceImpl();
    List<ProductBean> products = new ArrayList<>();

    String search = request.getParameter("search");
    String type = request.getParameter("type");
    String message = "All Products";

    if(search != null) {
        products = prodDao.searchAllProducts(search);
        message = "Showing Results for '" + search + "'";
    } else if(type != null) {
        products = prodDao.getAllProductsByType(type);
        message = "Showing Results for '" + type + "'";
    } else {
        products = prodDao.getAllProducts();
    }

    if(products.isEmpty()) {
        message = "No items found for the search '" + (search != null ? search : type) + "'";
        products = prodDao.getAllProducts();
    }
%>

<jsp:include page="header.jsp" />

<div class="text-center" style="color: black; font-size: 14px; font-weight: bold;"><%= message %></div>

<div class="container">
    <div class="row text-center">
        <%
            CartServiceImpl cartDao = new CartServiceImpl();
            for(ProductBean product : products) {
                int cartQty = cartDao.getCartItemCount(userName, product.getProdId());
        %>
        <div class="col-sm-4" style="height: 350px;">
            <div class="thumbnail">
                <img src="./ShowImage?pid=<%= product.getProdId() %>" alt="Product" style="height: 150px; max-width: 180px;">
                <p class="productname"><%= product.getProdName() %></p>
                <%
                    String description = product.getProdInfo();
                    description = description.substring(0, Math.min(description.length(), 100));
                %>
                <p class="productinfo"><%= description %>..</p>
                <p class="price">Rs <%= product.getProdPrice() %></p>

                <form method="post">
                    <%
                        if(cartQty == 0) {
                    %>
                        <button type="submit" formaction="./AddtoCart?uid=<%= userName %>&pid=<%= product.getProdId() %>&pqty=1" class="btn btn-success">Add to Cart</button>
                        &nbsp;&nbsp;&nbsp;
                        <button type="submit" formaction="./AddtoCart?uid=<%= userName %>&pid=<%= product.getProdId() %>&pqty=1" class="btn btn-primary">Buy Now</button>
                    <%
                        } else {
                    %>
                        <button type="submit" formaction="./AddtoCart?uid=<%= userName %>&pid=<%= product.getProdId() %>&pqty=0" class="btn btn-danger">Remove From Cart</button>
                        &nbsp;&nbsp;&nbsp;
                        <button type="submit" formaction="cartDetails.jsp" class="btn btn-success">Checkout</button>
                    <%
                        }
                    %>
                </form>
                <br />
            </div>
        </div>
        <%
            }
        %>
    </div>
</div>

<%@ include file="footer.html"%>
