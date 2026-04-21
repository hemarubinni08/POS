<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add Node</title>

    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600&display=swap"
          rel="stylesheet">

    <style>
        /* ✅ YOUR CSS (UNCHANGED) */
        body {
            margin: 0;
            font-family: 'Poppins', sans-serif;
            min-height: 100vh;
            background: #f4f6f9;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .card-container {
            position: relative;
            width: 520px;
            background: #ffffff;
            padding: 40px 45px;
            border-radius: 18px;
            box-shadow: 0 12px 35px rgba(0, 0, 0, 0.12);
            border: 1px solid #e6e6e6;
            color: #333;
            transition: transform 0.3s ease;
        }

        .card-container:hover {
            transform: translateY(-2px);
        }

        h2 {
            text-align: center;
            margin-bottom: 25px;
            font-weight: 600;
            color: #222;
        }

        .back-icon {
            position: absolute;
            top: 16px;
            left: 16px;
            width: 36px;
            height: 36px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 18px;
            color: #333;
            text-decoration: none;
            font-weight: bold;
            background: #f0f0f0;
            border-radius: 50%;
            transition: all 0.25s ease;
        }

        .back-icon:hover {
            background: #333;
            color: #fff;
            transform: translateX(-3px);
        }

        .form-group {
            margin-bottom: 18px;
        }

        label {
            display: block;
            margin-bottom: 6px;
            font-weight: 500;
            color: #444;
            font-size: 13px;
        }

        .form-control {
            width: 100%;
            padding: 11px 14px;
            border-radius: 10px;
            border: 1px solid #ddd;
            font-family: 'Poppins', sans-serif;
            font-size: 14px;
            background: #fff;
            color: #333;
            transition: all 0.2s ease;
        }

        .form-control::placeholder {
            color: #999;
        }

        .form-control:focus {
            outline: none;
            border-color: #4a90e2;
            box-shadow: 0 0 0 3px rgba(74, 144, 226, 0.15);
        }

        select[multiple] {
            height: 120px;
        }

        .error-message {
            padding: 12px;
            border-radius: 8px;
            margin-bottom: 15px;
            text-align: center;
            font-size: 13px;
            background: #ffe5e5;
            border: 1px solid #ffb3b3;
            color: #b30000;
        }

        .btn-submit {
            width: 100%;
            padding: 12px;
            border-radius: 10px;
            border: none;
            cursor: pointer;
            font-weight: 600;
            font-size: 15px;
            background: #4a90e2;
            color: white;
            transition: 0.3s ease;
        }

        .btn-submit:hover {
            background: #357bd8;
            transform: translateY(-1px);
            box-shadow: 0 8px 18px rgba(0,0,0,0.15);
        }

        .footer-text {
            text-align: center;
            margin-top: 15px;
            font-size: 12px;
            color: #888;
        }
    </style>

    <!-- ✅ POPUP SCRIPT -->
    <script>
        window.onload = function () {
            var message = "${message}";
            if (message && message.trim() !== "") {
                alert(message);
            }
        };
    </script>

</head>

<body>

<div class="card-container">

    <a href="/node/list" class="back-icon">←</a>

    <h2>Add New Node</h2>

    <!-- ✅ Optional inline message (can remove if only popup needed) -->
    <c:if test="${not empty message}">
        <div class="error-message">
            ${message}
        </div>
    </c:if>

    <form:form method="post"
               action="/node/add"
               modelAttribute="nodeDto">

        <div class="form-group">
            <label>Node Name</label>
            <form:input path="identifier"
                        cssClass="form-control"
                        placeholder="Enter node name"
                        required="required"/>
        </div>

        <div class="form-group">
            <label>Path</label>
            <form:input path="path"
                        cssClass="form-control"
                        placeholder="Enter path"
                        required="required"/>
        </div>

        <div class="form-group">
            <label>Roles</label>
            <form:select path="roles"
                         multiple="true"
                         cssClass="form-control">
                <form:options items="${roles}"
                              itemValue="identifier"
                              itemLabel="identifier"/>
            </form:select>
        </div>

        <button type="submit" class="btn-submit">
            Add Node
        </button>

    </form:form>

    <div class="footer-text">
        POS Management System
    </div>

</div>

</body>
</html>