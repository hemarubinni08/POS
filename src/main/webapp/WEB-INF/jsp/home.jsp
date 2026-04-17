<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:forEach var="node" items="${nodes}">
    <a href="${node.path}"> ${node.identifier}</a>
</c:forEach>
