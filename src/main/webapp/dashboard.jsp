<%@ page import="java.util.*" %>
<%@ page session="true" %>
<%
Integer  voterId = (Integer ) session.getAttribute("voterId");
    String username = (String) session.getAttribute("username");
    if (voterId== null || username == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard</title>
    <style>
        body {
            margin: 0;
            font-family: Arial, sans-serif;
        }

        /* Navbar */
        .navbar {
            display: flex;
            justify-content: space-between;
            align-items: center;
            background-color: #003366;
            padding: 10px 20px;
            color: white;
        }

        .navbar .nav-left button,
        .navbar .menu-dropdown a {
            background-color: #0055aa;
            border: none;
            padding: 10px 15px;
            color: white;
            cursor: pointer;
            margin-right: 10px;
            border-radius: 4px;
        }
        a{
        	margin-top:10px;
        }

        .menu {
            position: relative;
            display: inline-block;
        }

        .menu-icon {
            font-size: 24px;
            cursor: pointer;
        }

        .menu-dropdown {
            display: none;
            position: absolute;
            right: 0;
            background-color: white;
            color: black;
            box-shadow: 0px 8px 16px rgba(0,0,0,0.2);
            min-width: 200px;
            z-index: 1;
            padding:10px;
        }

        .menu-dropdown a {
            display: block;
            padding: 10px;
            text-decoration: none;
            color: black;
        }

        .menu-dropdown a:hover {
            background-color: #ddd;
        }

        .menu:hover .menu-dropdown {
            display: block;
        }

        /* Main content */
        .main {
            display: flex;
            height: calc(100vh - 60px);
        }

        .left {
            width: 30%;
            padding: 20px;
            background-color: #f4f4f4;
            overflow-y: auto;
            border-right: 1px solid #ccc;
        }

        .right {
            width: 70%;
            padding: 20px;
            overflow-y: auto;
        }

        .card {
            background-color: white;
            padding: 15px;
            margin-bottom: 15px;
            border-radius: 8px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }

        .card h3 {
            margin: 0 0 10px 0;
        }

        .card p {
            margin: 5px 0;
        }
    </style>
</head>
<body>

    <!-- Navbar -->
    <div class="navbar">
        <div class="nav-left">
            <button onclick="location.href='applyVote.jsp'">Apply for Vote</button>
            <button onclick="location.href='conductElection.jsp'">Conduct Election</button>
        </div>
        <div class="menu">
            <span class="menu-icon">&#9776;</span>
            <div class="menu-dropdown">
                <a href="#">User</a>
                <a href="logout.jsp">Logout</a>
                <a href="#">Help & Support</a>
            </div>
        </div>
    </div>

    <!-- Main content -->
    <div class="main">
        <!-- Left: Elections Conducted by User -->
        <div class="left">
            <h2>Your Elections</h2>

            <div class="card">
                <h3>Local Ward Election</h3>
                <p>Date: 2025-07-30</p>
                <p>Status: Completed</p>
                <p>Result: Declared</p>
            </div>

            <div class="card">
                <h3>School Board Vote</h3>
                <p>Date: 2025-08-15</p>
                <p>Status: Scheduled</p>
                <p>Result: -</p>
            </div>

        </div>

        <!-- Right: Elections to Vote In -->
        <div class="right">
            <h2>Elections You Can Vote In</h2>

            <div class="card">
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

        </div>
    </div>

</body>
</html>
