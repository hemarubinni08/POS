<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Warehouse List</title>

    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600&display=swap" rel="stylesheet">

    <style>
        body {
            margin: 0;
            font-family: 'Poppins', Arial, sans-serif;
            background: #f4f6f9;
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .card-container {
            position: relative;
            width: 900px;
            background: #fff;
            padding: 35px;
            border-radius: 18px;
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
        }

        .add-btn {
            display: inline-block;
            margin-bottom: 15px;
            text-decoration: none;
            background: #4a90e2;
            color: white;
            padding: 8px 14px;
            border-radius: 8px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        th, td {
            padding: 12px;
            border-bottom: 1px solid #eaeaea;
            text-align: left;
        }

        th {
            background: #f0f3f7;
        }

        tr:hover {
            background: #fafafa;
        }

        .action-icon {
            margin-right: 10px;
            text-decoration: none;
            font-size: 16px;
        }
    </style>
</head>

<body>

<div class="card-container">
    <a href="/" class="back-icon">←</a>
    <h2>Warehouse List</h2>
    <a href="/warehouse/add" class="add-btn">+ Add Warehouse</a>

    <table>
        <thead>
            <tr>
                <th>Identifier</th>
                <th>Location</th>
                <th>Manager</th>
                <th>Actions</th>
            </tr>
        </thead>

        <tbody>
            <c:forEach items="${warehouses}" var="warehouse">
                <tr>
                    <td>${warehouse.identifier}</td>
                    <td>${warehouse.location}</td>
                    <td>${warehouse.manager}</td>

                    <td>
                        <a href="/warehouse/get?identifier=${warehouse.identifier}"
                           class="action-icon"
                           title="Edit">✏️</a>

                        <a href="/warehouse/delete?identifier=${warehouse.identifier}"
                           class="action-icon"
                           title="Delete"
                           onclick="return confirm('Are you sure you want to delete this warehouse?');">
                            🗑️
                        </a>
                    </td>
                </tr>
            </c:forEach>

            <c:if test="${empty warehouses}">
                <tr>
                    <td colspan="4" style="text-align:center;">No warehouses found.</td>
                </tr>
            </c:if>
        </tbody>
    </table>

</div>

</body>
</html>