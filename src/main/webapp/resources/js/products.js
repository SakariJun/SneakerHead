// Thêm sản phẩm
$("#btn-add").click(e => {
    e.preventDefault();

    $(".btn-update-confirm").removeData()
    $("#modal-update").modal({
        backdrop: 'static',
        keyboard: false
    })
    $("#modal-title").html("Thêm sản phẩm")

    $(".btn-update-confirm").data("method", 'POST')
})

// Cập nhật
$(".btn-update").click(e => {
    e.preventDefault();

    $(".btn-update-confirm").removeData()
    $("#modal-update").modal({
        backdrop: 'static',
        keyboard: false
    })

    $("#modal-title").html("Cập nhật sản phẩm")
    $(".btn-update-confirm").data("method", 'PUT')
    $(".btn-update-confirm").data("id", $(e.target).data("id"))

    $.ajax({
        url: '../Product?id=' + $(e.target).data("id"),
        type: 'get',
        dataType: "json",
        success: function(data) {
            if (!data.status) {
                fadeError(data.message)
                return;
            }
            // load sản phẩm lên modal
            let product = data.message
            $('input[name="product_name"]').val(product.product_name)
            $('input[name="product_code"]').val(product.product_code)
            $('select[name="brand"]').val(product.brand.brand_id)
            $('select[name="category"]').val(product.category.category_id)
            $('select[name="gender"]').val(product.gender.gender_id)
            $('input[name="product_price"]').val(product.product_price)
            $('input[name="product_price_original"]').val(product.product_price_original)
            $('textarea[name="product_desc"]').val(product.product_desc)
            $('input[name="discount"]').val(product.discount * 100)
            let images = JSON.parse(product.product_images)
            for (var index in images) {
                $("#old-images").append(`<div class="form-check">
                <input class="form-check-input" type="checkbox" name="old-images[]" value="${images[index]}" id="image-${index}" checked>
                <label class="form-check-label" for="image-${index}">
                  ${images[index]}
                </label>
              </div>`)
            }

            for (var index in product.product_detail) {
                var detail = product.product_detail[index]
                $("#product_detail").append(`
                <div class="form-group col-6">
						<label>Size</label> <input type="number" class="form-control"
							name="product_size" value="${detail.product_size}">
					</div>
					<div class="form-group col-6">
						<label>Số lượng</label> <input type="number" class="form-control"
							name="product_quantity" value="${detail.product_quantity}">
					</div>
                    `)
            }
        },
        error: function(error) {
            console.log(error)
        }
    });
})

// Thêm size sản phẩm
$(".btn-add-size").click(e => {
    e.preventDefault();

    $("#product_detail").append(`<div class="form-group col-6">
    <label>Size</label> <input type="number" class="form-control"
        name="product_size">
</div>
<div class="form-group col-6">
    <label>Số lượng</label> <input type="number" class="form-control"
        name="product_quantity">
</div>`)
})

// Thêm / Update
$(".btn-update-confirm").click(e => {
    e.preventDefault();

    let name = $('input[name="product_name"]').val()
    let code = $('input[name="product_code"]').val()
    let brand = $('select[name="brand"]').val()
    let category = $('select[name="category"]').val()
    let gender = $('select[name="gender"]').val()
    let price = $('input[name="product_price"]').val()
    let price_original = $('input[name="product_price_original"]').val()
    let images = $('input[name="images"]')[0].files
    let desc = $('textarea[name="product_desc"]').val()
    let discount = $('input[name="discount"]').val() / 100
    let old_images = $('input[name="old-images[]"]:checked')

    let size = []
    let quantity = []

    $('input[name="product_size"]').each(function() {
        size.push($(this).val());
    });

    $('input[name="product_quantity"]').each(function() {
        quantity.push($(this).val());
    });

    var formData = new FormData();

    for (let i = 0; i < size.length; i++) {
        formData.append("product_detail[]", JSON.stringify({ "product_size": size[i], "product_quantity": quantity[i] }));
    }

    let product_images_json = {}

    for (let i = 0; i < old_images.length; i++) {
        product_images_json[(i + 1).toString()] = old_images[i].value
    }

    formData.append("product_name", name);
    formData.append("product_code", code);
    formData.append("brand_id", brand);
    formData.append("category_id", category);
    formData.append("gender_id", gender);
    formData.append("product_price", price);
    formData.append("product_price_original", price_original);
    formData.append("discount", discount);
    formData.append("product_desc", desc);

    for (let i = 0; i < images.length; i++) {
        formData.append("images[]", images[i])
    }

    if ($(e.target).data("method") == "PUT") {
        formData.append("product_images_json", JSON.stringify(product_images_json));

        // Sửa sản phẩm
        $.ajax({
            url: '../Product?id=' + $(e.target).data("id"),
            type: 'PUT',
            dataType: "json",
            caches: false,
            contentType: false,
            processData: false,
            data: formData,
            success: function(data) {
                console.log(data)
                if (!data['status']) {
                    fadeError(data.message)
                    return;
                }
                location.reload()
            },
            error: function(error) {
                console.log(error)
            }
        })
    }

    if ($(e.target).data("method") == "POST") {
        // Thêm sản phẩm
        $.ajax({
            url: '../Product',
            type: 'POST',
            dataType: "json",
            caches: false,
            contentType: false,
            processData: false,
            data: formData,
            success: function(data) {
                console.log(data)
                if (!data['status']) {
                    fadeError(data.message)
                    return;
                }
                location.reload()
            },
            error: function(error) {
                console.log(error)
            }
        })
    }
})

// Ẩn sản phẩm
$(".btn-hide").click(e => {
    e.preventDefault();
    $(".btn-confirm").removeData()

    $("#modal-confirm").modal({
        backdrop: 'static',
        keyboard: false
    })

    displayMessage("Bạn có chắc ẩn sản phẩm này?")

    $(".btn-confirm").data("url", "../Product/hide?id=" + $(e.target).data("id"))
})

// hiển sản phẩm
$(".btn-show").click(e => {
    e.preventDefault();
    $(".btn-confirm").removeData()

    $("#modal-confirm").modal({
        backdrop: 'static',
        keyboard: false
    })
    displayMessage("Bạn có chắc Hiện sản phẩm này?")
    $(".btn-confirm").data("url", "../Product/show?id=" + $(e.target).data("id"))
})

// Xóa sản phẩm
$(".btn-delete").click(e => {
    e.preventDefault();
    $(".btn-confirm").removeData()

    $("#modal-confirm").modal({
        backdrop: 'static',
        keyboard: false
    })
    displayMessage("Bạn có chắc muốn xóa sản phẩm này?")
    $(".btn-confirm").data("url", "../Product?id=" + $(e.target).data("id"))
    $(".btn-confirm").data("action", "delete")
})

// confirm hide/show/xóa
$(".btn-confirm").click(e => {
    e.preventDefault();

    let url = $(e.target).data("url")
    let method = "get"

    if ($(e.target).data("action") == "delete") {
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