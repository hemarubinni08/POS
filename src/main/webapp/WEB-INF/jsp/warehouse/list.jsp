<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Warehouse Management</title>

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Font -->
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600&display=swap" rel="stylesheet">

    <style>
        :root {
            --primary:#2563eb;
            --bg:#f8fafc;
            --glass:rgba(255,255,255,.75);
            --text:#0f172a;
            --muted:#64748b;
            --border:#e2e8f0;
            --danger:#dc2626;
            --radius:16px;
            --shadow:0 20px 40px rgba(2,6,23,.08);
        }

        * {
            box-sizing:border-box;
            font-family:'Inter',sans-serif;
        }

        body {
            background:var(--bg);
            min-height:100vh;
            padding:40px 20px;
            color:var(--text);
        }

        /* BACK BUTTON */
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
                    transition:
                        transform .2s ease,
                        box-shadow .2s ease,
                        background .2s ease,
                        color .2s ease;
                }

                .back-arrow:hover {
                    background: #eef2ff;
                    color: var(--primary);
                    transform: translate(-2px, -2px);
                    box-shadow: 0 12px 28px rgba(37,99,235,.25);
                }

        .container-box {
            max-width:1100px;
            margin:60px auto 0;
        }

        .card {
            background:var(--glass);
            backdrop-filter:blur(16px);
            border-radius:var(--radius);
            border:1px solid var(--border);
            box-shadow:var(--shadow);
            overflow:hidden;
            animation:fadeUp .4s ease;
        }

        @keyframes fadeUp {
            from { opacity:0; transform:translateY(12px); }
            to   { opacity:1; transform:translateY(0); }
        }

        .card-header {
            padding:20px 24px;
            display:flex;
            justify-content:space-between;
            align-items:center;
            font-weight:600;
            font-size:18px;
        }

        .add-btn {
            padding:8px 14px;
            border-radius:10px;
            background:var(--primary);
            color:white;
            text-decoration:none;
            font-size:13px;
            font-weight:600;
            transition:transform .2s, box-shadow .2s;
        }

        .add-btn:hover {
            transform:translateY(-1px);
            box-shadow:0 8px 18px rgba(37,99,235,.35);
        }

        table {
            width:100%;
            border-collapse:collapse;
        }

        th, td {
            padding:14px 16px;
            font-size:13px;
            border-bottom:1px solid var(--border);
        }

        th {
            font-size:11px;
            text-transform:uppercase;
            color:var(--muted);
            background:rgba(248,250,252,.9);
        }

        tr:hover {
            background:rgba(241,245,249,.6);
        }

        .actions {
            display:flex;
            gap:8px;
        }

        .btn-action {
            padding:6px 12px;
            border-radius:8px;
            font-size:12px;
            font-weight:600;
            text-decoration:none;
            transition:transform .2s;
        }

        .btn-edit {
            background:#eef2ff;
            color:var(--primary);
        }

        .btn-delete {
            background:#fee2e2;
            color:var(--danger);
        }

        .btn-action:hover {
            transform:translateY(-1px);
        }

        .empty-state {
            padding:40px;
            text-align:center;
            color:var(--muted);
        }
    </style>
</head>

<body>

<!-- BACK -->
<a href="${pageContext.request.contextPath}/" class="back-arrow">←</a>

<div class="container-box">
    <div class="card">

        <div class="card-header">
            <span>Warehouse Management</span>
            <a href="${pageContext.request.contextPath}/warehouse/add" class="add-btn">
                + Add Warehouse
            </a>
        </div>

        <c:if test="${empty warehouses}">
            <div class="empty-state">No warehouses found</div>
        </c:if>

        <c:if test="${not empty warehouses}">
            <table>
                <thead>
                <tr>
                    <th>Identifier</th>
                    <th>Name</th>
                    <th>Location</th>
                    <th style="width:180px;">Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="w" items="${warehouses}">
                    <tr>
                        <td><strong>${w.identifier}</strong></td>
                        <td>${w.name}</td>
                        <td>${w.location}</td>
                        <td>
                            <div class="actions">
                                <a class="btn-action btn-edit"
                                   href="${pageContext.request.contextPath}/warehouse/get?identifier=${w.identifier}">
                                    Edit
                                </a>
                                <a class="btn-action btn-delete"
                                   href="${pageContext.request.contextPath}/warehouse/delete?identifier=${w.identifier}"
                                   onclick="return confirm('Delete this warehouse?');">
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