<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Rack Management</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
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
            width: 950px;
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
        }

        th {
            background: #4a90e2;
            color: white;
            padding: 12px;
        }

        td {
            padding: 12px;
            text-align: center;
            border-bottom: 1px solid #eee;
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
        .status-toggle {
            position: relative;
            display: inline-block;
            width: 42px;
            height: 22px;
        }

        .status-toggle input {
            opacity: 0;
            width: 0;
            height: 0;
        }

        .status-slider {
            position: absolute;
            cursor: pointer;
            inset: 0;
            background-color: #dc2626;
            transition: 0.3s;
            border-radius: 22px;
        }

        .status-slider:before {
            position: absolute;
            content: "";
            height: 18px;
            width: 18px;
            left: 2px;
            bottom: 2px;
            background-color: white;
            transition: 0.3s;
            border-radius: 50%;
        }

        .status-toggle input:checked + .status-slider {
            background-color: #16a34a;
        }

        .status-toggle input:checked + .status-slider:before {
            transform: translateX(20px);
        }

        .btn-add {
            display: inline-block;
            margin-top: 20px;
            padding: 10px 16px;
            background: #4a90e2;
            color: white;
            text-decoration: none;
            border-radius: 10px;
            font-weight: 600;
        }

        .alert {
            padding: 10px;
            border-radius: 8px;
            text-align: center;
            margin-bottom: 15px;
        }

        .alert-warning {
            background: #fff3cd;
            color: #856404;
        }
    </style>
</head>

<body>

<div class="card-container">

    <a href="/" class="back-icon">←</a>

    <h2>Rack Management</h2>

    <c:if test="${empty racks}">
        <div class="alert alert-warning">
            No racks available
        </div>
    </c:if>

    <c:if test="${not empty racks}">
        <table>
            <thead>
            <tr>
                <th>Rack Name</th>
                <th>Shelfs</th>
                <th>Status</th>
                <th>Action</th>
            </tr>
            </thead>

            <tbody>
            <c:forEach var="rack" items="${racks}">
                <tr>
                    <td>${rack.identifier}</td>

                    <td>
                        <c:choose>
                            <c:when test="${not empty rack.shelfs}">
                                <c:forEach var="s" items="${rack.shelfs}" varStatus="st">
                                    ${s}<c:if test="${!st.last}">, </c:if>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>-</c:otherwise>
                        </c:choose>
                    </td>

                    <td>
                        <label class="status-toggle">
                            <input type="checkbox"
                                   <c:if test="${rack.status}">checked</c:if>
                                   onclick="toggleStatus('${rack.identifier}', this)">
                            <span class="status-slider"></span>
                        </label>
                    </td>

                    <td>
                        <a href="${pageContext.request.contextPath}/rack/get?identifier=${rack.identifier}"
                           class="action-icon"
                           title="Edit">✏️</a>

                        <a href="${pageContext.request.contextPath}/rack/delete?identifier=${rack.identifier}"
                           class="action-icon delete-icon"
                           title="Delete"
                           onclick="return confirm('Delete this rack?');">
                            🗑
                        </a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>

    <div style="text-align:center;">
        <a href="${pageContext.request.contextPath}/rack/add"
           class="btn-add">+ Add Rack</a>
    </div>

</div>

<script>
function toggleStatus(identifier, checkbox) {
    fetch('${pageContext.request.contextPath}/rack/toggle-status', {
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