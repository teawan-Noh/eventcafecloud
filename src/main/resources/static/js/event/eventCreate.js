let results = [];
let weekends = 0;

function startCal() {
    results = [];
    weekends = 0;
    getDatesStartToLast();
}

function getDatesStartToLast() {
    let startDate = document.getElementById('datePicker-start').value;
    let lastDate = document.getElementById('datePicker-end').value;
    let curDate = new Date(startDate);
    while (curDate <= new Date(lastDate)) {
        results.push(curDate.toISOString().split("T")[0]);
        curDate.setDate(curDate.getDate() + 1);
    }

    for (let index = 0; index < results.length; index++) {
        results[index] = results[index].replace(/-0+/g, '-')
    }

    let number = 0;
    for (let i = 0; i < results.length; i++) {
        if (dates.includes(results[i])) {
            number += 1;
        }
    }

    // 휴무일 및 예약된 날짜 포함 기간 설정 시
    if (number === 0) {
        findDate(results);
    } else {
        alert("휴무일 및 예약된 날짜가 포함되어있습니다!");
        document.getElementById('datePicker-start').value = null;
        document.getElementById('datePicker-end').value = null;
        $('#datePicker-start').focus();
        results = [];

    }
}

function findDate(results) {
    let week = ['일', '월', '화', '수', '목', '금', '토'];
    for (result of results) {
        let dayOfWeek = week[new Date(result).getDay()];
        if (dayOfWeek === week[0] || dayOfWeek === week[6]) {
            weekends += 1;     // 주말 날짜의 수만큼 증가
        }
    }

    $('#eventPeriod').text("  총 " +results.length+ " 일 : 평일 " + (results.length - weekends) + "일 + 주말 " + weekends + "일");
    getParams();
}

function getParams() {
    const curUrl = window.location.href;
    const url = new URL(curUrl);

    const urlParams = url.searchParams;

    let weekdayPrice = urlParams.get('weekdayPrice');
    let weekendPrice = urlParams.get('weekendPrice');

    $('#eventPrice').val(eventPrice(weekdayPrice, weekendPrice));
}

function eventPrice(weekdayPrice, weekendPrice) {
    let totalPrice = 0;
    let totalWeek = (results.length - weekends) * weekdayPrice;
    let totalWeekend = weekends * weekendPrice;
    totalPrice = totalWeek + totalWeekend;

    $('#eventTotalPrice').text("총 결제 금액 : " + totalWeek.toLocaleString() + "원 + " + totalWeekend.toLocaleString() + "원");
    return totalPrice.toString();
}