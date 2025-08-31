<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>




<%@ page import="java.util.*"%>
<%@ page session="true"%>
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
    response.setHeader("Pragma", "no-cache"); // HTTP 1.0
    response.setDateHeader("Expires", 0); // Proxies
    if (session.getAttribute("username") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<%
Integer userId = (Integer) session.getAttribute("userId");
String username = (String) session.getAttribute("username");
System.out.println("session id : " + session.getId() + " is on dashboard");
if (userId == null || username == null) {
	response.sendRedirect("login.jsp");
	return;
}
%>


<!DOCTYPE html>
<html>
<head>
<title>Dashboard</title>

<link rel="stylesheet" href="css/dashboard.css">
</head>
<body>
	<jsp:include page="navbar.jsp" /><!--  
<p>DEBUG: myElections = ${voteElections}</p>
<p>DEBUG: size = ${fn:length(voteElections)}</p>-->
	<!--notification -->
	<%
	String msg = request.getParameter("msg");
	String refCode = request.getParameter("refcode");
	if (msg != null) {
	%>
	<div id="msgBox" class="msg-box msg-success">
		<%=msg%><br>

		<%
		if (refCode != null && !refCode.trim().isEmpty()) {
		%>
		<div id="refCode"><%=refCode%></div>
		<button id="copyTextBtn">copy to clipboard</button>
		<%
		}
		%>
	</div>

	<%
	}
	%>
	<!-- Main content -->
	<div class="main">
		<!-- Left: Elections Conducted by User -->
		<div class="left">
			<h2>Your Elections</h2>
			<c:choose>
    <c:when test="${not empty myElections}">
        <c:forEach var="e" items="${myElections}">
            <div class="card electionCard " data-election-id="${e.id}" data-status="${e.status}">
                <h3>${e.title}</h3>
                <p>Date: ${e.date}</p>
                <p>Status: ${e.status}</p>
                <p>Result: ${e.result }</p>
            </div>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <div>No Elections</div>
    </c:otherwise>
</c:choose>

		</div>

		<!-- Right: Elections to Vote In -->
		<div class="right">
			<h2>Elections You Can Vote In</h2>

	<c:choose>
    <c:when test="${not empty voteElections}">
        <c:forEach var="e" items="${voteElections}">
            <div class="card voteCard" data-election-id="${e.id}" data-status="${e.status}">
                <h3>${e.title}</h3>
                <p>Date: ${e.date}</p>
                <p>Status: ${e.status}</p>
                <p>Result: ${e.result }</p>
            </div>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <div>No Elections</div>
    </c:otherwise>
</c:choose>


		</div>


<script>
const alertBox = document.getElementById("msgBox");
const copyTextBtn = document.getElementById("copyTextBtn");

// hide notification after 7 seconds
setTimeout(() => {
    if (alertBox) {
        alertBox.style.display = "none";
    }
}, 7000);

// copy button handler (only if button exists)
if (copyTextBtn) {
    copyTextBtn.addEventListener("click", () => {
        let refcode = document.getElementById("refCode").innerText;
        console.log(refcode);
        navigator.clipboard.writeText(refcode)
            .then(() => {
                if (alertBox) {
                    alertBox.style.display = "none";
                }
                alert("Reference code copied to clipboard");
            })
            .catch(err => {
                if (alertBox) {
                    alertBox.style.display = "none";
                }
                console.error("Failed to copy:", err);
                alert("Failed to copy the reference code.");
            });
    });
}

document.addEventListener("DOMContentLoaded", () => {
    // vote cards
    const voteCards = document.querySelectorAll(".voteCard");
    voteCards.forEach(card => {
        card.addEventListener("click", () => {
            const electionId = card.dataset.electionId;
            const voteCardStatus = card.dataset.status;

            if (electionId) {
                if (voteCardStatus.toLowerCase() === "ongoing") {
                    // If election is ongoing, go to VoteServlet
                    window.location.href = "VoteServlet?electionId=" + electionId;
                } else if (voteCardStatus.toLowerCase() === "completed") {
                    // If election is completed, go to ResultServlet
                    window.location.href = "ResultServlet?electionId=" + electionId;
                } else {
                    console.warn("Unknown status for election:", status);
                }
            }
        });
    });

    // election cards (no change)
    const electionCards = document.querySelectorAll(".electionCard");
    electionCards.forEach(card => {
        card.addEventListener("click", () => {
            const electionId = card.dataset.electionId;
          
            if (electionId) {
           
                  window.location.href = "MyElectionServlet?electionId=" + electionId;
                
            }
        });
    });
});

</script>

</body>
</html>
