<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Product Management</title>

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
        }

        /* Back button */
        .back-arrow {
            position: fixed;
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
            z-index: 1000;
        }

        .back-arrow:hover {
            background: var(--accent);
            color: black;
        }

        .container {
            max-width: 1200px;
            margin: 80px auto 0 auto; /* prevents overlap */
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
            color: white;
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
            vertical-align: top;
        }

        th {
            font-size: 12px;
            text-transform: uppercase;
            color: var(--muted);
            background: #f9fafb;
        }

        tr:hover {
            background: #f9fafb;
        }

        .price-box {
            font-size: 12px;
            color: var(--muted);
            margin-bottom: 4px;
        }

        .btn {
            padding: 6px 10px;
            border-radius: 8px;
            font-size: 12px;
            font-weight: 600;
            text-decoration: none;
            display: inline-block;
            margin: 2px;
        }

        .btn-success {
            background: var(--primary);
            color: white;
        }

        .btn-secondary {
            background: var(--accent);
            color: black;
        }

        .btn-danger {
            background: var(--danger);
            color: white;
        }

        .card-footer {
            padding: 16px;
            text-align: center;
            background: #f9fafb;
            border-top: 1px solid var(--border);
        }

        .alert {
            background: #fff3cd;
            color: #856404;
            padding: 10px 12px;
            border-radius: 8px;
            font-size: 13px;
            text-align: center;
        }
    </style>
</head>

<body>

<a href="${pageContext.request.contextPath}/" class="back-arrow">←</a>

<div class="container">

    <div class="card">

        <div class="card-header">
            Product Management (With Prices)
        </div>

        <div class="card-body">

            <c:if test="${empty products}">
                <div class="alert">No products found</div>
            </c:if>

            <c:if test="${not empty products}">
                <table>
                    <thead>
                    <tr>
                        <th>Name</th>
                        <th>SKU</th>
                        <th>Description</th>
                        <th>Status</th>
                        <th>Prices</th>
                        <th>Action</th>
                    </tr>
                    </thead>

                    <tbody>
                    <c:forEach var="product" items="${products}">
                        <tr>
                            <td>${product.name}</td>
                            <td>${product.sku}</td>
                            <td>${product.description}</td>
                            <td>${product.status}</td>

                            <!-- Prices from Price Management -->
                            <td>
                                <c:set var="hasPrice" value="false"/>
                                <c:forEach var="price" items="${prices}">
                                    <c:if test="${price.productId == product.id}">
                                        <c:set var="hasPrice" value="true"/>
                                        <div class="price-box">
                                            Sell: ₹${price.sellingPrice} |
                                            Cost: ₹${price.costPrice}
                                        </div>
                                    </c:if>
                                </c:forEach>

                                <c:if test="${!hasPrice}">
                                    <div class="price-box">No price configured</div>
                                </c:if>
                            </td>

                            <td>
                                <a class="btn btn-secondary"
                                   href="${pageContext.request.contextPath}/product/get?id=${product.id}">
                                    Edit
                                </a>
                                <a class="btn btn-danger"
                                   href="${pageContext.request.contextPath}/product/delete?id=${product.id}">
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
            <a href="${pageContext.request.contextPath}/product/add"
               class="btn btn-success">
                + Add Product
            </a>
        </div>

    </div>

</div>

</body>
</html>