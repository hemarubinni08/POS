<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Price Management</title>

    <style>
        :root {
            --bg:#f6fff8;
            --card:#ffffff;
            --text:#1f2937;
            --muted:#6b7280;

            --primary:#28a745;
            --primary-hover:#218838;
            --accent:#ffc107;

            --danger:#dc3545;
            --danger-hover:#c82333;

            --border:#e5e7eb;
            --radius:14px;
            --shadow:0 10px 30px rgba(0,0,0,0.08);
        }

        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Inter', sans-serif;
        }

        body {
            min-height:100vh;
            background:var(--bg);
            padding:40px 16px;
            position:relative;
            color: var(--text);
        }

        .back-arrow {
            position:absolute;
            top:20px;
            left:20px;
            width:42px;
            height:42px;
            border-radius:50%;
            background:var(--card);
            border:1px solid var(--border);
            display:flex;
            align-items:center;
            justify-content:center;
            color:var(--text);
            font-size:18px;
            box-shadow:var(--shadow);
            text-decoration:none;
            transition:.2s;
        }

        .back-arrow:hover {
            background:var(--accent);
            color:black;
        }

        .container {
            max-width:1100px;
            margin:auto;
        }

        .card {
            background:var(--card);
            border-radius:var(--radius);
            box-shadow:var(--shadow);
            overflow:hidden;
        }

        .card-header {
            background:var(--primary);
            color:#fff;
            padding:18px;
            text-align:center;
            font-size:18px;
            font-weight:600;
        }

        .card-body {
            padding:18px;
        }

        table {
            width:100%;
            border-collapse:collapse;
        }

        th, td {
            padding:12px;
            border-bottom:1px solid var(--border);
            text-align:center;
            font-size:13px;
        }

        th {
            background:#f9fafb;
            color:var(--muted);
            text-transform:uppercase;
            font-size:12px;
        }

        tr:hover {
            background:#f9fafb;
        }

        .btn {
            padding:7px 12px;
            border-radius:8px;
            font-size:13px;
            font-weight:600;
            text-decoration:none;
            display:inline-block;
        }

        .btn-secondary {
            background:var(--accent);
            color:black;
        }

        .btn-secondary:hover {
            background:#e0a800;
        }

        .btn-danger {
            background:var(--danger);
            color:white;
        }

        .btn-danger:hover {
            background:var(--danger-hover);
        }

        .btn-success {
            background:var(--primary);
            color:white;
        }

        .btn-success:hover {
            background:var(--primary-hover);
        }

        .card-footer {
            background:#f9fafb;
            padding:16px;
            text-align:center;
            border-top:1px solid var(--border);
        }

        .alert {
            background:#fff3cd;
            color:#856404;
            padding:12px;
            border-radius:8px;
            text-align:center;
            font-size:13px;
        }
    </style>
</head>

<body>

<a href="${pageContext.request.contextPath}/" class="back-arrow">←</a>

<div class="container">
    <div class="card">

        <div class="card-header">
            Price Management
        </div>

        <div class="card-body">

            <c:if test="${empty prices}">
                <div class="alert">No price records found</div>
            </c:if>

            <c:if test="${not empty prices}">
                <table>
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Product</th>
                        <th>Selling Price</th>
                        <th>Cost Price</th>
                        <th>Action</th>
                    </tr>
                    </thead>

                    <tbody>
                    <c:forEach var="price" items="${prices}">
                        <tr>
                            <td>${price.id}</td>
                            <td>${price.productName}</td>
                            <td>${price.sellingPrice}</td>
                            <td>${price.costPrice}</td>
                            <td>
                                <a class="btn btn-secondary"
                                   href="${pageContext.request.contextPath}/price/get?id=${price.id}">
                                    Edit
                                </a>

                                <a class="btn btn-danger"
                                   href="${pageContext.request.contextPath}/price/delete?id=${price.id}">
                                    Delete
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>

        </div>

        <div class="card-footer">
            <a href="${pageContext.request.contextPath}/price/add"
               class="btn btn-success">
                + Add Price
            </a>
        </div>

    </div>
</div>

</body>
</html>