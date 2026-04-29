<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Shelf Management</title>

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
          rel="stylesheet">

    <style>
        body {
            background: linear-gradient(135deg, #ede9fe, #ddd6fe);
            min-height: 100vh;
        }

        .table th {
            background-color: #a78bfa;
            color: #ffffff;
        }

        .btn-success {
            background-color: #7c3aed;
            border: none;
        }
    </style>
</head>

<body>

<div class="container mt-5">
    <div class="card shadow-lg">

        <div class="card-header text-center">
            <h4>List of Shelfs</h4>
        </div>

        <div class="card-body">

            <c:choose>
                <c:when test="${empty shelfs}">
                    <div class="alert alert-warning text-center">
                        No shelfs available
                    </div>
                </c:when>

                <c:otherwise>
                    <table class="table table-bordered table-hover text-center">
                        <thead>
                        <tr>
                            <th>SL</th>
                            <th>Name</th>
                            <th>Status</th>
                            <th>Actions</th>
                        </tr>
                        </thead>

                        <tbody>
                        <c:forEach var="shelf" items="${shelfs}" varStatus="s">
                            <tr>
                                <td>${s.count}</td>
                                <td>${shelf.identifier}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${shelf.status}">
                                            <span class="badge bg-success">Active</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge bg-danger">Inactive</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/shelfs/get?identifier=${shelf.identifier}"
                                       class="btn btn-sm btn-primary">Edit</a>
                                    <a href="${pageContext.request.contextPath}/shelfs/delete?identifier=${shelf.identifier}"
                                       class="btn btn-sm btn-danger">Delete</a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:otherwise>
            </c:choose>

        </div>

        <div class="card-footer text-center">
            <a href="/" class="btn btn-secondary">Home</a>
            <a href="${pageContext.request.contextPath}/shelfs/add"
               class="btn btn-success">Add Shelf</a>
        </div>

    </div>
</div>

</body>
</html>
