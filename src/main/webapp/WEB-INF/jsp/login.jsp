<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>

    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Font -->
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">

    <style>
        :root {
            --bg: #f1f5f9;
            --card: #ffffff;
            --text: #0f172a;
            --muted: #64748b;

            --primary: #22c55e;
            --primary-hover: #16a34a;

            --border: #e2e8f0;
            --radius: 16px;

            --shadow: 0 20px 40px rgba(15,23,42,0.08);
        }

        * {
            font-family: 'Inter', sans-serif;
            box-sizing: border-box;
        }

        body {
            margin: 0;
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            background: linear-gradient(
                135deg,
                #f8fafc 0%,
                #eef2f7 100%
            );
        }

        .login-card {
            width: 400px;
            background: var(--card);
            padding: 36px 32px;
            border-radius: var(--radius);
            box-shadow: var(--shadow);
            border: 1px solid var(--border);
        }

        h2 {
            text-align: center;
            margin-bottom: 24px;
            color: var(--text);
            font-weight: 700;
            font-size: 22px;
        }

        label {
            font-size: 13px;
            color: var(--muted);
            margin-bottom: 6px;
            display: block;
            font-weight: 500;
        }

        .form-control {
            border-radius: 12px;
            padding: 12px 14px;
            border: 1px solid var(--border);
            font-size: 14px;
        }

        .form-control:focus {
            border-color: var(--primary);
            box-shadow: 0 0 0 3px rgba(34,197,94,0.15);
        }

        .btn-login {
            width: 100%;
            padding: 12px;
            background: var(--primary);
            border: none;
            color: #fff;
            font-weight: 600;
            border-radius: 12px;
            margin-top: 6px;
            transition: transform .15s ease, box-shadow .15s ease;
        }

        .btn-login:hover {
            background: var(--primary-hover);
            transform: translateY(-1px);
            box-shadow: 0 8px 20px rgba(22,163,74,0.25);
        }

        .btn-register {
            display: block;
            text-align: center;
            width: 100%;
            padding: 12px;
            margin-top: 14px;
            border-radius: 12px;
            border: 1px solid var(--border);
            color: var(--text);
            font-weight: 600;
            text-decoration: none;
            background: #f8fafc;
            transition: background .2s ease, transform .15s ease;
        }

        .btn-register:hover {
            background: #eef2f7;
            transform: translateY(-1px);
        }

        .divider {
            text-align: center;
            margin: 16px 0;
            font-size: 12px;
            color: var(--muted);
            position: relative;
        }

        .divider::before,
        .divider::after {
            content: "";
            position: absolute;
            top: 50%;
            width: 40%;
            height: 1px;
            background: var(--border);
        }

        .divider::before {
            left: 0;
        }

        .divider::after {
            right: 0;
        }

        .alert {
            font-size: 13px;
            border-radius: 12px;
            border: 1px solid #fecaca;
            background: #fee2e2;
            color: #991b1b;
            padding: 10px;
        }
    </style>

    <script>
        function validateLoginForm() {
            let username = document.getElementsByName("username")[0].value.trim();
            let password = document.getElementsByName("password")[0].value.trim();

            if (username === "" || password === "") {
                alert("Please fill in all fields.");
                return false;
            }

            let emailPattern = /^[^ ]+@[^ ]+\.[a-z]{2,}$/;

            if (!username.match(emailPattern)) {
                alert("Enter a valid email address.");
                return false;
            }

            return true;
        }
    </script>
</head>

<body>

<div class="login-card">

    <h2>Login</h2>

    <c:if test="${not empty errorMsg}">
        <div class="alert text-center mb-3">
            ${errorMsg}
        </div>
    </c:if>

    <form action="${pageContext.request.contextPath}/login"
          method="post"
          onsubmit="return validateLoginForm()">

        <div class="mb-3">
            <label>Email</label>
            <input type="email" name="username" class="form-control" required />
        </div>

        <div class="mb-3">
            <label>Password</label>
            <input type="password" name="password" class="form-control" required />
        </div>

        <button type="submit" class="btn-login">
            Login
        </button>
    </form>

    <div class="divider">OR</div>

    <a href="${pageContext.request.contextPath}/register"
       class="btn-register">
        Create New Account
    </a>

</div>

</body>
</html>