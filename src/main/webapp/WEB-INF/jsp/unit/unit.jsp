<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="UTF-8">

<title>Update Unit</title>

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

    .form-control,
    .form-select {
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

        <span class="navbar-brand fw-bold">
            Unit Management
        </span>

        <a href="/unit/list"
           class="btn btn-outline-light btn-sm">
            Back
        </a>

    </div>

</nav>

<!-- PAGE -->
<div class="container d-flex justify-content-center align-items-center"
     style="min-height: 100vh;">

    <div class="card shadow p-4"
         style="width: 500px;">

        <!-- TITLE -->
        <h3 class="text-center mb-4 fw-bold">
            Update Unit
        </h3>

        <!-- MESSAGE -->
        <c:if test="${not empty message}">

            <div class="alert alert-danger text-center">
                ${message}
            </div>

        </c:if>

        <!-- FORM -->
        <form action="/unit/update"
              method="post">

            <!-- IDENTIFIER -->
            <div class="mb-3">

                <label class="form-label">
                    Identifier
                </label>

                <input type="text"
                       name="identifier"
                       class="form-control"
                       value="${unitDto.identifier}"
                       readonly>

            </div>

            <!-- UNIT NAME -->
            <div class="mb-3">

                <label class="form-label">
                    Unit Name
                </label>

                <input type="text"
                       name="unitName"
                       class="form-control"
                       value="${unitDto.unitName}"
                       readonly>

            </div>

            <!-- STATUS -->
            <div class="mb-4">

                <label class="form-label fw-semibold">
                    Status
                </label>

                <select name="status"
                        class="form-select">

                    <option value="true"
                        <c:if test="${unitDto.status}">
                            selected
                        </c:if>>
                        Active
                    </option>

                    <option value="false"
                        <c:if test="${!unitDto.status}">
                            selected
                        </c:if>>
                        Inactive
                    </option>

                </select>

            </div>

            <!-- BUTTONS -->
            <div class="d-flex gap-2">

                <button type="submit"
                        class="btn btn-primary w-100">

                    Update

                </button>

                <a href="/unit/list"
                   class="btn btn-outline-secondary w-100">

                    Cancel

                </a>

            </div>

        </form>

    </div>

</div>

</body>

</html>