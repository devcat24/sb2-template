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


    <style>
        a {
            text-decoration: none ! important;
        }
    </style>


</head>
<body>
<input type="hidden" id="ctxPath" name="ctxPath" value="${pageContext.request.contextPath}" />
<input type="hidden" id="keepAlivePing" name="keepAlivePing" value="${keepAlivePing}" />


<div class="container">
    <div class="bs-component">
        <h3>Dev Template</h3>
    </div>
    <hr />

    <p>1. <a href="./basicContents">Directory configuration & Resource bundle</a></p>
    <p>2. <a href="./dbTemplate">Database JPA</a></p>
    <p>3. RestTemplate & Jackson processing
        <i class="fa fa-chevron-left" aria-hidden="true"></i>
            <a href="./rest/resttemplate01">#1</a>,
            <a href="./rest/resttemplate02">#2</a>
        <i class="fa fa-chevron-right" aria-hidden="true"></i></p>
    <p>4. RestTemplate File download : RestTemplateSvc.downloadRemoteFile()</p>
    <p>5. Reflection : <i class="fa fa-chevron-left" aria-hidden="true"></i>
        <a href="./reflectionEx01">TestController.reflectionEx01()</a>,
        <a href="https://commons.apache.org/proper/commons-lang/apidocs/org/apache/commons/lang3/reflect/package-summary.html">Commons Lang <i class="fa fa-arrow-right" aria-hidden="true"></i> Reflect</a>
        <i class="fa fa-chevron-right" aria-hidden="true"></i>
    </p>
    <p>6. File Archive : ArchiveUtils.*</p>
    <p>7. Send Mail : MailNotificationSvc.sendHtmlEmail()</p>
    <p>8. Spring Actuator : <a href="http://localhost:8201/manage/TemplateSvc">TemplateSvcEndpoint.*</a></p>
    <p>9. Security Filter & Development user simulation : TemplateSecurityFilter</p>
    <p>10. AES Password encryption/decription : AESUtil</p>
    <p>11. Keep Alive ping using prevents session timeout : DefaultController.ping()</p>

    <br />
    <p>12. src/main/resources/Readme/*.txt</p>
    <hr />
    <sub>App version: ${applicationVersion}</sub>
</div>
</body>
</html>


<script src="${pageContext.request.contextPath}/js/welcome_jstl.js"></script>
<script src="${pageContext.request.contextPath}/js/util_template.js"></script>