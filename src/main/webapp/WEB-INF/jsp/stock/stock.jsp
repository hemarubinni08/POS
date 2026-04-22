<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Edit Stock</title>

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        :root {
            --bg: #f6fff8;
            --card: #ffffff;
            --text: #1f2937;
            --muted: #6b7280;
            --primary: #28a745;
            --primary-hover: #218838;
            --accent: #ffc107;
            --border: #e5e7eb;
            --radius: 14px;
            --shadow: 0 10px 30px rgba(0,0,0,0.08);
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
        }

        .back-arrow {
            position: absolute;
            top: 20px;
            left: 20px;
            background: var(--card);
            border: 1px solid var(--border);
            border-radius: 50%;
            width: 42px;
            height: 42px;
            display: flex;
            align-items: center;
            justify-content: center;
            text-decoration: none;
            color: var(--text);
            font-size: 18px;
            box-shadow: var(--shadow);
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
            color: var(--text);
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

        .info-box {
            background: #f9fafb;
            border: 1px solid var(--border);
            padding: 10px;
            border-radius: 10px;
            font-size: 13px;
            margin-bottom: 12px;
        }
    </style>
</head>

<body>

<a href="${pageContext.request.contextPath}/stock/list" class="back-arrow">
    ←
</a>

<div class="container-box">
    <div class="card">

        <h2>Edit Stock</h2>

        <!-- Read-only info -->
        <div class="info-box">
            <div><b>Product Name:</b> ${stock.productName}</div>
            <div><b>Warehouse Name:</b> ${stock.warehouseName}</div>
        </div>

        <form action="${pageContext.request.contextPath}/stock/update"
              method="post">

            <!-- REQUIRED -->
            <input type="hidden" name="stockId" value="${stock.id}" />

            <label>Quantity</label>
            <input type="number"
                   name="quantity"
                   value="${stock.quantity}"
                   min="0"
                   class="form-control"
                   required />

            <button type="submit" class="btn-update">
                Update Stock
            </button>

        </form>

    </div>
</div>

</body>
</html>
