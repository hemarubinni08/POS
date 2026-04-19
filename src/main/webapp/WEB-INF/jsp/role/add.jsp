<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add Role</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
          rel="stylesheet">

    <style>
        body {
            background: linear-gradient(135deg, #667eea, #764ba2);
            min-height: 100vh;
        }
        .card {
            border-radius: 12px;
        }
        .form-control {
            border-radius: 8px;
        }
    </style>
</head>
<body>

<div class="container d-flex justify-content-center align-items-center mt-5">
    <div class="col-md-5">

        <div class="card shadow-lg">
            <div class="card-header text-center bg-primary text-white">
                <h4 class="mb-0">Add New Role</h4>
            </div>

            <div class="card-body">

                <!-- Success Message -->
                <c:if test="${not empty role}">
                    <div class="alert alert-success text-center">
                        ${role}
                    </div>
                </c:if>

                <!-- Form -->
                <form:form method="post"
                           action="/role/add"
                           modelAttribute="roleDto">

                    <!-- Role Name -->
                    <div class="mb-3">
                        <label class="form-label fw-semibold">Role Name</label>
                        <form:input path="identifier"
                                    cssClass="form-control"
                                    placeholder="Enter role name"
                                    required="required"/>
                    </div>

                    <!-- Description -->
                    <div class="mb-3">
                        <label class="form-label fw-semibold">Role Description</label>
                        <form:input path="description"
                                    cssClass="form-control"
                                    placeholder="Enter description"
                                    required="required"/>
                    </div>

                    <!-- Buttons -->
                    <div class="d-flex justify-content-between">
                        <a href="/role/list" class="btn btn-secondary">
                            Cancel
                        </a>

                        <button type="submit" class="btn btn-primary">
                            Add Role
                        </button>
                    </div>

                    <!-- Error Message -->
                    <c:if test="${not empty message}">
                        <div class="alert alert-danger mt-3 text-center">
                            ${message}
                        </div>
                    </c:if>

                </form:form>

            </div>

            <div class="card-footer text-center text-muted small">
                POS Management System
            </div>
        </div>

    </div>
</div>

</body>
</html>