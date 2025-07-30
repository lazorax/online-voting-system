<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>Login - Online Voting System</title>
  <link rel="stylesheet" href="css/login.css" />
</head>
<body>
  <div class="login-container">
    <h2>Login to Your Account</h2>
    <form action="LoginServlet" method="post">
     
      
      <div class="form-group">
        <label for="email">Email</label>
        <input type="email" id="email" name="email"  />
      </div>

      <div class="form-group">
        <label for="password">Password</label>
        <input type="password" id="password" name="password" required />
      </div>

      <button type="submit" class="btn-login">Login</button>
    </form>

    <p class="register-link">Don't have an account? <a href="register.jsp">Register</a></p>
  </div>
</body>
</html>
