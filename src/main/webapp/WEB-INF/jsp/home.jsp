<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<title>Dashboard</title>

<link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">

<style>
body{
margin:0;
font-family:Inter,sans-serif;
background:linear-gradient(135deg,#eef2ff,#f8fafc);
color:#1f2937;
}

/* SIDEBAR */
.sidebar{
position:fixed;
left:0;
top:0;
width:250px;
height:100vh;
background:#ffffff;
border-right:1px solid #e5e7eb;
box-shadow:2px 0 20px rgba(0,0,0,0.05);
}

.sidebar-header{
padding:18px;
font-weight:700;
color:#2563eb;
border-bottom:1px solid #eee;
}

.sidebar a{
display:block;
padding:12px 18px;
margin:6px 10px;
text-decoration:none;
color:#374151;
border-radius:8px;
transition:0.2s;
}

.sidebar a:hover{
background:#eef2ff;
color:#2563eb;
transform:translateX(4px);
}

/* CONTENT WRAPPER */
.content{
margin-left:250px;
padding:40px;
display:flex;
align-items:center;
justify-content:center;
min-height:100vh;
position:relative;
}

/* LOGOUT BUTTON */
.logout-btn{
position:absolute;
top:25px;
right:30px;
padding:10px 16px;
background:#ffffff;
border:1px solid #e5e7eb;
border-radius:10px;
font-size:13px;
font-weight:600;
color:#374151;
text-decoration:none;
box-shadow:0 4px 12px rgba(0,0,0,0.05);
transition:0.2s;
}

.logout-btn:hover{
background:#f3f4f6;
color:#2563eb;
transform:translateY(-2px);
border-color:#2563eb;
}

/* WELCOME CARD */
.welcome-card{
background:rgba(255,255,255,0.9);
backdrop-filter:blur(10px);
border-radius:20px;
padding:50px 60px;
text-align:center;
box-shadow:0 20px 50px rgba(0,0,0,0.08);
max-width:600px;
animation:fadeIn 0.6s ease-in-out;
}

.welcome-card h1{
font-size:34px;
margin-bottom:10px;
color:#111827;
}

.welcome-card p{
font-size:16px;
color:#6b7280;
margin-bottom:20px;
}

.highlight{
color:#2563eb;
font-weight:600;
}

.badge{
display:inline-block;
margin-top:10px;
padding:6px 14px;
border-radius:999px;
background:#dbeafe;
color:#1e3a8a;
font-size:13px;
}

@keyframes fadeIn{
from{opacity:0; transform:translateY(10px);}
to{opacity:1; transform:translateY(0);}
}
</style>
</head>

<body>

<!-- SIDEBAR -->
<div class="sidebar">
<div class="sidebar-header">User Management System</div>

<c:forEach var="node" items="${nodes}">
<a href="${pageContext.request.contextPath}${node.path}">
${node.name}
</a>
</c:forEach>
</div>

<!-- CONTENT -->
<div class="content">

<!-- LOGOUT -->
<a class="logout-btn"
   href="${pageContext.request.contextPath}/login">
Logout
</a>

<!-- WELCOME CARD -->
<div class="welcome-card">

<h1>Welcome to Dashboard</h1>

<p>
Hello, <span class="highlight">${sessionScope.userName}</span>
Welcome back! You are now inside the User Management System.
</p>

<div class="badge">System Ready</div>

</div>

</div>

</body>
</html>