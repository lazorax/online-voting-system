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
            margin-bottom: 20px;
        }
        th, td {
            padding: 10px;
            border: 1px solid #ddd;
            text-align: left;
        }
        h1 {
            color: green;
        }
        #chartContainer {
            width: 30%;
            margin: 2px auto;
        }
    </style>
    <!-- Chart.js CDN -->
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <!-- Confetti library -->
    <script src="https://cdn.jsdelivr.net/npm/canvas-confetti@1.6.0/dist/confetti.browser.min.js"></script>
</head>
<body>
    <jsp:include page="navbar.jsp" />

    <div class="main">
        <h2>Election Results</h2>

        <c:if test="${not empty voteCounts}">
            <!-- Table Result -->
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

            <!-- Pie Chart -->
            <div id="chartContainer">
                <canvas id="resultChart"></canvas>
            </div>

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

            <!-- JS for Pie Chart + Confetti -->
            <script>
                // Prepare data from server-side
                var labels = [];
                var data = [];

                <% for (Map.Entry<String, Integer> entry : voteCounts.entrySet()) { %>
                    labels.push("<%= entry.getKey() %>");
                    data.push(<%= entry.getValue() %>);
                <% } %>

                // Render Pie Chart
                var ctx = document.getElementById("resultChart").getContext("2d");
                new Chart(ctx, {
                    type: 'pie',
                    data: {
                        labels: labels,
                        datasets: [{
                            data: data,
                            backgroundColor: [
                                "#FF6384", "#36A2EB", "#FFCE56", "#4CAF50", "#9C27B0", "#FF9800"
                            ]
                        }]
                    },
                    options: {
                        responsive: true,
                        plugins: {
                            legend: {
                                position: 'bottom'
                            }
                        }
                    }
                });

                // Confetti Celebration
                var duration = 5 * 1000;
                var end = Date.now() + duration;

                (function frame() {
                    confetti({
                        particleCount: 4,
                        angle: 60,
                        spread: 55,
                        origin: { x: 0 }
                    });
                    confetti({
                        particleCount: 4,
                        angle: 120,
                        spread: 55,
                        origin: { x: 1 }
                    });

                    if (Date.now() < end) {
                        requestAnimationFrame(frame);
                    }
                }());
            </script>
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
