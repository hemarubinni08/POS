<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Update User</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
          rel="stylesheet"/>

    <style>
        body {
            margin: 0;
            min-height: 100vh;
            font-family: 'Poppins', sans-serif;
            background: #f7f7fb;
            display: flex;
            justify-content: center;
            align-items: center;
            overflow-x: hidden;
        }

        /* BLOBS */
        .blob {
            position: absolute;
            border-radius: 50%;
            filter: blur(80px);
            opacity: 0.5;
            animation: float 8s ease-in-out infinite;
        }

        .blob1 {
            width: 300px;
            height: 300px;
            background: #3b82f6;
            top: -80px;
            left: -80px;
        }

        .blob2 {
            width: 250px;
            height: 250px;
            background: #60a5fa;
            bottom: -80px;
            right: -80px;
            animation-delay: 2s;
        }

        @keyframes float {
            0%, 100% { transform: translateY(0px); }
            50% { transform: translateY(25px); }
        }

        /* GLASS CARD */
        .update-card {
            width: 480px;
            padding: 35px 40px;
            border-radius: 18px;
            background: rgba(255, 255, 255, 0.75);
            backdrop-filter: blur(18px);
            box-shadow: 0 25px 50px rgba(0,0,0,0.15);
            animation: fadeIn 0.6s ease;
        }

        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(20px); }
            to { opacity: 1; transform: translateY(0); }
        }

        h3 {
            text-align: center;
            color: #3b82f6;
            font-weight: 700;
            margin-bottom: 25px;
        }

        label {
            font-weight: 600;
            font-size: 14px;
            margin-bottom: 4px;
        }

        .form-control {
            border-radius: 10px;
            border: 1px solid #e5e7eb;
            padding: 10px;
            transition: 0.2s;
        }

        .form-control:focus {
            border-color: #3b82f6;
            box-shadow: 0 0 0 3px rgba(59,130,246,0.15);
        }

        /* ROLES CHECKBOX GROUP */
        .checkbox-list {
            display: flex;
            flex-direction: column;
            gap: 7px;
            margin-top: 6px;
        }

        .checkbox-item {
            display: flex;
            align-items: center;
            gap: 10px;
            padding: 10px 12px;
            border: 1px solid #e5e7eb;
            border-radius: 10px;
            cursor: pointer;
            transition: border-color 0.2s, background 0.2s;
        }

        .checkbox-item:hover {
            border-color: #3b82f6;
            background: #eff6ff;
        }

        .checkbox-item input[type="checkbox"] {
            width: 16px;
            height: 16px;
            min-width: 16px;
            margin: 0;
            padding: 0;
            accent-color: #3b82f6;
            cursor: pointer;
        }

        .checkbox-item span {
            font-size: 13px;
            color: #374151;
            font-weight: 500;
        }

        .checkbox-item:has(input:checked) {
            border-color: #3b82f6;
            background: #eff6ff;
        }

        /* BUTTON */
        .btn-update {
            width: 100%;
            padding: 12px;
            border-radius: 10px;
            border: none;

            background: linear-gradient(135deg, #93c5fd, #3b82f6);
            color: white;
            font-weight: 600;

            box-shadow: 0 10px 25px rgba(37,99,235,0.25);
            transition: 0.2s ease;
        }

        .btn-update:hover {
            transform: translateY(-2px);
            box-shadow: 0 15px 30px rgba(37,99,235,0.35);
        }

        a {
            color: #3b82f6;
            text-decoration: none;
            font-weight: 500;
        }

        a:hover {
            text-decoration: underline;
        }
    </style>
</head>

<body>

<!-- BACKGROUND BLOBS -->
<div class="blob blob1"></div>
<div class="blob blob2"></div>

<!-- FORM -->
<div class="update-card">
    <h3>Update User</h3>

    <form:form action="/user/update" method="post" modelAttribute="userDto">

        <form:input type="hidden" path="id"/>

        <div class="mb-3">
            <label for="name">Name</label>
            <form:input id="name" path="name" cssClass="form-control" required="true"/>
        </div>

        <div class="mb-3">
            <label for="email">Email</label>
            <form:input id="email" path="username" type="email"
                        cssClass="form-control"
                        placeholder="example@email.com"
                        required="true"/>
        </div>

        <div class="mb-3">
            <label for="phoneNo">Phone Number</label>
            <form:input id="phoneNo" path="phoneNo" type="number"
                        cssClass="form-control"
                        placeholder="10-digit phone number"
                        required="true"/>
        </div>

        <div class="mb-3">
            <label for="checkbox-list">Roles</label>
            <div id="checkbox-list" class="checkbox-list">
                <c:forEach var="role" items="${roles}">
                    <label for="roles" class="checkbox-item">
                        <form:checkbox id="roles" path="roles" value="${role.identifier}"/>
                        <span>${role.identifier}</span>
                    </label>
                </c:forEach>
            </div>
        </div>

        <button type="submit" class="btn-update">Update User</button>

    </form:form>

    <div class="text-center mt-3">
        <a href="/user/list">← Back to User List</a>
    </div>

</div>

</body>
</html>
