package com.iweb.d0429_springboot_shop.comparator;

import com.iweb.d0429_springboot_shop.entity.Product;

import java.util.Comparator;

/**
 * @author Yang
 * @date 2023/4/12 0:49
 */
public class ProductPriceComparator implements Comparator<Product> {
    @Override
    public int compare(Product o1, Product o2) {
        return o1.getPromotePrice().compareTo(o2.getPromotePrice());
    }
}
