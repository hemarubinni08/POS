<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <title>Edit Warehouse</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600&display=swap" rel="stylesheet">

    <style>
        body {
            background:#f8fafc;
            display:flex;
            justify-content:center;
            align-items:center;
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
    </style>
</head>

<body>

<a href="${pageContext.request.contextPath}/warehouse/list" class="back-arrow">←</a>

<div class="form-card">
    <h4 class="text-center mb-3">Edit Warehouse</h4>

    <form action="${pageContext.request.contextPath}/warehouse/update" method="post">

        <input type="hidden" name="identifier" value="${warehouseDto.identifier}">

        <label>Name</label>
        <input name="name" value="${warehouseDto.name}" class="form-control" required>

        <label>Location</label>
        <input name="location" value="${warehouseDto.location}" class="form-control" required>

        <button class="btn-submit">Update Warehouse</button>
    </form>
</div>

</body>
</html>