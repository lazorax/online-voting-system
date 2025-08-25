package com.onlineVoting;

import java.io.IOException;
import java.util.*;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/dashboard") // every dashboard.jsp redirect should be dashboard
public class Dashboard extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        Integer userId = (Integer) request.getSession().getAttribute("userId");
        System.out.println("is on dashboard servlet");

        if (userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try (Connection conn = DButil.getConnection()) {

            // 1️.1 Update statuses first
            // Ongoing
            String updateOngoing = "UPDATE elections "+
                "SET status = 'ongoing' "+
                "WHERE status = 'scheduled' "+
                  "AND election_date = CURDATE() "+
                  "AND NOW() BETWEEN CONCAT(election_date, ' ', start_time) "+
                                "AND CONCAT(election_date, ' ', end_time)";
            
            conn.prepareStatement(updateOngoing).executeUpdate();

            // Completed
            String updateCompleted = "UPDATE elections "+
                "SET status = 'completed' "+
                "WHERE status IN ('scheduled','ongoing') "+
                  "AND NOW() > CONCAT(election_date, ' ', end_time)";
          
            conn.prepareStatement(updateCompleted).executeUpdate();
            //1.2 update result 
            String updateResult = "UPDATE elections "
            		+ "SET result = 'declared' "
            		+ "WHERE status = 'completed';";
            		
            conn.prepareStatement(updateResult).executeUpdate();
            // 2 Fetch elections created by this user
            String getElectionQuery = "SELECT election_id, title, election_date, status, type , result "+
                "FROM elections "+
                "WHERE conductor_id = ?";

            List<Map<String, Object>> myElections = new ArrayList<>();

            try (PreparedStatement ps = conn.prepareStatement(getElectionQuery)) {
                ps.setInt(1, userId);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Map<String, Object> row = new HashMap<>();
                        row.put("id", rs.getInt("election_id"));
                        row.put("title", rs.getString("title"));
                        row.put("date", rs.getDate("election_date"));
                        row.put("status", rs.getString("status"));
                        row.put("type", rs.getString("type"));
                        row.put("result", rs.getString("result"));
                        myElections.add(row);
                    }
                }
            }

            // 3️⃣ Fetch elections user can vote in
            String registeredQuery = "SELECT e.election_id, e.title, e.election_date, e.status, e.type ,e.result "+
                "FROM elections e "+
                "JOIN election_voters ev ON e.election_id = ev.election_id "+
                "WHERE ev.voter_id = ? ";
          

            List<Map<String, Object>> registeredElections = new ArrayList<>();

            try (PreparedStatement smt = conn.prepareStatement(registeredQuery)) {
                smt.setInt(1, userId);
                try (ResultSet rs = smt.executeQuery()) {
                    while (rs.next()) {
                        Map<String, Object> row = new HashMap<>();
                        row.put("id", rs.getInt("election_id"));
                        row.put("title", rs.getString("title"));
                        row.put("date", rs.getDate("election_date"));
                        row.put("status", rs.getString("status"));
                        row.put("type", rs.getString("type"));
                        row.put("result", rs.getString("result"));
                        registeredElections.add(row);
                    }
                }
            }

            System.out.println("out of dashboard servlet");

            // 4️⃣ Forward to JSP
            request.setAttribute("myElections", myElections);
            request.setAttribute("voteElections", registeredElections);
            request.getRequestDispatcher("dashboard.jsp").forward(request, response);

        } catch (SQLException e) {
            System.out.println("DB Error in Dashboard: " + e.getMessage());
            throw new ServletException(e);
        }
    }
}
