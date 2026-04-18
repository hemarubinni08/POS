<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Login</title>

    <style>
        body {
            margin: 0;
            height: 100vh;
            font-family: Arial, sans-serif;
            background: linear-gradient(135deg, #667eea, #764ba2);
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .login-box {
            background: #ffffff;
            padding: 30px 35px;
            width: 340px;
            border-radius: 10px;
            box-shadow: 0 10px 25px rgba(0, 0, 0, 0.25);
        }

        h2 {
            text-align: center;
            margin-bottom: 20px;
            color: #333;
        }

        label {
            display: block;
            margin-bottom: 6px;
            font-size: 14px;
            color: #555;
        }

        input {
            width: 100%;
            padding: 10px;
            font-size: 14px;
            border: 1px solid #ccc;
            border-radius: 6px;
            margin-bottom: 15px;
            box-sizing: border-box;
        }

        input:focus {
            outline: none;
            border-color: #667eea;
        }

        .btn {
            width: 100%;
            padding: 10px;
            border-radius: 6px;
            font-size: 15px;
            cursor: pointer;
            border: none;
            box-sizing: border-box;
        }

        .login-btn {
            background-color: #667eea;
            color: white;
            margin-bottom: 10px;
        }

        .login-btn:hover {
            background-color: #5a67d8;
        }

        .register-btn {
            background-color: #f1f1f1;
            color: #333;
            text-decoration: none;
            display: block;
            text-align: center;
        }

        .register-btn:hover {
            background-color: #ddd;
        }

        .error {
            color: red;
            font-size: 13px;
            text-align: center;
            margin-bottom: 10px;
        }

        .success {
            color: green;
            font-size: 13px;
            text-align: center;
            margin-bottom: 10px;
        }
    </style>
</head>

<body>

<div class="login-box">

    <h2>Login</h2>

    <!-- ERROR -->
    <div class="error" th:if="${param.error}">
        Invalid username or password
    </div>

    <!-- LOGOUT -->
    <div class="success" th:if="${param.logout}">
        You have been logged out successfully
    </div>

    <form th:action="@{/login}" method="post">

        <label>Username</label>
        <input type="text" name="username" required />

        <label>Password</label>
        <input type="password" name="password" required />

        <!-- SAME SIZE BUTTONS -->
        <button type="submit" class="btn login-btn">
            Login
        </button>

        <a href="/register" class="btn register-btn">
            Create New Account
        </a>

    </form>

</div>

</body>
</html>
