<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Update User</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        :root {
            --bg: #ede9fe;
            --card: #ffffff;

            --text: #4c1d95;               /* deep purple */
            --muted: #6b7280;

            --primary: #7c3aed;            /* main purple */
            --primary-hover: #6d28d9;

            --accent: #c4b5fd;

            --danger: #b91c1c;
            --danger-bg: #fee2e2;
            --danger-border: #fca5a5;

            --success: #15803d;
            --success-bg: #dcfce7;
            --success-border: #86efac;

            --border: #ddd6fe;

            --radius: 14px;
            --shadow: 0 15px 35px rgba(76, 29, 149, 0.18);
        }

        * {
            font-family: 'Inter', sans-serif;
            box-sizing: border-box;
        }

        body {
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            background: linear-gradient(135deg, #ede9fe, #ddd6fe);
            padding: 20px;
            position: relative;
        }

        /* ✅ BACK BUTTON */
        .back-arrow {
            position: absolute;
            top: 20px;
            left: 20px;
            background: var(--card);
            border: 1px solid var(--border);
            border-radius: 50%;
            width: 42px;
            height: 42px;
            display: flex;
            align-items: center;
            justify-content: center;
            text-decoration: none;
            color: var(--text);
            font-size: 18px;
            box-shadow: var(--shadow);
            transition: 0.2s;
        }

        .back-arrow:hover {
            background: var(--accent);
            color: #4c1d95;
        }

        .container-box {
            width: 100%;
            max-width: 800px;
        }

        .card {
            background: var(--card);
            border-radius: var(--radius);
            box-shadow: var(--shadow);
            padding: 26px;
        }

        h3 {
            text-align: center;
            font-weight: 600;
            margin-bottom: 18px;
            color: var(--primary);
        }

        label {
            font-size: 13px;
            color: var(--text);
            font-weight: 500;
        }

        .btn-update {
            width: 100%;
            padding: 10px;
            background: var(--primary);
            color: white;
            border-radius: 10px;
            border: none;
            font-weight: 600;
        }

        .btn-update:hover {
            background: var(--primary-hover);
        }

        .server-msg {
            text-align: center;
            padding: 10px 14px;
            border-radius: 10px;
            font-size: 13px;
            margin-bottom: 12px;
        }

        .server-msg.success {
            background: var(--success-bg);
            color: var(--success);
            border: 1px solid var(--success-border);
        }

        .server-msg.error {
            background: var(--danger-bg);
            color: var(--danger);
            border: 1px solid var(--danger-border);
        }

        .invalid-feedback {
            font-size: 12px;
            color: var(--danger);
            margin-top: 4px;
        }

        .error-border {
            border: 1px solid var(--danger) !important;
        }

        .toast-message {
            position: fixed;
            bottom: 20px;
            right: 20px;
            background: #4c1d95;
            color: white;
            padding: 12px 16px;
            border-radius: 10px;
            box-shadow: var(--shadow);
            opacity: 0;
            transform: translateY(20px);
            transition: 0.3s;
            font-size: 13px;
            z-index: 999;
        }

        .toast-message.show {
            opacity: 1;
            transform: translateY(0);
        }
    </style>

    <!-- ✅ JS UNCHANGED -->
</head>

<body>

<a href="${pageContext.request.contextPath}/user/list" class="back-arrow">←</a>

<div class="container-box">

    <c:if test="${not empty message}">
        <div class="server-msg ${messageType == 'success' ? 'success' : 'error'}">
            ${message}
        </div>
    </c:if>

    <div class="card">

        <h3>Update User</h3>

        <form:form action="${pageContext.request.contextPath}/user/update"
                   method="post"
                   modelAttribute="user"
                   onsubmit="return validateForm()">

            <form:hidden path="id"/>

            <div class="row">
                <div class="col-md-6">
                    <div class="mb-3">
                        <label>Name</label>
                        <form:input path="name" cssClass="form-control"/>
                    </div>

                    <div class="mb-3">
                        <label>Email</label>
                        <form:input path="username" cssClass="form-control"/>
                        <div id="emailErr" class="invalid-feedback"></div>
                    </div>
                </div>

                <div class="col-md-6">
                    <div class="mb-3">
                        <label>Phone Number</label>
                        <form:input path="phoneNo"
                                    cssClass="form-control"
                                    maxlength="10"
                                    oninput="this.value = this.value.replace(/[^0-9]/g, '').slice(0,10)"/>
                        <div id="phoneErr" class="invalid-feedback"></div>
                    </div>
                </div>
            </div>

            <div class="mb-3">
                <label>Roles</label>
                <form:select path="roles" multiple="true" cssClass="form-control">
                    <form:options items="${roles}" itemValue="identifier" itemLabel="identifier"/>
                </form:select>
            </div>

            <button type="submit" class="btn-update">Update User</button>

        </form:form>

    </div>
</div>

<div id="toast" class="toast-message"></div>

</body>
</html>