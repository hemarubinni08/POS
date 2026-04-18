<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Home</title>

<!-- Bootstrap CSS -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css">

<style>
body {
    overflow-x: hidden;
    background-color: #EDE9E6;
}

.sidebar {
    height: 100vh;
    position: fixed;
    top: 0;
    left: 0;
    width: 220px;
    background-color: #343a40;
    padding-top: 60px;
    display: flex;
    flex-direction: column;
}

.sidebar a {
    color: #ddd;
    padding: 10px 20px;
    display: block;
    text-decoration: none;
}

.sidebar a:hover {
    background-color: #495057;
    color: #fff;
}

.content {
    margin-left: 220px;
    padding: 20px;
}

.sidebar-links {
    flex: 1; /* pushes logout to bottom */
}

.logout-container {
    padding: 15px 20px;
    border-top: 1px solid #495057;
}

.logout-btn {
    width: 100%;
    border-radius: 20px;
    font-weight: 600;
}

</style>

</head>

<body>


<div class="sidebar">
    <div class="sidebar-links">
        <c:forEach var="node" items="${nodes}">
            <a href="${node.path}">${node.identifier}</a>
        </c:forEach>
    </div>

    <!-- Logout at bottom -->
    <div class="logout-container">
        <form action="/logout" method="post">
            <button class="btn btn-danger logout-btn" type="submit">
                Logout
            </button>
        </form>
    </div>
</div>

<div class="content">

    <div class="container-fluid">
        <div class="card shadow-sm">
            <div class="card-body rounded-pill">
                <h4 class="card-title">Welcome to the dashboard</h4>
                <p class="card-text">
                    You can manage your application features from here. Use the sidebar to navigate through different sections.
                </p>
            </div>
        </div>
    </div>
</div>
</body>
</html>