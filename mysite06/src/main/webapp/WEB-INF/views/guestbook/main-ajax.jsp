<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="${pageContext.request.contextPath }/assets/css/guestbook-ajax.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
var messageBox = function(title, message, callback) {
	$("#dialog").attr('title', title);
	$("#dialog p").text(message);
	$("#dialog").dialog({
		width: 340,
		modal: true,
		buttons: {
			"확인": function() {
				$(this).dialog("close");
			}
		},
		close: callback
	});			
}

var render = function(vo, mode){
	var html = 
		"<li data-no='"+vo.no+"'>" +
		"<strong>"+vo.name+"</strong>" + 
		"<p>"+(vo.contents).replaceAll('\n', '<br>')+"</p>" + 
		"<strong></strong>" +
		"<a href='' data-no='"+vo.no+"'>삭제</a>" + 
		"</li>";
	 
	$("#list-guestbook")[mode ? 'prepend':'append'](html);
} 

var fetch = function(){
	$.ajax({
		url:'/mysite06/api/guestbook',
		type:'get',
		dataType:'json',
		success:function(response){
			if(response.result === 'fail'){
				console.error(response.message);
				return;
			}
			
			response.data.forEach(function(vo){
				render(vo, false);
			});
		}
	});
};

$(function(){
	$("#add-form").submit(function(event){
		event.preventDefault();
		
		if($("#input-name").val() === ''){
			messageBox("게스트북", "이름은 필수 항목 입니다.", function(){
				$("#input-name").focus();
			})
			return;
		}
		if($("#input-password").val() === ''){
			messageBox("게스트북", "비밀번호는 필수 항목 입니다.", function(){
				$("#input-password").focus();
			})
			return;
		}
		if($("#tx-content").val() === ''){
			messageBox("게스트북", "내용은 필수 항목 입니다.", function(){
				$("#tx-content").focus();
			})
			return;
		}
		
		var vo = {};
		vo.name = $("#input-name").val();
		vo.password = $("#input-password").val();
		vo.contents = ($("#tx-content").val()).replaceAll('<br>', '\n');
		
		$.ajax({
			url: '${pageContext.request.contextPath}/api/guestbook',
			type: 'post',
			dataType: 'json',
			contentType: 'application/json',
			data: JSON.stringify(vo),
			success: function(response){
				if(response.result === 'fail'){
					return;
				}
				$("#input-name").val('');
				$("#input-password").val('');
				$("#tx-content").val('');
				render(vo, true);
			},
			error: function(){
				console.error(response.message);
				return;
			}
		});
	});
	
	var dialogDelete = $("#dialog-delete-form").dialog({
		autoOpen:false,
		modal: true,
		buttons: {
			"삭제":function(){
				var no = $("#hidden-no").val();
				var password = $("#password-delete").val();
				
				console.log("ajax 삭제 !!", no, password);
				
				//후처리
				//1. server 요소 제거
				$.ajax({
					url:'${pageContext.request.contextPath}/api/guestbook/'+no,
					type: 'delete',
					dataType: 'json',
					contentType: 'application/x-www-form-urlencoded',
					data: 'password='+password,
					success: function(response){
						if (response.result === 'fail'){
							console.error(response.message);
							return;
						}
						
						//2. response.data(no) 가지고 있는 <li data+no='{no}'> 찾아서 삭제
						$("li[data-no="+no+"]").remove();
					}
				});
				
				//3. dialogDelete.dialog('close');
				dialogDelete.dialog('close');
			
				//폼의 input value reset;
				$("#password-delete").val('');
			},
			"취소":function(){
				$(this).dialog('close');
			}
		},
		close: function(){
			console.log("dialog close!!!");
		}
	});
	
	// message delete button click event (Live Event)
	$(document).on('click', '#list-guestbook li a', function(event){
		event.preventDefault();
		console.log($(this).data('no'));
		$("#hidden-no").val($(this).data('no'));
		dialogDelete.dialog('open');
	});
	
	//최초 목록
	fetch();
});

</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="guestbook">
				<h1>방명록</h1>
				<form id="add-form" action="" method="post">
					<input type="text" id="input-name" placeholder="이름">
					<input type="password" id="input-password" placeholder="비밀번호">
					<textarea id="tx-content" placeholder="내용을 입력해 주세요."></textarea>
					<input type="submit" value="보내기" />
				</form>
				<ul id="list-guestbook"></ul>
			</div>
			<div id="dialog-delete-form" title="메세지 삭제" style="display:none">
  				<p class="validateTips normal">작성시 입력했던 비밀번호를 입력하세요.</p>
  				<p class="validateTips error" style="display:none">비밀번호가 틀립니다.</p>
  				<form>
 					<input type="password" id="password-delete" value="" class="text ui-widget-content ui-corner-all">
					<input type="hidden" id="hidden-no" value="">
					<input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
  				</form>
			</div>
			<div id="dialog-message" title="" style="display:none">
  				<p></p>
			</div>						
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="guestbook-ajax"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
	<!-- 경고창 -->
	<div id="dialog-message" title="">
  		<p></p>
	</div>
</body>
</html>