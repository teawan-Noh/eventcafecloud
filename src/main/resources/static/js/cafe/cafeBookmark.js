function showBookmarkStatus(){
    let class_bookmark;
    if(bookmarkByLoginUser === "false") {
        class_bookmark = "fa-regular";
    }else {
        class_bookmark = "fa-solid";
    }
    $('.fa-bookmark').addClass(class_bookmark);
}

function bookmark(){
    if ($('.fa-bookmark').hasClass("fa-regular")){
        $.ajax({
            type: "POST",
            url: `/cafes/${id}/bookmark`,
            data: {},
            success: function (response) {
                $('.fa-bookmark').addClass("fa-solid").removeClass("fa-regular");
            }
        })
    } else {
        $.ajax({
            type: "DELETE",
            url: `/cafes/${id}/bookmark`,
            data: {},
            success: function (response) {
                $('.fa-bookmark').addClass("fa-regular").removeClass("fa-solid");
            }
        })
    }
}