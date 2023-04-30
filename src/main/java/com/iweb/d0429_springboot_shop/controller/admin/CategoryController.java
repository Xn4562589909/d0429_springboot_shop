package com.iweb.d0429_springboot_shop.controller.admin;

import com.github.pagehelper.PageHelper;
import com.iweb.d0429_springboot_shop.entity.Category;
import com.iweb.d0429_springboot_shop.service.CategoryService;
import com.iweb.d0429_springboot_shop.util.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Yang
 * @date 2023/4/29 11:42
 */
@Controller
@RequestMapping("/admin/category")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ModelAndView list(@RequestParam(value = "start",required = false,defaultValue = "0")int start){
        ModelAndView mav = new ModelAndView("admin/category/listCategory");
        int total = categoryService.total();
        Page page = new Page();
        page.calculateLast(total);
        if (start < 0){
            start = 0;
        }
        if (start > page.getEnd()){
            start = page.getEnd();
        }
        page.setStart(start);
        PageHelper.offsetPage(page.getStart(),page.getCount());
        List<Category> categories = categoryService.list();
        mav.addObject("categories",categories);
        mav.addObject("page",page);
        return mav;
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public ModelAndView add(Category category){
        ModelAndView mav = new ModelAndView("redirect:/admin/category/list");
        categoryService.add(category);
        return mav;
    }

    @RequestMapping(value = "/edit",method = RequestMethod.GET)
    public ModelAndView edit(int id){
        ModelAndView mav = new ModelAndView("admin/category/editCategory");
        Category category = categoryService.edit(id);
        mav.addObject("c",category);
        return mav;
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public ModelAndView update(Category category){
        ModelAndView mav = new ModelAndView("redirect:/admin/category/list");
        categoryService.update(category);
        return mav;
    }

    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    @ResponseBody
    public String delete(int id){
        categoryService.delete(id);
        return "success";
    }
}
