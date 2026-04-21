<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Management | Retail Core</title>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <style>
        :root {
            --accent-blue: #3b82f6;
            --bg-main: #f9fafb;
            --text-dark: #111827;
            --border-color: #e5e7eb;
        }

        body {
            margin: 0;
            font-family: 'Inter', sans-serif;
            background-color: var(--bg-main);
            color: var(--text-dark);
        }

        .top-navbar {
            background: white;
            height: 70px;
            display: flex;
            align-items: center;
            justify-content: space-between;
            padding: 0 40px;
            border-bottom: 1px solid var(--border-color);
            position: sticky;
            top: 0;
            z-index: 1000;
        }

        .logo-section {
            font-weight: 700;
            font-size: 1.1rem;
            display: flex;
            align-items: center;
            text-decoration: none;
            color: var(--text-dark);
        }

        .logo-text { color: var(--accent-blue); margin-right: 10px; }

        .btn-home {
            display: flex;
            align-items: center;
            gap: 8px;
            padding: 8px 16px;
            border-radius: 8px;
            background-color: white;
            border: 1px solid var(--border-color);
            color: var(--text-dark);
            text-decoration: none;
            font-weight: 600;
            font-size: 14px;
            transition: all 0.2s ease;
        }

        .btn-home:hover {
            background-color: #f3f4f6;
            border-color: #d1d5db;
        }

        .main-content { padding: 40px; max-width: 1400px; margin: 0 auto; }

        .data-card {
            background: pink;
            border-radius: 12px;
            border: 1px solid var(--border-color);
            box-shadow: 0 1px 3px rgba(0,0,0,0.05);
            overflow: hidden;
        }

        .card-header-custom {
            padding: 24px;
            background: purple;
            border-bottom: 1px solid var(--border-color);
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .role-badge {
            display: inline-block;
            padding: 4px 12px;
            background-color: #eff6ff;
            color: #1e40af;
            border-radius: 20px;
            font-size: 12px;
            font-weight: 500;
        }

        .btn-action { font-size: 13px; font-weight: 600; border-radius: 6px; padding: 6px 12px; background-color : red; color : black; border-color : yellow}

        .logout-form { margin: 0; }

        .btn-logout-nav {
            background: none;
            border: none;
            color: #ef4444;
            font-weight: 600;
            font-size: 14px;
            cursor: pointer;
            padding: 8px 16px;
        }
    </style>
</head>
<body>

    <header class="top-navbar">

        <div class="d-flex align-items-center gap-3">
            <a href="${pageContext.request.contextPath}/" class="btn-home">
                Return to Dashboard
            </a>

            <form action="${pageContext.request.contextPath}/logout" method="post" class="logout-form">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <button type="submit" class="btn-logout-nav">Sign Out</button>
            </form>
        </div>
    </header>

    <main class="main-content">
        <div class="data-card">
            <div class="card-header-custom">
                <h4 class="m-0 font-weight-bold">User Management</h4>
                <a href="${pageContext.request.contextPath}/register" class="btn btn-primary btn-action">
                    + Add New Staff
                </a>
            </div>

            <div class="table-responsive">
                <table class="table table-hover m-0">
                    <thead>
                        <tr class="table-light">
                            <th class="ps-4">Email Address</th>
                            <th>Name</th>
                            <th>Phone</th>
                            <th>Assigned Roles</th>
                            <th class="text-end pe-4">Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="user" items="${users}">
                            <tr>
                                <td class="ps-4">${user.username}</td>
                                <td><strong>${user.name}</strong></td>
                                <td>${user.phoneNo}</td>
                                <td><span class="role-badge">${user.roles}</span></td>
                                <td class="text-end pe-4">
                                    <div class="d-flex justify-content-end gap-2">
                                        <a class="btn btn-outline-primary btn-action"
                                           href="${pageContext.request.contextPath}/user/get?username=${user.username}">
                                            Edit
                                        </a>
                                        <a class="btn btn-outline-danger btn-action"
                                           href="${pageContext.request.contextPath}/user/delete?username=${user.username}"
                                           onclick="return confirm('Delete this staff record?');">
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
    </main>

</body>
</html>