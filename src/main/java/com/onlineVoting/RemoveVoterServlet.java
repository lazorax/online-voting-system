package com.onlineVoting;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.*;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/RemoveVoterServlet")
public class RemoveVoterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String voterIdParam = request.getParameter("voterId");
        String electionIdParam = request.getParameter("electionId");

        if (voterIdParam == null || electionIdParam == null) {
            response.sendRedirect("dashboard?msg=Invalid+parameters");
            return;
        }

        int voterId = Integer.parseInt(voterIdParam);
        int electionId = Integer.parseInt(electionIdParam);

        String deleteQuery = "DELETE FROM election_voters WHERE id = ?";

        try (Connection conn = DButil.getConnection()) {

        	// First, get the election_voters.id for this user in this election
        	String getEvIdQuery = "SELECT id FROM election_voters WHERE voter_id = ? AND election_id = ?";
        	int electionVoterId = -1;
        	try (PreparedStatement ps = conn.prepareStatement(getEvIdQuery)) {
        	    ps.setInt(1, voterId);
        	    ps.setInt(2, electionId);
        	    try (ResultSet rs = ps.executeQuery()) {
        	        if (rs.next()) {
        	            electionVoterId = rs.getInt("id");
        	        } else {
        	            // No such voter registered for this election
        	            response.sendRedirect("MyElectionServlet?electionId=" + electionId + "&msg=Voter+not+registered");
        	            return;
        	        }
        	    }
        	}

        	// Now check if this voter has voted for this election
        	String checkVotes = "SELECT COUNT(*) FROM votes WHERE election_voter_id = ?";
        	try (PreparedStatement ps = conn.prepareStatement(checkVotes)) {
        	    ps.setInt(1, electionVoterId);
        	    try (ResultSet rs = ps.executeQuery()) {
        	        if (rs.next() && rs.getInt(1) > 0) {
        	            // Voter already voted, cannot delete
        	            response.sendRedirect("MyElectionServlet?electionId=" + electionId + "&msg=Voter+has+already+voted");
        	            return;
        	        }
        	    }
        	}

        	// Safe to delete
        	try (PreparedStatement ps = conn.prepareStatement(
        	        "DELETE FROM election_voters WHERE id = ?")) {
        	    ps.setInt(1, electionVoterId);
        	    ps.executeUpdate();
        	}

        	response.sendRedirect("MyElectionServlet?electionId=" + electionId + "&msg=Voter+removed");


        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("MyElectionServlet?electionId=" + electionId + "&msg=Error+removing+voter");
        }

    }
}
