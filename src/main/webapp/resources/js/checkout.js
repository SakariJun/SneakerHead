$(document).ready(() => {

    var cart = []

    if (window.localStorage.getItem("cart") != undefined) {
        cart = JSON.parse(window.localStorage.getItem("cart"))
    }

    var total = 0;
    cart.forEach((item, index) => {
        $.ajax({
            url: '../Product?id=' + item.id,
            type: 'get',
            dataType: "json",
            success: function(data) {
                if (!data.status) {
                    fadeError(data.message)
                    return;
                }
                let product = data.message

                let images = JSON.parse(product.product_images)

                cart_item = `<div class="cart-item my-2">
                <img src="/SneakerHead/resources/images/${product.product_id }/${images[1] }" alt="" />
                <h5>${product.product_name}</h5><p>size ${item.size}</p>`

                let dataPrice = 0;
                if (product.discount > 0) {
                    discount_price = product.product_price * (1 - product.discount)
                    cart_item += `<p class="p-0">
                    <small style="text-decoration: line-through;">${product.product_price.toLocaleString()} </small> ${discount_price.toLocaleString()} 
                    VNĐ</p>`;
                    total = total + item.quantity * discount_price
                    dataPrice = discount_price;
                } else {
                    cart_item += `<p class="p-0">
                  <span style="text-decoration: line-through;"></span>${product.product_price.toLocaleString()}
                </p>`
                    total = total + item.quantity * product.product_price
                    dataPrice = product.product_price
                }
                cart_item += `<input data-total="${item.quantity * dataPrice}" id="quantity-${product.product_id}-${item.size}" data-price="${dataPrice}" type="number" min="1" max="99" name="quantity" value="${item.quantity}"
                step="1"/>
                <a class="btn-remove" data-size="${item.size}" data-id="${product.product_id}">
                <i class="fas fa-trash fa-2x"> </i>
                </a>
                </div>`

                $("#cart-items").append(cart_item)
                $("#total-price").html(total.toLocaleString())
                $(`#quantity-${product.product_id}-${item.size}`).bind('keyup change', e => {
                    let price = $(e.target).val() * $(e.target).data("price")
                    let diff = price - $(e.target).data("total")
                    $(e.target).data("total", price)
                    let total = parseInt($("#total-price").html().replaceAll(".", "")) + diff;
                    $("#total-price").html(total.toLocaleString())
                })
            },
            error: function(error) {
                console.log(error)
            }
        });
    })
})

$(document).on("click", ".btn-remove", e => {
    let id = $(e.target.parentNode).data("id")
    let size = $(e.target.parentNode).data("size")
    var confirm = window.confirm("Bạn muốn xóa khỏi đơn hàng?");
    if (confirm == true) {
        cart = JSON.parse(window.localStorage.getItem("cart"))
        cart.forEach((item, index) => {
            if (item.size == size && item.id == id) {
                cart.splice(index, 1)
            }
        })

        window.localStorage.setItem("cart", JSON.stringify(cart))
        location.reload()
    } else {}
})

$(".button-checkout").click(e => {
    e.preventDefault();
    let name = $('input[name="user_name"]').val()
    let phone = $('input[name="user_phone"]').val()
    let address = $('input[name="user_address"]').val()
    let note = $('input[name="note"]').val()

    if (name.length < 1) {
        alert("Vui lòng nhập Họ tên")
        return
    }
    if (phone.length < 1) {
        alert("Vui lòng nhập Số điện thoại")
        return
    }
    if (address.length < 1) {
        alert("Vui lòng nhập Địa chỉ nhận hàng")
        return
    }

    var cart = JSON.parse(window.localStorage.getItem("cart"))

    $.ajax({
        url: '../Order',
        type: 'POST',
        dataType: 'json',
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify({
            "user_name": name,
            "user_phone": phone,
            "user_address": address,
            "note": note,
            "order_detail": cart,
        }),
        success: function(data) {
            if (!data.status) {
                alert(data.message)
                return
            }
            window.localStorage.removeItem("cart")
            window.location.href = './Complete'
        },
        error: function(err) {
            console.log(err)
        }
    });
})