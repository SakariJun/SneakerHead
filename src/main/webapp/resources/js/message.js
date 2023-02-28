function fadeError(error) {
    $("#error-message").html(error).removeClass().addClass("alert alert-warning").fadeIn('slow');
}