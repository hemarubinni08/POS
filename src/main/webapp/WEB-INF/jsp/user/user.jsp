<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Edit Profile</title>

<style>
    /* ✅ Modern UI Transformation */
    * { margin: 0; padding: 0; box-sizing: border-box; }

    body {
        font-family: 'Inter', -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, sans-serif;
        background: radial-gradient(circle at top left, #f8fafc, #e2e8f0);
        display: flex;
        justify-content: center;
        align-items: center;
        min-height: 100vh;
        color: #334155;
    }

    .edit-card {
        width: 100%;
        max-width: 440px;
        background: rgba(255, 255, 255, 0.95);
        padding: 40px;
        border-radius: 20px;
        border: 1px solid rgba(255, 255, 255, 0.3);
        box-shadow: 0 20px 25px -5px rgba(0, 0, 0, 0.1), 0 10px 10px -5px rgba(0, 0, 0, 0.04);
        backdrop-filter: blur(10px);
    }

    h2 {
        text-align: center;
        margin-bottom: 30px;
        font-weight: 700;
        letter-spacing: -0.025em;
        color: #1e293b;
        font-size: 24px;
    }

    label {
        font-size: 14px;
        font-weight: 600;
        margin-bottom: 6px;
        display: block;
        color: #64748b;
    }

    /* Target Spring Form Inputs */
    input[type="text"], input[type="email"], select {
        width: 100%;
        padding: 12px 16px;
        margin-bottom: 20px;
        border-radius: 10px;
        border: 1px solid #e2e8f0;
        background-color: #fcfcfd;
        font-size: 15px;
        color: #1e293b;
        transition: all 0.2s ease;
    }

    input:focus {
        outline: none;
        border-color: #6366f1;
        box-shadow: 0 0 0 4px rgba(99, 102, 241, 0.1);
        background-color: #ffffff;
    }

    .role-list {
        border: 1px solid #f1f5f9;
        border-radius: 12px;
        padding: 8px;
        max-height: 160px;
        overflow-y: auto;
        margin-bottom: 24px;
        background: #f8fafc;
    }

    /* Custom Scrollbar */
    .role-list::-webkit-scrollbar { width: 5px; }
    .role-list::-webkit-scrollbar-thumb { background: #cbd5e1; border-radius: 10px; }

    .role-item {
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 10px 12px;
        margin-bottom: 4px;
        font-size: 14px;
        font-weight: 500;
        border-radius: 8px;
        transition: background 0.2s;
        cursor: pointer;
    }

    .role-item:hover {
        background-color: #eff6ff;
        color: #2563eb;
    }

    /* Styled Checkbox */
    input[type="checkbox"] {
        width: 18px;
        height: 18px;
        cursor: pointer;
        accent-color: #6366f1;
    }

    button {
        width: 100%;
        padding: 14px;
        border: none;
        border-radius: 12px;
        background: #1e293b;
        color: white;
        font-size: 16px;
        font-weight: 600;
        cursor: pointer;
        transition: all 0.3s ease;
    }

    button:hover {
        background: #0f172a;
        transform: translateY(-1px);
        box-shadow: 0 10px 15px -3px rgba(0, 0, 0, 0.1);
    }

    button:active {
        transform: translateY(0);
    }

    .error {
        margin-top: 15px;
        text-align: center;
        color: #e11d48;
        background: #fff1f2;
        padding: 10px;
        border-radius: 8px;
        font-size: 14px;
        font-weight: 500;
    }

    .link-btn {
        display: block;
        text-align: center;
        margin-top: 20px;
        color: #64748b;
        text-decoration: none;
        font-size: 14px;
        font-weight: 500;
        transition: color 0.2s;
    }

    .link-btn:hover {
        color: #4f46e5;
    }

</style>
</head>

<body>

<div class="edit-card">

    <h2>Edit Profile</h2>

    <c:if test="${not empty user}">

        <form:form action="${pageContext.request.contextPath}/user/update"
                   method="post"
                   modelAttribute="user">

            <form:hidden path="id"/>

            <label>Name</label>
            <form:input path="name" placeholder="Full Name"/>

            <label>Email Address</label>
            <form:input path="username" placeholder="name@example.com"/>

            <label>Phone Number</label>
            <form:input path="phoneNo"
                        required="true"
                        pattern="[0-9]{10}"
                        placeholder="10-digit number"
                        title="Enter a valid 10-digit phone number"
                        oninput="this.value = this.value.replace(/[^0-9]/g, '')"/>

            <label>Assign Roles</label>
            <div class="role-list">
                <c:forEach var="r" items="${roles}">
                    <label class="role-item">
                        <span>${r.identifier}</span>
                        <form:checkbox path="roles" value="${r.identifier}" />
                    </label>
                </c:forEach>
            </div>

            <button type="submit">Save Changes</button>

            <a href="${pageContext.request.contextPath}/user/list" class="link-btn">
                Back to User List
            </a>

        </form:form>

    </c:if>

    <c:if test="${not empty message}">
        <p class="error">${message}</p>
    </c:if>

</div>

</body>
</html>