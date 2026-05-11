<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Model List</title>
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
            width: 900px;
            background: #ffffff;
            padding: 40px 45px;
            border-radius: 18px;
            box-shadow: 0 12px 35px rgba(0, 0, 0, 0.12);
            border: 1px solid #e6e6e6;
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

        table {
            width: 100%;
            border-collapse: collapse;
        }

        th {
            background: #4a90e2;
            color: white;
            padding: 12px;
            font-size: 14px;
        }

        td {
            padding: 12px;
            text-align: center;
            border-bottom: 1px solid #eee;
            font-size: 14px;
        }

        tr:hover {
            background: #f7f9ff;
        }

        .action-icon {
            font-size: 16px;
            margin: 0 6px;
            text-decoration: none;
            color: #4a90e2;
        }

        .delete-icon {
            color: #e74c3c;
        }

        .footer-actions {
            margin-top: 20px;
            display: flex;
            justify-content: center;
        }

        .btn-add {
            padding: 10px 16px;
            border-radius: 10px;
            text-decoration: none;
            font-weight: 600;
            font-size: 14px;
            background: #4a90e2;
            color: white;
        }

        .alert {
            padding: 10px;
            border-radius: 8px;
            margin-bottom: 15px;
            text-align: center;
            font-size: 13px;
            background: #fff3cd;
            color: #856404;
        }

        .switch {
            position: relative;
            display: inline-block;
            width: 44px;
            height: 24px;
        }

        .switch input {
            display: none;
        }

        .slider {
            position: absolute;
            inset: 0;
            cursor: pointer;
            background-color: #ddd;
            border-radius: 999px;
            transition: 0.25s;
        }

        .slider::before {
            content: "";
            position: absolute;
            height: 18px;
            width: 18px;
            left: 3px;
            top: 3px;
            background-color: white;
            border-radius: 50%;
            transition: 0.25s;
        }

        .switch input:checked + .slider {
            background-color: #4a90e2;
        }

        .switch input:checked + .slider::before {
            transform: translateX(20px);
        }
    </style>
</head>
<body>
<div class="card-container">
    <a href="/" class="back-icon">←</a>
    <h2>Model List</h2>
    <c:if test="${empty models}">
        <div class="alert">
            No models available
        </div>
    </c:if>
    <c:if test="${not empty models}">
        <table>
            <thead>
            <tr>
                <th>Identifier</th>
                <th>Status</th>
                <th>Action</th>
            </tr>
            </thead>

            <tbody>
            <c:forEach var="model" items="${models}">
                <tr>

                    <td>${model.identifier}</td>

                    <td>
                        <label class="switch">
                            <input type="checkbox"
                                   <c:if test="${model.status}">checked</c:if>
                                   onclick="toggleStatus('${model.identifier}', this)">
                            <span class="slider"></span>
                        </label>
                    </td>

                    <td>
                        <a href="${pageContext.request.contextPath}/models/get?identifier=${model.identifier}"
                           class="action-icon"
                           title="Edit">✏️</a>

                        <a href="${pageContext.request.contextPath}/models/delete?identifier=${model.identifier}"
                           class="action-icon delete-icon"
                           title="Delete"
                           onclick="return confirm('Are you sure you want to delete this model?');">
                            🗑
                        </a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
    <div class="footer-actions">
        <a href="${pageContext.request.contextPath}/models/add"
           class="btn-add">
            + Add Model
        </a>
    </div>
</div>

<script>
function toggleStatus(identifier, checkbox) {
    fetch('${pageContext.request.contextPath}/models/toggle-status', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: 'identifier=' + identifier + '&status=' + checkbox.checked
    })
    .then(res => {
        if (!res.ok) throw new Error();
    })
    .catch(() => {
        checkbox.checked = !checkbox.checked;
        alert('Status update failed');
    });
}
</script>

</body>
</html>