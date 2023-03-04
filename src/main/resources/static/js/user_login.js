
$(function () {
    initial();
})

let user;

let webSocket;

// let messCount = 0;

let initial = function () {
    $.getJSON("/user/initial", function (data, statusText) {

        user = data;

        if (statusText == "success") {
            let parent = $("#login_and_register").parent();

            let address = $("#address");

            parent.children("#login_and_register").remove();
            parent.append("<div id='user-header' class=\"dropdown\">"
                + "<button class=\"btn btn-sm\" type='button' data-bs-toggle=\"dropdown\"style='border-style: none'>"
                + "<img src='/schoolres/images/users/" + data.photo + "' style='border-radius: 50%; height: 40px'/>"
                + "</button>"
                + "<ul class=\"dropdown-menu dropdown-menu-sm-end\">"
                + "<li><button class=\"dropdown-item\" type=\"button\" onclick='sign_out()'>退出登录</button></li>"
                // + "<li><button class=\"dropdown-item\" type=\"button\">订单</button></li>"
                // + "<li><button class=\"dropdown-item\" type=\"button\">消息</button></li>"
                + "</ul>"
                + "</div>");

            let address_list = data.address;

            for (let index in address_list) {
                $("#address_datalist").append("<option value=\"" + address_list[index] + "\"/>");
            }

            address.attr("style", "color: block");
            address.children().text("");
            address.next("div").children("button").removeAttr("disabled");
            // address.next("div").children("button").removeAttr("class");
            address.next("div").children("button").attr("class", "btn btn-outline-success");

            // address.next("div").next("div").children("button:last").removeAttr("disabled");
            // address.next("div").next("div").children("button:last").removeAttr("class");
            address.next("div").next("div").children("button:last").attr("class", "btn btn-outline-info btn-sm h5");

            chatHandle();
        }
    })
}

let sign_out = function () {
    let parent = $("#user-header").parent();
    $("#user-header").remove();

    $.ajax("/user/signOut", {method : 'delete'});

    // location.reload();

    parent.append("<div id=\"login_and_register\" class=\"text-end\">\n" +
        "                <a href=\"/user/login\" class=\"btn btn-outline-primary\" th:text=\"#{login}\">登录</a>\n" +
        "                <a href=\"/register\" class=\"btn btn-warning\" th:text=\"#{register}\">注册</a>\n" +
        "            </div>");
}

let setting_address = function () {
    let value = $("input[name='address']").val();

    if (value == undefined || value == '')
    {
        return;
    }

    $("#address").next("div").next("div").children("button:last").removeAttr("disabled");

    $.post("/setAddress", {address : value, id : user.id});

    $("input[name='address']").val("");
    $("#address").children().text(value);
}

let clear_all = function () {
    // let id_list = new Array();
    $("#cart_list_group").children("a").each(function () {
        $("body>div").find(".row").find("#" + $(this).attr("id")).each(function () {
            $(this).children("span").text(0);
            $(this).children("span").attr("hidden", "hidden");
            $(this).parent("div").children("div:last").children("div:last").children("div").children("button:first").attr("disabled", "disabled");
        })
    });

    $("#cart_list_group").children().remove();
}

let gotoEnd = function () {
    $("#mess-container").animate({
        scrollTop: ($('#mess-modal-footer').offset().top) + "px"
    }, 500)
}

let addMess = function (tip, data) {
    let node = $("#mess-container");
    console.log("tip is" + tip);

    if (tip == 0)
    {
        node.append("<div class=\"info-view\" style=\"justify-content: flex-end\">\n" +
            "                        <div class=\"mess-border\" style=\"margin-right: 17px\">\n" +
            "                            <div class=\"mess-border-sub\" style=\"right: -16px; border-color: #fff #fff #fff cornflowerblue;\"></div>\n" +
            "                            " + data + "\n" +
            "                        </div>\n" +
            "                        <img src=\"/schoolres/images/users/default.png\" class=\"mess-img\">\n" +
            "                    </div>");
    }
    else if (tip == 1)
    {
        node.append("<div class=\"info-view\">\n" +
            "                        <img src=\"/schoolres/images/users/default.png\" class=\"mess-img\">\n" +
            "                        <div class=\"mess-border\"  style=\"margin-left: 17px\">\n" +
            "                            <div class=\"mess-border-sub\" style=\"left: -16px; border-color: #fff cornflowerblue #fff #fff;\"></div>\n" +
            "                            " + data + "\n" +
            "                        </div>\n" +
            "                    </div>");
    }

    gotoEnd();
    // else
    // {
    //     console.log("tip == jjj");
    //     node.append("<div class=\"info-view\">\n" +
    //         "                        <img src=\"/schoolres/images/users/default.png\" class=\"mess-img\">\n" +
    //         "                        <div class=\"mess-border\"  style=\"margin-left: 17px\">\n" +
    //         "                            <div class=\"mess-border-sub\" style=\"left: -16px; border-color: #fff cornflowerblue #fff #fff;\"></div>\n" +
    //         "                            " + data + "\n" +
    //         "                        </div>\n" +
    //         "                    </div>");
    // }
}

let chatHandle = function () {

    if (webSocket && webSocket.readyState == 1)
    {
        webSocket.close();
    }

    webSocket = new WebSocket("wss://www.shuyuan.store/chatHandle/" + user.name);

    webSocket.onopen = function () {
        webSocket.onmessage = function (res) {
            console.log(res.data);
            let data = res.data.split(",");
            let rider = data[3];
            let order_id = data[1];

            // messCount += 1;

            // messages.push(data[0]);
            addMess(1, data[0]);

            let group = $("#mess-obj-list-group").children();

            if (group.length == 0)
            {
                // data-order-id='" + data[1] + "' data-rider='" + obj + "'
                // data-bs-toggle="modal" data-bs-target="#chatModal"
                $("#mess-obj-list-group").append("<a id='mes-item-" + data[1] + "' class=\"list-group-item\">\n" +
                    "                                <div class=\"mess-view\">\n" +
                    "                                    <span class=\"position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger\">\n" +
                    "                                        1 \n" +
                    // "                                        <span class=\"visually-hidden\">unread messages</span>\n" +
                    "                                    </span>\n" +
                    "                                    <div style=\"width: 30%\">\n" +
                    "                                        <img src=\"/schoolres/images/users/default.png\" class=\"mess-item\">\n" +
                    "                                    </div>\n" +
                    "                                    <div class=\"mess-text-sty\">\n" +
                    "                                        <div>\n" +
                    "                                            <h5>订单# " + data[1] + "</h5>\n" +
                    "                                        </div>\n" +
                    "                                        <div class=\"mess-text-mess\">\n" +
                    "                                            " + data[0] + "\n" +
                    "                                        </div>\n" +
                    "                                    </div>\n" +
                    "                                </div>\n" +
                    "                            </a>");

                $("#mes-item-" + data[1]).click((obj) => {
                    modalShow(order_id, rider);

                    let badge = $("#mes-item-" + data[1]).find("div>span");

                    badge.attr("hidden", "hidden");
                    badge.text(0)
                })
            }
            else
            {
                for (let i = 0; i < group.length; i++)
                {
                    // console.log(i);
                    // console.log($(group[i]).attr("id"));
                    // console.log($(group[i]));
                    if ($(group[i]).attr("id") == "mes-item-" + data[1])
                    {
                        let badge = $(group[i]).find("div>span");

                        // console.log(badge);
                        let count = Number.parseInt(badge.text());
                        badge.text(count + 1);
                        badge.removeAttr("hidden");
                        $(group[i]).children("div").children("div:last").children("div:last").text(data[0]);
                    }
                }
            }
        }
    }
    
}

let sendMes = function (obj) {
    // console.log($(obj));
    let mess = $(obj).prev().val().trim();

    let order_id = $(obj).data("data-order-id");
    let rider = $(obj).data("data-rider");

    if (mess == undefined || mess == '' || mess == null)
    {
        return;
    }

    let data = {
        mess: mess,
        id: order_id,
        obj: rider,
        tag: '0'
    }

    addMess(0, mess);

    $("#chatMess").val("");

    webSocket.send(JSON.stringify(data));
}