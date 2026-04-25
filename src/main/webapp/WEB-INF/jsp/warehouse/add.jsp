<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Add Warehouse</title>

    <!-- ✅ SAME CSS AS OTHER ADD PAGES -->
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
            font-weight: 600;
            cursor: pointer;
        }

        button:hover {
            background: #6d28d9;
        }

        .error {
            color: red;
            text-align: center;
            margin-bottom: 10px;
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
    </style>
</head>

<body>

<div class="container">

    <h2>Add Warehouse</h2>

    <c:if test="${not empty message}">
        <div class="error">${message}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/warehouse/add" method="post">

        <!-- ✅ Product Dropdown as Identifier -->
        <label>Product</label>
        <select name="identifier" required>
            <option value="">-- Select Product --</option>
            <c:forEach var="product" items="${products}">
                <option value="${product.identifier}">
                    ${product.identifier}
                </option>
            </c:forEach>
        </select>

        <label>Location</label>
        <input type="text" name="location" required />

        <label>Manager</label>
        <input type="text" name="manager" required />

        <button type="submit">Save</button>
    </form>

    <a href="${pageContext.request.contextPath}/warehouse/list">
        ← Back to Warehouse List
    </a>

</div>

</body>
</html>