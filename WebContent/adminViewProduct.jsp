<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.shashi.service.impl.*, com.shashi.beans.*, java.util.*" %>

<%
    // -------------------- SESSION CHECK --------------------
   String userType = (String) session.getAttribute("usertype");
String userName = (String) session.getAttribute("username");

if (userType == null || !userType.equalsIgnoreCase("ADMIN")) {
    response.sendRedirect("login.jsp?message=Access Denied, Login as admin!!");
} else if (userName == null) {
    response.sendRedirect("login.jsp?message=Session Expired, Login Again!!");
}

%>

<!DOCTYPE html>
<html>
<head>
    <title>Admin - View Products</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
</head>
<body>
<jsp:include page="header.jsp" />

<%
    ProductServiceImpl prodDao = new ProductServiceImpl();
    List<ProductBean> products = prodDao.getAllProducts();
%>

<div class="container">
    <h3 class="text-center">All Products</h3>
    <div class="row text-center">
        <%
        for (ProductBean product : products) {
        %>
        <div class="col-sm-4">
            <div class="thumbnail">
                <img src="./ShowImage?pid=<%=product.getProdId()%>" alt="Product" style="height:150px;">
                <p><%=product.getProdName()%></p>
                <p>Rs <%=product.getProdPrice()%></p>
                <form method="post">
                    <button type="submit" formaction="./RemoveProductSrv?prodid=<%=product.getProdId()%>" class="btn btn-danger">Remove</button>
                    <button type="submit" formaction="updateProduct.jsp?prodid=<%=product.getProdId()%>" class="btn btn-primary">Update</button>
                </form>
            </div>
        </div>
        <%
        }
        %>
    </div>
</div>

<jsp:include page="footer.html" />
</body>
</html>
