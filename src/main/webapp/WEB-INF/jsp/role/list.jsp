<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Role List</title>

    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">

    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">

    <style>
        body {
            background-color: #f5f6fa;
        }

        .card-custom {
            max-width: 800px;
            margin: auto;
            border-radius: 12px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.1);
        }

        .action-btn {
            min-width: 80px;
        }
    </style>
</head>

<body>

<div class="container mt-5">
    <div class="card card-custom p-4">

        <div class="d-flex justify-content-between align-items-center mb-4">
            <h3 class="mb-0">Role List</h3>

            <div>
                <a href="${pageContext.request.contextPath}/"
                   class="btn btn-outline-secondary me-2">
                    <i class="bi bi-arrow-left-circle"></i>
                    Back to Home
                </a>

                <a href="${pageContext.request.contextPath}/role/add"
                   class="btn btn-success">
                    <i class="bi bi-plus-circle"></i>
                    Add Role
                </a>
            </div>
        </div>

        <table class="table table-hover table-bordered align-middle">
            <thead class="table-dark text-center">
            <tr>
                <th>Role ID</th>
                <th>Role Identifier</th>
                <th>description</th>
                <th>Action</th>

            </tr>
            </thead>

            <tbody>
            <c:forEach var="role" items="${roles}">
                <tr>
                    <td class="text-center">${role.id}</td>
                    <td class="text-center fw-semibold">${role.identifier}</td>
                    <td> ${role.description}</td>
                    <td class="text-center">
                        <a href="${pageContext.request.contextPath}/role/get?identifier=${role.identifier}"
                           class="btn btn-sm btn-warning action-btn me-1">
                            Edit
                        </a>

                        <a href="${pageContext.request.contextPath}/role/delete?identifier=${role.identifier}"
                           class="btn btn-sm btn-danger action-btn"
                           onclick="return confirm('Are you sure you want to delete this role?');">
                            Delete
                        </a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

    </div>
</div>

</body>
</html>