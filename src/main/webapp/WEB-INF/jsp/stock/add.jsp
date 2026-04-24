<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Add Stock</title>

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Font -->
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600&display=swap" rel="stylesheet">

    <style>
        :root {
                    --primary: #2563eb;
                    --primary-hover: #1e40af;

                    --bg: #f8fafc;
                    --glass: rgba(255,255,255,0.75);

                    --text: #0f172a;
                    --muted: #64748b;

                    --border: #e2e8f0;

                    --danger: #dc2626;
                    --success: #16a34a;

                    --radius: 16px;
                    --shadow: 0 20px 40px rgba(2,6,23,0.08);
                }

        * {
            font-family: 'Inter', sans-serif;
            box-sizing: border-box;
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

        /* Back Button */
        .back-arrow {
                                position: absolute;
                                top: 20px;
                                left: 20px;
                                width: 42px;
                                height: 42px;

                                display: flex;
                                align-items: center;
                                justify-content: center;

                                border-radius: 50%;
                                background: var(--glass);
                                backdrop-filter: blur(10px);

                                border: 1px solid var(--border);
                                color: var(--text);

                                text-decoration: none;
                                font-size: 18px;

                                box-shadow: var(--shadow);
                                transition: 0.2s;
                            }

        .back-arrow:hover {
            background:var(--accent);
            transform:scale(1.05);
        }

        .card {
            width:460px;
            padding:26px;
            border-radius:var(--radius);
            box-shadow:var(--shadow);
            background:var(--card);
        }

        h5 {
            text-align:center;
            font-weight:600;
            margin-bottom:18px;
            color:var(--text);
        }

        label {
            font-size:13px;
            color:var(--muted);
            margin-top:12px;
        }

        .form-control, .form-select {
            margin-top:6px;
            border-radius:10px;
            padding:10px;
            border:1px solid var(--border);
        }

        .form-control:focus, .form-select:focus {
            border-color:var(--primary);
            box-shadow:0 0 0 3px rgba(40,167,69,0.15);
        }

        .btn-save {
            margin-top:22px;
            width:100%;
            background:var(--primary);
            color:#fff;
            border:none;
            border-radius:10px;
            padding:11px;
            font-weight:600;
            transition:.2s;
        }

        .btn-save:hover {
            background:var(--primary-hover);
        }

        .server-msg {
            text-align:center;
            padding:10px;
            margin-bottom:14px;
            border-radius:10px;
            font-size:13px;
        }

        .server-msg.error {
            background:var(--danger-bg);
            color:var(--danger);
            border:1px solid var(--danger-border);
        }

        .server-msg.success {
            background:var(--success-bg);
            color:var(--success);
            border:1px solid var(--success-border);
        }
    </style>
</head>

<body>

<!-- Back -->
<a href="${pageContext.request.contextPath}/stock/list" class="back-arrow">←</a>

<div class="card">

    <h5>Add Stock</h5>

    <!-- Server Message -->
    <c:if test="${not empty message}">
        <div class="server-msg ${messageType}">
            ${message}
        </div>
    </c:if>

    <form:form modelAttribute="stockDto"
               method="post"
               action="${pageContext.request.contextPath}/stock/add">

        <!-- Product -->
        <label>Product</label>
        <form:select path="productId" cssClass="form-select" required="true">
            <form:option value="" label="-- Select Product --"/>
            <c:forEach items="${products}" var="product">
                <form:option value="${product.id}">
                    ${product.name}
                </form:option>
            </c:forEach>
        </form:select>

        <!-- Warehouse -->
        <label>Warehouse</label>
        <form:select path="warehouseId" cssClass="form-select" required="true">
            <form:option value="" label="-- Select Warehouse --"/>
            <c:forEach items="${warehouses}" var="warehouse">
                <form:option value="${warehouse.id}">
                    ${warehouse.name}
                </form:option>
            </c:forEach>
        </form:select>

        <!-- Quantity -->
        <label>Quantity</label>
        <form:input path="quantity"
                    type="number"
                    min="0"
                    cssClass="form-control"
                    required="true"/>

        <button type="submit" class="btn-save">
            Save Stock
        </button>

    </form:form>

</div>

</body>
</html>