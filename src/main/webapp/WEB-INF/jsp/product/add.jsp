<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Add Product</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        :root {
            --bg:#f6fff8;
            --card:#ffffff;
            --text:#1f2937;
            --muted:#6b7280;
            --primary:#28a745;
            --accent:#ffc107;
            --danger:#dc2626;
            --danger-bg:#fee2e2;
            --danger-border:#fca5a5;
            --success:#16a34a;
            --success-bg:#dcfce7;
            --success-border:#86efac;
            --border:#e5e7eb;
            --radius:14px;
            --shadow:0 10px 30px rgba(0,0,0,.08);
        }

        body {
            background:var(--bg);
            min-height:100vh;
            display:flex;
            justify-content:center;
            align-items:center;
            padding:20px;
            position:relative;
        }

        .back-arrow {
            position:absolute;
            top:20px;
            left:20px;
            width:42px;
            height:42px;
            border-radius:50%;
            display:flex;
            align-items:center;
            justify-content:center;
            background:var(--card);
            border:1px solid var(--border);
            text-decoration:none;
            color:var(--text);
            font-size:18px;
            box-shadow:var(--shadow);
            transition:.2s;
        }
        .back-arrow:hover {
            background:var(--accent);
            color:black;
        }

        .card {
            width:500px;
            padding:24px;
            border-radius:var(--radius);
            box-shadow:var(--shadow);
        }

        h5 {
            color:var(--text);
        }

        label {
            font-size:13px;
            color:var(--muted);
            margin-top:10px;
        }

        .btn-save {
            margin-top:18px;
            width:100%;
            background:var(--primary);
            color:#fff;
            border:none;
            border-radius:10px;
            padding:10px;
        }

        .server-msg {
            text-align:center;
            padding:10px;
            margin-bottom:12px;
            border-radius:10px;
            font-size:13px;
        }
        .server-msg.error {
            background:var(--danger-bg);
            color:var(--danger);
            border:1px solid var(--danger-border);
        }
        .server-msg.success {
            background:var(--success-bg);
            color:var(--success);
            border:1px solid var(--success-border);
        }
    </style>
</head>

<body>

<!-- Back arrow -->
<a href="${pageContext.request.contextPath}/product/list" class="back-arrow">←</a>

<div class="card">

    <h5 class="text-center mb-3">Add Product</h5>

    <!-- Server Message -->
    <c:if test="${not empty message}">
        <div class="server-msg ${messageType}">
            ${message}
        </div>
    </c:if>

    <form action="${pageContext.request.contextPath}/product/add" method="post">

        <label>Identifier</label>
        <input
                type="text"
                name="identifier"
                value="${productDto.identifier}"
                class="form-control"
                required>

        <label>Product Name</label>
        <input
                type="text"
                name="name"
                value="${productDto.name}"
                class="form-control"
                required>

        <label>SKU</label>
        <input
                type="text"
                name="sku"
                value="${productDto.sku}"
                class="form-control"
                required>

        <label>Description</label>
        <textarea
                name="description"
                class="form-control"
                rows="3">${productDto.description}</textarea>

        <label>Status</label>
        <select name="status" class="form-control">
            <option value="ACTIVE"
                <c:if test="${productDto.status == 'ACTIVE'}">selected</c:if>>
                ACTIVE
            </option>
            <option value="INACTIVE"
                <c:if test="${productDto.status == 'INACTIVE'}">selected</c:if>>
                INACTIVE
            </option>
        </select>

        <button type="submit" class="btn-save">Save Product</button>
    </form>

</div>

</body>
</html>