// 메인페이지 - 이벤트 최신순 5개 호출
function getEventTop5() {
    $('#eventListContainer').empty();
    $.ajax({
        type: "GET",
        url: "/api/events/top5",
        data: {},
        success: function(response){
            for (const eventListElement of response) {
                let tempHtml = createHtml(eventListElement);
                $("#eventListContainer").append(tempHtml);
            }
        }
    })
}

function createHtml(event) {
    const eventNumber = event["eventNumber"];
    const eventImageUrls = event["eventImageUrls"]
    const eventName = event["eventName"];
    const eventCategory = event["eventCategory"];
    const eventStartDate = event["eventStartDate"];
    const eventEndDate = event["eventEndDate"];
    const eventCmtCount = event["eventCmtCount"]
    const eventBookmarkCount = event["eventBookmarkCount"]

    return `<div class="card" id="card-event-top5" onclick="location.href='/events/${eventNumber}/detail'">
                            <div class="card-image">
                                <figure class="image is-4by3">
                                    <img src="${eventImageUrls[0]}" class="card-img-top" alt="Placeholder image">
                                </figure>
                            </div>
                            <div class="card-content" id="card-content">
                                <h5><span class="badge badge-secondary" id="badge-cate" >${eventCategory}</span></h5>
                                <p id="title-event-top5" class="title is-4">${eventName}</p>
                                <p id="period-event-top5" class="title is-4">${eventStartDate} ~ ${eventEndDate}</p>
                                <div id="cmt-count">
                                    <i class="fa-regular fa-comment"></i> ${eventCmtCount}
                                    <i class="fa-regular fa-bookmark"></i> ${eventBookmarkCount}
                                </div>
                            </div>
                       </div>`;
}