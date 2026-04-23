<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Edit Price</title>

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

        body {
            background:var(--bg);
            min-height:100vh;
            display:flex;
            justify-content:center;
            align-items:center;
            padding:20px;
            position:relative;
            color:var(--text);
        }

        .back-arrow {
            position:absolute;
            top:20px;
            left:20px;
            width:42px;
            height:42px;
            border-radius:50%;
            background:var(--card);
            border:1px solid var(--border);
            display:flex;
            align-items:center;
            justify-content:center;
            color:var(--text);
            font-size:18px;
            box-shadow:var(--shadow);
            text-decoration:none;
            transition:.2s;
        }

        .back-arrow:hover {
            background:var(--accent);
            color:black;
        }

        .card {
            width:480px;
            padding:24px;
            border-radius:var(--radius);
            box-shadow:var(--shadow);
            background:var(--card);
        }

        label {
            font-size:13px;
            color:var(--muted);
            margin-top:10px;
        }

        .btn-update {
            margin-top:18px;
            width:100%;
            background:var(--primary);
            color:#fff;
            border:none;
            border-radius:10px;
            padding:10px;
            font-weight:600;
        }

        .btn-update:hover {
            background:var(--primary-hover);
        }

        .server-msg {
            text-align:center;
            padding:10px;
            border-radius:10px;
            margin-bottom:12px;
            font-size:13px;
        }

        .server-msg.error {
            background:var(--danger-bg);
            color:var(--danger);
            border:1px solid var(--danger-border);
        }
    </style>
</head>

<body>

<a href="${pageContext.request.contextPath}/price/list" class="back-arrow">←</a>

<div class="card">

    <h5 class="text-center mb-3">Edit Price</h5>

    <c:if test="${not empty message}">
        <div class="server-msg error">${message}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/price/update" method="post">

        <input type="hidden" name="id" value="${price.id}" />

        <label>Product</label>
        <select name="productId" class="form-control" required>
            <c:forEach items="${products}" var="product">
                <option value="${product.id}"
                    <c:if test="${product.id == price.productId}">selected</c:if>>
                    ${product.name}
                </option>
            </c:forEach>
        </select>

        <label>Selling Price</label>
        <input type="number" step="0.01"
               name="sellingPrice"
               value="${price.sellingPrice}"
               class="form-control"
               required />

        <label>Cost Price</label>
        <input type="number" step="0.01"
               name="costPrice"
               value="${price.costPrice}"
               class="form-control"
               required />


        <button type="submit" class="btn-update">Update Price</button>

    </form>

</div>

</body>
</html>