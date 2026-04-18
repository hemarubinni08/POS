<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Node List</title>

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
    </style>
</head>

<body>

<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-8">

            <!-- Page Title -->
            <h4 class="text-center mb-4">List of Nodes</h4>

            <!-- Empty State -->
            <c:if test="${empty nodes}">
                <div class="alert alert-warning text-center">
                    No nodes found
                </div>
            </c:if>

            <!-- Nodes Table -->
            <c:if test="${not empty nodes}">
                <table class="table table-bordered table-hover align-middle">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Identifier</th>
                        <th>Path</th>
                        <th>Nodes</th>
                        <th>Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="node" items="${nodes}">
                        <tr>
                            <td>${node.id}</td>
                            <td>${node.identifier}</td>
                            <td>${node.path}</td>
                            <td>${node.roles}</td>
                            <td class="d-flex justify-content-center gap-2">
                                <a href="/node/get?identifier=${node.identifier}"
                                   class="btn btn-success btn-sm">
                                    Edit
                                </a>

                                <a href="/node/delete?identifier=${node.identifier}"
                                   class="btn btn-danger btn-sm"
                                   onclick="return confirm('Are you sure you want to delete this node?');">
                                    Delete
                                </a>
                            </td>

                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>

            <!-- Action Buttons -->
            <div class="d-flex justify-content-center gap-3 mt-4">
                <a href="/" class="btn btn-secondary">
                    Home
                </a>

                <a href="/node/add" class="btn btn-success">
                    + Add New Node
                </a>
            </div>

        </div>
    </div>
</div>

</body>
</html>