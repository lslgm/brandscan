
function imgerror(){
    let img = document.getElementById('imgPath').value;
    let title = document.getElementById('title').valueOf();


    if(img===""){
        window.alert('파일을 등록하세요.');
        img.focus();
        return false;
    }

    if(title ===""){
        window.alert('제목을 입력하세요.');
        document.getElementById('title').value='';
        return false;
    }


}