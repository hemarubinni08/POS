<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Edit Role</title>

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
          rel="stylesheet">

    <style>
        body {
            min-height: 100vh;
            background: linear-gradient(135deg, #ede9fe, #ddd6fe); /* light purple gradient */
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .card {
            width: 420px;
            border-radius: 15px;
            background-color: #ffffff;
            box-shadow: 0 20px 40px rgba(76, 29, 149, 0.18); /* soft purple shadow */
        }

        h4 {
            font-weight: 600;
            color: #6d28d9; /* purple heading */
        }

        .form-control {
            border-radius: 8px;
            border: 1px solid #c4b5fd;
        }

        .form-control:focus {
            border-color: #a78bfa;
            box-shadow: 0 0 0 0.15rem rgba(167, 139, 250, 0.35);
        }

        .help-text {
            font-size: 12px;
            color: #6b7280;
            margin-top: 6px;
        }

        .btn-primary {
            background-color: #7c3aed;
            border: none;
        }

        .btn-primary:hover {
            background-color: #6d28d9;
        }

        .btn-outline-secondary {
            color: #6b21a8;
            border-color: #c4b5fd;
        }

        .btn-outline-secondary:hover {
            background-color: #ddd6fe;
            color: #4c1d95;
            border-color: #c4b5fd;
        }
    </style>
</head>

<body>

<div class="card">
    <div class="card-body">

        <h4 class="text-center mb-4">Edit Role</h4>

        <c:if test="${empty role}">
            <div class="alert alert-danger text-center">
                Role not found
            </div>
        </c:if>

        <c:if test="${not empty role}">

            <form:form action="/role/update"
                       method="post"
                       modelAttribute="role">

                <!-- Hidden ID -->
                <form:hidden path="id"/>

                <!-- Role Identifier (READ ONLY) -->
                <div class="mb-4">
                    <label class="form-label fw-semibold">Role Name</label>

                    <form:input path="identifier"
                                cssClass="form-control"
                                placeholder="Enter role"
                                required="true"
                                readonly="true"/>
                </div>

                <div class="mb-4">
                    <label class="form-label fw-semibold">Description</label>

                    <form:input path="description"
                                cssClass="form-control"
                                placeholder="Enter role"
                                required="true"/>
                </div>

                <div class="d-flex justify-content-between">
                    <a href="/role/list" class="btn btn-outline-secondary">
                        Cancel
                    </a>
                    <button type="submit" class="btn btn-primary">
                        Update
                    </button>
                </div>

            </form:form>

        </c:if>

    </div>
</div>
</body>
</html>