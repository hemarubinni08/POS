<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Rack Management</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
          rel="stylesheet">

    <style>
        body {
            background: linear-gradient(135deg, #ede9fe, #ddd6fe);
            min-height: 100vh;
        }

        .table th {
            background-color: #a78bfa;
            color: #ffffff;
        }

        .btn-success {
            background-color: #7c3aed;
            border: none;
        }

        /* ✅ STATUS TOGGLE STYLE */
        .status-toggle {
            position: relative;
            display: inline-block;
            width: 42px;
            height: 22px;
        }

        .status-toggle input {
            opacity: 0;
            width: 0;
            height: 0;
        }

        .status-slider {
            position: absolute;
            cursor: pointer;
            inset: 0;
            background-color: #dc2626;
            transition: 0.3s;
            border-radius: 22px;
        }

        .status-slider:before {
            position: absolute;
            content: "";
            height: 18px;
            width: 18px;
            left: 2px;
            bottom: 2px;
            background-color: white;
            transition: 0.3s;
            border-radius: 50%;
        }

        .status-toggle input:checked + .status-slider {
            background-color: #16a34a;
        }

        .status-toggle input:checked + .status-slider:before {
            transform: translateX(20px);
        }
    </style>
</head>

<body>
<div class="container mt-5">
    <div class="card shadow-lg">

        <div class="card-header text-center">
            <h4>List of Racks</h4>
        </div>

        <div class="card-body">

            <c:choose>
                <c:when test="${empty racks}">
                    <div class="alert alert-warning text-center">
                        No racks available
                    </div>
                </c:when>

                <c:otherwise>
                    <table class="table table-bordered table-hover text-center">
                        <thead>
                        <tr>
                            <th>Rack Name</th>
                            <th>Shelfs</th>
                            <th>Status</th>
                            <th>Actions</th>
                        </tr>
                        </thead>

                        <tbody>
                        <c:forEach var="rack" items="${racks}">
                            <tr>
                                <td>${rack.identifier}</td>

                                <td>
                                    <c:choose>
                                        <c:when test="${not empty rack.shelfs}">
                                            <c:forEach var="s" items="${rack.shelfs}" varStatus="st">
                                                ${s}<c:if test="${!st.last}">, </c:if>
                                            </c:forEach>
                                        </c:when>
                                        <c:otherwise>-</c:otherwise>
                                    </c:choose>
                                </td>

                                <td>
                                    <label class="status-toggle">
                                        <input type="checkbox"
                                               <c:if test="${rack.status}">checked</c:if>
                                               disabled>
                                        <span class="status-slider"></span>
                                    </label>
                                </td>

                                <td>
                                    <a href="${pageContext.request.contextPath}/rack/get?identifier=${rack.identifier}"
                                       class="btn btn-sm btn-primary">Edit</a>

                                    <a href="${pageContext.request.contextPath}/rack/delete?identifier=${rack.identifier}"
                                       class="btn btn-sm btn-danger">Delete</a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:otherwise>
            </c:choose>

        </div>

        <!-- ✅ UPDATED FOOTER (Home button added) -->
        <div class="card-footer text-center">
            <a href="${pageContext.request.contextPath}/"
               class="btn btn-secondary me-2">Home</a>

            <a href="${pageContext.request.contextPath}/rack/add"
               class="btn btn-success">Add Rack</a>
        </div>

    </div>
</div>
</body>
</html>
