<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Edit Node</title>

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
            padding: 22px;
        }

        h2 {
            text-align: center;
            font-size: 18px;
            font-weight: 600;
            margin-bottom: 18px;
            color: var(--text);
        }

        label {
            display: block;
            margin-top: 12px;
            font-size: 13px;
            color: var(--muted);
            font-weight: 500;
        }

        input, select {
            width: 100%;
            margin-top: 6px;
            padding: 10px 12px;
            border: 1px solid var(--border);
            border-radius: 8px;
            font-size: 14px;
            transition: 0.2s;
            background: #fff;
        }

        input:focus, select:focus {
            outline: none;
            border-color: var(--primary);
            box-shadow: 0 0 0 3px rgba(37, 99, 235, 0.15);
        }

        select[multiple] {
            height: 120px;
        }

        button {
            margin-top: 18px;
            width: 100%;
            padding: 11px 14px;
            border: none;
            border-radius: 10px;
            font-size: 14px;
            font-weight: 600;
            cursor: pointer;
            background: var(--primary);
            color: #fff;
            transition: 0.2s;
        }

        button:hover {
            background: var(--primary-hover);
        }

        a {
            display: block;
            text-align: center;
            margin-top: 14px;
            font-size: 13px;
            color: var(--primary);
            font-weight: 600;
            text-decoration: none;
        }

        a:hover {
            text-decoration: underline;
        }
    </style>
</head>

<body>

<div class="container">
    <div class="card">

        <h2>Edit Node</h2>

        <form action="${pageContext.request.contextPath}/node/update" method="post">

            <input type="hidden" name="id" value="${node.id}" />
            <input type="hidden" name="identifier" value="${node.identifier}" />

            <label>Path</label>
            <input type="text" name="path" value="${node.path}" required />

            <label>Roles</label>
            <select name="roles" multiple required>
                <c:forEach var="role" items="${roles}">
                    <option value="${role.identifier}"
                        <c:if test="${node.roles.contains(role.identifier)}">selected</c:if>>
                        ${role.identifier}
                    </option>
                </c:forEach>
            </select>

            <button type="submit">Update</button>

        </form>

        <a href="${pageContext.request.contextPath}/node/list">Back to Node List</a>

    </div>
</div>

</body>
</html>