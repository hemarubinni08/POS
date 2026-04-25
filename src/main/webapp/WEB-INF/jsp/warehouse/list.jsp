<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Warehouse List</title>


    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
          rel="stylesheet">


    <style>
        body {
            background: linear-gradient(135deg, #ede9fe, #ddd6fe);
            min-height: 100vh;
        }

        .card {
            border-radius: 16px;
            background-color: #ffffff;
            box-shadow: 0 20px 40px rgba(76, 29, 149, 0.18);
        }

        .card-header {
            background-color: #a78bfa;
            color: #ffffff;
        }

        table th {
            background-color: #a78bfa;
            color: white;
        }

        table.table-hover tbody tr:hover {
            background-color: #f5f3ff;
        }

        .btn-success {
            background-color: #7c3aed;
            border: none;
        }

        .btn-success:hover {
            background-color: #6d28d9;
        }

        .btn-primary {
            background-color: #7c3aed;
            border: none;
        }

        .btn-primary:hover {
            background-color: #6d28d9;
        }

        .btn-danger {
            border: none;
        }
    </style>
</head>

<body>

<div class="container mt-5">

    <div class="card">

        <div class="card-header text-center">
            <h4 class="mb-0">List of Warehouses</h4>
        </div>

        <div class="card-body">

            <c:if test="${empty warehouses}">
                <div class="alert alert-warning text-center">
                    No warehouses available
                </div>
            </c:if>

            <c:if test="${not empty warehouses}">
                <table class="table table-bordered table-hover text-center align-middle">
                    <thead>
                    <tr>
                        <th>Identifier</th>
                        <th>Location</th>
                        <th>Manager</th>
                        <th>Action</th>
                    </tr>
                    </thead>

                    <tbody>
                    <c:forEach var="warehouse" items="${warehouses}">
                        <tr>
                            <td>${warehouse.identifier}</td>
                            <td>${warehouse.location}</td>
                            <td>${warehouse.manager}</td>
                            <td class="d-flex justify-content-center gap-2">
                                <a href="${pageContext.request.contextPath}/warehouse/get?identifier=${warehouse.identifier}"
                                   class="btn btn-sm btn-primary">Edit</a>

                                <a href="${pageContext.request.contextPath}/warehouse/delete?identifier=${warehouse.identifier}"
                                   class="btn btn-sm btn-danger">Delete</a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>

        </div>


        <div class="card-footer text-center bg-light d-flex justify-content-center gap-3">

            <a href="${pageContext.request.contextPath}/"
               class="btn btn-secondary">
                Home
            </a>

            <a href="${pageContext.request.contextPath}/warehouse/add"
               class="btn btn-success">
                + Add Warehouse
            </a>

        </div>

    </div>

</div>

</body>
</html>