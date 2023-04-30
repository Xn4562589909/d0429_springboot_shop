package com.iweb.d0429_springboot_shop.mapper;

import com.iweb.d0429_springboot_shop.entity.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Yang
 * @date 2023/4/15 17:07
 */
@Mapper
public interface CategoryMapper {
    /** 添加分类
     * @param category 分类对象
     * @return 插入多少条记录
     */
    int add(Category category);

    /** 删除分类
     * @param id 分类id
     * @return 删除了多少条记录
     */
    int delete(Integer id);

    /** 根据id查询一条分类
     * @param id 分类id
     * @return 分类对象
     */
    Category get(int id);

    /** 修改分类
     * @param category 分类对象
     * @return 修改了多少条记录
     */
    int update(Category category);

    /** 查询所有分类数据
     * @return 分类集合
     */
    List<Category> list();

    /** 查询表中一共有多少条数据
     * @return 数量
     */
    int getTotal();

}
