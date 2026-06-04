<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<title>Add Model Product</title>

<style>
body {
    font-family:'Poppins',sans-serif;
    background:linear-gradient(135deg,#1a1b26,#2a2b3d);
    display:flex;
    justify-content:center;
    align-items:center;
    height:100vh;
}
.card {
    width:420px;
    padding:30px;
    border-radius:15px;
    background:rgba(255,255,255,0.05);
    backdrop-filter:blur(12px);
}
h2 { color:#fff;text-align:center;margin-bottom:20px; }
label { color:#ccc;font-size:13px; }

input,select,textarea {
    width:100%;
    padding:10px;
    margin:8px 0 14px;
    border:none;
    border-radius:8px;
    background:rgba(255,255,255,0.1);
    color:#fff;
}

input:focus,select:focus,textarea:focus {
    border:1px solid #00ffff;
    box-shadow:0 0 8px #00ffff;
}

button {
    width:100%;
    padding:12px;
    border:none;
    border-radius:8px;
    background:#00ffff;
    font-weight:bold;
}
</style>
</head>

<body>

<div class="card">

<h2>Add Model Product</h2>

<form:form method="post" action="/modelproduct/add" modelAttribute="modelProductDto">

<label>Model Name</label>
<form:input path="identifier"/>

<label>Description</label>
<form:textarea path="description" rows="3"/>

<label>Status</label>
<form:select path="status">
    <form:option value="true">Active</form:option>
    <form:option value="false">Inactive</form:option>
</form:select>

<button type="submit">Save</button>

</form:form>

</div>

</body>
</html>