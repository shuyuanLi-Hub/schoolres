package org.shuyuan.schoolres.service.message.impl;

import lombok.extern.slf4j.Slf4j;
import org.shuyuan.schoolres.dao.MessageDao;
import org.shuyuan.schoolres.dao.OrderDao;
import org.shuyuan.schoolres.dao.RiderDao;
import org.shuyuan.schoolres.dao.UserDao;
import org.shuyuan.schoolres.domain.Message;
import org.shuyuan.schoolres.domain.Rider;
import org.shuyuan.schoolres.domain.User;
import org.shuyuan.schoolres.service.message.MessageService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class MessageServiceImpl implements MessageService
{
    private MessageDao messageDao;
    private UserDao userDao;
    private RiderDao riderDao;
    private OrderDao orderDao;

    public MessageServiceImpl(MessageDao messageDao, UserDao userDao, RiderDao riderDao, OrderDao orderDao)
    {
        this.messageDao = messageDao;
        this.userDao = userDao;
        this.riderDao = riderDao;
        this.orderDao = orderDao;
    }

    @Override
    public List<Message> findAll(Sort sort)
    {
        List<Message> messages = new ArrayList<>();
        messageDao.findAll(Sort.by(new Sort.Order(Sort.Direction.DESC, "message_id"))).forEach(e -> {
            messages.add(e);
        });

        return messages;
    }

    @Override
    public void save(String content, String user, Integer orderId, Character tag, Date time)
    {
        log.info("开始存");
        Message message = new Message(content,
                time,
                userDao.findUserByName(user),
                orderDao.findById(orderId).get().getRider(),
                tag);

        messageDao.save(message);
    }

    @Override
    public void save(String content, Integer orderId, String rider, Character tag, Date time)
    {
        Message message = new Message(content,
                time,
                orderDao.findById(orderId).get().getUser(),
                riderDao.findRiderByOpenId(rider),
                tag);

        messageDao.save(message);
    }
}
