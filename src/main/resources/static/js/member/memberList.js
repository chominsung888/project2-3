function memberListFn(){
const a=$('#a').val()
 $.ajax({
    type: 'GET',
    url: `/member/memberList?department=${a}`,
    success: function () {

      },
    error: function () {
    alert('실패')
    }
  });
}
function memberListFn(){
const b=$('#b').val()
 $.ajax({
    type: 'GET',
    url: `/member/memberList?department=${b}`,
    success: function () {

      },
    error: function () {
    alert('실패')
    }
  });
}
function memberListFn(){
const c=$('#c').val()
 $.ajax({
    type: 'GET',
    url: `/member/memberList?department=${c}`,
    success: function () {

      },
    error: function () {
    alert('실패')
    }
  });
}
function memberListFn(){
const d=$('#d').val()
 $.ajax({
    type: 'GET',
    url: `/member/memberList?department=${d}`,
    success: function () {

      },
    error: function () {
    alert('실패')
    }
  });
}

function memberListAllFn(){
 $.ajax({
    type: 'GET',
    url: `/member/memberList`,
    success: function () {

      },
    error: function () {
    alert('실패')
    }
  });
}


