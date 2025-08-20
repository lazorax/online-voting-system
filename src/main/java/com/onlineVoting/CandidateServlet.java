package com.onlineVoting;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;

import java.sql.*;


@MultipartConfig(maxFileSize = 16177215) // for photo upload
  

@WebServlet("/CandidateServlet")
public class CandidateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CandidateServlet() {
        super();
       
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {

	        int electionId = Integer.parseInt(request.getParameter("electionId"));
	        int totalCandidates = Integer.parseInt(request.getParameter("totalCandidates"));
	        	String refId = request.getParameter("refCode");
	       
	        

	        try(Connection conn = DButil.getConnection()) {
	          
	            String sql = "INSERT INTO election_candidates (fullname, age, education, address, dob, info, image, election_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	            PreparedStatement ps = conn.prepareStatement(sql);

	            for (int i = 1; i <= totalCandidates; i++) {
	                String name = request.getParameter("name" + i);
	                int age = Integer.parseInt(request.getParameter("age" + i));
	                String education = request.getParameter("education" + i);
	                String address = request.getParameter("address" + i);
	                String dob = request.getParameter("dob" + i);
	                String about = request.getParameter("about" + i);

	                Part photoPart = request.getPart("photo" + i);

//	                ps.setInt(1, electionId);
	                ps.setString(1, name);
	                ps.setInt(2, age);
	                ps.setString(3, education);
	                ps.setString(4, address);
	                ps.setDate(5, java.sql.Date.valueOf(dob));
	                ps.setString(6, about);

	                if (photoPart != null) {
	                    ps.setBlob(7, photoPart.getInputStream());
	                } else {
	                    ps.setNull(7, java.sql.Types.BLOB);
	                }
	                ps.setInt(8,electionId);

	                ps.executeUpdate();
	            }

	            response.sendRedirect("dashboard?msg=Election+created+successfully&refcode="+refId);

	        } catch (Exception e) {
	            e.printStackTrace();
	            System.out.println("Error: " + e.getMessage());
	        } 
	    }
}

