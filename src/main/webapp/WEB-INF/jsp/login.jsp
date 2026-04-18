<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Font -->
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600&display=swap" rel="stylesheet">

    <style>
        :root {
            --bg: #f6fff8;
            --card: #ffffff;
            --text: #1f2937;
            --muted: #6b7280;

            --primary: #28a745;
            --primary-hover: #218838;

            --accent: #ffc107;
            --accent-hover: #e0a800;

            --border: #e5e7eb;
            --radius: 14px;

            --shadow: 0 10px 30px rgba(0,0,0,0.08);
        }

        * {
            font-family: 'Inter', Arial, sans-serif;
            box-sizing: border-box;
        }

        body {
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            background: var(--bg);
            margin: 0;
        }

        .login-card {
            width: 380px;
            background: var(--card);
            padding: 32px;
            border-radius: var(--radius);
            box-shadow: var(--shadow);
            border-top: 5px solid var(--accent);
        }

        h2 {
            text-align: center;
            margin-bottom: 22px;
            color: var(--text);
            font-weight: 600;
        }

        label {
            font-size: 13px;
            color: var(--muted);
            margin-bottom: 5px;
            display: block;
        }

        .form-control {
            border-radius: 10px;
            padding: 10px;
            border: 1px solid var(--border);
        }

        .form-control:focus {
            border-color: var(--primary);
            box-shadow: 0 0 0 3px rgba(40,167,69,0.15);
        }

        .btn-login {
            width: 100%;
            padding: 10px;
            background: var(--primary);
            color: white;
            border-radius: 10px;
            border: none;
            font-weight: 600;
            margin-top: 10px;
        }

        .btn-login:hover {
            background: var(--primary-hover);
        }

        .btn-register {
            display: block;
            width: 100%;
            padding: 10px;
            background: var(--accent);
            color: black;
            text-align: center;
            border-radius: 10px;
            font-weight: 600;
            margin-top: 10px;
            text-decoration: none;
        }

        .btn-register:hover {
            background: var(--accent-hover);
            color: black;
        }

        .divider {
            text-align: center;
            margin: 12px 0;
            color: var(--muted);
            font-size: 12px;
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

    <!-- LOGIN FORM -->
    <form action="${pageContext.request.contextPath}/login"
          method="post"
          onsubmit="return validateLoginForm()">

        <h2>Login</h2>

        <div class="mb-3">
            <label>Email</label>
            <input type="email"
                   name="username"
                   class="form-control"
                   required />
        </div>

        <div class="mb-3">
            <label>Password</label>
            <input type="password"
                   name="password"
                   class="form-control"
                   required />
        </div>

        <button type="submit" class="btn-login">Login</button>
    </form>

    <div class="divider">OR</div>

    <!-- REGISTER BUTTON -->
    <a href="${pageContext.request.contextPath}/register"
       class="btn-register">
        New User? Register
    </a>

</div>

</body>
</html>