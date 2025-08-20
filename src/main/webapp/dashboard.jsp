<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>



<%@ page import="java.util.*" %>
<%@ page session="true" %>
<%
Integer  userId = (Integer ) session.getAttribute("userId");
    String username = (String) session.getAttribute("username");
       System.out.println("session id : " + session.getId()+" is on dashboard");
    if (userId== null || username == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title> Dashboard </title>
    
   	<link rel="stylesheet" href="css/dashboard.css">
</head>
<body>
<jsp:include page="navbar.jsp" /><!--  
<p>DEBUG: myElections = ${voteElections}</p>
<p>DEBUG: size = ${fn:length(voteElections)}</p>-->
 <!--notification -->
  <% 
        String msg = request.getParameter("msg");
         String refCode  = request.getParameter("refcode");
        if (msg != null) { 
    %>
        <div id="msgBox" class="msg-box msg-success">
            <%= msg %><br>
            
            <%if(refCode != null && !refCode.trim().isEmpty()){ %>
            <div id="refCode"><%= refCode %></div>
            <button id="copyTextBtn">copy to clipboard</button>
            <%} %>
        </div>
  
 <%} %>
    <!-- Main content --> 
    <div class="main">
        <!-- Left: Elections Conducted by User -->
        <div class="left">
            <h2>Your Elections</h2>
       <c:forEach var="e" items="${myElections}">
        <div class="card">
            <h3>${e.title}</h3>
            <p>Date: ${e.date}</p>
            <p>Status: ${e.status}</p>
             <c:choose>
            <c:when test="${e.status eq 'completed'}">
                <p>Result: Declared</p>
            </c:when>
            <c:otherwise>
                <p>Result: -</p>
            </c:otherwise>
        </c:choose>
            
        </div>
    </c:forEach>

        </div>

        <!-- Right: Elections to Vote In -->
        <div class="right">
            <h2>Elections You Can Vote In</h2>

       <!--     <div class="card">
                <h3>State Assembly</h3>
                <p>Date: 2025-08-10</p>
                <p>Status: Upcoming</p>
                <p>Result: -</p>
            </div>

            <div class="card">
                <h3>Resident Welfare Association</h3>
                <p>Date: 2025-08-20</p>
                <p>Status: Not Voted</p>
                <p>Result: Pending</p>
            </div>

        </div> --> 
      
        <c:forEach var="ev" items="${voteElections}">
        		<div class="card">
        			  <h3>${ev.title}</h3>
        			  <p>Date: ${ev.date}</p>
        			  <p>Status: ${ev.status}</p>
        			  <c:choose>
        			  	<c:when test="${ev.status eq 'completed'}">
        			  		<p>Result: Declared</p>
        			  	</c:when>
        			  	<c:otherwise>
        			  		<p>Result: -</p>
        			  	</c:otherwise>
        			  </c:choose>
        		</div>
        </c:forEach>
        
    </div> 
    
    
 <script>
 const alertBox = document.getElementById("msgBox");
 const copyTextBtn = document.getElementById("copyTextBtn");

 /*document.addEventListener("click", () => {
     if (alertBox) {
         alertBox.style.display = "none";
     }
 });*/

 setTimeout(() => {
     if (alertBox) {
         alertBox.style.display = "none";
     }
 }, 7000);
 
 copyTextBtn.addEventListener("click",()=>{
	 let refcode =  document.getElementById("refCode").innerText
	 console.log(refcode)
	 navigator.clipboard.writeText(refcode)
	 .then(()=>{
		 if (alertBox) {
	         alertBox.style.display = "none";
	     }
		 alert("Reference code copied to clipboard");

	 })  .catch(err => {
		 if (alertBox) {
	         alertBox.style.display = "none";
	     }
         console.error("Failed to copy:", err);
         alert("Failed to copy the reference code.");
     });
 })
    </script>
</body>
</html>
