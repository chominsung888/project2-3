(()=>{
    weatherFn(document.getElementById("search").value);
})();

function weatherSearch(){
    // ???? ??? id="map"? ??
    weatherFn(document.getElementById("search").value);
}

function weatherFn(location){
    let lat, lon;

    switch(location) {
        case "Songpa":
            lat = 37.514856398566735;
            lon = 127.10323168435833;
            break;
        case "Nowon":
            lat = 37.65496749140469;
            lon = 127.061297075607;
            break;
        case "Paju":
            lat = 37.83472924837163;
            lon = 126.73551132667517;
            break;
        case "Gangbuk":
            lat = 37.62227771123334;
            lon = 127.0397961958558;
            break;
        case "Mapo":
            lat = 37.557641983466915;
            lon = 126.92587776948747;
            break;
        default:
            lat = 37.5665;
            lon = 126.9780;
    }

    /*alert(`${location}`);*/

    const apiUrl = `/api/weatherList?lat=${lat}&lon=${lon}`;

    $.ajax({
        url: apiUrl,
        dataType: 'json',
        type: 'GET',
        success: function(result){
            console.log(result); // weatherList:{}


            const weatherList = JSON.parse(result.weatherList);

            const temp_max = (weatherList.main.temp_max - 273.15).toFixed(1);
            const temp_min = (weatherList.main.temp_min - 273.15).toFixed(1);
            const name = location;

            const humidity = weatherList.main.humidity;

            const icon = weatherList.weather[0].icon; // ??? ?? ????
            const iconUrl = `http://openweathermap.org/img/wn/${icon}@2x.png`; // ??? URL ??

            const description = weatherList.weather[0].description;
            const temp = (weatherList.main.temp - 273.15).toFixed(1);

            const speed = weatherList.wind.speed;
            const clouds_all = weatherList.clouds.all;

            /////////////////////////////////////////////////

            $('#city').text(name);
            $('#temp_max').text(temp_max);
            $('#temp_min').text(temp_min);

            $('#humidity').text(humidity);

            $('#weather_icon').attr('src', iconUrl); // ??? ??

            $('#description').text(description);
            $('#temp').text(temp);

            $('#speed').text(speed);
            $('#clouds_all').text(clouds_all);

            kakaoFn(lat, lon);
        }
    });
}

function kakaoFn(lat, lon){
    /*alert(`${lat}, ${lon}`);*/

    var mapContainer = document.getElementById('map'), // ??? ??? div
        mapOption = {
            center: new kakao.maps.LatLng(lat, lon), // ??? ????
            level: 3 // ??? ?? ??
        };

    var map = new kakao.maps.Map(mapContainer, mapOption); // ??? ?????

    // ??? ??? ??? ??? ?????
    var marker = new kakao.maps.Marker({
        // ?? ????? ??? ?????
        position: map.getCenter()
    });
    // ??? ??? ?????
    marker.setMap(map);

    // ??? ?? ???? ?????
    // ??? ???? ??? ????? ??? ??? ?????
    kakao.maps.event.addListener(map, 'click', function (mouseEvent) {
        // ??? ??, ?? ??? ?????
        var latlng = mouseEvent.latLng;

        // ?? ??? ??? ??? ????
        marker.setPosition(latlng);

        var message = '??? ??? ??? ' + latlng.getLat() + ' ??, ';
        message += '??? ' + latlng.getLng() + ' ???';

        var resultDiv = document.getElementById('clickLatlng');
        resultDiv.innerHTML = message;
    });
}