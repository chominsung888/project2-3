function memberAppOkFn(){
  var role = $('#role').val();
  var memberId = $('#id').val();
  var department = document.getElementById('department').value;
  var mRank = document.getElementById('mRank').value;

  $.ajax({
    type: 'POST',
    url: '/member/memberAppOk',
    data: { id: memberId, role: role, mRank: mRank, department: department },
    success: function (res) {
    if(res==1){
    alert('승인되었습니다')
    location.href=`/member/memberAppList`;
    }
    },
    error: function () {
      alert('실패')
    }
  });
}

function memberAppNoFn(){
  var memberId = $('#id').val();
  $.ajax({
    type: 'POST',
    url: `/member/memberDelete/${memberId}`,
    success: function (res) {
    if(res==1){
    alert('거절되었습니다')
    location.href=`/member/memberAppList`;
    }
    },
    error: function () {
      alert('실패')
    }
  });
}

function memberAppNoReady() {
        if (!confirm("거절하시겠습니까?")) {
        } else {
        memberAppNoFn()
        }
}



function pageBeforeFn(){
const before= $('#before').val()
const startPage=$('#startPage').val()
const i=$('#i').val()
if(before===i){
document.getElementById('pageBeforeBtn').disabled =true;
$('#pageBeforeBtn').text('◁')
}else{
location.href=`/member/memberAppList?page=${before}`;
document.getElementById('pageBeforeBtn').disabled =false;

}
}

function pageNextFn(){
const next=$('#next').val()
const total=$('#total').val()
if(next===total){
document.getElementById('pageNextBtn').disabled =true;
$('#pageNextBtn').text('▷')
}else{
location.href=`/member/memberAppList?page=${next}`;
document.getElementById('pageNextBtn').disabled =false;
}
}
















