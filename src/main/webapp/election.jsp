<%@ page import="java.util.List" %>
<%@ page import="com.onlineVoting.model.Candidate" %>
<%@ page import="com.onlineVoting.model.Voter" %>
<%
    List<Candidate> candidates = (List<Candidate>) request.getAttribute("candidates");
    List<Voter> voters = (List<Voter>) request.getAttribute("voters");
    String electionName = (String) request.getAttribute("electionName");
    Integer electionId = (Integer) request.getAttribute("electionId");
%>


<!DOCTYPE html>
<html>
<head>
    <title>Election Management</title>
    <link rel="stylesheet" href="css/dashboard.css">
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }
        th, td {
            padding: 8px;
            border: 1px solid #ddd;
        }
        .candidate-photo {
            width: 80px;
            height: 80px;
            object-fit: cover;
        }
    </style>
</head>
<body>
<jsp:include page="navbar.jsp" />
<%
	String msg = request.getParameter("msg");
	String refCode = request.getParameter("refcode");
	if (msg != null) {%>
		<div id="msgBox" class="msg-box msg-success">
		<%=msg%><br></div>
	<% }
	%>
	
    <h2>Election: <%= electionName %> :  ${refCode}</h2>

    <!-- Candidate List -->
    <h3>Candidates</h3>
    <table>
        <tr>
            <th>Photo</th>
            <th>Full Name</th>
            <th>Age</th>
            <th>Education</th>
            <th>Info</th>
        </tr>
        <%
            if (candidates != null) {
                for (Candidate c : candidates) {
        %>
        <tr>
            <td><img src="ShowImageServlet?id=<%= c.getCandidateId() %>" class="candidate-photo"></td>
            <td><%= c.getFullname() %></td>
            <td><%= c.getAge() %></td>
            <td><%= c.getEducation() %></td>
            <td><%= c.getInfo() %></td>
        </tr>
        <%
                }
            }
        %>
    </table>

    <!-- Voter List -->
    <h3>Registered Voters</h3>
    <table>
        <tr>
            <th>Voter ID</th>
            <th>Voter Name</th>
            <th>Registered At</th>
            <th>Action</th>
        </tr>
        <%
            if (voters != null) {
                for (Voter v : voters) {
        %>
        <tr>
            <td><%= v.getVoterId() %></td>
            <td><%= v.getUsername() %></td>
            <td><%= v.getRegisteredAt() %></td>
            <td>
                <form action="RemoveVoterServlet" method="post">
                    <input type="hidden" name="voterId" value="<%= v.getVoterId() %>">
                    <input type="hidden" name="electionId" value="<%= electionId %>">
                    <button type="submit">Remove</button>
                </form>
            </td>
        </tr>
        <%
                }
            }
        %>
    </table>
</body>
</html>
