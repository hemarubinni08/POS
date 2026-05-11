<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Price List</title>

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
            width: 1050px;
            background: #ffffff;
            padding: 35px 40px;
            border-radius: 18px;
            box-shadow: 0 12px 35px rgba(0, 0, 0, 0.12);
            border: 1px solid #e6e6e6;
        }

        h2 {
            text-align: center;
            margin-bottom: 25px;
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

        .footer-actions {
            margin-top: 20px;
            text-align: center;
        }

        .btn {
            padding: 10px 16px;
            border-radius: 10px;
            text-decoration: none;
            background: #4a90e2;
            color: white;
        }
    </style>
</head>

<body>

<div class="card-container">

    <a href="/" class="back-icon">←</a>

    <h2>Price List</h2>

    <c:if test="${empty prices}">
        <div style="text-align:center;">No prices found</div>
    </c:if>

    <c:if test="${not empty prices}">
        <table>
            <thead>
            <tr>
                <th>Identifier</th>
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

                    <td>
                        <a href="/price/get?identifier=${price.identifier}"
                           class="action-icon"
                           title="Update">✏️</a>

                        <a href="/price/delete?identifier=${price.identifier}"
                           class="action-icon"
                           title="Delete"
                           onclick="return confirm('Are you sure?');">
                            🗑
                        </a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>

    <div class="footer-actions">
        <a href="/price/add" class="btn">+ Add New Price</a>
    </div>

</div>

</body>
</html>