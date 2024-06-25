 $(function(){
	$("#question").keyup(questionKeyuped);
});

 function openChat() {
	 setConnectStated(true); // 챗창 보이게 처리
	 connect();
 }

 function findDetail() {
	 var questionInput = document.getElementById('question'); // 입력 필드 가져오기
	 questionInput.value = "오늘"; // 입력 필드에 값 설정
	 var sendButton = document.getElementById('btn-msg-send'); // 전송 버튼 가져오기
	 sendButton.click(); // 전송 버튼 클릭
 }
 function findDetail1() {
	 var questionInput = document.getElementById('question'); // 입력 필드 가져오기
	 questionInput.value = "주말"; // 입력 필드에 값 설정
	 var sendButton = document.getElementById('btn-msg-send'); // 전송 버튼 가져오기
	 sendButton.click(); // 전송 버튼 클릭
 }
 function findDetail2() {
	 var questionInput = document.getElementById('question'); // 입력 필드 가져오기
	 questionInput.value = "주중"; // 입력 필드에 값 설정
	 var sendButton = document.getElementById('btn-msg-send'); // 전송 버튼 가져오기
	 sendButton.click(); // 전송 버튼 클릭
 }

 function showMessage(message) {
	 $("#chat-content").append(message);
	 $("#chat-content").scrollTop($("#chat-content").prop("scrollHeight"));
 }

 function setConnectStated(isTrue) {
	 if (isTrue) {
		 $("#btn-chat-open").hide();
		 $("#chat-disp").show();
	 } else {
		 $("#btn-chat-open").show();
		 $("#chat-disp").hide();
	 }
	 $("#chat-content").html("");
 }

 function disconnect() {
	 setConnectStated(false);
	 console.log("Disconnected");
 }

 function connect() {
	 sendMessage("안녕");
 }

 function sendMessage(message) {
	 $.ajax({
		 url: "/chat/chatController",
		 type: "post",
		 data: {message: message},
		 success: function (responsedHtml) {
			 showMessage(responsedHtml);
		 }
	 });
 }

 function btnMsgSendClicked() {
	 var question = $("#question").val().trim();
	 if (question == "" || question.length < 2) return;
	 sendMessage(question);

	 var message = inputTagString(question);
	 showMessage(message);
	 $("#question").val("");
 }

 function questionKeyuped(event) {
	 if (event.keyCode != 13) return;
	 btnMsgSendClicked();
 }

 function inputTagString(text) {
	 var now = new Date();
	 var ampm = (now.getHours() > 11) ? "오후" : "오전";
	 var time = ampm + now.getHours() % 12 + ":" + now.getMinutes();
	 var message = `
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
