<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Register</title>

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        body {
            background-color: #E9EEF5;
        }

        .card {
            width: 380px;
            border: none;
            border-radius: 12px;
        }

        h3 {
            color: #000;
            font-weight: 600;
        }

        .form-label {
            font-size: 13px;
        }

        .role-box {
            background: #f8f9fa;
            padding: 10px;
            border-radius: 8px;
            max-height: 120px;
            overflow-y: auto;
        }
    </style>
</head>

<body class="d-flex align-items-start justify-content-center min-vh-100 pt-5">

<div class="card shadow p-4">

    <!-- TITLE -->
    <h3 class="text-center mb-4">Register</h3>

    <!-- ERROR MESSAGE -->
    <c:if test="${not empty message}">
        <div id="errorAlert" class="alert alert-danger text-center py-2 mb-3">
            ${message}
        </div>
    </c:if>

    <!-- FORM -->
    <form:form action="register" method="post" modelAttribute="userDto">

        <!-- NAME -->
        <div class="mb-3">
            <label class="form-label">Full Name</label>
            <form:input path="name" cssClass="form-control" required="required"/>
        </div>

        <!-- EMAIL -->
        <div class="mb-3">
            <label class="form-label">Email</label>
            <form:input path="username" type="email" cssClass="form-control" required="required"/>
        </div>

        <!-- ROLES -->
        <div class="mb-3">
            <label class="form-label">Roles</label>

            <div class="role-box">
                <c:forEach items="${roles}" var="role">
                    <div class="form-check">
                        <form:checkbox path="roles"
                                       value="${role.identifier}"
                                       cssClass="form-check-input"
                                       id="role_${role.identifier}" />

                        <label class="form-check-label" for="role_${role.identifier}">
                            ${role.identifier}
                        </label>
                    </div>
                </c:forEach>
            </div>
        </div>

        <!-- PHONE -->
        <div class="mb-3">
            <label class="form-label">Phone Number</label>
            <form:input path="phoneNo"
                        cssClass="form-control"
                        maxlength="10"
                        pattern="^[0-9]{10}$"
                        oninput="this.value=this.value.replace(/[^0-9]/g,'')"
                        required="required"/>
        </div>

        <!-- PASSWORD -->
        <div class="mb-3">
            <label class="form-label">Password</label>
            <form:password path="password" cssClass="form-control" required="required"/>
        </div>

        <!-- BUTTON -->
        <button type="submit" class="btn btn-success w-100">
            Register
        </button>

        <!-- LOGIN LINK -->
        <div class="text-center mt-3">
            <small>
                Already have an account?
                <a href="/login" class="text-decoration-none">Login</a>
            </small>
        </div>

    </form:form>
</div>

<!-- AUTO HIDE ERROR -->
<script>
    document.addEventListener("DOMContentLoaded", function () {
        const alertBox = document.getElementById("errorAlert");

        if (alertBox) {
            setTimeout(() => {
                alertBox.style.opacity = "0";
                alertBox.style.transition = "all 0.5s ease";
                setTimeout(() => alertBox.remove(), 500);
            }, 3000);
        }
    });
</script>

</body>
</html>
