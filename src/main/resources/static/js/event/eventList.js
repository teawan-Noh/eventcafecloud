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
    const id = event["id"];
    const eventImageUrls = event["eventImageUrls"]
    const eventName = event["eventName"];
    const eventCategory = event["eventCategory"];
    const eventStartDate = event["eventStartDate"];
    const eventEndDate = event["eventEndDate"];

    return `<div class="card" onclick="#" style="width: 200px; height: 250px;">
                            <div class="card-image">
                                <figure class="image is-4by3">
                                    <img src="${eventImageUrls[0]}" class="card-img-top" alt="Placeholder image">
                                </figure>
                            </div>
                            <div class="card-content">
                                <div class="media">
                                    <div class="media-content">
                                        <h5><span class="badge badge-secondary">${eventCategory}</span></h5>
                                        <p id="title" class="title is-4">${eventName}</p>
                                        <div class="date-start">
                                            <p id="event-start-date">${eventStartDate}</p>
                                        </div>
                                        <div class="date-end">
                                            <p id="event-end-date"> ~ ${eventEndDate}</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                       </div>`;
}