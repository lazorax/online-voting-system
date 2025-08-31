package com.conductElection;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import java.sql.*;
import java.util.Random;
import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/conductElection")


public class ConductElectionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Function to generate 6-digit referral code
    private String generateRefCode() {
    	  long currentTimeMillis = System.currentTimeMillis(); // current time in ms
    	    int salt = new Random().nextInt(900) + 100; // 3 digit salt 
    	    long combined = currentTimeMillis + salt;
    	    int refCode = (int) (Math.abs(combined) % 900000) + 100000;//make it 6 digit

    	    return String.valueOf(refCode);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String title = request.getParameter("title");
        String about = request.getParameter("about");
        
        String datestr = request.getParameter("date");
        LocalDate localDate = LocalDate.parse(datestr);  // ISO format by default
        java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);
        
        int candidates = Integer.parseInt(request.getParameter("candidates"));
        int conductorId = Integer.parseInt(request.getParameter("conductorId"));
        int voters = Integer.parseInt(request.getParameter("voters"));
        String type = request.getParameter("electionType");
        String refId = generateRefCode();
        
        Connection con = null;
        PreparedStatement ps = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/college", "root","root");

            String sql = "INSERT INTO elections (title, description, election_date, no_of_candidates, no_of_voters, type, ref_code,conductor_id) VALUES (?, ?, ?, ?, ?, ?,?,?)";
            ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, title);
            ps.setString(2, about);
            ps.setDate(3, sqlDate);
            ps.setInt(4, candidates);
            ps.setInt(5, voters);
            ps.setString(6, type);
            ps.setString(7, refId);
            ps.setInt(8,conductorId);
            int rowsInserted = ps.executeUpdate();

            if (rowsInserted > 0) {
               //Optional: display or forward the refId if needed
               //System.out.println("election added "+ refId);
               //response.sendRedirect("dashboard?msg=Election+created+successfully&refcode="+refId);
            	
            	//vaibhav's code - retrieving ele id and passing to candidate.jsp
                ResultSet rs = ps.getGeneratedKeys(); // ✅ now get generated keys
                if (rs.next()) {
                    int electionId = rs.getInt(1);

                    // Forward values directly to candidateForm.jsp
                    request.setAttribute("electionId", electionId);
                    request.setAttribute("noOfCandidates", candidates);
                    request.setAttribute("refcode", refId);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("candidateForm.jsp");
                    dispatcher.forward(request, response);
                } else {
                		System.out.println("Error retrieving election ID.");
                }
          
          
            } else {
            		response.sendRedirect("conductElection.jsp?msg=Failed+to+create+election");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("conductElection.jsp?msg=Database+error:+"
                    + java.net.URLEncoder.encode(e.getMessage(), "UTF-8")
                    + "&status=error");
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }
    }
}

