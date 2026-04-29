<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Edit Brand</title>

    <style>
        body {
            font-family: "Segoe UI", Arial, sans-serif;
            background: linear-gradient(135deg, #ede9fe, #ddd6fe);
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

        input, textarea, select {
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

        input:focus, textarea:focus, select:focus {
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
            border-radius: 6px;
            font-weight: 600;
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

    <h2>Edit Brand</h2>

    <!-- ✅ Error message at top (same pattern as Node) -->
    <c:if test="${not empty message}">
        <div class="error-message">
            ${message}
        </div>
    </c:if>

    <!-- ✅ Proper form -->
    <form action="${pageContext.request.contextPath}/brand/update" method="post">

        <!-- Hidden fields -->
        <input type="hidden" name="id" value="${brand.id}" />
        <input type="hidden" name="identifier" value="${brand.identifier}" />

        <!-- Brand Name (read-only) -->
        <label>Brand Name</label>
        <input type="text" value="${brand.identifier}" readonly />

        <!-- Description -->
        <label>Description</label>
        <textarea name="description">${brand.description}</textarea>

        <!-- Status (boolean) -->
        <label>Status</label>
        <select name="status">
            <option value="true" ${brand.status ? 'selected' : ''}>ACTIVE</option>
            <option value="false" ${!brand.status ? 'selected' : ''}>INACTIVE</option>
        </select>

        <button type="submit">Update</button>
    </form>

    <a href="${pageContext.request.contextPath}/brand/list">
        ← Back to Brand List
    </a>

</div>

</body>
</html>