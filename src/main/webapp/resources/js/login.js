$("#login").click(e => {
    e.preventDefault();

    let email = $('input[name="user-email"]').val()
    let pwd = $('input[name="user-password"]').val()

    $.ajax({
        url: './Login',
        type: 'POST',
        dataType: 'json',
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify({
            "user_email": email,
            "user_password": pwd
        }),
        success: function(data) {
            if (!data.status) {
                fadeError(data.message)
                return
            }
            // Đăng nhập thành công => redirect home page
            window.location.href = "./"
        },
        error: function() {
            alert("Có lỗi xảy ra.");
        }
    });
})