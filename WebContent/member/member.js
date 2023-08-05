function go_save() {
	if (document.formm.id.value == "") {
		alert("아이디를 입력하여 주세요.");
		document.formm.id.focus();
	} else if (document.formm.id.value != document.formm.reid.value) {
		alert("중복확인을 클릭하여 주세요.");
		document.formm.id.focus();
	} else if (document.formm.pwd.value == "") {
		alert("비밀번호를 입력해 주세요.");
		document.formm.pwd.focus();
	} else if ((document.formm.pwd.value != document.formm.pwdCheck.value)) {
		alert("비밀번호가 일치하지 않습니다.");
		document.formm.pwd.focus();
	} else if (document.formm.name.value == "") {
		alert("이름을 입력해 주세요.");
		document.formm.name.focus();
	} else if (document.formm.email.value == "") {
		alert("이메일을 입력해 주세요.");
		document.formm.email.focus();
	} else {
		document.formm.action = contextPath + "/members/join.do";
		document.formm.submit();
	}
}

function idcheck() {
	if (document.formm.id.value == "") {
		alert('아이디를 입력하여 주십시오.');
		document.formm.id.focus();
		return;
	}
	var url = contextPath + "/members/id_check_form?id="
			+ document.formm.id.value;
	window
			.open(url, "_blank_1",
					"toolbar=no, menubar=no, scrollbars=yes, resizable=no, width=330, height=200");
}

function post_zip() {
	var url = contextPath + "/members/find_zip_num";
	window
			.open(
					url,
					"_blank_1",
					"toolbar=no, menubar=no, scrollbars=yes, resizable=no, width=550, height=300, top=300, left=300, ");
}

function go_next() {
	if (document.formm.okon1[0].checked == true) {
		document.formm.submit();
	} else if (document.formm.okon1[1].checked == true) {
		alert('약관에 동의하셔야만 합니다.');
	}
}

function findMemberId() {
	var name = document.findId.name.value;
	var email = document.findId.email.value;

	if (name === "" || email === "") {
		alert("이름과 이메일을 모두 입력해 주세요.");
		return;
	}

	$.ajax({
		type : "POST",
		url : contextPath + "/members/find_member_id.do",
		data : {
			name : name,
			email : email
		},
		success : function(foundId) {
			if (foundId === "") {
				alert("해당하는 아이디가 없습니다.");
			} else {
				alert("찾은 아이디: " + foundId);
				window.location.href = contextPath + "/members/loginForm.do";
			}
		},
		error : function() {
			alert("서버와 통신 중 오류가 발생했습니다.");
		}
	});
}

function findPassword() {
	var id = document.findPW.memberId.value;
	var name = document.findPW.name.value;
	var email = document.findPW.email.value;

	if (id === "" || name === "" || email === "") {
		alert("아이디, 이름, 이메일을 모두 입력해 주세요.");
		return;
	}

	$.ajax({
		type : "POST",
		url : contextPath + "/members/find_member_password.do",
		data : {
			id : id,
			name : name,
			email : email
		},
		success : function(foundPassword) {
			if (foundPassword === "") {
				alert("해당하는 비밀번호가 없습니다.");
			} else {
				alert("찾은 비밀번호: " + foundPassword);
				window.location.href = contextPath + "/members/loginForm.do";
			}
		},
		error : function() {
			alert("서버와 통신 중 오류가 발생했습니다.");
		}
	});
}