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
import jakarta.servlet.http.HttpSession;

@WebServlet("/CastVoteServlet")
public class CastVoteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int electionId = Integer.parseInt(request.getParameter("electionId"));
        int candidateId = Integer.parseInt(request.getParameter("candidateId"));

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int userId = (int) session.getAttribute("userId"); // actual voter_id from users table

        try (Connection conn = DButil.getConnection()) {

            // Fetch the id from election_voters table (unique per election + voter)
            PreparedStatement voterStmt = conn.prepareStatement(
                    "SELECT id FROM election_voters WHERE election_id = ? AND voter_id = ?");
            voterStmt.setInt(1, electionId);
            voterStmt.setInt(2, userId);
            ResultSet rsVoter = voterStmt.executeQuery();

            if (!rsVoter.next()) {
                response.sendRedirect("dashboard?msg=You+are+not+registered+for+this+election");
                return;
            }

            int electionVoterId = rsVoter.getInt("id");

            // Check if this election_voter_id already voted
            PreparedStatement checkStmt = conn.prepareStatement(
                    "SELECT vote_id FROM votes WHERE election_voter_id = ?");
            checkStmt.setInt(1, electionVoterId);
            ResultSet rsCheck = checkStmt.executeQuery();

            if (rsCheck.next()) {
                response.sendRedirect("dashboard?msg=You+have+already+voted+in+this+election");
                return;
            }

            // Insert the vote
            PreparedStatement insertStmt = conn.prepareStatement(
                    "INSERT INTO votes (election_voter_id, election_id , candidate_id) VALUES (?, ?,?)");
            insertStmt.setInt(1, electionVoterId);
            insertStmt.setInt(2, electionId);
            insertStmt.setInt(3, candidateId);
            insertStmt.executeUpdate();

            response.sendRedirect("dashboard?msg=Vote+cast+successfully");

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("dashboard?msg=Error+casting+vote");
        }
    }
}
