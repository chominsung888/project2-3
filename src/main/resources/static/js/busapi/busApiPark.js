const arr=document.querySelector('arr');
function busArrInfo() {
    let html1 = "";
    let search2 = document.querySelector('#search2');
    let busRouteId = search2.value;

    let apiUrl = `/api/busArrInfo?busRouteId=${busRouteId}`;

    fetch(apiUrl)
        .then(response => response.json())
        .then(function (msg) {
            let jsonRs = JSON.parse(msg.rs);

            jsonRs.msgBody.itemList.forEach(el => {
                html1 += `

                <div>
                첫차도착${el.arrmsg1}
                2차도착${el.arrmsg2}
                정류소번호${el.arsId}
                첫차인원${el.brdrde_Num1}
                2차인원${el.brdrde_Num2}
                정류소명${el.stNm}
                ${el.busRouteAbrv}
                ${el.busRouteId}
                </div>


                `;
            });
            $('.arr').html(html1);

            // 위치 표시
            positionFn(jsonRs.msgBody.itemList);
        });
}


