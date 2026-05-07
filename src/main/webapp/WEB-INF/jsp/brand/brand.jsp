<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Update Brand</title>

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

        <span class="navbar-brand fw-bold">
            Brand Management
        </span>

        <a href="/brand/list"
           class="btn btn-outline-light btn-sm">
            Back
        </a>

    </div>
</nav>

<div class="container d-flex justify-content-center align-items-center"
     style="min-height: 100vh;">

    <div class="card shadow p-4" style="width: 500px;">

        <h3 class="text-center mb-4 fw-bold">
            Update Brand
        </h3>

        <!-- MESSAGE -->
        <c:if test="${not empty message}">
            <div class="alert alert-danger text-center">
                ${message}
            </div>
        </c:if>

        <form action="/brand/update" method="post">

            <!-- IDENTIFIER -->
            <div class="mb-3">
                <label class="form-label">Identifier</label>

                <input type="text"
                       name="identifier"
                       class="form-control"
                       value="${brandDto.identifier}"
                       readonly>
            </div>

            <!-- BRAND NAME -->
            <div class="mb-3">
                <label class="form-label">Brand Name</label>

                <input type="text"
                       name="brandName"
                       class="form-control"
                       value="${brandDto.brandName}"
                       readonly>
            </div>

            <!-- DESCRIPTION -->
            <div class="mb-3">
                <label class="form-label">Description</label>

                <input type="text"
                       name="description"
                       class="form-control"
                       value="${brandDto.description}"
                       required>
            </div>

            <!-- STATUS -->
            <div class="mb-4">

                <label class="form-label">Status</label>

                <div class="form-check form-switch">

                    <input class="form-check-input"
                           type="checkbox"
                           id="statusToggle"

                           <c:if test="${brandDto.status}">
                               checked
                           </c:if>

                           onchange="updateStatusValue()" />

                    <input type="hidden"
                           id="statusHidden"
                           name="status"
                           value="${brandDto.status}" />

                    <label class="form-check-label ms-2"
                           id="statusLabel">

                        <c:choose>
                            <c:when test="${brandDto.status}">
                                Active
                            </c:when>

                            <c:otherwise>
                                Inactive
                            </c:otherwise>
                        </c:choose>

                    </label>

                </div>

            </div>

            <!-- BUTTONS -->
            <div class="d-flex gap-2">

                <button type="submit"
                        class="btn btn-primary w-100">
                    Update
                </button>

                <a href="/brand/list"
                   class="btn btn-outline-secondary w-100">
                    Cancel
                </a>

            </div>

        </form>

    </div>

</div>

<script>

    function updateStatusValue() {

        const toggle = document.getElementById("statusToggle");
        const hidden = document.getElementById("statusHidden");
        const label = document.getElementById("statusLabel");

        hidden.value = toggle.checked;

        if (toggle.checked) {
            label.innerText = "Active";
        } else {
            label.innerText = "Inactive";
        }
    }

</script>

</body>
</html>