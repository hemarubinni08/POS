<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Price List</title>

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
        <span class="navbar-brand fw-bold">Price Management</span>

        <div class="d-flex gap-2">
            <a href="${pageContext.request.contextPath}/"
               class="btn btn-outline-light btn-sm">Home</a>

            <a href="${pageContext.request.contextPath}/price/add"
               class="btn btn-light btn-sm fw-semibold">
                + Add Price
            </a>
        </div>
    </div>
</nav>

<!-- MAIN -->
<div class="container mt-5">

    <div class="card shadow p-3">

        <h3 class="fw-bold mb-3 text-center">Price List</h3>

        <!-- MESSAGE -->
        <c:if test="${not empty message}">
            <div class="alert alert-info text-center">
                ${message}
            </div>
        </c:if>

        <!-- TABLE -->
        <div class="table-responsive">

            <table class="table table-hover table-striped mb-0">

                <thead class="table-dark">
                <tr>
                    <th>Identifier</th>
                    <th>Cost Price</th>
                    <th>MRP</th>
                    <th>Selling Price</th>
                    <th class="text-center">Actions</th>
                </tr>
                </thead>

                <tbody>

                <c:choose>

                    <c:when test="${empty prices}">
                        <tr>
                            <td colspan="5" class="text-center py-4 text-muted">
                                No price records available
                            </td>
                        </tr>
                    </c:when>

                    <c:otherwise>

                        <c:forEach var="p" items="${prices}">
                            <tr>

                                <!-- IDENTIFIER -->
                                <td class="fw-semibold">
                                    ${p.identifier}
                                </td>

                                <td>₹ ${p.costPrice}</td>
                                <td>₹ ${p.mrp}</td>
                                <td>₹ ${p.sellingPrice}</td>

                                <!-- ACTIONS -->
                                <td class="text-center">

                                    <a href="${pageContext.request.contextPath}/price/get?identifier=${p.identifier}"
                                       class="btn btn-sm btn-outline-primary me-2">
                                        Update
                                    </a>

                                    <a href="${pageContext.request.contextPath}/price/delete?identifier=${p.identifier}"
                                       class="btn btn-sm btn-outline-danger"
                                       onclick="return confirm('Are you sure you want to delete this price?');">
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