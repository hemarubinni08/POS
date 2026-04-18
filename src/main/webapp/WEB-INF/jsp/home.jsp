<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Home</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
          rel="stylesheet">

    <style>
        body {
            background: linear-gradient(135deg, #667eea, #764ba2);
            min-height: 100vh;
        }
        .card {
            border-radius: 12px;
        }
        .node-btn {
            border-radius: 8px;
            font-weight: 500;
        }
    </style>
</head>

<body>

<!-- Top Bar -->
<div class="container-fluid py-3">
    <div class="d-flex justify-content-between align-items-center">

        <h4 class="text-white mb-0">POS Management System</h4>

        <form action="${pageContext.request.contextPath}/logout" method="post">
            <button type="submit" class="btn btn-outline-light">
                Logout
            </button>
        </form>

    </div>
</div>

<!-- Main Content -->
<div class="container d-flex justify-content-center align-items-center mt-4">
    <div class="col-md-8">

        <div class="card shadow-lg">
            <div class="card-header bg-primary text-white text-center">
                <h5 class="mb-0">Available Modules</h5>
            </div>

            <div class="card-body">

                <c:if test="${empty nodes}">
                    <div class="alert alert-warning text-center">
                        No modules available
                    </div>
                </c:if>

                <div class="row g-3">

                    <c:forEach var="node" items="${nodes}">
                        <div class="col-md-4 d-grid">
                            <a href="${pageContext.request.contextPath}${node.path}"
                               class="btn btn-outline-primary btn-lg node-btn">
                                ${node.identifier}
                            </a>
                        </div>
                    </c:forEach>

                </div>

            </div>

            <div class="card-footer text-center text-muted small">
                © POS Management System
            </div>
        </div>

    </div>
</div>

</body>
</html>