<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Price List</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
          rel="stylesheet">

    <!-- ✅ SAME CSS STYLE AS STOCK / PRODUCT LIST -->
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

        .btn-secondary {
            background-color: #b197fc;
            border: none;
            color: #ffffff;
        }

        .btn-secondary:hover {
            background-color: #a78bfa;
        }

        .btn-success {
            background-color: #7c3aed;
            border: none;
        }

        .btn-success:hover {
            background-color: #6d28d9;
        }
    </style>
</head>

<body>

<div class="container mt-5">

    <div class="card shadow-lg">

        <div class="card-header text-center">
            <h4 class="mb-0">List of Prices</h4>
        </div>

        <div class="card-body">

            <c:if test="${empty prices}">
                <div class="alert alert-warning text-center">
                    No price information available
                </div>
            </c:if>

            <c:if test="${not empty prices}">
                <table class="table table-bordered table-hover text-center align-middle">

                    <thead>
                    <tr>
                        <th>Product</th>
                        <th>MRP</th>
                        <th>Selling Price</th>
                        <th>Effective From</th>
                        <th>Action</th>
                    </tr>
                    </thead>

                    <tbody>
                    <c:forEach var="price" items="${prices}">
                        <tr>
                            <td>${price.identifier}</td>
                            <td>${price.mrp}</td>
                            <td>${price.sellingPrice}</td>
                            <td>${price.effectiveFrom}</td>

                            <td class="d-flex justify-content-center gap-2">
                                <a href="${pageContext.request.contextPath}/price/get?identifier=${price.identifier}"
                                   class="btn btn-primary btn-sm">
                                    Edit
                                </a>

                                <a href="${pageContext.request.contextPath}/price/delete?identifier=${price.identifier}"
                                   class="btn btn-danger btn-sm">
                                    Delete
                                </a>
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

            <a href="${pageContext.request.contextPath}/price/add"
               class="btn btn-success">
                + Add Price
            </a>

        </div>

    </div>

</div>

</body>
</html>