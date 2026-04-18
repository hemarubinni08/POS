<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Role List</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
          rel="stylesheet">

    <style>
        body {
            background-color: #f4f6fb;
            font-family: 'Poppins', sans-serif;
        }

        /* CARD STYLE */
        .card {
            border-radius: 10px;
            border: none;
            box-shadow: 0 4px 12px rgba(0,0,0,0.06);
        }

        /* HEADER */
        .card-header {
            background: #4e73df !important;
            color: #fff !important;
            border-top-left-radius: 10px;
            border-top-right-radius: 10px;
            font-weight: 600;
        }

        /* TABLE */
        table {
            border-radius: 8px;
            overflow: hidden;
        }

        table th {
            background-color: #4e73df;
            color: white;
            font-weight: 500;
            font-size: 14px;
        }

        table td {
            font-size: 14px;
            color: #555;
        }

        .table-hover tbody tr:hover {
            background-color: #f1f4ff;
        }

        /* ✅ UPDATE BUTTON → BLUE */
        .btn-warning {
            background-color: #4e73df;
            border: none;
            color: #fff;
        }

        .btn-warning:hover {
            background-color: #2e59d9;
        }

        /* DELETE BUTTON */
        .btn-danger {
            background-color: #e74a3b;
            border: none;
        }

        .btn-danger:hover {
            background-color: #c0392b;
        }

        /* ADD BUTTON */
        .btn-success {
            background-color: #1cc88a;
            border: none;
        }

        .btn-success:hover {
            background-color: #17a673;
        }

        /* HOME BUTTON */
        .btn-secondary {
            background-color: #858796;
            border: none;
        }

        .btn-secondary:hover {
            background-color: #6c757d;
        }

        /* FOOTER */
        .card-footer {
            background-color: #f8f9fc;
            border-top: none;
        }

        /* ALERT */
        .alert-warning {
            background-color: #fff3cd;
            border: none;
            color: #856404;
        }
    </style>
</head>

<body>

<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-10">

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
                                <th>Description</th>
                                <th>Actions</th>
                            </tr>
                            </thead>
                            <tbody>

                            <c:forEach var="role" items="${roles}">
                                <tr>
                                    <td>${role.id}</td>
                                    <td>${role.identifier}</td>
                                    <td>${role.description}</td>
                                    <td class="d-flex justify-content-center gap-2">
                                        <!-- UPDATE (BLUE) -->
                                        <a href="/role/get?identifier=${role.identifier}"
                                           class="btn btn-warning btn-sm">
                                            Update
                                        </a>

                                        <!-- DELETE (RED) -->
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

                <div class="card-footer text-center d-flex justify-content-center gap-3">
                    <a href="/" class="btn btn-secondary">Home</a>
                    <a href="/role/add" class="btn btn-success">+ Add New Role</a>
                </div>

            </div>

        </div>
    </div>
</div>

</body>
</html>
``