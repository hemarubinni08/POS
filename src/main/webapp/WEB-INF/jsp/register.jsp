<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Staff Registration | Retail Core</title>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">

    <style>
        :root {
            --retail-navy: #1e293b;
            --retail-blue: #2563eb;
            --bg-light: #f8fafc;
            --text-main: #0f172a;
            --text-muted: #64748b;
            --border-color: #e2e8f0;
            --error-red: #ef4444;
        }

        body {
            margin: 0;
            font-family: 'Inter', sans-serif;
            min-height: 100vh;
            background-color: var(--bg-light);
            background-image: radial-gradient(#cbd5e1 0.5px, transparent 0.5px);
            background-size: 24px 24px;
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 20px;
        }

        .register-card {
            width: 100%;
            max-width: 450px;
            background: #ffffff;
            padding: 40px;
            border-radius: 12px;
            box-shadow: 0 10px 25px -5px rgba(0, 0, 0, 0.1);
            border-top: 5px solid var(--retail-blue);
        }

        .header { margin-bottom: 30px; }
        h2 { margin: 0; color: var(--text-main); font-size: 24px; font-weight: 700; }
        .subtitle { color: var(--text-muted); font-size: 14px; margin-top: 5px; }

        .form-group { margin-bottom: 20px; position: relative; }

        label.field-label {
            display: block;
            font-size: 12px;
            font-weight: 600;
            color: var(--text-muted);
            margin-bottom: 8px;
            text-transform: uppercase;
            letter-spacing: 0.05em;
        }

        input[type="text"], input[type="email"], input[type="password"], input[type="tel"] {
            width: 100%;
            padding: 12px 14px;
            border-radius: 8px;
            border: 1.5px solid var(--border-color);
            font-size: 14px;
            box-sizing: border-box;
            transition: all 0.2s ease;
            background-color: #fcfcfc;
        }

        input:focus {
            outline: none;
            border-color: var(--retail-blue);
            box-shadow: 0 0 0 3px rgba(37, 99, 235, 0.1);
        }

        /* Error Message Styling */
        .error-msg {
            color: var(--error-red);
            font-size: 12px;
            margin-top: 5px;
            display: block;
            font-weight: 500;
        }

        .checkbox-container {
            border: 1.5px solid var(--border-color);
            border-radius: 8px;
            padding: 12px;
            background-color: #fcfcfc;
            max-height: 150px;
            overflow-y: auto;
        }

        .checkbox-container span {
            display: flex;
            align-items: center;
            margin-bottom: 8px;
            font-size: 14px;
            color: var(--text-main);
            cursor: pointer;
        }

        .checkbox-container input[type="checkbox"] {
            width: 18px;
            height: 18px;
            margin-right: 10px;
            cursor: pointer;
            accent-color: var(--retail-blue);
        }

        .validation-hook {
            position: absolute; bottom: 0; left: 50%; opacity: 0; width: 0; height: 0; pointer-events: none;
        }

        .btn-submit {
            margin-top: 10px;
            width: 100%;
            padding: 14px;
            background-color: var(--retail-navy);
            color: white;
            border: none;
            border-radius: 8px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: background-color 0.2s;
        }

        .btn-submit:hover { background-color: #0f172a; }

        .footer-link {
            text-align: center;
            margin-top: 25px;
            padding-top: 20px;
            border-top: 1px solid var(--border-color);
            font-size: 14px;
        }

        .footer-link a { color: var(--retail-blue); text-decoration: none; font-weight: 600; }
    </style>
</head>

<body>

<c:if test="${not empty message}">
    <div style="color: var(--error-red); background: #fee2e2; padding: 10px; border-radius: 8px; margin-bottom: 20px; font-size: 14px; border: 1px solid #fecaca;">
        ${message}
    </div>
</c:if>

<div class="register-card">
    <div class="header">
        <h2>Create Staff Account</h2>
        <p class="subtitle">System Onboarding & Role Assignment</p>
    </div>

    <form:form action="register" method="post" modelAttribute="userDto">

        <div class="form-group">
            <label class="field-label">Full Name</label>
            <form:input path="name" placeholder="John Doe" required="required"/>
        </div>

        <div class="form-group">
            <label class="field-label">Email Address</label>
            <form:input path="username" type="email" placeholder="name@retail.com" required="required"/>
            <%-- Shows backend error if email exists --%>
            <form:errors path="username" cssClass="error-msg" />
        </div>

        <div class="form-group">
            <label class="field-label">Assign System Roles</label>
            <div class="checkbox-container">
                <form:checkboxes path="roles" items="${roles}" itemValue="identifier"
                                 itemLabel="identifier" element="span"
                                 onchange="document.getElementById('roleWatcher').required = !Array.from(document.getElementsByName('roles')).some(x => x.checked);"/>
                <input type="checkbox" id="roleWatcher" class="validation-hook" required title="Please select at least one role">
            </div>
            <form:errors path="roles" cssClass="error-msg" />
        </div>

        <div class="form-group">
            <label class="field-label">Phone Number (10 Digits)</label>
            <%-- pattern="[0-9]{10}" forces exactly 10 digits --%>
            <form:input path="phoneNo"
                        type="tel"
                        placeholder="9876543210"
                        required="required"
                        oninput="this.value = this.value.replace(/[^0-9]/g, '');"
                        maxlength="10"
                        pattern="[0-9]{10}"
                        inputmode="numeric"
                        title="Please enter exactly 10 digits"/>
            <form:errors path="phoneNo" cssClass="error-msg" />
        </div>

        <div class="form-group">
            <label class="field-label">Secure Password (Min 6 Characters)</label>
            <form:password path="password" placeholder="••••••••"
                           minlength="6" required="required"/>
            <form:errors path="password" cssClass="error-msg" />
        </div>

        <button type="submit" class="btn-submit">Register User</button>

    </form:form>

    <div class="footer-link">
        Already have an account?
        <a href="${pageContext.request.contextPath}/login">Sign In</a>
    </div>
</div>

</body>
</html>