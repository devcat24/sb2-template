<#import "/spring.ftl" as spring/>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="utf-8">
    <title>${title}</title>
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
<h1>${body} : ${.now?string["EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'"]}</h1>
</body>
</html>
