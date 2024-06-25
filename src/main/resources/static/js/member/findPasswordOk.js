function passwordCheck(){
if($('#userPw').val()==$('#userPw2').val()){
$('#noMessage').text('')
document.getElementById('next').disabled =false;

}else{
$('#noMessage').text('비밀번호가 다릅니다')
document.getElementById('next').disabled =true;
}
}




