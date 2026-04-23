<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Edit Product</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        :root {
            --bg:#f6fff8;
            --card:#ffffff;
            --text:#1f2937;
            --muted:#6b7280;

            --primary:#28a745;
            --primary-hover:#218838;
            --accent:#ffc107;

            --danger:#dc2626;
            --danger-bg:#fee2e2;
            --danger-border:#fca5a5;

            --border:#e5e7eb;
            --radius:14px;
            --shadow:0 10px 30px rgba(0,0,0,0.08);
        }

        * {
            font-family: 'Inter', sans-serif;
            box-sizing: border-box;
        }

        body {
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            background: var(--bg);
            padding: 20px;
            position: relative;
            color: var(--text);
        }

        .back-arrow {
            position: absolute;
            top: 20px;
            left: 20px;
            width: 42px;
            height: 42px;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            background: var(--card);
            border: 1px solid var(--border);
            text-decoration: none;
            color: var(--text);
            font-size: 18px;
            box-shadow: var(--shadow);
            transition: .2s;
        }

        .back-arrow:hover {
            background: var(--accent);
            color: black;
        }

        .container-box {
            width: 100%;
            max-width: 480px;
        }

        .card {
            background: var(--card);
            border-radius: var(--radius);
            box-shadow: var(--shadow);
            padding: 24px;
        }

        h2 {
            text-align: center;
            font-size: 18px;
            font-weight: 600;
            margin-bottom: 18px;
        }

        label {
            font-size: 13px;
            color: var(--muted);
            margin-top: 10px;
        }

        .form-control {
            margin-top: 6px;
            border-radius: 10px;
            padding: 10px;
            border: 1px solid var(--border);
        }

        .form-control:focus {
            border-color: var(--primary);
            box-shadow: 0 0 0 3px rgba(40,167,69,0.15);
        }

        .btn-update {
            margin-top: 18px;
            width: 100%;
            padding: 10px;
            border-radius: 10px;
            border: none;
            background: var(--primary);
            color: white;
            font-weight: 600;
        }

        .btn-update:hover {
            background: var(--primary-hover);
        }

        .server-msg {
            text-align: center;
            padding: 10px;
            border-radius: 10px;
            margin-bottom: 12px;
            font-size: 13px;
        }

        .server-msg.error {
            background: var(--danger-bg);
            color: var(--danger);
            border: 1px solid var(--danger-border);
        }
    </style>
</head>

<body>

<a href="${pageContext.request.contextPath}/product/list" class="back-arrow">
    ←
</a>

<div class="container-box">
    <div class="card">

        <h2>Edit Product</h2>

        <c:if test="${not empty message}">
            <div class="server-msg error">
                ${message}
            </div>
        </c:if>

        <form action="${pageContext.request.contextPath}/product/update"
              method="post">

            <!-- REQUIRED -->
            <input type="hidden" name="id" value="${product.id}" />

            <label>Product Name</label>
            <input type="text"
                   name="name"
                   value="${product.name}"
                   class="form-control"
                   required />

            <label>SKU</label>
            <input type="text"
                   name="sku"
                   value="${product.sku}"
                   class="form-control"
                   required />

            <label>Description</label>
            <input type="text"
                   name="description"
                   value="${product.description}"
                   class="form-control"
                   required />

            <label>Status</label>
            <select name="status" class="form-control" required>
                <option value="ACTIVE"
                    <c:if test="${product.status == 'ACTIVE'}">selected</c:if>>
                    ACTIVE
                </option>
                <option value="INACTIVE"
                    <c:if test="${product.status == 'INACTIVE'}">selected</c:if>>
                    INACTIVE
                </option>
            </select>

            <button type="submit" class="btn-update">
                Update Product
            </button>

        </form>

    </div>
</div>

</body>
</html>