<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Stock List</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600&display=swap" rel="stylesheet">

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
            width: 1050px;
            background: #ffffff;
            padding: 35px 40px;
            border-radius: 18px;
            box-shadow: 0 12px 35px rgba(0, 0, 0, 0.12);
            border: 1px solid #e6e6e6;
        }

        h2 {
            text-align: center;
            margin-bottom: 25px;
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
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        th, td {
            padding: 12px;
            text-align: center;
            border-bottom: 1px solid #eee;
        }

        th {
            background: #f1f3f5;
        }

        tr:hover {
            background: #f9fafc;
        }

        .action-icon {
            font-size: 18px;
            margin: 0 6px;
            text-decoration: none;
        }

        .footer-actions {
            margin-top: 20px;
            text-align: center;
        }

        .btn {
            padding: 10px 16px;
            border-radius: 10px;
            text-decoration: none;
            background: #4a90e2;
            color: white;
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

    <h2>Stock List</h2>

    <c:if test="${empty stocks}">
        <div>No stocks found</div>
    </c:if>

    <c:if test="${not empty stocks}">
        <table>
            <thead>
            <tr>
                <th>ID</th>
                <th>Identifier</th>
                <th>Warehouse</th>
                <th>Available Qty</th>
                <th>Outgoing Qty</th>
                <th>Status</th>
                <th>Action</th>
            </tr>
            </thead>

            <tbody>
            <c:forEach var="stock" items="${stocks}">
                <tr>
                    <td>${stock.id}</td>
                    <td>${stock.identifier}</td>
                    <td>${stock.warehouse}</td>
                    <td>${stock.availableQuantity}</td>
                    <td>${stock.outgoingQuantity}</td>

                    <td>
                        <label class="switch">
                            <input type="checkbox"
                                   <c:if test="${stock.status}">checked</c:if>
                                   onclick="toggleStatus('${stock.identifier}', this)">
                            <span class="slider"></span>
                        </label>
                    </td>

                    <td>
                        <a href="/stock/get?identifier=${stock.identifier}"
                           class="action-icon"
                           title="Update">✏️</a>

                        <a href="/stock/delete?identifier=${stock.identifier}"
                           class="action-icon"
                           title="Delete"
                           onclick="return confirm('Are you sure?');">
                            🗑
                        </a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>

    <div class="footer-actions">
        <a href="/stock/add" class="btn">+ Add New Stock</a>
    </div>

</div>

<script>
function toggleStatus(identifier, checkbox) {
    fetch('/stock/toggle-status', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: 'identifier=' + encodeURIComponent(identifier) +
              '&status=' + checkbox.checked
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