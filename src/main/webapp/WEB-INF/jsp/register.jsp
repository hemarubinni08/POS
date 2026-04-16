<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html>
<html>
<head>
    <title>User Registration</title>

    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600&display=swap" rel="stylesheet">

    <style>
        body {
            margin: 0;
            font-family: 'Poppins', sans-serif;
            min-height: 100vh;
            background: linear-gradient(135deg, #667eea, #764ba2);
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .register-card {
            width: 430px;
            background: rgba(255, 255, 255, 0.95);
            padding: 35px 40px;
            border-radius: 16px;
            box-shadow: 0 25px 50px rgba(0, 0, 0, 0.25);
        }

        h2 {
            text-align: center;
            margin-bottom: 30px;
            color: #4b6cb7;
            font-weight: 600;
        }

        .form-group {
            margin-bottom: 16px;
        }

        label {
            font-size: 13px;
            font-weight: 500;
            color: #333;
            margin-bottom: 6px;
            display: block;
        }

        input, select {
            width: 100%;
            padding: 11px 14px;
            border-radius: 8px;
            border: 1px solid #ccc;
            font-size: 14px;
        }

        select[multiple] {
            height: 130px; /* ✅ MAKES MULTI-SELECT CLEAR */
        }

        small {
            color: #666;
            font-size: 11px;
        }

        .btn-submit {
            margin-top: 10px;
            width: 100%;
            padding: 13px;
            background: linear-gradient(135deg, #4b6cb7, #182848);
            color: white;
            border: none;
            border-radius: 10px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
        }
    </style>
</head>

<body>

<div class="register-card">
    <h2>User Registration</h2>

    <form:form action="register" method="post" modelAttribute="userDto">

        <!-- Name -->
        <div class="form-group">
            <label>Name</label>
            <form:input path="name"/>
        </div>

        <!-- Email -->
        <div class="form-group">
            <label>Email</label>
            <form:input path="email" type="email"/>
        </div>

        <div class="form-group">
            <label>Primary Role</label>
            <form:select path="role">
                <form:option value="" label="-- Select Primary Role --"/>
                <form:options items="${roles}" itemValue="name" itemLabel="name"/>
            </form:select>
        </div>

        <div class="form-group">
            <label>Additional Roles</label>
            <form:select path="roles" multiple="true">
                <form:options items="${roles}" itemValue="name" itemLabel="name"/>
            </form:select>
        </div>

        <div class="form-group">
            <label>Phone Number</label>
            <form:input path="phoneNo"/>
        </div>

        <div class="form-group">
            <label>Username</label>
            <form:input path="userName"/>
        </div>

        <div class="form-group">
            <label>Password</label>
            <form:password path="password"/>
        </div>

        <div class="form-group">
            <label>Age</label>
            <form:input path="age"/>
        </div>

        <input type="submit" value="Register" class="btn-submit"/>

    </form:form>

</div>

</body>
</html>