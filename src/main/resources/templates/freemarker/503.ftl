<#-- @ftlvariable name="trace" type="java.lang.String" -->
<#-- @ftlvariable name="exception" type="java.lang.Exception" -->
<#-- @ftlvariable name="message" type="java.lang.String" -->
<#-- @ftlvariable name="status" type="java.lang.String" -->
<#-- @ftlvariable name="timestamp" type="java.util.Date" -->
<#-- @ftlvariable name="error" type="java.lang.String" -->
<#import "/spring.ftl" as spring/>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="utf-8">
    <title>Freemarker template Error page</title>
    <style>
        table td {
            vertical-align: top;
            border: solid 1px #888;
            padding: 10px;
        }
    </style>
</head>
<body>
<input type="hidden" id="ctxPath" name="ctxPath" value="<@spring.url ''/>" />
<h1>Default 5xx Error Page</h1>
    <table>
        <tr>
            <td>Date</td>
            <td>${timestamp?string["EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'"]}</td>
        </tr>
        <tr>
            <td>Error</td>
            <td>${error}</td>
        </tr>
        <tr>
            <td>Status</td>
            <td>${status}</td>
        </tr>
        <tr>
            <td>Message</td>
            <td>${message}</td>
        </tr>
        <tr>
            <td>Exception</td>
            <td>${exception}</td>
        </tr>
        <tr>
            <td>Trace</td>
            <td>
                <pre>${trace}</pre>
            </td>
        </tr>
    </table>
</body>
</html>