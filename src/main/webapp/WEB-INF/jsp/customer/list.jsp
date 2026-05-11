<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Customer Management</title>
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
            width: 1000px;
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
            color: #333;
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

        .home-link {
            position: absolute;
            top: 16px;
            right: 16px;
            font-size: 13px;
            font-weight: 500;
            color: #333;
            text-decoration: none;
            padding: 6px 12px;
            border-radius: 8px;
            background: #f0f0f0;
        }

        .list-actions {
            display: flex;
            justify-content: flex-end;
            margin-bottom: 15px;
        }

        .btn-add {
            padding: 10px 16px;
            border-radius: 10px;
            background: #4a90e2;
            color: white;
            font-weight: 600;
            text-decoration: none;
            font-size: 14px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 10px;
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

        .action-link {
            font-size: 16px;
            margin: 0 6px;
            text-decoration: none;
        }

        .edit {
            color: #4a90e2;
        }

        .delete {
            color: #e74c3c;
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

        .alert {
            padding: 10px;
            border-radius: 8px;
            margin-bottom: 15px;
            text-align: center;
            font-size: 13px;
            background: #fff3cd;
            color: #856404;
        }
    </style>
</head>
<body>
<div class="card-container">
    <a href="${pageContext.request.contextPath}/" class="back-icon">←</a>
    <a href="${pageContext.request.contextPath}/" class="home-link">Home</a>
    <h2>Customer Management</h2>
    <div class="list-actions">
        <a class="btn-add" href="${pageContext.request.contextPath}/customer/add">
            + Add Customer
        </a>
    </div>
    <c:if test="${empty customers}">
        <div class="alert">No customers found</div>
    </c:if>
    <c:if test="${not empty customers}">
        <table>
            <thead>
            <tr>
                <th>Phone</th>
                <th>Name</th>
                <th>Party Type</th>
                <th>Credit</th>
                <th>Credit Type</th>
                <th>Status</th>
                <th>Action</th>
            </tr>
            </thead>

            <tbody>
            <c:forEach var="c" items="${customers}">
                <tr>
                    <td>${c.identifier}</td>
                    <td>${c.customerName}</td>
                    <td>${c.partyType}</td>
                    <td>${c.credit}</td>
                    <td>${c.creditType}</td>

                    <td>
                        <label class="switch">
                            <input type="checkbox"
                                   <c:if test="${c.status}">checked</c:if>
                                   onclick="toggleStatus('${c.identifier}', this)">
                            <span class="slider"></span>
                        </label>
                    </td>

                    <td>
                        <a class="action-link edit"
                           href="${pageContext.request.contextPath}/customer/get?identifier=${c.identifier}">
                            ✏️
                        </a>

                        <a class="action-link delete"
                           href="${pageContext.request.contextPath}/customer/delete?identifier=${c.identifier}"
                           onclick="return confirm('Are you sure you want to delete this customer?');">
                            🗑
                        </a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
</div>
<script>
function toggleStatus(identifier, checkbox) {
    fetch('${pageContext.request.contextPath}/customer/toggle-status', {
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