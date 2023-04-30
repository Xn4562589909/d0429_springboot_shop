package com.iweb.d0429_springboot_shop.controller.admin;

import com.iweb.d0429_springboot_shop.entity.Category;
import com.iweb.d0429_springboot_shop.entity.Product;
import com.iweb.d0429_springboot_shop.entity.Property;
import com.iweb.d0429_springboot_shop.entity.PropertyValue;
import com.iweb.d0429_springboot_shop.service.ProductService;
import com.iweb.d0429_springboot_shop.service.PropertyService;
import com.iweb.d0429_springboot_shop.service.PropertyValueService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Yang
 * @date 2023/4/29 20:57
 */
@Controller
@RequestMapping("/admin/propertyValue")
public class PropertyValueController {
    @Resource
    private ProductService productService;
    @Resource
    private PropertyService propertyService;
    @Resource
    private PropertyValueService propertyValueService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(int id, Model m){
        Product product = productService.get(id);
        Category category = product.getCategory();
        List<Property> properties = propertyService.list(category.getId());
        List<PropertyValue> propertyValues = propertyValueService.list(id);
        properties = propertyValueService.listNotAddPt(properties,propertyValues);
        m.addAttribute("pts",properties);
        m.addAttribute("pvs",propertyValues);
        m.addAttribute("product",product);
        return "admin/propertyValue/listPropertyValue";
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public String update(PropertyValue propertyValue){
        PropertyValue newPv = propertyValueService.get(propertyValue.getId());
        newPv.setValue(propertyValue.getValue());
        propertyValueService.update(newPv);
        return "success";
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public String add(String ptIdName,int pid,PropertyValue propertyValue){
        int ptId = Integer.parseInt(ptIdName.substring(0,ptIdName.indexOf("-")));
        Property property = propertyService.get(ptId);
        propertyValue.setProduct(productService.get(pid));
        propertyValue.setProperty(property);
        propertyValueService.add(propertyValue);
        return "redirect:/admin/propertyValue/list?id="+pid;
    }

    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public String delete(String pvIdValue){
        int pvId = Integer.parseInt(pvIdValue.substring(0,pvIdValue.indexOf("-")));
        Product product = propertyValueService.get(pvId).getProduct();
        propertyValueService.delete(pvId);
        return "redirect:/admin/propertyValue/list?id="+product.getId();
    }
}
