// 메인페이지 - 카페 최신순 5개 호출
function getCafeTop5() {
    $('#cafeListContainer').empty();
    $.ajax({
        type: "GET",
        url: "/api/cafes/top5",
        data: {},
        success: function (response) {
            for (const cafeListElement of response) {
                let tempHtml = makeHtml(cafeListElement);
                $("#cafeListContainer").append(tempHtml);
            }
        }
    })
}

function getCafeList() {
    let dataSource = null;
    let searchVal = $("#searchBox input").val();
    let sortVal = $("#sorting option:selected").val();
    let sortStrategyKey = sortVal.split("-")[1];
    let sortStrategyValue = sortVal.split("-")[0];

    dataSource = `/api/cafes?searchVal=${searchVal}&sortStrategyKey=${sortStrategyKey}&sortStrategyValue=${sortStrategyValue}`;

    $('#cafeListContainer').empty();
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
        pageSize: 12,
        showPrevious: true,
        showNext: true,
        ajax: {
            beforeSend: function () {
                $('#cafeListContainer').html('불러오는 중...');
            }
        },
        callback: function (data, pagination) {
            $('#cafeListContainer').empty();
            for (let cafe of data) {
                let tempHtml = makeHtml(cafe);
                $('#cafeListContainer').append(tempHtml);
            }
        }
    });
}

function makeHtml(cafe) {
    const id = cafe["cafeNumber"];
    const cafeName = cafe["cafeName"];
    const cafeInfo = cafe["cafeInfo"];
    const cafeWeekdayPrice = cafe["cafeWeekdayPrice"];
    const cafeImgUrl = cafe["cafeImgUrl"];
    const cafeReviewCount = cafe["cafeReviewCount"]
    const cafeBookmarkCount = cafe["cafeBookmarkCount"]

    return `<div class="card" onclick="location.href='/cafes/${id}/detail'">
                        <div class="card-image">
                            <figure class="image is-4by3">
                                <img src="${cafeImgUrl}" class="card-img-top" alt="Placeholder image">
                            </figure>
                        </div>
                        <div class="card-content" id="card-content">
                            <p id="title" class="title is-4">${cafeName}</p>
                            <p id="subTitle" class="subtitle is-6">${cafeInfo}</p>
                            <div id="card-content-bottom">
                                <div>
                                    <i style="color: lightgray" class="fa-solid fa-comment"></i> ${cafeReviewCount}
                                    <i style="color: lightgray" class="fa-solid fa-bookmark"></i> ${cafeBookmarkCount}
                                </div>
                                <div id="price">
                                    <p id="priceDetail">₩${cafeWeekdayPrice.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',')}</p>
                                </div>
                            </div>
                        </div>
                   </div>`;
}