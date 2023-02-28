$("#update").click(e => {
    e.preventDefault();

    let name = $('input[name="user-name"]').val()
    let phone = $('input[name="user-phone"]').val()
    let address = $('input[name="user-address"]').val()

    $.ajax({
        url: './Account',
        type: 'PUT',
        dataType: 'json',
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify({
            "user_name": name,
            "user_phone": phone,
            "user_address": address
        }),
        success: function(data) {
            $(".modal-btn").removeClass('d-none');
            if (!data.status) {
                fadeError(data.message)
                return
            }
            location.reload()
        },
        error: function() {
            fadeError("Có lỗi xảy ra")
        }
    });
})