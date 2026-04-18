<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Add Node</title>

    <style>
        body {
            font-family: "Segoe UI", Arial, sans-serif;
            background: linear-gradient(135deg, #eef2ff, #f7f9ff);
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .card {
            width: 540px;
            background: #ffffff;
            border-radius: 16px;
            padding: 32px;
            box-shadow: 0 20px 45px rgba(0,0,0,0.12);
        }

        h3 {
            text-align: center;
            color: #2c3e50;
            margin-bottom: 28px;
            font-weight: 700;
        }

        .form-group {
            display: grid;
            grid-template-columns: 150px 1fr;
            align-items: center;
            gap: 14px;
            margin-bottom: 18px;
        }

        label {
            font-size: 14px;
            font-weight: 600;
            color: #444;
        }

        input, select {
            padding: 10px 12px;
            border-radius: 8px;
            border: 1px solid #ccd3e0;
            font-size: 14px;
            background: #f9fafc;
        }

        input:focus, select:focus {
            outline: none;
            border-color: #6f42c1;
            box-shadow: 0 0 0 3px rgba(111,66,193,0.15);
            background: #ffffff;
        }

        select[multiple] {
            height: 140px;
        }

        small {
            font-size: 12px;
            color: #666;
        }

        button {
            width: 100%;
            margin-top: 26px;
            padding: 12px;
            background: linear-gradient(135deg, #6f42c1, #9b6bff);
            border: none;
            border-radius: 30px;
            color: #ffffff;
            font-size: 16px;
            font-weight: 700;
            cursor: pointer;
            box-shadow: 0 12px 25px rgba(111,66,193,0.35);
        }
    </style>
</head>

<body>

<div class="card">
    <h3>Add Node</h3>

    <form action="/node/add" method="post">

        <div class="form-group">
            <label>Node Name</label>
            <input type="text" name="identifier" placeholder="Enter node name" required>
        </div>

        <div class="form-group">
            <label>Path</label>
            <input type="text" name="path" placeholder="Enter node path" required>
        </div>

        <div class="form-group">
            <label>Roles</label>
            <div>
                <select name="roles" multiple required>
                    <c:forEach items="${roles}" var="role">
                        <option value="${role.identifier}">
                            ${role.identifier}
                        </option>
                    </c:forEach>
                </select>
                <small>Hold Ctrl (Windows/Linux) or Cmd (Mac) to select multiple roles</small>
            </div>
        </div>

        <button type="submit">Add Node</button>
    </form>
</div>
</body>
</html>
