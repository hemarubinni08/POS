<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Edit Stock</title>

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
        <span class="navbar-brand fw-bold">Stock Management</span>
        <a href="${pageContext.request.contextPath}/stock/list"
           class="btn btn-outline-light btn-sm">Back</a>
    </div>
</nav>

<!-- MAIN -->
<div class="container d-flex justify-content-center align-items-center" style="min-height: 100vh;">

    <div class="card shadow p-4" style="width: 500px;">

        <h3 class="text-center mb-4 fw-bold">Edit Stock</h3>

        <!-- MESSAGE -->
        <c:if test="${not empty message}">
            <div class="alert alert-danger text-center">
                ${message}
            </div>
        </c:if>

        <c:if test="${not empty stockDto}">

            <form:form method="post"
                       action="${pageContext.request.contextPath}/stock/update"
                       modelAttribute="stockDto">

                <!-- HIDDEN IDENTIFIER -->
                <form:hidden path="identifier"/>

                <!-- DISPLAY IDENTIFIER -->
                <div class="mb-3">
                    <label class="form-label fw-semibold">Stock ID</label>
                    <input type="text"
                           class="form-control"
                           value="${stockDto.identifier}"
                           readonly>
                </div>

                <!-- PRODUCT DROPDOWN -->
                <div class="mb-3">
                    <label class="form-label fw-semibold">Product</label>
                    <form:select path="productIdentifier" cssClass="form-control">

                        <c:forEach var="p" items="${products}">
                            <form:option value="${p.identifier}"
                                         label="${p.productName} (${p.identifier})"/>
                        </c:forEach>

                    </form:select>
                </div>

                <!-- WAREHOUSE DROPDOWN -->
                <div class="mb-3">
                    <label class="form-label fw-semibold">Warehouse</label>
                    <form:select path="warehouseIdentifier" cssClass="form-control">

                        <c:forEach var="w" items="${warehouses}">
                            <form:option value="${w.identifier}"
                                         label="${w.warehouseName} (${w.identifier})"/>
                        </c:forEach>

                    </form:select>
                </div>

                <!-- QUANTITY -->
                <div class="mb-3">
                    <label class="form-label fw-semibold">Available Quantity</label>
                    <form:input path="availableQuantity"
                                type="number"
                                cssClass="form-control"/>
                </div>

                <!-- REORDER LEVEL -->
                <div class="mb-4">
                    <label class="form-label fw-semibold">Reorder Level</label>
                    <form:input path="reorderLevel"
                                type="number"
                                cssClass="form-control"/>
                </div>

                <!-- BUTTON -->
                <button type="submit" class="btn btn-primary w-100">
                    Update Stock
                </button>

            </form:form>

        </c:if>

    </div>

</div>

</body>
</html>