<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Edit Unit</title>
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

        .form-control {
            width: 100%;
            padding: 11px 14px;
            border-radius: 10px;
            border: 1px solid #ddd;
            font-size: 14px;
        }

        .form-control[readonly] {
            background: #f3f4f6;
            cursor: not-allowed;
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
            background: #357abd;
        }
    </style>
</head>

<body>

<div class="card-container">
    <a href="${pageContext.request.contextPath}/unit/list" class="back-icon">←</a>

    <h2>Edit Unit</h2>

    <form action="${pageContext.request.contextPath}/unit/update" method="post">
        <input type="hidden" name="id" value="${unit.id}" />
        <input type="hidden" name="identifier" value="${unit.identifier}" />
        <div class="form-group">
            <label>Unit Name</label>
            <input type="text"
                   value="${unit.identifier}"
                   class="form-control"
                   readonly />
        </div>
        <div class="form-group">
            <label>Status</label>
            <select name="status" class="form-control" required>
                <option value="true" <c:if test="${unit.status}">selected</c:if>>Active</option>
                <option value="false" <c:if test="${!unit.status}">selected</c:if>>Inactive</option>
            </select>
        </div>
        <button type="submit" class="btn-submit">Update Unit</button>
    </form>
</div>
</body>
</html>