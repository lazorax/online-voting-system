package com.onlineVoting;

import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/ShowImageServlet")
public class ShowImageServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        try (Connection conn = DButil.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT image FROM election_candidates WHERE candidate_id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                byte[] imgData = rs.getBytes("image");
                if (imgData != null) {
                    response.setContentType("image/jpeg");
                    OutputStream os = response.getOutputStream();
                    os.write(imgData);
                    os.flush();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
