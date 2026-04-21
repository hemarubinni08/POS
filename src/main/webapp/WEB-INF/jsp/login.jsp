<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Login</title>

    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600&display=swap"
          rel="stylesheet">

    <style>
        body {
            margin: 0;
            height: 100vh;
            font-family: 'Poppins', sans-serif;
            background: #f4f6f9;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .login-card {
            width: 380px;
            padding: 35px 40px;
            border-radius: 18px;
            background: #ffffff;
            border: 1px solid #e6e6e6;
            box-shadow: 0 12px 35px rgba(0,0,0,0.12);
            color: #333;
        }

        h2 {
            text-align: center;
            margin-bottom: 20px;
            font-weight: 600;
            color: #222;
        }

        .error-message {
            margin-bottom: 15px;
            padding: 12px;
            background: #ffe5e5;
            border: 1px solid #ffb3b3;
            color: #b30000;
            border-radius: 10px;
            text-align: center;
            font-size: 13px;
        }

        .success-message {
            margin-bottom: 15px;
            padding: 12px;
            background: #e6f4ea;
            border: 1px solid #b7e4c7;
            color: #1b4332;
            border-radius: 10px;
            text-align: center;
            font-size: 13px;
        }

        .form-group {
            margin-bottom: 16px;
        }

        label {
            display: block;
            margin-bottom: 6px;
            font-size: 13px;
            font-weight: 500;
            color: #444;
        }

        input {
            width: 100%;
            padding: 11px 12px;
            border-radius: 10px;
            border: 1px solid #ddd;
            font-size: 14px;
            background: #fff;
            color: #333;
            transition: 0.2s ease;
        }

        input::placeholder {
            color: #999;
        }

        input:focus {
            outline: none;
            border-color: #4a90e2;
            box-shadow: 0 0 0 3px rgba(74,144,226,0.15);
        }

        button {
            width: 100%;
            padding: 12px;
            background: #4a90e2;
            color: white;
            border: none;
            border-radius: 10px;
            font-size: 15px;
            font-weight: 600;
            cursor: pointer;
            transition: 0.3s ease;
        }

        button:hover {
            background: #357bd8;
            transform: translateY(-1px);
            box-shadow: 0 8px 18px rgba(0,0,0,0.15);
        }

        .register-link {
            display: block;
            margin-top: 15px;
            text-align: center;
            font-size: 13px;
            color: #4a90e2;
            text-decoration: none;
            font-weight: 500;
        }

        .register-link:hover {
            text-decoration: underline;
        }
    </style>
</head>

<body>

<div class="login-card">

    <h2>Login</h2>

    <c:if test="${param.error != null}">
        <div class="error-message">
            Invalid username or password
        </div>
    </c:if>

    <c:if test="${param.logout != null}">
        <div class="success-message">
            Logged out successfully
        </div>
    </c:if>

    <form action="/login" method="post">

        <div class="form-group">
            <label>Username</label>
            <input type="text" name="username" placeholder="Enter username" required />
        </div>

        <div class="form-group">
            <label>Password</label>
            <input type="password" name="password" placeholder="Enter password" required />
        </div>

        <button type="submit">Login</button>

    </form>

    <a href="/register" class="register-link">
        Don't have an account? Register
    </a>

</div>

</body>
</html>