<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Update User</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        body {
            margin: 0;
            font-family: 'Poppins', sans-serif;
            min-height: 100vh;
            background: #f4f6f9;
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 20px;
        }

        .back-arrow {
            position: absolute;
            top: 20px;
            left: 20px;
            width: 42px;
            height: 42px;
            display: flex;
            align-items: center;
            justify-content: center;
            text-decoration: none;
            font-size: 18px;
            background: #f0f0f0;
            color: #333;
            border-radius: 50%;
            border: 1px solid #e6e6e6;
            box-shadow: 0 8px 20px rgba(0,0,0,0.08);
            transition: 0.25s ease;
        }

        .back-arrow:hover {
            background: #333;
            color: #fff;
            transform: translateX(-3px);
        }

        .container-box {
            width: 100%;
            max-width: 850px;
        }

        .card {
            background: #ffffff;
            border-radius: 18px;
            box-shadow: 0 12px 35px rgba(0, 0, 0, 0.12);
            padding: 28px;
            border: 1px solid #e6e6e6;
        }

        h3 {
            text-align: center;
            font-weight: 600;
            margin-bottom: 18px;
            color: #222;
        }

        label {
            font-size: 13px;
            color: #444;
            font-weight: 500;
        }

        .form-control {
            border-radius: 10px;
            border: 1px solid #ddd;
            padding: 10px 12px;
            font-size: 14px;
            transition: 0.2s ease;
        }

        .form-control:focus {
            border-color: #4a90e2;
            box-shadow: 0 0 0 3px rgba(74,144,226,0.15);
        }

        .btn-update {
            width: 100%;
            padding: 12px;
            background: #4a90e2;
            color: white;
            border-radius: 10px;
            border: none;
            font-weight: 600;
            transition: 0.3s ease;
        }

        .btn-update:hover {
            background: #357bd8;
            transform: translateY(-1px);
            box-shadow: 0 8px 18px rgba(0,0,0,0.15);
        }

        .server-msg {
            text-align: center;
            padding: 12px;
            border-radius: 10px;
            font-size: 13px;
            margin-bottom: 12px;
        }

        .server-msg.success {
            background: #e6f4ea;
            color: #1b4332;
            border: 1px solid #b7e4c7;
        }

        .server-msg.error {
            background: #ffe5e5;
            color: #b30000;
            border: 1px solid #ffb3b3;
        }

        .invalid-feedback {
            font-size: 12px;
            color: #b30000;
            margin-top: 4px;
        }

        .toast-message {
            position: fixed;
            bottom: 20px;
            right: 20px;
            background: #333;
            color: white;
            padding: 12px 16px;
            border-radius: 10px;
            box-shadow: 0 10px 25px rgba(0,0,0,0.2);
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

                    <div class="mb-3">
                        <label>Roles</label>
                        <form:select path="roles" multiple="true" cssClass="form-control">
                            <form:options items="${roles}" itemValue="identifier" itemLabel="identifier"/>
                        </form:select>
                    </div>

                </div>
            </div>

            <button type="submit" class="btn-update">Update User</button>

        </form:form>

    </div>
</div>

<div id="toast" class="toast-message"></div>

</body>
</html>