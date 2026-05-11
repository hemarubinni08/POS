<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Update Stock</title>

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
            width: 480px;
            background: #ffffff;
            padding: 35px 40px;
            border-radius: 18px;
            box-shadow: 0 12px 35px rgba(0, 0, 0, 0.12);
            border: 1px solid #e6e6e6;
            color: #333;
        }

        h2 {
            text-align: center;
            margin-bottom: 25px;
            color: #222;
            font-weight: 600;
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
            color: #333;
        }

        .form-group {
            margin-bottom: 18px;
        }

        label {
            display: block;
            margin-bottom: 6px;
            font-weight: 500;
            font-size: 13px;
        }

        .form-control {
            width: 100%;
            padding: 11px 14px;
            border-radius: 10px;
            border: 1px solid #ddd;
        }

        .alert {
            padding: 12px;
            border-radius: 10px;
            margin-bottom: 15px;
            text-align: center;
            font-size: 13px;
            background: #ffe5e5;
            border: 1px solid #ffb3b3;
        }

        .btn-group {
            display: flex;
            gap: 10px;
        }

        .btn {
            padding: 10px;
            border-radius: 10px;
            border: none;
            cursor: pointer;
            width: 100%;
        }

        .btn-submit {
            background: #4a90e2;
            color: white;
        }

        .btn-cancel {
            background: #ccc;
        }
    </style>
</head>

<body>

<div class="card-container">

    <a href="/stock/list" class="back-icon">←</a>

    <h2>Edit Stock</h2>

    <c:if test="${empty stock}">
        <div class="alert">Stock not found</div>
    </c:if>

    <c:if test="${not empty stock}">
        <form:form action="/stock/update" method="post" modelAttribute="stock">
            <form:hidden path="identifier"/>
            <div class="form-group">
                <label>Warehouse</label>
                <select name="warehouse" class="form-control" required>
                    <option value="">-- Select Warehouse --</option>
                    <c:forEach var="warehouseObj" items="${warehouses}">
                        <option value="${warehouseObj.identifier}"
                            <c:if test="${warehouseObj.identifier == stock.warehouse}">
                                selected
                            </c:if>>
                            ${warehouseObj.location}
                        </option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <label>Available Quantity</label>
                <form:input path="availableQuantity"
                            cssClass="form-control"
                            required="true"/>
            </div>
            <div class="form-group">
                <label>Outgoing Quantity</label>
                <form:input path="outgoingQuantity"
                            cssClass="form-control"
                            required="true"/>
            </div>
            <div class="form-group">
                <label>Status</label>
                <form:select path="status" cssClass="form-control">
                    <form:option value="true" label="Available"/>
                    <form:option value="false" label="Not Available"/>
                </form:select>
            </div>

            <div class="btn-group">
                <a href="/stock/list" class="btn btn-cancel">Cancel</a>
                <button type="submit" class="btn btn-submit">Update</button>
            </div>

        </form:form>
    </c:if>
</div>
</body>
</html>