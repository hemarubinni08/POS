<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>User Management</title>

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
          rel="stylesheet">

    <style>
        body {
            background-color: #EDE9E6;
            min-height: 100vh;
        }

        .table thead th {
            background-color: #C9996B !important;
            color: white;
        }

        a.user-link {
            text-decoration: none;
            font-weight: 500;
        }

        a.user-link:hover {
            text-decoration: underline;
        }
    </style>
</head>

<body>

<div class="container mt-5">

    <!-- Page Title -->
    <h3 class="text-center mb-4">User Management</h3>

    <!-- NO USERS MESSAGE -->
    <c:if test="${empty users}">
        <div class="alert alert-warning text-center">
            No users found
        </div>
    </c:if>

    <!-- USERS TABLE -->
    <c:if test="${not empty users}">
        <div class="table-responsive">
            <table class="table table-bordered table-hover align-middle">
                <thead>
                <tr>
                    <th>Email</th>
                    <th>Name</th>
                    <th>Phone</th>
                    <th>Roles</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="user" items="${users}">
                    <tr>
                        <td>${user.username}</td>
                        <td>${user.name}</td>
                        <td>${user.phoneNo}</td>
                        <td>${user.roles}</td>
                        <td class="d-flex justify-content-center gap-2">
                            <a class="btn btn-primary btn-sm"
                               href="/user/get?username=${user.username}">
                                Edit
                            </a>

                            <a class="btn btn-danger btn-sm"
                               href="/user/delete?username=${user.username}"
                               onclick="return confirm('Are you sure you want to delete this user?');">
                                Delete
                            </a>
                        </td>

                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </c:if>

    <!-- ACTION BUTTONS -->
    <div class="d-flex justify-content-center gap-3 mt-4">
        <a href="/" class="btn btn-secondary">
            Home
        </a>

        <a href="/register" class="btn btn-success">
            Register
        </a>
    </div>

    <!-- FOOTER TEXT -->
    <div class="text-center text-muted small mt-3">
        User Management System
    </div>

</div>

</body>
</html>