   //lnb -> 이벤트 객체, 버블링 ,캡쳐링
   // 이벤트 위임
    $(".faq > ul").on("click", "li", function(e){
        if ($(e.target).is('.sub')) {
            // 하위 메뉴의 a 태그 클릭 시 기본 동작 수행
            return;
        }
        e.stopPropagation(); //버블링 , 캡쳐링 방지
       // e.preventDefault(); // 상위 메뉴의 a 태그 클릭 시 기본 동작 방지
        if($(this).hasClass("active") == false){
            // $(".faq > ul > li").removeClass("active");
            $(this).addClass("active");
            // $(".faq ul li.active .sub").slideUp(300);
            $(this).find(".sub").slideDown(300);
        }
        else{
            $(this).removeClass("active");
            $(this).find(".sub").slideUp(300);
        }
    });

//      document.querySelectorAll('.menu .submenu').forEach(function (item) {
//                item.addEventListener('click', function () {
//                    this.classList.toggle('active');
//                });
//            });