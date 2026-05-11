<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>User Management</title>
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

        .home-link {
            position: absolute;
            top: 16px;
            right: 16px;
            font-size: 14px;
            font-weight: 600;
            color: #333;
            text-decoration: none;
            padding: 8px 14px;
            border-radius: 8px;
            background: #f0f0f0;
            transition: all 0.25s ease;
        }

        .home-link:hover {
            background: #333;
            color: #fff;
            transform: translateY(-2px);
        }

        .table-wrapper {
            overflow-x: auto;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 10px;
            border-radius: 12px;
            overflow: hidden;
        }

        th {
            background: #4a90e2;
            color: white;
            padding: 12px;
            font-size: 14px;
            font-weight: 600;
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
            color: #4a90e2;
            transition: 0.25s ease;
        }

        .action-icon:hover {
            transform: scale(1.2);
            color: #1f5fbf;
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
    </style>
</head>

<body>

<div class="card-container">

    <a href="/" class="back-icon">←</a>

    <h2>User Management</h2>

    <c:if test="${empty users}">
        <div class="alert alert-warning">
            No users found
        </div>
    </c:if>

    <c:if test="${not empty users}">
        <div class="table-wrapper">
            <table>
                <thead>
                <tr>
                    <th>Email</th>
                    <th>Name</th>
                    <th>Phone</th>
                    <th>Roles</th>
                    <th>Action</th>
                </tr>
                </thead>

                <tbody>
                <c:forEach var="user" items="${users}">
                    <tr>
                        <td>${user.username}</td>
                        <td>${user.name}</td>
                        <td>${user.phoneNo}</td>
                        <td>${user.roles}</td>

                        <td>
                            <a href="/user/get?username=${user.username}"
                               class="action-icon"
                               title="Edit">✏️</a>

                            <a href="/user/delete?username=${user.username}"
                               class="action-icon"
                               title="Delete"
                               onclick="return confirm('Are you sure you want to delete this user?');">
                                🗑
                            </a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </c:if>

</div>

</body>
</html>