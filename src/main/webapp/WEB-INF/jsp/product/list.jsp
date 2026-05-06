<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Product Management</title>

    <style>

        /* ===== BODY ===== */

        body {
            margin: 0;
            font-family: "Inter", sans-serif;
            background-color: #3f3f3f;
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        /* ===== PAGE WRAPPER ===== */

        .page-wrapper {
            width: 1250px;
            background: #f3efe9;
            padding: 34px 42px;
            box-sizing: border-box;
        }

        /* ===== HEADER ===== */

        .top-section {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 28px;
        }

        .page-title {
            font-size: 26px;
            font-weight: 700;
            color: #2f2f2f;
            margin: 0;
        }

        /* ===== BUTTONS ===== */

        .top-actions {
            display: flex;
            align-items: center;
            gap: 12px;
        }

        .top-btn,
        .back-btn {
            height: 48px;
            min-width: 120px;
            padding: 0 18px;
            display: flex;
            align-items: center;
            justify-content: center;
            box-sizing: border-box;
            text-decoration: none;
            font-size: 12px;
            font-weight: 700;
            letter-spacing: 1px;
            border: 2px solid #3f3f3f;
            transition: 0.3s;
        }

        .top-btn {
            background: #3f3f3f;
            color: #ffffff;
        }

        .top-btn:hover {
            background: transparent;
            color: #3f3f3f;
        }

        .back-btn {
            background: transparent;
            color: #3f3f3f;
        }

        .back-btn:hover {
            background: #3f3f3f;
            color: #ffffff;
        }

        /* ===== TABLE ===== */

        table {
            width: 100%;
            border-collapse: collapse;
        }

        th {
            text-align: left;
            padding: 14px 12px;
            font-size: 11px;
            letter-spacing: 2px;
            color: #8a8a8a;
            border-bottom: 3px solid #d6d1cb;
        }

        td {
            padding: 20px 12px;
            border-bottom: 2px solid #dedad5;
            color: #2f2f2f;
            font-size: 14px;
            vertical-align: middle;
        }

        /* ===== TOGGLE ===== */

        .switch {
            display: flex;
            align-items: center;
            justify-content: center;
            width: 55px;
        }

        .slider {
            background-color: #ffffff2b;
            border-radius: 100px;
            cursor: pointer;
            position: relative;
            display: block;
            width: 46px;
            height: 26px;
            transition: 0.3s;
            box-shadow:
                rgba(0, 0, 0, 0.62) 0px 0px 5px inset,
                rgba(0, 0, 0, 0.21) 0px 0px 0px 24px inset,
                #22cc3f 0px 0px 0px 0px inset,
                rgba(224, 224, 224, 0.45) 0px 1px 0px 0px;
        }

        .slider::after {
            content: "";
            position: absolute;
            top: 2px;
            left: 2px;
            width: 22px;
            height: 22px;
            background-color: #e3e3e3;
            border-radius: 50%;
            transition: 0.3s;
            box-shadow:
                rgba(0, 0, 0, 0.3) 0px 4px 5px;
        }

        .switch input[type="checkbox"]:checked + .slider {

            box-shadow:
                rgba(0, 0, 0, 0.62) 0px 0px 5px inset,
                #22cc3f 0px 0px 0px 2px inset,
                #22cc3f 0px 0px 0px 24px inset,
                rgba(224, 224, 224, 0.45) 0px 1px 0px 0px;

        }

        .switch input[type="checkbox"]:checked + .slider::after {
            left: 22px;
        }

        .switch input[type="checkbox"] {
            display: none;
        }

        /* ===== ACTION BUTTONS ===== */

        .action-buttons {
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .action-link {
            width: 44px;
            height: 44px;
            display: flex;
            align-items: center;
            justify-content: center;
            box-sizing: border-box;
            text-decoration: none;
            font-size: 18px;
            font-weight: 700;
            border: 2px solid #3f3f3f;
            transition: 0.3s;
        }

        .edit {
            background: #3f3f3f;
            color: #ffffff;
        }

        .edit:hover {
            background: transparent;
            color: #3f3f3f;
        }

        .delete {
            background: transparent;
            color: #3f3f3f;
        }

        .delete:hover {
            background: #3f3f3f;
            color: #ffffff;
        }

        /* ===== EMPTY ===== */

        .empty {
            text-align: center;
            padding: 28px;
            color: #2f2f2f;
            font-size: 15px;
        }

    </style>

</head>

<body>

<div class="page-wrapper">

    <!-- ===== HEADER ===== -->

    <div class="top-section">

        <h2 class="page-title">
            Product Management
        </h2>

        <div class="top-actions">

            <a href="${pageContext.request.contextPath}/"
               class="back-btn">

                ᐸ BACK

            </a>

            <a href="${pageContext.request.contextPath}/product/add"
               class="top-btn">

                ADD PRODUCT

            </a>

        </div>

    </div>

    <!-- ===== TABLE ===== -->

    <c:if test="${empty products}">

        <div class="empty">

            No products found

        </div>

    </c:if>

    <c:if test="${not empty products}">

        <table>

            <thead>

            <tr>

                <th>PRODUCT</th>
                <th>CATEGORY</th>
                <th>SKU</th>
                <th>BRAND</th>
                <th>MODEL</th>
                <th>UNIT</th>
                <th>STATUS</th>
                <th>ACTION</th>

            </tr>

            </thead>

            <tbody>

            <c:forEach var="product" items="${products}">

                <tr>

                    <td>${product.identifier}</td>

                    <td>${product.category}</td>

                    <td>${product.skuCode}</td>

                    <td>${product.brand}</td>

                    <td>${product.model}</td>

                    <td>${product.unit}</td>

                    <!-- ===== STATUS ===== -->

                    <td>

                        <label class="switch">

                            <input type="checkbox"
                                   ${product.status ? 'checked' : ''}
                                   onchange="toggleStatus('${product.identifier}')">

                            <span class="slider"></span>

                        </label>

                    </td>

                    <!-- ===== ACTIONS ===== -->

                    <td>

                        <div class="action-buttons">

                            <a href="${pageContext.request.contextPath}/product/get/${product.identifier}"
                               class="action-link edit"
                               title="Edit">

                                ✎

                            </a>

                            <a href="${pageContext.request.contextPath}/product/delete/${product.identifier}"
                               class="action-link delete"
                               title="Delete"
                               onclick="return confirm('Delete this product?')">

                                🗑

                            </a>

                        </div>

                    </td>

                </tr>

            </c:forEach>

            </tbody>

        </table>

    </c:if>

</div>

<script>

    function toggleStatus(identifier) {

        fetch('${pageContext.request.contextPath}/product/toggle-status', {

            method: 'POST',

            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },

            body: 'identifier=' + encodeURIComponent(identifier)

        }).then(() => location.reload());

    }

</script>

</body>
</html>