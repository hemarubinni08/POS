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
            width: 450px;
            border-radius: 15px;
        }

        h4 {
            font-weight: 600;
        }

        small {
            font-size: 12px;
        }
    </style>
</head>

<body>

${message}

<div class="card shadow-lg">
    <div class="card-body">

        <h4 class="text-center mb-4 text-primary">Edit Node</h4>

        <c:if test="${empty node}">
            <div class="alert alert-danger text-center">
                Node not found
            </div>
        </c:if>

        <c:if test="${not empty node}">
            <form:form action="/node/update"
                       method="post"
                       modelAttribute="node">

                <!-- ID (hidden) -->
                <form:hidden path="id"/>

                <!-- Node Name -->
                <div class="mb-3">
                    <label class="form-label">Node Name</label>
                    <form:input path="identifier"
                                cssClass="form-control"
                                required="true"/>
                </div>

                <!-- ✅ Path -->
                <div class="mb-3">
                    <label class="form-label">Path</label>
                    <form:input path="path"
                                cssClass="form-control"
                                required="true"/>
                </div>

                <!-- ✅ Roles -->
                <div class="mb-4">
                    <label class="form-label">Roles</label>

                    <form:select path="roles"
                                 multiple="true"
                                 cssClass="form-control">

                        <form:options items="${roles}"
                                      itemValue="identifier"
                                      itemLabel="identifier"/>

                    </form:select>

                    <small class="text-muted">
                        Hold Ctrl (Windows/Linux) or Cmd (Mac) to select multiple roles
                    </small>
                </div>

                <div class="d-flex justify-content-between">
                    <a href="/node/list" class="btn btn-outline-secondary">
                        Cancel
                    </a>

                    <button type="submit" class="btn btn-primary">
                        Update
                    </button>
                </div>

            </form:form>
        </c:if>

    </div>
</div>

</body>
</html>
