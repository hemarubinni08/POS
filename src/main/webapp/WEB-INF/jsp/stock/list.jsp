<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Stock Management</title>

    <style>
        :root {
            --bg: #f6fff8;
            --card: #ffffff;
            --text: #1f2937;
            --muted: #6b7280;

            --primary: #28a745;
            --primary-hover: #218838;
            --accent: #ffc107;

            --danger: #dc3545;
            --danger-hover: #c82333;

            --border: #e5e7eb;
            --radius: 14px;
            --shadow: 0 10px 30px rgba(0,0,0,0.08);
        }

        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Inter', sans-serif;
        }

        body {
            min-height: 100vh;
            padding: 40px 16px;
            background: var(--bg);
            color: var(--text);
            position: relative;
        }

        /* ✅ Back button – MATCHES Edit Stock page */
        .back-arrow {
            position: absolute;
            top: 20px;
            left: 20px;
            background: var(--card);
            border: 1px solid var(--border);
            border-radius: 50%;
            width: 42px;
            height: 42px;
            display: flex;
            align-items: center;
            justify-content: center;
            text-decoration: none;
            color: var(--text);
            font-size: 18px;
            box-shadow: var(--shadow);
            transition: 0.2s;
        }

        .back-arrow:hover {
            background: var(--accent);
            color: black;
        }

        .container {
            max-width: 1100px;
            margin: auto;
        }

        .card {
            background: var(--card);
            border-radius: var(--radius);
            box-shadow: var(--shadow);
            overflow: hidden;
        }

        .card-header {
            padding: 18px;
            text-align: center;
            font-size: 18px;
            font-weight: 600;
            color: #fff;
            background: var(--primary);
        }

        .card-body {
            padding: 18px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        th, td {
            padding: 12px;
            text-align: center;
            border-bottom: 1px solid var(--border);
            font-size: 13px;
        }

        th {
            text-transform: uppercase;
            color: var(--muted);
            background: #f9fafb;
        }

        tr:hover {
            background: #f9fafb;
        }

        .btn {
            padding: 7px 10px;
            border-radius: 8px;
            font-size: 13px;
            font-weight: 600;
            text-decoration: none;
            display: inline-block;
        }

        .btn-success {
            background: var(--primary);
            color: white;
        }

        .btn-success:hover {
            background: var(--primary-hover);
        }

        .btn-secondary {
            background: var(--accent);
            color: black;
        }

        .btn-secondary:hover {
            background: #e0a800;
        }

        .btn-danger {
            background: var(--danger);
            color: white;
        }

        .btn-danger:hover {
            background: var(--danger-hover);
        }

        .card-footer {
            padding: 16px;
            background: #f9fafb;
            border-top: 1px solid var(--border);
            text-align: center;
        }

        .alert {
            background: #fff3cd;
            padding: 12px;
            border-radius: 8px;
            color: #856404;
            text-align: center;
        }
    </style>
</head>

<body>

<a href="${pageContext.request.contextPath}/" class="back-arrow">←</a>

<div class="container">
    <div class="card">

        <div class="card-header">
            Stock Management
        </div>

        <div class="card-body">

            <c:if test="${empty stocks}">
                <div class="alert">No stock records found</div>
            </c:if>

            <c:if test="${not empty stocks}">
                <table>
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Product</th>
                        <th>Warehouse</th>
                        <th>Quantity</th>
                        <th>Action</th>
                    </tr>
                    </thead>

                    <tbody>
                    <c:forEach var="stock" items="${stocks}">
                        <tr>
                            <td>${stock.id}</td>
                            <td>${stock.productName}</td>
                            <td>${stock.warehouseName}</td>
                            <td>${stock.quantity}</td>
                            <td>

                                <a class="btn btn-secondary"
                                   href="${pageContext.request.contextPath}/stock/get?productId=${stock.productId}&warehouseId=${stock.warehouseId}">
                                    Edit
                                </a>

                                <a class="btn btn-danger"
                                   href="${pageContext.request.contextPath}/stock/delete?id=${stock.id}">
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
            <a href="${pageContext.request.contextPath}/stock/add" class="btn btn-success">
                + Add Stock
            </a>
        </div>
    </div>
</div>

</body>
</html>