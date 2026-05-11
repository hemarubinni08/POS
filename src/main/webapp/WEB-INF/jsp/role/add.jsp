<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add Role</title>

    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600&display=swap" rel="stylesheet">

    <style>

        body {
            margin: 0;
            font-family: 'Poppins', sans-serif;
            min-height: 100vh;
            background: #f4f6f9;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .card-container {
            position: relative;
            width: 500px;
            background: #ffffff;
            padding: 35px 40px;
            border-radius: 18px;
            box-shadow: 0 12px 35px rgba(0, 0, 0, 0.12);
            border: 1px solid #e6e6e6;
            color: #333;
        }

        h2 {
            text-align: center;
            margin-bottom: 25px;
            font-weight: 600;
            color: #222;
        }

        .back-icon {
            position: absolute;
            top: 16px;
            left: 16px;
            width: 36px;
            height: 36px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 18px;
            color: #333;
            text-decoration: none;
            font-weight: bold;
            background: #f0f0f0;
            border-radius: 50%;
            transition: all 0.25s ease;
        }

        .back-icon:hover {
            background: #333;
            color: #fff;
            transform: translateX(-3px);
        }

        .form-group {
            margin-bottom: 18px;
        }

        label {
            display: block;
            margin-bottom: 6px;
            font-weight: 500;
            color: #444;
            font-size: 13px;
        }

        .form-control {
            width: 100%;
            padding: 11px 14px;
            border-radius: 10px;
            border: 1px solid #ddd;
            font-family: 'Poppins', sans-serif;
            font-size: 14px;
            background: #fff;
            color: #333;
            transition: all 0.2s ease;
        }

        .form-control::placeholder {
            color: #999;
        }

        .form-control:focus {
            outline: none;
            border-color: #4a90e2;
            box-shadow: 0 0 0 3px rgba(74, 144, 226, 0.15);
        }

        textarea.form-control {
            resize: none;
        }

        .alert {
            padding: 12px;
            border-radius: 10px;
            margin-bottom: 15px;
            text-align: center;
            font-size: 13px;
            background: #e6f4ea;
            border: 1px solid #b7e4c7;
            color: #1b4332;
        }

        .error-message {
            padding: 12px;
            border-radius: 10px;
            margin-bottom: 15px;
            text-align: center;
            font-size: 13px;
            background: rgba(220, 53, 69, 0.12);
            border: 1px solid #dc3545;
            color: #dc3545;
        }

        .btn-submit {
            width: 100%;
            padding: 12px;
            border-radius: 10px;
            border: none;
            cursor: pointer;
            font-weight: 600;
            font-size: 15px;
            background: #4a90e2;
            color: white;
            transition: 0.3s ease;
        }

        .btn-submit:hover {
            background: #357bd8;
            transform: translateY(-1px);
            box-shadow: 0 8px 18px rgba(0,0,0,0.15);
        }

        .footer-text {
            text-align: center;
            margin-top: 15px;
            font-size: 12px;
            color: #888;
        }
    </style>
</head>

<body>

<div class="card-container">

    <a href="/role/list" class="back-icon">←</a>

    <h2>Add New Role</h2>
    <c:if test="${not empty role}">
        <div class="alert">
            ${role}
        </div>
    </c:if>

    <c:if test="${not empty message}">
        <div class="error-message">
            ${message}
        </div>
    </c:if>

    <form:form method="post"
               action="/role/add"
               modelAttribute="roleDto">

        <div class="form-group">
            <label>Role Name</label>
            <form:input path="identifier"
                        cssClass="form-control"
                        placeholder="Enter role name" />
        </div>

        <div class="form-group">
            <label>Description</label>
            <form:textarea path="description"
                           cssClass="form-control"
                           rows="3"
                           placeholder="Enter role description" />
        </div>

        <button type="submit" class="btn-submit">
            Add Role
        </button>

    </form:form>

    <div class="footer-text">
        POS Management System
    </div>

</div>

</body>
</html>