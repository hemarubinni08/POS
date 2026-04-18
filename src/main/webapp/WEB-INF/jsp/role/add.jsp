<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add Role</title>

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
            position: relative;
        }

        .back-arrow {
            position: absolute;
            top: 20px;
            left: 20px;
            background: var(--card);
            border: 1px solid var(--border);
            border-radius: 50%;
            width: 42px;
            height: 42px;
            display: flex;
            align-items: center;
            justify-content: center;
            text-decoration: none;
            color: var(--text);
            font-size: 18px;
            box-shadow: var(--shadow);
            transition: 0.2s;
        }

        .back-arrow:hover {
            background: var(--accent);
            color: black;
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

<a href="${pageContext.request.contextPath}/role/list" class="back-arrow">
    ←
</a>

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

                <div class="mb-3">
                    <label>Description</label>
                    <form:input path="description"
                                cssClass="form-control"
                                placeholder="Enter role description"
                                required="true"/>
                </div>

                <button type="submit" class="btn btn-primary">
                    Add Role
                </button>

            </form:form>

        </div>

    </div>

</div>

</body>
</html>