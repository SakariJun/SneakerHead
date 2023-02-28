$(".button-cart").click(e => {
    e.preventDefault()

    var cart = []

    if (window.localStorage.getItem("cart") != undefined) {
        cart = JSON.parse(window.localStorage.getItem("cart"))
    }

    let id = $(e.target).data("id")
    let size = $('select[name="size"]').val()

    if (size == undefined) {
        alert("Vui lòng chọn size");
        return;
    }

    let found = -1
    let quantity = 1
    cart.forEach((item, index) => {
        if (item.size == size && item.id == id) {
            quantity = item.quantity + 1
            found = index
        }
    })

    if (found > -1) {
        cart[found] = { "id": id, "size": size, "quantity": quantity }
    } else {
        cart.push({ "id": id, "size": size, "quantity": 1 })
    }
    window.localStorage.setItem("cart", JSON.stringify(cart))
    alert("Thêm vào giỏ hàng thành công");
})