<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>User Registration</title>

    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600&display=swap" rel="stylesheet">

    <style>
        body {
            margin: 0;
            font-family: 'Poppins', sans-serif;
            min-height: 100vh;
            background: #f4f6f9;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .register-card {
            position: relative;
            width: 440px;
            padding: 35px 40px;
            border-radius: 18px;
            background: #ffffff;
            border: 1px solid #e6e6e6;
            box-shadow: 0 12px 35px rgba(0,0,0,0.12);
            color: #333;
        }

        .back-icon {
            position: absolute;
            top: 16px;
            left: 16px;
            width: 36px;
            height: 36px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 18px;
            color: #333;
            text-decoration: none;
            background: #f0f0f0;
            border-radius: 50%;
            transition: 0.25s ease;
        }

        .back-icon:hover {
            background: #333;
            color: #fff;
            transform: scale(1.1);
        }

        h2 {
            text-align: center;
            margin-bottom: 20px;
            font-weight: 600;
            color: #222;
        }

        .error-message {
            margin-bottom: 16px;
            padding: 12px;
            background: #ffe5e5;
            border: 1px solid #ffb3b3;
            color: #b30000;
            border-radius: 10px;
            text-align: center;
            font-size: 13px;
        }

        .form-group {
            margin-bottom: 14px;
        }

        label {
            font-size: 13px;
            font-weight: 500;
            color: #444;
        }

        input, select {
            width: 100%;
            padding: 11px 14px;
            margin-top: 6px;
            border-radius: 10px;
            border: 1px solid #ddd;
            background: #fff;
            color: #333;
            font-size: 14px;
            transition: 0.2s ease;
        }

        input::placeholder {
            color: #999;
        }

        input:focus, select:focus {
            outline: none;
            border-color: #4a90e2;
            box-shadow: 0 0 0 3px rgba(74,144,226,0.15);
        }

        .btn-submit {
            width: 100%;
            padding: 13px;
            margin-top: 10px;
            background: #4a90e2;
            color: white;
            border: none;
            border-radius: 10px;
            font-weight: 600;
            cursor: pointer;
            transition: 0.3s ease;
        }

        .btn-submit:hover {
            background: #357bd8;
            transform: translateY(-1px);
            box-shadow: 0 8px 18px rgba(0,0,0,0.15);
        }
    </style>

    <script>
        function validateForm() {

            const name = document.getElementById("name").value.trim();
            const email = document.getElementById("username").value.trim();
            const roles = document.getElementById("roles").selectedOptions.length;
            const phone = document.getElementById("phoneNo").value.trim();
            const password = document.getElementById("password").value.trim();

            const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.com$/;
            const phoneRegex = /^[0-9]{10}$/;

            if (!name || !email || roles === 0 || !phone || !password) {
                alert("Please fill all fields before registering");
                return false;
            }

            if (!emailRegex.test(email)) {
                alert("Email must end with .com");
                return false;
            }

            if (!phoneRegex.test(phone)) {
                alert("Phone number must be exactly 10 digits");
                return false;
            }

            return true;
        }
    </script>

</head>

<body>

<div class="register-card">

    <a href="/login" class="back-icon">←</a>

    <h2>User Registration</h2>

    <c:if test="${not empty message}">
        <div class="error-message">
            ${message}
        </div>
    </c:if>

    <form:form action="register"
               method="post"
               modelAttribute="userDto"
               onsubmit="return validateForm();">

        <div class="form-group">
            <label>Name</label>
            <form:input path="name" id="name" placeholder="Enter name"/>
        </div>

        <div class="form-group">
            <label>Email</label>
            <form:input path="username"
                        id="username"
                        type="email"
                        required="required"
                        placeholder="Enter email (.com)"
                        pattern="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.com$"/>
        </div>

        <div class="form-group">
            <label>Roles</label>
            <form:select path="roles" id="roles" multiple="true">
                <form:options items="${roles}" itemValue="identifier" itemLabel="identifier"/>
            </form:select>
        </div>

        <div class="form-group">
            <label>Phone Number</label>
            <form:input path="phoneNo"
                        id="phoneNo"
                        maxlength="10"
                        pattern="[0-9]{10}"
                        placeholder="10 digit number"
                        oninput="this.value=this.value.replace(/[^0-9]/g,'')"/>
        </div>

        <div class="form-group">
            <label>Password</label>
            <form:password path="password"
                           id="password"
                           placeholder="Enter password"/>
        </div>

        <button type="submit" class="btn-submit">Register</button>

    </form:form>

</div>

</body>
</html>explain tth