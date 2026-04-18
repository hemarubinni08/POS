<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Add Node</title>

    <style>
        body {
            font-family: "Segoe UI", Arial, sans-serif;
            background: linear-gradient(135deg, #ede9fe, #ddd6fe); /* light purple gradient */
            margin: 0;
            min-height: 100vh;
        }

        .container {
            width: 420px;
            margin: 80px auto;
            background: #ffffff;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 20px 40px rgba(76, 29, 149, 0.18); /* soft purple shadow */
        }

        h2 {
            text-align: center;
            margin-bottom: 24px;
            font-size: 20px;
            color: #6d28d9; /* purple heading */
            font-weight: 600;
        }

        label {
            margin-top: 16px;
            display: block;
            font-weight: 600;
            font-size: 13px;
            color: #4c1d95; /* deep purple label */
        }

        input, select {
            width: 100%;
            margin-top: 6px;
            padding: 9px;
            border: 1px solid #c4b5fd;
            border-radius: 6px;
            font-size: 13px;
        }

        input:focus, select:focus {
            outline: none;
            border-color: #a78bfa;
            box-shadow: 0 0 0 0.15rem rgba(167, 139, 250, 0.35);
        }

        select[multiple] {
            height: 110px;
        }

        button {
            margin-top: 26px;
            width: 100%;
            padding: 11px;
            background: #7c3aed; /* primary purple */
            color: #ffffff;
            border: none;
            font-weight: 600;
            border-radius: 6px;
            cursor: pointer;
        }

        button:hover {
            background: #6d28d9;
        }

        a {
            display: block;
            text-align: center;
            margin-top: 18px;
            color: #6d28d9;
            font-weight: 600;
            text-decoration: none;
            font-size: 13px;
        }

        a:hover {
            text-decoration: underline;
            color: #5b21b6;
        }
    </style>
</head>

<body>

<div class="container">

    <h2>Add Node</h2>

    <form action="${pageContext.request.contextPath}/node/add" method="post">

        <!-- ✅ Identifier -->
        <label>Identifier</label>
        <input type="text" name="identifier" required />

        <!-- ✅ Path -->
        <label>Path</label>
        <input type="text" name="path" required />

        <!-- ✅ Roles -->
        <label>Roles</label>
        <select name="roles" multiple required>
            <c:forEach var="role" items="${roles}">
                <option value="${role.identifier}">
                    ${role.identifier}
                </option>
            </c:forEach>
        </select>

        <button type="submit">Save</button>
    </form>

    <a href="${pageContext.request.contextPath}/node/list">
        ← Back to Node List
    </a>

</div>

</body>
</html>