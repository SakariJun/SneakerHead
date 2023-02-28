// Hủy / Xác thực / Hoàn thành đơn hàng
$(".btn-update").click(e => {
    e.preventDefault();
    $(".btn-confirm").removeData()

    $("#modal-confirm").modal({
        backdrop: 'static',
        keyboard: false
    })

    displayMessage("Cập nhật trạng thái đơn hàng này?")
    $(".btn-confirm").data("method", "PUT")
    $(".btn-confirm").data("url", "../Order/" + $(e.target).data("action") + "?id=" + $(e.target).data("id"))
})

// hiện chi tiết đơn hàng
$(".btn-more").click(e => {
    e.preventDefault();

    $("#modal").modal({
        backdrop: 'static',
        keyboard: false
    })
    $("#tbody-content").html('')

    // ajax
    $.ajax({
        url: "../Order?id=" + $(e.target).data("id"),
        type: 'get',
        dataType: "json",
        success: function(data) {
            if (!data.status) {
                displayMessage(data.message)
                return;
            }
            var details = data.message

            for (var index in details) {
                let detail = details[index]
                $("#tbody-content").append(`<tr>
                <td>${detail.product_id}</td>
                <td>${detail.size}</td>
                <td>${detail.quantity}</td>
            </tr>`)
            }
        },
        error: function(error) {
            console.log(error)
        }
    });
})

// Xóa Đơn hàng
$(".btn-delete").click(e => {
    e.preventDefault();
    $(".btn-confirm").removeData()

    $("#modal-confirm").modal({
        backdrop: 'static',
        keyboard: false
    })
    displayMessage("Bạn có chắc muốn xóa đơn hàng này?")
    $(".btn-confirm").data("method", "DELETE")
    $(".btn-confirm").data("url", "../Order/" + $(e.target).data("action") + "?id=" + $(e.target).data("id"))
})

// confirm hide/show/xóa
$(".btn-confirm").click(e => {
    e.preventDefault();

    let url = $(e.target).data("url")
    let method = "PUT"

    if ($(e.target).data("method") == "delete") {
        method = "delete"
    }

    $.ajax({
        url: url,
        type: method,
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