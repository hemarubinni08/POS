<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<title>User Registration</title>

<link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600&display=swap"
      rel="stylesheet">

<style>
body {
    margin: 0;
    font-family: 'Inter', sans-serif;
    background: #d1d5db;
}

/* 🎯 CONTAINER */
.register-card {
    width: 430px;
    margin: 100px auto;
    background: #f1f5f9;
    padding: 35px 40px;
    border-radius: 16px;
    box-shadow: 0 10px 25px rgba(0,0,0,0.1);
}

/* 🔷 TITLE */
h2 {
    text-align: center;
    margin-bottom: 20px;
    font-size: 22px;
    color: #0891b2;
    font-weight: 600;
}

/* ❌ ERROR MESSAGE */
.error-message {
    margin-bottom: 15px;
    padding: 10px 12px;
    background-color: #fee2e2;
    color: #b91c1c;
    border: 1px solid #fecaca;
    border-radius: 8px;
    font-size: 13px;
    text-align: center;
}

/* 🏷 LABEL */
label {
    margin-top: 16px;
    display: block;
    font-weight: 600;
    font-size: 13px;
    color: #334155;
}

/* ✏ INPUT */
input, select {
    width: 100%;
    margin-top: 6px;
    padding: 10px;
    border: 1px solid #cbd5e1;
    border-radius: 8px;
    font-size: 13px;
    outline: none;
    transition: 0.2s;
}

input:focus, select:focus {
    border-color: #0891b2;
    box-shadow: 0 0 0 2px rgba(8,145,178,0.2);
}

select[multiple] {
    height: 130px;
}

/* 🔥 BUTTON */
.btn-submit {
    margin-top: 28px;
    width: 100%;
    padding: 12px;
    background: linear-gradient(135deg, #0891b2, #0e7490);
    color: white;
    border: none;
    border-radius: 20px;
    font-weight: 600;
    cursor: pointer;
    transition: 0.25s;
}

.btn-submit:hover {
    background: linear-gradient(135deg, #0e7490, #075985);
    transform: translateY(-2px);
    box-shadow: 0 6px 15px rgba(8,145,178,0.4);
}
</style>

<script>
function validateForm() {

    const name = document.getElementById("name").value.trim();
    const email = document.getElementById("username").value.trim();
    const roles = document.getElementById("roles").selectedOptions.length;
    const phone = document.getElementById("phoneNo").value.trim();
    const password = document.getElementById("password").value.trim();

    const gmailRegex = /^[a-zA-Z0-9._%+-]+@gmail\.com$/;

    if (!name || !email || roles === 0 || !phone || !password) {
        alert("Please fill all the details before registering");
        return false;
    }

    if (!gmailRegex.test(email)) {
        alert("Email must be in the format abc@gmail.com");
        return false;
    }

    return true;
}
</script>

</head>

<body>

<div class="register-card">

<h2>User Registration</h2>

<!-- ✅ ERROR MESSAGE (TOP, PERFECT ALIGNMENT) -->
<c:if test="${not empty message}">
    <div class="error-message">
        ${message}
    </div>
</c:if>

<form:form action="register"
           method="post"
           modelAttribute="userDto"
           onsubmit="return validateForm();">

    <!-- Name -->
    <label>Name</label>
    <form:input path="name" id="name" required="true"/>

    <!-- Email -->
    <label>Email</label>
    <form:input
            path="username"
            id="username"
            required="true"
            pattern="^[a-zA-Z0-9._%+-]+@gmail\.com$"
            title="Email must be in the format name@gmail.com"/>

    <!-- Roles -->
    <label>Roles</label>
    <form:select path="roles" id="roles" multiple="true" required="true">
        <form:options items="${roles}"
                      itemValue="identifier"
                      itemLabel="identifier"/>
    </form:select>

<div class="form-group">
    <label>Mobile number</label>
    <form:input path="phoneNo"
                type="tel"
                pattern="[0-9]{10}"
                maxlength="10"
                required="true"
                title="Enter a valid 10-digit mobile number"/>
</div>

    <!-- Password -->
    <label>Password</label>
    <form:password path="password" id="password" required="true"/>

    <input type="submit" value="Register" class="btn-submit"/>

</form:form>

</div>

</body>
</html>