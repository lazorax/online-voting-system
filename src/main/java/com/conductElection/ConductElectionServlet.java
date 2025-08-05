package com.conductElection;

import jakarta.servlet.ServletException;
import java.sql.*;
import java.util.Random;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class conductElection
 */
@WebServlet("/conductElection")
//package com.onlinevoting.controller;

//import java.io.*;
//import javax.servlet.*;
//import javax.servlet.http.*;

public class ConductElectionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Function to generate 6-digit referral code
    private String generateRefCode() {
        Random rand = new Random();
        int code = 100000 + rand.nextInt(900000);  // ensures 6 digits
        return String.valueOf(code);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String title = request.getParameter("title");
        String about = request.getParameter("about");
        
        String datestr = request.getParameter("date");
        LocalDate localDate = LocalDate.parse(datestr);  // ISO format by default
        java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);
        
        int candidates = Integer.parseInt(request.getParameter("candidates"));
        int voters = Integer.parseInt(request.getParameter("voters"));
        String type = request.getParameter("electionType");
        String refId = generateRefCode();
        
        Connection con = null;
        PreparedStatement ps = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/college", "root", "Vaibhav@root");

            String sql = "INSERT INTO elections (title, description, election_date, no_of_candidates, no_of_voters, type, ref_code) VALUES (?, ?, ?, ?, ?, ?,?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, title);
            ps.setString(2, about);
            ps.setDate(3, sqlDate);

            
            ps.setInt(4, candidates);
            ps.setInt(5, voters);
            ps.setString(6, type);
            ps.setString(7, refId);

            int rowsInserted = ps.executeUpdate();

            if (rowsInserted > 0) {
                // Optional: display or forward the refId if needed
                System.out.println("Successfull!!!!!!!!");
            } else {
                response.getWriter().println("Error saving election.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Database Error: " + e.getMessage());
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }
    }
}

