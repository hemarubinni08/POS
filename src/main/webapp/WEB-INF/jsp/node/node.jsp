<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Edit Node</title>

    <style>

        /* ===== BODY ===== */

        body {
            margin: 0;
            font-family: "Inter", sans-serif;
            background-color: #3f3f3f;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        /* ===== CARD ===== */

        .node-card {
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

        /* ===== MULTI SELECT ===== */

        select {
            height: 120px;
            border: 3px solid #cfcfcf;
            padding: 10px;
        }

        option {
            background: #f3efe9;
            color: #2f2f2f;
            padding: 6px;
        }

        input:focus,
        select:focus {
            border-color: #3f3f3f;
        }

        /* ===== AUTOFILL FIX ===== */

        input:-webkit-autofill,
        input:-webkit-autofill:hover,
        input:-webkit-autofill:focus {

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

    </style>

</head>

<body>

<div class="node-card">

    <!-- ===== BACK ===== -->

    <a href="${pageContext.request.contextPath}/node/list"
       class="back-btn">

        ᐸ BACK

    </a>

    <!-- ===== TITLE ===== -->

    <h2>Edit Node</h2>

    <!-- ===== FORM ===== -->

    <form action="${pageContext.request.contextPath}/node/update"
          method="post">

        <input type="hidden"
               name="id"
               value="${node.id}" />

        <input type="hidden"
               name="identifier"
               value="${node.identifier}" />

        <!-- ===== PATH ===== -->

        <div class="form-group">

            <label>PATH</label>

            <input type="text"
                   name="path"
                   value="${node.path}"
                   required />

        </div>

        <!-- ===== ROLES ===== -->

        <div class="form-group">

            <label>ROLES</label>

            <select name="roles"
                    multiple
                    required>

                <c:forEach var="role" items="${roles}">

                    <option value="${role.identifier}"
                        <c:if test="${node.roles.contains(role.identifier)}">
                            selected
                        </c:if>>

                        ${role.identifier}

                    </option>

                </c:forEach>

            </select>

        </div>

        <!-- ===== BUTTON ===== -->

        <button type="submit"
                class="update-btn">

            UPDATE NODE

        </button>

    </form>

</div>

</body>
</html>