function pageBeforeFn(){
const before= $('#before').val()
const startPage=$('#startPage').val()
const i=$('#i').val()
if(before===i){
document.getElementById('pageBeforeBtn').disabled =true;
}else{
location.href=`/member/test?page=${before}`;
document.getElementById('pageBeforeBtn').disabled =false;
}
}

function pageNextFn(){
const next=$('#next').val()
const total=$('#total').val()
if(next===total){
document.getElementById('pageNextBtn').disabled =true;
}else{
location.href=`/member/test?page=${next}`;
document.getElementById('pageNextBtn').disabled =false;
}
}


function updateEmail() {
  var selectedDomain = document.getElementById("emailDomain").value;
  var email = document.getElementById("userEmail").value;
  var userEmail = email + selectedDomain;
}


function memberJoinFn(){

const userPw=$('#userPw').val()
const address=$('#address').val()
const phoneNumber=$('#phoneNumber').val()
const name=$('#name').val()
var memberFile= $('#memberFile').val()
var selectedDomain = document.getElementById("emailDomain").value;
var email = document.getElementById("userEmail").value;
var userEmail = email + selectedDomain;

  $.ajax({
    type: 'POST',
    url: '/member/test',
    dataType: 'json',
    data: {
    userEmail : userEmail,
    userPw : userPw,
    address : address,
    phoneNumber : phoneNumber,
    name : name,
    memberFile : memberFile
    },
    success: function (res) {

    if(res===1){
    alert('성공')
    }

    },
    error: function () {
      alert('실패')
    }
  });
}




