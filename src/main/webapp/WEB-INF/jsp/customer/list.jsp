<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Customer Management</title>

    <style>
        body {
            margin: 0;
            min-height: 100vh;
            font-family: "Segoe UI", Inter, Arial, sans-serif;
            background: linear-gradient(135deg, #ede9fe, #ddd6fe);
        }

        /* ===== TOP BAR (Purple Theme) ===== */
        .topbar {
            height: 56px;
            background-color: #4c1d95;
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 0 20px;
            box-shadow: 0 4px 12px rgba(76,29,149,0.25);
        }

        .topbar-left {
            display: flex;
            align-items: center;
            gap: 14px;
        }

        .top-title {
            color: #ede9fe;
            font-weight: 700;
        }

        .home-btn {
            padding: 7px 16px;
            background-color: #7c3aed;
            color: #ffffff;
            text-decoration: none;
            border-radius: 6px;
            font-weight: 600;
        }

        .home-btn:hover {
            background-color: #6d28d9;
        }

        .logout-btn {
            background: #dc2626;
            color: white;
            border: none;
            padding: 7px 16px;
            border-radius: 6px;
            font-weight: 600;
        }

        .page-title {
            text-align: center;
            padding: 22px 0 14px;
            font-size: 28px;
            font-weight: 700;
            color: #4c1d95;
        }

        /* ===== MAIN CARD ===== */
        .container {
            width: 95%;
            max-width: 1200px;
            margin: auto;
            background: #ffffff;
            padding: 26px;
            border-radius: 15px;
            box-shadow: 0 20px 40px rgba(76,29,149,0.18);
        }

        .list-actions {
            display: flex;
            justify-content: flex-end;
            margin-bottom: 18px;
        }

        .add-btn {
            background: #7c3aed;
            color: white;
            padding: 9px 18px;
            border-radius: 6px;
            font-weight: 600;
            text-decoration: none;
        }

        .add-btn:hover {
            background: #6d28d9;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        th {
            background-color: #a78bfa;
            padding: 14px;
            text-transform: uppercase;
            font-size: 13px;
            text-align: center;
            color: #ffffff;
        }

        td {
            padding: 14px;
            text-align: center;
            border-bottom: 1px solid #ddd6fe;
        }

        /* ===== STATUS TOGGLE (UNCHANGED LOGIC) ===== */
        .status-toggle {
            padding: 6px 14px;
            border-radius: 999px;
            color: white;
            font-weight: 600;
            border: none;
            cursor: pointer;
        }

        .status-true { background: #16a34a; }
        .status-false { background: #dc2626; }
    </style>
</head>

<body>

<div class="topbar">
    <div class="topbar-left">
        <div class="top-title">POS Application</div>
        <a class="home-btn" href="${pageContext.request.contextPath}/">Home</a>
    </div>
    <button class="logout-btn">Logout</button>
</div>

<div class="page-title">Customer Management</div>

<div class="container">

    <div class="list-actions">
        <a class="add-btn" href="${pageContext.request.contextPath}/customer/add">
            Add Customer
        </a>
    </div>

    <table>
        <thead>
        <tr>
            <th>Phone</th>
            <th>Name</th>
            <th>Party Type</th>
            <th>Credit</th>
            <th>Credit Type</th>
            <th>Status</th>
            <th>Action</th>
        </tr>
        </thead>

        <tbody>
        <c:forEach var="c" items="${customers}">
            <tr>
                <td>${c.identifier}</td>
                <td>${c.customerName}</td>
                <td>${c.partyType}</td>
                <td>${c.credit}</td>
                <td>${c.creditType}</td>

                <td>
                    <button class="status-toggle ${c.status ? 'status-true' : 'status-false'}"
                            onclick="toggleStatus('${c.identifier}')">
                        ${c.status ? 'Active' : 'Inactive'}
                    </button>
                </td>

                <td>
                    <a class="action-link edit"
                       href="${pageContext.request.contextPath}/customer/get?identifier=${c.identifier}">
                        Edit
                    </a>

                    <a class="action-link delete"
                       href="${pageContext.request.contextPath}/customer/delete?identifier=${c.identifier}"
                       onclick="return confirm('Are you sure you want to delete this customer?');">
                        Delete
                    </a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<script>
function toggleStatus(identifier) {
    fetch('${pageContext.request.contextPath}/customer/toggle-status?identifier=' + identifier, {
        method: 'POST'
    }).then(() => location.reload());
}
</script>

</body>
</html>