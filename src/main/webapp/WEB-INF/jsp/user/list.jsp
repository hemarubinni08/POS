<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>User Management</title>

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
            background: var(--bg);
            padding: 40px 16px;
        }

        .container {
            max-width: 1100px;
            margin: auto;
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
        }

        th {
            font-size: 12px;
            text-transform: uppercase;
            color: var(--muted);
        }

        tr:hover {
            background: #f9fafb;
        }

        .user-link {
            color: var(--primary);
            font-weight: 600;
            text-decoration: none;
        }

        .user-link:hover {
            text-decoration: underline;
        }

        .btn {
            padding: 7px 10px;
            border-radius: 8px;
            font-size: 13px;
            font-weight: 600;
            text-decoration: none;
            display: inline-block;
        }

        .btn-danger {
            background: var(--danger);
            color: white;
        }

        .btn-danger:hover {
            background: var(--danger-hover);
        }

        .btn-success {
            background: var(--primary);
            color: white;
        }

        .btn-success:hover {
            background: var(--primary-hover);
        }

        .btn-secondary {
            background: var(--accent);
            color: black;
        }

        .btn-secondary:hover {
            background: #e0a800;
        }

        .card-footer {
            padding: 16px;
            text-align: center;
            background: #f9fafb;
            border-top: 1px solid var(--border);
        }

        .footer-actions {
            display: flex;
            justify-content: center;
            gap: 12px;
        }

        .alert {
            background: #fff3cd;
            color: #856404;
            padding: 10px;
            border-radius: 8px;
            text-align: center;
        }

        .muted {
            font-size: 12px;
            color: var(--muted);
            margin-top: 10px;
        }
    </style>
</head>

<body>

<div class="container">

    <div class="card">

        <div class="card-header">
            User Management
        </div>

        <div class="card-body">

            <c:if test="${empty users}">
                <div class="alert">No users found</div>
            </c:if>

            <c:if test="${not empty users}">
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
                            <td>
                                <a class="user-link"
                                   href="${pageContext.request.contextPath}/user/get?username=${user.username}">
                                    ${user.username}
                                </a>
                            </td>

                            <td>${user.name}</td>
                            <td>${user.phoneNo}</td>
                            <td>${user.roles}</td>

                            <td>
                                <a class="btn btn-danger"
                                   href="${pageContext.request.contextPath}/user/delete?username=${user.username}"
                                   onclick="return confirm('Are you sure you want to delete this user?');">
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

            <div class="footer-actions">
                <a href="${pageContext.request.contextPath}/register" class="btn btn-success">
                    Register
                </a>

                <a href="${pageContext.request.contextPath}/" class="btn btn-secondary">
                    ← Back to Home
                </a>
            </div>

            <div class="muted">User Management System</div>

        </div>

    </div>

</div>

</body>
</html>