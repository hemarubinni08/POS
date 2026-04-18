<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
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
            margin-bottom: 25px;
            font-weight: 600;
        }

        label {
            font-weight: 600;
            font-size: 14px;
        }

        .form-control {
            border-radius: 8px;
            padding: 10px 12px;
        }

        .role-box {
            border: 1px solid #ced4da;
            border-radius: 8px;
            padding: 10px;
            max-height: 140px;
            overflow-y: auto;
        }

        .role-item {
            display: flex;
            align-items: center;
            margin-bottom: 6px;
        }

        .role-item input {
            margin-right: 8px;
        }

        .btn-update {
            width: 100%;
            padding: 12px;
            background: #4b6cb7;
            border: none;
            color: #fff;
            font-weight: 600;
            border-radius: 8px;
        }

        .btn-update:hover {
            background: #182848;
        }
    </style>
</head>

<body>
${message}

<div class="update-card">
    <h3>Update User</h3>

    <form:form action="/user/update" method="post" modelAttribute="userDto">

        <form:input type="hidden" path="id"/>

        <div class="mb-3">
            <label>Name</label>
            <form:input path="name" cssClass="form-control" required="true"/>
        </div>

        <div class="mb-3">
            <label>Email</label>
            <form:input path="username" cssClass="form-control" required="true" type="email"/>
        </div>

           <div class="mb-3">
                   <label class="form-label">Phone Number <span class="text-danger">*</span></label>

                   <form:input path="phoneNo"
                               cssClass="form-control"
                               placeholder="Enter 10-digit number"
                               required="required"
                               pattern="^[6-9][0-9]{9}$"
                               title="Enter valid 10-digit Indian mobile number"/>
               </div>

        <div class="mb-3">
            <label>Roles</label>

            <div class="role-box">
                <c:forEach var="r" items="${roles}">
                    <c:set var="checked" value="false"/>

                    <c:forEach var="ur" items="${userDto.roles}">
                        <c:if test="${ur == r.identifier}">
                            <c:set var="checked" value="true"/>
                        </c:if>
                    </c:forEach>

                    <div class="role-item">
                        <input
                            type="checkbox"
                            name="roles"
                            value="${r.identifier}"
                            <c:if test="${checked}">checked</c:if>
                        <label>${r.identifier}</label>
                    </div>
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