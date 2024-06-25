document.addEventListener('DOMContentLoaded', (event) => {
    busSearch(); // 초기 버스노선 정보 표시
});

const busDetail = document.querySelector('.bus-detail');
let tbodyTag = document.querySelector('#bus1');
let csvTbodyTag = document.querySelector('#csvBusData');

function busSearch() {
    let html1 = "";
    let search = document.querySelector('#search');
    let strSrch = search.value;

    let apiUrl = `/api/busList?strSrch=${strSrch}`;

    fetch(apiUrl)
        .then(response => response.json())
        .then(function (msg) {
            let jsonRs = JSON.parse(msg.rs);

            jsonRs.msgBody.itemList.forEach(el => {
                html1 += "<tr>";
                html1 += `
                          <td>${el.busRouteNm}</td>
                          <td>${el.routeType}</td>
                          <td>${el.edStationNm}</td>
                          <td>${el.stStationNm}</td>
                          <td>${el.firstBusTm}</td>
                          <td>${el.lastBusTm}</td>
                          <td>${el.term}</td>
                          <td onclick='stationPost("${el.busRouteId}")' style="background-color:; cursor: pointer;">${el.busRouteId}</td>
                          <td>${el.routeType}</td>
                        `;
                html1 += "</tr>";
            });
            tbodyTag.innerHTML = html1;

            //위치표시
            positionFn(jsonRs.msgBody.itemList);
        });
}




//CSV 버스 데이터 검색함수
function csvBusSearch() {
    let html1 = "";
    let search = document.querySelector('#search');
    let routeId = search.value;

    let apiUrl = `/api/csvBusData?routeId=${routeId}`;

    fetch(apiUrl)
        .then(response => response.json())
        .then(function (data) {
            data.forEach(el => {
                html1 += "<tr>";
                html1 += `
                    <td>${el[0]}</td>
                    <td>${el[1]}</td>
                    <td>${el[2]}</td>
                    <td>${el[3]}</td>
                    <td>${el[4]}</td>
                `;
                html1 += "</tr>";
            });
            csvTbodyTag.innerHTML = html1;
        });
}
//정류장 클릭시 정류장 정보를 표시하는 함수
const stationNm = document.querySelector('.bus-station');

function stationPost(busRouteId) {
    let html1 = "";

    let apiUrl = `/api/busStationPost?busRouteId=${busRouteId}`;

    fetch(apiUrl)
        .then(response => response.json())
        .then(function (msg) {
            let jsonRs = JSON.parse(msg.rs);

            jsonRs.msgBody.itemList.forEach(el => {
                html1 += `<div>${el.stationNm}</div>`;
            });
            stationNm.innerHTML = html1;

            // 위치 표시
            positionFn(jsonRs.msgBody.itemList);
        });
}

(() => {
    busSearch();
})();

//맵에 버스 노선 및 정류장 위치를 표시하는 함수
function positionFn(dataVal) {
    let positions = [];
    dataVal.forEach((el) => {
        let latlng = new kakao.maps.LatLng(parseFloat(el.gpsY), parseFloat(el.gpsX));
        positions.push(latlng);
    });

    let mapContainer = document.getElementById('map');
    let mapOption = {
        center: new kakao.maps.LatLng(dataVal[Math.floor(positions.length / 2)].gpsY, dataVal[Math.floor(positions.length / 2)].gpsX),
        level: 8
    };

    let map = new kakao.maps.Map(mapContainer, mapOption);


   let markerImage = new kakao.maps.MarkerImage(
       '/images/marker.png', // 이미지 경로 설정
       new kakao.maps.Size(30, 50), // 마커 이미지의 실제 픽셀 크기 설정
       {
           spriteSize: new kakao.maps.Size(30, 50), // 스프라이트 크기 설정 (실제 이미지 크기와 동일하게 설정)
           spriteOrigin: new kakao.maps.Point(0, 0), // 스프라이트 원점 설정
           offset: new kakao.maps.Point(15, 25) // 마커 이미지의 기준점 설정 (이미지 중앙 하단에 맞춤)
       }
   );


    // 버스 노선을 표시할 선의 스타일 설정
    let busPath = new kakao.maps.Polyline({
        path: positions,
        strokeWeight: 5,            // 선의 두께
        strokeColor: '#FF00FF',     // 핑크색
        strokeOpacity: 1,           // 선의 불투명도
        strokeStyle: 'solid'        // 선의 스타일
    });

    busPath.setMap(map);

    // 정류장 마커 추가
    let markerGap = 0.002; // 마커 간격 설정
    dataVal.forEach((el, index) => {
        let latlng = new kakao.maps.LatLng(parseFloat(el.gpsY), parseFloat(el.gpsX));

        // 마커 위치를 약간씩 위아래로 옮겨서 겹치지 않게 설정
        let markerPosition = new kakao.maps.LatLng(latlng.getLat() + (markerGap * (index % 2 === 0 ? 1 : -1)), latlng.getLng());

        let marker = new kakao.maps.Marker({
            position: markerPosition,
            image: markerImage,
            map: map
        });

        // 마커 클릭 이벤트 처리
        kakao.maps.event.addListener(marker, 'click', function() {
            // 클릭한 마커의 정보를 표시할 코드 작성
              showStationInfo(el.stationId, el.stationNm, el.gpsY, el.hpsX, el.busRouteId);
            // 예를 들어, 정보를 콘솔에 출력하거나 다른 작업을 수행할 수 있습니다.
            console.log('Clicked marker:', el);

        });
    });

    // 나머지 로드뷰 및 마커 설정 코드는 그대로 사용합니다.
    map.addOverlayMapTypeId(kakao.maps.MapTypeId.ROADVIEW);

    let rvContainer = document.getElementById('roadview');
    let rv = new kakao.maps.Roadview(rvContainer);
    let rvClient = new kakao.maps.RoadviewClient();

    let rvMarker = new kakao.maps.Marker({
        image: markerImage,
        position: mapOption.center,
        draggable: true,
        map: map
    });

    kakao.maps.event.addListener(rvMarker, 'dragend', function(mouseEvent) {
        let position = rvMarker.getPosition();
        toggleRoadview(position);
    });

    kakao.maps.event.addListener(map, 'click', function(mouseEvent) {
        let position = mouseEvent.latLng;
        rvMarker.setPosition(position);
        toggleRoadview(position);
    });

    function toggleRoadview(position) {
        rvClient.getNearestPanoId(position, 50, function(panoId) {
            if (panoId === null) {
                rvContainer.style.display = 'none';
                mapContainer.style.width = '100%';
                map.relayout();
            } else {
                mapContainer.style.width = '50%';
                map.relayout();
                rvContainer.style.display = 'block';
                rv.setPanoId(panoId, position);
                rv.relayout();
            }
        });
    }
}


//여기서부터 도착정보
// 정류장 클릭 이벤트 리스너: 지도에 정류장 정보 표시
stationList.addEventListener('click', function(event) {
    const stationItem = event.target.closest('.station-item');
    if (stationItem) {
        const stationId = stationItem.getAttribute('data-station-id');
        fetchStationInfo(stationId); // 선택한 정류장에 대한 정보 가져오기
    }
});

// 선택한 정류장에 대한 정보 가져오기 함수
function fetchStationInfo(stationId) {
    let apiUrl = `/api/stationInfo?stationId=${stationId}`;

    fetch(apiUrl)
        .then(response => response.json())
        .then(data => {
            showOnMap(parseFloat(data.gpsY), parseFloat(data.gpsX), data.stationNm); // 지도에 정류장 표시
        })
        .catch(error => {
            console.error('정류장 정보를 가져오는 중 에러 발생:', error);
        });
}

// 지도에 정류장 표시하는 함수
function showOnMap(stationLat, stationLng, stationNm) {
    const mapContainer = document.getElementById('map');

    // 지도가 없으면 생성하고, 있으면 중심을 변경합니다.
    if (!map) {
        map = new kakao.maps.Map(mapContainer, {
            center: new kakao.maps.LatLng(stationLat, stationLng),
            level: 5 // 줌 레벨 조정 가능
        });
    } else {
        map.setCenter(new kakao.maps.LatLng(stationLat, stationLng));
    }

    // 마커를 추가하고 정보창을 엽니다.
    const markerPosition = new kakao.maps.LatLng(stationLat, stationLng);
    const marker = new kakao.maps.Marker({
        position: markerPosition,
        map: map
    });

    const infoWindow = new kakao.maps.InfoWindow({
        position: markerPosition,
        content: `<div style="padding:5px;">${stationNm}</div>`
    });

    infoWindow.open(map, marker);
}

//보기 전환 함수
function toggleView(viewType) {
    const mapContainer = document.getElementById('map');
    const rvContainer = document.getElementById('roadview');
    const busRouteContainer= document.getElementById('bus-route');


    if (viewType === 'roadview') {
        rvContainer.style.display = 'block'; //로드뷰 컨테이너 보이기
        busRouteContainer.style.display ='none'; //버스 노선 정보 컨테이너 숨기기
        mapContainer.style.width = '100%';
        rvContainer.style.width='100%'; //로드뷰의 넓이도 100% 로 조정 추가한거
        // positionFn([]); // 필요 없는 코드
    } else if (viewType === 'bus-route') {
        rvContainer.style.display = 'none';
        mapContainer.style.width = '100%';
        busSearch(); // 버스 노선 정보 다시 로드
    }
}





// DOMContentLoaded 이벤트 리스너로 초기 실행
document.addEventListener('DOMContentLoaded', (event) => {
    busSearch(); // 초기 버스노선 정보 표시
});


function toggleView(viewType) {
    const mapContainer = document.getElementById('map');
    const rvContainer = document.getElementById('roadview');

    if (viewType === 'roadview') {
        rvContainer.style.display = 'block';
        mapContainer.style.display = 'none'; // 맵 컨테이너를 숨깁니다.
        rvContainer.style.width = '100%'; // 로드뷰의 넓이도 100%로 조정
    } else if (viewType === 'bus-route') {
        rvContainer.style.display = 'none';
        mapContainer.style.display = 'block'; // 맵 컨테이너를 다시 보이게 합니다.
        mapContainer.style.width = '100%'; // 지도의 넓이를 100%로 조정
        map.relayout(); // 지도의 레이아웃을 다시 설정합니다.
        busSearch(); // 버스 노선 정보를 다시 로드
    }
}





