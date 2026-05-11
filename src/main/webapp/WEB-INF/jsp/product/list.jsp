<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Product List</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600&display=swap" rel="stylesheet">

    <style>
        body {
            margin: 0;
            font-family: 'Poppins', sans-serif;
            min-height: 100vh;
            background: #f4f6f9;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .card-container {
            position: relative;
            width: 950px;
            background: white;
            padding: 30px;
            border-radius: 16px;
            box-shadow: 0 12px 35px rgba(0,0,0,0.12);
        }

        h2 {
            text-align: center;
            margin-bottom: 20px;
        }

        .back-icon {
            position: absolute;
            top: 16px;
            left: 16px;
            width: 36px;
            height: 36px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 18px;
            text-decoration: none;
            background: #f0f0f0;
            border-radius: 50%;
            color: #333;
            transition: 0.25s ease;
        }

        .back-icon:hover {
            background: #333;
            color: #fff;
            transform: translateX(-2px);
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        th, td {
            padding: 12px;
            text-align: center;
            border-bottom: 1px solid #eee;
        }

        th {
            background: #f1f3f5;
        }

        tr:hover {
            background: #f9fafc;
        }

        .action-icon {
            font-size: 18px;
            margin: 0 6px;
            text-decoration: none;
        }

        .btn {
            padding: 10px 16px;
            border-radius: 10px;
            text-decoration: none;
            background: #4a90e2;
            color: white;
        }

        .footer-actions {
            margin-top: 20px;
            text-align: center;
        }
    </style>
</head>

<body>

<div class="card-container">

    <a href="/" class="back-icon">←</a>
    <h2>Product List</h2>
    <c:if test="${empty products}">
        <div style="text-align:center;">No products found</div>
    </c:if>
    <c:if test="${not empty products}">
        <table>
            <thead>
            <tr>
                <th>Identifier</th>
                <th>Categories</th>
                <th>Description</th>
                <th>Brand</th>
                <th>SKU</th>
                <th>Action</th>
            </tr>
            </thead>

            <tbody>
            <c:forEach var="p" items="${products}">
                <tr>
                    <td>${p.identifier}</td>
                    <td>${p.categories}</td>
                    <td>${p.description}</td>
                    <td>${p.brand}</td>
                    <td>${p.sku}</td>

                    <td>
                        <a class="action-icon"
                           href="/product/get?identifier=${p.identifier}"
                           title="Edit">✏️</a>

                        <a class="action-icon"
                           href="/product/delete?identifier=${p.identifier}"
                           onclick="return confirm('Delete product?')"
                           title="Delete">🗑</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>

    <div class="footer-actions">
        <a class="btn" href="/product/add">+ Add Product</a>
    </div>
</div>
</body>
</html>