function deleteCafe(cafeId){
    if(confirm("정말 삭제하시겠습니까?")){
        $.ajax({
            type: "DELETE",
            url: `/api/cafes/${cafeId}`,
            data: {},
            success: function(response){
                alert(response)
                location.reload(true);
            }
        })
    }
}