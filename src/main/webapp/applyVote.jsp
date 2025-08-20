<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.onlineVoting.model.Election" %>
<%@ page session="true" %>



<%
Integer  userId = (Integer ) session.getAttribute("userId");
    String username = (String) session.getAttribute("username");
       System.out.println("session id : " + session.getId()+" is on applyvote");
    if (userId== null || username == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>apply vote</title>
<link rel="stylesheet" href="css/conduct-apply.css">
</head>
<body>
<jsp:include page="navbar.jsp" />
<h1>Apply to Vote</h1>

<form action="applyVote" method="post">
    <label>Enter 6-Digit Referral Code:</label>
    <input type="text" name="referralCode" required>
    <input type="submit" value="Check Election">
</form>
<%
Election election = (Election) request.getAttribute("election");
String error = (String) request.getAttribute("error");
if(election != null){
	
	%>
		 <div class="election-card">
            <div>
            
<table border="0" cellpadding="9" cellspacing="0">
   
    <tr>
        <td style="padding:10px;">Title</td>
        <td style="padding:10px;"><%= election.getTitle() %></td>
    </tr>
    <tr>
        <td style="padding:10px;">Description</td>
        <td style="padding:10px;"><%= election.getDescription() %></td>
    </tr>
    <tr>
        <td style="padding:10px;">Type</td>
        <td style="padding:10px;"><%= election.getType() %></td>
    </tr>
    <tr>
        <td style="padding:10px;">Status</td>
        <td style="padding:10px;"><%= election.getStatus() %></td>
    </tr>
    <tr>
        <td style="padding:10px;">Date</td>
        <td style="padding:10px;"><%= election.getElectionDate() %></td>
    </tr>
    <tr>
        <td style="padding:10px;">Start</td>
        <td style="padding:10px;"><%= election.getStartTime() %></td>
    </tr>
    <tr>
        <td style="padding:10px;">End</td>
        <td style="padding:10px;"><%= election.getEndTime() %></td>
    </tr>
</table>

            </div>
            <% if ("scheduled".equalsIgnoreCase(election.getStatus())) { %>
            <form action="registerVoter" method="post">
                <input type="hidden" name="electionId" value="<%= election.getElectionId() %>">
                <input type="hidden" name="voterId" value="<%= userId %>">
                <input type="submit" class="apply-btn" value="Apply as Voter" >
            </form>
        <% } else { %>
            <p class="status-note">Applications are closed for this election.</p>
        <% } %>
        </div>
	<%
}
else if(error != null){
	
	%>
	<div class="election-card" style="color:red; text-align:center;"> <%= error%> </div>
	<%
}
%>
</body>
</html>