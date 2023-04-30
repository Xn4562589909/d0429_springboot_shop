package com.iweb.d0429_springboot_shop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    private String name;
    private int id;
    private String gmtCreate;
    private String gmtModified;

//    private String gmt_create;
//    private String gmt_modified;
    //每一个分类所对应的商品集合
    List<Product> products;
}
