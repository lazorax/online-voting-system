package com.onlineVoting;

import java.io.IOException;
import java.util.*;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/dashboard")//every dashboard.jsp redirect should be dashboard

public class Dashboard extends HttpServlet{
	

	
	private static final long serialVersionUID = 1L;

	//fetch data from the 
	protected void doGet(HttpServletRequest request, HttpServletResponse response )
	throws IOException, ServletException{
		Integer userId = (Integer) request.getSession().getAttribute("userId");
		System.out.println("is on dashboard servlet");
		if(userId == null) {
			response.sendRedirect("login.jsp");
			return;
		}
		//check from elections table if conductor id matches to userId send that data to dashboard.jsp
		
		try(Connection conn = DButil.getConnection()){
			//election created by user
			String getElectionQuery = "SELECT election_id, title, election_date, status, type"
					+ " FROM elections WHERE conductor_id = ?";
			
			//array of dictionary/{}/row/map which contains key val pairs of colname and data
			List<Map<String,Object>> myElections = new ArrayList<>();
			
			try(PreparedStatement ps = conn.prepareStatement(getElectionQuery)){
		
				ps.setInt(1, userId);
				try(ResultSet rs = ps.executeQuery()){
					while(rs.next()) {
						Map<String,Object> row = new HashMap<>();
						row.put("id",rs.getInt("election_id"));
						row.put("title",rs.getString("title"));
						row.put("date",rs.getDate("election_date"));
						row.put("status",rs.getString("status"));
						row.put("type",rs.getString("type"));
						
						myElections.add(row);
					}
				}
			}
			
			//elections user can vote for 
			String registeredQuery = "SELECT e.election_id, e.title, e.election_date, e.status, e.type "
					+ "FROM elections e "
					+ "JOIN election_voters ev ON e.election_id = ev.election_id "
					+ "WHERE ev.voter_id = ?";
			
			List<Map<String,Object>> registerdElections = new ArrayList<>();
			
			try(PreparedStatement smt = conn.prepareStatement(registeredQuery)){
			
				smt.setInt(1, userId);
				try(ResultSet rs = smt.executeQuery()){
					while(rs.next()) {
						Map<String,Object> row = new HashMap<>();
						row.put("id",rs.getInt("e.election_id"));
						row.put("title",rs.getString("title"));
						row.put("date",rs.getDate("election_date"));
						row.put("status",rs.getString("status"));
						row.put("type",rs.getString("type"));
						
						registerdElections.add(row);
					}
				}
				
			}
			System.out.println("out of  dashboard servlet");
		      request.setAttribute("myElections", myElections);
	            request.setAttribute("voteElections", registerdElections);
	            request.getRequestDispatcher("dashboard.jsp").forward(request, response);
			
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	

}
