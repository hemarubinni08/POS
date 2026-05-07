<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Edit Shelf</title>

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
        .form-control, .form-select {
            border-radius: 10px;
        }
        .btn {
            border-radius: 10px;
        }
    </style>
</head>

<body>

<nav class="navbar navbar-dark bg-dark shadow">
    <div class="container-fluid">

        <span class="navbar-brand fw-bold">Shelf Management</span>

        <a href="${pageContext.request.contextPath}/shelf/list"
           class="btn btn-outline-light btn-sm">
            Back
        </a>

    </div>
</nav>

<div class="container d-flex justify-content-center align-items-center" style="min-height: 100vh;">

    <div class="card shadow p-4" style="width: 450px;">

        <h3 class="text-center mb-4 fw-bold">Edit Shelf</h3>

        <!-- MESSAGE -->
        <c:if test="${not empty message}">
            <div class="alert alert-danger text-center">
                ${message}
            </div>
        </c:if>

        <!-- FORM -->
        <form:form method="post"
                   action="${pageContext.request.contextPath}/shelf/update"
                   modelAttribute="shelfDto">

            <!-- IDENTIFIER -->
            <form:hidden path="identifier"/>

            <!-- SHOW IDENTIFIER -->
            <div class="mb-3">
                <label class="form-label fw-semibold">Shelf ID</label>
                <input type="text"
                       class="form-control"
                       value="${shelfDto.identifier}"
                       readonly />
            </div>

            <!-- NAME (DISPLAY ONLY) -->
            <div class="mb-3">
                <label class="form-label fw-semibold">Shelf Name</label>
                <input type="text"
                       class="form-control"
                       value="${shelfDto.name}"
                       readonly />
            </div>

            <!-- STATUS -->
            <div class="mb-4">
                <label class="form-label fw-semibold">Status</label>

                <form:select path="status" cssClass="form-select">
                    <form:option value="true">Active</form:option>
                    <form:option value="false">Inactive</form:option>
                </form:select>
            </div>

            <!-- BUTTONS -->
            <div class="d-flex gap-2">

                <button type="submit" class="btn btn-primary w-100">
                    Update
                </button>

                <a href="${pageContext.request.contextPath}/shelf/list"
                   class="btn btn-outline-secondary w-100">
                    Cancel
                </a>

            </div>

        </form:form>

    </div>

</div>

</body>
</html>