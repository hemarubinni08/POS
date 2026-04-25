<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Edit Category</title>

    <style>
        body {
            font-family: "Segoe UI", Arial, sans-serif;
            background: linear-gradient(135deg, #ede9fe, #ddd6fe);
            min-height: 100vh;
        }

        .container {
            width: 420px;
            margin: 80px auto;
            background: #ffffff;
            padding: 30px;
            border-radius: 12px;
        }
    </style>
</head>

<body>

<div class="container">

    <h2>Edit Category</h2>

    <form action="${pageContext.request.contextPath}/category/update" method="post">

        <input type="hidden" name="id" value="${category.id}" />
        <input type="hidden" name="identifier" value="${category.identifier}" />

        <label>Category</label>
        <input type="text" value="${category.identifier}" readonly />

        <label>Super Category</label>
        <input type="text" name="superCategory" value="${category.superCategory}" />

        <button type="submit">Update</button>
    </form>

    <a href="${pageContext.request.contextPath}/category/list">
        ← Back to Category List
    </a>

</div>

</body>
</html>