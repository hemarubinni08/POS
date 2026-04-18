<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Update User</title>

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

        .form-control {
            border-radius: 8px;
            padding: 10px 12px;
        }

        /* 🔽 Dropdown with checkbox */
        .dropdown-box {
            border: 1px solid #ccc;
            padding: 10px;
            border-radius: 8px;
            cursor: pointer;
            background: #fff;
        }

        .dropdown-list {
            display: none;
            position: absolute;
            width: 100%;
            max-height: 180px;
            overflow-y: auto;
            background: white;
            border: 1px solid #ddd;
            border-radius: 8px;
            z-index: 1000;
            margin-top: 5px;
        }

        .role-dropdown.active .dropdown-list {
            display: block;
        }

        .dropdown-list label {
            display: flex;
            align-items: center;
            gap: 8px;
            padding: 8px;
            cursor: pointer;
        }

        .dropdown-list label:hover {
            background: #f1f1f1;
        }

        /* 🔘 GREY BUTTONS */
        .btn-grey {
            background: #6c757d;
            color: white;
            font-weight: 600;
            border: none;
            padding: 10px;
            border-radius: 8px;
        }

        .btn-grey:hover {
            background: #5a6268;
            color: white;
        }
    </style>
</head>

<body>

<div class="update-card">

    <h3>Update User</h3>

    <!-- ERROR MESSAGE -->
    <c:if test="${not empty message}">
        <div class="alert alert-danger text-center">
            ${message}
        </div>
    </c:if>

    <form action="${pageContext.request.contextPath}/user/update" method="post">

        <input type="hidden" name="oldUsername" value="${user.username}" />
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

        <!-- Roles Dropdown with Checkbox -->
        <div class="mb-3 position-relative role-dropdown" id="roleDropdown">

            <label>Select Roles</label>

            <div class="dropdown-box" onclick="toggleDropdown()">
                Select Roles
            </div>

            <div class="dropdown-list">

                <c:forEach var="role" items="${roles}">
                    <label>
                        <input type="checkbox" name="roles"
                               value="${role.identifier}"
                               <c:if test="${user.roles.contains(role.identifier)}">
                                   checked
                               </c:if>>
                        ${role.identifier}
                    </label>
                </c:forEach>

            </div>
        </div>

        <!-- BUTTONS (GREY) -->
        <div class="d-flex gap-2 mt-3">

            <button type="submit" class="btn btn-grey w-100">
                Update User
            </button>

            <a href="/user/list" class="btn btn-grey w-100 text-center">
                Cancel
            </a>

        </div>

    </form>

</div>

<script>
    function toggleDropdown() {
        document.getElementById("roleDropdown").classList.toggle("active");
    }

    document.addEventListener("click", function (e) {
        const dropdown = document.getElementById("roleDropdown");
        if (!dropdown.contains(e.target)) {
            dropdown.classList.remove("active");
        }
    });
</script>

</body>
</html>
