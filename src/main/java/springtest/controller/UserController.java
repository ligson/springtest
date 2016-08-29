package springtest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by ligson on 2016/8/26.
 * user
 */
@Controller
public class UserController {
    @RequestMapping(name = "/login.html", method = {RequestMethod.GET})
    public String toLogin() {
        return "login";
    }
}
