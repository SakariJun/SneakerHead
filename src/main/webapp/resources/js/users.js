// Khóa user
$(".btn-lock").click(e => {
    e.preventDefault();
    $(".btn-confirm").removeData()

    $("#modal-confirm").modal({
        backdrop: 'static',
        keyboard: false
    })

    displayMessage("Bạn có chắc muốn khóa tài khoản này?")

    $(".btn-confirm").data("method", "PUT")
    $(".btn-confirm").data("id", $(e.target).data("id"))
})

// hiển sản phẩm
$(".btn-delete").click(e => {
    e.preventDefault();
    $(".btn-confirm").removeData()

    $("#modal-confirm").modal({
        backdrop: 'static',
        keyboard: false
    })
    displayMessage("Bạn có chắc muốn xóa Tài khoản này?")
    $(".btn-confirm").data("method", "DELETE")
    $(".btn-confirm").data("id", $(e.target).data("id"))
})

// confirm hide/show/xóa
$(".btn-confirm").click(e => {
    e.preventDefault();

    $.ajax({
        url: '../User?id=' + $(e.target).data("id"),
        type: $(e.target).data("method"),
        dataType: "json",
        success: function(data) {
            if (!data.status) {
                displayMessage(data.message)
                return;
            }
            location.reload();
        },
        error: function(error) {
            console.log(error)
        }
    });
})

function displayMessage(message) {
    $("#message").html(message)
}