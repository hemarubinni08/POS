<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Retail Core | Management</title>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <style>
        :root {
            --sidebar-bg: #111827;
            --sidebar-hover: #1f2937;
            --accent-blue: #3b82f6;
            --bg-main: #f9fafb;
            --text-dark: #111827;
            --text-muted: #6b7280;
            --border-color: #e5e7eb;
            --sidebar-width: 280px;
        }

        body {
            margin: 0;
            font-family: 'Inter', sans-serif;
            display: flex;
            min-height: 100vh;
            background-color: var(--bg-main);
            overflow-x: hidden;
        }

        .sidebar {
            width: var(--sidebar-width);
            background-color: var(--sidebar-bg);
            color: #d1d5db;
            display: flex;
            flex-direction: column;
            position: fixed;
            top: 0;
            left: calc(-1 * var(--sidebar-width));
            height: 100vh;
            border-right: 1px solid var(--border-color);
            z-index: 1050;
            transition: left 0.3s ease;
        }

        .sidebar.active {
            left: 0;
        }

        .sidebar-header {
            padding: 24px;
            font-size: 1.1rem;
            font-weight: 700;
            color: white;
            display: flex;
            align-items: center;
            justify-content: space-between;
            border-bottom: 1px solid rgba(255,255,255,0.05);
        }

        .sidebar-header span.logo-text { color: var(--accent-blue); margin-right: 10px; }

        .nav-menu { flex-grow: 1; padding: 24px 0; }
        .nav-label { padding: 0 24px 10px; font-size: 11px; font-weight: 700; text-transform: uppercase; color: #4b5563; letter-spacing: 1px; }

        .nav-item {
            display: flex;
            align-items: center;
            padding: 12px 24px;
            color: #9ca3af;
            text-decoration: none;
            font-size: 0.9rem;
            font-weight: 500;
            transition: all 0.2s ease;
        }

        .nav-item:hover { background: var(--sidebar-hover); color: white; }
        .nav-item.active { color: white; background: var(--sidebar-hover); border-right: 4px solid var(--accent-blue); }

        .content-wrapper {
            flex-grow: 1;
            display: flex;
            flex-direction: column;
            width: 100%;
            transition: margin-left 0.3s ease;
        }

        @media (min-width: 1200px) {
            .sidebar.active + .content-wrapper {
                margin-left: var(--sidebar-width);
            }
        }

        .top-navbar {
            background: white;
            height: 64px;
            display: flex;
            align-items: center;
            justify-content: space-between;
            padding: 0 24px;
            border-bottom: 1px solid var(--border-color);
            position: sticky;
            top: 0;
            z-index: 1000;
        }

        .menu-toggle {
            background: none;
            border: none;
            cursor: pointer;
            padding: 0;
            display: flex;
            flex-direction: column;
            justify-content: space-between;
            width: 24px;
            height: 18px;
        }

        .menu-toggle span {
            display: block;
            height: 2px;
            width: 100%;
            background-color: var(--text-dark);
            border-radius: 2px;
        }

        .user-profile {
            display: flex;
            align-items: center;
            gap: 10px;
            font-size: 0.85rem;
            font-weight: 600;
            color: var(--text-dark);
        }

        .main-content { padding: 60px 40px; max-width: 900px; margin: 0 auto; }

        .empty-state {
            background: white;
            border: 2px dashed var(--border-color);
            border-radius: 12px;
            padding: 60px;
            text-align: center;
        }

        .welcome-title { font-size: 2rem; font-weight: 800; color: var(--text-dark); margin-bottom: 12px; }
        .welcome-desc { color: var(--text-muted); font-size: 1.1rem; max-width: 500px; margin: 0 auto 30px; }

        .overlay {
            position: fixed;
            top: 0;
            left: 0;
            width: 100vw;
            height: 100vh;
            background: rgba(0,0,0,0.3);
            display: none;
            z-index: 1040;
        }

        .overlay.active { display: block; }

        .btn-logout {
            width: 100%; background: transparent; color: #9ca3af; border: 1px solid #374151;
            padding: 10px; border-radius: 6px; font-size: 14px; font-weight: 600; cursor: pointer;
        }
        .btn-logout:hover { background-color: #ef4444; color: white; border-color: #ef4444; }
    </style>
</head>
<body>

    <div class="overlay" id="overlay" onclick="toggleSidebar()"></div>

    <aside class="sidebar" id="sidebar">
        <div class="sidebar-header">
            <div><span class="logo-text">■</span> RETAIL CORE</div>
            <button onclick="toggleSidebar()" style="background:none; border:none; color:white; font-size:24px; cursor:pointer; line-height: 1;">&times;</button>
        </div>

        <nav class="nav-menu">
            <div class="nav-label">System Modules</div>
            <a href="${pageContext.request.contextPath}/" class="nav-item active">Home</a>
            <c:forEach var="node" items="${nodes}">
                <a href="${node.path}" class="nav-item">${node.identifier}</a>
            </c:forEach>
        </nav>

        <div class="logout-section" style="padding: 20px; border-top: 1px solid rgba(255,255,255,0.05);">
            <form action="/logout" method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <button type="submit" class="btn-logout">Sign Out</button>
            </form>
        </div>
    </aside>

    <main class="content-wrapper">
        <header class="top-navbar">
            <button class="menu-toggle" onclick="toggleSidebar()">
                <span></span>
                <span></span>
                <span></span>
            </button>
        </header>

        <section class="main-content">
            <div class="empty-state">
                <h1 class="welcome-title">Welcome back.</h1>
                <p class="welcome-desc">
                    Your retail management environment is ready. Open the menu to manage system entities.
                </p>
                <div style="display: inline-flex; gap: 10px; color: var(--text-muted); font-size: 13px; font-weight: 500;">

                </div>
            </div>
        </section>
    </main>

    <script>
        function toggleSidebar() {
            document.getElementById('sidebar').classList.toggle('active');
            document.getElementById('overlay').classList.toggle('active');
        }
    </script>

</body>
</html>