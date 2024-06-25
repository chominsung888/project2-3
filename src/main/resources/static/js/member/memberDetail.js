function memberNameUpdateFn(){
  var name = $('#name').val();
  var memberId = $('#memberId').val();
  $.ajax({
    type: 'POST',
    url: '/member/nameUpdate',
    data: { id: memberId, name: name },
    success: function () {
    location.href=`/member/myDetail/${memberId}`;

    },
    error: function () {
      alert('실패')
    }
  });
}

function memberAddressUpdateFn(){
  var address = $('#address').val();
  var memberId = $('#memberId2').val();
  $.ajax({
    type: 'POST',
    url: '/member/addressUpdate',
    data: { id: memberId, address: address },
    success: function () {
    location.href=`/member/myDetail/${memberId}`;
    },
    error: function () {
      alert('실패')
    }
  });
}


function memberPhoneNumberUpdateFn(){
  var phoneNumber = $('#phoneNumber').val();
  var memberId = $('#memberId3').val();
  $.ajax({
    type: 'POST',
    url: '/member/phoneNumberUpdate',
    data: { id: memberId, phoneNumber: phoneNumber },
    success: function () {
    location.href=`/member/myDetail/${memberId}`;
    },
    error: function () {
      alert('실패')
    }
  });
}


function memberPasswordUpdateFn(){
  var userPw = $('#userPw').val()
  var userPw2= $('#userPw2').val()
  var memberId = $('#memberId4').val();
  $.ajax({
    type: 'POST',
    url: '/member/passwordUpdate',
    data: { id: memberId, userPw: userPw },
    success: function () {

    if(userPw == userPw2){
    location.href=`/member/myDetail/${memberId}`;
    }else{
    alert('비밀번호가 같아야합니다')
    }
    },
    error: function () {
      alert('실패')
    }
  });
}


// 모달 버튼과 모달창 가져오기

var modalBtn3 = document.getElementById("modalBtn3");
var modalBtn4 = document.getElementById("modalBtn4");
var modalBtn5 = document.getElementById("modalBtn5");
var modal = document.getElementById("myModal");
var modal3 = document.getElementById("myModal3");
var modal4 = document.getElementById("myModal4");
var modal5 = document.getElementById("myModal5");

// 모달 버튼을 클릭하면 모달창을 열도록 이벤트 리스너 등록
modalBtn.onclick = function() {
  modal.style.display = "block";
};

// 모달 닫기 버튼 클릭 시 모달창 닫도록 이벤트 리스너 등록
var closeBtn = modal.querySelector(".close");
closeBtn.onclick = function() {
  modal.style.display = "none";
};

var closeBtn2 = modal.querySelector(".close2");
closeBtn.onclick = function() {
  modal.style.display = "none";
};

///////////////이름///////////////////
modalBtn3.onclick = function() {
  modal3.style.display = "block";
};

// 모달 닫기 버튼 클릭 시 모달창 닫도록 이벤트 리스너 등록
var closeBtn3 = modal3.querySelector(".close3");
closeBtn3.onclick = function() {
  modal3.style.display = "none";
};

////////////////////////////////////////
modalBtn4.onclick = function() {
  modal4.style.display = "block";
};

// 모달 닫기 버튼 클릭 시 모달창 닫도록 이벤트 리스너 등록
var closeBtn4 = modal4.querySelector(".close4");
closeBtn4.onclick = function() {
  modal4.style.display = "none";
};

////////////////////////////////////
modalBtn5.onclick = function() {
  modal5.style.display = "block";
};

// 모달 닫기 버튼 클릭 시 모달창 닫도록 이벤트 리스너 등록
var closeBtn5 = modal5.querySelector(".close5");
closeBtn5.onclick = function() {
  modal5.style.display = "none";
};







