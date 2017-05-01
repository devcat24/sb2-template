<%--suppress HtmlUnknownTarget --%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- @taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" --%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Dev Template</title>

    <script src="${pageContext.request.contextPath}/webjars/jquery/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/webjars/bootstrap/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/webjars/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.bootswatch.readable.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/webjars/font-awesome/css/font-awesome.min.css">
    <script src="${pageContext.request.contextPath}/js/db_template.js"></script>

    <style>
        a {
            text-decoration: none ! important;
        }
    </style>


</head>
<body>
<input type="hidden" id="ctxPath" name="ctxPath" value="${pageContext.request.contextPath}" />


<div class="container">
    <div class="bs-component">
        <h3><%= "Welcome!" %> <sup><%= "from /webapp/WEB-INF/view" %></sup></h3>
    </div>
    <ul>
        <li>Context Path: ${pageContext.request.contextPath}</li>
        <li>Request URI: ${pageContext.request.requestURI}</li>
        <li>HTTP Session: ${pageContext.request.getSession(true)}</li>
        <li>Real Path: ${pageContext.request.getRealPath("/")}</li>
    </ul>
    <br />
    Language : <a href="wel?lang=en_US">English</a> | <a href="wel?lang=ko_KR">Korea</a>
    <h3>
        Message : <spring:message code="hello.test" text="default text" />
    </h3>
    Current Locale : ${pageContext.response.locale}
    <br />
    <hr />
    <%--<p>Click <a href="./welcome_member"> to a member greeting.</a></p>
    <br />
    <hr />--%>

    <p><a href="./dbTemplate">1. Database JPA Template</a></p>
    <p>2. RestTemplate & Jackson processing
        <i class="fa fa-chevron-left" aria-hidden="true"></i>
            <a href="./rest/resttemplate01">#1</a>,
            <a href="./rest/resttemplate02">#2</a>
        <i class="fa fa-chevron-right" aria-hidden="true"></i></p>
    <p>3. RestTemplate File download : RestTemplateSvc.downloadRemoteFile()</p>
    <p>4. Reflection : <i class="fa fa-chevron-left" aria-hidden="true"></i>
        <a href="./reflectionEx01">TestController.reflectionEx01()</a>,
        <a href="https://commons.apache.org/proper/commons-lang/apidocs/org/apache/commons/lang3/reflect/package-summary.html">Commons Lang <i class="fa fa-arrow-right" aria-hidden="true"></i> Reflect</a>
        <i class="fa fa-chevron-right" aria-hidden="true"></i>
    </p>
    <p>5. File Archive : com.github.devcat24.util.archive.ArchiveUtils.*</p>
    <p>6. Send Mail : MailNotificationSvc.sendHtmlEmail()</p>
    <p>7. Spring Actuator : <a href="http://localhost:8201/manage/TemplateSvc">com.github.devcat24.util.actuator.TemplateSvcEndpoint.*</a></p>

    <hr />
    <sub>App version: ${applicationVersion}</sub>
</div>
</body>
</html>