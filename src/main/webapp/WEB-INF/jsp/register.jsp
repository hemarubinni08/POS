<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>User Registration</title>

<link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600&display=swap"
      rel="stylesheet">

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

.back-btn {
    position: fixed;
    top: 20px;
    left: 20px;
    width: 42px;
    height: 42px;
    border-radius: 50%;
    background: white;
    display: flex;
    align-items: center;
    justify-content: center;
    cursor: pointer;
    box-shadow: 0 8px 20px rgba(0,0,0,0.2);
}
.back-btn::before {
    content: '';
    width: 10px;
    height: 10px;
    border-left: 3px solid #333;
    border-bottom: 3px solid #333;
    transform: rotate(45deg);
    margin-left: 5px;
}

.register-card {
    width: 430px;
    background: rgba(255,255,255,0.95);
    padding: 35px 40px;
    border-radius: 16px;
    box-shadow: 0 25px 50px rgba(0,0,0,0.25);
}

h2 {
    text-align: center;
    margin-bottom: 30px;
    color: #4b6cb7;
}

.form-group {
    margin-bottom: 16px;
}

label {
    font-size: 13px;
    font-weight: 500;
    display: block;
    margin-bottom: 6px;
}

input {
    width: 100%;
    padding: 11px 14px;
    border-radius: 8px;
    border: 1px solid #ccc;
    font-size: 14px;
}

.error {
    color: red;
    font-size: 12px;
    display: none;
}

input:invalid:not(:placeholder-shown):not(:focus) {
    border-color: red;
}
input:invalid:not(:placeholder-shown):not(:focus) + .error {
    display: block;
}

#roleList {
    border: 1px solid #ccc;
    border-radius: 8px;
    padding: 8px;
    max-height: 150px;
    overflow-y: auto;
}
.role-item {
    padding: 6px 4px;
}
.role-item label {
    display: flex;
    align-items: center;
    gap: 10px;
    cursor: pointer;
}
.role-item input[type="checkbox"] {
    width: 16px;
    height: 16px;
    margin: 0;
}

.btn-submit {
    margin: 20px auto 0;
    display: block;
    width: 220px;
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

<div class="back-btn" onclick="history.back()"></div>

<div class="register-card">
    <h2>User Registration</h2>

    <form action="register" method="post" onsubmit="return validateRoles();">

        <!-- NAME -->
        <div class="form-group">
            <label>Name</label>
            <input type="text" name="name" required placeholder="Enter name">
            <span class="error">Name is required</span>
        </div>

        <!-- EMAIL -->
        <div class="form-group">
            <label>Email</label>
            <input type="email" name="username" required placeholder="Enter email">
            <span class="error">Valid email is required</span>
        </div>

        <!-- ROLES -->
        <div class="form-group">
            <label>Roles</label>
            <div id="roleList">
                <div class="role-item">
                    <label><input type="checkbox" name="roles" value="Admin"> Admin</label>
                </div>
                <div class="role-item">
                    <label><input type="checkbox" name="roles" value="User"> User</label>
                </div>
                <div class="role-item">
                    <label><input type="checkbox" name="roles" value="Manager"> Manager</label>
                </div>
            </div>
            <span class="error" id="roleError">Select at least one role</span>
        </div>

        <!-- PHONE -->
        <div class="form-group">
            <label>Phone Number</label>
            <input type="tel" name="phoneNo" pattern="[0-9]{10}" required placeholder="10 digit number">
            <span class="error">Enter valid 10 digit phone number</span>
        </div>

        <!-- PASSWORD -->
        <div class="form-group">
            <label>Password</label>
            <input type="password" name="password" minlength="6" required placeholder="Enter password">
            <span class="error">Minimum 6 characters required</span>
        </div>

        <input type="submit" value="Register" class="btn-submit">
    </form>
</div>

<script>
function validateRoles() {
    const roles = document.querySelectorAll('input[name="roles"]');
    const roleError = document.getElementById('roleError');
    let selected = false;

    roles.forEach(r => {
        if (r.checked) selected = true;
    });

    if (!selected) {
        roleError.style.display = 'block';
        return false;
    } else {
        roleError.style.display = 'none';
        return true;
    }
}
</script>

</body>
</html>