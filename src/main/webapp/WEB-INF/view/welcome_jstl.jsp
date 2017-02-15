<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- @ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" --%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Dev Template</title>
    </head>
    <body>
        <%= "Welcome!" %> <sub><%= "from /webapp/WEB-INF/view" %></sub>
        <ul>
            <li>Context Path: ${pageContext.request.contextPath}</li>
            <li>Request URI: ${pageContext.request.requestURI}</li>
            <li>HTTP Session: ${pageContext.request.getSession(true)}</li>
            <li>Real Path: ${pageContext.request.getRealPath("/")}</li>
        </ul>


    </body>
</html>