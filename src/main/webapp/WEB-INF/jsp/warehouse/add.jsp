<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Add Warehouse</title>

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Font -->
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600&display=swap" rel="stylesheet">

    <style>
        :root {
            --primary: #2563eb;
            --primary-hover: #1e40af;

            --bg: #f8fafc;
            --glass: rgba(255,255,255,0.75);

            --text: #0f172a;
            --muted: #64748b;

            --border: #e2e8f0;

            --danger: #dc2626;
            --success: #16a34a;

            --radius: 16px;
            --shadow: 0 20px 40px rgba(2,6,23,0.08);
        }

        * {
            font-family: 'Inter', sans-serif;
            box-sizing: border-box;
        }

        body {
            background: var(--bg);
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 20px;
        }

        /* BACK BUTTON */
        .back-arrow {
            position: fixed;
            top: 20px;
            left: 20px;

            width: 42px;
            height: 42px;

            display: flex;
            align-items: center;
            justify-content: center;

            border-radius: 50%;
            background: var(--glass);
            backdrop-filter: blur(10px);

            border: 1px solid var(--border);
            color: var(--text);

            text-decoration: none;
            font-size: 18px;

            box-shadow: var(--shadow);
            transition: 0.2s;
        }

        .back-arrow:hover {
            background: #eef2ff;
            color: var(--primary);
        }

        /* CARD */
        .form-card {
            width: 460px;
            padding: 32px;

            border-radius: var(--radius);
            background: var(--glass);
            backdrop-filter: blur(16px);

            border: 1px solid var(--border);
            box-shadow: var(--shadow);
        }

        h2 {
            text-align: center;
            font-weight: 600;
            margin-bottom: 20px;
        }

        label {
            font-size: 13px;
            color: var(--muted);
            margin-top: 12px;
        }

        .form-control {
            margin-top: 6px;
            border-radius: 12px;
            padding: 11px;
            font-size: 14px;

            border: 1px solid var(--border);
            background: rgba(255,255,255,0.9);
        }

        .form-control:focus {
            border-color: var(--primary);
            box-shadow: 0 0 0 3px rgba(37,99,235,0.2);
            background: #fff;
        }

        .btn-submit {
            margin-top: 20px;
            width: 100%;
            padding: 12px;

            border-radius: 12px;
            border: none;

            background: var(--primary);
            color: #fff;
            font-weight: 600;

            transition: 0.2s;
        }

        .btn-submit:hover {
            background: var(--primary-hover);
            transform: translateY(-1px);
        }

        .server-msg {
            text-align: center;
            padding: 10px;
            margin-bottom: 14px;
            border-radius: 10px;
            font-size: 13px;
        }

        .server-msg.error {
            background: #fee2e2;
            color: var(--danger);
        }

        .server-msg.success {
            background: #dcfce7;
            color: var(--success);
        }
    </style>
</head>

<body>

<a href="${pageContext.request.contextPath}/warehouse/list" class="back-arrow">←</a>

<div class="form-card">

    <h2>Add Warehouse</h2>

    <c:if test="${not empty message}">
        <div class="server-msg ${messageType}">
            ${message}
        </div>
    </c:if>

    <form action="${pageContext.request.contextPath}/warehouse/add" method="post">

        <div class="mb-3">
            <label>Identifier</label>
            <input type="text"
                   name="identifier"
                   value="${warehouseDto.identifier}"
                   class="form-control"
                   required>
        </div>

        <div class="mb-3">
            <label>Warehouse Name</label>
            <input type="text"
                   name="name"
                   value="${warehouseDto.name}"
                   class="form-control"
                   required>
        </div>

        <div class="mb-3">
            <label>Location</label>
            <input type="text"
                   name="location"
                   value="${warehouseDto.location}"
                   class="form-control"
                   required>
        </div>

        <button type="submit" class="btn-submit">
            Save Warehouse
        </button>

    </form>

</div>

</body>
</html>