let access_token = $.cookie('access_token');
if (access_token!=null){
 $.ajaxSetup({
     beforeSend: function (xhr) {
         xhr.setRequestHeader("Content-type", "application/json");
         xhr.setRequestHeader("Authorization", "Bearer" + access_token);
     }
 });
}