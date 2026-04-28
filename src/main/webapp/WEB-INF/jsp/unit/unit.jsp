<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Edit Unit</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600&display=swap" rel="stylesheet">

    <style>
        :root {
            --primary:#2563eb; --primary-hover:#1e40af;
            --bg:#f8fafc; --glass:rgba(255,255,255,0.85);
            --text:#0f172a; --muted:#64748b;
            --border:#e2e8f0;
            --danger:#dc2626;
            --radius:16px; --shadow:0 20px 40px rgba(2,6,23,0.08);
        }

        *{font-family:'Inter',sans-serif;box-sizing:border-box}
        body{
            background:var(--bg);
            min-height:100vh;
            display:flex;
            justify-content:center;
            align-items:center;
            position:relative;
        }

        .back-arrow{
            position:absolute;top:20px;left:20px;
            width:42px;height:42px;
            border-radius:50%;
            display:flex;align-items:center;justify-content:center;
            background:var(--glass);
            border:1px solid var(--border);
            box-shadow:var(--shadow);
            color:var(--text);
            text-decoration:none;font-size:18px;
        }

        .container-box{width:100%;max-width:480px}

        .card{
            padding:28px;
            background:var(--glass);
            border-radius:var(--radius);
            box-shadow:var(--shadow);
        }

        h2{text-align:center;margin-bottom:20px;font-weight:600}

        label{font-size:13px;color:var(--muted);margin-top:12px}
        .form-control{border-radius:10px;padding:10px;margin-top:6px}

        .btn-update{
            margin-top:20px;width:100%;
            background:var(--primary);
            color:white;border:none;
            border-radius:10px;
            padding:10px;font-weight:600;
        }
    </style>
</head>

<body>

<a href="${pageContext.request.contextPath}/unit/list" class="back-arrow">←</a>

<div class="container-box">
    <div class="card">
        <h2>Edit Unit</h2>

        <form action="${pageContext.request.contextPath}/unit/update" method="post">

            <input type="hidden" name="id" value="${unit.id}">
            <input type="hidden" name="identifier" value="${unit.identifier}">

            <label>Unit Name</label>
            <input type="text" class="form-control" value="${unit.identifier}" readonly>

            <label>Status</label>
            <select name="status" class="form-control">
                <option value="true" <c:if test="${unit.status}">selected</c:if>>Active</option>
                <option value="false" <c:if test="${!unit.status}">selected</c:if>>Inactive</option>
            </select>

            <button class="btn-update" type="submit">Update Unit</button>
        </form>

    </div>
</div>

</body>
</html>