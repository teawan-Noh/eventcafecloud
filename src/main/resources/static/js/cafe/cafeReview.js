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

    if (confirm("리뷰를 등록하시겠습니까?")) {
        $.ajax({
            type: "POST",
            url: `/api/cafes/${id}/review`,
            data: {
                reviewContent: reviewContent,
                reviewRating: reviewRating
            },
            success: function (response) {
                if (response === 500) {
                    alert("잘못된 요청입니다")
                }
                location.reload(true);
                getCafeReviewList();
            }
        })
    }

}

function getCafeReviewList() {
    let dataSource;
    // 정렬 조건 추가하면 사용 // 카페 list 페이지에서 사용하는 것 참조
    // let sortStrategyKey = $("#sorting option:selected").val();
    let sortStrategyKey = "desc";
    let sortStrategyValue = "createdDate"
    dataSource = `/api/cafes/${id}/review?sortStrategyKey=${sortStrategyKey}&sortStrategyValue=${sortStrategyValue}`;

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
                // 별표시
                rateIt('#review' + review["cafeReviewNumber"], review["cafeReviewRating"]);
                if (loginUserId !== userId) {
                    $(`.delete${cafeReviewNumber}`).hide();
                }
                if (loginUserId === userId) {
                    $('#reviews .review-input-box').hide();
                    $('#reviews .star-box').hide();
                }
            }
        }
    });
}

function rateIt(target, rating) {
    var ratings = {RatingScore: rating}
    var totalRating = 5;
    var table = document.querySelector(target);

    for (rating in ratings) {
        ratingPercentage = ratings[rating] / totalRating * 100;
        ratingRounded = Math.round(ratingPercentage / 10) * 10 + '%';
        star = table.querySelector(`.${rating} .inner-star`);
        star.style.width = ratingRounded;
    }
}

function makeHtmlReview(review) {
    const userId = review["userId"];
    const userNickname = review["userNickname"];
    const userImage = review["userImage"];
    const cafeReviewNumber = review["cafeReviewNumber"];
    const cafeReviewContent = review["cafeReviewContent"];
    const cafeReviewRating = review["cafeReviewRating"];
    const createdDate = review["createdDate"].replace("T", " ");

    return `<div class="review-container">
                        <div class="user-img">
                            <img alt="유저 이미지" src="${userImage}">
                        </div>
                        <div class="review-info">
                            <div class="review-info-top">
                                <div class="review-username" id="reviews-user-nickname">${userNickname}</div>
                                <div class='RatingStar' id="review${cafeReviewNumber}" rating="${cafeReviewRating}">
                                <div class='RatingScore'>
                                    <div class='outer-star'><div class='inner-star'></div></div>
                                </div>
                            </div>
                            </div>  
                            <div class="review-info-middle">${cafeReviewContent}</div>
                            <div class="review-info-bottom">
                                <div class="review-create-date">${createdDate}</div>
                                <div class="form-group del">
                                    <button onclick="deleteReview(${cafeReviewNumber})" class="delete${cafeReviewNumber}">삭제</button>
                                </div>
                            </div>
                        </div>
                    </div>`;
}

function deleteReview(cafeReviewNumber) {
    if (confirm("삭제 하시겠습니까?")) {
        $.ajax({
            type: "DELETE",
            url: `/api/cafes/review/${cafeReviewNumber}`,
            data: {},
            success: function (response) {
                getCafeReviewList();
                location.reload(true);
            }
        })
    }
}