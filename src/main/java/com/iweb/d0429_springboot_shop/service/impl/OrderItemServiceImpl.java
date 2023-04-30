package com.iweb.d0429_springboot_shop.service.impl;

import com.iweb.d0429_springboot_shop.entity.OrderItem;
import com.iweb.d0429_springboot_shop.entity.Product;
import com.iweb.d0429_springboot_shop.entity.ProductImage;
import com.iweb.d0429_springboot_shop.mapper.OrderItemMapper;
import com.iweb.d0429_springboot_shop.mapper.ProductImageMapper;
import com.iweb.d0429_springboot_shop.service.OrderItemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Yang
 * @date 2023/4/10 4:49
 */
@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Resource
    private OrderItemMapper orderItemMapper;
    @Resource
    private ProductImageMapper productImageMapper;

    @Override
    public List<OrderItem> list(int uid) {
        List<OrderItem> orderItems = orderItemMapper.listByUid(uid);
        return orderItems;
    }

    @Override
    public int getTotal(int uid) {
        return orderItemMapper.getTotalByUid(uid);
    }

    @Override
    public int getShoppingCartsNum(int uid) {
        int carNum;
        if (null==orderItemMapper.getCarCount(uid)){
            carNum = 0;
        }else {
            carNum = orderItemMapper.getCarCount(uid);
        }
        return carNum;
    }

    @Override
    public void add(OrderItem orderItem) {
        orderItemMapper.add(orderItem);
    }

    @Override
    public OrderItem getCarOrderItemByPid(int pid,int uid) {
        return orderItemMapper.getCarOrderItemByPid(pid,uid);
    }

    @Override
    public void update(OrderItem orderItem) {
        orderItemMapper.update(orderItem);
    }

    @Override
    public List<OrderItem> listCar(int uid) {
        List<OrderItem> orderItems = orderItemMapper.listCar(uid);
        for (OrderItem oi:orderItems) {
            List<ProductImage> pis = productImageMapper.listByPid(oi.getProduct().getId());
            oi.getProduct().setImages(pis);
        }
        return orderItems;
    }

    @Override
    public OrderItem get(int id) {
        return orderItemMapper.get(id);
    }

    @Override
    public void delete(int id) {
        orderItemMapper.delete(id);
    }

    @Override
    public List<OrderItem> listByOid(int oid) {
        List<OrderItem> orderItems = orderItemMapper.listByOid(oid);
        for (OrderItem oi:orderItems) {
            Product p = oi.getProduct();
            List<ProductImage> pis = productImageMapper.listByPid(p.getId());
            p.setImages(pis);
        }
        return orderItems;
    }
}
