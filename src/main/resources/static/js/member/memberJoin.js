function emailCheck(){
		var emailForm = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/;
		var userEmail=$('#userEmail').val()
 if(emailForm.test(userEmail)=== false){
        $('#emailConfirm').text('올바른 이메일 형식이 아닙니다').css('color','red')
        $('#email').css('outline','solid 3px red')
        $('#joinGo').css('color','red')
        $('#joinGo').css('cursor','no-drop')
        document.getElementById('joinGo').disabled =true;
 }else if(emailForm.test(userEmail)=== true){
        $('#emailConfirm').text('')
        $('#email').css('outline','')
        $('#joinGo').css('color','#ffffff')
        $('#joinGo').css('cursor','pointer')
        document.getElementById('joinGo').disabled =false;
 }
 }

function passwordCheck(){
    const joinGo=document.getElementById('.joinGo')
    if($('#userPw').val() == $('#userPwCheck').val()){
        $('#password').css('outline','')
        $('#password2').css('outline','')
        $('#pwConfirm').text('')
        $('#joinGo').css('color','#ffffff')
        $('#joinGo').css('cursor','pointer')
        document.getElementById('joinGo').disabled =false;
    }else{
        $('#pwConfirm').text('비밀번호가 다릅니다').css('color', 'red')
        $('#password').css('outline','solid 3px red')
        $('#password2').css('outline','solid 3px red')
        $('#joinGo').css('color','red')
        $('#joinGo').css('cursor','no-drop')
        document.getElementById('joinGo').disabled =true;
    }
}

function nameCheck(){
  const nameCheck = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/;
  const name=$('#name').val()
  if(nameCheck.test(name)===false){
  $('#userName').css('outline','solid 3px red')
  $('#joinGo').css('cursor','no-drop')
  $('#nameConfirm').text('한글만 입력가능합니다').css('color', 'red')
  document.getElementById('joinGo').disabled =true;
  }else{
  $('#userName').css('outline','')
  $('#joinGo').css('cursor','pointer')
  $('#nameConfirm').text('')
  document.getElementById('joinGo').disabled =false;
  }
}

function phoneNumberCheck(){
//let phoneCheck = /[0-9]/;
let phoneCheck = /^[0-9]*$/;
let phoneNumber=$('#phoneNumber').val()
if(phoneCheck.test(phoneNumber)===false){
$('#phone').css('outline','solid 3px red')
$('#joinGo').css('cursor','no-drop')
$('#phoneNumberConfirm').text('숫자만 입력해주세요').css('color', 'red')
document.getElementById('joinGo').disabled =true;
}else{
$('#phone').css('outline','')
$('#joinGo').css('cursor','pointer')
$('#phoneNumberConfirm').text('')
document.getElementById('joinGo').disabled =false;
}
}


function showPassFn(event){
event.preventDefault();
var userPw = document.getElementById("userPw");
var userPw2 = document.getElementById("userPwCheck");
userPw.type="text";
userPw2.type="text";
}















