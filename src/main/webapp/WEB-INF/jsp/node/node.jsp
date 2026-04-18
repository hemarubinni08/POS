<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Edit Node</title>

<style>
/* Modern Reset & Font */
* {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
}

body {
    font-family: 'Inter', -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, sans-serif;
    background-color: #f8fafc;
    /* Subtle background texture */
    background-image: radial-gradient(#e2e8f0 0.5px, transparent 0.5px);
    background-size: 20px 20px;
    display: flex;
    justify-content: center;
    align-items: center;
    min-height: 100vh;
    color: #334155;
}

/* Card Container */
.container {
    width: 100%;
    max-width: 440px;
    background: #ffffff;
    padding: 40px;
    border-radius: 16px;
    border: 1px solid #e2e8f0;
    box-shadow: 0 20px 25px -5px rgba(0, 0, 0, 0.1), 0 10px 10px -5px rgba(0, 0, 0, 0.04);
}

/* Title */
h2 {
    text-align: center;
    margin-bottom: 30px;
    font-weight: 700;
    font-size: 24px;
    letter-spacing: -0.025em;
    color: #0f172a;
}

/* Input group */
.input-group {
    margin-bottom: 20px;
}

/* Label */
label {
    font-size: 14px;
    font-weight: 600;
    margin-bottom: 8px;
    display: block;
    color: #64748b;
}

/* Inputs */
input[type="text"] {
    width: 100%;
    padding: 12px 16px;
    border-radius: 10px;
    border: 1px solid #cbd5e1;
    background: #ffffff;
    color: #1e293b;
    font-size: 15px;
    transition: all 0.2s ease;
}

input[type="text"]:focus {
    outline: none;
    border-color: #6366f1;
    box-shadow: 0 0 0 4px rgba(99, 102, 241, 0.1);
}

/* Role list wrapper */
.role-list {
    border: 1px solid #f1f5f9;
    border-radius: 12px;
    padding: 8px;
    max-height: 160px;
    overflow-y: auto;
    background: #f8fafc;
}

/* Custom Scrollbar */
.role-list::-webkit-scrollbar { width: 5px; }
.role-list::-webkit-scrollbar-thumb { background: #cbd5e1; border-radius: 10px; }

/* Alignment */
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
}

.role-item:hover {
    background-color: #eff6ff;
    color: #2563eb;
}

/* Custom Checkbox Color */
.role-item input[type="checkbox"] {
    width: 18px;
    height: 18px;
    cursor: pointer;
    accent-color: #6366f1;
}

/* Primary Button */
button {
    width: 100%;
    padding: 14px;
    margin-top: 10px;
    border-radius: 12px;
    border: none;
    background: #1e293b; /* Sophisticated dark tone */
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

/* Error Notification */
.bottom-error {
    margin-top: 20px;
    padding: 12px;
    text-align: center;
    border-radius: 10px;
    background: #fff1f2;
    border: 1px solid #fecdd3;
    color: #be123c;
    font-size: 14px;
    font-weight: 500;
}

/* Secondary Navigation Link */
.link-btn {
    display: block;
    text-align: center;
    margin-top: 25px;
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

<div class="container">

<h2>Edit Node</h2>

<form:form action="${pageContext.request.contextPath}/node/update"
           method="post"
           modelAttribute="node">

    <form:hidden path="id"/>

    <div class="input-group">
        <label>Node Name</label>
        <form:input path="identifier" placeholder="Enter node name" readonly="true"/>
    </div>

    <div class="input-group">
        <label>Path</label>
        <form:input path="path" placeholder="/example/path"/>
    </div>

    <div class="input-group">
        <label>Assign Roles</label>
        <div class="role-list">
            <c:forEach var="r" items="${roles}">
                <div class="role-item">
                    <span>${r.identifier}</span>
                    <form:checkbox path="roles" value="${r.identifier}" />
                </div>
            </c:forEach>
        </div>
    </div>

    <button type="submit">Save Changes</button>

    <a href="${pageContext.request.contextPath}/node/list" class="link-btn">
        &larr; Back to Node List
    </a>

</form:form>

<c:if test="${not empty message}">
    <div class="bottom-error">
        ${message}
    </div>
</c:if>

</div>

</body>
</html>