<#import "/spring.ftl" as spring/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Dev Template: Database</title>

    <script src="<@spring.url '/webjars/jquery/jquery.min.js'/>"></script>
    <script src="<@spring.url '/webjars/bootstrap/js/bootstrap.min.js'/>"></script>
    <link rel="stylesheet" href="<@spring.url '/webjars/bootstrap/css/bootstrap.min.css'/>">
    <script src="<@spring.url '/webjars/bootstrap-datepicker/js/bootstrap-datepicker.min.js'/>"></script>
    <link rel="stylesheet" href="<@spring.url '/webjars/bootstrap-datepicker/css/bootstrap-datepicker.standalone.min.css'/>">
    <#--<link rel="stylesheet" href="<@spring.url '/css/custom.min.css' />">-->
    <script src="<@spring.url '/webjars/bootbox/bootbox.js'/>"></script>

    <style>
        a {
            text-decoration: none ! important;
        }
    </style>


</head>
<body>
<input type="hidden" id="ctxPath" name="ctxPath" value="<@spring.url ''/>" />


<div class="container">
    <div class="bs-component">
        <h3>All members</h3>
    </div>

    <br />
    <div>
        <table class="table table-striped table-hover" width="100%">
            <tr>
                <th>ID</th>
                <th>NAME</th>
                <th>CITY</th>
                <th>STREET</th>
                <th>ZIPCODE</th>
            </tr>
        <#list allMembers as member>
            <tr>
                <td>
                ${member.id!}
                </td>
                <td>
                ${member.name!}
                </td>
                <td>
                ${member.city!}
                </td>
                <td>
                ${member.street!}
                </td>
                <td>
                ${member.zipcode!}
                </td>
            </tr>
        </#list>
        </table>
    </div>

    <hr />
    <div class="bs-component">
        <h4>Find member by id: <input type="text" id="find_mem_id"  onblur="findMemberById($(this));"></h4>
    </div>
    <hr />

    <div class="bs-component">
        <h4>Members who have bigger Id than: <input type="text" id="find_mem_id_from"  onblur="findAllMembersFrom($(this));"></h4>
    </div>
    <div class="bs-component" style="background-color: #33afe9">
        <h4 id="found_member_list"></h4>
    </div>
    <br />
    <div class="bs-component" style="background-color: #d43f3a;color: #FFFFFF; font-weight:bold; padding: 1px;">
        <h5>&nbsp;Check JPA examples from : com.github.devcat24.mvc.svc.JPAService</h5>
    </div>
    <br />


</div>

</body>
</html>

<link rel="stylesheet" href="<@spring.url '/css/bootstrap.bootswatch.readable.css' />">
<script src="<@spring.url '/js/db_template.js' />"></script>
