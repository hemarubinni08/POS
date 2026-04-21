<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Home</title>

    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600&display=swap"
          rel="stylesheet"/>

    <style>
        body {
            margin: 0;
            font-family: 'Poppins', sans-serif;
            min-height: 100vh;

            /* NEW BACKGROUND */
            background: #eef2f7;

            display: flex;
        }

        /* Sidebar */
        .sidebar {
            width: 260px;

            /* NEW SIDEBAR COLOR */
            background: #1f2937;

            padding: 20px;
            color: #fff;
            display: flex;
            flex-direction: column;
        }

        .sidebar h5 {
            margin: 0;
            font-weight: 600;
            color: #ffffff;
        }

        /* Logout Button */
        .logout-btn {
            padding: 8px 14px;
            border: none;
            border-radius: 8px;
            background: #ef4444;
            color: #fff;
            font-weight: 600;
            cursor: pointer;
            transition: 0.25s ease;
        }

        .logout-btn:hover {
            background: #dc2626;
            transform: scale(1.05);
        }

        .sidebar a {
            color: #e5e7eb;
            text-decoration: none;
            padding: 10px 14px;
            display: block;
            border-radius: 10px;
            font-weight: 500;
            transition: 0.25s ease;
            background: rgba(255,255,255,0.08);
        }

        .sidebar a:hover {
            background: #3b82f6;
            color: #fff;
            transform: translateX(5px);
        }

        /* Content */
        .content-area {
            flex: 1;
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 30px;
        }

        .glass-card {
            width: 520px;
            padding: 30px;
            border-radius: 18px;

            background: #ffffff;
            border: 1px solid #e5e7eb;
            box-shadow: 0 12px 35px rgba(0,0,0,0.1);

            color: #333;
            text-align: center;
        }

        .glass-card h4 {
            margin-bottom: 10px;
            font-weight: 600;
            color: #111827;
        }

        .glass-card p {
            color: #6b7280;
            font-size: 14px;
        }

        .alert {
            padding: 10px;
            border-radius: 8px;
            font-size: 13px;
            margin-bottom: 15px;
        }

        .alert-light {
            background: #f3f4f6;
            color: #333;
            border: 1px solid #e5e7eb;
        }

        ul {
            padding: 0;
            margin-top: 15px;
            list-style: none;
        }

        li {
            margin-bottom: 8px;
        }

        /* Header area */
        .sidebar-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
        }
    </style>
</head>

<body>

<!-- ===== Sidebar ===== -->
<div class="sidebar">

    <div class="sidebar-header">
        <h5>POS System</h5>

        <!-- Logout Button (no symbol) -->
        <form action="${pageContext.request.contextPath}/logout" method="post">
            <button type="submit" class="logout-btn">Logout</button>
        </form>
    </div>

    <c:if test="${empty nodes}">
        <div class="alert alert-light">
            No nodes found
        </div>
    </c:if>

    <c:if test="${not empty nodes}">
        <ul>
            <c:forEach var="n" items="${nodes}">
                <li>
                    <a href="${n.path}">
                        • ${n.identifier}
                    </a>
                </li>
            </c:forEach>
        </ul>
    </c:if>

</div>

<!-- ===== Main Content ===== -->
<div class="content-area">

    <div class="glass-card">
        <h4>Welcome</h4>
        <p>Select an option from the sidebar to continue.</p>
    </div>

</div>

</body>
</html>