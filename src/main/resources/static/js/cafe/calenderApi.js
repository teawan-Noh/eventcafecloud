(function () {
    $(function () {
        // calendar element 취득
        var calendarEl = $('#calendar')[0];
        // full-calendar 생성하기
        var calendar = new FullCalendar.Calendar(calendarEl, {
            height: '500px', // calendar 높이 설정
            expandRows: true, // 화면에 맞게 높이 재설정
            // 해더에 표시할 툴바
            headerToolbar: {
                left: 'prev,next today',
                center: 'title',
                right: 'dayGridMonth'
            },
            initialView: 'dayGridMonth', // 초기 로드 될때 보이는 캘린더 화면(기본 설정: 달)
            nowIndicator: true, // 현재 시간 마크
            dayMaxEvents: true, // 이벤트가 오버되면 높이 제한 (+ 몇 개식으로 표현)
            locale: 'ko', // 한국어 설정
            //이벤트 클릭시 function
            eventClick: function (info){
                const eventId = info.event.id;
                window.open(`/events/${eventId}/detail`)
            },
            // 이벤트 표시
            eventSources: [
                {
                    url: '/api/cafes/calender?id=' + id,
                    color: '#4C7A01',
                    textColor: 'white',
                },
                {
                    url: '/hosts/profile/api/cafes/calender/schedule?id=' + id,
                    color: 'darkorange',
                    textColor: 'white',
                }
            ]
        });
        // 캘린더 랜더링
        calendar.render();
    });
})();