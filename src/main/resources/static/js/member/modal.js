// 모달 버튼과 모달창 가져오기
/*222222222*/
var modalBtn = document.getElementById("modalBtn");
var modal = document.getElementById("myModal");

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

function pageBeforeFn(){
const before= $('#before').val()
const next=$('#startPage').val()
if(before===startPage){
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








