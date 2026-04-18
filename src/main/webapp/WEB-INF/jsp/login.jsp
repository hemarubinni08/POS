<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Login</title>

    <!-- Bootstrap 5 CDN -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>

<body style="background-color: #E9EEF5" class="d-flex align-items-center justify-content-center vh-100">

<div class="card shadow p-4" style="width: 350px;">
    <h3 style="color: #000000;" class="text-center mb-4">Login</h3>

    <form th:action="@{/login}" method="post">
        <div class="mb-3">
            <label class="form-label">Email</label>
            <input type="text" name="username" class="form-control" required />
        </div>

        <div class="mb-3">
            <label class="form-label">Password</label>
            <input type="password" name="password" class="form-control" required />
        </div>

        <%
            String error = (String) session.getAttribute("errorMessage");
            if (error != null && !error.trim().isEmpty()) {
        %>
            <div id="errorAlert"
                 class="alert alert-danger text-center py-2 mb-3"
                 role="alert">
                <strong>Login failed:</strong> <%= error %>
            </div>
        <%
                session.removeAttribute("errorMessage");
            }
        %>

        <button type="submit" class="btn btn-success w-100">Login</button>

        <!-- Register link -->
        <div class="text-center mt-3">
            <small>
                Don't have an account?
                <a href="/register" class="text-decoration-none">Register here</a>
            </small>
        </div>
    </form>
</div>
<script>
    document.addEventListener("DOMContentLoaded", function () {
        const alertBox = document.getElementById("errorAlert");

        if (alertBox) {
            setTimeout(() => {
                alertBox.style.opacity = "0";
                alertBox.style.transform = "translateY(-5px)";
                alertBox.style.transition = "all 0.5s ease";

                setTimeout(() => alertBox.remove(), 500);
            }, 3500); // disappears after 3.5 seconds
        }
    });
</script>
</body>
</html>