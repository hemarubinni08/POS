<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Edit Node</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
          rel="stylesheet">

    <style>
        body {
            min-height: 100vh;
            background: linear-gradient(135deg, #667eea, #764ba2);
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .card {
            width: 500px;
            border-radius: 15px;
        }

        h4 {
            font-weight: 600;
        }

        /* Dropdown container */
        .role-dropdown {
            position: relative;
        }

        .dropdown-box {
            border: 1px solid #ccc;
            padding: 10px;
            border-radius: 8px;
            background: #fff;
            cursor: pointer;
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
            z-index: 999;
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
    </style>
</head>

<body>

<c:if test="${not empty message}">
    <div class="alert alert-info text-center w-100">
        ${message}
    </div>
</c:if>

<div class="card shadow-lg">
    <div class="card-body">

        <h4 class="text-center mb-4 text-primary">Edit Node</h4>

        <c:if test="${empty node}">
            <div class="alert alert-danger text-center">
                Node not found
            </div>
        </c:if>

        <c:if test="${not empty node}">
            <form:form action="${pageContext.request.contextPath}/node/update"
                       method="post"
                       modelAttribute="node">

                <form:hidden path="id"/>

                <!-- Identifier -->
                <div class="mb-3">
                    <label class="form-label fw-semibold">Node Identifier</label>
                    <form:input path="identifier"
                                cssClass="form-control"
                                readonly="true"/>
                </div>

                <!-- Path -->
                <div class="mb-3">
                    <label class="form-label fw-semibold">Node Path</label>
                    <form:input path="path"
                                cssClass="form-control"
                                required="true"/>
                </div>

                <!-- Roles Dropdown with Checkbox -->
                <div class="mb-4 role-dropdown" id="roleDropdown">

                    <label class="form-label fw-semibold">Assign Roles</label>

                    <div class="dropdown-box" onclick="toggleDropdown()">
                        Select Roles
                    </div>

                    <div class="dropdown-list">
                        <c:forEach items="${roles}" var="role">
                            <label>
                                <input type="checkbox" name="roles"
                                       value="${role.identifier}">
                                ${role.identifier}
                            </label>
                        </c:forEach>
                    </div>

                </div>

                <!-- Buttons (ALL GREY) -->
                <div class="d-flex gap-2">

                    <button type="submit" class="btn btn-secondary w-100">
                        Update
                    </button>

                    <a href="${pageContext.request.contextPath}/node/list"
                       class="btn btn-secondary w-100 text-center">
                        Cancel
                    </a>

                </div>

            </form:form>
        </c:if>

    </div>
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