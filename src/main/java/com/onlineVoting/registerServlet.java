package com.onlineVoting;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class registerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    public void init() throws ServletException {
        System.out.println(">>> SignupServlet initialized");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println(">>> Received POST request for register");

        String email = request.getParameter("email"); 
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        if (!password.equals(confirmPassword)) {
        	System.out.println(">>> Passwords do not match");
            request.setAttribute("error", "Passwords do not match");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        try (Connection conn = DButil.getConnection()) {
            String checkSql = "SELECT user_id FROM users WHERE email = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setString(1, email);
                ResultSet rs = checkStmt.executeQuery();

                if (rs.next()) {
                    System.out.println(">>> user already exists");

                    request.setAttribute("error", "user already exists");
                    request.getRequestDispatcher("register.jsp").forward(request, response);
                    return;
                }
            }

            String sql = "INSERT INTO users (email, userName, password) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, email);
                stmt.setString(2, name);
                stmt.setString(3, password);
                stmt.executeUpdate();
                System.out.println(">>> Registration successful");
                request.setAttribute("success", "Registration successful! Please login.");
                request.getRequestDispatcher("login.jsp").forward(request, response);


            }
        } catch (SQLException e) {
            e.printStackTrace(); 
            System.out.println(">>> Database error occurred");
            request.setAttribute("error", "Database error occurred");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }
}
