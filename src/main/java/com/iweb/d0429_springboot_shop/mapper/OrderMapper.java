package com.iweb.d0429_springboot_shop.mapper;


import com.iweb.d0429_springboot_shop.entity.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Yang
 * @date 2023/4/15 21:30
 */
@Mapper
public interface OrderMapper {
    /** 添加订单
     * @param order 订单对象
     * @return 插入多少条记录
     */
    int add(Order order);

    /** 删除订单
     * @param id 订单id
     * @return 删除了多少条记录
     */
    int delete(Integer id);

    /** 根据id查询一条订单数据
     * @param id 订单id
     * @return 订单对象
     */
    Order get(int id);

    /** 修改订单
     * @param order 订单对象
     * @return 修改了多少条记录
     */
    int update(Order order);

    /** 查询所有订单数据
     * @return 订单集合
     */
    List<Order> list();

    /** 查询表中一共有多少条数据
     * @return 数量
     */
    int getTotal();

    /** 根据订单编号查询订单
     * @param orderCode 订单编号
     * @return 订单对象
     */
    Order getByOrderCode(String orderCode);

    /** 根据uid查找订单
     * @param uid 用户id
     * @return 订单集合
     */
    List<Order> listByUid(int uid);
}
