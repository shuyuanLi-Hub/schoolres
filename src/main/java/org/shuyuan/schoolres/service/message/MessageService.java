package org.shuyuan.schoolres.service.message;

import org.shuyuan.schoolres.domain.Message;
import org.shuyuan.schoolres.domain.Rider;
import org.shuyuan.schoolres.domain.User;
import org.springframework.data.domain.Sort;

import java.util.Date;
import java.util.List;

public interface MessageService
{
    List<Message> findAll(Sort sort);

    void save(String content, String user, Integer orderId, Character tag, Date time);

    void save(String content, Integer orderId, String rider, Character tag, Date time);
}
