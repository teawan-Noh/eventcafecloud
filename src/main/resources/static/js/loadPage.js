$(document).ready(function () {
    let first = $(".menuVar > .menuVar-link")[0];
    $(first).addClass('active');

    loadByAjax(first.attributes[1].nodeValue);
});


$(".menuVar > .menuVar-link").on("click", function (e) {
    e.preventDefault();
    $(".nav > .nav-link").removeClass('active');
    $(this).addClass('active');
    let href = this.attributes[1].nodeValue;

    loadByAjax(href);
});


function loadByAjax(href) {
    $.ajax(href).done(function (result) {
        $("#ContentsPlace").html(result);
    });
}