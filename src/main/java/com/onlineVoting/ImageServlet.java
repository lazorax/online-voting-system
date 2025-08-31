package com.onlineVoting;

import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/candidateImage")
public class ImageServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int candidateId = Integer.parseInt(request.getParameter("id"));

        try (Connection conn = DButil.getConnection()) {
            String sql = "SELECT image FROM election_candidates WHERE candidate_id = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, candidateId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        byte[] imgData = rs.getBytes("image");
                        if (imgData != null) {
                            response.setContentType("image/jpeg");
                            OutputStream out = response.getOutputStream();
                            out.write(imgData);
                            out.flush();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
