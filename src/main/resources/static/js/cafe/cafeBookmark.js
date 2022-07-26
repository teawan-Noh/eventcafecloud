function showBookmarkStatus(){
    let class_bookmark;
    if(bookmarkByLoginUser === "false") {
        class_bookmark = "fa-regular";
    }else {
        class_bookmark = "fa-solid";
    }
    $('.fa-star').addClass(class_bookmark);
}

function bookmark(){
    if ($('.fa-star').hasClass("fa-regular")){
        $.ajax({
            type: "POST",
            url: `/cafes/${id}/bookmark`,
            data: {},
            success: function (response) {
                $('.fa-star').addClass("fa-solid").removeClass("fa-regular");
            }
        })
    } else {
        $.ajax({
            type: "DELETE",
            url: `/cafes/${id}/bookmark`,
            data: {},
            success: function (response) {
                $('.fa-star').addClass("fa-regular").removeClass("fa-solid");
            }
        })
    }
}