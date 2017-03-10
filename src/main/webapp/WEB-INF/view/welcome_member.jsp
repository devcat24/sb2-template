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
        <h1>Hello New </h1>
        <h3>
            Message : <spring:message code="hello.test" text="default text" />
        </h3>

    </body>
</html>