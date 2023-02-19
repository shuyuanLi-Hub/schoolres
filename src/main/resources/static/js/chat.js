

let webSocket = null;

let chatHandle = function () {
    if (webSocket && webSocket.readyState == 1)
    {
        webSocket.close();
    }

    webSocket = new WebSocket("https://www.shuyuan.store/chatHandle");

}