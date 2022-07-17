function addReview() {
    let reviewContent = $("#textarea-review").val()
    let reviewRating = $("input[type=radio][name=rating]:checked").val();
    if (reviewContent === "") {
        alert("후기를 작성해주세요");
        return;
    } else if (reviewRating === undefined) {
        alert("별점을 등록해주세요")
        return;
    }
    $.ajax({
        type: "POST",
        url: `/api/cafes/${id}/review`,
        data: {
            reviewContent: reviewContent,
            reviewRating: reviewRating
        },
        success: function (response) {
            console.log(response);
            getCafeReviewList();
        }
    })
}

function getCafeReviewList() {
    let dataSource = null;
    // 정렬 조건 추가하면 사용 // 카페 list 페이지에서 사용하는 것 참조
    // let sortStrategyKey = $("#sorting option:selected").val();
    let sortStrategyKey = "createdDateDesc";
    dataSource = `/api/cafes/${id}/review?sortStrategyKey=${sortStrategyKey}`;

    $('#review-list-container').empty();
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
        pageSize: 5,
        showPrevious: true,
        showNext: true,
        ajax: {
            beforeSend: function () {
                $('#review-list-container').html('불러오는 중...');
            }
        },
        callback: function (data, pagination) {
            $('#review-list-container').empty();
            for (let review of data) {
                let tempHtml = makeHtmlReview(review);
                let userId = review["userId"];
                let cafeReviewNumber = review["cafeReviewNumber"];
                $('#review-list-container').append(tempHtml);
                if (loginUserId !== userId){
                    $(`.delete${cafeReviewNumber}`).hide();
                }
            }
        }
    });
}

function makeHtmlReview(review) {
    const userId = review["userId"];
    const userNickname = review["userNickname"];
    const userImage = review["userImage"];
    const cafeReviewNumber = review["cafeReviewNumber"];
    const cafeReviewContent = review["cafeReviewContent"];
    const cafeReviewRating = review["cafeReviewRating"];
    const createdDate = review["createdDate"].split("T")[0];

    return `<div class="review-container">
                        <div class="user-img">
                            <img alt="유저 이미지" src="${userImage}" id="reviews-user-img">
                        </div>
                        <div class="review-info">
                            <div class="review-info-top">
                                <div class="review-username" id="reviews-user-nickname">${userNickname}</div>
                                <div class="review-rating">평점 : ${cafeReviewRating}</div>
                            </div>
                            <div class="review-info-middle">${cafeReviewContent}</div>
                            <div class="review-info-bottom">
                                <div class="review-create-date">${createdDate}</div>
                                <div class="form-group">
                                    <button onclick="deleteReview(${cafeReviewNumber})" class="delete${cafeReviewNumber}" id="review-del-btn">삭제</button>
                                </div>
                            </div>
                        </div>
                    </div>`;
}

function deleteReview(cafeReviewNumber) {
    if(confirm("삭제 하시겠습니까?")){
        $.ajax({
            type: "DELETE",
            url: `/api/cafes/review/${cafeReviewNumber}`,
            data: {},
            success: function (response) {
                getCafeReviewList();
            }
        })
    }
}