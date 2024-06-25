


// 출근 버튼 이벤트 핸들러
const checkInTimeBtn = $('#checkInTimeBtn');
checkInTimeBtn.on('click', checkInTimeBtnFn);

// 퇴근 버튼 이벤트 핸들러
const checkOutTimeBtn = $('#checkOutTimeBtn');
checkOutTimeBtn.on('click', checkOutTimeBtnFn);


function loadAttendanceInfo() {
    const savedInfo = localStorage.getItem('attendanceInfo');
    if (savedInfo) {
        const res = JSON.parse(savedInfo);
        attendanceId = res.id; // 저장된 출근 정보의 attendanceId 설정
        updateAttendanceInfo(res);

    }
}

$(document).ready(function() {
    loadAttendanceInfo(); // 페이지 로드 시 출근 정보 불러오기
});

function checkInTimeBtnFn() {
    if (!confirm("출근하시겠습니까?")) {
        return;
    }
    checkInTimeWrite();
}


let attendanceId; // 출근 시 생성된 attendance ID를 저장할 변수

function checkInTimeWrite() {
    const memberId = $('#memberId').val();
    const dataVal = { 'memberId': memberId };

    console.log(`checkInTimeWrite fn call`);
    console.log(dataVal);

    $.ajax({
        type: 'POST',
        url: '/api/admin/attendance/test/checkInTime',
        contentType: "application/json",
        data: JSON.stringify(dataVal),
        success: function(res) {
            alert('출근완료');

            // 반환된 attendance ID 저장
            attendanceId = res.id;

            // 출근 정보 로컬 스토리지에 저장
            localStorage.setItem('attendanceInfo', JSON.stringify(res));

            let htmlData = `
                <tr>
                    <!-- <td>${res.id}</td> -->
                    <!-- <td>${res.memberId}</td> -->
                    <td>${res.attendanceType}</td>
                    <td>${res.checkInTime}</td>
                    <!-- <td>${res.checkOutTime}</td> -->
                </tr>
            `;
            $(".tData").append(htmlData);

            // 출근 정보 표시
            updateAttendanceInfo(res);


        },
        error: function() {
            alert('이미 출근하셨습니다.');
        }
    });
}

function checkOutTimeBtnFn() {
    if (!confirm("퇴근하시겠습니까?")) {
        return;
    }
    checkOutTimeWrite();
}

function checkOutTimeWrite() {
    if (!attendanceId) {
        alert("출근 기록이 없습니다.");
        return;
    }

    const data = {
        checkOutTime: new Date().toISOString()
    };

    $.ajax({
        type: 'PUT',
        url: `/api/admin/attendance/test/${attendanceId}`, // 저장된 attendanceId를 URL에 포함
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function(res) {
            alert('퇴근 완료');

            // 출근 정보 로컬 스토리지에 저장
            localStorage.setItem('attendanceInfo', JSON.stringify(res));

            // 퇴근 정보 표시
            updateAttendanceInfo(res);
        },
        error: function() {
            alert('퇴근 실패');
        }
    });
}

function updateAttendanceInfo(res) {
    let infoHtml = `
        <div class="attendance-info">
            <!-- <p>Attendance ID: ${res.id}</p> -->
            <!-- <p>Member ID: ${res.memberId}</p> -->

            <div class="info-row">
                <div class="label">근태 현황:</div>
                <div class="value">${res.attendanceType}</div>
            </div>
            <div class="info-row">
                <div class="label">출근 시간:</div>
                <div class="value">${res.checkInTime}</div>
            </div>
            <div class="info-row">
                <div class="label">퇴근 시간:</div>
                <div class="value">${res.checkOutTime || 'N/A'}</div>
            </div>
            <div class="info-row">
                <div class="label">근로 시간:</div>
                <div class="value">${res.workTime || 'N/A'}</div>
            </div>
        </div>
    `;
    $("#attendanceInfo").html(infoHtml); // 정보 표시 영역에 추가
}






/////////////////////////////////////////////////////////////////////////////////

function updateCurrentTime() {
    const now = new Date();
    const dateOptions = { year: 'numeric', month: '2-digit', day: '2-digit', weekday: 'short' };
    const timeOptions = { hour: '2-digit', minute: '2-digit', second: '2-digit' };

    const currentDate = now.toLocaleDateString('ko-KR', dateOptions);
    const currentTime = now.toLocaleTimeString('ko-KR', timeOptions);

    document.getElementById('currentDate').textContent = currentDate;
    document.getElementById('currentTime').textContent = currentTime;
}

setInterval(updateCurrentTime, 1000);
updateCurrentTime();

/////////////////////////////////////////////////////////////////////////////////




















const FONT_FAMILY = 'Audiowide';
const FONT_FAMILY_EMBED = `https://fonts.googleapis.com/css2?family=${FONT_FAMILY}&display=swap`;

const createElement = (tagName, properties) => Object.assign(document.createElement(tagName), {...properties});
const initArray = (length, f = (_, i) => i) => Array.from({length: length}, f);
const fontLink = createElement('link', {rel: 'stylesheet', href: FONT_FAMILY_EMBED});
const loop = () => {
  const set = new Set;
  requestAnimationFrame(function run(time) {
    set.forEach(f => f(time));
    requestAnimationFrame(run);
  });
  return set;
};

class AnalogClock {
  themeLight = 'light';
  themeDark = 'dark';
  clockFrame = null;
  constructor({size = 240, theme = 'light', fontSize = 15, padding = 0, wrapper} = {}){
    Object.assign(this, {size, theme, fontSize, padding, wrapper});
  }
  get _targetTheme(){
    return this.theme === 'light' ? this.themeLight : this.themeDark;
  }
  get _clockFrame(){
    return createElement('div', {className: 'clock-frame', style: `
      position: relative;
      width: ${this.size}px;
      height: ${this.size}px;
      padding: ${this.padding}px;
      box-sizing: border-box;
      border-radius: 50%;
    `});
  }
  get _hourNumbers(){
    return initArray(12, (_, i) => {
      const currentDeg = (360/12) * (i+1);
      const radius = (this.size/2) - this.fontSize - 10;
      const x = radius * Math.cos(Math.PI * ((currentDeg) - 90) / 180);
      const y = radius * Math.sin(Math.PI * ((currentDeg) - 90) / 180);
      const hour = createElement('div', {className: `hour-number`, style: `
        font-family: ${FONT_FAMILY}, cursive;
        font-size: ${this.fontSize}px;
        position: absolute;
        left: 50%;
        top: 50%;
        transform: translate(-50%, -50%) translate(${x}px, ${y}px);
      `});
      hour.innerText = i+1;
      return hour;
    });
  }
  get _graduations(){
    return initArray(60, (_, i) => {
      const currentDeg = (360/60) * i;
      const height = 12;
      const radius = (this.size/2) - (height/2) - 5;
      const x = radius * Math.cos(Math.PI * ((currentDeg) - 90) / 180);
      const y = radius * Math.sin(Math.PI * ((currentDeg) - 90) / 180);
      const border = i % 5 === 0 ? `width: 6px; height: ${height}px;` : `width: 1px; height: ${height}px;`;
      const graduations = createElement('div', {className: `graduations`, style: `
        ${border}
        position: absolute;
        left: 50%;
        top: 50%;
        transform: translate(-50%, -50%) translate(${x}px, ${y}px) rotate(${currentDeg}deg);
      `});
      return graduations;
    });
  }
  render(currentTheme = this._targetTheme){
    this.clockFrame = this._clockFrame;
    this._hourNumbers.forEach(el => this.clockFrame.appendChild(el));
    this._graduations.forEach(el => this.clockFrame.appendChild(el));

    this.wrapper.appendChild(fontLink);
    this.wrapper.appendChild(this.clockFrame);
    this.wrapper.classList.add(currentTheme);
  }
  start(){
    this.render();
    const secondsHand = createElement('div', {className: 'seconds', style: `
      width: 1px;
      height: ${(this.size/2) - this.fontSize}px;
      position: absolute;
      left: 50%;
      bottom: 50%;
      transform-origin: bottom;
      transform: translate(-50%, 0) rotate(0);
      background: #FF0000;
    `});
    const minutesHand = createElement('div', {className: 'minutes', style: `
      width: 5px;
      height: ${(this.size/2) - this.fontSize - 20}px;
      position: absolute;
      left: 50%;
      bottom: 50%;
      transform-origin: bottom;
      transform: translate(-50%, 0) rotate(0);
      z-index: 1;
      background: #fff;
    `});
    const hoursHand = createElement('div', {className: 'hours', style: `
          width: 5px;
          height: ${(this.size/2) - this.fontSize - 40 }px;
          position: absolute;
          left: 50%;
          bottom: 50%;
          transform-origin: bottom;
          transform: translate(-50%, 0) rotate(0);
          z-index: 1;
          background: #fff;
        `})
    this.clockFrame.appendChild(secondsHand);
    this.clockFrame.appendChild(minutesHand);
    this.clockFrame.appendChild(hoursHand);

    const set = loop();
    set.add(() => {
      const now = new Date();
      const h = now.getHours();
      const m = now.getMinutes();
      const s = now.getSeconds();
      const ms = now.getMilliseconds();

      const hDeg = h*30 // (h%12) * (360/12)
      const mDeg = m*6 // (m%60) * (360/60)
      const sDeg = s*6 // (s%60) * (360/60)
      const msDeg = ms*(6/1000)

      secondsHand.style.transform = `translate(-50%, 0) rotate(${msDeg + sDeg}deg)`;
      minutesHand.style.transform = `translate(-50%, 0) rotate(${mDeg + (sDeg / 360) * (360 / 60)}deg)`;
      hoursHand.style.transform = `translate(-50%, 0) rotate(${hDeg + (mDeg / 360) * (360 / 12)}deg)`;
    });
  }
}

const analogClock = new AnalogClock({
  size: 200,
  theme: 'dark',
  fontSize: 15,
  wrapper: document.querySelector('.clock-wrapper')
});
analogClock.start();