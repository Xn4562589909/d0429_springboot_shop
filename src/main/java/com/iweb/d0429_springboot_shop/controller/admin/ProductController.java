package com.iweb.d0429_springboot_shop.controller.admin;

import com.github.pagehelper.PageHelper;
import com.iweb.d0429_springboot_shop.entity.Category;
import com.iweb.d0429_springboot_shop.entity.Product;
import com.iweb.d0429_springboot_shop.service.CategoryService;
import com.iweb.d0429_springboot_shop.service.ProductService;
import com.iweb.d0429_springboot_shop.util.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Yang
 * @date 2023/4/29 18:25
 */
@Controller
@RequestMapping("/admin/product")
public class ProductController {

    @Resource
    private ProductService productService;
    @Resource
    private CategoryService categoryService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(@RequestParam(value = "start",required = false,defaultValue = "0")int start,int id, Model m){
        Page page = new Page();
        page.calculateLast(productService.getTotalByCid(id));
        if (start<0){
            start=0;
        }
        if (start>page.getEnd()){
            start=page.getEnd();
        }
        page.setStart(start);
        PageHelper.offsetPage(page.getStart(),page.getCount());
        List<Product> products = productService.list(id);
        m.addAttribute("cid",id);
        m.addAttribute("products",products);
        m.addAttribute("page",page);
        return "admin/product/listProduct";
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public String add(int cid,Product product){
        Category category = categoryService.get(cid);
        product.setCategory(category);
        productService.add(product);
        return "redirect:/admin/product/list?id="+cid;
    }

    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    @ResponseBody
    public String delete(int id){
        productService.delete(id);
        return "success";
    }

    @RequestMapping(value = "/edit",method = RequestMethod.GET)
    public String edit(int id,Model m){
        Product product = productService.get(id);
        m.addAttribute("p",product);
        return "admin/product/editProduct";
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public String update(Product product,int cid,Model m){
        Category category = categoryService.get(cid);
        product.setCategory(category);
        productService.update(product);
        return "redirect:/admin/product/list?id="+cid;
    }
}
