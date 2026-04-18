<!DOCTYPE html>
<html>
<head>
    <title>Add Role Form</title>

    <style>
    .error-input{
                border: 1px solid red;
                border-bottom: 3px solid red;
                outline: none;
            }
            .error-text
            {
                color: red;
                font-size: 12px;
                margin-top: 5px;
            }
        body {
            margin: 0;
            padding: 0;
            background-color: #f2f4f8;
            font-family: Arial, sans-serif;
        }

        .container {
            width: 350px;
            margin: 100px auto;
            padding: 25px;
            background-color: #ffffff;
            border-radius: 8px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.15);
            text-align: center;
        }

        h2 {
            margin-bottom: 20px;
            color: #333;
        }

        label {
            display: block;
            text-align: left;
            font-weight: bold;
            margin-bottom: 5px;
            color: #555;
        }

        input {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            border-radius: 4px;
            border: 1px solid #ccc;
            font-size: 14px;
        }

        input:focus {
            border-color: #007bff;
            outline: none;
        }

        button {
            width: 100%;
            padding: 10px;
            background-color: #007bff;
            color: #ffffff;
            border: none;
            border-radius: 4px;
            font-size: 16px;
            cursor: pointer;
        }

        button:hover {
            background-color: #0056b3;
        }
    </style>
</head>

<body>
<div class="container">
    <h2>Add new Node</h2>
        <!-- ✅ MESSAGE DISPLAY -->
        <c:if test="${not empty message}">
            <div class="error-message">
                ${message}
            </div>
        </c:if>
    <form action="${pageContext.request.contextPath}/node/add" method="post" modelAttribute="nodeDto">
        <label>Name:</label>
        <input type="text" name="identifier" required/>
        <class="${not empty node.message ? 'error-input' : ''}"/>
                        <c:if test="${not empty node.message}">
                            <div class="error-text">${node.message}</div>
                        </c:if>
        <label>Path:</label>
                <input type="text" name="path" required/>
                <class="${not empty node.message ? 'error-input' : ''}"/>
                                <c:if test="${not empty node.message}">
                                    <div class="error-text">${node.message}</div>
                                </c:if>
        <label>Role:</label>
                        <input type="text" name="roles" required/>
                        <class="${not empty node.message ? 'error-input' : ''}"/>
                                        <c:if test="${not empty node.message}">
                                            <div class="error-text">${node.message}</div>
                                        </c:if>
        <button type="submit">Register</button>
    </form>
</div>
</body>
</html>