<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ page import="java.util.*" %>

<!DOCTYPE html>
<html>
<head>
    <title>Election Results</title>
    <style>
        table {
            width: 80%;
            border-collapse: collapse;
        }
        th, td {
            padding: 10px;
            border: 1px solid #ddd;
            text-align: left;
        }
        h1 {
            color: green;
        }
    </style>
</head>
<body>
    <jsp:include page="navbar.jsp" />

    <div class="main">
        <h2>Election Results</h2>

        <c:if test="${not empty voteCounts}">
            <table>
                <tr>
                    <th>Candidate</th>
                    <th>Votes</th>
                </tr>
                <c:forEach var="entry" items="${voteCounts.entrySet()}">
                    <tr>
                        <td>${entry.key}</td>
                        <td>${entry.value}</td>
                    </tr>
                </c:forEach>
            </table>

            <%
                // Find winner(s)
                Map<String, Integer> voteCounts = (Map<String, Integer>) request.getAttribute("voteCounts");
                if (voteCounts != null && !voteCounts.isEmpty()) {
                    int maxVotes = Collections.max(voteCounts.values());
                    List<String> winners = new ArrayList<>();
                    for (Map.Entry<String, Integer> e : voteCounts.entrySet()) {
                        if (e.getValue() == maxVotes) {
                            winners.add(e.getKey());
                        }
                    }
                    // Build winner text
                    String winnerText = winners.size() > 1 ? 
                        "It's a tie between: " + String.join(", ", winners) :
                        "Winner: " + winners.get(0);
            %>
            <h1><%= winnerText %></h1>
            <%
                }
            %>

        </c:if>

        <c:if test="${empty voteCounts}">
            <p>No votes found for this election.</p>
        </c:if>
    </div>
</body>
</html>
