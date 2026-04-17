<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Dashboard</title>

    <style>
        body {
            margin: 0;
            font-family: Arial, sans-serif;
        }

        /* Main container */
        .layout {
            display: flex;
            height: 100vh;
        }

        /* Sidebar */
        .sidebar {
            width: 220px;
            background-color: #2c3e50;
            color: white;
            padding-top: 20px;
        }

        .sidebar a {
            display: block;
            color: white;
            padding: 12px 20px;
            text-decoration: none;
            font-size: 14px;
        }

        .sidebar a:hover {
            background-color: #34495e;
        }

        /* Right content (kept empty or minimal) */
        .content {
            flex: 1;
            background-color: #f4f6f8;
            padding: 20px;
        }

        /* Optional title */
        .sidebar h3 {
            text-align: center;
            margin-bottom: 20px;
            font-size: 18px;
        }
    </style>
</head>

<body>

<div class="layout">

    <!-- ✅ LEFT SIDEBAR -->
    <div class="sidebar">
        <h3>Menu</h3>

        <!-- Put ALL links here -->
        <%--<a href="/user/registeruserList">Users</a>
        <a href="/role/allRoles">Roles</a>
        <a href="/node/nodeList">Nodes</a>
        <a href="/logout">Logout</a>--%>
        <c:forEach var="node" items="${nodes}">
            <a href="${node.path}"> ${node.identifier}</a>
        </c:forEach>

    </div>

    <!-- ✅ RIGHT SIDE (EMPTY / OPTIONAL CONTENT) -->
    <div class="content">
        <!-- Keep empty or show a welcome message -->
        <h2>Welcome</h2>
        <p>Select an option from the left sidebar.</p>
    </div>

</div>

</body>
</html>


