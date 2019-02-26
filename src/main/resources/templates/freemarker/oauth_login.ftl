<#import "/spring.ftl" as spring/>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="utf-8">
    <title>OAuth2 Login Page</title>
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


<#list urls?keys as urlKey>
    <a href="${urls[urlKey]}">${urlKey}</a>
</#list>
</body>
</html>
