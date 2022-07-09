// 메인페이지 - 카페 최신순 5개 호출
function getCafeTop5() {
    $('#cafeListContainer').empty();
    $.ajax({
        type: "GET",
        url: "/api/cafes/top5",
        data: {},
        success: function(response){
            for (const cafeListElement of response) {
                let tempHtml = makeHtml(cafeListElement);
                $("#cafeListContainer").append(tempHtml);
            }
        }
    })
}

function getCafeList() {
    let dataSource = null;
    // let sorting = $("#sorting option:selected").val();
    // let isAsc = $(':radio[name="isAsc"]:checked').val();
    // console.log(sorting, isAsc);
    // dataSource = `/api/cafes?sortBy=${sorting}&isAsc=${isAsc}}`;
    dataSource = `/api/cafes`;

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
        pageSize: 2,
        showPrevious: true,
        showNext: true,
        ajax: {
            beforeSend: function() {
                $('#cafeListContainer').html('불러오는 중...');
            }
        },
        callback: function(data, pagination) {
            $('#cafeListContainer').empty();
            console.log(data);
            for (let cafe of data) {
                let tempHtml = makeHtml(cafe);
                $('#cafeListContainer').append(tempHtml);
            }
        }
    });
}

function makeHtml(cafe) {
    const id = cafe["id"];
    const cafeName = cafe["cafeName"];
    const cafeInfo = cafe["cafeInfo"];
    const cafeWeekdayPrice = cafe["cafeWeekdayPrice"];
    const cafeImgUrl = cafe["cafeImgUrl"];

    return `<div class="card" onclick="#" style="width: 200px; height: 250px;">
                        <div class="card-image">
                            <figure class="image is-4by3">
                                <img src="${cafeImgUrl}" class="card-img-top" alt="Placeholder image">
                            </figure>
                        </div>
                        <div class="card-content">
                            <div class="media">
                                <div class="media-content">
                                    <p id="title" class="title is-4">${cafeName}</p>
                                    <p class="subtitle is-6">${cafeInfo}</p>
                                    <p>₩${cafeWeekdayPrice.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',')}</p>
                                </div>
                            </div>
                        </div>
                   </div>`;
}