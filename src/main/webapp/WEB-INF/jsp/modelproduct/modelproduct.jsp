<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<title>Model Product List</title>

<style>
* {
    margin:0;
    padding:0;
    box-sizing:border-box;
    font-family:'Poppins',sans-serif;
}

body {
    min-height:100vh;
    background:linear-gradient(135deg,#1a1b26,#2a2b3d);
    display:flex;
    justify-content:center;
    align-items:center;
}

.container {
    width:1000px;
    padding:30px;
    border-radius:15px;
    background:rgba(255,255,255,0.05);
    backdrop-filter:blur(12px);
}

h2 {
    text-align:center;
    color:#fff;
    margin-bottom:25px;
    font-size:28px;
}

table {
    width:100%;
    border-collapse:collapse;
}

th {
    background:#2f4f59;
    color:#00ffff;
    padding:14px;
    font-size:16px;
}

td {
    padding:14px;
    text-align:center;
    color:#ddd;
}

.switch {
    position:relative;
    width:60px;
    height:30px;
    display:inline-block;
}

.switch input {
    display:none;
}

.slider {
    position:absolute;
    inset:0;
    background:#ff4d4d;
    border-radius:30px;
    transition:0.3s;
}

.slider:before {
    content:"";
    position:absolute;
    width:24px;
    height:24px;
    left:3px;
    bottom:3px;
    background:white;
    border-radius:50%;
    transition:0.3s;
}

input:checked + .slider {
    background:#00ff99;
}

input:checked + .slider:before {
    transform:translateX(28px);
}

.btn {
    padding:8px 14px;
    border-radius:8px;
    text-decoration:none;
    font-size:13px;
    margin:3px;
}

.update {
    background:#ffc107;
    color:#000;
}

.delete {
    background:#ff4d4d;
    color:#fff;
}

.footer {
    margin-top:25px;
    display:flex;
    justify-content:center;
    gap:20px;
}

.home-btn {
    background:#777;
    color:#fff;
    padding:12px 20px;
    border-radius:10px;
    text-decoration:none;
    font-weight:bold;
}

.add-btn {
    background:#00ffff;
    color:#000;
    padding:12px 20px;
    border-radius:10px;
    text-decoration:none;
    font-weight:bold;
}

.add-btn:hover {
    box-shadow:0 0 15px #00ffff;
}
</style>
</head>

<body>

<div class="container">

<h2>Model Product List</h2>

<table>

<tr>
<th>SL</th>
<th>Model</th>
<th>Description</th>
<th>Status</th>
<th>Action</th>
</tr>

<c:forEach var="m" items="${modelProducts}" varStatus="i">

<tr>

<td>${i.index + 1}</td>
<td>${m.identifier}</td>
<td>${m.description}</td>

<td>
<form action="${pageContext.request.contextPath}/modelproduct/toggleStatus"
      method="post">

<input type="hidden" name="identifier" value="${m.identifier}"/>

<label class="switch">
<input type="checkbox"
       <c:if test="${m.status == true}">checked</c:if>
       onchange="this.form.submit()">
<span class="slider"></span>
</label>

</form>
</td>

<td>

<a class="btn update"
   href="${pageContext.request.contextPath}/modelproduct/get?identifier=${m.identifier}">
   Update
</a>

<a class="btn delete"
   href="${pageContext.request.contextPath}/modelproduct/delete?identifier=${m.identifier}">
   Delete
</a>

</td>

</tr>

</c:forEach>

</table>

<div class="footer">

<a href="${pageContext.request.contextPath}/" class="home-btn">
Home
</a>

<a href="${pageContext.request.contextPath}/modelproduct/add" class="add-btn">
+ Add Model
</a>

</div>

</div>

</body>
</html>