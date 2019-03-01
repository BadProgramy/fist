package website.psuti.fist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import website.psuti.fist.configuration.Sender;
import website.psuti.fist.model.Role;
import website.psuti.fist.model.User;
import website.psuti.fist.service.UserService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminUserController {

    @Autowired
    private Sender sender;

    @Autowired
    private UserService userService;

    @RequestMapping("/admin/user/add")
    public String userAdd(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", Role.values());
        return "adminUsersAdd";
    }

    @RequestMapping("/admin/user/add/submit")
    public String userAddSubmit(@ModelAttribute("user") User user) throws SQLException {
        List<Role> roles = new ArrayList<>();
        for (String role: user.getRolesString()) {
            roles.add(Role.valueOf(role));
        }
        user.setRole(roles);
        user.setEnabled(false);
        userService.save(user);
        sender.send("Пример загаловка", "Пример текста", user.getUsername(), user);
        return "redirect:../add";
    }

    @RequestMapping("/user/enable/email={email}")
    public String activationUser(@PathVariable("email") String email) throws SQLException {
        User user = userService.findUserByName(email);
        user.setEnabled(true);
        userService.save(user);
        return "enabledAccount";
    }
}
