<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Conduct Election</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            padding: 20px;
        }
        h2 {
            color: #003366;
        }
        form {
            max-width: 500px;
            margin: auto;
        }
        label {
            display: block;
            margin-top: 10px;
            font-weight: bold;
        }
        input, textarea {
            width: 100%;
            padding: 8px;
            margin-top: 4px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        input[type="submit"] {
            background-color: #007bff;
            color: white;
            cursor: pointer;
            font-weight: bold;
        }
        input[type="submit"]:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <h2>Conduct New Election</h2>
    <form action="conductElection" method="post">
        <label for="title">Title:</label>
        <input type="text" id="title" name="title" required>

        <label for="about">About the Election:</label>
        <textarea id="about" name="about" rows="4" required></textarea>

        <label for="date">Date:</label>
        <input type="date" id="date" name="date" required>

        <label for="candidates">Number of Candidates:</label>
        <input type="number" id="candidates" name="candidates" min="1" required>

        <label for="voters">Number of Voters:</label>
        <input type="number" id="voters" name="voters" min="1" required>
        
        <label > Type of Election: </label>
        <select name="electionType" id="electionType" required>
        	<option>Society</option>
        	<option>School</option>
        	<option>College</option>
        </select>

        <input type="submit" value="Create Election">
    </form>
</body>
</html>