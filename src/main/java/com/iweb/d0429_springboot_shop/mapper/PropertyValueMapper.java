package com.iweb.d0429_springboot_shop.mapper;

import com.iweb.d0429_springboot_shop.entity.PropertyValue;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Yang
 * @date 2023/4/16 0:32
 */
@Mapper
public interface PropertyValueMapper {
    /** 添加商品属性值
     * @param propertyValue 商品属性值对象
     * @return 插入多少条记录
     */
    int add(PropertyValue propertyValue);

    /** 删除商品属性值
     * @param id 商品属性值id
     * @return 删除了多少条记录
     */
    int delete(Integer id);

    /** 根据id查询商品属性值
     * @param id 商品属性值id
     * @return 商品属性值对象
     */
    PropertyValue get(int id);

    /** 修改商品属性值
     * @param propertyValue 商品属性值对象
     * @return 修改了多少条记录
     */
    int update(PropertyValue propertyValue);

    /** 查询所有商品属性数据
     * @return 商品属性集合
     */
    List<PropertyValue> list();

    /** 查询表中一共有多少条数据
     * @return 数量
     */
    int getTotal();

    /** 查询指定商品下所有的属性值
     * @param pid 商品id
     * @return 属性值集合
     */
    List<PropertyValue> listByPid(int pid);

    /** 查询指定属性下所有的属性值
     * @param ptid 属性id
     * @return 属性值集合
     */
    List<PropertyValue> listByPtid(int ptid);

}
