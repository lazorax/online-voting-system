<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Online Voting - Election</title>
    <style>
        table { border-collapse: collapse; width: 90%; margin: 20px auto; }
        th, td { border: 1px solid #ddd; padding: 10px; text-align: center; }
        th { background: #f4f4f4; }
        img { border-radius: 8px; }
    </style>
</head>
<body>
    <h2>Election: ${electionName} (ID: ${electionId})</h2>
    <p>Status: ${status} | Date: ${date} | Ref Code: ${refCode} | Result: ${result}</p>
    <div style="display: flex; justify-content: flex-end; gap: 10px; margin: 10px 40px;">
    <form action="ElectionStatusServlet" method="post" style="display:inline;">
        <input type="hidden" name="electionId" value="${electionId}"/>
        <input type="hidden" name="action" value="start"/>
        <button type="submit" 
                style="background: green; color: white; padding: 8px 14px; border: none; border-radius: 6px; cursor: pointer;">
            Start Election
        </button>
    </form>

    <form action="ElectionStatusServlet" method="post" style="display:inline;">
        <input type="hidden" name="electionId" value="${electionId}"/>
        <input type="hidden" name="action" value="end"/>
        <button type="submit" 
                style="background: red; color: white; padding: 8px 14px; border: none; border-radius: 6px; cursor: pointer;">
            End Election
        </button>
    </form>
</div>
    

    <h3>Candidates</h3>
    <table>
        <tr>
            <th>Photo</th>
            <th>Full Name</th>
            <th>Age</th>
            <th>Education</th>
            <th>Info</th>
        </tr>
        <c:forEach var="candidate" items="${candidates}">
            <tr>
                <td>
                    <img src="candidateImage?id=${candidate.candidateId}" width="100" height="100"/>
                </td>
                <td>${candidate.fullname}</td>
                <td>${candidate.age}</td>
                <td>${candidate.education}</td>
                <td>${candidate.info}</td>
            </tr>
        </c:forEach>
    </table>

    <h3>Registered Voters</h3>
    <table>
        <tr>
            <th>Voter ID</th>
            <th>Voter Name</th>
            <th>Registered At</th>
            <th>Action</th>
        </tr>
        <c:forEach var="voter" items="${voters}">
            <tr>
                <td>${voter.voterId}</td>
                <td>${voter.username}</td>
                <td>${voter.registeredAt}</td>
                <td>
                    <form action="RemoveVoterServlet" method="post">
                        <input type="hidden" name="id" value="${voter.id}"/>
                        <button type="submit">Remove</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
