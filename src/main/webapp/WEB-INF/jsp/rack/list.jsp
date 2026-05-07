<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Rack List</title>

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
            font-size: 12px;
        }

        .form-switch .form-check-input {
            width: 45px;
            height: 22px;
            cursor: pointer;
        }
    </style>
</head>

<body>

<!-- NAVBAR -->
<nav class="navbar navbar-dark bg-dark shadow">
    <div class="container-fluid">

        <span class="navbar-brand fw-bold">Rack Management</span>

        <div class="d-flex gap-2">

            <a href="${pageContext.request.contextPath}/"
               class="btn btn-outline-light btn-sm">
                Home
            </a>

            <a href="${pageContext.request.contextPath}/rack/add"
               class="btn btn-light btn-sm fw-semibold">
                + Add Rack
            </a>

        </div>

    </div>
</nav>

<!-- MAIN -->
<div class="container mt-5">

    <div class="card shadow p-4">

        <h4 class="text-center fw-bold mb-4">Rack List</h4>

        <!-- EMPTY -->
        <c:if test="${empty racks}">
            <div class="text-center text-muted py-4">
                No racks available
            </div>
        </c:if>

        <!-- TABLE -->
        <c:if test="${not empty racks}">

            <div class="table-responsive">

                <table class="table table-hover table-striped align-middle">

                    <thead class="table-dark">
                    <tr>
                        <th>Rack ID</th>
                        <th>Name</th>
                        <th>Shelves</th>
                        <th class="text-center">Status</th>
                        <th class="text-center">Actions</th>
                    </tr>
                    </thead>

                    <tbody>

                    <c:forEach var="r" items="${racks}">
                        <tr>

                            <!-- ID -->
                            <td class="fw-semibold">${r.identifier}</td>

                            <!-- NAME -->
                            <td>${r.name}</td>

                            <!-- SHELVES -->
                            <td>
                                <c:choose>
                                    <c:when test="${empty r.shelfIdentifiers}">
                                        <span class="text-muted">No shelves</span>
                                    </c:when>

                                    <c:otherwise>
                                        <c:forEach var="s" items="${r.shelfIdentifiers}">
                                            <span class="badge bg-secondary me-1">
                                                ${s}
                                            </span>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>
                            </td>

                            <!-- STATUS -->
                            <td class="text-center">

                                <div class="form-check form-switch d-flex justify-content-center">

                                    <input class="form-check-input"
                                           type="checkbox"
                                           onclick="toggleRack('${r.identifier}')"
                                           <c:if test="${r.status}">checked</c:if> />

                                </div>

                            </td>

                            <!-- ACTIONS -->
                            <td class="text-center">

                                <a href="${pageContext.request.contextPath}/rack/edit?identifier=${r.identifier}"
                                   class="btn btn-sm btn-outline-primary me-2">
                                    Edit
                                </a>

                                <a href="${pageContext.request.contextPath}/rack/delete?identifier=${r.identifier}"
                                   class="btn btn-sm btn-outline-danger"
                                   onclick="return confirm('Delete this rack?');">
                                    Delete
                                </a>

                            </td>

                        </tr>
                    </c:forEach>

                    </tbody>

                </table>

            </div>

        </c:if>

    </div>

</div>

<script>
    function toggleRack(identifier) {
        window.location.href =
            "${pageContext.request.contextPath}/rack/toggle?identifier=" + identifier;
    }
</script>

</body>
</html>