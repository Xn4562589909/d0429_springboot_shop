package com.iweb.d0429_springboot_shop.controller.admin;

import com.iweb.d0429_springboot_shop.entity.Product;
import com.iweb.d0429_springboot_shop.entity.ProductImage;
import com.iweb.d0429_springboot_shop.service.ProductImageService;
import com.iweb.d0429_springboot_shop.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Yang
 * @date 2023/4/29 17:36
 */
@Controller
@RequestMapping("/admin/productImage")
public class ProductImageController {

    @Resource
    private ProductImageService productImageService;
    @Resource
    private ProductService productService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(int id,Model m){
        Product product = productService.get(id);
        List<ProductImage> piList = productImageService.list(id);
        m.addAttribute("piList",piList);
        m.addAttribute("product",product);
        return "admin/productImage/listImage";
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public String add(int pid,ProductImage productImage,Model m){
        Product product = productService.get(pid);
        productImage.setP(product);
        productImageService.add(productImage);
        return "redirect:/admin/productImage/list?id="+pid;
    }

    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    @ResponseBody
    public String delete(int id){
        productImageService.delete(id);
        return "success";
    }

}
