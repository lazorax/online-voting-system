package com.onlineVoting;

import java.io.IOException;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebServlet("/login")

public class loginServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request,HttpServletResponse response ) 
	throws ServletException,IOException{
		
	    System.out.println(">>> Received POST request for login");
	    String email = request.getParameter("email");
	    String password = request.getParameter("password");
	    
	    try (Connection conn = DButil.getConnection()){
	        String checkSql = "SELECT * FROM users WHERE email = ?";
	        PreparedStatement stmt = conn.prepareStatement(checkSql);
	        stmt.setString(1,email);
	        
	        stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String dbPassword = rs.getString("password");
                String username = rs.getString("user_name");
                int voterId = rs.getInt("voterId");
                

                if (password.equals(dbPassword)) {
                    // ✅ Password matched
                    HttpSession session = request.getSession();
                    session.setAttribute("voterId", voterId);
                    session.setAttribute("username", username);
                    System.out.println(">>> LOgin successful" + username);
                    response.sendRedirect("dashboard.jsp");
                } else {
                    // ❌ Wrong password
                    request.setAttribute("error", "Invalid email or password");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                }
            } else {
                // ❌ No such email
                request.setAttribute("error", "User not found");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
	        
	        
	    }catch(SQLException e) {
	    	  e.printStackTrace();
	            request.setAttribute("error", "Database error");
	            request.getRequestDispatcher("login.jsp").forward(request, response);
	    	
	    }
		
	}

}
