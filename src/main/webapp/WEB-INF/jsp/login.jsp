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
            background: #ffffff;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        form {
            background: #ffffff;
            padding: 30px 35px;
            width: 320px;
            border-radius: 10px;
            box-shadow: 0 10px 25px rgba(0, 0, 0, 0.25);
        }

        h2 {
            text-align: center;
            margin-bottom: 25px;
            color: #333;
        }

        form div {
            margin-bottom: 18px;
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
        }

        input:focus {
            outline: none;
            border-color: #667eea;
        }

        button {
            width: 100%;
            padding: 10px;
            background-color: #667eea;
            color: white;
            border: none;
            border-radius: 6px;
            font-size: 15px;
            cursor: pointer;
            margin-bottom: 12px;
        }

        .register-link {
            display: block;
            margin-top: 15px;
            text-align: center;
            font-size: 14px;
            color: #667eea;
            text-decoration: none;
            font-weight: 500;
            transition: color 0.3s ease;
        }

        .register-link:hover {
            color: #5a67d8;
            text-decoration: underline;
        }

        button:hover {
            background-color: #5a67d8;
        }

        .error-message {
            margin-bottom: 15px;
            padding: 10px;
            background: rgba(220, 53, 69, 0.12);
            border: 1px solid #dc3545;
            color: #dc3545;
            border-radius: 8px;
            text-align: center;
            font-size: 14px;
            font-weight: 500;
        }
    </style>
</head>

<body>

<form th:action="@{/login}" method="post">
    <h2>Login</h2>

    <!-- ✅ ERROR MESSAGE -->
    <c:if test="${param.error != null}">
        <div class="error-message">
            Invalid username or password
        </div>
    </c:if>

    <!-- ✅ LOGOUT MESSAGE (optional) -->
    <c:if test="${param.logout != null}">
        <div class="error-message" style="color:#155724; border-color:#28a745;">
            Logged out successfully
        </div>
    </c:if>

    <div>
        <label>Username:</label>
        <input type="text" name="username" required />
    </div>

    <div>
        <label>Password:</label>
        <input type="password" name="password" required />
    </div>

    <button type="submit">Login</button>
    <a href ="/register">Don't have an account?</a>

</form>

</body>
</html>