package com.iweb.d0429_springboot_shop.controller.fore;

import com.iweb.d0429_springboot_shop.comparator.ProductPriceComparator;
import com.iweb.d0429_springboot_shop.comparator.ProductReviewComparator;
import com.iweb.d0429_springboot_shop.comparator.ProductSaleComparator;
import com.iweb.d0429_springboot_shop.entity.Category;
import com.iweb.d0429_springboot_shop.entity.Product;
import com.iweb.d0429_springboot_shop.entity.PropertyValue;
import com.iweb.d0429_springboot_shop.entity.Review;
import com.iweb.d0429_springboot_shop.service.CategoryService;
import com.iweb.d0429_springboot_shop.service.ProductService;
import com.iweb.d0429_springboot_shop.service.PropertyValueService;
import com.iweb.d0429_springboot_shop.service.ReviewService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Yang
 * @date 2023/4/30 14:50
 */
@Controller
@RequestMapping("/fore/foreProduct")
public class ForeProductController {
    @Resource
    private ProductService productService;
    @Resource
    private PropertyValueService propertyValueService;
    @Resource
    private ReviewService reviewService;
    @Resource
    private CategoryService categoryService;

    @RequestMapping(value = "/show",method = RequestMethod.GET)
    public String show(int id,Model m){
        Product product = productService.get(id);
        List<PropertyValue> pvs = propertyValueService.list(id);
        List<Review> reviews = reviewService.list(id);
        m.addAttribute("pvs",pvs);
        m.addAttribute("product",product);
        m.addAttribute("reviews",reviews);
        return "fore/noLogin/showProduct";
    }

    @RequestMapping(value = "/cList",method = RequestMethod.GET)
    public String listByCid(int id,String sort,Model m){
        Category category = categoryService.get(id);
        Comparator<Product> comparator;
        if ("price".equals(sort)){
            comparator = new ProductPriceComparator();
            Collections.sort(category.getProducts(),comparator);
        }else if ("sale".equals(sort)){
            comparator = new ProductSaleComparator();
            Collections.sort(category.getProducts(),comparator);
        }else {
            comparator = new ProductReviewComparator();
            Collections.sort(category.getProducts(),comparator);
        }
        m.addAttribute("category",category);
        return "fore/noLogin/categoryShowProduct";
    }

    @RequestMapping(value = "/nameList",method = RequestMethod.GET)
    public String listByName(@RequestParam(value = "sort",required = false,defaultValue = "price")String sort, String searchProduct, Model m){
        List<Product> products = productService.list(searchProduct);
        Comparator<Product> comparator;
        if ("price".equals(sort)){
            comparator = new ProductPriceComparator();
            Collections.sort(products,comparator);
        }else if ("sale".equals(sort)){
            comparator = new ProductSaleComparator();
            Collections.sort(products,comparator);
        }else {
            comparator = new ProductReviewComparator();
            Collections.sort(products,comparator);
        }
        m.addAttribute("searchNameProduct",searchProduct);
        m.addAttribute("products",products);
        return "fore/noLogin/fuzzySearchProduct";
    }
}
