<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Add Product</title>

    <style>

        body {
            font-family: "Segoe UI", Arial, sans-serif;
            background: linear-gradient(135deg, #ede9fe, #ddd6fe);
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
            font-size: 20px;
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

        a {
            display: block;
            text-align: center;
            margin-top: 18px;
            color: #6d28d9;
            font-weight: 600;
            text-decoration: none;
            font-size: 13px;
        }
    </style>
</head>

<body>
<div class="container">

    <c:if test="${not empty message}">
        <div class="error-message">${message}</div>
    </c:if>

    <h2>Add Product</h2>

    <form action="${pageContext.request.contextPath}/product/add" method="post">

        <label>Product Name</label>
        <input type="text" name="identifier" required/>

        <label>Brand</label>
        <input type="text" name="brand" required/>

                <label>Categories</label>
                <select name="categories" multiple required>
                    <c:forEach var="cat" items="${categories}">
                        <option value="${cat.identifier}">
                            ${cat.identifier}
                        </option>
                    </c:forEach>
                </select>

        <label>SKU</label>
        <input type="text" name="sku" required/>

        <label>Description</label>
        <textarea name="description" required></textarea>

        <button type="submit">Save</button>
    </form>

    <a href="${pageContext.request.contextPath}/product/list">← Back to Product List</a>

</div>
</body>
</html>