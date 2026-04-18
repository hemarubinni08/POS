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
            background: linear-gradient(135deg, #ede9fe, #ddd6fe);
            min-height: 100vh;
        }

        .card {
            border-radius: 16px;
            background-color: #ffffff;
            box-shadow: 0 20px 40px rgba(76, 29, 149, 0.18);
        }

        .card-header {
            background-color: #a78bfa;
            color: #ffffff;
            border-top-left-radius: 16px;
            border-top-right-radius: 16px;
        }

        table th {
            background-color: #a78bfa;
            color: white;
        }

        table.table-hover tbody tr:hover {
            background-color: #f5f3ff;
        }

        .btn-secondary {
            background-color: #b197fc;
            border: none;
            color: #ffffff;
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
    <div class="row justify-content-center">
        <div class="col-md-8">

            <div class="card shadow-lg">
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
                        <table class="table table-bordered table-hover text-center align-middle">
                            <thead>
                            <tr>
                                <th>ID</th>
                                <th>Role</th>
                                <th>Action</th>
                            </tr>
                            </thead>

                            <tbody>
                            <c:forEach var="role" items="${roles}">
                                <tr>

                                    <td class="fw-semibold">
                                        ${role.id}
                                    </td>

                                    <td>${role.identifier}</td>


                                    <td class="d-flex justify-content-center gap-2">
                                        <a href="/role/get?identifier=${role.identifier}"
                                           class="btn btn-primary btn-sm">
                                            Edit
                                        </a>

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
                    </c:if>

                </div>

                <div class="card-footer text-center bg-light d-flex justify-content-center gap-3">
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