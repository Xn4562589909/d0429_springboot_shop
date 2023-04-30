package com.iweb.d0429_springboot_shop.controller.admin;

import com.github.pagehelper.PageHelper;
import com.iweb.d0429_springboot_shop.entity.Category;
import com.iweb.d0429_springboot_shop.entity.Property;
import com.iweb.d0429_springboot_shop.service.CategoryService;
import com.iweb.d0429_springboot_shop.service.PropertyService;
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
 * @date 2023/4/29 20:00
 */
@Controller
@RequestMapping("/admin/property")
public class PropertyController {

    @Resource
    private PropertyService propertyService;
    @Resource
    private CategoryService categoryService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(@RequestParam(value = "start",required = false,defaultValue = "0")int start, int id, Model m){
        Page page = new Page();
        page.calculateLast(propertyService.getTotalByCid(id));
        if (start<0){
            start=0;
        }
        if (start>page.getEnd()){
            start=page.getEnd();
        }
        page.setStart(start);
        PageHelper.offsetPage(page.getStart(),page.getCount());
        List<Property> properties = propertyService.list(id);
        m.addAttribute("cid",id);
        m.addAttribute("pts",properties);
        m.addAttribute("page",page);
        return "admin/property/listProperty";
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public String add(Property property,int cid){
        Category category = categoryService.get(cid);
        property.setCategory(category);
        propertyService.add(property);
        return "redirect:/admin/property/list?id="+cid;
    }

    @RequestMapping(value = "/edit",method = RequestMethod.GET)
    public String edit(int id,Model m){
        Property property = propertyService.get(id);
        m.addAttribute("pt",property);
        return "admin/property/editProperty";
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public String update(int cid,Property property){
        Category category = categoryService.get(cid);
        property.setCategory(category);
        propertyService.update(property);
        return "redirect:/admin/property/list?id="+cid;
    }

    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    @ResponseBody
    public String delete(int id){
        propertyService.delete(id);
        return "success";
    }
}
