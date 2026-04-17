<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Update User</title>

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

            --success: #16a34a;
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
            max-width: 500px;
        }

        .card {
            background: var(--card);
            border-radius: var(--radius);
            box-shadow: var(--shadow);
            padding: 24px;
        }

        h3 {
            text-align: center;
            font-size: 18px;
            font-weight: 600;
            margin-bottom: 18px;
            color: var(--text);
        }

        label {
            display: block;
            font-size: 13px;
            color: var(--muted);
            font-weight: 500;
            margin-bottom: 6px;
        }

        input, select {
            width: 100%;
            padding: 10px 12px;
            border-radius: 8px;
            border: 1px solid var(--border);
            font-size: 14px;
            transition: 0.2s;
            background: #fff;
        }

        input:focus, select:focus {
            outline: none;
            border-color: var(--primary);
            box-shadow: 0 0 0 3px rgba(37, 99, 235, 0.15);
        }

        .form-group {
            margin-bottom: 14px;
        }

        select[multiple] {
            height: 120px;
        }

        .roles-current {
            font-size: 12px;
            color: var(--muted);
            margin-bottom: 8px;
        }

        .badge {
            display: inline-block;
            padding: 4px 8px;
            border-radius: 999px;
            font-size: 11px;
            background: #e5e7eb;
            color: #111827;
            margin-right: 4px;
            margin-top: 4px;
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

        .back-link {
            display: block;
            text-align: center;
            margin-top: 14px;
            font-size: 13px;
            color: var(--primary);
            font-weight: 600;
            text-decoration: none;
        }

        .back-link:hover {
            text-decoration: underline;
        }

        .message {
            text-align: center;
            font-size: 13px;
            color: var(--success);
            margin-bottom: 10px;
        }
    </style>
</head>

<body>

<div class="container">

    <c:if test="${not empty message}">
        <div class="message">${message}</div>
    </c:if>

    <div class="card">

        <h3>Update User</h3>

        <form:form action="/user/update" method="post" modelAttribute="user">

            <form:hidden path="id"/>

            <div class="form-group">
                <label>Name</label>
                <form:input path="name" required="true"/>
            </div>

            <div class="form-group">
                <label>Email</label>
                <form:input path="username" required="true"/>
            </div>

            <div class="form-group">
                <label>Phone Number</label>
                <form:input path="phoneNo" required="true"/>
            </div>

            <div class="form-group">

                <label>Roles</label>

                <div class="roles-current">
                    Current:
                    <c:forEach var="r" items="${user.roles}">
                        <span class="badge">${r}</span>
                    </c:forEach>
                </div>

                <form:select path="roles" multiple="true">
                    <form:options items="${roles}" itemValue="identifier" itemLabel="identifier"/>
                </form:select>

            </div>

            <button type="submit" class="btn btn-primary">Update User</button>

        </form:form>

        <a href="/user/list" class="back-link">← Back to User List</a>

    </div>

</div>

</body>
</html>