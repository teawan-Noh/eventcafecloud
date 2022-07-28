function addComment() {
    let eventCmtContent = $("#textarea-box").val()
    if (eventCmtContent === "") {
        alert("댓글을 작성해주세요");
        return;
    }

    if(confirm("댓글을 등록하시겠습니까?")){
        $.ajax({
            type: "POST",
            url: `/api/events/${eventNumber}/comment`,
            data: {
                eventCmtContent: eventCmtContent
            },
            success: function (response) {
                location.reload(true);
                getEventCmtList();
            }
        })
    }

}

function getEventCmtList() {
    let dataSource = null;
    // 정렬 조건 추가하면 사용 // 카페 list 페이지에서 사용하는 것 참조
    // let sortStrategyKey = $("#sorting option:selected").val();
    let sortStrategyKey = "desc";
    let sortStrategyValue = "createdDate"
    dataSource = `/api/events/${eventNumber}/comment?sortStrategyKey=${sortStrategyKey}&sortStrategyValue=${sortStrategyValue}`;

    $('#comment-list-container').empty();
    $('#pagination').pagination({
        dataSource,
        locator: 'content',
        alias: {
            pageNumber: 'page',
            pageSize: 'size'
        },
        totalNumberLocator: (response) => {
            return response.totalElements;
        },
        pageSize: 10,
        showPrevious: true,
        showNext: true,
        ajax: {
            beforeSend: function () {
                $('#comment-list-container').html('불러오는 중...');
            }
        },
        callback: function (data, pagination) {
            $('#comment-list-container').empty();
            for (let comment of data) {
                let tempHtml = makeHtmlComment(comment);
                let userId = comment["userNumber"];
                let eventCmtNumber = comment["eventCmtNumber"];
                $('#comment-list-container').append(tempHtml);
                if (loginUserId !== userId){
                    $(`.delete${eventCmtNumber}`).hide();
                }
                if (loginUserId === userId){
                    $('#event-comments .comment-input-box').hide();
                }
            }
        }
    });
}

function makeHtmlComment(comment) {
    const userId = comment["userId"];
    const userNickname = comment["userNickname"];
    const userImage = comment["userImage"];
    const eventCmtNumber = comment["eventCmtNumber"];
    const eventCmtContent = comment["eventCmtContent"];
    const createdDate = comment["createdDate"].replace("T", " ");

    return `<div class="comment-container">
                        <div class="user-img">
                            <img alt="유저 이미지" src="${userImage}">
                        </div>
                        <div class="comment-info">
                            <div class="comment-info-top">
                                <div class="comment-username" id="comments-user-nickname">${userNickname}</div>
                            </div>
                            <div class="comment-info-middle">${eventCmtContent}</div>
                            <div class="comment-info-bottom">
                                <div class="comment-create-date">${createdDate}</div>
                             </div>  
                        </div>
                        <div class="comment-delete">
                            <div class="form-group del">
                                    <button onclick="deleteComment(${eventCmtNumber})" class="delete${eventCmtNumber}">삭제</button>
                            </div>                        
                        </div>  
            </div>`;
}

function deleteComment(eventCmtNumber) {
    if(confirm("삭제 하시겠습니까?")){
        $.ajax({
            type: "DELETE",
            url: `/api/events/comment/${eventCmtNumber}`,
            data: {},
            success: function (response) {
                getEventCmtList();
                location.reload(true);
            }
        })
    }
}