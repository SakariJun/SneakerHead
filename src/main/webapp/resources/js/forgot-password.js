$("#submit").click(e => {
	e.preventDefault();

	let email = $('input[name="user-email"]').val()
	let phone = $('input[name="user-phone"]').val()


	$(".modal").modal({
		backdrop: 'static',
		keyboard: false
	})

	$.ajax({
		url: './ForgotPassword',
		type: 'POST',
		dataType: 'json',
		contentType: "application/json; charset=utf-8",
		data: JSON.stringify({
			"user_email": email,
			"user_phone": phone
		}),
		success: function(data) {
			$(".modal-btn").removeClass('d-none');
			if (!data.status) {
				displayMessage(data.message)
				return
			}
			displayMessage(data.message)
		},
		error: function() {
			displayMessage("Có lỗi xảy ra")
			$(".modal-btn").removeClass('d-none');
		}
	});
})

function displayMessage(message) {
	$("#message").html(message)
}