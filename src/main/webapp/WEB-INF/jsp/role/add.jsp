<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add Role</title>

    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600&display=swap" rel="stylesheet">

    <style>
        body {
            margin: 0;
            font-family: 'Inter', sans-serif;
            background: #d1d5db;
        }

        /* 🎯 SAME CONTAINER AS NODE */
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
        input {
            width: 100%;
            margin-top: 6px;
            padding: 10px;
            border: 1px solid #cbd5f5;
            border-radius: 8px;
            font-size: 13px;
            outline: none;
            transition: 0.2s;
        }

        input:focus {
            border-color: #0891b2;
            box-shadow: 0 0 0 2px rgba(8,145,178,0.2);
        }

        /* 🔥 BUTTON */
        button {
            margin-top: 28px;
            width: 100%;
            padding: 12px;
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

        /* ✅ SUCCESS MESSAGE */
        .alert {
            margin-bottom: 15px;
            padding: 10px;
            border-radius: 8px;
            text-align: center;
            font-size: 13px;
            background: rgba(34,197,94,0.1);
            color: #166534;
        }

        /* 🔙 BACK LINK */
        a {
            display: block;
            text-align: center;
            margin-top: 20px;
            color: #0891b2;
            font-weight: 600;
            text-decoration: none;
            font-size: 13px;
            transition: 0.2s;
        }

        a:hover {
            color: #0e7490;
        }
    </style>
</head>

<body>

<div class="container">

    <h2>Add Role</h2>

    <!-- ✅ SUCCESS MESSAGE -->
    <c:if test="${not empty role}">
        <div class="alert">
            ${role}
        </div>
    </c:if>

    <form:form method="post"
               action="/role/add"
               modelAttribute="roleDto">

        <label>Role Name</label>
        <form:input path="identifier"
                    placeholder="Enter role name"/>

        <label>Description</label>
        <form:input path="description"
                    placeholder="Description"/>

        <button type="submit">Add Role</button>

    </form:form>

    <a href="${pageContext.request.contextPath}/role/list">
        Back to Role List
    </a>

</div>

</body>
</html>