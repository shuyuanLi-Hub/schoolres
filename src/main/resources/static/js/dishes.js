
$(function (){

    get_json("normal");
    init_dishes("1", "res_one");
    init_dishes("2", "res_two");
    plus_and_reduce("six_dishes");

    $("#refresh").on("click", function (){
        get_json("special")
    });
});

let get_json = function (type) {
    $.getJSON("get_dishes", {type : type}, function (data, statusText) {

        for (let i in data)
        {
            let index = Number.parseInt(i);
            if (index < 3)
            {
                let val = $("#six_dishes>div:first>div").get(index);

                $(val).find("div[class=position-relative]").attr("id", data[index].id);
                $(val).find("div:first>img").attr("src", "/schoolres/images/" + data[index].photo);
                $(val).find("div>div>h6>b").html(data[index].name);
                $(val).find("div>div>h6>span:first>span").html(data[index].price);
                $(val).find("div>div>h6>span:last>span").html(data[index].shop);
            }
            else
            {
                let val = $("#six_dishes>div:last>div").get(index - 3);

                $(val).find("div[class=position-relative]").attr("id", data[index].id);
                $(val).find("div:first>img").attr("src", "/schoolres/images/" + data[index].photo);
                $(val).find("div>div>h6>b").html(data[index].name);
                $(val).find("div>div>h6>span:first>span").html(data[index].price);
                $(val).find("div>div>h6>span:last>span").html(data[index].shop);
            }
        }
    });
}

let on_click_plus = function (obj) {
    let cart = $("#cart");
    let count = Number.parseInt(cart.attr("data-count"));

    let image_div = obj.parent("div").parent("div").parent("div").parent("div").find("div[class=position-relative]");
    let image_name = $.trim(image_div.children("img").attr("src"));
    let dishes_token = image_div.next("div").find("div>h6");
    let dishes_name = $.trim(dishes_token.find("b").text());
    let dishes_price = $.trim(dishes_token.children("span:first").text());
    let dishes_shop_name = $.trim(dishes_token.children("span:last").find("span").text());
    let rm_id = image_div.attr("id");

    // obj.prev().removeAttr("disabled");
    cart.attr("data-count", count + 1);

    // 获取徽章
    // let budget = image_div.children("span");
    // let budget_count = Number.parseInt(budget.text());
    // budget.text(budget_count + 1);
    // budget.removeAttr("hidden");

    $("body>div").find(".row").find("#" + rm_id).each(function () {
        $(this).children("span").text(Number.parseInt($(this).children("span").text()) + 1);
        $(this).children("span").removeAttr("hidden");
        $(this).parent("div").children("div:last").children("div:last").children("div").children("button:first").removeAttr("disabled");
    });

    let list_group = cart.next("div").find("div[class=list-group]");

    if (list_group.children().length == 0)
    {
        list_group.append("<a href=\"#\" id='" + rm_id + "'" + "class=\"list-group-item list-group-item\" aria-current=\"true\">\n" +
            "                            <div class=\"d-flex w-100 justify-content-between\">\n" +
            "                                <img src=" + "\"" + image_name + "\"" + "class=\"rounded h-25 w-25\">\n" +
            "                                <smal><span class=\"badge rounded-pill text-bg-danger\">1</span></smal>\n" +
            "                            </div>\n" +
            "                            <div class=\"d-flex justify-content-between align-items-end\">\n" +
            "                                <div>\n" +
            "                                    <p class=\"mb-1\">" + dishes_name + "<span class=\"badge text-bg-warning rounded\" style='height: 17px'>" + dishes_price + " <i class=\"bi bi-currency-yen\"></i></span></p>\n" +
            "                                    <span class='small text-secondary'><i class=\"bi bi-house\"></i><span>" + dishes_shop_name + "</span></span>\n" +
            "                                </div>\n" +
            "                                <div>\n" +
            "                                    <div class=\"btn-group mb-1\" role=\"button\">\n" +
            "                                        <button class=\"btn btn-outline-danger btn-sm\" type=\"button\" onclick='cart_on_reduce($(this))'>\n" +
            "                                            <i class=\"bi bi-dash-circle\"></i>\n" +
            "                                        </button>\n" +
            "                                        <button class=\"btn btn-outline-info btn-sm\" type=\"button\" onclick='cart_on_plus($(this))'>\n" +
            "                                            <i class=\"bi bi-plus-circle\"></i>\n" +
            "                                        </button>\n" +
            "                                    </div>\n" +
            "                                </div>\n" +
            "                            </div>\n" +
            "                        </a>");
    }
    else
    {
        let lists = list_group.children("a");

        for (let i = 0; i < lists.length; i++)
        {
            if ($(lists[i]).attr("id") == rm_id)
            {
                let span = $(lists[i]).children("div:first").find("smal>span");
                let count = Number.parseInt(span.text());
                $(lists[i]).children("div:first").find("smal>span").text(count + 1);
                return;
            }
        }

        list_group.append("<a href=\"#\" id='" + rm_id + "'" + "class=\"list-group-item list-group-item\" aria-current=\"true\">\n" +
            "                            <div class=\"d-flex w-100 justify-content-between\">\n" +
            "                                <img src=" + "\"" + image_name + "\"" + "class=\"rounded h-25 w-25\">\n" +
            "                                <smal><span class=\"badge rounded-pill text-bg-danger\">1</span></smal>\n" +
            "                            </div>\n" +
            "                            <div class=\"d-flex justify-content-between align-items-end\">\n" +
            "                                <div>\n" +
            "                                    <p class=\"mb-1\">" + dishes_name + "<span class=\"badge text-bg-warning rounded\" style='height: 17px'>" + dishes_price + " <i class=\"bi bi-currency-yen\"></i></span></p>\n" +
            "                                    <span class='small text-secondary'><i class=\"bi bi-house\"></i><span>" + dishes_shop_name + "</span></span>\n" +
            "                                </div>\n" +
            "                                <div>\n" +
            "                                    <div class=\"btn-group mb-1\" role=\"button\">\n" +
            "                                        <button class=\"btn btn-outline-danger btn-sm\" type=\"button\" onclick='cart_on_reduce($(this))'>\n" +
            "                                            <i class=\"bi bi-dash-circle\"></i>\n" +
            "                                        </button>\n" +
            "                                        <button class=\"btn btn-outline-info btn-sm\" type=\"button\" onclick='cart_on_plus($(this))'>\n" +
            "                                            <i class=\"bi bi-plus-circle\"></i>\n" +
            "                                        </button>\n" +
            "                                    </div>\n" +
            "                                </div>\n" +
            "                            </div>\n" +
            "                        </a>");
    }
}

let on_click_reduce = function (obj) {
    let cart = $("#cart");
    let count = Number.parseInt(cart.attr("data-count"));
    let image_div = obj.parent("div").parent("div").parent("div").parent("div").find("div[class=position-relative]");
    let budget = image_div.children("span");
    let budget_count = Number.parseInt(budget.text());
    let rm_id = $.trim(image_div.attr("id"));
    let list_group = cart.next("div").find("div[class=list-group]");
    let group_item = cart.next("div").find(".offcanvas-body").children().find("#" + rm_id);
    let item_count = group_item.children("div:first").find("smal>span").text();

    if (budget_count > 1)
    {
        // budget.text(budget_count - 1);

        $("body>div").find(".row").find("#" + rm_id).each(function () {
            $(this).children("span").text(budget_count - 1);
        });

        group_item.children("div:first").find("smal>span").text(budget_count - 1);
        cart.attr("data-count", count - 1);
    }
    else if (budget_count == 1 && item_count == budget_count)
    {
        $("body>div").find(".row").find("#" + rm_id).each(function () {
            $(this).children("span").text(budget_count - 1);
            $(this).children("span").attr("hidden", "hidden");
            $(this).parent("div").children("div:last").children("div:last").children("div").children("button:first").attr("disabled", "disabled");
        });

        list_group.get(0).removeChild(group_item.get(0));
        cart.attr("data-count", count - 1);
        // budget.attr("hidden", "hidden");
        // obj.attr("disabled", "disabled");
    }
    else
    {
        // budget.text(budget_count - 1);

        $("body>div").find(".row").find("#" + rm_id).each(function () {
            $(this).children("span").text(budget_count - 1);
            $(this).children("span").attr("hidden", "hidden");
            $(this).parent("div").children("div:last").children("div:last").children("div").children("button:first").attr("disabled", "disabled");
        });

        group_item.children("div:first").find("smal>span").text(item_count - 1);
        // budget.attr("hidden", "hidden");
        // obj.attr("disabled", "disabled");
    }
}

let plus_and_reduce = function (tag_id) {
    $("#" + tag_id + ">div").each(function () {
        $(this).children("div").each(function () {
            $(this).children("div:last").children("div:last").find("div>button:last").on("click", function () {
                on_click_plus($(this));
            });

            let reduce_btn = $(this).children("div:last").children("div:last").find("div>button:first");
            reduce_btn.prop("disabled", "disabled");
            reduce_btn.on("click", function () {
                on_click_reduce($(this));
            });
        })
    });
}

let init_dishes = function (category, node_name) {
    $.getJSON("init_dishes", {category : category, node_name : node_name}, function (data, statusText) {
        let node = $("#" + node_name);
        let row;

        for (let index in data)
        {
            if (index % 5 == 0)
            {
                row = $("<div class=\"row mt-1 mb-3\"></div>");
                node.append(row);
            }

            row.append($("<div class=\"col-md-1-5\">\n" +
                "\n" +
                "                        <!-- 图片部分 -->\n" +
                "                        <div id='" + data[index].id + "' class=\"position-relative\"!\">\n" +
                "                            <!-- 徽章 -->\n" +
                "                            <span class=\"position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger\" hidden>\n" +
                "                                0\n" +
                "                            </span>\n" +
                "                            <img src='" + "/schoolres/images/" + data[index].photo + "' alt=\"加载中\" class=\"d-block rounded\" style=\"width: 100%\"/>\n" +
                "                        </div>\n" +
                "\n" +
                "                        <!-- 第一张图中的文字部分 -->\n" +
                "                        <div class=\"row mt-1\">\n" +
                "\n" +
                "                            <div class=\"col-8\">\n" +
                "                                <h6>\n" +
                "                                    <!-- 菜名 -->\n" +
                "                                    <b>" + data[index].name + "</b>\n" +
                "                                    <span class=\"badge text-bg-light text-danger\">\n" +
                "                                        <!-- 价格 -->\n" +
                "                                        <span>" + data[index].price + "</span> <i class=\"bi bi-currency-yen\"></i>\n" +
                "                                    </span><br>\n" +
                "                                    <!-- 商家名 -->\n" +
                "                                    <span class=\"small text-secondary\"><i class=\"bi bi-house-heart\"></i> <span>" + data[index].shop + "</span> </span>\n" +
                "                                </h6>\n" +
                "                            </div>\n" +
                "\n" +
                "                            <!-- 增减按钮 -->\n" +
                "                            <div class=\"col-4\" id=\"paa\">\n" +
                "                                <div class=\"btn-group\" role=\"button\">\n" +
                "                                    <button class=\"btn btn-outline-danger btn-sm\" type=\"button\" disabled onclick='on_click_reduce($(this))'>\n" +
                "                                        <i class=\"bi bi-dash-circle\"></i>\n" +
                "                                    </button>\n" +
                "                                    <button class=\"btn btn-outline-info btn-sm\" type=\"button\" onclick='on_click_plus($(this))'>\n" +
                "                                        <i class=\"bi bi-plus-circle\"></i>\n" +
                "                                    </button>\n" +
                "                                </div>\n" +
                "                            </div>\n" +
                "                        </div>\n" +
                "\n" +
                "                    </div>"));
        }
    });
}

let search_offcanvas = function (obj) {
    let value = obj.prev().val();

    if (value == "")
    {
        return;
    }
    let nodes = $("body").children("div")

    $.getJSON("search_dishes", {pattern : value}, function (data, statusText) {

        if (data.length == 0)
        {
            obj.prev().val("");
            $("#alert-toast").show();
            setTimeout(function () {
                $("#alert-toast").hide();
            }, 1000)
        }
        else
        {
            nodes.empty();
            $("#page-footer").attr("class", "bg-light position-fixed bottom-0 w-100");
            let container = $("<div class=\"container-fluid mt-3 w-auto\" style=\"margin-left: 50px; margin-right: 50px\"></div>");
            let row;

            for (let index in data)
            {
                if (index % 5 == 0)
                {
                    row = $("<div class=\"row mt-1 mb-3\"></div>");
                    container.append(row);
                }

                row.append($("<div class=\"col-md-1-5\">\n" +
                    "\n" +
                    "                        <!-- 图片部分 -->\n" +
                    "                        <div id='" + data[index].id + "' class=\"position-relative\" >\n" +
                    "                            <!-- 徽章 -->\n" +
                    "                            <span class=\"position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger\" hidden>\n" +
                    "                                0\n" +
                    "                            </span>\n" +
                    "                            <img src='" + "/schoolres/images/" + data[index].photo + "' alt=\"加载中\" class=\"d-block rounded\" style=\"width: 100%\"/>\n" +
                    "                        </div>\n" +
                    "\n" +
                    "                        <!-- 第一张图中的文字部分 -->\n" +
                    "                        <div class=\"row mt-1\">\n" +
                    "\n" +
                    "                            <div class=\"col-8\">\n" +
                    "                                <h6>\n" +
                    "                                    <!-- 菜名 -->\n" +
                    "                                    <b>" + data[index].name + "</b>\n" +
                    "                                    <span class=\"badge text-bg-light text-danger\">\n" +
                    "                                        <!-- 价格 -->\n" +
                    "                                        <span>" + data[index].price + "</span> <i class=\"bi bi-currency-yen\"></i>\n" +
                    "                                    </span><br>\n" +
                    "                                    <!-- 商家名 -->\n" +
                    "                                    <span class=\"small text-secondary\"><i class=\"bi bi-house-heart\"></i> <span>" + data[index].shop + "</span> </span>\n" +
                    "                                </h6>\n" +
                    "                            </div>\n" +
                    "\n" +
                    "                            <!-- 增减按钮 -->\n" +
                    "                            <div class=\"col-4\" id=\"paa\">\n" +
                    "                                <div class=\"btn-group\" role=\"button\">\n" +
                    "                                    <button class=\"btn btn-outline-danger btn-sm\" type=\"button\" disabled onclick='on_click_reduce($(this))'>\n" +
                    "                                        <i class=\"bi bi-dash-circle\"></i>\n" +
                    "                                    </button>\n" +
                    "                                    <button class=\"btn btn-outline-info btn-sm\" type=\"button\" onclick='on_click_plus($(this))'>\n" +
                    "                                        <i class=\"bi bi-plus-circle\"></i>\n" +
                    "                                    </button>\n" +
                    "                                </div>\n" +
                    "                            </div>\n" +
                    "                        </div>\n" +
                    "\n" +
                    "                    </div>"));
            }

            $("body").append(container);
        }
    })
}

let cart_on_plus = function (obj) {
    // 购物车中的物品加一
    let obj_body = obj.parent().parent().parent().parent();
    let budget = obj_body.children("div:first").find("smal>span");
    budget.text(Number.parseInt(budget.text()) + 1);

    let id =obj_body.attr("id");

    $("body>div").find(".row").find("#" + id).each(function () {
        $(this).children("span").text(Number.parseInt($(this).children("span").text()) + 1);
    });
}

let cart_on_reduce = function (obj) {
    let obj_body = obj.parent().parent().parent().parent();
    let budget = obj_body.children("div:first").find("smal>span");
    let budget_count = Number.parseInt(budget.text());
    let id =obj_body.attr("id");
    let list_group = $("#cart").next("div").find("div[class=list-group]");

    if (budget_count > 1)
    {
        budget.text(budget_count - 1);

        $("body>div").find(".row").find("#" + id).each(function () {
            $(this).children("span").text(budget_count - 1);
        });
    }
    else
    {
        list_group.get(0).removeChild(obj_body.get(0));

        $("body>div").find(".row").find("#" + id).each(function () {
            $(this).children("span").text(budget_count - 1);
            $(this).children("span").attr("hidden", "hidden");
            $(this).parent("div").children("div:last").children("div:last").children("div").children("button:first").attr("disabled", "disabled");
        });
    }


}

let over_order = function () {

    let group = $("#cart_list_group").children();
    let addr = $("#address").children().text();
    let remark = $("#remark");
    let time;

    if (group.length == 0)
    {
        return;
    }

    if (addr == undefined || addr == '')
    {
        return;
    }

    remark.on("input", function () {
        clearTimeout(time);
        time = setTimeout(function () {
            let data_list = new Array();

            $("#cart_list_group").children().each(function () {
                let item_count = $(this).children("div:first").find("smal>span").text();
                let shop = $(this).children("div:last").children("div:first").find("span>span").text();
                data_list.push({id : $(this).attr("id"), count : item_count, shop : shop, address : addr, remark : remark.val()});
            })

            remark.val("");
            clear_all();
            $("#orderOver").modal("hide");
            $.ajax({url : "orderOver", type : "POST", data : JSON.stringify(data_list), contentType : "application/json;charset=utf-8"});
        }, 5000)
    })

    $("#orderOver").on("hidden.bs.modal", function () {
        clearTimeout(time);
    })

    $("#orderOver").on("show.bs.modal", function () {
        time = setTimeout(function () {
            // console.log("开始计时");
            let data_list = new Array();

            $("#cart_list_group").children().each(function () {
                let item_count = $(this).children("div:first").find("smal>span").text();
                let shop = $(this).children("div:last").children("div:first").find("span>span").text();
                data_list.push({id : $(this).attr("id"), count : item_count, shop : shop, address : addr});
            })

            clear_all();

            $.ajax({url : "orderOver", type : "POST", data : JSON.stringify(data_list), contentType : "application/json;charset=utf-8"});
        }, 5000)
    })
    $("#orderOver").modal("show");

}

// let order_over = function () {
//     let addr = $("#address").children().text()
//
//     if (addr == undefined || addr == '')
//     {
//         return;
//     }
//
//     let data_list = new Array();
//
//     $("#cart_list_group").children().each(function () {
//         let item_count = $(this).children("div:first").find("smal>span").text();
//         let shop = $(this).children("div:last").children("div:first").find("span>span").text();
//         data_list.push({id : $(this).attr("id"), count : item_count, shop : shop, address : addr});
//     })
//
//     clear_all();
//
//     $.ajax({url : "orderOver", type : "POST", data : JSON.stringify(data_list), contentType : "application/json;charset=utf-8"});
// }

let modalShow = function (order_id, rider) {
    $("#chatMess").next("button").data("data-order-id", order_id);
    $("#chatMess").next("button").data("data-rider", rider);
    $("#mess-modal-title").text("订单# " + order_id);
    $("#chatModal").modal('show');
    // $("#chatMess").focus();
}

let showOrders = function () {
    $("#orderList").children().remove();

    $.getJSON("getOrders", function (data, statusText) {
        if (statusText == "success")
        {
            let orders = $("#orderList");
            // console.log(data);

            for (let index in data)
            {
                // let dishes = index.dishes;

                orders.append("<li id='" + data[index].id + "' class=\"list-group-item\">\n" +
                    "                                <div class=\"order-title-sty\">\n" +
                    "                                    <h5>" + data[index].dishes[0].shopName + "</h5>\n" +
                    "                                    <h5>#" + data[index].id + "</h5>\n" +
                    "                                </div>\n" +
                    "                                <div id='orderListItems'></div>" +
                    "                                <hr style=\"border: 1px solid grey; margin-bottom: 0\">\n" +
                    "                            </li>");

                let count = priceCount = 0;
                let dishes = data[index].dishes;
                let status = data[index].status;

                for (let i in dishes)
                {
                    count += dishes[i].count;
                    priceCount += dishes[i].price * dishes[i].count;

                    $("#orderList>#" + data[index].id + ">#orderListItems").append("<div class=\"order-view\">\n" +
                        "                                        <div>\n" +
                        "                                            <div class=\"order-dishes-view\">\n" +
                        "                                                <img src='" + "/schoolres/images/" + dishes[i].photo + "' class=\"order-dishes-img\">\n" +
                        "                                                <div class=\"order-dishes-text-view\">\n" +
                        "                                                    <div style=\"display: flex; justify-content: space-between\">\n" +
                        "                                                        <span>" + dishes[i].dishesName + "</span>\n" +
                        "                                                    </div>\n" +
                        "\n" +
                        "                                                    <div class=\"order-dishes-text-view-sub\">\n" +
                        "                                                        <div>\n" +
                        "                                                            <span class=\"badge rounded-pill bg-warning\" style=\"margin-right: 5px\">商家描述:</span>\n" +
                        "                                                        </div>\n" +
                        "\n" +
                        "                                                        <div style=\"border-bottom: 1px solid lightblue;\">\n" +
                        "                                                            <span style=\"font-weight: lighter; font-size: small\" class='nowrap'>" + dishes[i].desc + "</span>\n" +
                        "                                                        </div>\n" +
                        "                                                    </div>\n" +
                        "\n" +
                        "                                                </div>\n" +
                        "                                            </div>\n" +
                        "                                            <div class=\"order-info-view\">\n" +
                        "                                                <div class=\"order-info-item\">\n" +
                        "                                                    <span>单价: </span>\n" +
                        "                                                    <span>" + dishes[i].price + " <i class=\"bi bi-currency-yen\"></i></span>\n" +
                        "                                                </div>\n" +
                        "                                                <div class=\"order-info-item\">\n" +
                        "                                                    <span>数量: </span>\n" +
                        "                                                    <span>" + dishes[i].count + "</span>\n" +
                        "                                                </div>\n" +
                        "                                            </div>\n" +
                        "                                        </div>\n" +
                        "                                    </div>")
                }

                if (status == "0")
                {
                    $("#orderList>#" + data[index].id).append("<div class=\"order-info-full-view\">\n" +
                        "                                    <div class=\"order-info-item\">\n" +
                        "                                        <span>总价: </span>\n" +
                        "                                        <span>" + priceCount + " <i class=\"bi bi-currency-yen\"></i></span>\n" +
                        "                                    </div>\n" +
                        "                                    <div class=\"order-info-item\">\n" +
                        "                                        <span>数量: </span>\n" +
                        "                                        <span>" + count + "</span>\n" +
                        "                                    </div>\n" +
                        "                                    <div class=\"order-info-item\">\n" +
                        "                                        <span>餐具: </span>\n" +
                        "                                        <span>2 份</span>\n" +
                        "                                    </div>\n" +
                        "                                    <div class=\"order-info-item\">\n" +
                        "                                        <span>配送状态: </span>\n" +
                        "                                        <span>未分配骑手</span>\n" +
                        "                                    </div>\n" +
                        "                                </div>");
                }
                else if (status == "1")
                {
                    let node = $("<div class=\"order-info-full-view\">\n" +
                        "                                    <div class=\"order-info-item\">\n" +
                        "                                        <span>总价: </span>\n" +
                        "                                        <span>" + priceCount + " <i class=\"bi bi-currency-yen\"></i></span>\n" +
                        "                                    </div>\n" +
                        "                                    <div class=\"order-info-item\">\n" +
                        "                                        <span>数量: </span>\n" +
                        "                                        <span>" + count + "</span>\n" +
                        "                                    </div>\n" +
                        "                                    <div class=\"order-info-item\">\n" +
                        "                                        <span>餐具: </span>\n" +
                        "                                        <span>2 份</span>\n" +
                        "                                    </div>\n" +
                        "                                    <div class=\"order-info-item\">\n" +
                        "                                        <div style='border-bottom: 1px solid red'>" +
                        "                                        <span>配送状态: 骑手正在路上</span></div>\n" +
                        "                                        <button type='button' class='connRiderBtn'>联系骑手</button>\n" +
                        "                                    </div>\n" +
                        "                                </div>");
                    node.find("button[class='connRiderBtn']").click(() => {
                        modalShow(data[index].id, data[index].rider);
                        btnAddMessItem(data[index].id, data[index].rider);

                    })

                    $("#orderList>#" + data[index].id).append(node);
                }
            }
        }
    })
}

let btnAddMessItem = function (orderid, rider) {

    let messListGroup = $("#mess-obj-list-group").children();

    let node = $("<a id='mes-item-" + orderid + "' class=\"list-group-item\">\n" +
        "                                <div class=\"mess-view\">\n" +
        "                                    <span class=\"position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger\" hidden>\n" +
        "                                        0\n" +
        "                                        <span class=\"visually-hidden\">unread messages</span>\n" +
        "                                    </span>\n" +
        "                                    <div style=\"width: 30%\">\n" +
        "                                        <img src=\"/schoolres/images/users/default.png\" class=\"mess-item\">\n" +
        "                                    </div>\n" +
        "                                    <div class=\"mess-text-sty\">\n" +
        "                                        <div>\n" +
        "                                            <h5>订单# " + orderid + "</h5>\n" +
        "                                        </div>\n" +
        "                                        <div class=\"mess-text-mess\">\n" +
        // "                                            " + data[0] + "\n" +
        "                                        </div>\n" +
        "                                    </div>\n" +
        "                                </div>\n" +
        "                            </a>");

    if (messListGroup.length == 0)
    {
        $("#mess-obj-list-group").append(node);

        $("#mes-item-" + orderid).click(() => {
            modalShow(orderid, rider)
        });
    }
    else
    {
        for (let i = 0; i < messListGroup.length; i++)
        {
            if (messListGroup[i].attr("id") == 'mes-item-' + orderid)
            {
                continue;
            }
            else if (i == messListGroup.length - 1 && messListGroup[i].attr("id") != 'mess-item-' + orderid)
            {
                // console.log("在这里");
                $("#mess-obj-list-group").append(node);

                $("#mes-item-" + orderid).click(() => {
                    modalShow(orderid, rider)
                });
            }
        }
    }
}

let showHistory = function () {
    let history_group = $("#history_list");
    history_group.children().remove();

    $.getJSON("getHistory", function (data, statusText) {
        // console.log(data);
        for (let index in data)
        {
            history_group.append("<li id='history-item-" + data[index].id + "' class=\"list-group-item\">\n" +
                "                                <div class=\"order-title-sty\">\n" +
                "                                    <h5>" + data[index].dishes[0].shopName + "</h5>\n" +
                "                                    <h5>#" + data[index].id + "</h5>\n" +
                "                                </div>\n" +
                "                                <div id='historyItems'></div>" +
                "                            </li>");

            let dishes = data[index].dishes;
            let price = 0;

            for (let i in dishes)
            {
                price += dishes[i].price * dishes[i].count;
                $("#history-item-" + data[index].id + ">#historyItems").append("<div class=\"order-dishes-view\" style=\"border-bottom: 1px solid gold\">\n" +
                    "                                    <img src='" + "/schoolres/images/" + dishes[i].photo + "' class=\"order-dishes-img\">\n" +
                    "                                    <div class=\"order-dishes-text-view\" style='margin-bottom: 5px'>\n" +
                    "                                        <span>" + dishes[i].dishesName + "</span>\n" +
                    "                                        <span class=\"history-item-text\">价格: " + dishes[i].price + "<i class=\"bi-currency-yen\"></i></span>\n" +
                    "                                        <span class=\"history-item-text\">数量: x" + dishes[i].count + "</span>\n" +
                    "                                    </div>\n" +
                    "                                </div>");
            }

            $("#history-item-" + data[index].id).append("<div class=\"history-item-text-view\">\n" +
                "                                    <span class=\"history-item-text\">总价: " + price + "<i class=\"bi-currency-yen\"></i></span>\n" +
                "                                    <span class=\"history-item-text\">完成日期: " + data[index].date + "</span>\n" +
                "                                </div>");
        }
    })
}