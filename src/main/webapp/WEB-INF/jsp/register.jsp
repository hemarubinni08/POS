<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Retail POS | Staff Registration</title>

<style>
    :root {
        --primary: #2563eb;
        --primary-dark: #1d4ed8;
        --bg-light: #f8fafc;
        --text-main: #0f172a;
        --text-muted: #64748b;
        --border: #e2e8f0;
        --card-bg: #ffffff;
    }

    * { box-sizing: border-box; }

    body {
        margin: 0;
        min-height: 100vh;
        font-family: 'Inter', -apple-system, system-ui, sans-serif;
        background-color: var(--bg-light);
        background-image: radial-gradient(#cbd5e1 0.5px, transparent 0.5px);
        background-size: 24px 24px;
        display: flex;
        justify-content: center;
        align-items: center;
        padding: 40px 20px;
    }

    .container {
        background: var(--card-bg);
        padding: 40px;
        width: 100%;
        max-width: 460px;
        border-radius: 12px;
        box-shadow: 0 4px 6px -1px rgba(0,0,0,0.1),
                    0 2px 4px -1px rgba(0,0,0,0.06);
        border-top: 5px solid var(--primary);
    }

    .header {
        margin-bottom: 30px;
    }

    .header h2 {
        margin: 0;
        font-size: 24px;
        color: var(--text-main);
    }

    .header p {
        margin-top: 8px;
        font-size: 14px;
        color: var(--text-muted);
    }

    .error-msg {
        background-color: #fef2f2;
        color: #dc2626;
        padding: 12px;
        border-radius: 6px;
        margin-bottom: 20px;
        text-align: center;
    }

    .form-group { margin-bottom: 18px; }

    label {
        display: block;
        font-size: 12px;
        font-weight: 600;
        color: var(--text-muted);
        margin-bottom: 6px;
        text-transform: uppercase;
    }

    input {
        width: 100%;
        padding: 12px 14px;
        border: 1px solid var(--border);
        border-radius: 6px;
        font-size: 15px;
    }

    input:focus {
        outline: none;
        border-color: var(--primary);
        box-shadow: 0 0 0 3px rgba(37,99,235,0.1);
    }

    .role-section { margin: 24px 0; }

    .role-list {
        border: 1px solid var(--border);
        border-radius: 8px;
        max-height: 160px;
        overflow-y: auto;
        background: #fbfcfe;
    }

    .role-item {
        display: flex;
        justify-content: space-between;
        padding: 10px 14px;
        border-bottom: 1px solid var(--border);
    }

    .role-item:last-child { border-bottom: none; }

    button {
        width: 100%;
        padding: 14px;
        background-color: #1e293b;
        color: white;
        border: none;
        border-radius: 6px;
        font-size: 15px;
        font-weight: 600;
        cursor: pointer;
    }

    button:hover { background-color: #0f172a; }

    /* Back Button */
    .back-btn {
        display: block;
        margin-top: 12px;
        text-align: center;
        padding: 12px;
        border-radius: 6px;
        background-color: #e5e7eb;
        color: #111827;
        font-weight: 600;
        text-decoration: none;
    }

    .back-btn:hover {
        background-color: #d1d5db;
    }

    .footer-links {
        margin-top: 25px;
        padding-top: 20px;
        border-top: 1px solid var(--border);
        text-align: center;
    }

    .link-btn {
        color: var(--primary);
        font-weight: 600;
        text-decoration: none;
    }
</style>
</head>

<body>

<div class="container">
    <div class="header">
        <h2>Create Staff Account</h2>
        <p>Register new personnel for the POS system.</p>
    </div>

    <c:if test="${not empty message}">
        <div class="error-msg">⚠️ ${message}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/register" method="post">

        <div class="form-group">
            <label>Full Name</label>
            <input type="text" name="name" value="${userDto.name}" required>
        </div>

        <div class="form-group">
            <label>E-mail Address</label>
            <input type="email" name="username" value="${userDto.username}" required>
        </div>

        <div class="form-group">
            <label>Secure Password</label>
            <input type="password" name="password" required>
        </div>

        <div class="form-group">
            <label>Phone Number</label>
            <input type="text" name="phoneNo" value="${userDto.phoneNo}" required>
        </div>

        <div class="role-section">
            <label>Access Permissions (Roles)</label>
            <div class="role-list">
                <c:forEach var="r" items="${roles}">
                    <div class="role-item">
                        <span>${r.identifier}</span>
                        <input type="checkbox" name="roles" value="${r.identifier}">
                    </div>
                </c:forEach>
            </div>
        </div>

        <!-- Register -->
        <button type="submit">Complete Registration</button>

        <!-- Back Button -->
        <a href="${pageContext.request.contextPath}/user/list" class="back-btn">
            ← Back
        </a>

        <div class="footer-links">
            <a href="${pageContext.request.contextPath}/user/list" class="link-btn">
                View Registered Users
            </a>
        </div>

    </form>
</div>

</body>
</html>