<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Add Warehouse</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600&display=swap" rel="stylesheet">

    <style>
        body {
            background:#f8fafc;
            display:flex;
            align-items:center;
            justify-content:center;
            min-height:100vh;
            font-family:'Inter',sans-serif;
        }

        .form-card {
            width:460px;
            padding:30px;
            background:rgba(255,255,255,.75);
            backdrop-filter:blur(16px);
            border-radius:16px;
            border:1px solid #e2e8f0;
            box-shadow:0 20px 40px rgba(2,6,23,.08);
        }

        .back-arrow {
            position:fixed;
            top:20px;
            left:20px;
            width:42px;
            height:42px;
            border-radius:50%;
            display:flex;
            align-items:center;
            justify-content:center;
            background:rgba(255,255,255,.75);
            text-decoration:none;
            font-size:18px;
            color:#0f172a;
            border:1px solid #e2e8f0;
        }

        .form-control {
            margin-top:6px;
            border-radius:10px;
        }

        .btn-submit {
            width:100%;
            margin-top:20px;
            background:#2563eb;
            border:none;
            color:white;
            padding:12px;
            border-radius:12px;
            font-weight:600;
        }
    </style>
</head>

<body>

<a href="${pageContext.request.contextPath}/warehouse/list" class="back-arrow">←</a>

<div class="form-card">
    <h4 class="text-center mb-3">Add Warehouse</h4>

    <form action="${pageContext.request.contextPath}/warehouse/add" method="post">

        <label>Identifier</label>
        <input name="identifier" class="form-control" required>

        <label>Warehouse Name</label>
        <input name="name" class="form-control" required>

        <label>Location</label>
        <input name="location" class="form-control" required>

        <button class="btn-submit">Save Warehouse</button>
    </form>
</div>

</body>
</html>