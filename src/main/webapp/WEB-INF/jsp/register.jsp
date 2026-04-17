<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html>
<html>
<head>
<title>User Registration</title>

<link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">

<style>
body {
margin: 0;
font-family: Inter, sans-serif;
background: linear-gradient(135deg, #eef2ff, #f8fafc);
height: 100vh;
display: flex;
justify-content: center;
align-items: center;
}

/* CARD */
.register-card {
width: 850px;
background: rgba(255,255,255,0.92);
backdrop-filter: blur(10px);
padding: 30px;
border-radius: 20px;
box-shadow: 0 20px 50px rgba(0,0,0,0.08);
}

/* TITLE */
h2 {
text-align: center;
margin-bottom: 20px;
font-size: 24px;
font-weight: 700;
color: #111827;
}

/* GRID LAYOUT */
.form-grid {
display: grid;
grid-template-columns: 1fr 1fr;
gap: 18px 25px;
}

/* FULL WIDTH FIELD */
.full {
grid-column: span 2;
}

/* FORM GROUP */
.form-group label {
font-size: 13px;
font-weight: 600;
color: #374151;
margin-bottom: 6px;
display: block;
}

input, select {
width: 100%;
padding: 10px 12px;
border-radius: 10px;
border: 1px solid #e5e7eb;
font-size: 14px;
outline: none;
transition: 0.2s;
}

input:focus, select:focus {
border-color: #2563eb;
box-shadow: 0 0 0 3px rgba(37,99,235,0.15);
}

/* MULTI SELECT */
select[multiple] {
height: 110px;
}

/* BUTTON */
.btn-submit {
margin-top: 20px;
width: 100%;
padding: 12px;
background: linear-gradient(135deg, #2563eb, #1e40af);
color: white;
border: none;
border-radius: 12px;
font-size: 15px;
font-weight: 600;
cursor: pointer;
transition: 0.2s;
}

.btn-submit:hover {
transform: translateY(-2px);
box-shadow: 0 10px 25px rgba(37,99,235,0.25);
}
</style>
</head>

<body>

<div class="register-card">

<h2>Create Account</h2>

<form:form action="register" method="post" modelAttribute="userDto">

<div class="form-grid">

<!-- Name -->
<div class="form-group">
<label>Name</label>
<form:input path="name"/>
</div>

<!-- Email -->
<div class="form-group">
<label>Email</label>
<form:input path="username"/>
</div>

<!-- Phone -->
<div class="form-group">
<label>Phone Number</label>
<form:input path="phoneNo"/>
</div>

<!-- Password -->
<div class="form-group">
<label>Password</label>
<form:password path="password"/>
</div>

<!-- Roles (full width) -->
<div class="form-group full">
<label>Roles</label>
<form:select path="roles" multiple="true">
<form:options items="${roles}" itemValue="identifier" itemLabel="identifier"/>
</form:select>
</div>

</div>

<input type="submit" value="Register" class="btn-submit"/>

</form:form>

</div>

</body>
</html>