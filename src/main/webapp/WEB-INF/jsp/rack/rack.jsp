<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Edit Rack</title>

    <style>
        body {
            margin: 0;
            font-family: 'Poppins', sans-serif;
            min-height: 100vh;
            background: #f4f6f9;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .card-container {
            position: relative;
            width: 520px;
            background: #ffffff;
            padding: 40px 45px;
            border-radius: 18px;
            box-shadow: 0 12px 35px rgba(0, 0, 0, 0.12);
            border: 1px solid #e6e6e6;
            color: #333;
        }

        h2 {
            text-align: center;
            margin-bottom: 25px;
            font-weight: 600;
        }

        /* BACK BUTTON */
        .back-icon {
            position: absolute;
            top: 16px;
            left: 16px;
            width: 36px;
            height: 36px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 18px;
            text-decoration: none;
            background: #f0f0f0;
            border-radius: 50%;
            color: #333;
        }

        label {
            display: block;
            margin-bottom: 6px;
            font-weight: 500;
            font-size: 13px;
        }

        input,
        select {
            width: 100%;
            padding: 11px 14px;
            border-radius: 10px;
            border: 1px solid #ddd;
            font-size: 14px;
            margin-bottom: 18px;
        }

        input:focus,
        select:focus {
            outline: none;
            border-color: #a78bfa;
            box-shadow: 0 0 0 0.15rem rgba(167, 139, 250, 0.35);
        }

        .btn-submit {
            width: 100%;
            padding: 12px;
            border-radius: 10px;
            border: none;
            background: #4a90e2;
            color: white;
            font-weight: 600;
            cursor: pointer;
        }

        .btn-submit:hover {
            background: #3578c6;
        }
    </style>
</head>

<body>

<div class="card-container">

    <a href="${pageContext.request.contextPath}/rack/list" class="back-icon">←</a>

    <h2>Edit Rack</h2>
    <form action="${pageContext.request.contextPath}/rack/update" method="post">

        <input type="hidden" name="identifier" value="${rack.identifier}"/>

        <label>Rack Name</label>
        <input type="text" value="${rack.identifier}" readonly />

        <label>Shelfs</label>
        <select name="shelfs" multiple required>
            <c:forEach var="shelf" items="${shelfs}">
                <option value="${shelf.identifier}">
                    ${shelf.identifier}
                </option>
            </c:forEach>
        </select>

        <button type="submit" class="btn-submit">Update Rack</button>

    </form>

</div>

</body>
</html>