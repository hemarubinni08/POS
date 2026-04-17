<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Role List</title>

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
            max-width: 900px;
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

        .alert {
            background: #fef3c7;
            color: #92400e;
            padding: 10px 12px;
            border-radius: 8px;
            font-size: 13px;
            text-align: center;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 10px;
            overflow: hidden;
            border-radius: 10px;
        }

        th, td {
            padding: 12px;
            text-align: center;
            border-bottom: 1px solid var(--border);
            font-size: 14px;
        }

        th {
            background: #f3f4f6;
            font-weight: 600;
            color: var(--text);
        }

        tr:hover {
            background: #f9fafb;
        }

        a {
            text-decoration: none;
        }

        .link {
            color: var(--primary);
            font-weight: 600;
        }

        .btn {
            padding: 6px 10px;
            border-radius: 8px;
            font-size: 13px;
            font-weight: 600;
            border: none;
            cursor: pointer;
            transition: 0.2s;
            display: inline-block;
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
            color: white;
        }

        .btn-success:hover {
            background: #15803d;
        }

        .card-footer {
            display: flex;
            justify-content: center;
            gap: 12px;
            padding: 16px;
            background: #f9fafb;
            border-top: 1px solid var(--border);
        }

        /* NEW BACK BUTTON */
        .back-home {
            padding: 6px 10px;
            border-radius: 8px;
            font-size: 13px;
            font-weight: 600;
            background: #e5e7eb;
            color: #111827;
            text-decoration: none;
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
            List of Roles
        </div>

        <div class="card-body">

            <c:if test="${empty roles}">
                <div class="alert">No roles found</div>
            </c:if>

            <c:if test="${not empty roles}">
                <table>
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Role</th>
                        <th>Action</th>
                    </tr>
                    </thead>

                    <tbody>
                    <c:forEach var="role" items="${roles}">
                        <tr>
                            <td>
                                <a class="link"
                                   href="/role/get?identifier=${role.identifier}">
                                    ${role.id}
                                </a>
                            </td>

                            <td>${role.identifier}</td>

                            <td>
                                <a class="btn btn-danger"
                                   href="/role/delete?identifier=${role.identifier}"
                                   onclick="return confirm('Are you sure you want to delete this role?');">
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
            <a href="/role/add" class="btn btn-success">+ Add New Role</a>
            <a href="/" class="back-home">← Back to Home JSP</a>
        </div>

    </div>

</div>

</body>
</html>