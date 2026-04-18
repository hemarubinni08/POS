<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add Role</title>

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
          rel="stylesheet">

    <style>
        body {
            background: linear-gradient(135deg, #ede9fe, #ddd6fe); /* same light purple gradient */
            min-height: 100vh;
        }

        .card {
            border-radius: 12px;
            background-color: #ffffff;
            box-shadow: 0 20px 40px rgba(76, 29, 149, 0.18); /* soft purple shadow */
        }

        .card-header {
            background-color: #a78bfa !important; /* soft purple */
            color: #ffffff;
        }

        .form-control {
            border-radius: 8px;
            border: 1px solid #c4b5fd;
        }

        .form-control:focus {
            border-color: #a78bfa;
            box-shadow: 0 0 0 0.15rem rgba(167, 139, 250, 0.35);
        }

        .btn-primary {
            background-color: #7c3aed;
            border: none;
        }

        .btn-primary:hover {
            background-color: #6d28d9;
        }

        .card-footer {
            color: #6b7280;
            background-color: #fafafa;
        }
    </style>
</head>

<body>

<div class="container d-flex justify-content-center align-items-center mt-5">
    <div class="col-md-5">

        <div class="card shadow-lg">
            <div class="card-header text-center">
                <h4 class="mb-0">Add New Role</h4>
            </div>

            <div class="card-body">

                <c:if test="${not empty role}">
                    <div class="alert alert-success text-center">
                        ${role}
                    </div>
                </c:if>

                <form:form method="post"
                           action="/role/add"
                           modelAttribute="roleDto">

                    <div class="mb-3">
                        <label class="form-label fw-semibold">Role Name</label>
                        <form:input path="identifier"
                                    cssClass="form-control"
                                    placeholder="Enter role name" />
                    </div>

                    <div class="d-grid">
                        <button type="submit" class="btn btn-primary btn-lg">
                            Add Role
                        </button>
                    </div>

                </form:form>

            </div>

            <div class="card-footer text-center small">
                POS Management System
            </div>
        </div>

    </div>
</div>

</body>
</html>