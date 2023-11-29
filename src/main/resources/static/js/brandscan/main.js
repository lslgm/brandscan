function readURL(input) {
    if (input.files && input.files[0]) {
        const reader = new FileReader();

        reader.onload = function (e) {
            const imageArea = document.querySelector(".image-area");
            imageArea.style.display = "block";

            $('#imageResult')
                .attr('src', e.target.result);
        };
        reader.readAsDataURL(input.files[0]);
    }
}

$(function () {
    $('#upload').on('change', function () {
        readURL(input);
    });
});

/*  ==========================================
    SHOW UPLOADED IMAGE NAME
* ========================================== */
const input = document.getElementById( 'upload' );
const infoArea = document.getElementById( 'upload-label' );

input.addEventListener( 'change', showFileName );
function showFileName( event ) {
    const input = event.target;
    const fileName = input.files[0].name;
    infoArea.textContent = 'File name: ' + fileName;
}

function onImageScanClick() {
    const form = document.querySelector("#imageScanForm");
    const data = new FormData(form);

    $.ajax({
        type: "POST",
        url: "http://localhost:5000/v1/object-detection/brandscan",
        //url: "http://13.209.68.221:5000/v1/object-detection/brandscan",
        data: data,
        contentType: false,
        processData: false,
        success: (response) => {
            let name = "Unknown";
            if (response['data'][0] != null) {
                name = response['data'][0]['name'];
                console.log(name);
            }
            location.href = "http://localhost:8080/brand/result?name=" + name;
            //location.href = "http://43.202.36.33:8080/brand/result?name=" + name;
        },
        error: (error) => { alert("스캔 오류"); }
    });
}