<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Warehouse</title>

    <style>
        body {
            margin: 0;
            font-family: Poppins, Arial, sans-serif;
            background: #f4f6f9;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .card-container {
            width: 520px;
            background: #fff;
            padding: 40px;
            border-radius: 18px;
            box-shadow: 0 12px 35px rgba(0,0,0,0.12);
        }

        h2 {
            text-align: center;
            margin-bottom: 25px;
        }

        .form-group {
            margin-bottom: 18px;
        }

        label {
            font-size: 13px;
            font-weight: 500;
        }

        .form-control {
            width: 100%;
            padding: 11px;
            border-radius: 10px;
            border: 1px solid #ddd;
        }

        .btn-submit {
            width: 100%;
            padding: 12px;
            background: #4a90e2;
            color: #fff;
            border: none;
            border-radius: 10px;
            cursor: pointer;
        }

        .error-message {
            background: #ffe5e5;
            padding: 10px;
            margin-bottom: 12px;
            border-radius: 8px;
        }
    </style>
</head>

<body>

<div class="card-container">

    <a href="/warehouse/list">← Back</a>

    <h2>Edit Warehouse</h2>

    <c:if test="${not empty message}">
        <div class="error-message">${message}</div>
    </c:if>
    <form:form method="post"
               action="/warehouse/update"
               modelAttribute="warehouse">
        <div class="form-group">
            <label>Warehouse Name</label>
            <form:input path="identifier"
                        cssClass="form-control"
                        readonly="true"/>
        </div>

        <div class="form-group">
            <label>Location</label>
            <form:input path="location"
                        cssClass="form-control"
                        required="required"/>
        </div>

        <div class="form-group">
            <label>Manager</label>
            <form:input path="manager"
                        cssClass="form-control"/>
        </div>

        <button type="submit"
                class="btn-submit">
            Update Warehouse
        </button>

    </form:form>

</div>

</body>
</html>