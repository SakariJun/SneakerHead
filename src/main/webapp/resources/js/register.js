$("#register").click(e => {
	e.preventDefault();

	let email = $('input[name="user-email"]').val()
	let pwd = $('input[name="user-password"]').val()
	let name = $('input[name="user-name"]').val()
	let phone = $('input[name="user-phone"]').val()
	let address = $('input[name="user-address"]').val()

	$(".modal").modal({
		backdrop: 'static',
		keyboard: false
	})

	$.ajax({
		url: './Register',
		type: 'POST',
		dataType: 'json',
		contentType: "application/json; charset=utf-8",
		data: JSON.stringify({
			"user_email": email,
			"user_password": pwd,
			"user_name": name,
			"user_phone": phone,
			"user_address": address
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