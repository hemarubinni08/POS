<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>

    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">

    <style>
        body {
            margin: 0;
            height: 100vh;
            font-family: Arial, sans-serif;
            background: linear-gradient(to right, #bdc3c7, #2c3e50);
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .login-box {
            background: #fff;
            padding: 30px 35px;
            width: 340px;
            border-radius: 10px;
            box-shadow: 0 10px 25px rgba(0,0,0,0.25);
        }

        h2 {
            text-align: center;
            margin-bottom: 20px;
        }

        label {
            font-size: 14px;
        }

        input {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            border-radius: 6px;
            border: 1px solid #ccc;
        }

        .btn-login {
            width: 100%;
            background: #667eea;
            color: white;
        }

        .btn-login:hover {
            background: #5a67d8;
        }

        .register-link {
            display: block;
            text-align: center;
            margin-top: 10px;
        }

        .msg {
            text-align: center;
            margin-bottom: 10px;
            font-size: 13px;
            color: red;
        }

        .success {
            color: green;
        }
    </style>
</head>

<body>

<div class="login-box">

    <h2>Login</h2>

    <!-- COMMON ERROR MESSAGE -->
    <c:if test="${param.error}">
        <div class="msg">
            Your username or password is incorrect.
        </div>
    </c:if>

    <!-- LOGOUT MESSAGE -->
    <c:if test="${param.logout}">
        <div class="msg success">
            You have been logged out successfully.
        </div>
    </c:if>

    <form action="/login" method="post">

        <label>Username</label>
        <input type="text" name="username" required>

        <label>Password</label>
        <input type="password" name="password" required>

        <button type="submit" class="btn btn-login">
            Login
        </button>

    </form>

    <a href="/register" class="register-link">
        Register New Account
    </a>

</div>

</body>
</html>