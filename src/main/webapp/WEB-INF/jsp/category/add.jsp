<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Add Category</title>

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

        input {
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
            color: white;
            border: none;
            border-radius: 6px;
            font-weight: 600;
        }

        .error-message {
            background: #fee2e2;
            color: #b91c1c;
            padding: 10px;
            border-radius: 8px;
            text-align: center;
            margin-bottom: 16px;
            font-size: 13px;
        }
    </style>
</head>

<body>

<div class="container">

    <h2>Add Category</h2>

    <c:if test="${not empty message}">
        <div class="error-message">${message}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/category/add" method="post">
    <label>Super Category</label>
    <label>Super Category</label>
    <select name="superCategory">
        <option value="">-- None (Top Level Category) --</option>

        <c:forEach var="cat" items="${categories}">
            <option value="${cat.identifier}">
                ${cat.identifier}
            </option>
        </c:forEach>
    </select>



        <label>Category</label>
        <input type="text" name="identifier" required />

        <button type="submit">Save</button>
    </form>

    <a href="${pageContext.request.contextPath}/category/list">
        ← Back to Category List
    </a>

</div>

</body>
</html>