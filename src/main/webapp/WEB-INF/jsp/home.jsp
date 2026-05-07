<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Retail POS | Management</title>
    <style>
        :root {
            --primary-navy: #0f172a;
            --accent-blue: #3b82f6;
            --sidebar-bg: #1e293b;
            --text-gray: #94a3b8;
            --border-subtle: rgba(255, 255, 255, 0.1);
            --sidebar-width: 260px;
        }

        body {
            margin: 0;
            font-family: 'Inter', system-ui, sans-serif;
            background-color: #f8fafc;
            display: flex;
        }

        /* --- Persistent Sidebar --- */
        .sidebar {
            width: var(--sidebar-width);
            background-color: var(--sidebar-bg);
            color: #f1f5f9;
            display: flex;
            flex-direction: column; /* Stack header, menu, footer vertically */
            position: fixed;
            height: 100vh;
            overflow: hidden; /* Prevent the whole sidebar from scrolling */
        }

        .sidebar-header {
            padding: 24px;
            font-size: 1.2rem;
            font-weight: 800;
            border-bottom: 1px solid var(--border-subtle);
            color: white;
            flex-shrink: 0; /* Keep header size fixed */
        }

        /* --- Updated Scrollable Menu --- */
        .nav-menu {
            flex-grow: 1;
            padding: 20px 0;
            overflow-y: auto; /* Enable scrolling for middle section only */
            scrollbar-width: thin; /* Firefox */
            scrollbar-color: rgba(255,255,255,0.2) transparent;
        }

        /* Custom Scrollbar for Chrome/Safari */
        .nav-menu::-webkit-scrollbar { width: 5px; }
        .nav-menu::-webkit-scrollbar-thumb { background: rgba(255,255,255,0.1); border-radius: 10px; }

        .nav-item {
            display: flex; align-items: center; padding: 12px 24px;
            color: #cbd5e1; text-decoration: none; font-size: 14px; font-weight: 500;
        }
        .nav-item:hover { background: rgba(255, 255, 255, 0.05); color: white; }
        .nav-item.active { background: var(--accent-blue); color: white; }

        /* --- Fixed Sidebar Footer --- */
        .sidebar-footer {
            padding: 20px;
            border-top: 1px solid var(--border-subtle);
            background: rgba(0, 0, 0, 0.3);
            flex-shrink: 0; /* Keep footer size fixed at bottom */
        }

        .user-profile-box {
            display: flex;
            align-items: center;
            gap: 12px;
            margin-bottom: 15px;
            padding: 5px;
        }

        .avatar-circle {
            width: 38px; height: 38px;
            background: var(--accent-blue);
            color: white;
            border-radius: 8px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-weight: 700;
            font-size: 16px;
            text-transform: uppercase;
        }

        .user-info {
            display: flex;
            flex-direction: column;
            overflow: hidden;
        }

        .user-name {
            font-size: 13px;
            font-weight: 700;
            color: white;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
        }

        .user-label { font-size: 10px; color: var(--text-gray); text-transform: uppercase; }

        .btn-logout {
            width: 100%;
            background: transparent;
            color: #fca5a5;
            border: 1px solid rgba(252, 165, 165, 0.2);
            padding: 8px;
            border-radius: 6px;
            font-size: 12px;
            font-weight: 700;
            cursor: pointer;
            transition: all 0.2s;
        }
        .btn-logout:hover { background: #ef4444; color: white; border-color: #ef4444; }

        /* --- Main Content --- */
        .content-wrapper { margin-left: var(--sidebar-width); flex-grow: 1; }
        .main-content { padding: 40px; }
    </style>
</head>
<body>

    <aside class="sidebar">
        <div class="sidebar-header">RETAIL CORE</div>

        <nav class="nav-menu">
            <a href="${pageContext.request.contextPath}/" class="nav-item">Dashboard Home</a>
            <c:forEach var="node" items="${nodes}">
                <c:if test="${node.identifier != 'Home'}">
                    <a href="${node.path}" class="nav-item">${node.identifier}</a>
                </c:if>
            </c:forEach>
        </nav>

        <div class="sidebar-footer">
            <div class="user-profile-box">
                <div class="avatar-circle" id="userInitial">
                    <sec:authentication property="principal.username" />
                </div>
                <div class="user-info">
                    <span class="user-label">Logged in as</span>
                    <span class="user-name">
                        <sec:authentication property="principal.username" />
                    </span>
                </div>
            </div>

            <form action="${pageContext.request.contextPath}/logout" method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <button type="submit" class="btn-logout">Sign Out</button>
            </form>
        </div>
    </aside>

    <div class="content-wrapper">
        <main class="main-content">
            <div style="background: white; padding: 40px; border-radius: 12px; border: 1px solid #e2e8f0;">
                <h2 style="margin-top:0;">System Overview</h2>
                <p style="color: #64748b;">Welcome back to the management interface.</p>
            </div>
        </main>
    </div>

    <script>
        window.onload = function() {
            const badge = document.getElementById('userInitial');
            if (badge) {
                const text = badge.innerText.trim();
                badge.innerText = text.charAt(0);
            }
        };
    </script>

</body>
</html>