<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page session="true" %>
<%
Integer  userId = (Integer ) session.getAttribute("userId");
    String username = (String) session.getAttribute("username");
       System.out.println("session id : " + session.getId()+" is on conduct election");
    if (userId== null || username == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Conduct Election</title>
 	<link rel="stylesheet" href="css/conduct-apply.css">
</head>
<body>
<jsp:include page="navbar.jsp" />
 <% 
        String msg = request.getParameter("msg");
         
        if (msg != null) { 
    %>
        <div id="msgBox" class="msg-box msg-error">
            <%= msg %>
        </div>
    <% } %>
    
    <h1>Conduct New Election</h1>
    <form action="conductElection" method="post">
        <label for="title">Title:</label>
        <input type="text" id="title" name="title" required>

        <label for="about">About the Election:</label>
        <textarea id="about" name="about" rows="4" required></textarea>

        <label for="date">Date:</label>
        <input type="date" id="electionDate" name="date" required>
<script>
document.getElementById("electionDate").addEventListener("change", function() {
    const selectedDate = new Date(this.value);
    const today = new Date();

    // Normalize to remove time (compare only date)
    today.setHours(0, 0, 0, 0);
    selectedDate.setHours(0, 0, 0, 0)
   
    const dateDiffrence = Math.floor((selectedDate-today)/(1000 * 60 * 60 * 24))
     
    if (dateDiffrence<1) {
        alert("Election date must be at least 1 day in the future!");
        this.value = ""; // clear invalid selection
    }
    });
    </script>
        <label for="candidates">Number of Candidates:</label>
        <input type="number" id="candidates" name="candidates" min="1" required>

        <label for="voters">Number of Voters:</label>
        <input type="number" id="voters" name="voters" min="1" required>
        
         <input type="hidden" name="conductorId" value="<%= userId %>">
         
        <label > Type of Election: </label>
        <select name="electionType" id="electionType" required>
        	<option>Society</option>
        	<option>School</option>
        	<option>College</option>
        </select>

        <input type="submit" value="Create Election">
    </form>
     <script>
     const alertBox = document.getElementById("msgBox");

     // Close on click anywhere
     document.addEventListener("click", () => {
         if (alertBox) {
             alertBox.style.display = "none";
         }
     });

     // OR auto hide after 5 seconds
     setTimeout(() => {
         if (alertBox) {
             alertBox.style.display = "none";
         }
     }, 3000);
    </script>
</body>
</html>