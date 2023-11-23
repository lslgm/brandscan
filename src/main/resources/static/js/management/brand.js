function onBrandUpdateClick() {
    if (confirm("수정 하시겠습니까?")) {
        const form = document.querySelector("#updateForm");
        form.submit();

        return true;
    }

    return false;
}

function onBrandDeleteClick() {
    if (confirm("삭제 하시겠습니까?")) {
        alert("보안 인증이 필요합니다.");

        return true;
    }

    return false;
}