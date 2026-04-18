<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Add Role</title>

    <style>
        body {
            margin: 0;
            min-height: 100vh;
            font-family: Arial, sans-serif;
            background: #ffffff;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .container {
            width: 380px;
            background: #ffffff;
            padding: 30px 35px;
            border-radius: 12px;
            box-shadow: 0 20px 40px rgba(0, 0, 0, 0.25);
        }

        h2 {
            text-align: center;
            margin-bottom: 25px;
            color: #4b6cb7;
        }

        label {
            display: block;
            margin-bottom: 6px;
            font-size: 13px;
            font-weight: 600;
            color: #333;
        }

        input {
            width: 100%;
            padding: 10px 12px;
            margin-bottom: 15px;
            border-radius: 8px;
            border: 1px solid #ccc;
            font-size: 14px;
        }

        input:focus {
            outline: none;
            border-color: #667eea;
            box-shadow: 0 0 5px rgba(102, 126, 234, 0.4);
        }

        button {
            width: 100%;
            padding: 11px;
            background: linear-gradient(135deg, #4b6cb7, #182848);
            color: white;
            border: none;
            border-radius: 10px;
            font-size: 15px;
            font-weight: 600;
            cursor: pointer;
        }

        button:hover {
            opacity: 0.9;
        }

        /* ✅ Success Message */
        .success-message {
            margin-bottom: 15px;
            padding: 10px;
            background: rgba(40, 167, 69, 0.12);
            border: 1px solid #28a745;
            color: #28a745;
            border-radius: 8px;
            font-size: 13px;
            text-align: center;
            font-weight: 500;
        }
    </style>
</head>

<body>

<div class="container">

    <h2>Add New Role</h2>
        <!-- ✅ MESSAGE DISPLAY -->
        <c:if test="${not empty message}">
            <div class="error-message">
                ${message}
            </div>
        </c:if>

    <!-- ✅ Success Message -->
    <c:if test="${not empty role}">
        <div class="success-message">
            ${role}
        </div>
    </c:if>

    <!-- ✅ Spring Form -->
    <form:form method="post"
               action="/role/add"
               modelAttribute="roleDto">

        <label>Role Name</label>
        <form:input path="identifier"/>

        <label>Description</label>
        <form:input path="description"/>

        <button type="submit">Add Role</button>
        <a href="/node/list"
        class="btn btn-secondary">Cancel
        </a>

    </form:form>

</div>

</body>
</html>