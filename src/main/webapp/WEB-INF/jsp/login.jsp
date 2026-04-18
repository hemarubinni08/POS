<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Retail POS | Secure Login</title>
    <style>
        :root {
            --retail-navy: #1e293b;
            --retail-blue: #2563eb;
            --retail-hover: #1d4ed8;
            --error-red: #dc2626;
            --bg-light: #f8fafc;
            --text-dark: #0f172a;
            --text-muted: #64748b;
        }

        body {
            font-family: 'Inter', -apple-system, sans-serif;
            background-color: var(--bg-light);
            /* Subtle retail-grid pattern */
            background-image: radial-gradient(#cbd5e1 0.5px, transparent 0.5px);
            background-size: 24px 24px;
            margin: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        /* --- Professional Toast Error --- */
        .error-toast {
            position: fixed;
            top: 24px;
            right: 24px;
            background-color: var(--error-red);
            color: white;
            padding: 16px 24px;
            border-radius: 8px;
            box-shadow: 0 10px 15px -3px rgba(0, 0, 0, 0.1);
            font-size: 14px;
            font-weight: 600;
            z-index: 1000;
            display: flex;
            align-items: center;
            animation: slideIn 0.4s ease-out;
        }

        @keyframes slideIn {
            from { transform: translateX(100%); opacity: 0; }
            to { transform: translateX(0); opacity: 1; }
        }

        .container {
            width: 100%;
            max-width: 420px;
            background: #ffffff;
            padding: 40px;
            border-radius: 12px;
            box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06);
            border-top: 5px solid var(--retail-blue); /* Retail accent border */
        }

        .header {
            text-align: left; /* Modern alignment */
            margin-bottom: 32px;
        }

        .header h2 {
            margin: 0;
            color: var(--text-dark);
            font-size: 24px;
            letter-spacing: -0.5px;
        }

        .header p {
            color: var(--text-muted);
            font-size: 14px;
            margin: 8px 0 0 0;
        }

        .form-group {
            margin-bottom: 24px;
        }

        .form-group label {
            display: block;
            font-weight: 600;
            margin-bottom: 8px;
            font-size: 12px;
            color: var(--text-muted);
            text-transform: uppercase;
            letter-spacing: 0.05em;
        }

        .input-field {
            width: 100%;
            padding: 12px 14px;
            border: 1px solid #e2e8f0;
            border-radius: 6px;
            box-sizing: border-box;
            font-size: 15px;
            transition: all 0.2s;
            background-color: #fcfcfc;
        }

        .input-field:focus {
            outline: none;
            border-color: var(--retail-blue);
            box-shadow: 0 0 0 3px rgba(37, 99, 235, 0.1);
            background-color: #fff;
        }

        .btn-login {
            width: 100%;
            padding: 13px;
            background-color: var(--retail-navy);
            color: white;
            border: none;
            border-radius: 6px;
            font-size: 15px;
            font-weight: 600;
            cursor: pointer;
            transition: background-color 0.2s;
            margin-bottom: 16px;
        }

        .btn-login:hover {
            background-color: var(--text-dark);
        }

        /* --- New Register Section --- */
        .footer-action {
            text-align: center;
            border-top: 1px solid #f1f5f9;
            padding-top: 20px;
            margin-top: 10px;
        }

        .footer-action span {
            color: var(--text-muted);
            font-size: 14px;
        }

        .btn-register {
            color: var(--retail-blue);
            text-decoration: none;
            font-size: 14px;
            font-weight: 600;
            margin-left: 5px;
        }

        .btn-register:hover {
            text-decoration: underline;
        }

        .system-tag {
            position: absolute;
            bottom: 20px;
            font-size: 12px;
            color: var(--text-muted);
            font-weight: 500;
        }
    </style>
</head>
<body>

<c:if test="${param.error != null}">
    <div class="error-toast">
        <span style="margin-right:10px;">⚠️</span>
        Invalid E-mail or Password
    </div>
</c:if>

<div class="container">
    <div class="header">
        <h2>System Login</h2>
        <p>Retail Management & POS Inventory</p>
    </div>

    <form action="${pageContext.request.contextPath}/login" method="post">
        <div class="form-group">
            <label>E-mail Address</label>
            <input type="email" name="username" class="input-field" placeholder="admin@retail.com" required autofocus>
        </div>

        <div class="form-group">
            <label>Password</label>
            <input type="password" name="password" class="input-field" placeholder="••••••••" required>
        </div>

        <button type="submit" class="btn-login">Sign In to Dashboard</button>
    </form>

    <div class="footer-action">
        <span>New staff member?</span>
        <a href="${pageContext.request.contextPath}/register" class="btn-register">Register Account</a>
    </div>
</div>

<div class="system-tag">Retail Management System v2.0</div>

</body>
</html>