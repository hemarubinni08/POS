<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
        }

        /* 🎯 CONTAINER (same as add node) */
        .container {
            width: 90%;
            max-width: 1000px;
            margin: 80px auto;
            background: #f1f5f9;
            padding: 35px;
            border-radius: 16px;
            box-shadow: 0 10px 25px rgba(0,0,0,0.1);
        }

        /* 🔷 TITLE */
        h2 {
            text-align: center;
            margin-bottom: 25px;
            font-size: 22px;
            color: #0891b2;
            font-weight: 600;
        }

        /* 📊 TABLE */
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

        /* 🔥 BUTTON BASE */
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

        /* ✏️ EDIT */
        .edit-btn {
            background: linear-gradient(135deg, #0891b2, #0e7490);
        }

        .edit-btn:hover {
            background: linear-gradient(135deg, #0e7490, #075985);
            transform: translateY(-2px);
            box-shadow: 0 6px 15px rgba(8,145,178,0.4);
        }

        /* ❌ DELETE */
        .delete-btn {
            background: linear-gradient(135deg, #ef4444, #dc2626);
        }

        .delete-btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 6px 15px rgba(239,68,68,0.4);
        }

        /* 🚫 EMPTY */
        .empty {
            text-align: center;
            padding: 30px;
            color: #475569;
            font-size: 14px;
        }

        /* 🔽 FOOTER */
        .footer {
            display: flex;
            justify-content: center;
            gap: 15px;
            margin-top: 25px;
        }

        .home-btn {
            background: #64748b;
        }

        .register-btn {
            background: linear-gradient(135deg, #0891b2, #0e7490);
        }

        .register-btn:hover {
            background: linear-gradient(135deg, #0e7490, #075985);
            transform: translateY(-2px);
            box-shadow: 0 6px 15px rgba(8,145,178,0.4);
        }
    </style>
</head>

<body>

<div class="container">

    <h2>User Management</h2>

    <c:if test="${empty users}">
        <div class="empty">
            No users found
        </div>
    </c:if>

    <c:if test="${not empty users}">
        <table>
            <thead>
            <tr>
                <th>Email</th>
                <th>Name</th>
                <th>Phone</th>
                <th>Roles</th>
                <th>Action</th>
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
                        <a href="/user/get?username=${user.username}"
                           class="btn edit-btn">Edit</a>

                        <a href="/user/delete?username=${user.username}"
                           class="btn delete-btn"
                           onclick="return confirm('Are you sure you want to delete this user?');">
                            Delete
                        </a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>

    <div class="footer">
        <a href="/" class="btn home-btn">Home</a>

        <a href="/register" class="btn register-btn">
            Register
        </a>
    </div>

</div>

</body>
</html>