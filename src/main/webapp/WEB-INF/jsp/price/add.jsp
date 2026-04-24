<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Add Price</title>

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Font -->
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600&display=swap" rel="stylesheet">

    <style>
        :root {
            --bg: #f6fff8;
            --card: #ffffff;

            --text: #1f2937;
            --muted: #6b7280;

            --primary: #28a745;
            --primary-hover: #218838;
            --accent: #ffc107;

            --danger: #dc2626;
            --danger-bg: #fee2e2;
            --danger-border: #fca5a5;

            --border: #e5e7eb;

            --radius: 14px;
            --shadow: 0 10px 30px rgba(0,0,0,0.08);
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
            padding: 40px 16px;
            color: var(--text);
        }

        /* BACK BUTTON */
        .back-arrow {
            position: fixed;
            top: 20px;
            left: 20px;
            width: 42px;
            height: 42px;
            border-radius: 50%;
            background: var(--card);
            border: 1px solid var(--border);
            display: flex;
            align-items: center;
            justify-content: center;
            color: var(--text);
            font-size: 18px;
            text-decoration: none;
            box-shadow: var(--shadow);
            transition: 0.2s;
        }

        .back-arrow:hover {
            background: var(--accent);
            color: #000;
        }

        /* CARD */
        .form-card {
            width: 520px;
            background: var(--card);
            padding: 28px;
            border-radius: var(--radius);
            box-shadow: var(--shadow);
        }

        h2 {
            text-align: center;
            margin-bottom: 22px;
            font-weight: 600;
        }

        /* FORM */
        label {
            font-size: 13px;
            color: var(--muted);
            font-weight: 500;
            margin-bottom: 4px;
        }

        .form-control {
            border-radius: 10px;
            border: 1px solid var(--border);
            padding: 10px;
            font-size: 14px;
        }

        .form-control:focus {
            border-color: var(--primary);
            box-shadow: 0 0 0 3px rgba(40,167,69,0.15);
        }

        /* BUTTON */
        .btn-submit {
            margin-top: 22px;
            background: var(--primary);
            color: white;
            border-radius: 10px;
            padding: 10px;
            font-weight: 600;
            border: none;
            width: 100%;
        }

        .btn-submit:hover {
            background: var(--primary-hover);
        }

        /* SERVER MESSAGE */
        .server-msg {
            background: var(--danger-bg);
            border: 1px solid var(--danger-border);
            color: var(--danger);
            padding: 10px;
            border-radius: 10px;
            font-size: 13px;
            text-align: center;
            margin-bottom: 14px;
        }
    </style>
</head>

<body>

<!-- BACK -->
<a href="${pageContext.request.contextPath}/price/list" class="back-arrow">←</a>

<div class="form-card">

    <h2>Add Price</h2>

    <!-- SERVER MESSAGE -->
    <c:if test="${not empty message}">
        <div class="server-msg">
            ${message}
        </div>
    </c:if>

    <form action="${pageContext.request.contextPath}/price/add" method="post">

        <!-- PRODUCT -->
        <div class="mb-3">
            <label>Product</label>
            <select name="productId" class="form-control" required>
                <option value="">-- Select Product --</option>
                <c:forEach items="${products}" var="product">
                    <option value="${product.id}"
                        <c:if test="${priceDto.productId == product.id}">selected</c:if>>
                        ${product.name}
                    </option>
                </c:forEach>
            </select>
        </div>

        <!-- SELLING PRICE -->
        <div class="mb-3">
            <label>Selling Price</label>
            <input type="number"
                   step="0.01"
                   min="0"
                   name="sellingPrice"
                   value="${priceDto.sellingPrice}"
                   class="form-control"
                   required>
        </div>

        <!-- COST PRICE -->
        <div class="mb-3">
            <label>Cost Price</label>
            <input type="number"
                   step="0.01"
                   min="0"
                   name="costPrice"
                   value="${priceDto.costPrice}"
                   class="form-control"
                   required>
        </div>

        <button type="submit" class="btn-submit">
            Save Price
        </button>

    </form>

</div>

</body>
</html>