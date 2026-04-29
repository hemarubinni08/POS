<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Edit Stock</title>

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
        }

        button {
            width: 100%;
            margin-top: 20px;
            padding: 10px;
            background: #7c3aed;
            color: white;
            border: none;
            border-radius: 8px;
        }

        button:hover {
            background: #6d28d9;
        }

        a {
            display: block;
            text-align: center;
            margin-top: 10px;
            color: #6d28d9;
        }
    </style>
</head>

<body>
<div class="container">

    <h2>Edit Stock</h2>

    <form action="${pageContext.request.contextPath}/stock/update" method="post">

        <input type="hidden" name="id" value="${stock.id}">
        <input type="hidden" name="identifier" value="${stock.identifier}">

        <!-- Warehouse -->

<label>Warehouse</label>
<select name="warehouse" required>
    <option value="">-- Select Warehouse --</option>

    <c:forEach var="wh" items="${warehouses}">
        <option value="${wh.identifier}"
            ${wh.identifier == stock.warehouse ? 'selected' : ''}>
            ${wh.location}
        </option>
    </c:forEach>

</select>


        <label>Available Quantity</label>
        <input type="number" name="availableQuantity" value="${stock.availableQuantity}" required>

        <label>Outgoing Quantity</label>
        <input type="number" name="outgoingQuantity" value="${stock.outgoingQuantity}" required>

        <!-- STATUS -->

<label>Status</label>
<select name="status" required>
    <option value="true" ${stock.status == true ? 'selected' : ''}>
        Available
    </option>
    <option value="false" ${stock.status == false ? 'selected' : ''}>
        Out of Stock
    </option>
</select>


        <button type="submit">Update</button>
    </form>

    <a href="${pageContext.request.contextPath}/stock/list">Back</a>

</div>
</body>
</html>