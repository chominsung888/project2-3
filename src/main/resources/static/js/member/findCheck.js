function findPasswordFn(event){
    event.preventDefault();

    var userEmail = $('#userEmail').val();
    var name = $('#name').val();
    var phoneNumber = $('#phoneNumber').val();

 $.ajax({
    type: 'POST',
    url: `/member/findCheck`,
     data: {
                userEmail: userEmail,
                name: name,
                phoneNumber: phoneNumber
            },
    success: function (res) {
      if(res==1){
               location.href=`/member/findPasswordOk?userEmail=`+userEmail;
        }else{
        alert('입력하신 정보가 다릅니다')
        }
      },
    error: function () {
    alert('정보가 없습니다')
    }
  });
}






