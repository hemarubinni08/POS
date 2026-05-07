<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add Rack</title>

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

<!-- NAVBAR -->
<nav class="navbar navbar-dark bg-dark shadow">
    <div class="container-fluid">
        <span class="navbar-brand fw-bold">Rack Management</span>
        <a href="${pageContext.request.contextPath}/rack/list"
           class="btn btn-outline-light btn-sm">Back</a>
    </div>
</nav>

<!-- MAIN -->
<div class="container d-flex justify-content-center align-items-center" style="min-height: 100vh;">

    <div class="card shadow p-4" style="width: 450px;">

        <h3 class="text-center mb-4 fw-bold">Add Rack</h3>

        <!-- MESSAGE -->
        <c:if test="${not empty message}">
            <div class="alert alert-danger text-center">
                ${message}
            </div>
        </c:if>

        <!-- FORM -->
        <form:form method="post"
                   action="${pageContext.request.contextPath}/rack/add"
                   modelAttribute="rackDto">

            <!-- NAME -->
            <div class="mb-3">
                <label class="form-label fw-semibold">Rack Name</label>
                <form:input path="name"
                            cssClass="form-control"
                            placeholder="Enter rack name"
                            required="true"/>
            </div>

            <!-- SHELVES -->
            <div class="mb-3">
                <label class="form-label fw-semibold">Select Shelves</label>

                <div class="border rounded p-3 bg-light">

                    <c:forEach var="s" items="${shelves}">
                        <div class="form-check">

                            <input class="form-check-input"
                                   type="checkbox"
                                   name="shelfIdentifiers"
                                   value="${s.identifier}"
                                   id="shelf_${s.identifier}" />

                            <label class="form-check-label" for="shelf_${s.identifier}">
                                ${s.name}
                            </label>

                        </div>
                    </c:forEach>

                </div>
            </div>

            <!-- STATUS -->
            <div class="mb-3">
                <label class="form-label fw-semibold">Status</label>

                <form:select path="status" cssClass="form-select">
                    <form:option value="true">Active</form:option>
                    <form:option value="false">Inactive</form:option>
                </form:select>
            </div>

            <!-- BUTTONS -->
            <div class="d-flex gap-2">

                <button type="submit" class="btn btn-primary w-100">
                    Save Rack
                </button>

                <a href="${pageContext.request.contextPath}/rack/list"
                   class="btn btn-outline-secondary w-100">
                    Cancel
                </a>

            </div>

        </form:form>

    </div>

</div>

</body>
</html>