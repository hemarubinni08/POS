<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Role List</title>

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
            position: relative;
            background: var(--bg);
            padding: 40px 16px;
        }

        .back-arrow {
            position: absolute;
            top: 20px;
            left: 20px;

            width: 42px;
            height: 42px;

            display: flex;
            align-items: center;
            justify-content: center;

            border-radius: 50%;
            background: var(--card);
            border: 1px solid var(--border);

            color: var(--text);
            font-size: 18px;
            text-decoration: none;

            box-shadow: var(--shadow);
            transition: 0.2s;
        }

        .back-arrow:hover {
            background: var(--accent);
            color: black;
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
            color: white;
            background: var(--primary);
        }

        .card-body {
            padding: 18px;
        }

        .alert {
            background: #fff3cd;
            color: #856404;
            padding: 10px;
            border-radius: 8px;
            text-align: center;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 10px;
        }

        th, td {
            padding: 12px;
            text-align: center;
            border-bottom: 1px solid var(--border);
            font-size: 14px;
        }

        th {
            background: #f9fafb;
            color: var(--muted);
            text-transform: uppercase;
            font-size: 12px;
        }

        tr:hover {
            background: #f9fafb;
        }

        .btn {
            padding: 6px 10px;
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

        .card-footer {
            display: flex;
            justify-content: center;
            gap: 12px;
            padding: 16px;
            background: #f9fafb;
            border-top: 1px solid var(--border);
        }
    </style>
</head>

<body>

<a href="${pageContext.request.contextPath}/" class="back-arrow">←</a>

<div class="container">

    <div class="card">

        <div class="card-header">
            Role List
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
                        <th>Roles</th>
                        <th>Action</th>
                    </tr>
                    </thead>

                    <tbody>
                    <c:forEach var="role" items="${roles}">
                        <tr>
                            <td>${role.id}</td>
                            <td>${role.identifier}</td>

                            <td>
                                <a class="btn btn-danger"
                                   href="${pageContext.request.contextPath}/role/delete?identifier=${role.identifier}"
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
            <a href="${pageContext.request.contextPath}/role/add"
               class="btn btn-success">
                + Add Role
            </a>
        </div>

    </div>

</div>

</body>
</html>