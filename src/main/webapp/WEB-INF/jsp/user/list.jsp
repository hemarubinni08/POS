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
            background: linear-gradient(135deg, #ede9fe, #ddd6fe);
            min-height: 100vh;
        }

        .card {
            border-radius: 15px;
            background-color: #ffffff;
        }

        .table th {
            background-color: #a78bfa;
            color: #ffffff;
        }

        .table-hover tbody tr:hover {
            background-color: #f5f3ff;
        }

        .btn-secondary {
            background-color: #c4b5fd;
            border: none;
            color: #4c1d95;
        }

        .btn-secondary:hover {
            background-color: #b197fc;
            color: #ffffff;
        }

        .btn-success {
            background-color: #7c3aed;
            border: none;
        }

        .btn-success:hover {
            background-color: #6d28d9;
        }
    </style>
</head>

<body>

<div class="container mt-5">
    <div class="card shadow-lg">
        <div class="card-body">

            <h3 class="text-center mb-4 fw-semibold">
                User Management
            </h3>

            <!-- NO USERS MESSAGE -->
            <c:if test="${empty users}">
                <div class="alert alert-warning text-center">
                    No users found
                </div>
            </c:if>

            <c:if test="${not empty users}">
                <div class="table-responsive">
                    <table class="table table-bordered table-hover align-middle text-center">
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

                                <td class="fw-semibold">
                                    ${user.username}
                                </td>

                                <td>${user.name}</td>
                                <td>${user.phoneNo}</td>
                                <td>${user.roles}</td>


                                <td class="d-flex justify-content-center gap-2">
                                    <a href="/user/get?username=${user.username}"
                                       class="btn btn-primary btn-sm">
                                        Edit
                                    </a>

                                    <a href="/user/delete?username=${user.username}"
                                       class="btn btn-danger btn-sm"
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

        </div>

        <div class="card-footer text-center">
            <div class="d-flex justify-content-center gap-3">
                <a href="/" class="btn btn-secondary">
                    Home
                </a>

                <a href="/register" class="btn btn-success">
                    Register
                </a>
            </div>

            <div class="text-muted small mt-2">
                User Management System
            </div>
        </div>

    </div>
</div>

</body>
</html>