$.datepicker.setDefaults({
    dateFormat: 'yy-mm-dd',
    prevText: '이전 달',
    nextText: '다음 달',
    monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
    monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
    dayNames: ['일', '월', '화', '수', '목', '금', '토'],
    dayNamesShort: ['일', '월', '화', '수', '목', '금', '토'],
    dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'],
    showMonthAfterYear: true,
    yearSuffix: '년',
    beforeShowDay: disableSomeDay
});

// 제외할 날짜 (받아오기) - 휴무일, 예약된 날짜
let disabledDays = dates;
for (let index = 0; index < disabledDays.length; index++) {
    disabledDays[index] = disabledDays[index].replace(/-0+/g, '-')
}

// 이전 날짜들은 선택막기
function noBefore(date){
    if (date < new Date())
        return [false];
    return [true];
}

// 날짜 선택을 막기위한 함수
function disableSomeDay(date) {
    let dates = date.getDate();
    let month = date.getMonth();
    let year = date.getFullYear();

    for (let i = 0; i < disabledDays.length; i++) {
        if($.inArray(year + '-' + (month+1) + '-' + dates, disabledDays) !== -1) {
            return [false];
        }
    }
    return [true];
}

$(function() {
    $('#datePicker-start').datepicker();
    $('#datePicker-start').datepicker("option", "maxDate", $("#datePicker-end").val());
    $('#datePicker-start').datepicker("option", "onClose", function ( selectedDate ) {
        $("#datePicker-end").datepicker( "option", "minDate", selectedDate );
    });

    $('#datePicker-end').datepicker();
    $('#datePicker-end').datepicker("option", "minDate", $("#datePicker-start").val());
    $('#datePicker-end').datepicker("option", "onClose", function ( selectedDate ) {
        $("#datePicker-start").datepicker( "option", "maxDate", selectedDate );
    });
});