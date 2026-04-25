<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Edit Product</title>

    <style>
        body {
            font-family: "Segoe UI", Arial, sans-serif;
            background: linear-gradient(135deg, #ede9fe, #ddd6fe); /* same as Node */
            margin: 0;
            min-height: 100vh;
        }

        .container {
            width: 420px;
            margin: 80px auto;
            background: #ffffff;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 20px 40px rgba(76, 29, 149, 0.18);
        }

        h2 {
            text-align: center;
            margin-bottom: 24px;
            color: #6d28d9;
            font-weight: 600;
        }

        label {
            margin-top: 16px;
            display: block;
            font-weight: 600;
            font-size: 13px;
            color: #4c1d95;
        }

        input, textarea {
            width: 100%;
            margin-top: 6px;
            padding: 9px;
            border: 1px solid #c4b5fd;
            border-radius: 6px;
            font-size: 13px;
        }

        textarea {
            height: 80px;
            resize: none;
        }

        input:focus, textarea:focus {
            outline: none;
            border-color: #a78bfa;
            box-shadow: 0 0 0 0.15rem rgba(167, 139, 250, 0.35);
        }

        button {
            margin-top: 26px;
            width: 100%;
            padding: 11px;
            background: #7c3aed;
            color: #ffffff;
            border: none;
            font-weight: 600;
            border-radius: 6px;
            cursor: pointer;
        }

        button:hover {
            background: #6d28d9;
        }

        a {
            display: block;
            text-align: center;
            margin-top: 18px;
            color: #6d28d9;
            font-weight: 600;
            text-decoration: none;
            font-size: 13px;
        }

        a:hover {
            text-decoration: underline;
            color: #5b21b6;
        }

        /* optional error message support */
        .error-message {
            background: #fee2e2;
            color: #b91c1c;
            border: 1px solid #fca5a5;
            padding: 10px;
            border-radius: 8px;
            font-size: 13px;
            text-align: center;
            margin-bottom: 16px;
        }
    </style>
</head>

<body>

<div class="container">

    <h2>Edit Product</h2>

    <c:if test="${not empty message}">
        <div class="error-message">${message}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/product/update" method="post">

        <!-- hidden fields -->
        <input type="hidden" name="id" value="${product.id}" />
        <input type="hidden" name="identifier" value="${product.identifier}" />

        <!-- read-only Product Name -->
        <label>Product Name</label>
        <input type="text" value="${product.identifier}" readonly />

        <label>Brand</label>
        <input type="text" name="brand" value="${product.brand}" required />

                        <label>Categories</label>
                        <select name="categories" multiple required>
                            <c:forEach var="cat" items="${categories}">
                                <option value="${cat.identifier}">
                                    ${cat.identifier}
                                </option>
                            </c:forEach>
                        </select>

        <label>SKU</label>
        <input type="text" name="sku" value="${product.sku}" required />

        <label>Description</label>
        <textarea name="description" required>${product.description}</textarea>

        <button type="submit">Update</button>
    </form>

    <a href="${pageContext.request.contextPath}/product/list">
        ← Back to Product List
    </a>

</div>

</body>
</html>
