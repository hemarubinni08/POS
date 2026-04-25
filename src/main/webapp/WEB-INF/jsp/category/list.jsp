<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Category Management</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        body {
            background: linear-gradient(135deg, #ede9fe, #ddd6fe);
            min-height: 100vh;
        }

        .table th {
            background-color: #a78bfa;
            color: white;
        }
    </style>
</head>

<body>

<div class="container mt-5">
    <div class="card shadow-lg">
        <div class="card-header text-center">
            <h4>List of Categories</h4>
        </div>

        <div class="card-body">

            <c:choose>
                <c:when test="${empty categories}">
                    <div class="alert alert-warning text-center">
                        No categories available
                    </div>
                </c:when>

                <c:otherwise>
                    <table class="table table-bordered table-hover text-center">
                        <thead>
                        <tr>
                            <th>category</th>
                            <th>Super Category</th>
                            <th>Actions</th>
                        </tr>
                        </thead>

                        <tbody>
                        <c:forEach var="category" items="${categories}">
                            <tr>
                                <td>${category.identifier}</td>
                                <td>${category.superCategory}</td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/category/get?identifier=${category.identifier}"
                                       class="btn btn-sm btn-primary">Edit</a>

                                    <a href="${pageContext.request.contextPath}/category/delete?identifier=${category.identifier}"
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
            <a href="${pageContext.request.contextPath}/category/add"
               class="btn btn-success">Add Category</a>
        </div>
    </div>
</div>

</body>
</html>
