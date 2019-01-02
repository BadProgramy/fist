package website.psuti.fist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import website.psuti.fist.model.Role;
import website.psuti.fist.model.User;
import website.psuti.fist.service.MainPageService;
import website.psuti.fist.service.UserService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {
    @Autowired
    private MainPageService mainPageService;

    @Autowired
    private UserService userService;

    @RequestMapping("/admin")
    public String adminPage() {
        return "adminPage";
    }

    @RequestMapping("/admin/login")
    public String login() {
        return "authentication";
    }

    /*@RequestMapping("/admin/login/submit")
    public String testCreateUser() throws SQLException {
        List<Role> roles = new ArrayList<>();
        roles.add(Role.FULLADMIN);
        User user = new User();
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);
        user.setFirstname("Айрат");
        user.setSecondname("Мухутдинов");
        user.setUsername("airat23059");
        user.setPassword("19970808");
        user.setPagevk("https://vk.com/id109488730");
        user.setRole(roles);
        userService.save(user);
        return authPage();
    }*/
}
