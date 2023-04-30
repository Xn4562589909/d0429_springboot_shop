package com.iweb.d0429_springboot_shop.service.impl;

import com.iweb.d0429_springboot_shop.entity.Product;
import com.iweb.d0429_springboot_shop.entity.ProductImage;
import com.iweb.d0429_springboot_shop.entity.PropertyValue;
import com.iweb.d0429_springboot_shop.mapper.*;
import com.iweb.d0429_springboot_shop.service.ProductService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Yang
 * @date 2023/4/3 23:32
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Resource
    private ProductMapper productMapper;
    @Resource
    private ProductImageMapper productImageMapper;
    @Resource
    private PropertyValueMapper propertyValueMapper;
    @Resource
    private OrderItemMapper orderItemMapper;
    @Resource
    private ReviewMapper reviewMapper;

    @Override
    public List<Product> list(int cid) {
        List<Product> list = productMapper.listByCid(cid);
        for (Product p:list) {
            List<ProductImage> pis = productImageMapper.listByPid(p.getId());
            p.setImages(pis);
        }
        return list;
    }

    @Override
    public void add(Product product) {
        productMapper.add(product);
    }

    @Override
    public void delete(int id) {
        List<ProductImage> pis = productImageMapper.listByPid(id);
        for (ProductImage pi:pis) {
            productImageMapper.delete(pi.getId());
        }
        List<PropertyValue> pvs = propertyValueMapper.listByPid(id);
        for (PropertyValue pv:pvs) {
            propertyValueMapper.delete(pv.getId());
        }
        productMapper.delete(id);
    }

    @Override
    public Product get(int id) {
        Product product = productMapper.get(id);
        List<ProductImage> productImages = productImageMapper.listByPid(product.getId());
        int saleCount;
        if (null == orderItemMapper.getSaleCount(product.getId())){
            saleCount = 0;
        }else {
            saleCount = orderItemMapper.getSaleCount(product.getId());
        }
        product.setSaleCount(saleCount);
        product.setImages(productImages);
        return product;
    }

    @Override
    public void update(Product product) {
        productMapper.update(product);
    }

    @Override
    public List<Product> list() {
        return productMapper.list();
    }

    @Override
    public List<Product> list(String name) {
        List<Product> products = productMapper.listByName(name);
        for (Product product:products) {
            int reviewCount = 0;
            if (null!=reviewMapper.getTotalByPid(product.getId())){
                reviewCount = reviewMapper.getTotalByPid(product.getId());
            }
            int saleCount = 0;
            if (null!=orderItemMapper.getSaleCount(product.getId())){
                saleCount = orderItemMapper.getSaleCount(product.getId());
            }
            List<ProductImage> pis = productImageMapper.listByPid(product.getId());
            product.setImages(pis);
            product.setSaleCount(saleCount);
            product.setReviewCount(reviewCount);
        }
        return products;
    }

    @Override
    public int getTotalByCid(int cid) {
        return productMapper.getTotalByCid(cid);
    }
}
