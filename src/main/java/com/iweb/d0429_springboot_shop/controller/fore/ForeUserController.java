package com.iweb.d0429_springboot_shop.controller.fore;

import com.iweb.d0429_springboot_shop.entity.User;
import com.iweb.d0429_springboot_shop.service.OrderItemService;
import com.iweb.d0429_springboot_shop.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Yang
 * @date 2023/4/30 10:12
 */
@Controller
@RequestMapping("/fore/user")
public class ForeUserController {
    @Resource
    private UserService userService;
    @Resource
    private OrderItemService orderItemService;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(User user, HttpServletRequest req){
        if (userService.login(user)){
            user = userService.get(user);
            int oiNum = orderItemService.getShoppingCartsNum(user.getId());
            req.getSession().setAttribute("foreUser",user);
            req.getSession().setAttribute("oiNum",oiNum);
            return "redirect:/fore/homePage/list";
        }else {
            return "fore/noLogin/login";
        }
    }

    @RequestMapping(value = "/exitLogin",method = RequestMethod.GET)
    public String exitLogin(HttpServletRequest req){
        req.getSession().removeAttribute("foreUser");
        req.getSession().removeAttribute("oiNum");
        return "redirect:/fore/homePage/list";
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public String register(User user){
        if (userService.register(user)){
            return "fore/noLogin/login";
        }else {
            return "fore/noLogin/register";
        }
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public String update(HttpServletRequest req,String password){
        User user = (User) req.getSession().getAttribute("foreUser");
        user.setPassword(password);
        userService.update(user);
        user = userService.get(user);
        req.getSession().setAttribute("foreUser",user);
        return "redirect:/page/fore/needLogin/user/updateUser.jsp";
    }
}
