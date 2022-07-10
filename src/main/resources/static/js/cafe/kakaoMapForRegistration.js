let mapContainer = document.getElementById('map'), // 지도를 표시할 div
    mapOption = {
        center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
        level: 3 // 지도의 확대 레벨
    };

// 지도를 생성합니다
const map = new kakao.maps.Map(mapContainer, mapOption);

// 주소-좌표 변환 객체를 생성합니다
const geocoder = new kakao.maps.services.Geocoder();
//마커를 미리 생성
let marker = new daum.maps.Marker({
    position: new daum.maps.LatLng(37.537187, 127.005476),
    map: map
});

let zonecode;
let addr;
let x;
let y;

function getAddressInfo(){
    $('#zonecode').val(zonecode);
    $('#address').val(addr);
    $('#cafeY').val(y);
    $('#cafeX').val(x);
}

function execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            // console.log(data);
            addr = data.address; // 최종 주소 변수
            zonecode = data.zonecode;
            // console.log(zonecode);
            // 주소 정보를 해당 필드에 넣는다.
            document.getElementById("modal-address").value = addr;
            //지도 보이기
            $('#map').show();
            // 주소로 상세 정보를 검색
            geocoder.addressSearch(data.address, function(results, status) {

                // 정상적으로 검색이 완료됐으면
                if (status === daum.maps.services.Status.OK) {
                    let result = results[0]; //첫번째 결과의 값을 활용

                    // 해당 주소에 대한 좌표를 받아서
                    let coords = new daum.maps.LatLng(result.y, result.x);
                    x = result.x;
                    y = result.y;
                    // 지도를 보여준다.
                    mapContainer.style.display = "block";
                    map.relayout();
                    // 지도 중심을 변경한다.
                    map.setCenter(coords);
                    // 마커를 결과값으로 받은 위치로 옮긴다.
                    marker.setPosition(coords)
                }
            });
        }
    }).open();
}
