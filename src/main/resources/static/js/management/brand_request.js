function onBrandRequestStateUpdateClick() {
    if (confirm("저장 하시겠습니까?")) {
        const form = document.querySelector("#detailForm");
        form.submit();

        return true;
    }

    return false;
}