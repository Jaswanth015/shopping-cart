package com.shashi.srv;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.shashi.beans.UserBean;
import com.shashi.service.impl.UserServiceImpl;

@WebServlet("/LoginSrv")
public class LoginSrv extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("username");
        String password = request.getParameter("password");

        // FIX null issue: safe usertype handling
        String userType = request.getParameter("usertype");
        if (userType == null) userType = "customer";
        userType = userType.toUpperCase();

        UserServiceImpl udao = new UserServiceImpl();
        String status = udao.isValidCredential(email, password);

        HttpSession session = request.getSession();

        if (status.equalsIgnoreCase("valid")) {

            UserBean user = udao.getUserDetails(email, password);

            if (user == null) {
                response.sendRedirect("login.jsp?message=User not found");
                return;
            }

            // Validate selected role and DB role
            if (!user.getUserType().equalsIgnoreCase(userType)) {
                response.sendRedirect("login.jsp?message=Invalid User Type");
                return;
            }

            // Store required session attributes
            session.setAttribute("userdata", user);
            session.setAttribute("username", user.getEmail()); // Use actual email!
            session.setAttribute("password", password);        // VERY IMPORTANT FIX
            session.setAttribute("usertype", user.getUserType().toUpperCase());

            if (user.getUserType().equalsIgnoreCase("ADMIN")) {
                response.sendRedirect("adminViewProduct.jsp");
            } else {
                response.sendRedirect("userHome.jsp");
            }

        } else {
            response.sendRedirect("login.jsp?message=" + status);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
