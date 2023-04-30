package com.iweb.d0429_springboot_shop.service.impl;

import com.iweb.d0429_springboot_shop.entity.ProductImage;
import com.iweb.d0429_springboot_shop.entity.Review;
import com.iweb.d0429_springboot_shop.mapper.ProductImageMapper;
import com.iweb.d0429_springboot_shop.mapper.ReviewMapper;
import com.iweb.d0429_springboot_shop.service.ReviewService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Yang
 * @date 2023/4/11 19:20
 */
@Service
public class ReviewServiceImpl implements ReviewService {

    @Resource
    private ReviewMapper reviewMapper;
    @Resource
    private ProductImageMapper productImageMapper;

    @Override
    public List<Review> list(int pid) {
        return reviewMapper.listByPid(pid);
    }

    @Override
    public void add(Review review) {
        reviewMapper.add(review);
    }

    @Override
    public List<Review> listUserReviews(int uid) {
        List<Review> reviews = reviewMapper.listByUid(uid);
        for (Review review:reviews) {
            List<ProductImage> pis = productImageMapper.listByPid(review.getProduct().getId());
            review.getProduct().setImages(pis);
        }
        return reviews;
    }

    @Override
    public Review get(int id) {
        return reviewMapper.get(id);
    }

    @Override
    public void delete(int id) {
        reviewMapper.delete(id);
    }

    @Override
    public void update(Review review) {
        reviewMapper.update(review);
    }
}
