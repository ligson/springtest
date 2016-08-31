package springtest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import springtest.domain.UserEntity;
import springtest.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by ligson on 2016/8/26.
 * user
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/login.html")
    public String toLogin() {
        return "login";
    }

    @RequestMapping("/reg.html")
    public String toReg() {
        return "reg";
    }

    @RequestMapping("/reg.do")
    public String reg(@RequestParam String username, @RequestParam String password) {
        UserEntity entity = userService.register(username, password);
        if (entity != null) {
            return "redirect:/user/list.html";
        } else {
            return "redirect:/reg.html?username=" + username;
        }
    }

    @RequestMapping("/user/list.html")
    public String list(HttpServletRequest request) {
        List<UserEntity> users = userService.list();
        request.setAttribute("users", users);
        return "user/list";
    }

    @RequestMapping("/user/logout.do")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/login.html";
    }

}
