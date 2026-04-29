<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Edit Shelf</title>

    <style>
        body {
            font-family: "Segoe UI", Arial, sans-serif;
            background: linear-gradient(135deg, #ede9fe, #ddd6fe);
            margin: 0;
            min-height: 100vh;
        }

        .container {
            width: 420px;
            margin: 80px auto;
            background: #ffffff;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 20px 40px rgba(76, 29, 149, 0.18);
        }

        h2 {
            text-align: center;
            margin-bottom: 24px;
            font-size: 20px;
            color: #6d28d9;
            font-weight: 600;
        }

        label {
            margin-top: 16px;
            display: block;
            font-weight: 600;
            font-size: 13px;
            color: #4c1d95;
        }

        input, select {
            width: 100%;
            margin-top: 6px;
            padding: 9px;
            border: 1px solid #c4b5fd;
            border-radius: 6px;
            font-size: 13px;
        }

        button {
            margin-top: 26px;
            width: 100%;
            padding: 11px;
            background: #7c3aed;
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
    </style>
</head>

<body>

<div class="container">

    <h2>Edit Shelf</h2>

    <form action="${pageContext.request.contextPath}/shelfs/update" method="post">

        <input type="hidden" name="identifier" value="${shelf.identifier}" />

        <label>Shelf Name</label>
        <input type="text" value="${shelf.identifier}" readonly />

        <label>Status</label>
        <select name="status" required>
            <option value="true" <c:if test="${shelf.status}">selected</c:if>>Active</option>
            <option value="false" <c:if test="${!shelf.status}">selected</c:if>>Inactive</option>
        </select>

        <button type="submit">Update</button>
    </form>

    <a href="${pageContext.request.contextPath}/shelfs/list">
        ← Back to Shelf List
    </a>

</div>

</body>
</html>