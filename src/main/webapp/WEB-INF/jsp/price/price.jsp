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
            --primary:#28a745;
            --primary-hover:#218838;
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
        }

        .card {
            width:480px;
            padding:24px;
            border-radius:var(--radius);
            box-shadow:var(--shadow);
            background:var(--card);
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
            color:#1f2937;
            text-decoration:none;
        }

        label {
            font-size:13px;
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
        }
    </style>
</head>

<body>

<a href="${pageContext.request.contextPath}/price/list" class="back-arrow">←</a>

<div class="card">

    <h5 class="text-center mb-3">Edit Price</h5>

    <form action="${pageContext.request.contextPath}/price/update" method="post">

        <input type="hidden" name="id" value="${price.id}"/>

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
        <input type="number" step="0.01" name="sellingPrice"
               value="${price.sellingPrice}" class="form-control" required>

        <label>Cost Price</label>
        <input type="number" step="0.01" name="costPrice"
               value="${price.costPrice}" class="form-control" required>

        <label>Status</label>
        <select name="status" class="form-control">
            <option value="ACTIVE" <c:if test="${price.status=='ACTIVE'}">selected</c:if>>
                ACTIVE
            </option>
            <option value="INACTIVE" <c:if test="${price.status=='INACTIVE'}">selected</c:if>>
                INACTIVE
            </option>
        </select>

        <button class="btn-update">Update Price</button>

    </form>

</div>

</body>
</html>