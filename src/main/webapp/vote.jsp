<%@ page import="java.util.List" %>
<%@ page import="com.onlineVoting.model.Candidate" %>
<%
    List<Candidate> candidates = (List<Candidate>) request.getAttribute("candidates");
    Integer electionId = (Integer) request.getAttribute("electionId");
    Integer userId = (Integer) session.getAttribute("userId");
    String username = (String) session.getAttribute("username");

    if (userId == null || username == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Cast Your Vote</title>
   <link rel="stylesheet" href="css/vote.css">
</head>
<body>
    <jsp:include page="navbar.jsp" />

    <div class="vote-container">
        <h2>Vote for Your Candidate</h2>

        <form action="CastVoteServlet" method="post">
            <input type="hidden" name="electionId" value="<%= electionId %>">

            <div class="candidate-grid">
            <%
                if (candidates != null) {
                    for (Candidate c : candidates) {
            %>
                <div class="candidate-card">
                    <%
                        if (c.getPhoto() != null && c.getPhoto().length > 0) {
                    %>
                        <img src="ShowImageServlet?id=<%= c.getCandidateId() %>" alt="Candidate Photo" class="candidate-photo">
                    <%
                        } else {
                    %>
                        <div style="width:130px; height:130px; display:flex; align-items:center; justify-content:center; background:#ccc; border-radius:50%; margin-bottom:12px;">Candidate</div>
                    <%
                        }
                    %>
                    <h3><%= c.getFullname() %></h3>
                    <p>Age: <%= c.getAge() %></p>
                    <p>Education: <%= c.getEducation() %></p>
                    <p>Info: <%= c.getInfo() %></p>

                    <input type="radio" id="candidate_<%= c.getCandidateId() %>" name="candidateId" value="<%= c.getCandidateId() %>" required>
                    <label for="candidate_<%= c.getCandidateId() %>">Select</label>
                </div>
            <%
                    }
                } else {
            %>
                <p>No candidates available for this election.</p>
            <%
                }
            %>
            </div>

            <button type="submit" class="vote-btn">Submit Vote</button>
        </form>
    </div>
</body>
</html>
