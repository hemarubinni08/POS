<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Product List</title>

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

        .badge {
            border-radius: 8px;
            margin-right: 4px;
        }

        .form-switch .form-check-input {
            width: 45px;
            height: 22px;
            cursor: pointer;
        }
    </style>
</head>

<body>

<nav class="navbar navbar-dark bg-dark shadow">
    <div class="container-fluid">
        <span class="navbar-brand fw-bold">Product Management</span>

        <div class="d-flex gap-2">
            <a href="${pageContext.request.contextPath}/"
               class="btn btn-outline-light btn-sm">Home</a>

            <a href="${pageContext.request.contextPath}/product/add"
               class="btn btn-light btn-sm fw-semibold">
                + Add Product
            </a>
        </div>
    </div>
</nav>

<div class="container mt-5">

    <div class="card shadow p-3">

        <h3 class="fw-bold mb-3 text-center">Product List</h3>

        <c:if test="${not empty message}">
            <div class="alert alert-info text-center">
                ${message}
            </div>
        </c:if>

        <div class="table-responsive">
            <table class="table table-hover table-striped mb-0">

                <thead class="table-dark">
                <tr>
                    <th>Identifier</th>
                    <th>Product Name</th>
                    <th>Brand</th>
                    <th>Model</th>
                    <th>Category</th>
                    <th>Unit</th>
                    <th>Status</th>
                    <th class="text-center">Actions</th>
                </tr>
                </thead>

                <tbody>

                <c:if test="${empty products}">
                    <tr>
                        <td colspan="8" class="text-center py-4 text-muted">
                            No products available
                        </td>
                    </tr>
                </c:if>

                <c:forEach var="p" items="${products}">
                    <tr>

                        <!-- IDENTIFIER -->
                        <td class="fw-semibold">
                            <a href="${pageContext.request.contextPath}/product/get?identifier=${p.identifier}"
                               class="text-decoration-none">
                                ${p.identifier}
                            </a>
                        </td>

                        <!-- PRODUCT NAME -->
                        <td>${p.productName}</td>

                        <!-- BRAND -->
                        <td>${p.brand}</td>

                        <!-- MODEL -->
                        <td>${p.model}</td>

                        <!-- CATEGORY -->
                        <td>
                            <c:if test="${empty p.categories}">
                                <span class="text-muted">-</span>
                            </c:if>

                            <c:forEach var="cat" items="${p.categories}">
                                <span class="badge bg-secondary">
                                    ${cat}
                                </span>
                            </c:forEach>
                        </td>

                        <!-- UNIT -->
                        <td>${p.unit}</td>

                        <!-- STATUS TOGGLE (FIXED - MODELS STYLE) -->
                        <td>
                            <div class="form-check form-switch d-flex justify-content-center">

                                <input class="form-check-input"
                                       type="checkbox"
                                       onclick="toggleProduct('${p.identifier}')"
                                       <c:if test="${p.status == 'ACTIVE'}">checked</c:if> />

                            </div>
                        </td>

                        <!-- ACTIONS -->
                        <td class="text-center">

                            <a href="${pageContext.request.contextPath}/product/get?identifier=${p.identifier}"
                               class="btn btn-sm btn-outline-primary me-2">
                                Update
                            </a>

                            <a href="${pageContext.request.contextPath}/product/delete?identifier=${p.identifier}"
                               class="btn btn-sm btn-outline-danger"
                               onclick="return confirm('Are you sure you want to delete this product?');">
                                Delete
                            </a>

                        </td>

                    </tr>
                </c:forEach>

                </tbody>

            </table>
        </div>

    </div>

</div>

<!-- TOGGLE SCRIPT -->
<script>
    function toggleProduct(identifier) {
        window.location.href =
            "${pageContext.request.contextPath}/product/toggle?identifier=" + identifier;
    }
</script>

</body>
</html>