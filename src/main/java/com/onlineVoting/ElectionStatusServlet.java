package com.onlineVoting;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/ElectionStatusServlet")
public class ElectionStatusServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action"); 
        int electionId = Integer.parseInt(request.getParameter("electionId"));
        
        String newStatus = null;
        if ("start".equals(action)) {
            newStatus = "ongoing";
        } else if ("end".equals(action)) {
            newStatus = "completed";
        }
        
        if (newStatus != null) {
            try (Connection conn = DButil.getConnection()) {
                String sql = "UPDATE elections SET status = ? WHERE election_id = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, newStatus);
                ps.setInt(2, electionId);
                ps.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Redirect back to election details page
        response.sendRedirect("MyElectionServlet?electionId=" + electionId);
    }
}

