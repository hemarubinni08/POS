<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Node | Retail Core</title>
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

        .sidebar.active { left: 0; }

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
            background: none; border: none; cursor: pointer; padding: 0;
            display: flex; flex-direction: column; justify-content: space-between;
            width: 24px; height: 18px;
        }
        .menu-toggle span { display: block; height: 2px; width: 100%; background-color: var(--text-dark); border-radius: 2px; }

        .main-content {
            padding: 40px;
            display: flex;
            justify-content: center;
        }

        .form-card {
            width: 100%;
            max-width: 480px;
            background: white;
            padding: 32px;
            border-radius: 12px;
            border: 1px solid var(--border-color);
            box-shadow: 0 1px 3px rgba(0,0,0,0.05);
        }

        .form-label-custom {
            font-size: 12px;
            font-weight: 700;
            text-transform: uppercase;
            color: var(--text-muted);
            margin-bottom: 8px;
            letter-spacing: 0.025em;
        }

        .form-control-custom {
            border: 1.5px solid var(--border-color);
            border-radius: 8px;
            padding: 12px 14px;
            font-size: 14px;
            transition: all 0.2s;
        }

        .form-control-custom:focus {
            border-color: var(--accent-blue);
            box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.1);
            outline: none;
        }

        .btn-submit {
            padding: 10px 24px;
            background: var(--sidebar-bg);
            color: white;
            border: none;
            border-radius: 8px;
            font-weight: 600;
            transition: opacity 0.2s;
        }

        .btn-cancel {
            padding: 10px 24px;
            background: transparent;
            color: var(--text-muted);
            border: 1.5px solid var(--border-color);
            border-radius: 8px;
            font-weight: 600;
            text-decoration: none;
            font-size: 14px;
        }

        .overlay {
            position: fixed; top: 0; left: 0; width: 100vw; height: 100vh;
            background: rgba(0,0,0,0.3); display: none; z-index: 1040;
        }
        .overlay.active { display: block; }

        .btn-logout {
            width: 100%; background: transparent; color: #9ca3af; border: 1px solid #374151;
            padding: 10px; border-radius: 6px; font-size: 14px; cursor: pointer;
        }
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
            <div class="nav-label">System Modules</div>
            <a href="${pageContext.request.contextPath}/" class="nav-item">Home</a>
            <c:forEach var="n" items="${nodes}">
                <a href="${n.path}" class="nav-item ${fn:contains(pageContext.request.requestURI, n.path) ? 'active' : ''}">
                    ${n.identifier}
                </a>
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
                <span></span><span></span><span></span>
            </button>
            <div class="breadcrumb-text small text-muted">Configuration / Update Node</div>
        </header>

        <section class="main-content">
            <div class="form-card">
                <h4 class="mb-4 font-weight-bold" style="color: var(--text-dark);">Edit System Node</h4>

                <c:if test="${not empty message}">
                    <div class="alert alert-info border-0 small py-2 mb-4">
                        ${message}
                    </div>
                </c:if>

                <c:if test="${empty node}">
                    <div class="alert alert-danger border-0 small py-2">
                        System Error: Node data not found.
                    </div>
                </c:if>

                <c:if test="${not empty node}">
                    <form:form action="/node/update" method="post" modelAttribute="nodeDto">
                        <%-- Hidden ID to ensure it is passed back to the controller --%>
                        <form:hidden path="id" value="${node.id}"/>

                        <div class="mb-3">
                            <label class="form-label-custom">Node Name (Display)</label>
                            <form:input path="identifier"
                                        cssClass="form-control form-control-custom"
                                        value="${node.identifier}"
                                        placeholder="e.g. Inventory Management"
                                        required="true"/>
                        </div>

                        <div class="mb-4">
                            <label class="form-label-custom">Navigation Path</label>
                            <form:input path="path"
                                        cssClass="form-control form-control-custom"
                                        value="${node.path}"
                                        placeholder="e.g. /inventory/list"
                                        required="true"/>
                        </div>

                        <div class="d-flex justify-content-between align-items-center">
                            <a href="/node/list" class="btn-cancel">Cancel</a>
                            <button type="submit" class="btn-submit">Save Node</button>
                        </div>
                    </form:form>
                </c:if>
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