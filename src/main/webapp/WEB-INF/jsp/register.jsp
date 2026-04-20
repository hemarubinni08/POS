<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>POS Management | Register</title>

    <style>
        body {
            margin: 0;
            min-height: 100vh;
            font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
            background: #F4F5F7;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 20px 0;
        }

        .register-card {
            background: #FFFFFF;
            width: 100%;
            max-width: 430px;
            padding: 40px;
            border-radius: 16px;
            box-shadow: 0 8px 20px rgba(0,0,0,0.08);
            position: relative;
        }

        .brand-header {
            background: #0B3C5D;
            margin: -40px -40px 30px -40px;
            padding: 25px;
            border-radius: 16px 16px 0 0;
            color: #FFFFFF;
            text-align: center;
        }

        .brand-header h1 {
            margin: 0;
            font-size: 22px;
            font-weight: 600;
            letter-spacing: 0.8px;
        }

        h2 {
            color: #1F2937;
            font-size: 18px;
            margin-bottom: 8px;
            text-align: center;
        }

        .subtitle {
            color: #6B7280;
            font-size: 14px;
            margin-bottom: 25px;
            text-align: center;
        }

        .form-group {
            margin-bottom: 16px;
        }

        label {
            display: block;
            font-size: 13px;
            font-weight: 600;
            color: #4B5563;
            margin-bottom: 6px;
        }

        input, select {
            width: 100%;
            padding: 12px;
            border: 1px solid #E5E7EB;
            border-radius: 8px;
            box-sizing: border-box;
            font-size: 14px;
            transition: border-color 0.2s;
        }

        input:focus, select:focus {
            outline: none;
            border-color: #0B3C5D;
            background: #F9FAFB;
        }

        select[multiple] {
            height: 100px;
        }

        .btn-submit {
            width: 100%;
            padding: 12px;
            background: #0B3C5D;
            color: #FFFFFF;
            border: none;
            border-radius: 8px;
            font-size: 15px;
            font-weight: 600;
            cursor: pointer;
            transition: opacity 0.2s;
            margin-top: 15px;
        }

        .btn-submit:hover {
            opacity: 0.9;
        }

        .error-message {
            background: #FEE2E2;
            color: #B91C1C;
            padding: 10px;
            border-radius: 8px;
            font-size: 13px;
            margin-bottom: 20px;
            border: 1px solid #FECACA;
            font-weight: 500;
            text-align: center;
        }

        .footer {
            margin-top: 30px;
            font-size: 12px;
            color: #9CA3AF;
            text-align: center;
        }

        .login-link {
            margin-top: 20px;
            font-size: 14px;
            color: #6B7280;
            text-align: center;
        }

        .login-link a {
            color: #0B3C5D;
            text-decoration: none;
            font-weight: 600;
        }
    </style>
</head>

<body>

    <div class="register-card">
        <div class="brand-header">
            <h1>POS Management</h1>
        </div>

        <h2>Registration</h2>
        <p class="subtitle">Enter all the Details to register.</p>

        <c:if test="${not empty message}">
            <div class="error-message">
                ${message}
            </div>
        </c:if>

        <form:form action="register" method="post" modelAttribute="userDto">

            <div class="form-group">
                <label>Full Name *</label>
                <form:input path="name" required="required" placeholder="eg. kushal"/>
            </div>

            <div class="form-group">
                <label>Email Address *</label>
                <form:input
                        path="username"
                        type="email"
                        required="required"
                        placeholder="eg. user@ust.com"
                        pattern="^[a-zA-Z0-9._%+-]+@(gmail\.com|yahoo\.com|outlook\.com|ust\.com)$"
                        title="Only @gmail.com, @yahoo.com, @outlook.com, @ust.com allowed"
                />
            </div>

            <div class="form-group">
                <label>Roles *</label>
                <form:select path="roles" multiple="true" required="required">
                    <form:options items="${roles}" itemValue="identifier" itemLabel="identifier"/>
                </form:select>
            </div>

            <div class="form-group">
                <label>Phone Number *</label>
                <form:input
                        path="phoneNo"
                        required="required"
                        inputmode="numeric"
                        placeholder="10-digit mobile number"
                        pattern="[0-9]{10}"
                        title="Phone number must be exactly 10 digits"/>
            </div>

            <div class="form-group">
                <label>Password *</label>
                <form:password path="password" required="required" placeholder="eg. @#!%$"/>
            </div>

            <input type="submit" value="Register User" class="btn-submit"/>

        </form:form>

        <div class="login-link">
            Already have an account? <a href="${pageContext.request.contextPath}/login">Login here</a>
        </div>

        <div class="footer">
            Developed by <strong>UST Global</strong> Trainees
        </div>
    </div>

</body>
</html>