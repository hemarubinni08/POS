<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User Registration</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600&display=swap" rel="stylesheet">

    <style>
        body {
            font-family: 'Poppins', sans-serif;
            background: linear-gradient(to right, #bdc3c7, #2c3e50);
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
            margin-bottom: 20px;
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

        .btn-primary {
            width: 100%;
            border-radius: 10px;
            padding: 10px;
            font-weight: 600;
        }

        .alert {
            font-size: 13px;
            padding: 8px;
            margin-bottom: 15px;
        }
    </style>
</head>

<body>

<div class="card bg-white">

    <h3>Register User</h3>

    <!-- MESSAGE FROM CONTROLLER -->
    <c:if test="${not empty message}">
        <div class="alert alert-danger text-center">
            ${message}
        </div>
    </c:if>

    <!-- POPUP (optional) -->
    <c:if test="${not empty message}">
        <script>
            alert("${message}");
        </script>
    </c:if>

    <form:form action="register" method="post" modelAttribute="userDto">

        <!-- NAME -->
        <div class="mb-3">
            <label class="form-label">Full Name</label>
            <form:input path="name" cssClass="form-control" required="required"/>
        </div>

        <!-- EMAIL -->
        <div class="mb-3">
            <label class="form-label">Email</label>
            <form:input path="username" cssClass="form-control" type="email" required="required"/>
        </div>

        <!-- ROLES -->
        <div class="mb-3">
            <label class="form-label">Select Roles</label>

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

        <!-- PHONE (ONLY NUMBERS) -->
        <div class="mb-3">
            <label class="form-label">Phone Number</label>

            <form:input path="phoneNo"
                        cssClass="form-control"
                        required="required"
                        maxlength="10"
                        pattern="^[0-9]{10}$"
                        oninput="this.value = this.value.replace(/[^0-9]/g, '')"
                        title="Enter only 10 digit numbers"/>
        </div>

        <!-- PASSWORD -->
        <div class="mb-3">
            <label class="form-label">Password</label>
            <form:password path="password" cssClass="form-control" required="required"/>
        </div>

        <!-- BUTTON -->
        <button type="submit" class="btn btn-primary">
            Register
        </button>

        <a href="/login" class="btn btn-link w-100 mt-2">
            Already have an account? Login
        </a>

    </form:form>

</div>

</body>
</html>