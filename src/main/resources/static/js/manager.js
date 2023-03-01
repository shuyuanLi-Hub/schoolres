
$(function () {
    getOrders();
    getDishes();
})

let getOrders = function () {
    let name = ($("#shop-name").text() + "").split("-")[0];
    let category = ($("#shop-name").text() + "").split("-")[1];
    let items = $("#orders-item");
    items.children().remove();

    $.getJSON("/managerOrders", {name : name, category : category}, function (data, statusText) {

        let price = 0;
        let errCount = 0;
        for (let i = 0; i < data.length; i++)
        {
            let item = $("<li class=\"list-group-item disabled\" aria-disabled=\"true\">\n" +
            "                            <div>\n" +
            "                                <div style=\"display: flex; flex-wrap: nowrap; justify-content: space-between\">\n" +
            "                                    <h4><u># " + data[i].id + "</u></h4>\n" +
            "                                    <h4><span class=\"badge text-bg-primary\"></span></h4>\n" +
            "                                </div>\n" +
            "\n" +
            "                                <div id='order-item-one' class=\"order-item-view\">\n" +
            "                                </div>\n" +
            "<!--                                <hr style=\"border: 1px solid black\">-->\n" +
            "                                <div class=\"order-mess-view\">\n" +
            "                                    <span>完成时间: " + data[i].date + "</span>\n" +
            "                                    <span>用户电话: " + data[i].phone + "</span>\n" +
            "                                    <span>总价: <span></span> <i class=\"bi-currency-yen\"></i></span>\n" +
            "                                </div>\n" +
            "                            </div>\n" +
            "                        </li>");

            let badge = item.children("div").children("div:first").find("h4>span");
            if (data[i].status == "0")
            {
                badge.attr("class", "badge text-bg-danger");
                badge.text("未分配骑手");
            }
            else if (data[i].status == "1")
            {
                badge.attr("class", "badge text-bg-primary");
                badge.text("配送中");
            }
            else if (data[i].status == "2")
            {
                badge.attr("class", "badge text-bg-success");
                badge.text("已完成");
            }
            else
            {
                errCount += 1;
                badge.attr("class", "badge text-bg-danger");
                badge.text("已取消");
            }

            let dishes = data[i].dishes;
            let count = 0;

            for (let i = 0; i < dishes.length; i++) {
                price += (count += dishes[i].count * dishes[i].price);
                item.find("div.order-item-view").append("<div class=\"order-item\">\n" +
                    "                                        <img src=\"/schoolres/images/" + dishes[i].photo + "\" class=\"order-item-img\">\n" +
                    "                                        <span class=\"order-item-text\">\n" +
                    "                                            " + dishes[i].dishesName + "\n" +
                    "                                            <i class=\"bi bi-x\"></i>\n" +
                    "                                            <span class=\"badge bg-danger\">" + dishes[i].count + "</span>\n" +
                    "                                        </span>\n" +
                    "                                    </div>");
            }

            item.find("div.order-mess-view").children("span:last").children("span").text(count);
            items.append(item);
        }

        let itemView = $("div.order-title").next("div.view-sub-view").children();
        itemView.children("div:first").children("span").text(data.length + "单");
        itemView.children("div:odd").find("span:first").text(errCount + "单");
        itemView.children("div:last").find("span>span").text(price);
    })
}

let getDishes = function () {
    let name = ($("#shop-name").text() + "" ).split("-")[0];
    let view = $("#dishes_list");
    view.children().remove();

    // console.log("shop = " + name);
    $.getJSON("/managerDishes", {name : name}, function (data, statusText) {
        // console.log(data);
        let row;
        for (let i = 0; i < data.length; i++)
        {
            if (i % 4 == 0)
            {
                row = $("<div class=\"row mb-2\" style=\"height: 200px\"></div>");
                view.append(row);
            }

            row.append("<div class=\"col-3 h-100 col-sty\" onclick='dishesModal($(this))'>\n" +
                "                                <img src=\"/schoolres/images/" + data[i].photo + "\" class=\"list-item-img\">\n" +
                "                                <div style=\"height: 20%\">\n" +
                "                                    <div class=\"list-item-text\">\n" +
                "                                        <h6><strong>" + data[i].name + "</strong></h6>\n" +
                "                                        <h6>" + data[i].price + "<i class=\"bi-currency-yen\"></i></h6>\n" +
                "                                    </div>\n" +
                "                                    <div class=\"list-item-text-sub\">\n" +
                "                                        <i class=\"bi bi-chat-heart\" style=\"margin-right: 5px\"></i><span>" + data[i].desc + "</span>\n" +
                "                                    </div>\n" +
                "                                </div>\n" +
                "                            </div>");
        }
    })
}

// let showImg = function (file) {
//     console.log("开始改变");
//     console.log(file.files[0]);
//     let reader = new FileReader();
//     let img = $("#modalImg");
//
//     reader.onload = function (e) {
//         img.attr("src", e.target.result);
//     }
//
//     reader.readAsDataURL(file.files[0]);
// }

let showImg = function () {
    $("input#modalCover").on("change", function () {
        let reader = new FileReader();
        let img = $("#modalImg");

        $("#modalCover").removeClass("is-invalid");
        reader.onload = (e) => {
            img.attr("src", e.target.result);
        }

        reader.readAsDataURL(this.files[0]);
    });

    $("input#modalCover").click();

}

let dishesModal = function (obj) {
    let model = $("#dishesModal");
    model.find("h5.modal-title").text($(obj).find("div.list-item-text>h6>strong").text());
    model.find("form#modalForm>div:first>a>img").attr("src", $(obj).children("img").attr("src"));
    model.find("form#modalForm").children("div:eq(1)").find("input#dishesName").val($(obj).find("div.list-item-text>h6>strong").text());
    // model.find("input#originName").val($(obj).find("div.list-item-text>h6>strong").text());
    model.find("input#dishesPrice").val($(obj).find("div>h6:last").text());
    model.find("input#dishesDesc").val($(obj).find("div>span").text());
    $("#modalCover").removeClass("is-invalid");
    $("#dishesModal").modal("show");
}

let login = function () {
    $.post("/managerLogin", {account : $("input#account").val(), passwd : $("input#passwd").val()}, function (data, statusText) {
        if (data.status == "ok")
        {
            $(location).attr("href", "/manager/"+ data.shop);
        }
        else
        {
            alert("用户名或密码错误");
        }
    })
}

let addDishes = function () {

    console.log($("input#cover").get(0).files[0].size);
    if ($("input#cover").get(0).files[0].size > 5 * 1024 * 1024)
    {
        let toast = $("#alert-toast");
        toast.children("div:last").text("上传文件大小不能超过1MB!");
        toast.show();

        setTimeout(() => {
            toast.hide();
        }, 2000);

        return ;
    }

    $.ajax({
        url : "/addDishes",
        type : "post",
        processData : false,
        contentType : false,
        data : new FormData($("#dishesForm")[0]),
    }).done(function (data, statusText) {
        $("#dishesForm")[0].reset();
        let toast = $("#alert-toast");
        if (data.status == "ok")
        {
            getDishes();
            toast.children("div:last").text("添加成功");
            toast.children("div:first").attr("class", "toast-header bg-success text-black");
            toast.show();
            setTimeout(function () {
                toast.hide()
            }, 2000);
        }
        else
        {
            toast.children("div:last").text(data.mess);
            toast.show();
            setTimeout(function () {
                toast.hide()
            }, 2000);
        }
    })

}

let updateDishes = function () {
    if ($("input#modalCover").get(0).files[0].size > 5 * 1024 * 1024)
    {
        $("#modalCover").addClass("is-invalid");

        return ;
    }

    $.ajax({
        url : "/updateDishes/" + $("h5.modal-title").text(),
        type : "patch",
        contentType: false,
        processData: false,
        data: new FormData($("#modalForm")[0])
    }).done(function (data, statusText) {
        $("form#modalForm")[0].reset();
        if (data == "ok")
        {
            getDishes();
        }
    })
}

let deleteDishes = function () {
    let name = $("h5.modal-title").text();

    $.ajax({
        url : "/deleteDishes",
        type : "delete",
        data : {name : name}
    }).always(function () {
        getDishes();
        let toast = $("#alert-toast");
        toast.children("div:last").text("删除成功!");
        toast.show();

        setTimeout(() => {
            toast.hide();
        }, 2000);
    })
}