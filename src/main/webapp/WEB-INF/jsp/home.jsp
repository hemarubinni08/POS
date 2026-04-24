<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>POS</title>

    <base href="${pageContext.request.contextPath}/">

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Icons -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css" rel="stylesheet">

    <!-- Font -->
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">

    <style>
        :root {
            --primary: #2563eb;
            --primary-hover: #1e40af;

            --bg: #f8fafc;
            --glass: rgba(255,255,255,0.75);

            --text: #0f172a;
            --muted: #64748b;

            --border: #e2e8f0;

            --radius: 16px;

            --sidebar-w: 260px;
            --topbar-h: 64px;

            --shadow: 0 20px 40px rgba(2,6,23,0.08);
        }

        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Inter', sans-serif;
        }

        body {
            background: var(--bg);
        }

        a { text-decoration: none; }

        /* TOPBAR */
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

            background: rgba(255,255,255,0.85);
            backdrop-filter: blur(10px);

            border-bottom: 1px solid var(--border);
            z-index: 1000;
        }

        .topbar-left {
            display: flex;
            align-items: center;
            gap: 14px;
        }

        .menu {
            width: 36px;
            height: 30px;
            display: flex;
            flex-direction: column;
            justify-content: center;
            gap: 5px;
            cursor: pointer;
        }

        .menu div {
            height: 2px;
            background: var(--text);
        }

        .title {
            font-weight: 600;
        }

        .btn-logout {
            background: #ef4444;
            border: none;
            color: white;
            padding: 8px 14px;
            border-radius: 10px;
        }

        /* SIDEBAR */
        .sidebar {
            position: fixed;
            top: var(--topbar-h);
            left: 0;

            width: var(--sidebar-w);
            height: calc(100vh - var(--topbar-h));

            background: linear-gradient(180deg, #0f172a, #1e293b);
            color: white;

            transform: translateX(-100%);
            transition: 0.3s ease;

            padding: 20px;
            z-index: 999;
        }

        .sidebar.active {
            transform: translateX(0);
        }

        .logo {
            font-weight: 700;
            margin-bottom: 24px;
        }

        .nav-item {
            display: flex;
            align-items: center;
            gap: 10px;

            padding: 12px;
            border-radius: 10px;

            color: #cbd5f5;
            font-size: 14px;

            margin-bottom: 6px;
            transition: 0.2s;
        }

        .nav-item:hover {
            background: rgba(255,255,255,0.1);
            color: white;
        }

        .nav-item.active {
            background: rgba(37,99,235,0.3);
            color: white;
        }

        /* CONTENT */
        .content {
            margin-top: var(--topbar-h);
            padding: 30px;
            transition: margin-left 0.3s ease;
        }

        .content.shift {
            margin-left: var(--sidebar-w);
        }

        /* CARD */
        .card-custom {
            max-width: 800px;

            padding: 28px;

            border-radius: var(--radius);

            background: var(--glass);
            backdrop-filter: blur(12px);

            border: 1px solid var(--border);
            box-shadow: var(--shadow);
        }

        .h1 {
            font-size: 22px;
            font-weight: 700;
            margin-bottom: 10px;
        }

        .sub {
            font-size: 14px;
            color: var(--muted);
        }

        .highlight {
            color: var(--primary);
            font-weight: 600;
        }

        /* OVERLAY */
        .overlay {
            position: fixed;
            top: var(--topbar-h);
            left: 0;
            width: 100%;
            height: calc(100% - var(--topbar-h));
            background: rgba(0,0,0,0.2);

            opacity: 0;
            pointer-events: none;
            transition: 0.3s;
        }

        .overlay.active {
            opacity: 1;
            pointer-events: all;
        }
    </style>

    <script>
        function toggleMenu() {
            document.getElementById("sidebar").classList.toggle("active");
            document.getElementById("content").classList.toggle("shift");
            document.getElementById("overlay").classList.toggle("active");
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
        <div class="title">Dashboard</div>
    </div>

    <form action="logout" method="post" style="margin:0;">
        <button type="submit" class="btn-logout">Logout</button>
    </form>
</div>

<!-- SIDEBAR -->
<div class="sidebar" id="sidebar">

    <div class="logo">POS System</div>

    <c:forEach var="node" items="${nodes}" varStatus="loop">
        <a href="${node.path}" class="nav-item">
            <i class="bi bi-grid"></i>
            ${node.identifier}
        </a>
    </c:forEach>

</div>

<!-- OVERLAY -->
<div class="overlay" id="overlay" onclick="toggleMenu()"></div>

<!-- CONTENT -->
<div class="content" id="content">

    <div class="card-custom">
        <div class="h1">Welcome</div>
        <div class="sub">
            Select a <span class="highlight">module</span> from the sidebar to manage roles and permissions.
        </div>
    </div>

</div>

</body>
</html>