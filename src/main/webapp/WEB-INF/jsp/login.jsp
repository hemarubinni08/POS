<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login</title>

    <!-- Bootstrap + Font -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600&display=swap" rel="stylesheet">

    <style>
        body {
            font-family: 'Poppins', sans-serif;
            background: #FFF8F0;
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .card {
            width: 360px;
            padding: 28px 30px;
            border-radius: 18px;
            background: #ffffff;
            box-shadow: 0 18px 35px rgba(75, 46, 43, 0.25);
        }

        h3 {
            text-align: center;
            margin-bottom: 22px;
            font-weight: 600;
            color: #4B2E2B;
        }

        .form-label {
            font-size: 13px;
            font-weight: 500;
            color: #4B2E2B;
        }

        .form-control {
            border-radius: 8px;
            border: 1px solid #ccb7b2;
        }

        .form-control:focus {
            border-color: #4B2E2B;
            box-shadow: none;
        }

        .btn-primary {
            background-color: #4B2E2B;
            border: none;
            border-radius: 10px;
            font-weight: 600;
            padding: 10px;
            width: 100%;
        }

        .btn-primary:hover {
            background-color: #3a2421;
        }

        .register-btn {
            margin-top: 14px;
            background-color: #fff3eb;
            color: #4B2E2B;
            border-radius: 10px;
            text-align: center;
            display: block;
            padding: 10px;
            text-decoration: none;
            font-weight: 500;
        }

        .register-btn:hover {
            background-color: #f1e3dc;
        }


        .error {
            color: red;
            text-align: center;
            margin-bottom: 15px;
            font-size: 14px;
        }

        .success {
            color: green;
            text-align: center;
            margin-bottom: 15px;
            font-size: 14px;
        }

    </style>
</head>

<body>

<div class="card">

    <h3>Login</h3>

    <form:form action="/login" method="post">

        <c:if test="${param.error != null}">
                <div class="error">
                    Invalid username or password
                </div>
        </c:if>
        <c:if test="${param.logout != null}">
                <div class="success">
                    You have been logged out successfully
                </div>
        </c:if>
        <!-- Username -->
        <div class="mb-3">
            <label class="form-label">Username</label>
            <input type="text" name="username" class="form-control" required />
        </div>

        <!-- Password -->
        <div class="mb-4">
                    <label class="form-label">Password</label>
                    <input type="password" name="password" class="form-control" required />
        </div>


        <!-- Login Button -->
        <button type="submit" class="btn btn-primary">
            Login
        </button>

        <!-- Register Link -->
        <a href="/register" class="register-btn">
            Create New Account
        </a>

    </form:form>

</div>

</body>
</html>
