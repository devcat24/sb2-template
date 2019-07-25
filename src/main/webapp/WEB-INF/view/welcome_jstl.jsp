<%--suppress HtmlUnknownTarget --%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- @taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" --%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Dev Template</title>

    <script src="${pageContext.request.contextPath}/webjars/jquery/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/webjars/bootstrap/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/webjars/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/webjars/font-awesome/css/font-awesome.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.bootswatch.readable.css">

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
        <h3>Dev Template (SB2)</h3>
    </div>
    <hr />

    <p>1. <a href="./basicContents">Directory configuration & Resource bundle</a></p>
    <p>2. <a href="./dbTemplate">Database JPA</a></p>
    <p>3. RestTemplate & Jackson processing
        <i class="fa fa-chevron-left" aria-hidden="true"></i>
        <a href="./rest/resttemplate01">#1</a>,
        <a href="./rest/resttemplate02">#2</a>
        <i class="fa fa-chevron-right" aria-hidden="true"></i></p>
    <p>4. Swagger : <a href="./swagger-ui.html">swagger-ui</a></p>
    <p>5. RestTemplate File download : RestTemplateSvc.downloadRemoteFile()</p>
    <p>6. Reflection : <i class="fa fa-chevron-left" aria-hidden="true"></i>
        ReflectionTest.serviceLoaderTest(),
        <a href="https://commons.apache.org/proper/commons-lang/apidocs/org/apache/commons/lang3/reflect/package-summary.html">Commons Lang <i class="fa fa-arrow-right" aria-hidden="true"></i> Reflect</a>
        <i class="fa fa-chevron-right" aria-hidden="true"></i>
    </p>
    <p>7. File Archive : ArchiveUtils.*</p>
    <p>8. Send Mail : MailNotificationSvc.sendHtmlEmail()</p>
    <p>9. Spring Actuator : <a href="./actuator/health">TemplateSvcEndpoint.*</a></p>
    <p>10. Security Filter & Development user simulation : TemplateSecurityFilter</p>
    <p>11. AES Password encryption/decription : AESUtil</p>
    <p>12. Keep Alive ping using prevents session timeout : DefaultController.ping()</p>
    <p>13. OpenCSV - Java Objec to CSV with header & order : CSVUtils.objToCSV()</p>

    <br />
    <p>14. src/main/resources/Readme/*.txt</p>
    <hr />
    <sub>App version: ${applicationVersion}</sub>
</div>
</body>
</html>

<script src="${pageContext.request.contextPath}/js/welcome_jstl.js"></script>
<script src="${pageContext.request.contextPath}/js/util_template.js"></script>
