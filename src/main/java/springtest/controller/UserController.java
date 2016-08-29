package springtest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import springtest.domain.UserEntity;
import springtest.service.UserService;

/**
 * Created by ligson on 2016/8/26.
 * user
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(name = "/login.html", method = {RequestMethod.GET})
    public String toLogin() {
        return "login";
    }

    @RequestMapping(name = "/reg.html", method = {RequestMethod.GET})
    public String toReg() {
        return "reg";
    }

    @RequestMapping(name = "/reg.do", method = {RequestMethod.POST})
    public String reg(@RequestParam String username, @RequestParam String password) {
        UserEntity entity = userService.register(username, password);
        if (entity != null) {
            return "redirect:/user/list.html";
        } else {
            return "redirect:/reg.html?username=" + username;
        }
    }

}
