<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Category List</title>
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
            width: 900px;
            background: #ffffff;
            padding: 40px 45px;
            border-radius: 18px;
            box-shadow: 0 12px 35px rgba(0, 0, 0, 0.12);
            border: 1px solid #e6e6e6;
            color: #333;
        }
        h2 {
            text-align: center;
            margin-bottom: 25px;
            font-weight: 600;
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
        }
        .home-link {
            position: absolute;
            top: 16px;
            right: 16px;
            font-size: 13px;
            font-weight: 500;
            color: #333;
            text-decoration: none;
            padding: 6px 12px;
            border-radius: 8px;
            background: #f0f0f0;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 10px;
        }
        th {
            background: #4a90e2;
            color: white;
            padding: 12px;
            font-size: 14px;
        }
        td {
            padding: 12px;
            text-align: center;
            border-bottom: 1px solid #eee;
            font-size: 14px;
            color: #333;
        }
        tr:hover {
            background: #f7f9ff;
        }
        .action-icon {
            font-size: 16px;
            margin: 0 6px;
            text-decoration: none;
            color: #4a90e2;
        }
        .alert {
            padding: 10px;
            border-radius: 8px;
            margin-bottom: 15px;
            text-align: center;
            font-size: 13px;
        }
        .alert-warning {
            background: #fff3cd;
            color: #856404;
        }
        .footer-actions {
            margin-top: 20px;
            display: flex;
            justify-content: center;
        }
        .btn {
            padding: 10px 16px;
            border-radius: 10px;
            text-decoration: none;
            font-weight: 600;
            font-size: 14px;
        }
        .btn-add {
            background: #4a90e2;
            color: white;
        }
    </style>
</head>
<body>
<div class="card-container">
    <a href="/" class="back-icon">←</a>
    <a href="/" class="home-link">Home</a>
    <h2>List of Categories</h2>
    <c:if test="${empty categories}">
        <div class="alert alert-warning">
            No categories found
        </div>
    </c:if>
    <c:if test="${not empty categories}">
        <table>
            <thead>
            <tr>
                <th>Category</th>
                <th>Super Category</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="category" items="${categories}">
                <tr>
                    <td>${category.identifier}</td>
                    <td>
                        <c:choose>
                            <c:when test="${empty category.superCategory}">
                            </c:when>
                            <c:otherwise>
                                ${category.superCategory}
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <a href="/category/get?identifier=${category.identifier}"
                           class="action-icon"
                           title="Edit">✏️</a>
                        <a href="/category/delete?identifier=${category.identifier}"
                           class="action-icon"
                           title="Delete"
                           onclick="return confirm('Are you sure you want to delete this category?');">
                            🗑
                        </a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
    <div class="footer-actions">
        <a href="/category/add" class="btn btn-add">+ Add New Category</a>
    </div>
</div>
</body>
</html>
