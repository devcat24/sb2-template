var ctxPath = $('#ctxPath').val();

$(document).ready(function(){
    var keepAlivePingString = $('#keepAlivePing').val();
    var keepAlivePing = parseInt( keepAlivePingString.replace(/[^0-9|^.]/g, ''));
    if(keepAlivePing < 1000){
        keepAlivePing = 1000;
    }


    // load contents !



    setInterval(pingToHost, keepAlivePing);
});