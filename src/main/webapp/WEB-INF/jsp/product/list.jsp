<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Product Management</title>

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Font -->
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600&display=swap" rel="stylesheet">

    <style>
        :root {
            --primary: #2563eb;
            --bg: #f8fafc;
            --glass: rgba(255,255,255,0.75);
            --text: #0f172a;
            --muted: #64748b;
            --border: #e2e8f0;
            --danger: #dc2626;
            --success: #16a34a;
            --radius: 16px;
            --shadow: 0 20px 40px rgba(2,6,23,0.08);
        }

        * { font-family: 'Inter', sans-serif; box-sizing: border-box; }

        body {
            background: var(--bg);
            min-height: 100vh;
            padding: 40px 20px;
        }

        .back-arrow {
                    position: fixed;
                    top: 20px;
                    left: 20px;

                    width: 42px;
                    height: 42px;

                    display: flex;
                    align-items: center;
                    justify-content: center;

                    border-radius: 50%;
                    background: var(--glass);
                    backdrop-filter: blur(10px);

                    border: 1px solid var(--border);
                    color: var(--text);

                    text-decoration: none;
                    font-size: 18px;

                    box-shadow: var(--shadow);
                    transition:
                        transform 0.2s ease,
                        box-shadow 0.2s ease,
                        background 0.2s ease,
                        color 0.2s ease;
                }

                .back-arrow:hover {
                    background: #eef2ff;
                    color: var(--primary);
                    transform: translateY(-2px) translateX(-2px);
                    box-shadow: 0 12px 28px rgba(37,99,235,0.25);
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
            display: flex;
            justify-content: space-between;
            align-items: center;
            font-weight: 600;
            font-size: 18px;
        }

        .add-btn {
            padding: 8px 14px;
            border-radius: 10px;
            background: var(--primary);
            color: white;
            text-decoration: none;
            font-size: 13px;
            font-weight: 600;
        }

        table { width: 100%; border-collapse: collapse; }

        th, td {
            padding: 14px 16px;
            border-bottom: 1px solid var(--border);
            font-size: 13px;
        }

        th {
            text-transform: uppercase;
            font-size: 11px;
            color: var(--muted);
            background: rgba(248,250,252,.9);
        }

        .toggle-switch {
            width: 46px;
            height: 24px;
            border-radius: 999px;
            background: #cbd5f5;
            position: relative;
            cursor: pointer;
        }

        .toggle-switch::after {
            content: "";
            position: absolute;
            top: 3px;
            left: 4px;
            width: 18px;
            height: 18px;
            background: #fff;
            border-radius: 50%;
            transition: 0.25s;
        }

        .toggle-switch.active {
            background: var(--success);
        }

        .toggle-switch.active::after {
            transform: translateX(20px);
        }

        .actions { display: flex; gap: 8px; }

        .btn-action {
            padding: 6px 12px;
            border-radius: 8px;
            font-size: 12px;
            font-weight: 600;
            text-decoration: none;
        }

        .btn-edit { background: #eef2ff; color: var(--primary); }
        .btn-delete { background: #fee2e2; color: var(--danger); }

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
            <a href="${pageContext.request.contextPath}/product/add" class="add-btn">+ Add Product</a>
        </div>

        <c:if test="${empty products}">
            <div class="empty-state">No products found</div>
        </c:if>

        <c:if test="${not empty products}">
            <table>
                <thead>
                <tr>
                    <th>Product</th>
                    <th>Category</th>
                    <th>Brand</th>
                    <th>Model</th>
                    <th>Unit</th>
                    <th>SKU</th>
                    <th>Status</th>
                    <th style="width:180px;">Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="product" items="${products}">
                    <tr>
                        <td><strong>${product.identifier}</strong></td>
                        <td>${product.category}</td>
                        <td>${product.brand}</td>
                        <td>${product.model}</td>
                        <td>${product.unit}</td>
                        <td>${product.skuCode}</td>
                        <td>
                            <div class="toggle-switch ${product.status ? 'active' : ''}"
                                 onclick="toggleProductStatus('${product.identifier}', this)">
                            </div>
                        </td>
                        <td>
                            <div class="actions">
                                <a class="btn-action btn-edit"
                                   href="${pageContext.request.contextPath}/product/get/${product.identifier}">
                                    Edit
                                </a>
                                <a class="btn-action btn-delete"
                                   href="${pageContext.request.contextPath}/product/delete/${product.identifier}"
                                   onclick="return confirm('Delete this product?')">
                                    Delete
                                </a>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:if>

    </div>
</div>

<script>
    function toggleProductStatus(identifier, el) {
        el.classList.toggle("active");
        fetch('${pageContext.request.contextPath}/product/toggle-status', {
            method: 'POST',
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            body: 'identifier=' + encodeURIComponent(identifier)
        });
    }
</script>

</body>
</html>