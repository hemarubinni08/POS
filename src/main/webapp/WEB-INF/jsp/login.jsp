<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Login</title>

    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600;700&display=swap" rel="stylesheet">

    <style>
        body {
            margin: 0;
            font-family: 'Poppins', sans-serif;
            height: 100vh;
            overflow: hidden;
        }

        .main-container {
            display: flex;
            height: 100vh;
            position: relative;
        }

        /* LEFT PANEL */
        .left-panel {
            flex: 1;
            background: linear-gradient(135deg, #cee9e9, #8fcfcd);
            color: #1f3b3b;
            display: flex;
            flex-direction: column;
            justify-content: center;
            padding: 80px;
            position: relative;
        }

        .left-panel h1 {
            font-size: 42px;
            font-weight: 700;
            margin: 0;
        }

        .left-panel p {
            font-size: 20px;
            margin-top: 20px;
            line-height: 1.5;
        }

        .btn {
            margin-top: 30px;
            display: inline-block;
            padding: 10px 18px;
            background: #4aa6a3;
            color: white;
            text-decoration: none;
            border-radius: 8px;
            font-weight: 500;
            width: fit-content;
        }

        /* CURVE (same as register) */
        .curve {
            position: absolute;
            top: 0;
            right: 0;
            height: 100%;
            width: 120px;
            z-index: 2;
        }

        .curve svg {
            height: 100%;
            width: 100%;
        }

        /* RIGHT PANEL */
        .right-panel {
            flex: 1;
            background: #f7f7fb;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        /* FORM BOX */
        .form-box {
            width: 360px;
            background: white;
            padding: 40px;
            border-radius: 18px;
            box-shadow: 0 20px 50px rgba(0,0,0,0.15);
        }

        .form-box h2 {
            text-align: center;
            margin-bottom: 20px;
            color: #4aa6a3;
            font-weight: 700;
            letter-spacing: 1px;
        }

        .form-box input {
            width: 100%;
            padding: 12px;
            margin-bottom: 12px;
            border-radius: 10px;
            border: 1px solid #ddd;
            outline: none;
        }

        .form-box input:focus {
            border-color: #4aa6a3;
        }

        .form-box button {
            width: 100%;
            padding: 12px;
            border: none;
            border-radius: 10px;
            background: linear-gradient(135deg, #cee9e9, #4aa6a3);
            color: white;
            font-weight: 600;
            cursor: pointer;
        }

        small {
            color: red;
        }
    </style>
</head>

<body>

<div class="main-container">

    <!-- LEFT -->
    <div class="left-panel">
        <h1>Welcome Back.</h1>
        <p>Login to continue your journey with us</p>

        <a href="/register" class="btn">REGISTER</a>

        <!-- SAME CURVE AS REGISTER -->
        <div class="curve">
            <svg viewBox="0 0 100 100" preserveAspectRatio="none">
                <path d="M0,0 H100 V100 H0 C60,80 60,20 0,0 Z" fill="#f7f7fb"></path>
            </svg>
        </div>
    </div>

    <!-- RIGHT -->
    <div class="right-panel">

        <div class="form-box">

            <h2>LOGIN</h2>

            <div id="messageBox">
                <small>${message}</small>
            </div>

            <form action="login" method="post">

                <input type="text" name="username" placeholder="Email Address" required />
                <input type="password" name="password" placeholder="Password" required />

                <button type="submit">LOGIN</button>

            </form>

        </div>

    </div>

</div>

</body>
</html>