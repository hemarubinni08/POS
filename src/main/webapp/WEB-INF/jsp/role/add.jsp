<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add Role</title>

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        :root {
            --bg: #f6fff8;
            --card: #ffffff;

            --text: #1f2937;
            --muted: #6b7280;

            --primary: #28a745;
            --primary-hover: #218838;

            --accent: #ffc107;

            --success: #16a34a;

            --border: #e5e7eb;

            --radius: 14px;
            --shadow: 0 10px 30px rgba(0,0,0,0.08);
        }

        * {
            font-family: 'Inter', sans-serif;
            box-sizing: border-box;
        }

        body {
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 20px;
            background: var(--bg);
        }

        .container-box {
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
            background: var(--primary);
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
            padding: 10px;
            border-radius: 8px;
            text-align: center;
            margin-bottom: 12px;
        }

        label {
            font-size: 13px;
            color: var(--muted);
            margin-bottom: 6px;
        }

        .form-control {
            border-radius: 10px;
            padding: 10px;
            border: 1px solid var(--border);
        }

        .form-control:focus {
            border-color: var(--primary);
            box-shadow: 0 0 0 3px rgba(40,167,69,0.15);
        }

        .btn-primary {
            width: 100%;
            padding: 10px;
            background: var(--primary);
            border: none;
            border-radius: 10px;
            font-weight: 600;
        }

        .btn-primary:hover {
            background: var(--primary-hover);
        }

        .btn-back {
            width: 100%;
            padding: 10px;
            background: var(--accent);
            color: black;
            border-radius: 10px;
            font-weight: 600;
            text-align: center;
            display: block;
            margin-top: 10px;
            text-decoration: none;
        }

        .btn-back:hover {
            background: #e0a800;
            color: black;
        }

        .card-footer {
            text-align: center;
            padding: 12px;
            font-size: 12px;
            color: var(--muted);
            border-top: 1px solid var(--border);
        }
    </style>

    <script>
        function validateRole() {
            let role = document.getElementsByName("identifier")[0].value.trim();

            if (role === "") {
                alert("Role name is required!");
                return false;
            }

            return true;
        }
    </script>
</head>

<body>

<div class="container-box">

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
                       action="${pageContext.request.contextPath}/role/add"
                       modelAttribute="roleDto"
                       onsubmit="return validateRole()">

                <div class="mb-3">
                    <label>Role Name</label>
                    <form:input path="identifier"
                                cssClass="form-control"
                                placeholder="Enter role name"
                                required="true"/>
                </div>

                <button type="submit" class="btn btn-primary">
                    Add Role
                </button>

            </form:form>

            <!-- BACK BUTTON -->
            <a href="${pageContext.request.contextPath}/role/list"
               class="btn-back">
                ← Back to Role List
            </a>

        </div>

        <div class="card-footer">
            POS Management System
        </div>

    </div>

</div>

</body>
</html>