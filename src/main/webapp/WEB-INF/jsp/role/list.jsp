<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Role List</title>

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
          rel="stylesheet">

    <style>
        body {
            background-color: #FFF8F0;
            min-height: 100vh;
            font-family: "Segoe UI", Arial, sans-serif;
            padding-top: 40px;
        }

        .card {
            border-radius: 16px;
            box-shadow: 0 18px 35px rgba(75, 46, 43, 0.25);
            border: none;
        }

        .card-header {
            background-color: #4B2E2B;
            color: #FFF8F0;
            border-top-left-radius: 16px;
            border-top-right-radius: 16px;
        }

        h4 {
            font-weight: 600;
        }

        .table th {
            background-color: #4B2E2B;
            color: #FFF8F0;
            border-color: #4B2E2B;
            font-weight: 600;
            text-align: center;
        }

        .table td {
            vertical-align: middle;
            text-align: center;
        }

        .table-hover tbody tr:hover {
            background-color: #fff3eb;
        }

        .btn-edit {
            background-color: #4B2E2B;
            color: #FFF8F0;
            border-radius: 6px;
            padding: 4px 12px;
            font-size: 13px;
            font-weight: 600;
            text-decoration: none;
            transition: all 0.3s ease;
            display: inline-block;
        }

        .btn-edit:hover {
            background-color: #3a2421;
            color: #FFF8F0;
        }

        .btn-danger {
            background-color: #8d3c36;
            border: none;
        }

        .btn-danger:hover {
            background-color: #702f2a;
        }

        .btn-secondary {
            background-color: #4B2E2B;
            border: none;
        }

        .btn-secondary:hover {
            background-color: #3a2421;
        }

        .btn-success {
            background-color: #6b4a46;
            border: none;
        }

        .btn-success:hover {
            background-color: #4B2E2B;
        }

        .card-footer {
            background-color: #fff3eb;
            border-top: none;
        }
    </style>
</head>

<body>

<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-8">

            <div class="card">
                <div class="card-header text-center">
                    <h4 class="mb-0">List of Roles</h4>
                </div>

                <div class="card-body">

                    <c:if test="${empty roles}">
                        <div class="alert alert-warning text-center">
                            No roles found
                        </div>
                    </c:if>

                    <c:if test="${not empty roles}">
                        <div class="table-responsive">
                            <table class="table table-bordered table-hover align-middle">
                                <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Role</th>
                                    <th>Description</th>
                                    <th>Edit</th>
                                    <th>Action</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="role" items="${roles}">
                                    <tr>
                                        <td>${role.id}</td>
                                        <td>${role.identifier}</td>
                                        <td>${role.description}</td>
                                        <td>
                                            <a href="/role/get?identifier=${role.identifier}"
                                               class="btn-edit">
                                                Edit
                                            </a>
                                        </td>
                                        <td>
                                            <a href="/role/delete?identifier=${role.identifier}"
                                               class="btn btn-danger btn-sm"
                                               onclick="return confirm('Are you sure you want to delete this role?');">
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

                <div class="card-footer text-center d-flex justify-content-center gap-3">
                    <a href="/" class="btn btn-secondary">
                        Home
                    </a>

                    <a href="/role/add" class="btn btn-success">
                        + Add New Role
                    </a>
                </div>

            </div>

        </div>
    </div>
</div>

</body>
</html>
``