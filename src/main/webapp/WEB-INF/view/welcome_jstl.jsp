<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- @taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" --%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
        <hr />
        Language : <a href="wel?lang=en_US">English</a> | <a href="wel?lang=ko_KR">Korea</a>
        <h3>
            Message : <spring:message code="hello.test" text="default text" />
        </h3>
        Current Locale : ${pageContext.response.locale}

    </body>
</html>