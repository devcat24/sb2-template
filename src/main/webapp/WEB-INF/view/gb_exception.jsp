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
    <div class="container">
        <br /><br />
        <div class="row vertical-center-row">
            <div class="col-lg-12">
                <table>
                    <tr>
                        <td>
                            <span style="font-size:13px;">
                                <strong>We are currently unable to complete this process as we are <br/> experiencing some technical problems</strong>
                            </span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <span style="font-size:13px;">
                                You can go back to the <strong><a href="${pageContext.request.contextPath}/wel">Homepage</a></strong> and try again<br/><br/>
                            </span>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
</body>
</html>