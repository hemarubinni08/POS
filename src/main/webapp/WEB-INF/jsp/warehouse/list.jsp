<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Warehouse Management</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600&display=swap" rel="stylesheet">

    <style>
        :root {
            --primary: #2563eb;
            --success: #16a34a;
            --bg: #f8fafc;
            --glass: rgba(255,255,255,0.75);
            --text: #0f172a;
            --muted: #64748b;
            --border: #e2e8f0;
            --danger: #dc2626;
            --radius: 16px;
            --shadow: 0 20px 40px rgba(2,6,23,0.08);
        }

        * { font-family: 'Inter', sans-serif; box-sizing: border-box; }

        body {
            background: var(--bg);
            min-height: 100vh;
            padding: 40px 20px;
            color: var(--text);
        }

        .back-arrow {
            position: fixed;
            top: 20px;
            left: 20px;
            width: 42px;
            height: 42px;
            display:flex;
            align-items:center;
            justify-content:center;
            border-radius:50%;
            background:var(--glass);
            backdrop-filter:blur(10px);
            border:1px solid var(--border);
            color:var(--text);
            font-size:18px;
            text-decoration:none;
            box-shadow:var(--shadow);
        }

        .container-box {
            max-width: 1100px;
            margin: 60px auto 0;
        }

        .card {
            background: var(--glass);
            backdrop-filter: blur(16px);
            border-radius: var(--radius);
            border: 1px solid var(--border);
            box-shadow: var(--shadow);
        }

        .card-header {
            padding: 20px 24px;
            display:flex;
            justify-content:space-between;
            align-items:center;
            font-weight:600;
        }

        .add-btn {
            background: var(--primary);
            color: #fff;
            padding: 8px 14px;
            border-radius: 10px;
            text-decoration: none;
            font-size: 13px;
            font-weight: 600;
        }

        table { width:100%; border-collapse:collapse; }

        th, td {
            padding: 14px 16px;
            font-size: 13px;
            border-bottom: 1px solid var(--border);
        }

        th {
            text-transform: uppercase;
            font-size: 11px;
            color: var(--muted);
            background: rgba(248,250,252,.9);
        }

        .toggle-switch {
            width: 46px;
            height: 24px;
            border-radius: 999px;
            background: #cbd5f5;
            position: relative;
            cursor: pointer;
        }

        .toggle-switch::after {
            content: "";
            position: absolute;
            top: 3px;
            left: 4px;
            width: 18px;
            height: 18px;
            background: #fff;
            border-radius: 50%;
            transition: 0.25s;
        }

        .toggle-switch.active {
            background: var(--success);
        }

        .toggle-switch.active::after {
            transform: translateX(20px);
        }

        .actions {
            display:flex;
            gap:8px;
        }

        .btn-edit {
            background:#eef2ff;
            color: var(--primary);
            padding:6px 12px;
            border-radius:8px;
            font-size:12px;
            font-weight:600;
            text-decoration:none;
        }

        .btn-delete {
            background:#fee2e2;
            color: var(--danger);
            padding:6px 12px;
            border-radius:8px;
            font-size:12px;
            font-weight:600;
            text-decoration:none;
        }
    </style>
</head>

<body>

<a href="${pageContext.request.contextPath}/" class="back-arrow">←</a>

<div class="container-box">
    <div class="card">

        <div class="card-header">
            <span>Warehouse Management</span>
            <a href="${pageContext.request.contextPath}/warehouse/add" class="add-btn">
                + Add Warehouse
            </a>
        </div>

        <table>
            <thead>
            <tr>
                <th>Identifier</th>
                <th>Name</th>
                <th>Location</th>
                <th>Status</th>
                <th style="width:160px;">Actions</th>
            </tr>
            </thead>

            <tbody>
            <c:forEach var="w" items="${warehouses}">
                <tr>
                    <td>${w.identifier}</td>
                    <td>${w.name}</td>
                    <td>${w.location}</td>
                    <td>
                        <div class="toggle-switch ${w.status ? 'active' : ''}"
                             onclick="toggleWarehouseStatus('${w.identifier}', this)">
                        </div>
                    </td>
                    <td>
                        <div class="actions">
                            <a class="btn-edit"
                               href="${pageContext.request.contextPath}/warehouse/get?identifier=${w.identifier}">
                                Edit
                            </a>
                            <a class="btn-delete"
                               href="${pageContext.request.contextPath}/warehouse/delete?identifier=${w.identifier}"
                               onclick="return confirm('Delete warehouse?')">
                                Delete
                            </a>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            </tbody>

        </table>

    </div>
</div>

<script>
    function toggleWarehouseStatus(identifier, el) {
        el.classList.toggle("active");
        fetch('${pageContext.request.contextPath}/warehouse/toggle-status', {
            method: 'POST',
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            body: 'identifier=' + encodeURIComponent(identifier)
        });
    }
</script>

</body>
</html>