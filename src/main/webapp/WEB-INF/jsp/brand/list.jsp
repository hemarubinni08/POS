<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Brand Management</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600&display=swap" rel="stylesheet">

    <style>
        :root {
            --primary:#2563eb; --primary-hover:#1e40af;
            --bg:#f8fafc; --glass:rgba(255,255,255,0.85);
            --text:#0f172a; --muted:#64748b;
            --border:#e2e8f0;
            --danger:#dc2626;
            --radius:16px; --shadow:0 20px 40px rgba(2,6,23,0.08);
        }

        *{font-family:'Inter',sans-serif;box-sizing:border-box}
        body{background:var(--bg);padding:40px 20px}

        .back-arrow{
            position:fixed;top:20px;left:20px;
            width:42px;height:42px;border-radius:50%;
            display:flex;align-items:center;justify-content:center;
            background:var(--glass);
            border:1px solid var(--border);
            box-shadow:var(--shadow);
            text-decoration:none;font-size:18px;color:var(--text);
        }

        .container-box{max-width:1100px;margin:60px auto 0}

        .card{
            background:var(--glass);
            border-radius:var(--radius);
            border:1px solid var(--border);
            box-shadow:var(--shadow);
        }

        .card-header{
            padding:20px 24px;
            display:flex;justify-content:space-between;align-items:center;
            font-weight:600;
        }

        .add-btn{
            padding:8px 14px;
            background:var(--primary);
            color:white;
            border-radius:10px;
            text-decoration:none;
            font-size:13px;font-weight:600;
        }

        table{width:100%;border-collapse:collapse}
        th,td{padding:14px 16px;border-bottom:1px solid var(--border);font-size:13px}
        th{text-transform:uppercase;font-size:11px;color:var(--muted)}
        tr:hover{background:rgba(241,245,249,0.6)}

        .actions{display:flex;gap:8px}
        .btn-edit{background:#eef2ff;color:var(--primary);padding:6px 12px;border-radius:8px;text-decoration:none}
        .btn-delete{background:#fee2e2;color:var(--danger);padding:6px 12px;border-radius:8px;text-decoration:none}
    </style>
</head>

<body>

<a href="${pageContext.request.contextPath}/" class="back-arrow">←</a>

<div class="container-box">
    <div class="card">

        <div class="card-header">
            <span>Brand Management</span>
            <a href="${pageContext.request.contextPath}/brand/add" class="add-btn">+ Add Brand</a>
        </div>

        <c:if test="${empty brands}">
            <div class="p-4 text-center text-muted">No brands found</div>
        </c:if>

        <c:if test="${not empty brands}">
            <table>
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Identifier</th>
                    <th>Description</th>
                    <th style="width:160px;">Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${brands}" var="brand">
                    <tr>
                        <td>${brand.id}</td>
                        <td>${brand.identifier}</td>
                        <td>${brand.description}</td>
                        <td>
                            <div class="actions">
                                <a class="btn-edit"
                                   href="${pageContext.request.contextPath}/brand/get?identifier=${brand.identifier}">
                                    Edit
                                </a>
                                <a class="btn-delete"
                                   href="${pageContext.request.contextPath}/brand/delete?identifier=${brand.identifier}">
                                    Delete
                                </a>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:if>

    </div>
</div>

</body>
</html>