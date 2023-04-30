package com.iweb.d0429_springboot_shop.service.impl;

import com.iweb.d0429_springboot_shop.entity.ProductImage;
import com.iweb.d0429_springboot_shop.mapper.ProductImageMapper;
import com.iweb.d0429_springboot_shop.service.ProductImageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Yang
 * @date 2023/4/7 19:43
 */
@Service
public class ProductImageServiceImpl implements ProductImageService {

    @Resource
    private ProductImageMapper productImageMapper;

    @Override
    public List<ProductImage> list(int pid) {
        return productImageMapper.listByPid(pid);
    }

    @Override
    public void add(ProductImage pi) {
        productImageMapper.add(pi);
    }

    @Override
    public void delete(int id) {
        productImageMapper.delete(id);
    }
}
