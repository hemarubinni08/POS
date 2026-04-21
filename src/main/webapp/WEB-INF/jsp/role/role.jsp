<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Role Management | Retail Core</title>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <style>
        :root { --sidebar-bg: #111827; --sidebar-hover: #1f2937; --accent-blue: #3b82f6; --bg-main: #f9fafb; --text-dark: #111827; --text-muted: #6b7280; --border-color: #e5e7eb; --sidebar-width: 280px; }
        body { margin: 0; font-family: 'Inter', sans-serif; display: flex; min-height: 100vh; background-color: var(--bg-main); overflow-x: hidden; }
        .sidebar { width: var(--sidebar-width); background-color: var(--sidebar-bg); color: #d1d5db; display: flex; flex-direction: column; position: fixed; top: 0; left: calc(-1 * var(--sidebar-width)); height: 100vh; border-right: 1px solid var(--border-color); z-index: 1050; transition: left 0.3s ease; }
        .sidebar.active { left: 0; }
        .sidebar-header { padding: 24px; font-size: 1.1rem; font-weight: 700; color: white; border-bottom: 1px solid rgba(255,255,255,0.05); display: flex; align-items: center; justify-content: space-between; }
        .nav-menu { flex-grow: 1; padding: 12px 0; }
        .nav-label { padding: 20px 24px 10px; font-size: 11px; font-weight: 700; text-transform: uppercase; color: #4b5563; letter-spacing: 1px; border-top: 1px solid rgba(255,255,255,0.05); margin-top: 10px; }
        .nav-item { display: flex; align-items: center; padding: 12px 24px; color: #9ca3af; text-decoration: none; font-size: 0.9rem; font-weight: 500; transition: all 0.2s ease; }
        .nav-item:hover { background: var(--sidebar-hover); color: white; }
        .nav-item.active { color: white; background: var(--sidebar-hover); border-right: 4px solid var(--accent-blue); }
        .content-wrapper { flex-grow: 1; display: flex; flex-direction: column; width: 100%; transition: margin-left 0.3s ease; }
        @media (min-width: 1200px) { .sidebar.active + .content-wrapper { margin-left: var(--sidebar-width); } }
        .top-navbar { background: white; height: 64px; display: flex; align-items: center; padding: 0 24px; border-bottom: 1px solid var(--border-color); position: sticky; top: 0; z-index: 1000; }
        .menu-toggle { background: none; border: none; cursor: pointer; display: flex; flex-direction: column; justify-content: space-between; width: 24px; height: 18px; }
        .menu-toggle span { display: block; height: 2px; width: 100%; background-color: var(--text-dark); border-radius: 2px; }
        .main-content { padding: 40px; }
        .data-card { background: white; border-radius: 12px; border: 1px solid var(--border-color); box-shadow: 0 1px 3px rgba(0,0,0,0.05); overflow: hidden; }
        .card-header-custom { padding: 20px 24px; border-bottom: 1px solid var(--border-color); display: flex; justify-content: space-between; align-items: center; }
        .role-name-text { font-weight: 600; color: var(--text-dark); background: #f1f5f9; padding: 4px 10px; border-radius: 6px; font-size: 13px; }
        .btn-action { font-size: 13px; font-weight: 600; border-radius: 6px; padding: 6px 12px; }
        .btn-logout { width: 100%; background: transparent; color: #9ca3af; border: 1px solid #374151; padding: 10px; border-radius: 6px; font-size: 14px; cursor: pointer; }
    </style>
</head>
<body>
    <div class="overlay" id="overlay" onclick="toggleSidebar()"></div>
    <aside class="sidebar" id="sidebar">
        <div class="sidebar-header">
            <div><span class="logo-text">■</span> RETAIL CORE</div>
            <button onclick="toggleSidebar()" style="background:none; border:none; color:white; font-size:24px; cursor:pointer;">&times;</button>
        </div>
        <nav class="nav-menu">
            <a href="${pageContext.request.contextPath}/" class="nav-item">Dashboard Home</a>
            <div class="nav-label">System Modules</div>
            <c:forEach var="n" items="${nodes}">
                <c:if test="${n.identifier != 'Home'}">
                    <a href="${n.path}" class="nav-item ${fn:contains(pageContext.request.requestURI, n.path) ? 'active' : ''}">${n.identifier}</a>
                </c:if>
            </c:forEach>
        </nav>
        <div class="logout-section" style="padding: 20px; border-top: 1px solid rgba(255,255,255,0.05);">
            <form action="${pageContext.request.contextPath}/logout" method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <button type="submit" class="btn-logout">Sign Out</button>
            </form>
        </div>
    </aside>

    <main class="content-wrapper">
        <header class="top-navbar">
            <button class="menu-toggle" onclick="toggleSidebar()"><span></span><span></span><span></span></button>
        </header>
        <section class="main-content">
            <div class="data-card">
                <div class="card-header-custom">
                    <h5 class="m-0 font-weight-bold">System Roles</h5>
                    <a href="${pageContext.request.contextPath}/role/add" class="btn btn-primary btn-action">+ Define Role</a>
                </div>
                <div class="table-responsive">
                    <table class="table table-hover m-0">
                        <thead><tr><th>Internal ID</th><th>Identifier</th><th class="text-end">Actions</th></tr></thead>
                        <tbody>
                            <c:forEach var="role" items="${roles}">
                                <tr>
                                    <td>#${role.id}</td>
                                    <td><span class="role-name-text">${role.identifier}</span></td>
                                    <td class="text-end">
                                        <a class="btn btn-outline-danger btn-action" href="${pageContext.request.contextPath}/role/delete?identifier=${role.identifier}" onclick="return confirm('Remove role?');">Remove</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </section>
    </main>
    <script>function toggleSidebar() { document.getElementById('sidebar').classList.toggle('active'); document.getElementById('overlay').classList.toggle('active'); }</script>
</body>
</html>