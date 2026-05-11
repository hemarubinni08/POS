<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add Stock</title>

    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600&display=swap"
          rel="stylesheet">

    <style>
        body {
            margin: 0;
            font-family: 'Poppins', sans-serif;
            min-height: 100vh;
            background: #f4f6f9;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .card-container {
            position: relative;
            width: 520px;
            background: #ffffff;
            padding: 40px 45px;
            border-radius: 18px;
            box-shadow: 0 12px 35px rgba(0, 0, 0, 0.12);
        }

        h2 {
            text-align: center;
            margin-bottom: 25px;
        }

        .back-icon {
            position: absolute;
            top: 16px;
            left: 16px;
            width: 36px;
            height: 36px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 18px;
            text-decoration: none;
            background: #f0f0f0;
            border-radius: 50%;
        }

        .form-group {
            margin-bottom: 18px;
        }

        label {
            display: block;
            margin-bottom: 6px;
            font-size: 13px;
            font-weight: 500;
        }

        .form-control {
            width: 100%;
            padding: 11px;
            border-radius: 10px;
            border: 1px solid #ddd;
        }

        .btn-submit {
            width: 100%;
            padding: 12px;
            border-radius: 10px;
            border: none;
            background: #4a90e2;
            color: white;
            cursor: pointer;
        }

        .error-message {
            background: #ffe5e5;
            padding: 10px;
            margin-bottom: 12px;
            border-radius: 8px;
        }
    </style>
</head>

<body>

<div class="card-container">

    <a href="/stock/list" class="back-icon">←</a>

    <h2>Add New Stock</h2>

    <c:if test="${not empty message}">
        <div class="error-message">${message}</div>
    </c:if>

    <form action="/stock/add" method="post">

        <div class="form-group">
            <label>Product</label>
            <select name="identifier" class="form-control" required>
                <option value="">-- Select Product --</option>
                <c:forEach var="product" items="${products}">
                    <option value="${product.identifier}">
                        ${product.identifier}
                    </option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group">
            <label>Warehouse</label>
            <select name="warehouseIdentifier" class="form-control" required>
                <option value="">-- Select Warehouse --</option>
                <c:forEach var="warehouse" items="${warehouses}">
                    <option value="${warehouse.identifier}">
                        ${warehouse.location}
                    </option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group">
            <label>Available Quantity</label>
            <input type="number" name="availableQuantity"
                   class="form-control" required/>
        </div>

        <div class="form-group">
            <label>Outgoing Quantity</label>
            <input type="number" name="outgoingQuantity"
                   class="form-control" required/>
        </div>

        <div class="form-group">
            <label>Status</label>
            <select name="status" class="form-control" required>
                <option value="">-- Select Status --</option>
                <option value="true">Active</option>
                <option value="false">Inactive</option>
            </select>
        </div>

        <button type="submit" class="btn-submit">
            Add Stock
        </button>

    </form>

</div>

</body>
</html>