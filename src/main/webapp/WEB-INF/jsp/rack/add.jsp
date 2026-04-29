<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Add Rack</title>

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
            box-shadow: 0 20px 40px rgba(76, 29, 149, 0.18);
        }

        h2 {
            text-align: center;
            color: #6d28d9;
        }

        label {
            margin-top: 16px;
            display: block;
            font-weight: 600;
            color: #4c1d95;
        }

        input, select {
            width: 100%;
            margin-top: 6px;
            padding: 9px;
            border: 1px solid #c4b5fd;
            border-radius: 6px;
        }

        button {
            margin-top: 24px;
            width: 100%;
            background: #7c3aed;
            color: #ffffff;
            border: none;
            padding: 10px;
            border-radius: 6px;
            font-weight: 600;
        }
    </style>
</head>

<body>
<div class="container">

    <h2>Add Rack</h2>

    <c:if test="${not empty message}">
        <div class="alert alert-danger text-center">${message}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/rack/add" method="post">

        <label>Rack Name</label>
        <input type="text" name="identifier" required>

        <label>Shelfs</label>
        <select name="shelfs" multiple class="form-control" required>
            <c:forEach var="shelf" items="${shelfs}">
                <option value="${shelf.identifier}">
                    ${shelf.identifier}
                </option>
            </c:forEach>
        </select>

        <button type="submit">Save</button>
    </form>

    <a href="${pageContext.request.contextPath}/rack/list"
       class="text-center d-block mt-3">← Back to Rack List</a>

</div>
</body>
</html>