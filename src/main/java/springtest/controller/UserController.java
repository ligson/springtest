package springtest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by trq on 2016/8/26.
 */
@Controller
public class UserController {
    @RequestMapping("/login.html")
    public String toLogin() {
        return "login";
    }
}
