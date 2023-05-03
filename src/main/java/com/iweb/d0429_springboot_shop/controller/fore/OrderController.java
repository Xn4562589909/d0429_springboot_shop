package com.iweb.d0429_springboot_shop.controller.fore;

import com.iweb.d0429_springboot_shop.entity.Order;
import com.iweb.d0429_springboot_shop.entity.OrderItem;
import com.iweb.d0429_springboot_shop.entity.Product;
import com.iweb.d0429_springboot_shop.entity.User;
import com.iweb.d0429_springboot_shop.service.OrderItemService;
import com.iweb.d0429_springboot_shop.service.OrderService;
import com.iweb.d0429_springboot_shop.service.ProductService;
import com.iweb.d0429_springboot_shop.util.OrderCodeUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Yang
 * @date 2023/4/30 16:10
 */
@Controller
@RequestMapping("/fore/order")
public class OrderController {
    @Resource
    private OrderService orderService;
    @Resource
    private ProductService productService;
    @Resource
    private OrderItemService orderItemService;

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public String update(int id){
        Order order = orderService.get(id);
        Date currentTime = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        order.setPayDate(dateFormat.format(currentTime));
        order.setStatus("waitDelivery");
        orderService.update(order);
        return "redirect:/fore/order/listOrder";
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public String add(Order order,int pid,int pNum,HttpServletRequest req, Model m){
        User user = (User) req.getSession().getAttribute("foreUser");
        order.setUser(user);
        order.setOrderCode(OrderCodeUtil.getOrderId(user.getId()));
        order.setStatus("waitPay");
        orderService.add(order);
        Product product = productService.get(pid);
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setNumber(pNum);
        orderItem.setUser(user);
        orderItem.setProduct(product);
        orderItemService.add(orderItem);
        product.setStock(product.getStock()-pNum);
        productService.update(product);
        order = orderService.get(order.getId());
        m.addAttribute("order",order);
        return "fore/needLogin/order/payView";
    }

    @RequestMapping(value = "/addByCar",method = RequestMethod.POST)
    public String addByCar(Order order,HttpServletRequest req,Model m){
        User user = (User) req.getSession().getAttribute("foreUser");
        order.setUser(user);
        order.setOrderCode(OrderCodeUtil.getOrderId(user.getId()));
        order.setStatus("waitPay");
        orderService.update(order);
        List<OrderItem> ois = orderItemService.listByOid(order.getId());
        for (OrderItem oi:ois) {
            Product p = oi.getProduct();
            p.setStock(p.getStock()-oi.getNumber());
            productService.update(p);
        }
        order = orderService.get(order.getId());
        m.addAttribute("order",order);
        return "fore/needLogin/order/payView";
    }

    @RequestMapping(value = "/listOrder",method = RequestMethod.GET)
    public String listOrder(HttpServletRequest req,Model m){
        User user = (User)req.getSession().getAttribute("foreUser");
        List<Order> orders = orderService.list(user.getId());
        m.addAttribute("orders",orders);
        return "fore/needLogin/order/myOrder";
    }

    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    @ResponseBody
    public String delete(int id){
        orderService.delete(id);
        return "success";
    }

    @RequestMapping(value = "/payView",method = RequestMethod.GET)
    public String payView(int id,Model m){
        Order order = orderService.get(id);
        m.addAttribute("order",order);
        return "fore/needLogin/order/payView";
    }

    @RequestMapping(value = "/comment",method = RequestMethod.GET)
    public String comment(int id,Model m,HttpServletRequest req){
        Order order = orderService.get(id);
        List<OrderItem> ois = order.getOrderItems();
        Set<Product> products = new HashSet<>();
        for (OrderItem oi:ois) {
            products.add(oi.getProduct());
        }
        m.addAttribute("orderId",id);
        if (null==req.getSession().getAttribute("productsComment")){
            req.getSession().setAttribute("productsComment",products);
        }
        return "fore/needLogin/review/commentProduct";
    }

    @RequestMapping(value = "/confirm",method = RequestMethod.GET)
    public String confirm(int id){
        Order order = orderService.get(id);
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        order.setStatus("waitReview");
        order.setConfirmDate(sdf.format(date));
        orderService.update(order);
        return "redirect:/fore/order/listOrder";
    }
}
