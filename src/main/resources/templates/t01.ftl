<#import "/spring.ftl" as spring/>
<!DOCTYPE html>
<html lang="en">
<head>
</head>
<body>
<input type="hidden" id="ctxPath" name="ctxPath" value="<@spring.url ''/>"/>
<h1>Hi~ t01 : ${.now?string["EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'"]}</h1>
</body>
</html>

