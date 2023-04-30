package com.iweb.d0429_springboot_shop.controller.fore;

import com.iweb.d0429_springboot_shop.entity.Order;
import com.iweb.d0429_springboot_shop.entity.OrderItem;
import com.iweb.d0429_springboot_shop.entity.Product;
import com.iweb.d0429_springboot_shop.entity.User;
import com.iweb.d0429_springboot_shop.service.OrderItemService;
import com.iweb.d0429_springboot_shop.service.OrderService;
import com.iweb.d0429_springboot_shop.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Yang
 * @date 2023/4/30 15:21
 */
@Controller
@RequestMapping("/fore/orderItem")
public class OrderItemController {
    @Resource
    private OrderItemService orderItemService;
    @Resource
    private ProductService productService;
    @Resource
    private OrderService orderService;

    @RequestMapping(value = "/joinCar",method = RequestMethod.POST)
    @ResponseBody
    public String joinCar(int productNumber, int productId, Model m, HttpServletRequest req){
        User user = (User) req.getSession().getAttribute("foreUser");
        OrderItem orderItem = orderItemService.getCarOrderItemByPid(productId,user.getId());
        if (null == orderItem){
            orderItem = new OrderItem();
            orderItem.setNumber(productNumber);
            orderItem.setUser(user);
            Order order = new Order();
            order.setId(-1);
            orderItem.setOrder(order);
            orderItem.setProduct(productService.get(productId));
            orderItemService.add(orderItem);
            int oiNum = orderItemService.getShoppingCartsNum(user.getId());
            req.getSession().setAttribute("oiNum",oiNum);
        }else {
            int newNumber = productNumber+orderItem.getNumber();
            orderItem.setNumber(newNumber);
            Order order = new Order();
            order.setId(-1);
            orderItem.setOrder(order);
            orderItemService.update(orderItem);
        }
        return "success";
    }

    @RequestMapping(value = "/directBuy",method = RequestMethod.GET)
    public String directBuy(int productNumber,int productId,Model m){
        Product product = productService.get(productId);
        m.addAttribute("productNum",productNumber);
        m.addAttribute("product",product);
        return "fore/needLogin/orderItem/directBuy";
    }

    @RequestMapping(value = "/carBuy",method = RequestMethod.POST)
    @ResponseBody
    public String carBuy(int[] list,HttpServletRequest req){
        User user = (User) req.getSession().getAttribute("foreUser");
        Order order = new Order();
        orderService.add(order);
        for (int i = 0; i < list.length; i++) {
            OrderItem orderItem = orderItemService.get(list[i]);
            orderItem.setOrder(order);
            orderItemService.update(orderItem);
        }
        int oiNum = orderItemService.getShoppingCartsNum(user.getId());
        req.getSession().setAttribute("oiNum",oiNum);
        return ""+order.getId();
    }

    @RequestMapping(value = "/goOrder",method = RequestMethod.GET)
    public String goOrder(int id,Model m){
        List<OrderItem> orderItems = orderItemService.listByOid(id);
        m.addAttribute("orderItems",orderItems);
        return "fore/needLogin/orderItem/directBuy";
    }

    @RequestMapping(value = "shoppingCar",method = RequestMethod.GET)
    public String shoppingCar(HttpServletRequest req,Model m){
        User user = (User) req.getSession().getAttribute("foreUser");
        List<OrderItem> orderItems = orderItemService.listCar(user.getId());
        m.addAttribute("orderItems",orderItems);
        return "fore/needLogin/orderItem/shoppingCar";
    }

    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    @ResponseBody
    public String delete(int id,HttpServletRequest req){
        orderItemService.delete(id);
        User user = (User)req.getSession().getAttribute("foreUser");
        int oiNum = orderItemService.getShoppingCartsNum(user.getId());
        req.getSession().setAttribute("oiNum",oiNum);
        return "success";
    }

    @RequestMapping(value = "/addProductNum",method = RequestMethod.GET)
    @ResponseBody
    public String addProductNum(int id){
        OrderItem orderItem = orderItemService.get(id);
        Order order = new Order();
        order.setId(-1);
        orderItem.setOrder(order);
        orderItem.setNumber(orderItem.getNumber()+1);
        orderItemService.update(orderItem);
        return "success";
    }

    @RequestMapping(value = "/subProductNum",method = RequestMethod.GET)
    @ResponseBody
    public String subProductNum(int id){
        OrderItem orderItem = orderItemService.get(id);
        Order order = new Order();
        order.setId(-1);
        orderItem.setOrder(order);
        orderItem.setNumber(orderItem.getNumber()-1);
        orderItemService.update(orderItem);
        return "success";
    }
}
