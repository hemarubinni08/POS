<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Role Management</title>

    <!-- Ensures correct routing (prevents login/navigation issues) -->
    <base href="${pageContext.request.contextPath}/">

    <!-- Font -->
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">

    <style>
        :root {
            --bg: #0b1220;
            --panel: rgba(255,255,255,0.06);
            --text: #e5e7eb;
            --muted: #9ca3af;

            --danger: #ef4444;

            --border: rgba(255,255,255,0.08);

            --radius: 14px;

            --topbar-h: 64px;
            --sidebar-w: 260px;
        }

        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Inter', sans-serif;
        }

        body {
            background: radial-gradient(circle at top, #111827, #0b1220);
            color: var(--text);
        }

        a {
            text-decoration: none;
            color: inherit;
        }

        /* ================= TOPBAR ================= */
        .topbar {
            position: fixed;
            top: 0;
            left: 0;
            right: 0;
            height: var(--topbar-h);

            display: flex;
            justify-content: space-between;
            align-items: center;

            padding: 0 20px;

            background: rgba(17, 24, 39, 0.75);
            backdrop-filter: blur(14px);

            border-bottom: 1px solid var(--border);
            z-index: 1000;
        }

        .topbar-left {
            display: flex;
            align-items: center;
            gap: 14px;
        }

        .title {
            font-weight: 600;
            font-size: 16px;
        }

        /* ================= MENU ICON ================= */
        .menu {
            width: 40px;
            height: 36px;
            display: flex;
            flex-direction: column;
            justify-content: center;
            gap: 5px;
            cursor: pointer;
            padding: 8px;
            border-radius: 10px;
        }

        .menu:hover {
            background: rgba(255,255,255,0.08);
        }

        .menu div {
            height: 2px;
            background: #fff;
            border-radius: 2px;
        }

        /* ================= BUTTON ================= */
        .btn {
            border: none;
            padding: 9px 14px;
            border-radius: 10px;
            font-weight: 600;
            cursor: pointer;
        }

        .btn-danger {
            background: var(--danger);
            color: white;
        }

        .btn-danger:hover {
            background: #dc2626;
        }

        /* ================= SIDEBAR (FIXED) ================= */
        .sidebar {
            position: fixed;
            top: var(--topbar-h);
            left: 0;

            width: var(--sidebar-w);
            height: calc(100vh - var(--topbar-h));

            background: rgba(17, 26, 46, 0.92);
            backdrop-filter: blur(16px);

            border-right: 1px solid var(--border);

            transform: translateX(-100%);
            transition: transform 0.3s ease;

            padding: 16px 10px;
            z-index: 999;
        }

        .sidebar.active {
            transform: translateX(0);
        }

        .sidebar a {
            display: block;
            padding: 12px 14px;
            margin: 6px 8px;
            border-radius: 10px;

            font-size: 14px;
            color: var(--muted);

            transition: 0.2s;
        }

        .sidebar a:hover {
            background: rgba(255,255,255,0.06);
            color: #fff;
            transform: translateX(4px);
        }

        /* ================= CONTENT (FIXED PUSH BEHAVIOR) ================= */
        .content {
            margin-top: var(--topbar-h);
            padding: 30px;

            transition: margin-left 0.3s ease;
            margin-left: 0;
        }

        .sidebar.active ~ .content {
            margin-left: var(--sidebar-w);
        }

        /* ================= CARD ================= */
        .card {
            max-width: 800px;
            padding: 24px;

            border-radius: var(--radius);
            background: var(--panel);
            border: 1px solid var(--border);

            backdrop-filter: blur(12px);
        }

        .h1 {
            font-size: 22px;
            font-weight: 700;
            margin-bottom: 8px;
        }

        .sub {
            font-size: 14px;
            color: var(--muted);
        }
    </style>

    <script>
        function toggleMenu() {
            document.getElementById("sidebar").classList.toggle("active");
        }
    </script>
</head>

<body>

<!-- TOPBAR -->
<div class="topbar">
    <div class="topbar-left">
        <div class="menu" onclick="toggleMenu()">
            <div></div>
            <div></div>
            <div></div>
        </div>
        <div class="title">Role Management</div>
    </div>

    <form action="logout" method="post" style="margin:0;">
        <button type="submit" class="btn btn-danger">Logout</button>
    </form>
</div>

<!-- SIDEBAR -->
<div class="sidebar" id="sidebar">
    <c:forEach var="node" items="${nodes}">
        <a href="${node.path}">
            ${node.identifier}
        </a>
    </c:forEach>
</div>

<!-- CONTENT -->
<div class="content">
    <div class="card">
        <div class="h1">Welcome</div>
        <div class="sub">
            Select a module from the sidebar to manage roles and permissions.
        </div>
    </div>
</div>

</body>
</html>