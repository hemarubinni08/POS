<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<title>Model Product List</title>

<style>
body {
    background:linear-gradient(135deg,#1a1b26,#2a2b3d);
    font-family:Poppins;
    display:flex;
    justify-content:center;
}
.container {
    width:1000px;
    margin-top:40px;
    padding:25px;
    border-radius:15px;
    background:rgba(255,255,255,0.05);
}
h2 {color:#fff;text-align:center;}

table {width:100%;border-collapse:collapse;}
th {background:#00ffff;color:#000;padding:10px;}
td {color:#ddd;padding:10px;text-align:center;}

.btn {
    padding:6px 10px;
    border-radius:6px;
    text-decoration:none;
}

.update {background:#ffc107;color:#000;}
.delete {background:#ff4d4d;color:#fff;}
.add {background:#00ffff;color:#000;padding:10px;margin-top:15px;display:inline-block;}
</style>
</head>

<body>

<div class="container">

<h2>Model Product List</h2>

<table>

<tr>
<th>Name</th>
<th>Description</th>
<th>Status</th>
<th>Action</th>
</tr>

<c:forEach var="m" items="${modelProducts}">
<tr>

<td>${m.identifier}</td>
<td>${m.description}</td>
<td>${m.status}</td>

<td>
<a class="btn update" href="/modelproduct/get?identifier=${m.identifier}">Update</a>
<a class="btn delete" href="/modelproduct/delete?identifier=${m.identifier}">Delete</a>
</td>

</tr>
</c:forEach>

</table>

<a href="/modelproduct/add" class="add">+ Add Model</a>

</div>

</body>
</html>