<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>User Management</title>

    <style>
        :root {
            --bg1: #0f172a;
            --bg2: #1e293b;

            --card: #ffffff;
            --text: #0f172a;
            --muted: #6b7280;

            --primary: #2563eb;
            --primary-hover: #1d4ed8;

            --danger: #ef4444;
            --danger-hover: #dc2626;

            --border: #e5e7eb;

            --radius: 14px;
            --shadow: 0 20px 50px rgba(0,0,0,0.25);
        }

        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Inter', sans-serif;
        }

        body {
            min-height: 100vh;
            padding: 40px 16px;
            background: linear-gradient(135deg, var(--bg1), var(--bg2));
            color: var(--text);
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
            color: #fff;
            background: linear-gradient(135deg, var(--primary), #1e40af);
        }

        .card-body {
            padding: 18px;
        }

        h3 {
            text-align: center;
            font-size: 18px;
            font-weight: 600;
            margin-bottom: 16px;
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
            font-weight: 600;
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
            border: none;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
            transition: 0.2s;
        }

        .btn-danger {
            background: var(--danger);
            color: #fff;
        }

        .btn-danger:hover {
            background: var(--danger-hover);
        }

        .btn-secondary {
            background: #e5e7eb;
            color: #111827;
        }

        .btn-secondary:hover {
            background: #d1d5db;
        }

        .btn-success {
            background: #16a34a;
            color: #fff;
        }

        .btn-success:hover {
            background: #15803d;
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
            background: #fef3c7;
            color: #92400e;
            padding: 10px 12px;
            border-radius: 8px;
            font-size: 13px;
            text-align: center;
        }

        .muted {
            font-size: 12px;
            color: var(--muted);
            margin-top: 10px;
        }

        /* NEW BACK BUTTON STYLE */
        .back-home {
            display: inline-block;
            padding: 7px 10px;
            border-radius: 8px;
            font-size: 13px;
            font-weight: 600;
            text-decoration: none;
            background: #e5e7eb;
            color: #111827;
            transition: 0.2s;
        }

        .back-home:hover {
            background: #d1d5db;
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
                                   href="/user/get?username=${user.username}">
                                    ${user.username}
                                </a>
                            </td>

                            <td>${user.name}</td>
                            <td>${user.phoneNo}</td>
                            <td>${user.roles}</td>

                            <td>
                                <a class="btn btn-danger"
                                   href="/user/delete?username=${user.username}"
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
                <a href="/register" class="btn btn-success">Register</a>
                <a href="/" class="back-home">← Back to Home JSP</a>
            </div>

            <div class="muted">User Management System</div>

        </div>

    </div>

</div>

</body>
</html>