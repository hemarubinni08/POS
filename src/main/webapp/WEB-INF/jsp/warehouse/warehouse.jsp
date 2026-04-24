<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Edit Warehouse</title>

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

            --radius: 16px;
            --shadow: 0 20px 40px rgba(2,6,23,0.08);
        }

        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Inter', sans-serif;
        }

        body {
            background: var(--bg);
            min-height: 100vh;
            padding: 40px 20px;
            color: var(--text);
            display: flex;
            justify-content: center;
            align-items: center;
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

        .container-box {
            width: 100%;
            max-width: 500px;
        }

        /* CARD */
        .card {
            background: var(--glass);
            backdrop-filter: blur(16px);

            border-radius: var(--radius);
            border: 1px solid var(--border);

            box-shadow: var(--shadow);
            padding: 28px;
        }

        h2 {
            text-align: center;
            font-size: 18px;
            font-weight: 600;
            margin-bottom: 20px;
        }

        label {
            font-size: 13px;
            color: var(--muted);
            margin-top: 12px;
        }

        .form-control, select {
            margin-top: 6px;
            border-radius: 10px;
            padding: 10px;
            border: 1px solid var(--border);
            font-size: 14px;
        }

        .form-control:focus, select:focus {
            border-color: var(--primary);
            box-shadow: 0 0 0 3px rgba(37,99,235,0.2);
        }

        .btn-update {
            margin-top: 22px;
            width: 100%;
            padding: 12px;
            border-radius: 12px;
            border: none;

            background: linear-gradient(135deg, var(--primary), var(--primary-hover));
            color: white;
            font-weight: 600;

            transition: all 0.2s ease;
        }

        .btn-update:hover {
            transform: translateY(-1px);
            box-shadow: 0 10px 25px rgba(37,99,235,0.3);
        }
    </style>
</head>

<body>

<!-- Back -->
<a href="${pageContext.request.contextPath}/warehouse/list" class="back-arrow">←</a>

<div class="container-box">

    <div class="card">

        <h2>Edit Warehouse</h2>

        <form action="${pageContext.request.contextPath}/warehouse/update"
              method="post">

            <!-- Hidden identifiers -->
            <input type="hidden" name="id" value="${warehouse.id}" />
            <input type="hidden" name="identifier" value="${warehouse.identifier}" />

            <label>Warehouse Name</label>
            <input type="text"
                   name="name"
                   value="${warehouse.name}"
                   class="form-control"
                   required />

            <label>Location</label>
            <input type="text"
                   name="location"
                   value="${warehouse.location}"
                   class="form-control"
                   required />

            <label>Status</label>
            <select name="status" class="form-control">
                <option value="ACTIVE"
                    <c:if test="${warehouse.status == 'ACTIVE'}">selected</c:if>>
                    ACTIVE
                </option>
                <option value="INACTIVE"
                    <c:if test="${warehouse.status == 'INACTIVE'}">selected</c:if>>
                    INACTIVE
                </option>
            </select>

            <button type="submit" class="btn-update">
                Update Warehouse
            </button>

        </form>

    </div>

</div>

</body>
</html>