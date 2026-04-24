<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Edit Product</title>

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
        .form-control {
            border-radius: 10px;
        }
        .btn {
            border-radius: 10px;
        }
    </style>
</head>

<body>

<!-- NAVBAR -->
<nav class="navbar navbar-dark bg-dark shadow">
    <div class="container-fluid">
        <span class="navbar-brand fw-bold">Product Management</span>
        <a href="${pageContext.request.contextPath}/product/list"
           class="btn btn-outline-light btn-sm">Back</a>
    </div>
</nav>

<!-- MAIN -->
<div class="container d-flex justify-content-center align-items-center" style="min-height: 100vh;">
    <div class="card shadow p-4" style="width: 450px;">

        <h3 class="text-center mb-4 fw-bold">Edit Product</h3>

        <!-- MESSAGE -->
        <c:if test="${not empty message}">
            <div class="alert alert-danger text-center">
                ${message}
            </div>
        </c:if>

        <form:form method="post"
                   action="${pageContext.request.contextPath}/product/update"
                   modelAttribute="productDto">

            <!-- IDENTIFIER (hidden for update) -->
            <form:hidden path="identifier"/>

            <!-- IDENTIFIER DISPLAY -->
            <div class="mb-3">
                <label class="form-label fw-semibold">Product ID</label>
                <input type="text"
                       class="form-control"
                       value="${productDto.identifier}"
                       readonly>
            </div>

            <!-- PRODUCT NAME -->
            <div class="mb-3">
                <label class="form-label fw-semibold">Product Name</label>
                <input type="text"
                       class="form-control"
                       value="${productDto.productName}"
                       readonly>
            </div>

            <!-- BRAND -->
            <div class="mb-3">
                <label class="form-label fw-semibold">Brand</label>
                <form:input path="brand" cssClass="form-control"/>
            </div>

            <!-- CATEGORY -->
            <div class="mb-3">
                <label class="form-label fw-semibold">Category</label>
                <form:input path="category" cssClass="form-control"/>
            </div>

            <!-- UNIT -->
            <div class="mb-3">
                <label class="form-label fw-semibold">Unit</label>
                <form:input path="unit" cssClass="form-control"/>
            </div>

            <!-- STATUS -->
            <div class="mb-3">
                <label class="form-label fw-semibold">Status</label>
                <form:select path="status" cssClass="form-control">
                    <form:option value="ACTIVE" label="Active"/>
                    <form:option value="INACTIVE" label="Inactive"/>
                </form:select>
            </div>

            <!-- BUTTONS -->
            <div class="d-flex gap-2">
                <button type="submit" class="btn btn-primary w-100">
                    Update
                </button>

                <a href="${pageContext.request.contextPath}/product/list"
                   class="btn btn-outline-secondary w-100">
                    Cancel
                </a>
            </div>

        </form:form>

    </div>
</div>

</body>
</html>