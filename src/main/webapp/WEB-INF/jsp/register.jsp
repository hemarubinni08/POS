<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>User Registration</title>

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
            color: white;
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

        .login-btn {
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

        /* CURVE DIVIDER (SVG SIDE) */
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

        .form-box input,
        .form-box select {
            width: 100%;
            padding: 12px;
            margin-bottom: 12px;
            border-radius: 10px;
            border: 1px solid #ddd;
            outline: none;
            transition: 0.2s;
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

        .checkbox {
            font-size: 12px;
            display: flex;
            gap: 6px;
            margin-bottom: 10px;
        }

        #messageBox {
            text-align: center;
            margin-bottom: 10px;
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
        <h1>Pull Designs.</h1>
        <p>Hello,<br>I'm so glad to see you again</p>

        <a href="/login" class="login-btn">LOG IN</a>

        <!-- CURVED DIVIDER -->
        <div class="curve">
            <svg viewBox="0 0 100 100" preserveAspectRatio="none">
                <path d="M0,0 H100 V100 H0 C60,80 60,20 0,0 Z" fill="#f7f7fb"></path>
            </svg>
        </div>
    </div>

    <!-- RIGHT -->
    <div class="right-panel">

        <div class="form-box">

            <h2>SIGN UP</h2>

            <div id="messageBox">
                <small>${message}</small>
            </div>

            <form:form action="register" method="post" modelAttribute="userDto">

                <form:input path="name" placeholder="Full Name" required="true"/>

                <form:input path="username"
                            type="text"
                            placeholder="Email Address"
                            required="true"
                            pattern="^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$"
                            title="Enter valid email like example@gmail.com"/>

                <form:select path="roles" multiple="true" required="true">
                    <form:options items="${roles}" itemValue="identifier" itemLabel="identifier"/>
                </form:select>

                <form:input path="phoneNo" type="number" placeholder="Phone Number" required="true"
                pattern="^[6-9][0-9]{9}$" title="Enter valid 10-digit Indian mobile number"/>

                <form:password path="password" placeholder="Password" required="true" minlength="6"/>

                <button type="submit">SIGN UP</button>

            </form:form>

        </div>

    </div>

</div>
<script>
    window.addEventListener("DOMContentLoaded", function () {
        const messageBox = document.getElementById("messageBox");

        if (messageBox) {
            setTimeout(() => {
                messageBox.style.opacity = "0";
                messageBox.style.transition = "opacity 0.5s ease";

                setTimeout(() => messageBox.remove(), 500);
            }, 4000); // disappears after 4 seconds
        }
    });
</script>
</body>
</html>