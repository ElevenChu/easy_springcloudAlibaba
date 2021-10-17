package com.elevenchu.springcloud.service.impl;

import com.elevenchu.springcloud.dao.OrderDao;
import com.elevenchu.springcloud.domain.Order;
import com.elevenchu.springcloud.service.AccountService;
import com.elevenchu.springcloud.service.OrderService;
import com.elevenchu.springcloud.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderDao orderDao;
    @Resource
    private AccountService accountService;
    @Resource
    private StorageService storageService;



    @Override
    public void create(Order order) {
        log.info("------->开始新建订单");
        orderDao.create(order);
        log.info("------->订单微服务开始调用库存，做扣减");
        storageService.decrease(order.getProductId(),order.getCount());
        log.info("------->订单微服务开始调用库存，做扣减END");

        //3 扣减账户
        log.info("----->订单微服务开始调用账户，做扣减Money");
        accountService.decrease(order.getUserId(),order.getMoney());
        log.info("----->订单微服务开始调用账户，做扣减end");

        //4 修改订单状态，从零到1,1代表已经完成
        log.info("----->修改订单状态开始");
        orderDao.update(order.getUserId(),0);
        log.info("----->修改订单状态结束");

        log.info("----->下订单结束了，O(∩_∩)O哈哈~");

    }
}
