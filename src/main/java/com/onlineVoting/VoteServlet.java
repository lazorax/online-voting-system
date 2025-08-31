package com.onlineVoting;

import java.io.IOException;
import java.sql.*;
import java.util.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import com.onlineVoting.model.Candidate;

@WebServlet("/VoteServlet")
public class VoteServlet extends HttpServlet {
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
            PreparedStatement ps = conn.prepareStatement("SELECT title, status FROM elections WHERE election_id = ?");
            ps.setInt(1, electionId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String status = rs.getString("status");
                String title = rs.getString("title");

                if (!"ongoing".equalsIgnoreCase(status)) {
                    response.sendRedirect("dashboard?msg=Election+not+ongoing");
                    return;
                }

                List<Candidate> candidates = new ArrayList<>();
                PreparedStatement ps2 = conn.prepareStatement(
                	    "SELECT candidate_id, fullname, age, education, info, image FROM election_candidates WHERE election_id=?");

                ps2.setInt(1, electionId);
                ResultSet rs2 = ps2.executeQuery();

                while (rs2.next()) {
                    Candidate c = new Candidate();
                    c.setCandidateId(rs2.getInt("candidate_id"));
                    c.setFullname(rs2.getString("fullname"));
                    c.setAge(rs2.getInt("age"));
                    c.setEducation(rs2.getString("education"));
                    c.setInfo(rs2.getString("info"));
                    c.setPhoto(rs2.getBytes("image"));
                    candidates.add(c);
                }

                request.setAttribute("candidates", candidates);
                request.setAttribute("electionTitle", title);
                request.setAttribute("electionId", electionId);
                request.getRequestDispatcher("vote.jsp").forward(request, response);
            } else {
                response.sendRedirect("dashboard?msg=Election+not+found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("dashboard?msg=Error+loading+election");
        }
    }
}
