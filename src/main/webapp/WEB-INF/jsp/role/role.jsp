<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<title>Edit Role</title>

<link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600&display=swap" rel="stylesheet">

<style>
body {
    margin: 0;
    font-family: 'Inter', sans-serif;
    background: #d1d5db;
}

/* 🎯 CONTAINER */
.container {
    width: 420px;
    margin: 100px auto;
    background: #f1f5f9;
    padding: 35px;
    border-radius: 16px;
    box-shadow: 0 10px 25px rgba(0,0,0,0.1);
}

/* 🔷 TITLE */
h2 {
    text-align: center;
    margin-bottom: 25px;
    font-size: 22px;
    color: #0891b2;
    font-weight: 600;
}

/* 🏷 LABEL */
label {
    margin-top: 16px;
    display: block;
    font-weight: 600;
    font-size: 13px;
    color: #334155;
}

/* ✏ INPUT */
.form-control {
    width: 100%;
    margin-top: 6px;
    padding: 10px;
    border: 1px solid #cbd5f5;
    border-radius: 8px;
    font-size: 13px;
    outline: none;
    transition: 0.2s;
}

.form-control:focus {
    border-color: #0891b2;
    box-shadow: 0 0 0 2px rgba(8,145,178,0.2);
}

/* 🔥 UPDATE BUTTON */
button {
    padding: 12px 26px;
    background: linear-gradient(135deg, #0891b2, #0e7490);
    color: #ffffff;
    border: none;
    font-weight: 600;
    border-radius: 20px;
    cursor: pointer;
    transition: 0.25s;
}

button:hover {
    background: linear-gradient(135deg, #0e7490, #075985);
    transform: translateY(-2px);
    box-shadow: 0 6px 15px rgba(8,145,178,0.4);
}

/* ❌ FIXED CANCEL BUTTON */
.cancel-btn {
    padding: 12px 26px;
    border-radius: 20px;
    border: 1px solid #cbd5f5;
    color: #334155;
    text-decoration: none;
    font-size: 14px;
    font-weight: 600;
    background: #ffffff;
    transition: 0.25s;
    display: inline-block;
}

.cancel-btn:hover {
    background: #e2e8f0;
}

/* BUTTON ROW FIX */
.btn-row {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-top: 25px;
}

/* ⚠ ERROR */
.error {
    background: #fee2e2;
    color: #b91c1c;
    padding: 10px;
    border-radius: 8px;
    text-align: center;
    font-size: 13px;
    margin-bottom: 15px;
}

/* ℹ HELP TEXT */
.help-text {
    font-size: 12px;
    color: #6b7280;
    margin-top: 6px;
}
</style>
</head>

<body>

<div class="container">

<h2>Edit Role</h2>

<c:if test="${empty role}">
    <div class="error">
        Role not found
    </div>
</c:if>

<c:if test="${not empty role}">

<form:form action="/role/update"
           method="post"
           modelAttribute="role">

    <!-- Hidden ID -->
    <form:hidden path="id"/>

    <!-- Role Name -->
    <label>Role Name</label>

    <form:input path="identifier"
                cssClass="form-control"
                readonly="true"/>

    <div class="help-text">
        This role name is a system identifier and cannot be edited,
        as it is used internally for authorization and access control.
    </div>

    <!-- BUTTONS -->
    <div class="btn-row">
        <a href="/role/list" class="cancel-btn">Cancel</a>
        <button type="submit">Update</button>
    </div>

</form:form>

</c:if>

</div>

</body>
</html>