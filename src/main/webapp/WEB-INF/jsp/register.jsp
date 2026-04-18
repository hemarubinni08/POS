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
            background: linear-gradient(135deg, #667eea, #764ba2);
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .register-card {
            width: 430px;
            background: rgba(255, 255, 255, 0.95);
            padding: 35px 40px;
            border-radius: 16px;
            box-shadow: 0 25px 50px rgba(0, 0, 0, 0.25);
        }

        h2 {
            text-align: center;
            margin-bottom: 30px;
            color: #4b6cb7;
            font-weight: 600;
        }

        .form-group {
            margin-bottom: 16px;
        }

        label {
            font-size: 13px;
            font-weight: 500;
            color: #333;
            margin-bottom: 6px;
            display: block;
        }

        input {
            width: 100%;
            padding: 11px 14px;
            border-radius: 8px;
            border: 1px solid #ccc;
            font-size: 14px;
        }

        .error {
            color: #cc0000;
            font-size: 12px;
            margin-top: 4px;
            display: block;
        }

        .btn-submit, .btn-secondary {
            width: 100%;
            padding: 13px;
            border-radius: 10px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            border: none;
            margin-top: 10px;
        }

        .btn-submit {
            background: linear-gradient(135deg, #4b6cb7, #182848);
            color: white;
        }

        .btn-secondary {
            background: #6c757d;
            color: white;
        }

        /* ===== DROPDOWN ===== */
        .dropdown {
            position: relative;
            width: 100%;
        }

        .dropdown-btn {
            width: 100%;
            padding: 11px 14px;
            border-radius: 8px;
            border: 1px solid #ccc;
            background: white;
            text-align: left;
            cursor: pointer;
            font-size: 14px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .dropdown-content {
            display: none;
            position: absolute;
            width: 100%;
            background: white;
            border: 1px solid #ccc;
            border-radius: 8px;
            margin-top: 5px;
            max-height: 150px;
            overflow-y: auto;
            z-index: 1000;
        }

        .dropdown-content label {
            display: flex;
            align-items: center;
            padding: 8px 10px;
            font-size: 13px;
            cursor: pointer;
        }

        .dropdown-content label:hover {
            background: #f2f2f2;
        }

        .dropdown-content input {
            width: auto;
            margin-right: 8px;
        }
    </style>
</head>

<body>

<div class="register-card">
    <h2>User Registration</h2>

    <c:if test="${not empty errorMessage}">
        <div style="color:red; text-align:center; margin-bottom:15px;">
            ${errorMessage}
        </div>
    </c:if>

    <form:form action="register" method="post" modelAttribute="userDto" id="registrationForm">

        <div class="form-group">
            <label>Name</label>
            <form:input path="name" id="name"/>
            <form:errors path="name" cssClass="error"/>
        </div>

        <div class="form-group">
            <label>Email</label>
            <form:input path="username" id="email"/>
            <form:errors path="username" cssClass="error"/>
        </div>

        <!-- ===== ROLES DROPDOWN ===== -->
        <div class="form-group">
            <label>Roles</label>

            <div class="dropdown">
                <button type="button" class="dropdown-btn" onclick="toggleDropdown()">
                    Select Roles
                    <span>▼</span>
                </button>

                <div id="rolesDropdown" class="dropdown-content">
                    <c:forEach var="role" items="${roles}">
                        <label>
                            <input type="checkbox" name="roles" value="${role.identifier}">
                            ${role.identifier}
                        </label>
                    </c:forEach>
                </div>
            </div>

            <form:errors path="roles" cssClass="error"/>
        </div>

        <div class="form-group">
            <label>Phone Number</label>
            <form:input path="phoneNo" id="phone"/>
            <form:errors path="phoneNo" cssClass="error"/>
        </div>

        <div class="form-group">
            <label>Password</label>
            <form:password path="password" id="password"/>
            <form:errors path="password" cssClass="error"/>
        </div>

        <input type="submit" value="Register" class="btn-submit"/>

    </form:form>

    <!-- USER LIST BUTTON -->
    <button type="button" class="btn-secondary" onclick="window.location.href='/user/list'">
        User List
    </button>

</div>

<script>
    // PHONE VALIDATION
    document.getElementById("phone").addEventListener("input", function () {
        this.value = this.value.replace(/[^0-9]/g, '');
        if (this.value.length > 10) {
            this.value = this.value.slice(0, 10);
        }
    });

    // FORM VALIDATION
    document.getElementById("registrationForm").addEventListener("submit", function (e) {
        let isValid = true;

        document.querySelectorAll(".client-error").forEach(el => el.remove());

        function showError(element, message) {
            const span = document.createElement("span");
            span.className = "error client-error";
            span.innerText = message;
            element.parentNode.appendChild(span);
            isValid = false;
        }

        const name = document.getElementById("name").value.trim();
        const email = document.getElementById("email").value.trim();
        const phone = document.getElementById("phone").value.trim();
        const password = document.getElementById("password").value;
        const roles = document.querySelectorAll('input[name="roles"]:checked');

        const nameRegex = /^[A-Za-z ]{3,}$/;
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        const phoneRegex = /^[0-9]{10}$/;
        const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&]).{8,}$/;

        if (!name || !nameRegex.test(name)) {
            showError(document.getElementById("name"), "Enter a valid name (min 3 letters)");
        }

        if (!email || !emailRegex.test(email)) {
            showError(document.getElementById("email"), "Enter a valid email address");
        }

        if (roles.length === 0) {
            showError(document.querySelector(".dropdown"), "Select at least one role");
        }

        if (!phone || !phoneRegex.test(phone)) {
            showError(document.getElementById("phone"), "Enter valid 10-digit phone number");
        }

        if (!password || !passwordRegex.test(password)) {
            showError(document.getElementById("password"),
                "Password must be 8+ chars with uppercase, lowercase, number & special char"
            );
        }

        if (!isValid) {
            e.preventDefault();
        }
    });

    // DROPDOWN TOGGLE
    function toggleDropdown() {
        const dropdown = document.getElementById("rolesDropdown");
        dropdown.style.display = dropdown.style.display === "block" ? "none" : "block";
    }

    document.addEventListener("click", function (e) {
        const dropdown = document.getElementById("rolesDropdown");
        const button = document.querySelector(".dropdown-btn");

        if (!button.contains(e.target) && !dropdown.contains(e.target)) {
            dropdown.style.display = "none";
        }
    });
</script>

</body>
</html>