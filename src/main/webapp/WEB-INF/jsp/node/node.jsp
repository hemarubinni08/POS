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
            background-color: #EDE9E6;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .card {
            width: 400px;
            border-radius: 15px;
        }

        h4 {
            font-weight: 600;
        }
    </style>
</head>

<body>
${message}
<div class="card shadow-lg">
    <div class="card-body">

        <h4 style="color: #5C4F4A;" class="text-center mb-4">Edit Node</h4>

        <c:if test="${empty node}">
            <div class="alert alert-danger text-center">
                Node not found
            </div>
        </c:if>

        <c:if test="${not empty node}">
            <form:form action="/node/update"
                       method="post"
                       modelAttribute="nodeDto">

                <form:hidden path="id" value="${node.id}"/>

                <div class="mb-4">
                    <label class="form-label">Node Name</label>
                    <form:input path="identifier"
                                cssClass="form-control"
                                placeholder="Enter node"
                                value="${node.identifier}"
                                required="true" readonly="true"/>
                </div>

                <div class="mb-4">
                    <label class="form-label">Node Path</label>
                    <form:input path="path"
                                cssClass="form-control"
                                placeholder="Enter path"
                                value="${node.path}"
                                required="true"/>
                </div>

                <div class="mb-4">
                    <label class="form-label">Node Roles</label>
                    <div class="mb-1 text-muted">
                        Current:
                        <c:forEach var="r" items="${node.roles}">
                            <span class="badge bg-secondary me-1">${r}</span>
                        </c:forEach>
                    </div>
                    <form:select path="roles" multiple="true" cssClass="form-control">
                        <form:options items="${roles}" itemValue="identifier" itemLabel="identifier"/>
                    </form:select>
                </div>

                <div class="d-flex justify-content-between">
                    <a href="/node/list" class="btn btn-outline-secondary">
                        Cancel
                    </a>
                    <button type="submit" class="btn btn-success">
                        Update
                    </button>
                </div>

            </form:form>
        </c:if>

    </div>
</div>

</body>
</html>