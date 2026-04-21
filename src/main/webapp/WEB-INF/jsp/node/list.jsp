<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Node Management</title>

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

        .roles {
            font-size: 13px;
            color: #4c1d95;
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

        <div class="card shadow-lg">
                        <div class="card-header text-center">
                            <h4 class="mb-0">List of Nodes</h4>
                        </div>

                        <div class="card-body">

            <c:choose>
                <c:when test="${empty nodes}">
                    <div class="alert alert-warning text-center">
                        No nodes available
                    </div>
                </c:when>

                <c:otherwise>
                    <div class="table-responsive">
                        <table class="table table-bordered table-hover align-middle text-center">
                            <thead>
                            <tr>
                                <th>Identifier</th>
                                <th>Path</th>
                                <th>Roles</th>
                                <th>Actions</th>
                            </tr>
                            </thead>

                            <tbody>
                            <c:forEach var="node" items="${nodes}">
                                <tr>
                                    <td>${node.identifier}</td>
                                    <td>${node.path}</td>

                                    <td class="roles">
                                        <c:forEach var="role" items="${node.roles}" varStatus="s">
                                            ${role}<c:if test="${!s.last}">, </c:if>
                                        </c:forEach>
                                    </td>


                                    <td class="d-flex justify-content-center gap-2">
                                        <a href="${pageContext.request.contextPath}/node/get?identifier=${node.identifier}"
                                           class="btn btn-sm btn-primary">
                                            Edit
                                        </a>

                                        <a href="${pageContext.request.contextPath}/node/delete?identifier=${node.identifier}"
                                           class="btn btn-sm btn-danger">
                                            Delete
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:otherwise>
            </c:choose>

        </div>

        <div class="card-footer text-center">
            <div class="d-flex justify-content-center gap-3">
                <a href="/" class="btn btn-secondary">Home</a>

                <a href="${pageContext.request.contextPath}/node/add"
                   class="btn btn-success">Add Node</a>
            </div>

            <div class="text-muted small mt-2">
                Node Management System
            </div>
        </div>
    </div>
</div>

</body>
</html>