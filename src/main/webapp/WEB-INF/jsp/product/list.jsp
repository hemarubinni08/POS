<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Product Management</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        :root {
            --primary: #2563eb;
            --primary-hover: #1e40af;
            --bg: #f8fafc;
            --glass: rgba(255,255,255,0.75);
            --text: #0f172a;
            --muted: #64748b;
            --border: #e2e8f0;
            --danger: #dc2626;
            --radius: 16px;
            --shadow: 0 20px 40px rgba(2,6,23,0.08);
        }

        * { font-family: 'Inter', sans-serif; box-sizing: border-box; }

        body {
            background: var(--bg);
            padding: 40px 20px;
            color: var(--text);
        }

        .back-arrow {
            position: fixed;
            top: 20px;
            left: 20px;
            width: 42px;
            height: 42px;
            border-radius: 50%;
            background: var(--glass);
            backdrop-filter: blur(10px);
            border: 1px solid var(--border);
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 18px;
            box-shadow: var(--shadow);
            text-decoration: none;
            color: var(--text);
        }

        .container-box {
            max-width: 1200px;
            margin: 60px auto 0;
        }

        .card {
            background: var(--glass);
            backdrop-filter: blur(16px);
            border-radius: var(--radius);
            border: 1px solid var(--border);
            box-shadow: var(--shadow);
            overflow: hidden;
        }

        .card-header {
            padding: 20px 24px;
            font-size: 18px;
            font-weight: 600;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .add-btn {
            padding: 8px 14px;
            border-radius: 10px;
            background: var(--primary);
            color: #fff;
            text-decoration: none;
            font-size: 13px;
            font-weight: 600;
        }

        table { width: 100%; border-collapse: collapse; }

        th, td {
            padding: 14px 16px;
            font-size: 13px;
            text-align: left;
            border-bottom: 1px solid var(--border);
        }

        th {
            font-size: 11px;
            text-transform: uppercase;
            color: var(--muted);
            background: rgba(248,250,252,0.8);
        }

        .price-box {
            font-size: 12px;
            color: var(--muted);
            line-height: 1.4;
        }

        .actions {
            display: flex;
            gap: 8px;
        }

        .btn-edit {
            background: #eef2ff;
            color: var(--primary);
            padding: 6px 12px;
            border-radius: 8px;
            font-size: 12px;
            font-weight: 600;
            text-decoration: none;
        }

        .btn-delete {
            background: #fee2e2;
            color: var(--danger);
            padding: 6px 12px;
            border-radius: 8px;
            font-size: 12px;
            font-weight: 600;
            text-decoration: none;
        }

        .empty-state {
            padding: 40px;
            text-align: center;
            color: var(--muted);
        }
    </style>
</head>

<body>

<a href="${pageContext.request.contextPath}/" class="back-arrow">←</a>

<div class="container-box">
    <div class="card">

        <div class="card-header">
            <span>Product Management</span>
            <a href="${pageContext.request.contextPath}/product/add" class="add-btn">
                + Add Product
            </a>
        </div>

        <c:if test="${empty products}">
            <div class="empty-state">No products found</div>
        </c:if>

        <c:if test="${not empty products}">
            <table>
                <thead>
                <tr>
                    <th>Name</th>
                    <th>SKU</th>
                    <th>Description</th>
                    <th>Categories</th>
                    <th>Prices</th>
                    <th style="width:160px;">Actions</th>
                </tr>
                </thead>

                <tbody>
                <c:forEach var="product" items="${products}">
                    <tr>
                        <td>${product.identifier}</td>
                        <td>${product.sku}</td>
                        <td>${product.description}</td>

                        <td>
                            <c:forEach var="cat" items="${product.categoryNames}">
                                <span class="badge bg-secondary me-1">${cat}</span>
                            </c:forEach>
                        </td>

                        <!-- ✅ FIXED PRICE LOGIC -->
                        <td>
                            <c:set var="hasPrice" value="false" />
                            <c:forEach var="price" items="${prices}">
                                <c:if test="${price.productId == product.id}">
                                    <c:set var="hasPrice" value="true" />
                                    <div class="price-box">
                                        Selling: ₹${price.sellingPrice}<br/>
                                        Cost: ₹${price.costPrice}
                                    </div>
                                </c:if>
                            </c:forEach>

                            <c:if test="${!hasPrice}">
                                <div class="price-box">Price not assigned</div>
                            </c:if>
                        </td>

                        <td class="actions">
                            <a class="btn-edit"
                               href="${pageContext.request.contextPath}/product/get?id=${product.id}">
                                Edit
                            </a>
                            <a class="btn-delete"
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
</div>

</body>
</html>