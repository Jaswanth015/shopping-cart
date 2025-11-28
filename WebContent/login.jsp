<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
</head>
<body>

<h2>Login Page</h2>

<!-- Show message -->
<% 
    String msg = request.getParameter("message");
    if (msg != null) { 
%>
    <p style="color:red;"><%= msg %></p>
<% } %>

<form action="LoginSrv" method="post">

    <label>Email:</label><br>
    <input type="text" name="username" required><br><br>

    <label>Password:</label><br>
    <input type="password" name="password" required><br><br>

    <label>Login As:</label><br>
    <select name="usertype">
        <option value="customer">Customer</option>
        <option value="admin">Admin</option>
    </select><br><br>

    <input type="submit" value="Login">

</form>

</body>
</html>
