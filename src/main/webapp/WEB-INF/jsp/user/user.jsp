<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Update User</title>

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Font -->
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600&display=swap" rel="stylesheet">

    <style>
        :root {
            --bg: #f6fff8;
            --card: #ffffff;

            --text: #1f2937;
            --muted: #6b7280;

            --primary: #28a745;
            --primary-hover: #218838;

            --accent: #ffc107;

            --border: #e5e7eb;
            --radius: 14px;

            --shadow: 0 10px 30px rgba(0,0,0,0.08);

            --success: #16a34a;
        }

        * {
            font-family: 'Inter', sans-serif;
            box-sizing: border-box;
        }

        body {
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            background: var(--bg);
            padding: 20px;
        }

        .container-box {
            width: 100%;
            max-width: 520px;
        }

        .card {
            background: var(--card);
            border-radius: var(--radius);
            box-shadow: var(--shadow);
            padding: 26px;
        }

        h3 {
            text-align: center;
            font-weight: 600;
            margin-bottom: 18px;
            color: var(--text);
        }

        label {
            font-size: 13px;
            color: var(--muted);
            margin-bottom: 5px;
        }

        .form-control, select {
            border-radius: 10px;
            padding: 10px;
            border: 1px solid var(--border);
        }

        .form-control:focus, select:focus {
            border-color: var(--primary);
            box-shadow: 0 0 0 3px rgba(40,167,69,0.15);
        }

        select[multiple] {
            height: 110px;
        }

        .badge {
            background: #e9f7ef;
            color: #1e7e34;
            border-radius: 999px;
            padding: 5px 10px;
            font-size: 11px;
            margin: 2px;
            display: inline-block;
        }

        .btn-update {
            width: 100%;
            padding: 10px;
            background: var(--primary);
            color: white;
            border-radius: 10px;
            border: none;
            font-weight: 600;
            margin-top: 10px;
        }

        .btn-update:hover {
            background: var(--primary-hover);
        }

        .back-link {
            display: block;
            text-align: center;
            margin-top: 14px;
            font-size: 13px;
            color: var(--accent);
            font-weight: 600;
            text-decoration: none;
        }

        .back-link:hover {
            text-decoration: underline;
        }

        .message {
            text-align: center;
            font-size: 13px;
            color: var(--success);
            margin-bottom: 10px;
        }
    </style>

    <script>
        function validateForm() {
            let name = document.getElementsByName("name")[0].value.trim();
            let email = document.getElementsByName("username")[0].value.trim();
            let phone = document.getElementsByName("phoneNo")[0].value.trim();

            if (name === "" || email === "" || phone === "") {
                alert("All fields are required!");
                return false;
            }

            let emailPattern = /^[^ ]+@[^ ]+\.[a-z]{2,}$/;
            if (!email.match(emailPattern)) {
                alert("Invalid email format!");
                return false;
            }

            return true;
        }
    </script>
</head>

<body>

<div class="container-box">

    <c:if test="${not empty message}">
        <div class="message">${message}</div>
    </c:if>

    <div class="card">

        <h3>Update User</h3>

        <form:form action="${pageContext.request.contextPath}/user/update"
                   method="post"
                   modelAttribute="user"
                   onsubmit="return validateForm()">

            <form:hidden path="id"/>

            <div class="mb-3">
                <label>Name</label>
                <form:input path="name" cssClass="form-control" required="true"/>
            </div>

            <div class="mb-3">
                <label>Email</label>
                <form:input path="username" cssClass="form-control" type="email" required="true"/>
            </div>

            <div class="mb-3">
                <label>Phone Number</label>
                <form:input path="phoneNo" cssClass="form-control" required="true"/>
            </div>

            <div class="mb-3">
                <label>Roles</label>

                <div class="mb-2">
                    <strong style="font-size:12px; color:var(--muted)">Current:</strong><br/>
                    <c:forEach var="r" items="${user.roles}">
                        <span class="badge">${r}</span>
                    </c:forEach>
                </div>

                <form:select path="roles" multiple="true" cssClass="form-control" required="true">
                    <form:options items="${roles}" itemValue="identifier" itemLabel="identifier"/>
                </form:select>
            </div>

            <button type="submit" class="btn-update">Update User</button>

        </form:form>

        <a href="${pageContext.request.contextPath}/user/list" class="back-link">
            ← Back to User List
        </a>

    </div>

</div>

</body>
</html>