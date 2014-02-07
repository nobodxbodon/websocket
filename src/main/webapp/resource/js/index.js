var wsocket;
	var serviceLocation = "ws://"+location.host+"/chat/ws/anything";
	var $nickName;
	var $message;
	var $chatWindow;
	var room = '';
	var sentTime = 0;

	function onMessageReceived(evt) {
		var msg = JSON.parse(evt.data); // native API
		var $messageLine = $(
				'<tr><td class="user label label-info">' + msg.sender
				+ '</td><td class="message badge">' + msg.message
				+ '</td><td class"debug">'+((new Date())-(sentTime==0?msg.received:sentTime))+"ms"
				+ '</td><td class="received">' + new Date(msg.received)
				+ '</td></tr>');
		$chatWindow.append($messageLine);
		sentTime = 0;
		$('.chat-wrapper h2').text('Chat # '+$nickName.val() + "@ "+msg.userInRoom +" in " + room + " ("+msg.userNum+" online, "+ msg.userVisited+" visited since last reboot)");
	}
	function sendMessage() {
		//ignore empty string
		if($.trim($message.val())==""){
			return;
		}
		sentTime = new Date();
		var msg = '{"message":"' + $message.val() + '", "sender":"'
				+ $nickName.val() + '", "room":"'+room+'"}';//'", "received":"'+(new Date())+
		wsocket.send(msg);
		$message.val('').focus();
	}
	
	function connectToChatserver() {
		room = $('#chatroom option:selected').val();
		wsocket = new WebSocket(serviceLocation);
		//alert(wsocket.readyState);
		if(wsocket.readyState > wsocket.OPEN){
			$('.chat-signin h3').text("connection failed");
			wsocket.close();
			alert("socket close:"+wsocket.readyState);
		}else{
			wsocket.onmessage = onMessageReceived;
		}
	}

	function leaveRoom() {
		wsocket.close();
		$chatWindow.empty();
		$('.chat-wrapper').hide();
		$('.chat-signin').show();
		$nickName.focus();
	}

	$(document).ready(function() {
		$nickName = $('#nickname');
		$message = $('#message');
		$chatWindow = $('#response');
		$('.chat-wrapper').hide();
		$('.chat-signin h3').hide();
		$nickName.focus();
		
		$('#enterRoom').click(function(evt) {
			evt.preventDefault();
			connectToChatserver();
			$('.chat-wrapper h2').text('Chat # '+$nickName.val() + "@" + room);
			$('.chat-signin').hide();
			$('.chat-wrapper').show();
			$message.focus();

		});
		$('#do-chat').submit(function(evt) {
			evt.preventDefault();
			sendMessage()
		});
		
		$('#leave-room').click(function(){
			leaveRoom();
		});
	});