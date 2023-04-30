package com.iweb.d0429_springboot_shop.controller.admin;

import com.iweb.d0429_springboot_shop.entity.User;
import com.iweb.d0429_springboot_shop.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Yang
 * @date 2023/4/29 15:33
 */
@Controller
@RequestMapping("/admin/user")
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ModelAndView login(User user, HttpServletRequest req){
        ModelAndView mav = new ModelAndView();
        if (userService.login(user)){
            req.getSession().setAttribute("user",user);
            mav.setViewName("redirect:/admin/category/list");
        }else {
            mav.setViewName("admin/user/login");
        }
        return mav;
    }

    @RequestMapping(value = "/exitLogin",method = RequestMethod.GET)
    public ModelAndView exitLogin(HttpServletRequest req){
        ModelAndView mav = new ModelAndView("admin/user/login");
        req.getSession().removeAttribute("user");
        return mav;
    }
}
