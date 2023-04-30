package com.iweb.d0429_springboot_shop.mapper;

import com.iweb.d0429_springboot_shop.entity.Property;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Yang
 * @date 2023/4/16 0:23
 */
@Mapper
public interface PropertyMapper {
    /** 添加商品属性
     * @param property 商品属性对象
     * @return 插入多少条记录
     */
    int add(Property property);

    /** 删除商品属性
     * @param id 商品属性id
     * @return 删除了多少条记录
     */
    int delete(Integer id);

    /** 根据id查询商品属性
     * @param id 商品属性id
     * @return 商品属性对象
     */
    Property get(int id);

    /** 修改商品属性
     * @param property 商品属性对象
     * @return 修改了多少条记录
     */
    int update(Property property);

    /** 查询所有商品属性数据
     * @return 商品属性集合
     */
    List<Property> list();

    /** 查询表中一共有多少条数据
     * @return 数量
     */
    int getTotal();

    /** 查询分类下所有的属性
     * @param cid 分类id
     * @return 属性集合
     */
    List<Property> listByCid(int cid);

    /** 根据分类id查询有多少条属性数据
     * @param cid 分类id
     * @return 数据数量
     */
    int getTotalByCid(int cid);
}
