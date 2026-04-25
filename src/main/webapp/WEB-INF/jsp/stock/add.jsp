<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Add Stock</title>

    <style>
        body {
            margin: 0;
            font-family: 'Inter', sans-serif;
            background: linear-gradient(135deg, #ede9fe, #ddd6fe);
        }

        .container {
            width: 400px;
            margin: 80px auto;
            background: #fff;
            padding: 25px;
            border-radius: 14px;
            box-shadow: 0 15px 35px rgba(76, 29, 149, 0.18);
        }

        h2 {
            text-align: center;
            color: #6d28d9;
        }

        label {
            display: block;
            margin-top: 12px;
            font-weight: 600;
            color: #4c1d95;
        }

        input, select {
            width: 100%;
            padding: 10px;
            margin-top: 6px;
            border: 1px solid #c4b5fd;
            border-radius: 8px;
            font-size: 14px;
        }

        input:focus, select:focus {
            outline: none;
            border-color: #7c3aed;
            box-shadow: 0 0 0 2px rgba(124, 58, 237, 0.2);
        }

        button {
            width: 100%;
            margin-top: 20px;
            padding: 10px;
            background: #7c3aed;
            color: white;
            border: none;
            border-radius: 8px;
            font-weight: 600;
            cursor: pointer;
        }

        button:hover {
            background: #6d28d9;
        }

        a {
            display: block;
            text-align: center;
            margin-top: 12px;
            color: #6d28d9;
            font-weight: 600;
            text-decoration: none;
        }

        a:hover {
            text-decoration: underline;
        }

        .error {
            color: red;
            text-align: center;
            margin-bottom: 10px;
            font-size: 13px;
        }
    </style>
</head>

<body>

<div class="container">

    <h2>Add Stock</h2>

    <!-- Error Message -->
    <c:if test="${not empty message}">
        <div class="error">${message}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/stock/add" method="post">

        <!-- Product Dropdown instead of Identifier input -->
        <label>Product</label>
        <select name="identifier" required>
            <option value="">-- Select Product --</option>
            <c:forEach var="product" items="${products}">
                <option value="${product.identifier}">
                    ${product.identifier}
                </option>
            </c:forEach>
        </select>

        <!--  Warehouse Dropdown like Product -->
        <label>Warehouse</label>
        <select name="warehouse" required>
            <option value="">-- Select Warehouse --</option>
            <c:forEach var="warehouse" items="${warehouses}">
                <option value="${warehouse.identifier}">
                    ${warehouse.location}
                </option>
            </c:forEach>
        </select>

        <!-- Available Quantity -->
        <label>Available Quantity</label>
        <input type="number" name="availableQuantity" required />

        <!-- Outgoing Quantity -->
        <label>Outgoing Quantity</label>
        <input type="number" name="outgoingQuantity" required />



        <!-- Status -->
        <label>Status</label>
        <select name="status" required>
            <option value="">-- Select Status --</option>
            <option value="AVAILABLE">Available</option>
            <option value="LOW_STOCK">Low Stock</option>
            <option value="OUT_OF_STOCK">Out of Stock</option>
        </select>

        <button type="submit">Save</button>
    </form>

    <a href="${pageContext.request.contextPath}/stock/list">
        ← Back to Stock List
    </a>

</div>

</body>
</html>
