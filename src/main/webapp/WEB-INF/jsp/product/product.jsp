<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Edit Product</title>

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
            padding: 30px 0;
        }

        /* ===== CARD ===== */

        .brand-card {
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
        select,
        textarea {
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

        select {
            padding-bottom: 12px;
        }

        select[multiple] {
            height: 120px;
            border: 3px solid #cfcfcf;
            padding: 10px;
            background: transparent;
        }

        textarea {
            resize: none;
            height: 40px;
            overflow: hidden;
        }

        input:focus,
        textarea:focus,
        select:focus {
            border-bottom: 3px solid #3f3f3f;
        }

        select[multiple]:focus {
            border: 3px solid #3f3f3f;
        }

        /* ===== READONLY INPUT ===== */

        input[readonly] {
            color: #7a7a7a;
            cursor: not-allowed;
        }

        /* ===== HINT ===== */

        .hint {
            font-size: 11px;
            color: #8a8a8a;
            margin-top: 8px;
            letter-spacing: 1px;
        }

        /* ===== AUTOFILL FIX ===== */

        input:-webkit-autofill,
        input:-webkit-autofill:hover,
        input:-webkit-autofill:focus,
        textarea:-webkit-autofill {

            -webkit-box-shadow: 0 0 0px 1000px #f3efe9 inset !important;
            -webkit-text-fill-color: #2f2f2f !important;
            transition: background-color 5000s ease-in-out 0s;

        }

        /* ===== BUTTON ===== */

        .update-btn {
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

        .update-btn:hover {
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

<div class="brand-card">

    <!-- ===== BACK ===== -->

    <a href="${pageContext.request.contextPath}/product/list"
       class="back-btn">

        ᐸ BACK

    </a>

    <!-- ===== TITLE ===== -->

    <h2>Edit Product</h2>

    <!-- ===== ERROR ===== -->

    <c:if test="${not empty message}">

        <div class="error-message">

            ${message}

        </div>

    </c:if>

    <!-- ===== FORM ===== -->

    <form:form
            action="${pageContext.request.contextPath}/product/update"
            method="post"
            modelAttribute="product">

        <form:hidden path="id"/>

        <!-- ===== PRODUCT NAME ===== -->

        <div class="form-group">

            <label>PRODUCT NAME</label>

            <form:input
                    path="identifier"
                    readonly="true"/>

        </div>

        <!-- ===== CATEGORY ===== -->

        <div class="form-group">

            <label>CATEGORY</label>

            <form:select path="category" multiple="true">

                <c:forEach var="cat" items="${categories}">

                    <form:option value="${cat.identifier}">

                        ${cat.identifier}

                    </form:option>

                </c:forEach>

            </form:select>

            <div class="hint">

                HOLD CTRL / CMD TO SELECT MULTIPLE

            </div>

        </div>

        <!-- ===== BRAND ===== -->

        <div class="form-group">

            <label>BRAND</label>

            <form:select path="brand">

                <c:forEach var="bran" items="${brand}">

                    <form:option value="${bran.identifier}">

                        ${bran.identifier}

                    </form:option>

                </c:forEach>

            </form:select>

        </div>

        <!-- ===== UNIT ===== -->

        <div class="form-group">

            <label>UNIT</label>

            <form:select path="unit">

                <c:forEach var="uni" items="${unit}">

                    <form:option value="${uni.identifier}">

                        ${uni.identifier}

                    </form:option>

                </c:forEach>

            </form:select>

        </div>

        <!-- ===== MODEL ===== -->

        <div class="form-group">

            <label>MODEL</label>

            <form:select path="model">

                <c:forEach var="mode" items="${model}">

                    <form:option value="${mode.identifier}">

                        ${mode.identifier}

                    </form:option>

                </c:forEach>

            </form:select>

        </div>

        <!-- ===== SKU ===== -->

        <div class="form-group">

            <label>SKU CODE</label>

            <form:input
                    path="skuCode"
                    readonly="true"/>

        </div>

        <!-- ===== BUTTON ===== -->

        <button type="submit"
                class="update-btn">

            UPDATE PRODUCT

        </button>

    </form:form>

</div>

</body>
</html>