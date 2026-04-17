<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Node Management</title>

    <style>
        :root {
            --bg1: #0f172a;
            --bg2: #1e293b;

            --card: #ffffff;
            --text: #0f172a;
            --muted: #6b7280;

            --primary: #2563eb;
            --primary-hover: #1d4ed8;

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

        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 18px 20px;
            background: linear-gradient(135deg, var(--primary), #1e40af);
            color: #fff;
        }

        .header h2 {
            font-size: 18px;
            font-weight: 600;
        }

        .add-btn {
            background: #fff;
            color: var(--primary);
            padding: 8px 12px;
            border-radius: 10px;
            font-size: 13px;
            font-weight: 600;
            text-decoration: none;
            transition: 0.2s;
        }

        .add-btn:hover {
            background: #f1f5f9;
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
            text-align: left;
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

        .roles {
            font-size: 12px;
            color: var(--muted);
        }

        .actions a {
            margin-right: 10px;
            font-size: 13px;
            font-weight: 600;
            text-decoration: none;
            color: var(--primary);
        }

        .actions a:hover {
            text-decoration: underline;
        }

        .empty {
            text-align: center;
            padding: 30px;
            font-size: 13px;
            color: var(--muted);
        }

        /* BACK BUTTON */
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

        <div class="header">
            <h2>Node Management</h2>
            <a class="add-btn" href="${pageContext.request.contextPath}/node/add">
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
                                    <a href="${pageContext.request.contextPath}/node/get?identifier=${node.identifier}">
                                        Edit
                                    </a>
                                    <a href="${pageContext.request.contextPath}/node/delete?identifier=${node.identifier}">
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
            <a href="${pageContext.request.contextPath}/" class="back-home">← Back to Home JSP</a>
        </div>

    </div>

</div>

</body>
</html>