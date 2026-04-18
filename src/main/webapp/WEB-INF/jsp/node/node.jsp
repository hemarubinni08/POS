<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Update Node</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        body {
            background: linear-gradient(to right, #bdc3c7, #2c3e50);
            min-height: 100vh;
        }
        .card {
            border-radius: 16px;
        }
        .form-control {
            border-radius: 10px;
        }
        .btn {
            border-radius: 10px;
        }
    </style>
</head>

<body>

<nav class="navbar navbar-dark bg-dark shadow">
    <div class="container-fluid">
        <span class="navbar-brand fw-bold">User Profile</span>
        <a href="/node/list" class="btn btn-outline-light btn-sm">Back</a>
    </div>
</nav>

<div class="container d-flex justify-content-center align-items-center" style="min-height: 100vh;">
    <div class="card shadow p-4" style="width: 450px;">

        <h3 class="text-center mb-4 fw-bold">Update Node</h3>

        <!-- SHOW ERROR MESSAGE -->
        <c:if test="${not empty message}">
            <div class="alert alert-danger">${message}</div>
        </c:if>

        <form action="/node/update" method="post">

            <!-- IDENTIFIER (READ ONLY) -->
            <div class="mb-3">
                <label class="form-label">Identifier</label>
                <input type="text"
                       class="form-control"
                       name="identifier"
                       value="${nodeDto.identifier}"
                       readonly>
            </div>

            <!-- PATH -->
            <div class="mb-3">
                <label class="form-label">Path</label>
                <input type="text"
                       class="form-control"
                       name="path"
                       value="${nodeDto.path}"
                       required>
            </div>

            <!-- ROLES -->
            <div class="mb-4">
                <label class="form-label fw-semibold">Roles (Multiple)</label>

                <div class="border rounded p-2">

                    <c:if test="${empty roles}">
                        <span class="text-danger">No roles available</span>
                    </c:if>

                    <c:forEach items="${roles}" var="role">

                        <c:set var="isChecked" value="false" />

                        <!-- CHECK IF ROLE ALREADY EXISTS -->
                        <c:if test="${not empty nodeDto.roles}">
                            <c:forEach items="${nodeDto.roles}" var="nr">
                                <c:if test="${nr eq role.identifier}">
                                    <c:set var="isChecked" value="true" />
                                </c:if>
                            </c:forEach>
                        </c:if>

                        <div class="form-check">
                            <input class="form-check-input"
                                   type="checkbox"
                                   name="roles"
                                   value="${role.identifier}"
                                   <c:if test="${isChecked}">checked</c:if>>

                            <label class="form-check-label">
                                ${role.identifier}
                            </label>
                        </div>

                    </c:forEach>

                </div>
            </div>

            <!-- BUTTONS -->
            <div class="d-flex gap-2">
                <button type="submit" class="btn btn-primary w-100">Update & Save</button>
                <a href="/node/list" class="btn btn-outline-secondary w-100">Cancel</a>
            </div>

        </form>

    </div>
</div>

</body>
</html>