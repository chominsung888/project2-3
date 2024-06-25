const memberId = document.querySelector('#memberId');
const id = document.querySelector('#id');
const attendanceType = document.querySelector('#attendanceType');
const checkInTime = document.querySelector('#checkInTime');
const checkOutTime = document.querySelector('#checkOutTime');
const tbody = document.querySelector('.tbody');

const checkInTimeBtn = $('#checkInTimeBtn');

checkInTimeBtn.on('click', test);
function test() {
    if (confirm("출근하시겠습니까?")) {
        checkInTimeWrite();
    }
}

function checkInTimeWrite() {
    const memberId = $('#memberId').val();

    const dataVal = {
        'memberId': memberId,
    };

    console.log(`checkInTimeWrite fn call`);
    console.log(dataVal);

    $.ajax({
        type: 'POST',
        url: '/api/admin/attendance/checkInTime',
        contentType: "application/json",
        data: JSON.stringify(dataVal),
        success: function(res) {
            alert(`출근완료`);

            let htmlData = `
                <tr>
                    <td>${res.id}</td>
                    <td>${res.memberId}</td>
                    <td>${res.attendanceType}</td>
                    <td>${res.checkInTime}</td>
                    <td>${res.checkOutTime}</td>
                </tr>`;
            $(".tbody").append(htmlData);

            ajaxAttendanceList(0);
        },
        error: function(xhr, status, error) {
                    if (xhr.responseJSON && xhr.responseJSON.message) {
                        alert(xhr.responseJSON.message);
                    } else {
                        alert("이미 출근하셨습니다.");
                    }
                }
    });
}

function attendanceDeleteBtnFn(event, id) {
    const memberId = $('#memberId').val();
    $.ajax({
        type: 'POST',
        url: `/api/admin/attendance/attendanceDelete/${id}`,
        success: function(res) {
            if (res == 1) {
                alert('삭제완료');
                console.log(res);
                ajaxAttendanceList(0);
            }
        },
        error: function() {
            alert('실패');
        }
    });
}

function ajaxAttendanceList(page) {
    const url = `/api/admin/attendance/attList?page=${page}&size=10&sort=id,DESC`;
    fetch(url, {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
        },
    })
    .then((response) => response.json())
    .then((data) => {
        console.log(data);
        let dataHtml = ``;
        data.content.forEach(el => {
            dataHtml += `
                <tr>
                    <td class="id" id="id">${el.id}</td>
                    <td>${el.memberEntity.id}</td>
                    <td>${el.attendanceType}</td>
                    <td>${el.checkInTime}</td>
                    <td>${el.checkOutTime}</td>

                    <td>${el.workTime}</td>

                    <td>
                        <button type="button" class="updateAttendance" name="updateAttendance" onclick="checkOutTimeBtnFn(${el.id})">퇴근</button>
                        <button type="button" class="checkOutTimeBtn" name="checkOutTimeBtn" onclick="attendanceDeleteBtnFn(event,${el.id})">삭제</button>
                    </td>

                </tr>`;
        });
        tbody.innerHTML = dataHtml;

        let paginationHtml = ``;
        const totalPages = data.totalPages;
        const currentPage = data.number;

        if (currentPage > 0) {
            paginationHtml += `<a href="#" onclick="ajaxAttendanceList(${currentPage - 1})">&laquo; Previous</a> `;
        }

        for (let i = 0; i < totalPages; i++) {
            paginationHtml += `<a href="#" onclick="ajaxAttendanceList(${i})">${i + 1}</a> `;
        }

        if (currentPage < totalPages - 1) {
            paginationHtml += `<a href="#" onclick="ajaxAttendanceList(${currentPage + 1})">Next &raquo;</a>`;
        }

        document.getElementById('pagination').innerHTML = paginationHtml;
    }).catch((error) => {
        console.log(error);
    });
}

function checkOutTimeBtnFn(id) {
    if (confirm("퇴근하시겠습니까?")) {
        const data = {
            id: id,
            attendanceType: "퇴근",
            checkOutTime: new Date().toISOString()
        };

        $.ajax({
            type: 'PUT',
            url: `/api/admin/attendance/${id}`,
            contentType: "application/json",
            data: JSON.stringify(data),
            success: function(res) {
                if (res === 1) {
                    alert('퇴근 완료');
                    ajaxAttendanceList(0);
                } else {
                    alert('퇴근 실패: 올바르지 않은 응답');
                    console.log(res);
                }
            },
            error: function(xhr, status, error) {
                alert('퇴근 실패');
                console.log(`Error: ${status}, ${error}`);
            }
        });
    }
}

$(document).ready(function() {
    ajaxAttendanceList(0);
});