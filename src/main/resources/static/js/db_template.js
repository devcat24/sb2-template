////////////////////////////////////////////////////////////////
//                     Common Attributes                      //
////////////////////////////////////////////////////////////////
var ctxPath = $('#ctxPath').val();

////////////////////////////////////////////////////////////////
//                       DB Template                          //
////////////////////////////////////////////////////////////////


var findMemberIdMsg_TypeID='Type Member ID';
var noMatchingMemberFoundMsg = 'No matching member found !';

/*
 * DB_Template: Find member by id
 */
var findMemberByIdXhr;
if(findMemberByIdXhr && findMemberByIdXhr.readyState !== 4){
    findMemberByIdXhr.abort();
}
var findMemberById = function(id_element) {
    //var memberId = $('#find_mem_id');
    var memberId = id_element;
    if(memberId.val() === ''){
        bootbox.alert(findMemberIdMsg_TypeID);
        return;
    }
    findMemberByIdXhr = $.ajax({
        "url" : ctxPath + "/findMemberById",
        "type" : "POST",
        "data" : {
            id: memberId.val()
        },
        "dataType" : "json"
    }).done(function (data) {
        //noinspection EqualityComparisonWithCoercionJS
        if(data.name != undefined || data.name != null){
            bootbox.alert (data.name);
        }   else {
            bootbox.alert (noMatchingMemberFoundMsg);
        }
    }).fail(function () {
    });
};




/*
 * DB_Template: Find member by id
 */
var findAllMembersFromXhr;
if(findAllMembersFromXhr && findAllMembersFromXhr.readyState !== 4){
    findAllMembersFromXhr.abort();
}
var findAllMembersFrom = function(id_element) {
    var memberId = id_element;
    if(memberId.val() === ''){
        bootbox.alert(findMemberIdMsg_TypeID);
        return;
    }
    var memberListBox = $('#found_member_list');

    findAllMembersFromXhr = $.ajax({
        "url" : ctxPath + "/findAllMembersFrom",
        "type" : "POST",
        "data" : {
            id: memberId.val()
        },
        "dataType" : "json"
    }).done(function (data) {

        if(data.length > 0){
            var found_names = '';
            $.each(data, function(idx){
                found_names = found_names + " "  + data[idx].name;
            });
            memberListBox.text(found_names);
        }   else {
            memberListBox.text(noMatchingMemberFoundMsg);
        }

    }).fail(function () {
    });
};





