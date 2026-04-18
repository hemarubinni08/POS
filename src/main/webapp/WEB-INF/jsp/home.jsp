<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Home</title>

    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600&display=swap"
          rel="stylesheet">

    <style>
        body {
            margin: 0;
            font-family: 'Poppins', sans-serif;
            background-color: #f4f6fb;
        }

        /* SIDEBAR (MATCH IMAGE STYLE) */
        .sidebar {
            position: fixed;
            top: 0;
            left: 0;
            width: 250px;
            height: 100vh;
            background: linear-gradient(180deg, #4e73df, #224abe);
            padding-top: 20px;
            box-shadow: 3px 0 15px rgba(0,0,0,0.1);
        }

        .sidebar h4 {
            color: #ffffff;
            text-align: center;
            margin-bottom: 30px;
            font-weight: 600;
            font-size: 20px;
        }

        .sidebar a {
            display: block;
            padding: 12px 25px;
            color: rgba(255,255,255,0.8);
            text-decoration: none;
            font-size: 14px;
            transition: all 0.3s ease;
        }

        .sidebar a:hover {
            background: rgba(255,255,255,0.15);
            color: #ffffff;
            padding-left: 32px;
        }

        .sidebar a.active {
            background: rgba(255,255,255,0.2);
            color: #fff;
            font-weight: 500;
        }

        /* LOGOUT BUTTON */
        .logout-btn {
            position: absolute;
            bottom: 20px;
            width: 100%;
            padding: 0 20px;
        }

     .logout-btn button {
         width: 100%;
         border-radius: 8px;
         font-size: 13px;
         padding: 8px;
         background: #ffffff;
         color: #4e73df;
         border: none;
         font-weight: 500;
         transition: all 0.3s ease;
     }

     .logout-btn button:hover {
         background: #e6ecff;
         color: #224abe;
     }

        /* CONTENT AREA */
        .content {
            margin-left: 250px;
            padding: 30px;
        }

        /* CARD STYLE LIKE IMAGE */
        .welcome-card {
            background: #ffffff;
            border-radius: 10px;
            padding: 35px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.06);
        }

        .welcome-card h1 {
            font-size: 26px;
            font-weight: 600;
            color: #333;
        }

        .welcome-card p {
            color: #777;
            font-size: 14px;
            margin-top: 10px;
        }
    </style>
</head>

<body>
<div class="sidebar">
    <h4>POS System</h4>
    <c:if test="${not empty nodes}">
        <c:forEach items="${nodes}" var="nav">
            <a href="${pageContext.request.contextPath}${nav.path}">
                ${nav.identifier}
            </a>
        </c:forEach>
    </c:if>

    <c:if test="${empty nodes}">
        <p style="color:#ccc;text-align:center;">No access</p>
    </c:if>
    <div class="logout-btn">
        <form action="${pageContext.request.contextPath}/logout" method="post">
            <button type="submit" class="btn btn-danger btn-sm">
                Logout
            </button>
        </form>
    </div>
</div>

<div class="content">
    <div class="welcome-card">
        <h1>Welcome</h1>
        <p>
            Select a module from the sidebar to continue managing your application.
        </p>
    </div>
</div>
</body>
</html>