<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Unit List</title>

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
          rel="stylesheet">

    <style>
        body {
            background: linear-gradient(135deg, #ede9fe, #ddd6fe);
            min-height: 100vh;
            font-family: "Segoe UI", Arial, sans-serif;
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
                    <h4 class="mb-0">List of Units</h4>
                </div>

                <div class="card-body">

                    <c:if test="${empty units}">
                        <div class="alert alert-warning text-center">
                            No units found
                        </div>
                    </c:if>

                    <c:if test="${not empty units}">
                        <table class="table table-bordered table-hover text-center align-middle">
                            <thead>
                            <tr>
                                <th>ID</th>
                                <th>Unit Name</th>
                                <th>Status</th>
                                <th>Action</th>
                            </tr>
                            </thead>

                            <tbody>
                            <c:forEach var="unit" items="${units}">
                                <tr>
                                    <td class="fw-semibold">${unit.id}</td>
                                    <td>${unit.identifier}</td>

                                    <td>
                                        <c:choose>
                                            <c:when test="${unit.status}">
                                                <span class="badge bg-success">Active</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge bg-danger">Inactive</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>

                                    <td class="d-flex justify-content-center gap-2">
                                        <a href="${pageContext.request.contextPath}/unit/get?identifier=${unit.identifier}"
                                           class="btn btn-primary btn-sm">
                                            Edit
                                        </a>

                                        <a href="${pageContext.request.contextPath}/unit/delete?identifier=${unit.identifier}"
                                           class="btn btn-danger btn-sm"
                                           onclick="return confirm('Are you sure you want to delete this unit?');">
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

                    <a href="${pageContext.request.contextPath}/unit/add" class="btn btn-success">
                        + Add New Unit
                    </a>
                </div>

            </div>

        </div>
    </div>
</div>

</body>
</html>