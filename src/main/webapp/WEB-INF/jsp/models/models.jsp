<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Edit Model</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600&display=swap"
          rel="stylesheet">

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

        .form-group {
            margin-bottom: 18px;
        }

        label {
            display: block;
            margin-bottom: 6px;
            font-weight: 500;
            font-size: 13px;
        }

        input {
            width: 100%;
            padding: 11px 14px;
            border-radius: 10px;
            border: 1px solid #ddd;
            font-size: 14px;
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

        .error-message {
            background: #ffe5e5;
            color: #b30000;
            padding: 10px;
            border-radius: 8px;
            text-align: center;
            margin-bottom: 15px;
            font-size: 13px;
        }
    </style>
</head>

<body>

<div class="card-container">

    <a href="${pageContext.request.contextPath}/models/list" class="back-icon">←</a>

    <h2>Edit Model</h2>

    <c:if test="${not empty message}">
        <div class="error-message">
            ${message}
        </div>
    </c:if>

    <form action="${pageContext.request.contextPath}/models/update" method="post">

        <input type="hidden" name="id" value="${model.id}" />
        <input type="hidden" name="identifier" value="${model.identifier}" />

        <!-- Identifier (readonly like Brand page) -->
        <div class="form-group">
            <label>Model Identifier</label>
            <input type="text" value="${model.identifier}" readonly />
        </div>

        <button type="submit" class="btn-submit">Update Model</button>

    </form>
</div>

</body>
</html>