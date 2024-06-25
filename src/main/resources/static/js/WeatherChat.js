weatherchatbot$(function(){
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

function setConnectStated(isTrue){
	if(isTrue){//true
		$("#btn-chat-open").hide();
		$("#chat-disp").show();
	}else{
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
		url:"/weather/botController",
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
		<div class="msg user flex end">
			<div class="message">
				<div class="part">
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
	var question=$("#question").val().trim();
	if(question=="" || question.length<2)return;

	sendMessage(question);
	 
	var message=inputTagString(question);
	showMessage(message);
	$("#question").val("");
}
