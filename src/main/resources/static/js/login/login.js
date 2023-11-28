/*

I built this login form to block the front end of most of my freelance wordpress projects during the development stage.

This is just the HTML / CSS of it but it uses wordpress's login system.

Nice and Simple

*/
//가입확인
function signUpCheck() {
    let password = document.getElementById("password").value
    let passwordCheck = document.getElementById("pwdchk").value
    let SC = ["!","@","#","$","%"];
    let check_SC=0;
    let check = true;
    //비밀번호확인
    if(password.length < 6 || password.length>12){
        window.alert('비밀번호는 6글자 이상, 12글자 이하만 이용 가능합니다.');
        document.getElementById('password').value='';
        check = false
    }
    for (let i = 0; i < SC.length; i++) {
        if (password.indexOf(SC[i]) !== -1) {
            check_SC = 1;
        }
    }
    if(check_SC === 0){
        window.alert('!,@,#,$,% 의 특수문자가 들어가 있지 않습니다.')
        document.getElementById('password').value='';
        check = false
    }
        if (password !== passwordCheck) {
            window.alert("비밀번호가 동일하지 않습니다.")
            document.getElementById('password').value='';
            check = false
        } else {
            document.getElementById("passwordError").innerHTML = ""
            document.getElementById("passwordCheckError").innerHTML = ""
        }

        if (password === "") {
            window.alert("비밀번호를 입력해주세요.")
            document.getElementById('password').value='';
            check = false
        } else {
            //document.getElementById("passwordError").innerHTML=""
        }
        if (passwordCheck === "") {
            window.alert("비밀번호를 다시 입력해주세요.")
            check = false
        } else {
            //document.getElementById("passwordCheckError").innerHTML=""
        }

        if (check) {
            document.getElementById("passwordError").innerHTML = ""
            document.getElementById("passwordCheckError").innerHTML = ""
        }
    }