<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Model Management</title>

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
          rel="stylesheet">

    <style>
        body {
            background: linear-gradient(135deg, #ede9fe, #ddd6fe);
            min-height: 100vh;
        }

        .card {
            border-radius: 15px;
            background-color: #ffffff;
        }

        .table th {
            background-color: #a78bfa;
            color: #ffffff;
        }

        .table-hover tbody tr:hover {
            background-color: #f5f3ff;
        }

        .btn-secondary {
            background-color: #c4b5fd;
            border: none;
            color: #4c1d95;
        }

        .btn-secondary:hover {
            background-color: #b197fc;
            color: #ffffff;
        }

        .btn-success {
            background-color: #7c3aed;
            border: none;
        }

        .btn-success:hover {
            background-color: #6d28d9;
        }

        /* ============================
           STATUS TOGGLE (RED / WHITE)
           ============================ */
        .switch {
            position: relative;
            display: inline-block;
            width: 44px;
            height: 24px;
        }

        .switch input {
            display: none;
        }

        /* OFF state */
        .slider {
            position: absolute;
            inset: 0;
            cursor: pointer;
            background-color: #ffffff;
            border: 2px solid #d1d5db;
            border-radius: 999px;
            transition: 0.25s;
        }

        .slider::before {
            content: "";
            position: absolute;
            height: 18px;
            width: 18px;
            left: 2px;
            top: 2px;
            background-color: #9ca3af;
            border-radius: 50%;
            transition: 0.25s;
        }

        /* ON state */
        .switch input:checked + .slider {
            background-color: #dc2626;
            border-color: #dc2626;
        }

        .switch input:checked + .slider::before {
            transform: translateX(20px);
            background-color: #ffffff;
        }
    </style>
</head>

<body>

<div class="container mt-5">
    <div class="card shadow-lg">

        <div class="card-header text-center">
            <h4 class="mb-0">List of Models</h4>
        </div>

        <div class="card-body">

            <c:choose>
                <c:when test="${empty models}">
                    <div class="alert alert-warning text-center">
                        No models available
                    </div>
                </c:when>

                <c:otherwise>
                    <div class="table-responsive">
                        <table class="table table-bordered table-hover align-middle text-center">
                            <thead>
                            <tr>
                                <th>Model Name</th>
                                <th>Status</th>
                                <th>Actions</th>
                            </tr>
                            </thead>

                            <tbody>
                            <c:forEach var="model" items="${models}">
                                <tr>
                                    <td>${model.identifier}</td>

                                    <!-- ✅ RED / WHITE TOGGLE -->
                                    <td>
                                        <label class="switch">
                                            <input type="checkbox"
                                                   <c:if test="${model.status}">checked</c:if>
                                                   onclick="toggleStatus('${model.identifier}', this)">
                                            <span class="slider"></span>
                                        </label>
                                    </td>

                                    <td class="d-flex justify-content-center gap-2">
                                        <a href="${pageContext.request.contextPath}/models/get?identifier=${model.identifier}"
                                           class="btn btn-sm btn-primary">
                                            Edit
                                        </a>

                                        <a href="${pageContext.request.contextPath}/models/delete?identifier=${model.identifier}"
                                           class="btn btn-sm btn-danger">
                                            Delete
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:otherwise>
            </c:choose>

        </div>

        <div class="card-footer text-center">
            <div class="d-flex justify-content-center gap-3">
                <a href="/" class="btn btn-secondary">Home</a>
                <a href="${pageContext.request.contextPath}/models/add"
                   class="btn btn-success">
                    Add Model
                </a>
            </div>

            <div class="text-muted small mt-2">
                Model Management System
            </div>
        </div>

    </div>
</div>

<!-- ✅ LOGIC UNCHANGED -->
<script>
function toggleStatus(identifier, checkbox) {
    fetch('${pageContext.request.contextPath}/models/toggle-status', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: 'identifier=' + identifier + '&status=' + checkbox.checked
    }).catch(() => {
        checkbox.checked = !checkbox.checked;
        alert('Status update failed');
    });
}
</script>

</body>
</html>