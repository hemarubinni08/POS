<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Role Management</title>

    <style>
        :root {
            --bg: #f5f7fb;
            --surface: #ffffff;
            --text: #111827;
            --muted: #6b7280;

            --primary: #111827;
            --danger: #ef4444;

            --border: #e5e7eb;

            --radius-sm: 6px;

            --shadow-sm: 0 1px 2px rgba(0,0,0,0.06);
            --shadow-md: 0 6px 18px rgba(0,0,0,0.08);

            --topbar-h: 56px;
            --sidebar-w: 240px;
        }

        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Inter', sans-serif;
        }

        body {
            background: var(--bg);
            color: var(--text);
        }

        a {
            text-decoration: none;
            color: inherit;
        }

        /* TOPBAR */
        .topbar {
            height: var(--topbar-h);
            background: var(--primary);
            color: #fff;

            display: flex;
            justify-content: space-between;
            align-items: center;

            padding: 0 16px;
            position: fixed;
            top: 0;
            left: 0;
            right: 0;

            box-shadow: var(--shadow-sm);
            z-index: 1000;
        }

        .topbar-left {
            display: flex;
            align-items: center;
            gap: 12px;
        }

        .topbar-title {
            font-size: 15px;
            font-weight: 600;
        }

        .menu {
            width: 36px;
            height: 32px;
            display: flex;
            flex-direction: column;
            justify-content: center;
            gap: 5px;
            cursor: pointer;
            border-radius: var(--radius-sm);
            padding: 6px;
        }

        .menu:hover {
            background: rgba(255,255,255,0.1);
        }

        .menu div {
            height: 2px;
            background: #fff;
            border-radius: 2px;
        }

        /* BUTTON */
        .btn {
            border: none;
            padding: 8px 14px;
            border-radius: var(--radius-sm);
            font-size: 13px;
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

        /* SIDEBAR */
        .sidebar {
            position: fixed;
            top: var(--topbar-h);
            left: -260px;
            width: var(--sidebar-w);
            height: calc(100vh - var(--topbar-h));

            background: var(--surface);
            border-right: 1px solid var(--border);

            transition: 0.25s ease;
            box-shadow: var(--shadow-md);
            padding-top: 10px;
            z-index: 999;
        }

        .sidebar.active {
            left: 0;
        }

        .sidebar a {
            display: block;
            padding: 12px 18px;
            margin: 4px 10px;
            border-radius: var(--radius-sm);

            color: var(--muted);
            font-size: 14px;

            transition: 0.2s;
        }

        .sidebar a:hover {
            background: #f3f4f6;
            color: var(--text);
        }

        /* CONTENT */
        .content {
            margin-top: var(--topbar-h);
            padding: 28px;
            transition: margin-left 0.25s ease;
        }

        .sidebar.active ~ .content {
            margin-left: var(--sidebar-w);
        }

        .title {
            font-size: 18px;
            font-weight: 600;
            margin-bottom: 6px;
        }

        .subtitle {
            font-size: 13px;
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

<div class="topbar">
    <div class="topbar-left">
        <div class="menu" onclick="toggleMenu()">
            <div></div>
            <div></div>
            <div></div>
        </div>
        <div class="topbar-title">Role Management</div>
    </div>

    <form action="${pageContext.request.contextPath}/logout" method="post" style="margin:0;">
        <button type="submit" class="btn btn-danger">Logout</button>
    </form>
</div>

<div class="sidebar" id="sidebar">
    <c:forEach var="node" items="${nodes}">
        <a href="${pageContext.request.contextPath}${node.path}">
            ${node.identifier}
        </a>
    </c:forEach>
</div>

<div class="content">
    <div class="title">Welcome to the Role Management System</div>
    <div class="subtitle">Please select an option from the menu.</div>
</div>

</body>
</html>