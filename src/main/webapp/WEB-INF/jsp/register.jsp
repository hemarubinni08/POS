<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User Registration</title>

    <!-- Bootstrap + Font -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600&display=swap" rel="stylesheet">

    <style>
        body {
            font-family: 'Poppins', sans-serif;
            background: linear-gradient(135deg, #667eea, #764ba2);
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .card {
            width: 420px;
            border-radius: 18px;
            padding: 25px 30px;
            box-shadow: 0 20px 40px rgba(0,0,0,0.2);
        }

        h3 {
            text-align: center;
            margin-bottom: 25px;
            font-weight: 600;
            color: #4b6cb7;
        }

        .form-label {
            font-size: 13px;
            font-weight: 500;
        }

        .form-control {
            border-radius: 8px;
        }

        .role-box {
            background: #f8f9fa;
            padding: 10px;
            border-radius: 10px;
            max-height: 140px;
            overflow-y: auto;
        }

        .form-check {
            margin-bottom: 5px;
        }

        .btn-primary {
            width: 100%;
            border-radius: 10px;
            padding: 10px;
            font-weight: 600;
        }

        .form-check-input:checked {
            background-color: #4b6cb7;
            border-color: #4b6cb7;
        }
    </style>
</head>

<body>

<div class="card bg-white">

    <h3>Register User</h3>

    <form:form action="register" method="post" modelAttribute="userDto">

        <!-- NAME -->
        <div class="mb-3">
            <label class="form-label">Full Name <span class="text-danger">*</span></label>
            <form:input path="name"
                        cssClass="form-control"
                        placeholder="Enter your name"
                        required="required"/>
        </div>

        <!-- EMAIL (VALIDATED FRONTEND ONLY) -->
        <div class="mb-3">
            <label class="form-label">Email Address <span class="text-danger">*</span></label>

            <form:input path="username"
                        cssClass="form-control"
                        placeholder="Enter valid email"
                        type="email"
                        required="required"
                        pattern="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
                        title="Enter valid email like example@gmail.com"/>
        </div>

        <!-- ROLES -->
        <div class="mb-3">
            <label class="form-label">Select Roles <span class="text-danger">*</span></label>

            <div class="role-box">
                <c:forEach items="${roles}" var="role">
                    <div class="form-check">

                        <form:checkbox path="roles"
                                       value="${role.identifier}"
                                       cssClass="form-check-input"
                                       id="role_${role.identifier}"/>

                        <label class="form-check-label" for="role_${role.identifier}">
                            ${role.identifier}
                        </label>

                    </div>
                </c:forEach>
            </div>
        </div>

        <!-- PHONE -->
        <div class="mb-3">
            <label class="form-label">Phone Number <span class="text-danger">*</span></label>

            <form:input path="phoneNo"
                        cssClass="form-control"
                        placeholder="Enter 10-digit number"
                        required="required"
                        pattern="^[6-9][0-9]{9}$"
                        title="Enter valid 10-digit Indian mobile number"/>
        </div>

        <!-- PASSWORD -->
        <div class="mb-4">
            <label class="form-label">Password <span class="text-danger">*</span></label>

            <form:password path="password"
                           cssClass="form-control"
                           placeholder="Enter password"
                           required="required"
                           minlength="6"
                           title="Password must be at least 6 characters"/>
        </div>

        <!-- BUTTON -->
        <button type="submit" class="btn btn-primary mb-2">
            Register
        </button>

        <a href="/" class="btn btn-danger w-100 mb-3 mt-2">
            Cancel
        </a>

        <div class="text-center">
            <small>
                Already have an account?
                <a href="/login" class="text-decoration-none fw-semibold">
                    Login
                </a>
            </small>
        </div>

    </form:form>

</div>

</body>
</html>