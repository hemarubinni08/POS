<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Edit Role</title>

    <style>
        :root {
            --bg1: #0f172a;
            --bg2: #1e293b;

            --card: #ffffff;
            --text: #0f172a;
            --muted: #6b7280;

            --primary: #2563eb;
            --primary-hover: #1d4ed8;

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
            background: linear-gradient(135deg, var(--bg1), var(--bg2));
        }

        .card {
            width: 420px;
            background: var(--card);
            border-radius: var(--radius);
            box-shadow: var(--shadow);
            padding: 26px;
        }

        h4 {
            text-align: center;
            font-size: 18px;
            font-weight: 600;
            margin-bottom: 18px;
            color: var(--text);
        }

        .form-group {
            margin-bottom: 14px;
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

        input[readonly] {
            background: #f3f4f6;
            cursor: not-allowed;
        }

        .help-text {
            font-size: 11px;
            color: var(--muted);
            margin-top: 6px;
            line-height: 1.4;
        }

        .btn-group {
            display: flex;
            justify-content: space-between;
            margin-top: 16px;
            gap: 10px;
        }

        .btn {
            padding: 10px 12px;
            border-radius: 10px;
            font-size: 14px;
            font-weight: 600;
            cursor: pointer;
            text-decoration: none;
            text-align: center;
            border: none;
            transition: 0.2s;
            width: 100%;
        }

        .btn-secondary {
            background: #e5e7eb;
            color: #111827;
        }

        .btn-secondary:hover {
            background: #d1d5db;
        }

        .btn-primary {
            background: var(--primary);
            color: #fff;
        }

        .btn-primary:hover {
            background: var(--primary-hover);
        }

        .alert {
            background: #fee2e2;
            color: #991b1b;
            padding: 10px 12px;
            border-radius: 8px;
            font-size: 13px;
            text-align: center;
            margin-bottom: 12px;
        }
    </style>
</head>

<body>

<div class="card">

    <h4>Edit Role</h4>

    <c:if test="${empty role}">
        <div class="alert">Role not found</div>
    </c:if>

    <c:if test="${not empty role}">

        <form:form action="/role/update" method="post" modelAttribute="role">

            <form:hidden path="id"/>

            <div class="form-group">
                <label>Role Name</label>

                <form:input path="identifier" readonly="true"/>

                <div class="help-text">
                    This role name is a system identifier and cannot be edited as it is used for authorization and access control.
                </div>
            </div>

            <div class="btn-group">
                <a href="/role/list" class="btn btn-secondary">Cancel</a>
                <button type="submit" class="btn btn-primary">Update</button>
            </div>

        </form:form>

    </c:if>

</div>

</body>
</html>