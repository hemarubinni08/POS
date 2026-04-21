<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Update Node</title>

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

        .info-text {
            font-size: 12px;
            color: #777;
            margin-top: 6px;
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

    <h2>Edit Node</h2>

    ${message}

    <c:if test="${empty node}">
        <div class="alert">
            Node not found
        </div>
    </c:if>

    <c:if test="${not empty node}">
        <form:form action="/node/update"
                   method="post"
                   modelAttribute="node">

            <form:hidden path="id"/>

            <div class="form-group">
                <label>Node Name</label>
                <form:input path="identifier"
                            cssClass="form-control"
                            required="true"/>
            </div>

            <div class="form-group">
                <label>Path</label>
                <form:input path="path"
                            cssClass="form-control"
                            required="true"/>
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

                <div class="info-text">
                    Hold Ctrl (Windows/Linux) or Cmd (Mac) to select multiple roles
                </div>
            </div>

            <div class="btn-group">
                <a href="/node/list" class="btn btn-cancel">
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