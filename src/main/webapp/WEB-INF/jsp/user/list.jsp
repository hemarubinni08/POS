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
            background-color: #f4f6fb;
            font-family: 'Poppins', sans-serif;
        }

        /* CARD */
        .card {
            border-radius: 10px;
            border: none;
            box-shadow: 0 4px 12px rgba(0,0,0,0.06);
        }

        /* HEADER TITLE */
        h3 {
            font-weight: 600;
            color: #333;
        }

        /* TABLE */
        .table th {
            background-color: #4e73df;
            color: white;
            font-weight: 500;
            font-size: 14px;
        }

        .table td {
            font-size: 14px;
            color: #555;
        }

        .table-hover tbody tr:hover {
            background-color: #eef3ff;
        }

        /* BUTTONS */
        .btn-primary {
            background-color: #4e73df;
            border: none;
        }

        .btn-primary:hover {
            background-color: #2e59d9;
        }

        .btn-danger {
            background-color: #e74a3b;
            border: none;
        }

        .btn-danger:hover {
            background-color: #c0392b;
        }

        .btn-secondary {
            background-color: #858796;
            border: none;
        }

        .btn-secondary:hover {
            background-color: #6c757d;
        }

        /* ALERT */
        .alert-warning {
            background-color: #fff3cd;
            border: none;
            color: #856404;
        }

        /* FOOTER */
        .card-footer {
            background-color: #f8f9fc;
            border-top: none;
        }

        /* LINKS */
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
    <div class="card shadow-lg">
        <div class="card-body">

            <h3 class="text-center mb-4">User Management</h3>
            <div class="mb-3 text-start">
                <a href="/" class="btn btn-secondary btn-sm">
                    Home
                </a>
            </div>

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
                                <td>${user.username}</td>
                                <td>${user.name}</td>
                                <td>${user.phoneNo}</td>
                                <td>${user.roles}</td>
                                <td>
                                    <a class="btn btn-primary btn-sm me-1"
                                       href="/user/get?username=${user.username}">
                                        Update
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

        </div>

        <div class="card-footer text-center">
            <div class="text-muted small">
                User Management System
            </div>
        </div>

    </div>
</div>
</body>
</html>