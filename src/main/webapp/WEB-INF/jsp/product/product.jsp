<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Edit Product</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
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
                padding: 20px;
                position: relative;
                color: var(--text);
            }

            /* BACK BUTTON */
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
                background: #eef2ff;
                color: var(--primary);
            }

            .container-box {
                width: 100%;
                max-width: 500px;
            }

            /* CARD */
            .card {
                padding: 28px;

                border-radius: var(--radius);

                background: var(--glass);
                backdrop-filter: blur(16px);

                border: 1px solid var(--border);
                box-shadow: var(--shadow);
            }

            h2 {
                text-align: center;
                font-size: 18px;
                font-weight: 600;
                margin-bottom: 20px;
            }

            /* FORM */
            label {
                font-size: 13px;
                color: var(--muted);
                margin-top: 12px;
                display: block;
            }

            .form-control {
                margin-top: 6px;
                border-radius: 10px;
                padding: 10px;
                border: 1px solid var(--border);
                font-size: 14px;
            }

            .form-control:focus {
                border-color: var(--primary);
                box-shadow: 0 0 0 3px rgba(37,99,235,0.15);
            }

            /* BUTTON */
            .btn-update {
                margin-top: 20px;
                width: 100%;

                padding: 11px;
                border-radius: 10px;

                border: none;
                background: var(--primary);
                color: white;

                font-weight: 600;
                transition: 0.2s;
            }

            .btn-update:hover {
                background: var(--primary-hover);
                transform: translateY(-1px);
            }

            /* SERVER MESSAGE */
            .server-msg {
                text-align: center;
                padding: 10px;
                border-radius: 10px;
                margin-bottom: 14px;
                font-size: 13px;
            }

            .server-msg.error {
                background: var(--danger-bg);
                color: var(--danger);
                border: 1px solid var(--danger-border);
            }
      </style>
</head>

<body>

<a href="${pageContext.request.contextPath}/product/list" class="back-arrow">←</a>

<div class="container-box">
    <div class="card">

        <h2>Edit Product</h2>

        <c:if test="${not empty message}">
            <div class="server-msg error">${message}</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/product/update" method="post">

            <input type="hidden" name="id" value="${product.id}" />

            <label>Product Name</label>
            <input type="text" name="identifier" value="${product.identifier}" class="form-control" required />

            <label>Categories</label>
            <select name="categoryIdentifiers" class="form-control" multiple required>
                <c:forEach items="${categories}" var="category">
                    <option value="${category.identifier}"
                        <c:if test="${product.categoryIdentifiers.contains(category.identifier)}">selected</c:if>>
                        ${category.identifier}
                    </option>
                </c:forEach>
            </select>

            <label>SKU</label>
            <input type="text" name="sku" value="${product.sku}" class="form-control" required />

            <label>Description</label>
            <input type="text" name="description" value="${product.description}" class="form-control" required />

            <button type="submit" class="btn-update">Update Product</button>
        </form>

    </div>
</div>

</body>
</html>