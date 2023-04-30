package com.iweb.d0429_springboot_shop.service.impl;

import com.iweb.d0429_springboot_shop.entity.*;
import com.iweb.d0429_springboot_shop.mapper.*;
import com.iweb.d0429_springboot_shop.service.CategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Yang
 * @date 2023/4/29 11:54
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;
    @Resource
    private ProductMapper productMapper;
    @Resource
    private ProductImageMapper productImageMapper;
    @Resource
    private OrderItemMapper orderItemMapper;
    @Resource
    private ReviewMapper reviewMapper;
    @Resource
    private PropertyMapper propertyMapper;
    @Resource
    private PropertyValueMapper propertyValueMapper;

    @Override
    public List<Category> list() {
        List<Category> list = categoryMapper.list();
        for (Category c:list) {
            List<Product> products = productMapper.listByCid(c.getId());
            for (Product p:products) {
                List<ProductImage> pis = productImageMapper.listByPid(p.getId());
                int sale;
                if (null==orderItemMapper.getSaleCount(p.getId())){
                    sale = 0;
                }else {
                    sale = orderItemMapper.getSaleCount(p.getId());
                }
                int reviewCount = reviewMapper.getTotalByPid(p.getId());
                p.setImages(pis);
                p.setSaleCount(sale);
                p.setReviewCount(reviewCount);
            }
            c.setProducts(products);
        }
        return list;
    }

    @Override
    public void add(Category category) {
        categoryMapper.add(category);
    }

    @Override
    public Category edit(int id) {
        return  categoryMapper.get(id);
    }

    @Override
    public void update(Category category) {
        categoryMapper.update(category);
    }

    @Override
    public void delete(int id) {
        List<Product> productList = productMapper.listByCid(id);
        for (Product p:productList) {
            List<ProductImage> pis = productImageMapper.listByPid(p.getId());
            for (ProductImage pi:pis) {
                productImageMapper.delete(pi.getId());
            }
            productMapper.delete(p.getId());
        }
        List<Property> properties = propertyMapper.listByCid(id);
        for (Property pt:properties) {
            List<PropertyValue> pvs = propertyValueMapper.listByPtid(pt.getId());
            for (PropertyValue pv:pvs) {
                propertyValueMapper.delete(pv.getId());
            }
            propertyMapper.delete(pt.getId());
        }
        categoryMapper.delete(id);
    }

    @Override
    public Category get(int id) {
        Category category = categoryMapper.get(id);
        List<Product> products = productMapper.listByCid(id);
        for (Product p:products) {
            List<ProductImage> pis = productImageMapper.listByPid(p.getId());
            int reviewCount;
            if (null==reviewMapper.getTotalByPid(p.getId())){
                reviewCount = 0;
            }else {
                reviewCount = reviewMapper.getTotalByPid(p.getId());
            }
            int saleCount;
            if (null==orderItemMapper.getSaleCount(p.getId())){
                saleCount = 0;
            }else {
                saleCount = orderItemMapper.getSaleCount(p.getId());
            }
            p.setSaleCount(saleCount);
            p.setReviewCount(reviewCount);
            p.setImages(pis);
        }
        category.setProducts(products);
        return category;
    }

    @Override
    public int total() {
        return categoryMapper.getTotal();
    }
}
