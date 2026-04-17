<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add Node</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
          rel="stylesheet"/>

    <style>
        body {
            min-height: 100vh;
            background: linear-gradient(135deg, #1d976c, #93f9b9);
            display: flex;
            justify-content: center;
            align-items: center;
        }
        .card {
            border-radius: 15px;
        }
        select[multiple] {
            height: 120px;
        }
    </style>
</head>

<body>

<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-5">

            <div class="card shadow-lg">
                <div class="card-body p-4">

                    <h4 class="text-center mb-4">Add Node</h4>

                    <form:form
                        action="/node/add"
                        method="post"
                        modelAttribute="nodeDto">

                        <div class="mb-3">
                            <label class="form-label">Node Name</label>
                            <form:input path="identifier"
                                        cssClass="form-control"
                                        required="true"/>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Node Path</label>
                            <form:input path="path"
                                        cssClass="form-control"
                                        required="true"/>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Additional Roles</label>
                            <form:select path="roles"
                                         multiple="true"
                                         cssClass="form-control">
                                <form:options items="${roles}"
                                              itemValue="identifier"
                                              itemLabel="identifier"/>
                            </form:select>
                            <small class="text-muted">
                                Hold Ctrl (Windows/Linux) or Cmd (Mac) to select multiple
                            </small>
                        </div>

                        <div class="d-grid mt-4">
                            <button type="submit" class="btn btn-primary">
                                Add Node
                            </button>
                        </div>

                    </form:form>

                </div>

                <div class="card-footer text-muted text-center small">
                    POS Management System
                </div>
            </div>

        </div>
    </div>
</div>

</body>
</html>