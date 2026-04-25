<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html>
<html>
<head>
    <title>Edit Warehouse</title>

    <style>
        body {
            font-family: "Segoe UI", Arial, sans-serif;
            background: linear-gradient(135deg, #ede9fe, #ddd6fe);
        }
        .container {
            width: 420px;
            margin: 80px auto;
            background: #ffffff;
            padding: 30px;
            border-radius: 12px;
        }
        h2 { text-align: center; color: #6d28d9; }
        label {
            margin-top: 16px;
            display: block;
            font-weight: 600;
        }
        input, select {
            width: 100%;
            padding: 9px;
            border: 1px solid #c4b5fd;
            border-radius: 6px;
        }
        button {
            margin-top: 26px;
            width: 100%;
            padding: 11px;
            background: #7c3aed;
            color: #ffffff;
            border: none;
            border-radius: 6px;
        }
    </style>
</head>

<body>
<div class="container">

    <h2>Edit Warehouse</h2>

    <!-- ✅ SPRING FORM -->
    <form:form modelAttribute="warehouse"
               action="${pageContext.request.contextPath}/warehouse/update"
               method="post">

        <!-- Hidden fields -->
        <form:hidden path="id"/>
        <form:hidden path="identifier"/>

        <!-- Identifier (readonly display) -->
        <label>Warehouse</label>
        <form:input path="identifier" readonly="true"/>

        <!-- Location -->
        <label>Location</label>
        <form:input path="location" required="true"/>

        <!-- Manager -->
        <label>Manager</label>
        <form:input path="manager" required="true"/>

        <!-- OPTIONAL: Product Dropdown (only if needed) -->
        <%--
        <label>Product</label>
        <form:select path="productIdentifier">
            <form:option value="" label="Select Product"/>
            <form:options items="${products}" itemValue="identifier" itemLabel="name"/>
        </form:select>
        --%>

        <button type="submit">Update</button>

    </form:form>

    <br/>

    <a href="${pageContext.request.contextPath}/warehouse/list">
        ← Back to Warehouse List
    </a>

</div>
</body>
</html>