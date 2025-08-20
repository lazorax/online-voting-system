package com.applyVote;

import java.io.IOException;
import java.sql.*;
import com.onlineVoting.DButil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.onlineVoting.model.Election;


@WebServlet("/applyVote")

public class applyVote extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request,HttpServletResponse response ) 
	throws ServletException,IOException{
		
	    System.out.println(">>> Received POST request for applyvote");
	    String refCodeInput = request.getParameter("referralCode");
	    System.out.println(refCodeInput);
	    
	    try(Connection conn = DButil.getConnection()){
			 PreparedStatement ps = conn.prepareStatement("select * from elections where ref_code = (?)");
			 ps.setString(1,refCodeInput);
			 ResultSet rs = ps.executeQuery();
			 
			 if(rs.next()){
				 Election election = new Election();
	                election.setElectionId(rs.getInt("election_id"));
	                election.setTitle(rs.getString("title"));
	                election.setRefCode(rs.getString("ref_code"));
	                election.setDescription(rs.getString("description"));
	                election.setType(rs.getString("type"));
	                election.setStatus(rs.getString("status"));
	                election.setElectionDate(rs.getDate("election_date"));
	                election.setStartTime(rs.getTime("start_time"));
	                election.setEndTime(rs.getTime("end_time"));

	                request.setAttribute("election", election);
	                request.getRequestDispatcher("applyVote.jsp").forward(request, response);
					 
				 
			 }
			 else{
				 request.setAttribute("error", "wrong ref-code");
                 request.getRequestDispatcher("applyVote.jsp").forward(request, response);
				
			 }
			
			 
			 
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}
		
	}

}
