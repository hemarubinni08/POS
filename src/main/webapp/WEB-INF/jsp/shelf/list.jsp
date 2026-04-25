<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Shelf Management</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        :root {
            --primary:#2563eb; --bg:#f8fafc; --glass:rgba(255,255,255,0.75);
            --text:#0f172a; --muted:#64748b; --border:#e2e8f0;
            --danger:#dc2626; --radius:16px; --shadow:0 20px 40px rgba(2,6,23,.08);
        }
        body{background:var(--bg);padding:40px 20px}
        .container-box{max-width:1000px;margin:60px auto 0}
        .card{background:var(--glass);border-radius:var(--radius);border:1px solid var(--border);box-shadow:var(--shadow)}
        .card-header{padding:20px 24px;font-weight:600;display:flex;justify-content:space-between}
        table{width:100%}
        th,td{padding:14px 16px;border-bottom:1px solid var(--border)}
        th{font-size:11px;color:var(--muted)}
        .badge-active{background:#dcfce7;color:#166534}
        .badge-inactive{background:#fee2e2;color:#991b1b}
        .btn-edit,.btn-delete{padding:6px 12px;border-radius:8px;font-size:12px;text-decoration:none}
        .btn-edit{background:#eef2ff;color:var(--primary)}
        .btn-delete{background:#fee2e2;color:var(--danger)}
        .add-btn{background:var(--primary);color:#fff;padding:8px 14px;border-radius:10px;text-decoration:none}
    </style>
</head>

<body>

<div class="container-box">
    <div class="card">
        <div class="card-header">
            <span>Shelf Management</span>
            <a href="${pageContext.request.contextPath}/shelf/add" class="add-btn">+ Add Shelf</a>
        </div>

        <table>
            <thead>
            <tr>
                <th>Name</th>
                <th>Status</th>
                <th style="width:150px;">Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${shelves}" var="shelf">
                <tr>
                    <td>${shelf.identifier}</td>
                    <td>
                        <span class="badge ${shelf.active ? 'badge-active' : 'badge-inactive'}">
                            ${shelf.active ? 'Active' : 'Inactive'}
                        </span>
                    </td>
                    <td>
                        <a class="btn-edit" href="${pageContext.request.contextPath}/shelf/get?id=${shelf.id}">Edit</a>
                        <a class="btn-delete" href="${pageContext.request.contextPath}/shelf/delete?id=${shelf.id}">Delete</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>