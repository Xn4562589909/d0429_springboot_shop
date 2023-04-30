package com.iweb.d0429_springboot_shop.controller.fore;

import com.iweb.d0429_springboot_shop.entity.Order;
import com.iweb.d0429_springboot_shop.entity.Product;
import com.iweb.d0429_springboot_shop.entity.Review;
import com.iweb.d0429_springboot_shop.entity.User;
import com.iweb.d0429_springboot_shop.service.OrderService;
import com.iweb.d0429_springboot_shop.service.ProductService;
import com.iweb.d0429_springboot_shop.service.ReviewService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @author Yang
 * @date 2023/4/30 20:17
 */
@Controller
@RequestMapping("/fore/review")
public class ReviewController {

    @Resource
    private ReviewService reviewService;
    @Resource
    private ProductService productService;
    @Resource
    private OrderService orderService;

    @RequestMapping(value = "/comment",method = RequestMethod.GET)
    public String comment(int productId, int orderId, Review review,String anonymity, HttpServletRequest req){
        Product product = productService.get(productId);
        User user = (User) req.getSession().getAttribute("foreUser");
        review.setUser(user);
        review.setProduct(product);
        if ("true".equals(anonymity)){
            String fakeName = user.getAnonymousName();
            review.setNickname(fakeName);
        }else {
            review.setNickname(user.getName());
        }
        reviewService.add(review);
        Set<Product> productsComment = (Set<Product>) req.getSession().getAttribute("productsComment");
        if (productsComment.size()==1){
            Order order = orderService.get(orderId);
            order.setStatus("finish");
            orderService.update(order);
            req.getSession().removeAttribute("productsComment");
            return "redirect:/fore/order/listOrder";
        }else {
            Iterator<Product> iterator = productsComment.iterator();
            while (iterator.hasNext()){
                if (iterator.next().getId()==productId){
                    iterator.remove();
                }
            }
            return "redirect:/fore/order/comment?id="+orderId;
        }
    }

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(Model m,HttpServletRequest req){
        User user = (User) req.getSession().getAttribute("foreUser");
        List<Review> reviews = reviewService.listUserReviews(user.getId());
        m.addAttribute("reviews",reviews);
        return "fore/needLogin/review/listReview";
    }

    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    @ResponseBody
    public String delete(int id){
        reviewService.delete(id);
        return "success";
    }

    @RequestMapping(value = "/edit",method = RequestMethod.GET)
    public String edit(Model m,int id){
        Review review = reviewService.get(id);
        m.addAttribute("review",review);
        return "fore/needLogin/review/editReview";
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public String update(int rid,String content){
        Review review = reviewService.get(rid);
        review.setContent(content);
        reviewService.update(review);
        return "redirect:/fore/review/list";
    }
}
