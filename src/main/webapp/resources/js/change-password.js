$("#confirm").click(e => {
    e.preventDefault();

    let pwd_new = $('input[name="user-password-new"]').val()
    let pwd = $('input[name="user-password"]').val()

    $.ajax({
        url: './Account/Change-Password',
        type: 'PUT',
        dataType: 'json',
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify({
            "user_password_new": pwd_new,
            "user_password": pwd
        }),
        success: function(data) {
            if (!data.status) {
                fadeError(data.message)
                return
            }
            // Đổi mk thành công -> về đăng nhập lại
            window.location.href = "./Login"
        },
        error: function() {
            alert("Có lỗi xảy ra.");
        }
    });
})