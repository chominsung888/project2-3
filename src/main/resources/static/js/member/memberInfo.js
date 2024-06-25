function memberDeleteFn(){
 const id=$('#id').text()
 $.ajax({
    type: 'POST',
    url: `/member/memberDelete/${id}`,
    success: function (res) {
      if(res==1){
         alert('해고완료')
       location.href=`/member/memberList`;
        }
      },
    error: function () {
    alert('실패')
    }
  });
}

function memberDeleteReady() {
        if (!confirm("해고하시겠습니까?")) {
        } else {
           memberDeleteFn();
        }
}


function memberDepartmentUpdateFn(){
  var department= $('#department').val()
  var memberId = $('#memberId2').val();
  $.ajax({
    type: 'POST',
    url: `/member/departUpdate`,
    data: { id: memberId, department: department },
    success: function () {
    alert('성공')
    },
    error: function () {
    location.href=`/member/memberInfo/${memberId}`;
    }
  });
}

function memberMRankUpdateFn(){
  var mRank= $('#mRankForm #mRank').val();
  var memberId = $('#memberId3').val();
  $.ajax({
    type: 'POST',
    url: '/member/MRankUpdate',
    data: { id: memberId, mRank: mRank },
    success: function () {
    alert('성공')
    },
    error: function () {
    location.href=`/member/memberInfo/${memberId}`;
    }
  });
}

var modalBtn1 = document.getElementById("modalBtn1");
var modalBtn2 = document.getElementById("modalBtn2");
var modal1 = document.getElementById("myModal1");
var modal2 = document.getElementById("myModal2");

modalBtn1.onclick = function() {
  modal1.style.display = "block";
};
var closeBtn1 = modal1.querySelector(".close1");
closeBtn1.onclick = function() {
  modal1.style.display = "none";
};


modalBtn2.onclick = function() {
  modal2.style.display = "block";
};
var closeBtn2 = modal2.querySelector(".close2");
closeBtn2.onclick = function() {
  modal2.style.display = "none";
};





