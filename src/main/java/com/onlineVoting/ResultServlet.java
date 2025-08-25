package com.onlineVoting;

import java.io.IOException;
import java.sql.*;
import java.util.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ResultServlet")
public class ResultServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        Integer userId = (Integer) request.getSession().getAttribute("userId");
        if (userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String electionIdParam = request.getParameter("electionId");
        if (electionIdParam == null || electionIdParam.isEmpty()) {
            response.sendRedirect("dashboard?msg=Invalid election ID");
            return;
        }

        int electionId = Integer.parseInt(electionIdParam);

        try (Connection conn = DButil.getConnection()) {

            // 1️⃣ Fetch candidates for this election
            String candidateQuery = "SELECT candidate_id, fullname FROM election_candidates WHERE election_id = ?";
            Map<Integer, String> candidates = new LinkedHashMap<>();
            try (PreparedStatement ps = conn.prepareStatement(candidateQuery)) {
                ps.setInt(1, electionId);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        candidates.put(rs.getInt("candidate_id"), rs.getString("fullname"));
                    }
                }
            }

            // 2️⃣ Count votes for each candidate
            Map<String, Integer> voteCounts = new LinkedHashMap<>();
            String voteQuery = "SELECT COUNT(*) AS vote_count FROM votes WHERE election_id = ? AND candidate_id = ?";
            for (Map.Entry<Integer, String> entry : candidates.entrySet()) {
                int candidateId = entry.getKey();
                String candidateName = entry.getValue();

                try (PreparedStatement psVote = conn.prepareStatement(voteQuery)) {
                    psVote.setInt(1, electionId);
                    psVote.setInt(2, candidateId);
                    try (ResultSet rsVote = psVote.executeQuery()) {
                        int count = 0;
                        if (rsVote.next()) {
                            count = rsVote.getInt("vote_count");
                        }
                        voteCounts.put(candidateName, count);
                    }
                }
            }

            // 3️⃣ Forward results to JSP
            request.setAttribute("electionId", electionId);
            request.setAttribute("voteCounts", voteCounts);
            request.getRequestDispatcher("result.jsp").forward(request, response);

        } catch (SQLException e) {
            System.out.println("DB Error in ResultServlet: " + e.getMessage());
            throw new ServletException(e);
        }
    }
}
