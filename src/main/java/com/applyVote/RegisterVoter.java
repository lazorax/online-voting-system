package com.applyVote;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.onlineVoting.DButil;

import java.io.IOException;
import java.sql.*;
import java.util.Date;
@WebServlet("/registerVoter")

public class RegisterVoter extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		int electionId = Integer.parseInt(request.getParameter("electionId"));
		int voterId = Integer.parseInt(request.getParameter("voterId"));
		
		try(Connection conn = DButil.getConnection()){
			//this is to check if voters has already registered 
			String checkQuery = "SELECT COUNT(*) FROM election_voters WHERE voter_id = ? AND election_id = ?";
			try(PreparedStatement smt = conn.prepareStatement(checkQuery)){
				smt.setInt(1, voterId);
				smt.setInt(2, electionId);
				try(ResultSet rs = smt.executeQuery()){
					 if (rs.next() && rs.getInt(1) > 0) {
				            System.out.println("Voter already registered for this election.");
				            request.setAttribute("error", "Has alreay registered !");
				            request.getRequestDispatcher("applyVote.jsp").forward(request, response);
					 }
					 else {
						//this is for insertion
							String insertQuery = "INSERT INTO election_voters (voter_id, election_id, registered_at) VALUES (?, ?, ?)";

							try(PreparedStatement ps = conn.prepareStatement(insertQuery)){
								ps.setInt(1, voterId);
								ps.setInt(2, electionId);
								Date now = new Date();
								Timestamp timestamp = new Timestamp(now.getTime());
								ps.setTimestamp(3, timestamp);
								int rowsAffected =ps.executeUpdate();
									if(rowsAffected>0) {
										System.out.println("voter registered successfully");
										response.sendRedirect("dashboard?msg=Voter+Registered+Successfully+!");
									}
								}
						 
					 }
					
				}
			}
			
			

		}
		catch(SQLException e) {

			System.out.println(e.getMessage());
			
		}
	}
	

}
