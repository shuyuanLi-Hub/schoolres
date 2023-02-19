
let sub = $("#sub");

let subV = function () {
    if ($.hasData(sub))
    {
        if (sub.attr("disabled") == undefined)
        {
            document.getElementById("sub").disabled = "disabled";
        }
    }
    else
    {
        document.getElementById("sub").removeAttribute("disabled");
    }
}

let change = function ()
{
    let div = $("#name");
    let reg = /^[a-zA-Z0-9_]{6,10}$/;

    if (!reg.test(div.val()))
    {
        $("#name").addClass("is-invalid");
        // submit.attr("disabled", "disabled");
        $.data(sub, "username", "error");
    }
    else
    {
        if (div.hasClass("is-invalid"))
        {
            div.removeClass("is-invalid");
        }
        div.addClass("is-valid");
        $.removeData(sub, "username");
        // submit.removeAttr("disabled");
        // sub.removeData(["username"]);
    }
    subV();
}

let passwdChange = function ()
{
    let reg = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{6,}$/;

    let div = $("#passwd");

    if (!reg.test(div.val()))
    {
        div.addClass("is-invalid");
        // submit.attr("disabled", "disabled");
        $.data(sub, "passwd", "error");
        // sub.data("passwd", "error");
    }
    else
    {
        if (div.hasClass("is-invalid"))
        {
            div.removeClass("is-invalid");
        }
        div.addClass("is-valid");
        // submit.removeAttr("disabled");
        $.removeData(sub, "passwd");
    }
    subV();
}

let mailCha = function ()
{
    let val = $("#mail>option").each(function ()
    {
        let  v = this.value.substring(this.value.lastIndexOf("@"));
        if ($("#email").val().indexOf("@") == -1)
        {
            this.value = $("#email").val() + v;
        }
    });
}

let mailV = function ()
{
    let mail = document.getElementById("email");
    if (mail.checkValidity())
    {
        $(mail).addClass("is-valid");
    }
}

let phoneV = function ()
{
    let reg = /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/;
    let phone = $("#phone");

    if (!reg.test(phone.val()))
    {
        phone.addClass("is-invalid");
        // sub.data("phone", "error");
        $.data(sub, "phone", "error");
    }
    else
    {
        if (phone.hasClass("is-invalid"))
        {
            phone.removeClass("is-invalid");
        }
        phone.addClass("is-valid");
        $.removeData(sub, "phone");
    }
    subV();
}

let img_click = function ()
{
    $("#img").attr("src", "/vercode?" + Math.random());
}

let checkError = function (tip)
{
    $("#" + tip).addClass("is-invalid");
}