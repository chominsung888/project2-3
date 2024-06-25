$(function(){
	$("#question").keyup(questionKeyuped);
});

function openChat(){
	setConnectStated(true);
	connect();
}

function showMessage(message) {
    $("#chat-content").append(message);
    $("#chat-content").scrollTop($("#chat-content").prop("scrollHeight"));
}

const bot=$('.bot-con');
function setConnectStated(isTrue){
	if(isTrue){//true
		$('.open-btn').hide();
		$("#btn-chat-open").hide();
		$("#chat-disp").show();
	}else{
		$('.open-btn').show();
		$("#btn-chat-open").show();
		$("#chat-disp").hide();

	}
	//챗봇창 화면 클리어
	$("#chat-content").html("");
}

function disconnect() {
    setConnectStated(false);
    console.log("Disconnected");
}
//버튼클릭시 접속
function connect() {
	sendMessage("안녕");
}

function sendMessage(message){
	$.ajax({
		url:"/busChatBot",
		type:"post",
		data:{message: message},
		success:function(responsedHtml){
			showMessage(responsedHtml);
		}
	});
}

function inputTagString(text){
	var now=new Date();
	var ampm=(now.getHours()>11)?"오후":"오전";
	var time= ampm + now.getHours()%12+":"+now.getMinutes();
	var message=`
		<div class="msg-user-flex-end">
			<div class="user-message">
				<div class="part-user">
					<p>${text}</p>
				</div>
				<div class="time">${time}</div>
			</div>
		</div>
	`;
	return message;
}

function menuclicked(el){
	var text=$(el).text().trim();
	var fToken=$(el).siblings(".f-token").val();
	console.log("-----> fToken:"+fToken+"----");
	var message=inputTagString(text);
	showMessage(message);
}

//엔터가 입력이되면 질문을 텍스트 화면에 표현 
function questionKeyuped(event){
	if(event.keyCode!=13)return;
	btnMsgSendClicked()
}

function btnMsgSendClicked(){

  	var question1 = $("#question").val().trim();
    var question2 = $("#question2").val().trim();
    var question  = question1 + question2;

	if(question=="" || question.length<2)return;

	sendMessage(question);
	 
	var message=inputTagString(question1);
	showMessage(message);
	$("#question").val("");
}

/* 모달 스크립트 html에있음*/
function stationDetail(busRouteId){
modal.style.display="block";
}
function modalClose(){
modal.style.display="none";
}




function stationPost(busRouteId) {

    let html1 = "";
    let apiUrl = `/api/busChatStation?busRouteId=${busRouteId}`;
    fetch(apiUrl)
        .then(response => response.json())
        .then(function (msg) {
            let jsonRs = JSON.parse(msg.rs);
            jsonRs.msgBody.itemList.forEach(el => {

                html1 += `
                <div class="stationList">
                ${el.stationNm}
                </div>
                `;
            });
            $('.stationGo').html(html1);
        });
}

const chatdown=$('#chat-question');
const answer=$('.answerKeyword');


function keyBus(){
answer.css('visibility','hidden');
chatdown.css('visibility','visible');
$('#question2').val('버스')
$('#question').attr('placeholder','버스번호를 입력하세요');
}

function keyStation(){
answer.css('visibility','hidden');
chatdown.css('visibility','visible');
$('#question2').val('정류장')
$('#question').attr('placeholder','정류장을 입력하세요');
}

function keySubway(){
alert('개발중입니다')
}










