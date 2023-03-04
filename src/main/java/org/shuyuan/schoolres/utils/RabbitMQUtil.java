package org.shuyuan.schoolres.utils;

import org.springframework.amqp.core.*;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQUtil
{
    public static void declare(AmqpAdmin admin, String exName, String qName)
    {
        admin.declareExchange(new DirectExchange(exName, true, false));
        admin.declareQueue(new Queue(qName, true, false, false));

        admin.declareBinding(new Binding(qName, Binding.DestinationType.QUEUE, exName, "order", null));
    }

    public static void produce(AmqpTemplate template, String exName, String message)
    {
        template.convertAndSend(exName, "order", message);
    }

    public static void produce(AmqpTemplate template, String exName, String routKey, String message)
    {
        template.convertAndSend(exName, routKey, message);
    }
}
