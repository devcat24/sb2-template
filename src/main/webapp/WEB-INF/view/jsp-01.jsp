<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Dev Template</title>

<%--
    <script src="${pageContext.request.contextPath}/webjars/jquery/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/webjars/bootstrap/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/webjars/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.bootswatch.readable.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/webjars/font-awesome/css/font-awesome.min.css">
    <script src="${pageContext.request.contextPath}/js/db_template.js"></script>
--%>
    <script src="${pageContext.request.contextPath}/webjars/jquery/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/webjars/jquery/1.12.3/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/webjars/bootstrap/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/webjars/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/webjars/bootstrap/3.3.6/css/bootstrap.min.css">

    <style>
        a {
            text-decoration: none ! important;
        }
    </style>


</head>
<body>
<div>
    <h3><%= "Welcome!" %> <sup><%= "from /webapp/WEB-INF/view" %></sup></h3>
</div>
<ul>
    <li>Context Path: ${pageContext.request.contextPath}</li>
    <li>Request URI: ${pageContext.request.requestURI}</li>
    <li>HTTP Session: ${pageContext.request.getSession(true)}</li>
    <li>Real Path: ${pageContext.request.getRealPath("/")}</li>
</ul>


</body>
</html>