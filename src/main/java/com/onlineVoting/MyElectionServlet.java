package com.onlineVoting;

import java.io.IOException;
import java.sql.*;
import java.util.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import com.onlineVoting.model.Candidate;
import com.onlineVoting.model.Voter;

@WebServlet("/MyElectionServlet")
public class MyElectionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String electionIdParam = request.getParameter("electionId");
        if (electionIdParam == null) {
            response.sendRedirect("dashboard?msg=Invalid+Election");
            return;
        }

        int electionId = Integer.parseInt(electionIdParam);

        try (Connection conn = DButil.getConnection()) {
            // Fetch election details
            String electionQuery = "SELECT title, status, election_date, result,ref_code FROM elections WHERE election_id = ?";
            try (PreparedStatement ps = conn.prepareStatement(electionQuery)) {
                ps.setInt(1, electionId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        request.setAttribute("electionName", rs.getString("title"));
                        request.setAttribute("status", rs.getString("status"));
                        request.setAttribute("date", rs.getDate("election_date"));
                        request.setAttribute("result", rs.getString("result"));
                        request.setAttribute("refCode", rs.getString("ref_code"));
                        request.setAttribute("electionId", electionId);
                    }
                }
            }

            // Fetch candidates
            List<Candidate> candidates = new ArrayList<>();
            String candidateQuery = "SELECT candidate_id, fullname, age, education, address, dob, info FROM election_candidates WHERE election_id = ?";
            try (PreparedStatement ps = conn.prepareStatement(candidateQuery)) {
                ps.setInt(1, electionId);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Candidate c = new Candidate();
                        c.setCandidateId(rs.getInt("candidate_id"));
                        c.setFullname(rs.getString("fullname"));
                        c.setAge(rs.getInt("age"));
                        c.setEducation(rs.getString("education"));
                        c.setAddress(rs.getString("address"));
                        c.setDob(rs.getDate("dob"));
                        c.setInfo(rs.getString("info"));
                        candidates.add(c);
                    }
                }
            }
            request.setAttribute("candidates", candidates);

            // Fetch voters
            List<Voter> voters = new ArrayList<>();
            String voterQuery = "SELECT ev.id AS electionVoterId, u.user_id, u.username, ev.registered_at " +
                                "FROM election_voters ev " +
                                "JOIN users u ON ev.voter_id = u.user_id " +
                                "WHERE ev.election_id = ?";
            try (PreparedStatement ps = conn.prepareStatement(voterQuery)) {
                ps.setInt(1, electionId);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                    	Voter v = new Voter();
                    	   v.setId(rs.getInt("electionVoterId"));  // match the alias
                    	    v.setVoterId(rs.getInt("user_id"));     // users table id
                    	    v.setElectionId(electionId);            // you already know electionId
                    	    v.setRegisteredAt(rs.getTimestamp("registered_at"));
                    	    v.setUsername(rs.getString("username")); // join users table
                        voters.add(v);
                    }
                }
            }
            request.setAttribute("voters", voters);
            String msg = request.getParameter("msg");
            // Forward to JSP
            RequestDispatcher rd = request.getRequestDispatcher("election.jsp?"+msg);
            rd.forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("dashboard?msg=Error+loading+election");
        }
    }
}
