<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Stock List</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
          rel="stylesheet">

    <style>
        body {
            background-color: #E9EEF5;
            min-height: 100vh;
        }
        .card {
            border-radius: 16px;
        }
        .btn {
            border-radius: 10px;
        }
        .table th, .table td {
            vertical-align: middle;
        }
    </style>
</head>

<body>

<!-- NAVBAR -->
<nav class="navbar navbar-dark bg-dark shadow">
    <div class="container-fluid">
        <span class="navbar-brand fw-bold">Stock Management</span>

        <div class="d-flex gap-2">
            <a href="${pageContext.request.contextPath}/"
               class="btn btn-outline-light btn-sm">Home</a>

            <a href="${pageContext.request.contextPath}/stock/add"
               class="btn btn-light btn-sm fw-semibold">
                + Add Stock
            </a>
        </div>
    </div>
</nav>

<!-- MAIN -->
<div class="container mt-5">

    <div class="card shadow p-3">

        <h3 class="fw-bold text-center mb-3">Stock List</h3>

        <!-- MESSAGE -->
        <c:if test="${not empty message}">
            <div class="alert alert-info text-center">
                ${message}
            </div>
        </c:if>

        <!-- TABLE -->
        <div class="table-responsive">
            <table class="table table-hover table-striped">

                <thead class="table-dark">
                <tr>
                    <th>Identifier</th>
                    <th>Product</th>
                    <th>Warehouse</th>
                    <th>Quantity</th>
                    <th>Reorder Level</th>
                    <th class="text-center">Actions</th>
                </tr>
                </thead>

                <tbody>

                <c:choose>
                    <c:when test="${empty stocks}">
                        <tr>
                            <td colspan="6" class="text-center py-4 text-muted">
                                No stock available
                            </td>
                        </tr>
                    </c:when>

                    <c:otherwise>
                        <c:forEach var="s" items="${stocks}">
                            <tr>

                                <!-- IDENTIFIER -->
                                <td>
                                    <a href="${pageContext.request.contextPath}/stock/get?identifier=${s.identifier}"
                                       class="fw-semibold text-decoration-none">
                                        ${s.identifier}
                                    </a>
                                </td>

                                <td>${s.productIdentifier}</td>
                                <td>${s.warehouseIdentifier}</td>
                                <td>${s.availableQuantity}</td>
                                <td>${s.reorderLevel}</td>

                                <!-- ACTIONS -->
                                <td class="text-center">

                                    <a href="${pageContext.request.contextPath}/stock/get?identifier=${s.identifier}"
                                       class="btn btn-sm btn-outline-primary me-2">
                                        Update
                                    </a>

                                    <a href="${pageContext.request.contextPath}/stock/delete?identifier=${s.identifier}"
                                       class="btn btn-sm btn-outline-danger"
                                       onclick="return confirm('Delete this stock?');">
                                        Delete
                                    </a>

                                </td>

                            </tr>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>

                </tbody>

            </table>
        </div>

    </div>

</div>

</body>
</html>