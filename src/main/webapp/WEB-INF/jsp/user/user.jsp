<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Update User</title>

    <!-- ✅ Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
          rel="stylesheet"/>

    <style>
        body {
            min-height: 100vh;
            background: linear-gradient(135deg, #667eea, #764ba2);
            display: flex;
            justify-content: center;
            align-items: center;
            font-family: "Segoe UI", Arial, sans-serif;
        }

        .update-card {
            width: 460px;
            background: #fff;
            padding: 30px 35px;
            border-radius: 15px;
            box-shadow: 0 20px 40px rgba(0, 0, 0, 0.25);
        }

        h3 {
            text-align: center;
            color: #4b6cb7;
            margin-bottom: 15px;
            font-weight: 600;
        }

        label {
            font-weight: 600;
            font-size: 14px;
            color: #333;
        }

        .form-control,
        .form-select {
            border-radius: 8px;
            padding: 10px 12px;
        }

        select[multiple] {
            height: 130px;
        }

        .btn-update {
            width: 100%;
            padding: 12px;
            background: #4b6cb7;
            border: none;
            color: white;
            font-weight: 600;
            border-radius: 8px;
        }

        .btn-update:hover {
            background: #182848;
        }

        small {
            font-size: 11px;
            color: #666;
        }
    </style>
</head>

<body>

<div class="update-card">
    <h3>Update User</h3>

    <!-- ✅ ERROR MESSAGE DISPLAY -->
    <c:if test="${not empty message}">
        <div class="alert alert-danger text-center">
            ${message}
        </div>
    </c:if>

    <!-- ✅ FORM (ONLY ONE FORM TAG) -->
    <form action="${pageContext.request.contextPath}/user/update" method="post">

        <!-- ✅ Required for username update -->
        <input type="hidden" name="oldUsername" value="${user.username}" />

        <!-- Optional -->
        <input type="hidden" name="id" value="${user.id}" />

        <!-- Name -->
        <div class="mb-3">
            <label>Name</label>
            <input type="text" name="name" class="form-control"
                   value="${user.name}" required />
        </div>

        <!-- Email -->
        <div class="mb-3">
            <label>Email</label>
            <input type="email" name="username" class="form-control"
                   value="${user.username}" required />
        </div>

        <!-- Phone -->
        <div class="mb-3">
            <label>Phone Number</label>
            <input type="text" name="phoneNo" class="form-control"
                   value="${user.phoneNo}" required />
        </div>

        <!-- Roles -->
        <div class="mb-3">
            <label class="fw-semibold">Select Roles</label>
            <select name="roles" id="roles" class="form-select" multiple required size="5">
                <c:forEach var="role" items="${roles}">
                    <option value="${role.identifier}"
                        <c:if test="${user.roles.contains(role.identifier)}">
                            selected
                        </c:if>>
                        ${role.identifier}
                    </option>
                </c:forEach>
            </select>

            <small class="text-muted">
                Hold <kbd>Ctrl</kbd> (Windows/Linux) or <kbd>Cmd</kbd> (Mac)
            </small>
        </div>

        <button type="submit" class="btn-update">
            Update User
        </button>
    </form>

    <div class="text-center mt-3">
        <a href="/user/list">← Back to User List</a>
    </div>
</div>

<script>
    document.querySelector("form").addEventListener("submit", function (e) {

        const name = this.name.value.trim();
        const phone = this.phoneNo.value.trim();
        const roles = document.getElementById("roles");

        if (!/^[A-Za-z ]{3,}$/.test(name)) {
            alert("Name must contain at least 3 letters.");
            e.preventDefault();
            return;
        }

        if (!/^[0-9]{10}$/.test(phone)) {
            alert("Phone number must be exactly 10 digits.");
            e.preventDefault();
            return;
        }

        if ([...roles.options].filter(o => o.selected).length === 0) {
            alert("Please select at least one role.");
            e.preventDefault();
        }
    });
</script>

</body>
</html>