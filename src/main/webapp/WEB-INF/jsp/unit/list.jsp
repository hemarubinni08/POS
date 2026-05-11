<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Unit List</title>

    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600&display=swap"
          rel="stylesheet">

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

        .delete-icon {
            color: #e74c3c;
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
    </style>
</head>

<body>

<div class="card-container">
    <a href="/" class="back-icon">←</a>
    <h2>Unit List</h2>
    <c:if test="${empty units}">
        <div class="alert alert-warning">
            No units found
        </div>
    </c:if>
    <c:if test="${not empty units}">
        <table>
            <thead>
            <tr>
                <th>Identifier</th>
                <th>Status</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="unit" items="${units}">
                <tr>
                    <td>${unit.identifier}</td>

                    <td>
                        <c:choose>
                            <c:when test="${unit.status}">Active</c:when>
                            <c:otherwise>Inactive</c:otherwise>
                        </c:choose>
                    </td>

                    <td>
                        <a href="/unit/get?identifier=${unit.identifier}"
                           class="action-icon"
                           title="Edit">✏️</a>

                        <a href="/unit/delete?identifier=${unit.identifier}"
                           class="action-icon delete-icon"
                           title="Delete"
                           onclick="return confirm('Are you sure you want to delete this unit?');">
                            🗑
                        </a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
    <div class="footer-actions">
        <a href="/unit/add" class="btn btn-add">+ Add Unit</a>
    </div>
</div>
</body>
</html>