package com.iweb.d0429_springboot_shop.controller.fore;

import com.iweb.d0429_springboot_shop.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

/**
 * @author Yang
 * @date 2023/4/30 10:35
 */
@Controller
@RequestMapping("/fore/homePage")
public class HomePageController {
    @Resource
    private CategoryService categoryService;
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(Model m){
        m.addAttribute("categories",categoryService.list());
        return "fore/noLogin/homePage";
    }
}
