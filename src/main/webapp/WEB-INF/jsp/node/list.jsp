<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Node Management</title>

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

        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 18px 20px;
            background: var(--primary);
            color: white;
        }

        .header h2 {
            font-size: 18px;
            font-weight: 600;
        }

        .add-btn {
            background: white;
            color: var(--primary);
            padding: 8px 12px;
            border-radius: 10px;
            font-size: 13px;
            font-weight: 600;
            text-decoration: none;
        }

        .add-btn:hover {
            background: #e9f7ef;
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
            border-bottom: 1px solid var(--border);
            font-size: 13px;
        }

        th {
            text-transform: uppercase;
            font-size: 12px;
            color: var(--muted);
        }

        tr:hover {
            background: #f9fafb;
        }

        .roles {
            font-size: 12px;
            color: var(--muted);
        }

        .actions a {
            margin-right: 8px;
            padding: 5px 8px;
            border-radius: 6px;
            font-size: 12px;
            font-weight: 600;
            text-decoration: none;
        }

        .edit-btn {
            background: var(--accent);
            color: black;
        }

        .edit-btn:hover {
            background: #e0a800;
        }

        .delete-btn {
            background: var(--danger);
            color: white;
        }

        .delete-btn:hover {
            background: var(--danger-hover);
        }

        .empty {
            text-align: center;
            padding: 30px;
            font-size: 13px;
            color: var(--muted);
        }

        .card-footer {
            display: flex;
            justify-content: center;
            gap: 12px;
            padding: 16px;
            background: #f9fafb;
            border-top: 1px solid var(--border);
        }

        .back-home {
            padding: 7px 10px;
            border-radius: 8px;
            font-size: 13px;
            font-weight: 600;
            background: var(--accent);
            color: black;
            text-decoration: none;
        }

        .back-home:hover {
            background: #e0a800;
        }
    </style>
</head>

<body>

<div class="container">

    <div class="card">

        <div class="header">
            <h2>Node Management</h2>
            <a class="add-btn"
               href="${pageContext.request.contextPath}/node/add">
                + Add Node
            </a>
        </div>

        <div class="card-body">

            <c:choose>
                <c:when test="${empty nodes}">
                    <div class="empty">No nodes available</div>
                </c:when>

                <c:otherwise>
                    <table>
                        <thead>
                        <tr>
                            <th>Identifier</th>
                            <th>Path</th>
                            <th>Roles</th>
                            <th>Actions</th>
                        </tr>
                        </thead>

                        <tbody>
                        <c:forEach var="node" items="${nodes}">
                            <tr>
                                <td>${node.identifier}</td>
                                <td>${node.path}</td>

                                <td class="roles">
                                    <c:forEach var="role" items="${node.roles}" varStatus="s">
                                        ${role}<c:if test="${!s.last}">, </c:if>
                                    </c:forEach>
                                </td>

                                <td class="actions">
                                    <a class="edit-btn"
                                       href="${pageContext.request.contextPath}/node/get?identifier=${node.identifier}">
                                        Edit
                                    </a>

                                    <a class="delete-btn"
                                       href="${pageContext.request.contextPath}/node/delete?identifier=${node.identifier}"
                                       onclick="return confirm('Delete this node?');">
                                        Delete
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:otherwise>
            </c:choose>

        </div>

        <div class="card-footer">
            <a href="${pageContext.request.contextPath}/"
               class="back-home">
                ← Back to Home
            </a>
        </div>

    </div>

</div>

</body>
</html>