<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>All Users</title>

    <style>
        body {
            font-family: 'Poppins', sans-serif;
            background: #f0f2f5;
            padding: 40px;
        }

        h2 {
            text-align: center;
            margin-bottom: 15px;
        }

        .top-bar {
            width: 90%;
            margin: 0 auto 20px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .back-btn {
            background-color: #6c757d;
            color: #fff;
            padding: 10px 18px;
            border-radius: 6px;
            font-size: 15px;
            font-weight: 600;
            text-decoration: none;
        }

        .back-btn:hover {
            background-color: #5a6268;
        }

        .add-btn {
            background-color: #5563DE;
            color: #fff;
            padding: 10px 18px;
            border-radius: 6px;
            font-size: 15px;
            font-weight: 600;
            text-decoration: none;
        }

        .add-btn:hover {
            background-color: #4450c7;
        }

        table {
            width: 90%;
            margin: auto;
            border-collapse: collapse;
            background: #fff;
            box-shadow: 0 4px 10px rgba(0,0,0,0.1);
        }

        th, td {
            padding: 12px;
            border-bottom: 1px solid #ddd;
            text-align: center;
            vertical-align: middle;
        }

        th {
            background-color: #5563DE;
            color: #fff;
        }

        tr:hover {
            background-color: #f5f7ff;
        }

        .btn {
            padding: 6px 14px;
            text-decoration: none;
            color: #fff;
            border-radius: 4px;
            font-size: 14px;
            font-weight: 500;
        }

        .view-btn {
            background-color: #28a745;
        }

        .delete-btn {
            background-color: #dc3545;
        }

        .action-buttons {
            display: flex;
            justify-content: center;
            gap: 10px;
        }

        .id-link {
            color: #5563DE;
            font-weight: 600;
            text-decoration: none;
        }

        .id-link:hover {
            text-decoration: underline;
        }

        .footer-actions {
            margin-top: 25px;
            display: flex;
            justify-content: center;
            gap: 20px;
        }

        .footer-actions a {
            padding: 10px 20px;
            border-radius: 6px;
            font-weight: 600;
            text-decoration: none;
            color: #fff;
        }

        .home-btn {
            background-color: #6c757d;
        }

        .register-btn {
            background-color: #28a745;
        }
    </style>
</head>

<body>

<h2>Registered Users</h2>

<div class="top-bar">
    <a href="${pageContext.request.contextPath}/register" class="add-btn">
        + Add New User
    </a>
</div>

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
        <c:forEach var="u" items="${users}">
            <tr>
                <td>
                    <a class="id-link"
                       href="${pageContext.request.contextPath}/user/get?username=${u.username}">
                        ${u.username}
                    </a>
                </td>

                <td>${u.name}</td>
                <td>${u.phoneNo}</td>
                <td>${u.roles}</td>

                <td>
                    <div class="action-buttons">
                        <a class="btn view-btn"
                           href="${pageContext.request.contextPath}/user/get?username=${u.username}">
                            Edit
                        </a>

                        <a class="btn delete-btn"
                           href="${pageContext.request.contextPath}/user/delete?username=${u.username}"
                           onclick="return confirm('Are you sure you want to delete this user?');">
                            Delete
                        </a>
                    </div>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>

<div class="footer-actions">
    <a href="${pageContext.request.contextPath}/" class="home-btn">
        Home
    </a>
</div>

</body>
</html>