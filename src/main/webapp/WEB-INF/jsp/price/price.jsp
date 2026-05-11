<%@ page language="java" contentType="text/html; charset=UTF-8" %>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Edit Price</title>

    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600&display=swap"
          rel="stylesheet">

    <style>
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
            width: 480px;
            background: #ffffff;
            padding: 35px 40px;
            border-radius: 18px;
            box-shadow: 0 12px 35px rgba(0, 0, 0, 0.12);
            border: 1px solid #e6e6e6;
            color: #333;
        }

        h2 {
            text-align: center;
            margin-bottom: 25px;
            color: #222;
            font-weight: 600;
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
            text-decoration: none;
            background: #f0f0f0;
            border-radius: 50%;
            color: #333;
            transition: 0.25s ease;
        }

        .back-icon:hover {
            background: #333;
            color: #fff;
            transform: translateX(-2px);
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

        .form-control:focus {
            outline: none;
            border-color: #4a90e2;
            box-shadow: 0 0 0 3px rgba(74, 144, 226, 0.15);
        }

        .alert {
            padding: 12px;
            border-radius: 10px;
            margin-bottom: 15px;
            text-align: center;
            font-size: 13px;
            background: #ffe5e5;
            border: 1px solid #ffb3b3;
            color: #b30000;
        }

        .btn-group {
            display: flex;
            justify-content: space-between;
            margin-top: 15px;
            gap: 10px;
        }

        .btn {
            padding: 10px 16px;
            border-radius: 10px;
            text-decoration: none;
            font-weight: 600;
            font-size: 14px;
            border: none;
            cursor: pointer;
            transition: 0.25s ease;
            width: 100%;
            text-align: center;
        }

        .btn-cancel {
            background: #e9ecef;
            color: #333;
        }

        .btn-cancel:hover {
            background: #333;
            color: #fff;
        }

        .btn-submit {
            background: #4a90e2;
            color: #fff;
        }

        .btn-submit:hover {
            background: #357bd8;
            transform: translateY(-1px);
            box-shadow: 0 8px 18px rgba(0,0,0,0.15);
        }
    </style>
</head>

<body>

<div class="card-container">

    <a href="/price/list" class="back-icon">←</a>

    <h2>Edit Price</h2>

    <c:if test="${not empty message}">
        <div class="alert">
            ${message}
        </div>
    </c:if>

    <c:if test="${empty price}">
        <div class="alert">
            Price not found
        </div>
    </c:if>

    <c:if test="${not empty price}">
        <form:form method="post"
                   action="/price/update"
                   modelAttribute="price">

            <form:hidden path="identifier"/>

            <div class="form-group">
                <label>MRP</label>
                <form:input path="mrp"
                            cssClass="form-control"
                            required="true"/>
            </div>

            <div class="form-group">
                <label>Selling Price</label>
                <form:input path="sellingPrice"
                            cssClass="form-control"
                            required="true"/>
            </div>

            <div class="form-group">
                <label>Effective From</label>
                <form:input path="effectiveFrom"
                            type="date"
                            cssClass="form-control"
                            required="true"/>
            </div>

            <div class="btn-group">
                <a href="/price/list" class="btn btn-cancel">
                    Cancel
                </a>

                <button type="submit" class="btn btn-submit">
                    Update
                </button>
            </div>

        </form:form>
    </c:if>

</div>

</body>
</html>