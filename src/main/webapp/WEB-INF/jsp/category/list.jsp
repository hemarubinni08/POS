<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Category Management</title>

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Font -->
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600&display=swap" rel="stylesheet">

    <style>
        :root {
            --primary: #2563eb;
            --primary-hover: #1e40af;

            --bg: #f8fafc;
            --glass: rgba(255,255,255,0.75);

            --text: #0f172a;
            --muted: #64748b;

            --border: #e2e8f0;

            --danger: #ef4444;
            --danger-hover: #dc2626;

            --radius: 16px;
            --shadow: 0 20px 40px rgba(2,6,23,0.08);
        }

        * {
            font-family: 'Inter', sans-serif;
            box-sizing: border-box;
        }

        body {
            background: var(--bg);
            padding: 40px 20px;
        }

        /* BACK */
        .back-arrow {
            position: fixed;
            top: 20px;
            left: 20px;
            width: 42px;
            height: 42px;

            display: flex;
            align-items: center;
            justify-content: center;

            border-radius: 50%;
            background: var(--glass);
            backdrop-filter: blur(10px);

            border: 1px solid var(--border);
            color: var(--text);

            text-decoration: none;
            font-size: 18px;

            box-shadow: var(--shadow);
        }

        .back-arrow:hover {
            background: #eef2ff;
            color: var(--primary);
        }

        .container {
            max-width: 1100px;
            margin: 60px auto 0;
        }

        /* CARD */
        .card {
            border-radius: var(--radius);

            background: var(--glass);
            backdrop-filter: blur(16px);

            border: 1px solid var(--border);
            box-shadow: var(--shadow);
        }

        .card-header {
            padding: 20px;
            font-weight: 600;
            font-size: 16px;
            color: var(--text);

            border-bottom: 1px solid var(--border);
        }

        .card-body {
            padding: 0;
        }

        /* TABLE */
        table {
            width: 100%;
            border-collapse: collapse;
        }

        th {
            text-align: left;
            font-size: 12px;
            color: var(--muted);
            padding: 14px 18px;
            border-bottom: 1px solid var(--border);
            background: rgba(248,250,252,0.6);
        }

        td {
            padding: 16px 18px;
            border-bottom: 1px solid var(--border);
            font-size: 14px;
        }

        tr:hover {
            background: rgba(248,250,252,0.6);
        }

        /* ACTION BUTTONS */
        .btn-action {
            padding: 6px 10px;
            border-radius: 8px;
            font-size: 12px;
            font-weight: 600;
            text-decoration: none;
            display: inline-block;
            margin-right: 6px;
        }

        .btn-edit {
            background: #eef2ff;
            color: var(--primary);
        }

        .btn-edit:hover {
            background: #e0e7ff;
        }

        .btn-delete {
            background: #fee2e2;
            color: var(--danger);
        }

        .btn-delete:hover {
            background: #fecaca;
        }

        /* FOOTER */
        .card-footer {
            padding: 18px;
            text-align: right;
            border-top: 1px solid var(--border);
        }

        .btn-add {
            background: var(--primary);
            color: white;
            padding: 10px 16px;
            border-radius: 10px;
            font-weight: 600;
            text-decoration: none;
        }

        .btn-add:hover {
            background: var(--primary-hover);
        }

        /* EMPTY STATE */
        .empty {
            padding: 30px;
            text-align: center;
            color: var(--muted);
        }
    </style>
</head>

<body>

<!-- BACK -->
<a href="${pageContext.request.contextPath}/" class="back-arrow">←</a>

<div class="container">

    <div class="card">

        <div class="card-header">
            Category Management
        </div>

        <div class="card-body">

            <c:if test="${empty categories}">
                <div class="empty">
                    No categories found
                </div>
            </c:if>

            <c:if test="${not empty categories}">
                <table>
                    <thead>
                    <tr>
                        <th>Category Name</th>
                        <th>Description</th>
                        <th>Action</th>
                    </tr>
                    </thead>

                    <tbody>
                    <c:forEach items="${categories}" var="category">
                        <tr>
                            <td>${category.identifier}</td>
                            <td>${category.description}</td>

                            <td>
                                <a class="btn-action btn-edit"
                                   href="${pageContext.request.contextPath}/category/get?id=${category.id}">
                                    Edit
                                </a>

                                <a class="btn-action btn-delete"
                                   href="${pageContext.request.contextPath}/category/delete?id=${category.id}">
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
            <a href="${pageContext.request.contextPath}/category/add"
               class="btn-add">
                + Add Category
            </a>
        </div>

    </div>

</div>

</body>
</html>