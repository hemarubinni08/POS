<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Node | Retail Core</title>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <style>
        :root {
            --accent-blue: #3b82f6;
            --bg-main: #f9fafb;
            --text-dark: #111827;
            --text-muted: #6b7280;
            --border-color: #e5e7eb;
        }

        body {
            margin: 0;
            font-family: 'Inter', sans-serif;
            background-color: var(--bg-main);
            color: var(--text-dark);
            display: flex;
            flex-direction: column;
            min-height: 100vh;
        }

        .top-navbar {
            background: white;
            height: 70px;
            display: flex;
            align-items: center;
            justify-content: space-between;
            padding: 0 40px;
            border-bottom: 1px solid var(--border-color);
        }

        .btn-home {
            display: flex;
            align-items: center;
            gap: 8px;
            padding: 8px 16px;
            border-radius: 8px;
            background-color: white;
            border: 1px solid var(--border-color);
            color: var(--text-dark);
            text-decoration: none;
            font-weight: 600;
            font-size: 13px;
        }

        .main-content {
            flex-grow: 1;
            padding: 60px 20px;
            display: flex;
            justify-content: center;
            align-items: flex-start;
        }

        .form-card {
            width: 100%;
            max-width: 480px;
            background: white;
            padding: 40px;
            border-radius: 12px;
            border: 1px solid var(--border-color);
            box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1);
        }

        .form-label-custom {
            font-size: 11px;
            font-weight: 700;
            text-transform: uppercase;
            color: var(--text-muted);
            margin-bottom: 8px;
            display: block;
            letter-spacing: 0.05em;
        }

        .form-control-custom {
            border: 1.5px solid var(--border-color);
            border-radius: 8px;
            padding: 12px 15px;
            font-size: 14px;
            width: 100%;
            transition: border-color 0.2s;
        }

        .btn-submit {
            padding: 12px 24px;
            background: var(--text-dark);
            color: white;
            border: none;
            border-radius: 8px;
            font-weight: 600;
        }

        .btn-cancel {
            padding: 12px 24px;
            background: transparent;
            color: var(--text-muted);
            border: 1.5px solid var(--border-color);
            border-radius: 8px;
            font-weight: 600;
            text-decoration: none;
            font-size: 14px;
        }
    </style>
</head>
<body>

    <header class="top-navbar">
        <div style="font-weight: 800; letter-spacing: -0.5px;">
            <span style="color: var(--accent-blue);">■</span> RETAIL CORE
        </div>
        <a href="../list" class="btn-home">
            <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="m15 18-6-6 6-6"/></svg>
            Back to Registry
        </a>
    </header>

    <main class="main-content">
        <div class="form-card">
            <h4 class="mb-4 font-weight-bold">Edit System Node</h4>

            <c:if test="${param.error eq 'true'}">
                <div class="alert alert-danger border-0 small py-2 mb-4">
                    Update failed: The name or path is already in use by another node.
                </div>
            </c:if>

            <c:if test="${empty node}">
                <div class="alert alert-danger border-0 small py-2">
                    System Error: Node data not found.
                </div>
            </c:if>

            <c:if test="${not empty node}">
                <form:form action="update" method="post" modelAttribute="nodeDto">
                    <form:hidden path="id" value="${node.id}"/>

                    <div class="mb-3">
                        <label class="form-label-custom">Node Name (Display)</label>
                        <form:input path="identifier"
                                    cssClass="form-control-custom"
                                    value="${node.identifier}"
                                    required="true"/>
                    </div>

                    <div class="mb-4">
                        <label class="form-label-custom">Navigation Path</label>
                        <form:input path="path"
                                    cssClass="form-control-custom"
                                    value="${node.path}"
                                    required="true"/>
                    </div>

                    <div class="d-flex justify-content-between align-items-center">
                        <a href="../list" class="btn-cancel">Cancel</a>
                        <button type="submit" class="btn-submit">Update Node</button>
                    </div>
                </form:form>
            </c:if>
        </div>
    </main>

</body>
</html>