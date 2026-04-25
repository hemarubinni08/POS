<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Edit Rack</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        :root {
            --primary:#2563eb; --primary-hover:#1e40af;
            --bg:#f8fafc; --glass:rgba(255,255,255,0.75);
            --text:#0f172a; --muted:#64748b; --border:#e2e8f0;
            --radius:16px; --shadow:0 20px 40px rgba(2,6,23,.08);
        }
        *{font-family:'Inter',sans-serif;box-sizing:border-box}
        body{background:var(--bg);min-height:100vh;display:flex;align-items:center;justify-content:center}
        .back-arrow{position:absolute;top:20px;left:20px;width:42px;height:42px;border-radius:50%;
            background:var(--glass);display:flex;align-items:center;justify-content:center;text-decoration:none;color:var(--text)}
        .card{width:520px;padding:28px;border-radius:var(--radius);background:var(--glass);box-shadow:var(--shadow)}
        label{font-size:13px;margin-top:12px;display:block;color:var(--muted)}
        .form-control{margin-top:6px;border-radius:10px}
        .btn-update{margin-top:20px;width:100%;background:var(--primary);color:#fff;border:none;border-radius:10px;padding:11px;font-weight:600}
    </style>
</head>

<body>

<a href="${pageContext.request.contextPath}/rack/list" class="back-arrow">←</a>

<div class="card">

    <h5 class="text-center">Edit Rack</h5>

    <form action="${pageContext.request.contextPath}/rack/update" method="post">

        <input type="hidden" name="id" value="${rack.id}"/>

        <label>Rack Name</label>
        <input type="text" name="identifier" value="${rack.identifier}" class="form-control" required>

        <label>Assign Shelves</label>
        <select name="shelfIdentifiers" class="form-control" multiple required>
            <c:forEach items="${shelves}" var="shelf">
                <option value="${shelf.identifier}"
                    <c:if test="${rack.shelfIdentifiers.contains(shelf.identifier)}">selected</c:if>>
                    ${shelf.identifier}
                </option>
            </c:forEach>
        </select>

        <button type="submit" class="btn-update">Update Rack</button>
    </form>

</div>
</body>
</html>