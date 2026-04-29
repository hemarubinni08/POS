<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>User Management</title>

    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600&display=swap" rel="stylesheet">

    <style>
        body {
            margin: 0;
            font-family: 'Inter', sans-serif;
            background: #d1d5db;

            min-height: 100vh;

            /* ✅ SAME AS NODE PAGE */
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 20px 0;
        }

        /* ✅ SAME WIDTH AS NODE */
        .container {
            width: 90%;
            max-width: 900px;
            background: #f1f5f9;
            padding: 30px;
            border-radius: 16px;
            box-shadow: 0 10px 25px rgba(0,0,0,0.1);
        }

        h2 {
            text-align: center;
            margin-bottom: 20px;
            font-size: 22px;
            color: #0891b2;
            font-weight: 600;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        th {
            background: #0891b2;
            color: white;
            padding: 12px;
            font-size: 13px;
            text-align: center;
        }

        td {
            padding: 14px 10px;
            border-bottom: 1px solid #e2e8f0;
            font-size: 13px;
            text-align: center;
        }

        tr:hover {
            background: rgba(8,145,178,0.05);
        }

        .btn {
            padding: 6px 14px;
            border-radius: 20px;
            font-size: 12px;
            text-decoration: none;
            font-weight: 600;
            color: white;
            transition: 0.25s;
            display: inline-block;
        }

        /* ✅ SAME AS NODE */
        .edit-btn {
            background: linear-gradient(135deg, #0891b2, #0e7490);
        }

        .edit-btn:hover {
            background: linear-gradient(135deg, #0e7490, #075985);
            transform: translateY(-2px);
            box-shadow: 0 6px 15px rgba(8,145,178,0.4);
        }

        .delete-btn {
            background: linear-gradient(135deg, #ef4444, #dc2626);
        }

        .delete-btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 6px 15px rgba(239,68,68,0.4);
        }

        .empty {
            text-align: center;
            padding: 30px;
            color: #475569;
            font-size: 14px;
        }

        .footer {
            display: flex;
            justify-content: center;
            gap: 15px;
            margin-top: 20px;
        }

        .home-btn {
            background: #64748b;
        }

        .home-btn:hover {
            background: #475569;
        }

        /* ✅ ADD BUTTON LIKE NODE PAGE */
        .add-btn {
            background: linear-gradient(135deg, #0891b2, #0e7490);
        }

        .add-btn:hover {
            background: linear-gradient(135deg, #0e7490, #075985);
            transform: translateY(-2px);
            box-shadow: 0 6px 15px rgba(8,145,178,0.4);
        }
    </style>
</head>

<body>

<div class="container">

    <h2>User Management</h2>

    <c:choose>
        <c:when test="${empty users}">
            <div class="empty">No users found</div>
        </c:when>

        <c:otherwise>
            <table>
                <thead>
                <tr>
                    <th>Email</th>
                    <th>Name</th>
                    <th>Phone</th>
                    <th>Roles</th>
                    <th>Actions</th>
                </tr>
                </thead>

                <tbody>
                <c:forEach var="user" items="${users}">
                    <tr>
                        <td>${user.username}</td>
                        <td>${user.name}</td>
                        <td>${user.phoneNo}</td>
                        <td>${user.roles}</td>

                        <td>
                            <a href="${pageContext.request.contextPath}/user/get?username=${user.username}"
                               class="btn edit-btn">Edit</a>

                            <a href="${pageContext.request.contextPath}/user/delete?username=${user.username}"
                               class="btn delete-btn"
                               onclick="return confirm('Are you sure you want to delete this user?');">
                                Delete
                            </a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>

    <div class="footer">
        <a href="${pageContext.request.contextPath}/"
           class="btn home-btn">Home</a>

        <!-- ✅ NEW BUTTON -->
        <a href="${pageContext.request.contextPath}/user/add"
           class="btn add-btn">Add User</a>
    </div>

</div>

</body>
</html>