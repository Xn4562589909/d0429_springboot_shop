package com.iweb.d0429_springboot_shop.service.impl;

import com.iweb.d0429_springboot_shop.entity.Order;
import com.iweb.d0429_springboot_shop.entity.OrderItem;
import com.iweb.d0429_springboot_shop.entity.Product;
import com.iweb.d0429_springboot_shop.entity.ProductImage;
import com.iweb.d0429_springboot_shop.mapper.OrderItemMapper;
import com.iweb.d0429_springboot_shop.mapper.OrderMapper;
import com.iweb.d0429_springboot_shop.mapper.ProductImageMapper;
import com.iweb.d0429_springboot_shop.service.OrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author Yang
 * @date 2023/4/12 21:22
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderMapper orderMapper;
    @Resource
    private OrderItemMapper orderItemMapper;
    @Resource
    private ProductImageMapper productImageMapper;

    @Override
    public void add(Order order) {
        orderMapper.add(order);
    }

    @Override
    public Order get(String orderCode) {
        Order order = orderMapper.getByOrderCode(orderCode);
        List<OrderItem> ois = orderItemMapper.listByOid(order.getId());
        order.setOrderItems(ois);
        int totalNumber = 0;
        BigDecimal total = new BigDecimal(0);
        for (OrderItem oi:ois) {
            Product product = oi.getProduct();
            List<ProductImage> pis = productImageMapper.listByPid(product.getId());
            product.setImages(pis);
            totalNumber += oi.getNumber();
            BigDecimal price = oi.getProduct().getPromotePrice().multiply(new BigDecimal(oi.getNumber()));
            total = total.add(price);
        }
        order.setTotal(total);
        order.setTotalNumber(totalNumber);
        order.getStatusDesc();
        return order;
    }

    @Override
    public Order get(int id){
        Order order = orderMapper.get(id);
        List<OrderItem> ois = orderItemMapper.listByOid(order.getId());
        int totalNumber = 0;
        BigDecimal total = new BigDecimal(0);
        for (OrderItem oi:ois) {
            Product product = oi.getProduct();
            List<ProductImage> pis = productImageMapper.listByPid(product.getId());
            product.setImages(pis);
            totalNumber += oi.getNumber();
            BigDecimal price = product.getPromotePrice().multiply(new BigDecimal(oi.getNumber()));
            total = total.add(price);
        }
        order.setOrderItems(ois);
        order.setTotal(total);
        order.setTotalNumber(totalNumber);
        order.getStatusDesc();
        return order;
    }

    @Override
    public void update(Order order) {
        orderMapper.update(order);
    }

    @Override
    public List<Order> list(int uid) {
        List<Order> orders = orderMapper.listByUid(uid);
        for (Order order:orders) {
            List<OrderItem> ois = orderItemMapper.listByOid(order.getId());
            int totalNumber = 0;
            BigDecimal total = new BigDecimal(0);
            for (OrderItem oi:ois) {
                totalNumber += oi.getNumber();
                BigDecimal price = oi.getProduct().getPromotePrice().multiply(new BigDecimal(oi.getNumber()));
                total = total.add(price);
            }
            order.setOrderItems(ois);
            order.setTotal(total);
            order.setTotalNumber(totalNumber);
            order.getStatusDesc();
        }
        return orders;
    }

    @Override
    public void delete(int id) {
        orderMapper.delete(id);
    }
}
