function onBrandRetouchReplyClick() {
    if (confirm("답글을 저장 하시겠습니까?")) {
        const form = document.querySelector("#replyForm");
        form.submit();

        return true;
    }

    return false;
}