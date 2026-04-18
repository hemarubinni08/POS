<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Home</title>

    <!-- Bootstrap CSS -->
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css">

    <style>
        body {
            margin: 0;
            font-family: "Segoe UI", Arial, sans-serif;
            background-color: #FFF8F0;
            display: flex;
            height: 100vh;
        }

        .sidebar {
            position: fixed;
            top: 0;
            left: 0;
            height: 100vh;
            width: 230px;
            background: #4B2E2B;
            padding-top: 40px;
            box-shadow: 2px 0 12px rgba(75, 46, 43, 0.35);
        }

        .sidebar a {
            display: block;
            padding: 14px 24px;
            color: #FFF8F0;
            text-decoration: none;
            font-size: 15px;
            font-weight: 800;
            transition: all 0.3s ease;
            border-left: 4px solid transparent;
        }

        .sidebar a:hover {
            background-color: rgba(255, 248, 240, 0.15);
            border-left: 4px solid #FFF8F0;
            padding-left: 20px;
        }

        .content {
            margin-left: 230px;
            padding: 40px;
            width: 100%;
        }

        .dashboard-card {
            background-color: #ffffff;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 10px 25px rgba(75, 46, 43, 0.25);
            max-width: 720px;
        }

        .dashboard-card h1 {
            margin: 0;
            color: #4B2E2B;
            font-weight: 600;
        }

        .dashboard-card p {
            margin-top: 12px;
            font-size: 16px;
            color: #6b4a46;
        }

        .logout-btn {
            position: absolute;
            bottom: 25px;
            left: 20px;
            right: 20px;
            text-align: center;
            padding: 12px;
            background-color: #8d3c36;
            color: #ffffff !important;
            border-radius: 8px;
            font-weight: 600;
            text-decoration: none;
            transition: all 0.3s ease;
        }

        .logout-btn:hover {
            background-color: #702f2a;
            transform: translateY(-2px);
            box-shadow: 0 6px 14px rgba(75, 46, 43, 0.4);
        }
    </style>
</head>

<body>

<div class="sidebar">
    <c:forEach items="${nodes}" var="nav">
        <a href="${nav.path}">
            ${nav.identifier}
        </a>
    </c:forEach>

    <a href="/login?logout" class="logout-btn">
        Logout
    </a>
</div>

<div class="content">
    <div class="dashboard-card">
        <h1>Home</h1>
        <p>Welcome to the dashboard.</p>
    </div>
</div>

</body>
</html>
