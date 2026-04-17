<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add Role</title>

    <style>
        :root {
            --bg1: #0f172a;
            --bg2: #1e293b;

            --card: #ffffff;
            --text: #0f172a;
            --muted: #6b7280;

            --primary: #2563eb;
            --primary-hover: #1d4ed8;

            --success: #16a34a;

            --border: #e5e7eb;

            --radius: 14px;
            --shadow: 0 20px 50px rgba(0,0,0,0.25);
        }

        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Inter', sans-serif;
        }

        body {
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 20px;

            background: linear-gradient(135deg, var(--bg1), var(--bg2));
        }

        .container {
            width: 100%;
            max-width: 460px;
        }

        .card {
            background: var(--card);
            border-radius: var(--radius);
            box-shadow: var(--shadow);
            overflow: hidden;
        }

        .card-header {
            background: linear-gradient(135deg, var(--primary), #1e40af);
            color: #fff;
            padding: 16px;
            text-align: center;
            font-size: 18px;
            font-weight: 600;
        }

        .card-body {
            padding: 18px;
        }

        .alert {
            background: #dcfce7;
            color: #166534;
            padding: 10px 12px;
            border-radius: 8px;
            font-size: 13px;
            text-align: center;
            margin-bottom: 12px;
        }

        label {
            display: block;
            font-size: 13px;
            color: var(--muted);
            margin-bottom: 6px;
            font-weight: 500;
        }

        input {
            width: 100%;
            padding: 10px 12px;
            border-radius: 8px;
            border: 1px solid var(--border);
            font-size: 14px;
            transition: 0.2s;
        }

        input:focus {
            outline: none;
            border-color: var(--primary);
            box-shadow: 0 0 0 3px rgba(37, 99, 235, 0.15);
        }

        .form-group {
            margin-bottom: 14px;
        }

        .btn {
            width: 100%;
            padding: 11px 14px;
            border: none;
            border-radius: 10px;
            font-size: 14px;
            font-weight: 600;
            cursor: pointer;
            transition: 0.2s;
        }

        .btn-primary {
            background: var(--primary);
            color: #fff;
        }

        .btn-primary:hover {
            background: var(--primary-hover);
        }

        .card-footer {
            text-align: center;
            padding: 12px;
            font-size: 12px;
            color: var(--muted);
            border-top: 1px solid #f1f5f9;
        }
    </style>
</head>

<body>

<div class="container">

    <div class="card">

        <div class="card-header">
            Add New Role
        </div>

        <div class="card-body">

            <c:if test="${not empty role}">
                <div class="alert">
                    ${role}
                </div>
            </c:if>

            <form:form method="post"
                       action="/role/add"
                       modelAttribute="roleDto">

                <div class="form-group">
                    <label>Role Name</label>
                    <form:input path="identifier" cssClass="form-control"
                                placeholder="Enter role name" />
                </div>

                <button type="submit" class="btn btn-primary">
                    Add Role
                </button>

            </form:form>

        </div>

        <div class="card-footer">
            POS Management System
        </div>

    </div>

</div>

</body>
</html>