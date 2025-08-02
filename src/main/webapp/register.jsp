
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>Register - Online Voting System</title>
  <link rel="stylesheet" href="css/login.css" />
</head>
<body>
  <div class="login-container">
    <h2>Create Your Account</h2>
    <form action="register" method="post">
      <div class="form-group">
        <label for="email">Email address</label>
        <input type="email" id="email" name="email" required />
      </div>

      <div class="form-group">
        <label for="name">Full Name</label>
        <input type="text" id="name" name="name" required />
      </div>

      <div class="form-group">
        <label for="password">Password</label>
        <input type="password" id="password" name="password" required />
      </div>

      <div class="form-group">
        <label for="confirmPassword">Confirm Password</label>
        <input type="password" id="confirmPassword" name="confirmPassword" required />
      </div>

      <button type="submit" class="btn-login">Register</button>
    </form>
    <%
    String error = (String) request.getAttribute("error");
        		System.out.println(">>> inside register.jsp "+error);
    if (error != null) {
%>
    <div class="alert alert-error"><%= error %></div>
<%
    }
%>
    

    <p class="register-link">Already have an account? <a href="login.jsp">Login here</a></p>
  </div>
</body>
</html>
