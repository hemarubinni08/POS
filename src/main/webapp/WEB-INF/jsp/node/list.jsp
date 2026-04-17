<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>All Nodes</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
          rel="stylesheet"/>

    <style>
        body {
            background: linear-gradient(135deg, #667eea, #764ba2);
            min-height: 100vh;
        }
        .card {
            border-radius: 15px;
        }
    </style>
</head>
<body>

<div class="container mt-5">
    <div class="card shadow-lg">
        <div class="card-body">

            <h4 class="text-center mb-4">List of Nodes</h4>

            <c:if test="${empty node}">
                <div class="alert alert-warning text-center">
                    No nodes found
                </div>
            </c:if>

            <c:if test="${not empty node}">
                <table class="table table-bordered table-hover text-center align-middle">
                    <head class="table-dark">
                    <tr>
                        <th>ID</th>
                        <th>Node Name</th>
                        <th>Path</th>
                        <th>Roles</th>
                        <th>Actions</th>
                    </tr>
                    </head>
                    <body>
                    <c:forEach var="node" items="${node}">
                        <tr>
                            <td>${node.id}</td>
                            <td>${node.identifier}</td>
                            <td>${node.path}</td>
\                            <td>${node.roles}</td>
                            <td>
                                <a href="/node/get?identifier=${node.identifier}"
                                   class="btn btn-sm btn-warning">
                                    Update
                                </a>

                                <a href="/node/delete?identifier=${node.identifier}"
                                   class="btn btn-sm btn-danger"
                                   onclick="return confirm('Are you sure you want to delete this node?');">
                                    Delete
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                    </body>
                </table>
            </c:if>

            <div class="d-flex justify-content-center gap-3 mt-3">
                <a href="/"
                   class="btn btn-secondary">
                    Home
                </a>

                <a href="/node/add"
                   class="btn btn-primary">
                    Add New Node
                </a>
            </div>

        </div>

        <div class="card-footer text-muted small text-center">
            POS Management System
        </div>
    </div>
</div>

</body>
</html>
