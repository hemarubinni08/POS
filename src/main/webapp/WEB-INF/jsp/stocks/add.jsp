<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Add Stock</title>

    <style>

        /* ===== BODY ===== */

        body {
            margin: 0;
            font-family: "Inter", sans-serif;
            background-color: #3f3f3f;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
        }

        /* ===== CARD ===== */

        .stock-card {
            background: #f3efe9;
            width: 470px;
            padding: 42px;
            box-sizing: border-box;
        }

        /* ===== BACK BUTTON ===== */

        .back-btn {
            display: inline-block;
            margin-bottom: 22px;
            text-decoration: none;
            color: #2f2f2f;
            font-size: 13px;
            font-weight: 700;
            letter-spacing: 1px;
        }

        .back-btn:hover {
            opacity: 0.7;
        }

        /* ===== TITLE ===== */

        h2 {
            margin: 0 0 34px;
            font-size: 26px;
            font-weight: 700;
            color: #2f2f2f;
        }

        /* ===== FORM ===== */

        .form-group {
            margin-bottom: 28px;
        }

        label {
            font-size: 12px;
            letter-spacing: 2px;
            color: #8a8a8a;
            display: block;
            margin-bottom: 10px;
        }

        input,
        select {
            width: 100%;
            box-sizing: border-box;
            padding: 10px 0;
            border: none;
            border-bottom: 3px solid #cfcfcf;
            background: transparent;
            font-size: 16px;
            outline: none;
            color: #2f2f2f;
            font-family: "Inter", sans-serif;
        }

        input:focus,
        select:focus {
            border-bottom: 3px solid #3f3f3f;
        }

        /* ===== SELECT ===== */

        option {
            background: #f3efe9;
            color: #2f2f2f;
        }

        /* ===== AUTOFILL FIX ===== */

        input:-webkit-autofill,
        input:-webkit-autofill:hover,
        input:-webkit-autofill:focus,
        select:-webkit-autofill {

            -webkit-box-shadow: 0 0 0px 1000px #f3efe9 inset !important;
            -webkit-text-fill-color: #2f2f2f !important;
            transition: background-color 5000s ease-in-out 0s;

        }

        /* ===== BUTTON ===== */

        .add-btn {
            width: 100%;
            padding: 16px;
            margin-top: 8px;
            background: #3f3f3f;
            color: #ffffff;
            border: 2px solid #3f3f3f;
            font-size: 15px;
            font-weight: 700;
            letter-spacing: 2px;
            cursor: pointer;
            transition: 0.3s;
        }

        .add-btn:hover {
            background: transparent;
            color: #3f3f3f;
        }

        /* ===== ERROR ===== */

        .error-message {
            margin-top: 16px;
            padding: 12px;
            background: #ffe5e0;
            color: #b91c1c;
            font-size: 13px;
        }

    </style>

</head>

<body>

<div class="stock-card">

    <!-- ===== BACK ===== -->

    <a href="${pageContext.request.contextPath}/stocks/list"
       class="back-btn">

        ᐸ BACK

    </a>

    <!-- ===== TITLE ===== -->

    <h2>Add Stock</h2>

    <!-- ===== ERROR ===== -->

    <c:if test="${not empty message}">

        <div class="error-message">

            ${message}

        </div>

    </c:if>

    <!-- ===== FORM ===== -->

    <form:form
            action="${pageContext.request.contextPath}/stocks/add"
            method="post"
            modelAttribute="stocksDto">

        <!-- ===== PRODUCT NAME ===== -->

        <div class="form-group">

            <label>PRODUCT NAME</label>

            <form:select path="identifier" required="true">

                <form:option value="">
                    -- Select Product --
                </form:option>

                <c:forEach var="product" items="${products}">

                    <form:option value="${product.identifier}">
                        ${product.identifier}
                    </form:option>

                </c:forEach>

            </form:select>

        </div>

        <!-- ===== AVAILABLE STOCK ===== -->

        <div class="form-group">

            <label>AVAILABLE STOCK</label>

            <form:input
                    path="availableStock"
                    type="number"
                    min="0"
                    required="true"/>

        </div>

        <!-- ===== INCOMING STOCK ===== -->

        <div class="form-group">

            <label>INCOMING STOCK</label>

            <form:input
                    path="incomingStock"
                    type="number"
                    min="0"/>

        </div>

        <!-- ===== OUTGOING STOCK ===== -->

        <div class="form-group">

            <label>OUTGOING STOCK</label>

            <form:input
                    path="outgoingStock"
                    type="number"
                    min="0"/>

        </div>

        <!-- ===== PRODUCT STATUS ===== -->

        <div class="form-group">

            <label>PRODUCT STATUS</label>

            <form:select path="productStatus" required="true">

                <form:option value="">
                    -- Select Status --
                </form:option>

                <form:option value="AVAILABLE">
                    Available
                </form:option>

                <form:option value="OUT_OF_STOCK">
                    Out of Stock
                </form:option>

                <form:option value="LOW_STOCK">
                    Low Stock
                </form:option>

                <form:option value="INCOMING">
                    Incoming
                </form:option>

                <form:option value="BLOCKED">
                    Blocked
                </form:option>

                <form:option value="DAMAGED">
                    Damaged
                </form:option>

            </form:select>

        </div>

        <!-- ===== WAREHOUSE ===== -->

        <div class="form-group">

            <label>WAREHOUSE</label>

            <form:select path="wareHouse" required="true">

                <form:option value="">
                    -- Select Warehouse --
                </form:option>

                <c:forEach var="wh" items="${warehouse}">

                    <form:option value="${wh.identifier}">
                        ${wh.identifier}
                    </form:option>

                </c:forEach>

            </form:select>

        </div>

        <!-- ===== BUTTON ===== -->

        <button type="submit"
                class="add-btn">

            ADD STOCK

        </button>

    </form:form>

</div>

</body>
</html>