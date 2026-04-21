<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Node List</title>

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
            background: #ffffff;
            padding: 35px 40px;
            border-radius: 18px;
            box-shadow: 0 12px 35px rgba(0, 0, 0, 0.12);
            border: 1px solid #e6e6e6;
            color: #333;
        }

        h2 {
            text-align: center;
            margin-bottom: 25px;
            font-weight: 600;
            color: #222;
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
            color: #333;
            text-decoration: none;
            font-weight: bold;
            background: #f0f0f0;
            border-radius: 50%;
            transition: all 0.25s ease;
        }

        .back-icon:hover {
            background: #333;
            color: #fff;
            transform: translateX(-3px);
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 10px;
            border-radius: 12px;
            overflow: hidden;
            background: #fff;
        }

        th {
            background: #f1f3f5;
            color: #333;
            padding: 12px;
            font-size: 14px;
            font-weight: 600;
            border-bottom: 1px solid #e6e6e6;
        }

        td {
            padding: 12px;
            text-align: center;
            border-bottom: 1px solid #eee;
            font-size: 14px;
            color: #444;
            word-break: break-word;
        }

        tr:hover {
            background: #f9fafc;
        }

        .action-icon {
            font-size: 18px;
            margin: 0 6px;
            text-decoration: none;
            color: #333;
            transition: 0.25s ease;
        }

        .action-icon:hover {
            transform: scale(1.3);
            color: #4a90e2;
        }

        .alert {
            padding: 12px;
            border-radius: 10px;
            margin-bottom: 15px;
            text-align: center;
            font-size: 14px;
        }

        .alert-warning {
            background: #fff4e5;
            color: #8a5a00;
            border: 1px solid #ffd59e;
        }

        .footer-actions {
            margin-top: 20px;
            display: flex;
            justify-content: center;
            gap: 12px;
        }

        .btn {
            padding: 10px 16px;
            border-radius: 10px;
            text-decoration: none;
            font-weight: 600;
            font-size: 14px;
            display: inline-block;
            transition: 0.25s ease;
        }


        .btn-add {
            background: #4a90e2;
            color: white;
        }

        .btn-add:hover {
            background: #357bd8;
            transform: translateY(-1px);
            box-shadow: 0 8px 18px rgba(0,0,0,0.15);
        }
    </style>
</head>

<body>

<div class="card-container">

    <a href="/" class="back-icon">←</a>

    <h2>List of Nodes</h2>

    <c:if test="${empty nodes}">
        <div class="alert alert-warning">
            No nodes found
        </div>
    </c:if>

    <c:if test="${not empty nodes}">
        <table>
            <thead>
            <tr>
                <th>ID</th>
                <th>Node</th>
                <th>Path</th>
                <th>Roles</th>
                <th>Action</th>
            </tr>
            </thead>

            <tbody>
            <c:forEach var="node" items="${nodes}">
                <tr>
                    <td>${node.id}</td>
                    <td>${node.identifier}</td>
                    <td>${node.path}</td>
                    <td>${node.roles}</td>

                    <td>
                        <a href="/node/get?identifier=${node.identifier}"
                           class="action-icon"
                           title="Edit">✏️</a>

                        <a href="/node/delete?identifier=${node.identifier}"
                           class="action-icon"
                           title="Delete"
                           onclick="return confirm('Are you sure you want to delete this node?');">
                            🗑
                        </a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>

    <div class="footer-actions">
        <a href="/node/add" class="btn btn-add">+ Add New Node</a>
    </div>

</div>

</body>
</html>