//브라우저가 HTML을 전부 읽고 DOM 트리를 완성하는 즉시 발생
document.addEventListener('DOMContentLoaded', function () {
    let calendarEl = document.getElementById('calendar');
    let Calendar = FullCalendar.Calendar;
    let calendar = new FullCalendar.Calendar(calendarEl, {
        // 형식
        initialView: 'dayGridMonth',
        defaultDate: new Date(),
        customButtons: {
            prev: {
                text: "Prev month",
                click: function () {
                    calendar.prev()
                    getCalendar(calendar.getDate())
                }
            },
            next: {
                text: "Next month",
                click: function () {
                    calendar.next()
                    getCalendar(calendar.getDate())
                }
            },
            today: {
                text: "today",
                click: function () {
                    console.log(calendar.gotoDate(new Date()))
                    getCalendar(calendar.getDate())
                }
            },

            // 이벤트 구현
            AddEventButton: {
                // 오른쪽 텍스트
                text: "일정 추가",
                click: function () {

                    $("#calendarModal").modal('show'); // 커스텀 하게 제작

                    $("#addBtn").on("click", function () {

                        location.replace(location.href);

                        let content = $("#calendar_content").val();
                        let start_date = $("#calendar_start_date").val();
                        let end_date = $("#calendar_end_date").val();
                        //   let memberId = $("#memberId").val(); //사용자 아이디 가져오기

                        if (content == null || content == "") {
                            alert("내용을 입력하세요.");
                        } else if (start_date == "" || end_date == "") {
                            alert("날짜를 입력하세요.");
                        } else if (new Date(end_date) - new Date(start_date) < 0) { // date 타입으로 변경 후 확인
                            alert("종료일이 시작일보다 먼저입니다.");
                        } else {
                            <!--                 let obj = {-->
                            <!--                    "content": content,-->
                            <!--                    "start": start_date,-->
                            <!--                    "end": end_date-->
                            <!--                  }-->
                            setCalendar(content, start_date, end_date)
                        }
                    });
                    $("#calendarModal").modal('hide');
                }
            },
        },
        eventSources: [],
        //   headerToolbar
        headerToolbar: {
            left: 'prev,next today',
            center: 'title',
            right: 'AddEventButton'
        },
        editable: false,
        droppable: true,
        eventClick: function (info) {
            if (confirm('정말로 이 이일정을 삭제하시겠습니까?')) {
                info.event.remove(); //캘린더에서 일정제거
                deleteCalendarEvent(info.event.id); //서버에서 일정 삭제
            }
        }
    });

    // ajax  DB 데이터 set
    function setCalendar(content, start, end) {
        $.ajax({
            url: "/api/calendar",
            method: "POST",
            dataType: "json",
            async: false,
            data: {
                content: content,
                start: start,
                end: end
            }
        })
            .done(function (data) {
                // getCalendar 함수 호출
                getCalendar(calendar.getDate())
                calendar.render();
            })
            .fail(function (xhr, status, errorThrown) {
                console.log("오류");
            })
            .always(function (xhr, status) {
                console.log("완료");
            });

    }

    // DB 데이터 get
    function getCalendar(date) {
        calendar.removeAllEvents();
        let result;
        month = date.getMonth() + 1
        if (month < 10) {
            month = "0" + month
        }
        $.ajax({
            url: "/api/calendar",
            dataType: "json",
            async: false
        })
            .done(function (data) {
                console.log(data);
                // data -> DB data
                $.each(data, function (index, element) {
                    console.log(element.content, element.start);

                    calendar.addEvent({
                        // title ->
                        title: element.content,
                        start: element.start,
                        end: element.end,
                    })
                })
                //calendar.render();
                result = data
            })
            .fail(function (xhr, status, errorThrown) {
                console.log("오류");
            })
            .always(function (xhr, status) {
                console.log("완료");
            });
        return result
    }

    // 서버에서 일정 삭제 후 새로운 데이터 가져와서 캘린더에 반영
    function deleteCalendarEvent(eventId) {
        $.ajax({
            url: "/api/calendar/delete",
            method: "POST",
            dataType: "json",
            data: {
                eventId: eventId
            }
        })
        .done(function (data) {
            // 삭제가 완료되면 캘린더를 다시 로드합니다.
            getCalendar(calendar.getDate());
            calendar.render();
            // 일정이 성공적으로 삭제되었다는 메시지를 출력합니다.
            console.log("일정이 성공적으로 삭제되었습니다.");

            // 사용자에게 메시지를 표시합니다.
            alert("일정이 성공적으로 삭제되었습니다.");
        })
        .fail(function (xhr, status, errorThrown) {
            // 일정 삭제 중 오류가 발생한 경우 오류 메시지를 출력합니다.
            console.error("일정 삭제 중 오류가 발생했습니다:", errorThrown);

            // 사용자에게 오류 메시지를 표시합니다.
            alert("일정 삭제 중 오류가 발생했습니다. 다시 시도해주세요.");
        });
    }

    // 처음 실행 시
    calendar.addEvent({
        title: "월요일",
        start: "2024-05-13"
    });
    calendar.render(); // 그린다(실제 브라우저에 표시)

    getCalendar(calendar.getDate()); // getCalendar함수 호출

});
