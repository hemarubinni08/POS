<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Add Stock</title>

    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600&display=swap" rel="stylesheet">

    <style>
        body {
            margin: 0;
            font-family: 'Inter', sans-serif;
            background: #d1d5db;

            /* ✅ CENTER CONTENT */
            height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .container {
            width: 420px;
            background: #f1f5f9;
            padding: 30px;
            border-radius: 16px;
            box-shadow: 0 10px 25px rgba(0,0,0,0.1);
        }

        h2 {
            text-align: center;
            color: #0891b2;
            font-size: 22px;
            font-weight: 600;
            margin-bottom: 15px;
        }

        label {
            display: block;
            margin-top: 14px;
            font-weight: 600;
            font-size: 13px;
            color: #334155;
        }

        input, select {
            width: 100%;
            padding: 10px;
            margin-top: 6px;
            border: 1px solid #cbd5f5;
            border-radius: 8px;
            font-size: 13px;
            outline: none;
            transition: 0.2s;
        }

        input:focus, select:focus {
            border-color: #0891b2;
            box-shadow: 0 0 0 2px rgba(8,145,178,0.2);
        }

        button {
            width: 100%;
            margin-top: 22px;
            padding: 12px;
            background: linear-gradient(135deg, #0891b2, #0e7490);
            color: white;
            border: none;
            border-radius: 20px;
            font-weight: 600;
            cursor: pointer;
            transition: 0.25s;
        }

        button:hover {
            background: linear-gradient(135deg, #0e7490, #075985);
            transform: translateY(-2px);
            box-shadow: 0 6px 15px rgba(8,145,178,0.4);
        }

        a {
            display: block;
            text-align: center;
            margin-top: 15px;
            color: #0891b2;
            font-weight: 600;
            text-decoration: none;
            font-size: 13px;
        }

        a:hover {
            color: #0e7490;
        }

        .error {
            background: #fee2e2;
            color: #b91c1c;
            padding: 10px;
            border-radius: 8px;
            margin-bottom: 10px;
            font-size: 13px;
            text-align: center;
            font-weight: 500;
        }
    </style>
</head>

<body>

<div class="container">

    <h2>Add Stock</h2>

    <!-- ERROR MESSAGE -->
    <c:if test="${not empty message}">
        <div class="error">${message}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/stock/add" method="post">

        <!-- PRODUCT -->
        <label>Product</label>
        <select name="identifier" required>
            <option value="">-- Select Product --</option>
            <c:forEach var="product" items="${products}">
                <option value="${product.identifier}">
                    ${product.identifier}
                </option>
            </c:forEach>
        </select>

        <!-- WAREHOUSE -->
        <label>Warehouse</label>
        <select name="warehouse" required>
            <option value="">-- Select Warehouse --</option>
            <c:forEach var="warehouse" items="${warehouses}">
                <option value="${warehouse.identifier}">
                    ${warehouse.location}
                </option>
            </c:forEach>
        </select>

        <!-- AVAILABLE -->
        <label>Available Quantity</label>
        <input type="number" name="availableQuantity" required />

        <!-- OUTGOING -->
        <label>Outgoing Quantity</label>
        <input type="number" name="outgoingQuantity" required />

        <!-- SUBMIT -->
        <button type="submit">Save</button>
    </form>

    <a href="${pageContext.request.contextPath}/stock/list">
        ← Back to Stock List
    </a>

</div>

</body>
</html>