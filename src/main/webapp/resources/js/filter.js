$(document).on("change", "input[type=radio]", e => {

    var filter = []
    const price_table = {
        "price-1": 1000000,
        "price-2": 2000000,
        "price-3": 3000000,
        "price-4": 4000000,
    }

    $('input[type=radio]:checked').each((index, item) => {
        filter.push($(item).attr("id"))
    })

    let field = filter.length;
    console.log(filter)

    var children = $('.items2').children();
    children.each(function(idx, item) {
        let brand = 'brand-' + $(item).data('brand');
        let category = 'category-' + $(item).data('category');
        let price = $(item).data('price');

        let out = 0;
        if (filter.includes("price-1")) {
            if (price >= price_table["price-1"]) {
                out += 1
            }
        }
        if (filter.includes("price-2")) {
            if (price < price_table["price-1"] || price > price_table["price-2"]) {
                out += 1
            }
        }
        if (filter.includes("price-3")) {
            if (price < price_table["price-2"] || price > price_table["price-3"]) {
                out += 1
            }
        }
        if (filter.includes("price-4")) {
            if (price < price_table["price-3"] || price > price_table["price-4"]) {
                out += 1
            }
        }
        if (filter.includes("price-5")) {
            if (price <= price_table["price-4"]) {
                out += 1
            }
        }

        for (var x in filter) {
            if (filter[x] != brand && filter[x] != category && filter[x].indexOf("price") < 0) {
                out += 1
            }
        }
        if (out > 0) {
            $(item).addClass("d-none")
        } else {
            $(item).removeClass("d-none")
        }
    });
})

$(document).on("click", "button[type=reset]", e => {
    var children = $('.items2').children();
    children.each(function(idx, item) {
        $(item).removeClass("d-none")
    });
})