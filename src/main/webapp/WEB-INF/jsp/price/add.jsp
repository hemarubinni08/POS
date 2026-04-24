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
                          --primary: #2563eb;
                          --primary-hover: #1e40af;

                          --bg: #f8fafc;
                          --glass: rgba(255,255,255,0.75);

                          --text: #0f172a;
                          --muted: #64748b;

                          --border: #e2e8f0;

                          --danger: #dc2626;
                          --danger-bg: #fee2e2;
                          --danger-border: #fca5a5;

                          --success: #16a34a;
                          --success-bg: #dcfce7;
                          --success-border: #86efac;

                          --radius: 16px;
                          --shadow: 0 20px 40px rgba(2,6,23,0.08);
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
                                position: absolute;
                                top: 20px;
                                left: 20px;
                                width: 42px;
                                height: 42px;
                                border-radius: 50%;

                                display: flex;
                                align-items: center;
                                justify-content: center;

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
                                      background: #eef2ff;
                                      color: var(--primary);
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