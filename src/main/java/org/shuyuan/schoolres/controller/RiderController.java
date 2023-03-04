package org.shuyuan.schoolres.controller;

import lombok.extern.slf4j.Slf4j;
import org.shuyuan.schoolres.domain.Order;
import org.shuyuan.schoolres.domain.Rider;
import org.shuyuan.schoolres.service.order.OrderService;
import org.shuyuan.schoolres.service.rider.RiderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class RiderController
{
    private RiderService riderService;
    private OrderService orderService;
    private final String APPID = "wx68cbced4292b3a57";
    private final String SECRET = "685e145bb38b5c0aae4843a2df87b2b8";

    public RiderController(RiderService riderService, OrderService orderService)
    {
        this.riderService = riderService;
        this.orderService = orderService;
    }

    @CrossOrigin
    @GetMapping("/init_orders")
    @ResponseBody
    public ResponseEntity<List<Order>> init()
    {
        log.info("收到请求");
        return new ResponseEntity<>(orderService.findOrderByStatus('0'), HttpStatus.OK);
    }


    @CrossOrigin
    @GetMapping("/rider_login")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> login(String openId, String name)
    {
        Rider rider = new Rider(name, null, openId);

        if (riderService.findRiderByOpenId(openId) != null)
        {
            Map<String, Object> res = new HashMap<>();
            List<Order> orders = orderService.findOrderByRider(name);

            res.put("orders", orders);

            var comRiders = orderService.findOrderByRiderAndStatus(riderService.findRiderByOpenId(openId), '2');

            if (comRiders != null)
            {
                var count = comRiders.size();
                res.put("comCount", count);
                res.put("price", 3 * count);
            }

            var errRiders = orderService.findOrderByRiderAndStatus(riderService.findRiderByOpenId(openId), '3');
            if (errRiders != null)
            {
                res.put("errCount", errRiders.size());
            }

            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        else
        {
            riderService.save(rider);
            return null;
        }
    }

    @CrossOrigin
    @GetMapping("/accOrder")
    public void accOrder(Integer id, String openid)
    {
        orderService.updateStatusAndRider('1', riderService.findRiderByOpenId(openid).getId(), id);
    }

    @CrossOrigin
    @GetMapping("/completeOrder")
    public void completeOrder(Integer id)
    {
        log.info("id = " + id);
        orderService.updateStatusById('2', id);
    }
}
