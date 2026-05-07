<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Edit Rack</title>

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
           class="btn btn-outline-light btn-sm">
            Back
        </a>

    </div>
</nav>

<!-- MAIN -->
<div class="container d-flex justify-content-center align-items-center" style="min-height: 100vh;">

    <div class="card shadow p-4" style="width: 450px;">

        <h3 class="text-center mb-4 fw-bold">Edit Rack</h3>

        <!-- MESSAGE -->
        <c:if test="${not empty message}">
            <div class="alert alert-danger text-center">
                ${message}
            </div>
        </c:if>

        <form action="${pageContext.request.contextPath}/rack/update" method="post">

            <!-- IDENTIFIER -->
            <input type="hidden" name="identifier" value="${rackDto.identifier}" />

            <!-- NAME -->
            <div class="mb-3">
                <label class="form-label fw-semibold">Rack Name</label>
                <input type="text"
                       class="form-control"
                       name="name"
                       value="${rackDto.name}"
                       required />
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
                                   id="shelf_${s.identifier}"

                                   <c:if test="${rackDto.shelfIdentifiers != null}">
                                       <c:forEach var="sel" items="${rackDto.shelfIdentifiers}">
                                           <c:if test="${sel == s.identifier}">
                                               checked
                                           </c:if>
                                       </c:forEach>
                                   </c:if>
                            />

                            <label class="form-check-label" for="shelf_${s.identifier}">
                                ${s.name}
                            </label>

                        </div>

                    </c:forEach>

                </div>
            </div>

            <!-- STATUS -->
            <div class="mb-4">
                <label class="form-label fw-semibold">Status</label>

                <select name="status" class="form-select">

                    <option value="true"
                        <c:if test="${rackDto.status}">selected</c:if>>
                        Active
                    </option>

                    <option value="false"
                        <c:if test="${!rackDto.status}">selected</c:if>>
                        Inactive
                    </option>

                </select>
            </div>

            <!-- BUTTONS -->
            <div class="d-flex gap-2">

                <button type="submit" class="btn btn-primary w-100">
                    Update
                </button>

                <a href="${pageContext.request.contextPath}/rack/list"
                   class="btn btn-outline-secondary w-100">
                    Cancel
                </a>

            </div>

        </form>

    </div>

</div>

</body>
</html>