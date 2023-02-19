package org.shuyuan.schoolres.websocket;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.TypeReference;
import jakarta.websocket.*;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.shuyuan.schoolres.service.message.MessageService;
import org.shuyuan.schoolres.service.order.OrderService;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@ServerEndpoint("/chatHandle/{name}")
@Component
public class ChatHandle
{
    private static final ConcurrentHashMap<Session, String> clients = new ConcurrentHashMap<>();
    private static final ExecutorService executorService = Executors.newFixedThreadPool(100);

    @Component
    public static class Tool
    {
        private static MessageService messageService;
        private static OrderService orderService;

        public Tool(MessageService messageService, OrderService orderService)
        {
            this.orderService = orderService;
            this.messageService = messageService;
        }
    }

//    private String name = null;
    private Calendar calendar = Calendar.getInstance();
//    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm");


    @OnOpen
    public void onOpen(Session session, @PathParam("name") String name)
    {
        log.info("连接成功, name = " + name);
//        this.name = name;
        clients.put(session, name);
    }

    @OnClose
    public void onClose(Session session)
    {
        log.info("关闭连接");
        clients.remove(session);
    }

    @OnMessage
    public void onMessage(Session session, String message)
    {
        Map<String, String> obj = JSON.parseObject(message, new TypeReference<Map<String, String>>() {});
        System.out.println(obj);

        clients.forEach((k, v) -> {
            System.out.println(v);
            if (obj.get("obj").equals(v))
            {
                log.info("已发送给" + obj.get("obj"));
                k.getAsyncRemote().sendText(obj.get("mess") + "," + obj.get("id") + "," + obj.get("obj") + "," + clients.get(session));
            }
        });

        if (obj.get("tag").equals("0"))
        {
            executorService.execute(() -> {
                Tool.messageService.save((String) obj.get("mess"), clients.get(session), Integer.valueOf(obj.get("id")), '0', calendar.getTime());
            });
        }
        else
        {
            executorService.execute(() -> {
//                System.out.println(clients.get(session));
                Tool.messageService.save((String) obj.get("mess"), Integer.valueOf(obj.get("id")), clients.get(session), '1', calendar.getTime());
            });
        }
    }


    @OnError
    public void onError(Session session, Throwable ex)
    {
        clients.clear();
    }
}
