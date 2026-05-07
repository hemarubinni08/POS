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
        body { background-color: #E9EEF5; min-height: 100vh; }
        .card { border-radius: 16px; }
        .form-control { border-radius: 10px; }
        .btn { border-radius: 10px; }
        .form-check { margin-bottom: 6px; }
        .category-box {
            max-height: 150px;
            overflow-y: auto;
            border: 1px solid #ced4da;
            border-radius: 10px;
            padding: 10px;
            background: #fff;
        }
    </style>
</head>

<body>

<nav class="navbar navbar-dark bg-dark shadow">
    <div class="container-fluid">
        <span class="navbar-brand fw-bold">Product Management</span>
        <a href="${pageContext.request.contextPath}/product/list"
           class="btn btn-outline-light btn-sm">Back</a>
    </div>
</nav>

<div class="container d-flex justify-content-center align-items-center" style="min-height: 100vh;">
    <div class="card shadow p-4" style="width: 500px;">

        <h3 class="text-center mb-4 fw-bold">Edit Product</h3>

        <c:if test="${not empty message}">
            <div class="alert alert-danger text-center">
                ${message}
            </div>
        </c:if>

        <form:form method="post"
                   action="${pageContext.request.contextPath}/product/update"
                   modelAttribute="productDto">

            <!-- IDENTIFIER -->
            <form:hidden path="identifier"/>

            <div class="mb-3">
                <label class="form-label fw-semibold">Product ID</label>
                <input type="text" class="form-control"
                       value="${productDto.identifier}" readonly>
            </div>

            <!-- PRODUCT NAME -->
            <div class="mb-3">
                <label class="form-label fw-semibold">Product Name</label>
                <input type="text" class="form-control"
                       value="${productDto.productName}" readonly>
            </div>

            <!-- BRAND -->
            <div class="mb-4">
                <label class="form-label fw-semibold">Brand</label>

                <div class="category-box">
                    <c:forEach items="${brands}" var="b">
                        <div class="form-check">
                            <form:radiobutton path="brand"
                                              value="${b.identifier}"
                                              cssClass="form-check-input"/>
                            <label class="form-check-label">
                                ${b.brandName}
                            </label>
                        </div>
                    </c:forEach>
                </div>
            </div>

            <!-- MODEL -->
            <div class="mb-4">
                <label class="form-label fw-semibold">Model</label>

                <div class="category-box">
                    <c:forEach items="${models}" var="m">
                        <div class="form-check">
                            <form:radiobutton path="model"
                                              value="${m.identifier}"
                                              cssClass="form-check-input"/>
                            <label class="form-check-label">
                                ${m.modelName}
                            </label>
                        </div>
                    </c:forEach>
                </div>
            </div>

            <!-- CATEGORY -->
            <div class="mb-4">
                <label class="form-label fw-semibold">Category</label>

                <div class="category-box">
                    <c:forEach items="${categories}" var="c">
                        <div class="form-check">
                            <form:checkbox path="categories"
                                           value="${c.identifier}"
                                           cssClass="form-check-input"/>
                            <label class="form-check-label">
                                ${c.name} (${c.identifier})
                            </label>
                        </div>
                    </c:forEach>
                </div>
            </div>

            <!-- UNIT (FIXED - DROPDOWN ACTIVE ONLY) -->
            <div class="mb-3">
                <label class="form-label fw-semibold">Unit</label>

                <form:select path="unit" cssClass="form-control">

                    <form:option value="">-- Select Unit --</form:option>

                    <c:forEach items="${units}" var="u">
                        <c:if test="${u.status}">
                            <option value="${u.identifier}"
                                <c:if test="${productDto.unit == u.identifier}">
                                    selected
                                </c:if>>
                                ${u.unitName}
                            </option>
                        </c:if>
                    </c:forEach>

                </form:select>
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