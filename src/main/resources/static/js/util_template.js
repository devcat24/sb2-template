


/**
 * request ping to timesheet server (avoid session timeout)
 *
 */
var pingXhr;
if(pingXhr && pingXhr.readyState != 4){
    pingXhr.abort();
}

var pingToHost = function (){
    /*var now = new Date();
     var sec = now.getSeconds();
     console.log(sec);*/


    pingXhr = $.ajax({
        "url" : ctxPath + "/ping",
        "type" : "POST",
        "data" : {  },
        "dataType" : "json"
    }).done(function (data) {
         //noinspection JSUnresolvedVariable
         console.log(data.status + '  [ip: ' + data.ip + ' ]   [now: ' + data.now +']');
    }).fail(function () {
        /*console.log('ping error');*/
    });
};
