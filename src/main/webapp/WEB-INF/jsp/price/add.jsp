<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add Price</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
          rel="stylesheet">
</head>

<body>

<nav class="navbar navbar-dark bg-dark">
    <div class="container-fluid">
        <span class="navbar-brand">Price Management</span>
        <a href="${pageContext.request.contextPath}/price/list"
           class="btn btn-light btn-sm">Back</a>
    </div>
</nav>

<div class="container d-flex justify-content-center align-items-center" style="min-height: 90vh;">

    <div class="card p-4 shadow" style="width: 450px;">
        <h3 class="text-center mb-3">Add Price</h3>
        <c:if test="${not empty message}">
            <div class="alert alert-danger">${message}</div>
        </c:if>

        <form:form method="post"
                   action="${pageContext.request.contextPath}/price/add"
                   modelAttribute="priceDto">

            <div class="mb-3">
                <label class="form-label fw-semibold">Product</label>
                <form:select path="productId" cssClass="form-control" required="required">
                    <form:option value="" label="-- Select Product --"/>
                    <c:forEach var="p" items="${products}">
                        <form:option value="${p.identifier}">
                            ${p.productName} (${p.identifier})
                        </form:option>
                    </c:forEach>

                </form:select>
            </div>

            <div class="mb-3">
                <label class="form-label fw-semibold">Cost Price</label>
                <form:input path="costPrice"
                            cssClass="form-control"
                            type="number"
                            step="0.01"
                            required="required"/>
            </div>

            <div class="mb-3">
                <label class="form-label fw-semibold">MRP</label>
                <form:input path="mrp"
                            cssClass="form-control"
                            type="number"
                            step="0.01"
                            required="required"/>
            </div>

            <div class="mb-3">
                <label class="form-label fw-semibold">Selling Price</label>
                <form:input path="sellingPrice"
                            cssClass="form-control"
                            type="number"
                            step="0.01"
                            required="required"/>
            </div>

            <button type="submit" class="btn btn-primary w-100">
                Save
            </button>

        </form:form>

    </div>
</div>

</body>
</html>